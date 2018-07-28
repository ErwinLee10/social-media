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
import models.FriendConnection;
import models.SubscriptionStatus;
import models.User;
import models.UserUpdateSubscription;
import repositories.IFriendRepository;
import repositories.IUserRepository;
import repositories.IUserUpdateSubscriptionRepository;
import responses.CommonFriendsResponse;
import responses.SuccessResponse;
import responses.UserCanReceiveUpdateResponse;
import responses.UserFriendResponse;
import specifications.CommonFriendSpecification;
import specifications.UserSpecification;
import specifications.UserUpdateSubscriptionSpecification;

public class UserServiceImpl implements IUserService {

	@Inject
	IUserRepository userRepository;

	@Inject
	IFriendRepository friendRepository;

	@Inject
	IUserUpdateSubscriptionRepository userUpdateSubscriptionRepository;

	private static final int CREATE_FRIEND_SUPPORTED_SIZE = 2;

	@Override
	public SuccessResponse createFriendConnection(CreateFriendConnectionDTO dto) {
		if (dto.getFriends().size() != CREATE_FRIEND_SUPPORTED_SIZE) {
			// TODO throw exception
		}

		// TODO check throw user not found exception
		User firstUser = findUserByEmail(dto.getFriends().get(0));
		User secondUser = findUserByEmail(dto.getFriends().get(1));

		Ebean.beginTransaction();

		try {
			FriendConnection firstFriendRelation = new FriendConnection();
			firstFriendRelation.setUser(firstUser);
			firstFriendRelation.setFriend(secondUser);

			FriendConnection secondFriendRelation = new FriendConnection();
			secondFriendRelation.setUser(secondUser);
			secondFriendRelation.setFriend(firstUser);

			friendRepository.add(firstFriendRelation);
			friendRepository.add(secondFriendRelation);
			Ebean.commitTransaction();
		} finally {
			Ebean.endTransaction();
		}

		return new SuccessResponse();
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
		return userRepository.queryUnique(userSpec);
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
		UserUpdateSubscription updateSubscription = getOrCreateIfNotExist(dto.getRequestor(), dto.getTarget());
		updateSubscription.setSubscriptionStatus(SubscriptionStatus.SUBSCRIBED);

		userUpdateSubscriptionRepository.add(updateSubscription);

		return new SuccessResponse();
	}

	@Override
	public SuccessResponse blockUpdate(BlockUpdateDTO dto) {
		// TODO check throw user not found exception
		UserUpdateSubscription updateSubscription = getOrCreateIfNotExist(dto.getRequestor(), dto.getTarget());
		updateSubscription.setSubscriptionStatus(SubscriptionStatus.BLOCKED);

		userUpdateSubscriptionRepository.add(updateSubscription);

		return new SuccessResponse();
	}

	UserUpdateSubscription getOrCreateIfNotExist(String requestor, String target) {
		UserUpdateSubscription updateSubscription;
		updateSubscription = userUpdateSubscriptionRepository
				.queryUnique(new UserUpdateSubscriptionSpecification(requestor, target));

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
		Set<String> myHatersEmail  = user
				.getMyHaters()
				.stream()
				.map(e -> e.getRequestor().getEmail())
				.collect(Collectors.toSet());
		
		List <String> myFriends= user.
				getFriendsConnection()
				.stream()
				.filter(e -> !myHatersEmail.contains(e.getFriend().getEmail()))
				.map(e -> e.getFriend().getEmail())
				.collect(Collectors.toList());
	
		Set<String> myFans  = user
				.getMyFans()
				.stream()
				.filter(e -> !myHatersEmail.contains(e.getRequestor().getEmail()))
				.map(e -> e.getRequestor().getEmail())
				.collect(Collectors.toSet());
		
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
