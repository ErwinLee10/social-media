package services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.avaje.ebean.Ebean;

import dtos.BlockUpdateDTO;
import dtos.CreateFriendConnectionDTO;
import dtos.GetUsersCanReceiveUpdateDTO;
import dtos.SubscribeToUpdateDTO;
import exceptions.NotAllowToAddFriendDueToBlockUpdateException;
import exceptions.RequestPayloadException;
import exceptions.UserNotFoundException;
import models.FriendConnection;
import models.SubscriptionStatus;
import models.User;
import models.UserUpdateSubscription;
import repositories.IFriendConnectionRepository;
import repositories.IUserRepository;
import repositories.IUserUpdateSubscriptionRepository;
import responses.CommonFriendsResponse;
import responses.SuccessResponse;
import responses.UserCanReceiveUpdateResponse;
import responses.UserFriendResponse;
import specifications.CommonFriendSpecification;
import specifications.FriendConnectionSpecification;
import specifications.UserSpecification;
import specifications.UserUpdateSubscriptionSpecification;

public class UserServiceImpl implements IUserService {

	@Inject
	IUserRepository userRepository;

	@Inject
	IFriendConnectionRepository friendRepository;

	@Inject
	IUserUpdateSubscriptionRepository userUpdateSubscriptionRepository;

	private static final int CREATE_FRIEND_SUPPORTED_SIZE = 2;

	@Override
	public SuccessResponse createFriendConnection(CreateFriendConnectionDTO dto) {
		if (dto.getFriends().size() != CREATE_FRIEND_SUPPORTED_SIZE) {
			RequestPayloadException exception = new RequestPayloadException();
			exception.setMoreInfo("friends length must be 2");
			throw exception;
		}

		User firstUser = findUserByEmail(dto.getFriends().get(0));
		User secondUser = findUserByEmail(dto.getFriends().get(1));

		Ebean.beginTransaction();

		try {
			validateIfExistBlockUpdateBetweenTheseUsers(firstUser, secondUser);
			createFriendshipIfNotExist(firstUser, secondUser);
			createFriendshipIfNotExist(secondUser, firstUser);
			Ebean.commitTransaction();
		} finally {
			Ebean.endTransaction();
		}

		return new SuccessResponse();
	}
	
	private void validateIfExistBlockUpdateBetweenTheseUsers(User firstUser, User secondUser) {
		UserUpdateSubscription firstUserToSecondUser = getUserUpdateSubscription(
				firstUser.getEmail(), secondUser.getEmail());
		UserUpdateSubscription secondUserToFirstUser = getUserUpdateSubscription(
				secondUser.getEmail(), firstUser.getEmail());
		
		if(firstUserToSecondUser != null && 
				firstUserToSecondUser.getSubscriptionStatus() == SubscriptionStatus.BLOCKED) {
			throw new NotAllowToAddFriendDueToBlockUpdateException(
					firstUser.getEmail(), secondUser.getEmail());
		}
		
		if(secondUserToFirstUser != null && 
				secondUserToFirstUser.getSubscriptionStatus() == SubscriptionStatus.BLOCKED) {
			throw new NotAllowToAddFriendDueToBlockUpdateException(
					secondUser.getEmail(), firstUser.getEmail());
		}
	}

	private void createFriendshipIfNotExist(User user, User friend) {
		FriendConnection friendRelation;
		FriendConnectionSpecification spec = new FriendConnectionSpecification();
		spec.setFriendEmail(friend.getEmail());
		spec.setUserEmail(user.getEmail());
		friendRelation = friendRepository.queryUnique(spec);
		if (friendRelation == null) {
			friendRelation = new FriendConnection();
			friendRelation.setUser(user);
			friendRelation.setFriend(friend);
			friendRepository.add(friendRelation);
		}
	}

	@Override
	public UserFriendResponse getUserFriends(String userEmail) {
		User user = findUserByEmail(userEmail);
		List<String> friendsList = user.getFriendsConnection().stream().map(e -> e.getFriend().getEmail())
				.collect(Collectors.toList());

		UserFriendResponse resp = new UserFriendResponse();
		resp.setFriends(friendsList);
		resp.setCount(friendsList.size());

		return resp;
	}

	private User findUserByEmail(String email) {
		UserSpecification userSpec = new UserSpecification();
		userSpec.setEmail(email);
		User user = userRepository.queryUnique(userSpec);

		if (user == null) {
			throw new UserNotFoundException();
		}

		return user;
	}

	@Override
	public CommonFriendsResponse findCommonFriends(List<String> emailList) {
		CommonFriendSpecification spec = new CommonFriendSpecification();
		spec.setEmail(emailList);
		List<User> friends = userRepository.query(spec);
		CommonFriendsResponse resp = new CommonFriendsResponse();
		resp.setFriends(friends.stream().map(e -> e.getEmail()).collect(Collectors.toList()));

		resp.setCount(friends.size());

		return resp;
	}

	@Override
	public SuccessResponse subscribeToUpdate(SubscribeToUpdateDTO dto) {
		UserUpdateSubscription updateSubscription = getOrCreateUserUpdateSubscriptionIfNotExist(
				dto.getRequestor(), dto.getTarget());
		updateSubscription.setSubscriptionStatus(SubscriptionStatus.SUBSCRIBED);

		userUpdateSubscriptionRepository.add(updateSubscription);

		return new SuccessResponse();
	}

	@Override
	public SuccessResponse blockUpdate(BlockUpdateDTO dto) {
		UserUpdateSubscription updateSubscription = getOrCreateUserUpdateSubscriptionIfNotExist(
				dto.getRequestor(), dto.getTarget());
		updateSubscription.setSubscriptionStatus(SubscriptionStatus.BLOCKED);

		userUpdateSubscriptionRepository.add(updateSubscription);

		return new SuccessResponse();
	}

	UserUpdateSubscription getUserUpdateSubscription(String requestor, String target) {
		return userUpdateSubscriptionRepository
		.queryUnique(new UserUpdateSubscriptionSpecification(requestor, target));
	}
	
	UserUpdateSubscription getOrCreateUserUpdateSubscriptionIfNotExist(String requestor, String target) {
		UserUpdateSubscription updateSubscription;
		updateSubscription = getUserUpdateSubscription(requestor, target);

		if (updateSubscription == null) {
			User requestorUser = findUserByEmail(requestor);
			User targetUser = findUserByEmail(target);

			updateSubscription = new UserUpdateSubscription();
			updateSubscription.setRequestor(requestorUser);
			updateSubscription.setTarget(targetUser);
		}

		return updateSubscription;
	}

	@Override
	public UserCanReceiveUpdateResponse getUsersCanReceiveUpdateFrom(GetUsersCanReceiveUpdateDTO dto) {
		User user = findUserByEmail(dto.getSender());

		UserCanReceiveUpdateResponse resp = new UserCanReceiveUpdateResponse();
		resp.setRecipients(findEligibleUsersToReceiveUpdate(user, dto.getText()));

		return resp;
	}

	public Set<String> findEligibleUsersToReceiveUpdate(User user, String message) {
		Set<String> myHatersEmail = user.getMyHaters().stream().map(e -> e.getRequestor().getEmail())
				.collect(Collectors.toSet());

		List<String> myFriends = user.getFriendsConnection().stream()
				.filter(e -> !myHatersEmail.contains(e.getFriend().getEmail())).map(e -> e.getFriend().getEmail())
				.collect(Collectors.toList());

		Set<String> myFans = user.getMyFans().stream().filter(e -> !myHatersEmail.contains(e.getRequestor().getEmail()))
				.map(e -> e.getRequestor().getEmail()).collect(Collectors.toSet());

		Set<String> eligibleUserToReceiveUpdates = new HashSet<>();
		eligibleUserToReceiveUpdates.addAll(myFriends);
		eligibleUserToReceiveUpdates.addAll(myFans);
		eligibleUserToReceiveUpdates.addAll(findEmailAddresses(message));

		return eligibleUserToReceiveUpdates;
	}

	public Set<String> findEmailAddresses(String input) {
		Pattern p = Pattern.compile("\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b", Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(input);
		Set<String> emails = new HashSet<String>();
		while (matcher.find()) {
			emails.add(matcher.group());
		}

		return emails;
	}

}
