import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import dtos.BlockUpdateDTO;
import dtos.CreateFriendConnectionDTO;
import dtos.GetUsersCanReceiveUpdateDTO;
import dtos.SubscribeToUpdateDTO;
import models.FriendConnection;
import models.User;
import models.UserUpdateSubscription;
import repositories.IFriendConnectionRepository;
import repositories.IUserRepository;
import repositories.IUserUpdateSubscriptionRepository;
import responses.CommonFriendsResponse;
import responses.SuccessResponse;
import responses.UserCanReceiveUpdateResponse;
import responses.UserFriendResponse;
import services.UserServiceImpl;
import specifications.CommonFriendSpecification;
import specifications.FriendConnectionSpecification;
import specifications.UserSpecification;
import specifications.UserUpdateSubscriptionSpecification;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	@Mock private IUserRepository userRepository;
	
	@Mock IUserUpdateSubscriptionRepository userUpdateSubscriptionRepository;
	
	@Mock IFriendConnectionRepository friendRepository;
	
	@InjectMocks private UserServiceImpl userService;
	
	@Test
	public void testCreateFriendConnectionSuccess() {
	  User user = new User();
	  
	  when(userRepository.queryUnique(any(UserSpecification.class))).thenReturn(user);
	  when(userUpdateSubscriptionRepository.queryUnique(any(UserUpdateSubscriptionSpecification.class))).thenReturn(null);
	  when(friendRepository.queryUnique(any(FriendConnectionSpecification.class))).thenReturn(null);
	  
	  CreateFriendConnectionDTO dto = new CreateFriendConnectionDTO();
	  List<String> friends = new ArrayList<>();
	  friends.add("erwin@example.com");
	  friends.add("kamu@example.com");
	  dto.setFriends(friends);
	  
	  
	  assertEquals(userService.createFriendConnection(dto), new SuccessResponse()); 
	  verify(userRepository, times(2)).queryUnique(any(UserSpecification.class));
	  verify(userUpdateSubscriptionRepository, times(2)).queryUnique(any(UserUpdateSubscriptionSpecification.class));
	  verify(friendRepository, times(2)).queryUnique(any(FriendConnectionSpecification.class));
	}

	@Test
	public void testGetUserFriendsSuccess() {
	  String friendEmail = "friend@example.com";
	  User user = new User();
	  user.setEmail("erwin@example.com");
	 
	  List<FriendConnection> friends = new ArrayList<>();
	  User friend = new User();
	  friend.setEmail(friendEmail);
	  FriendConnection friendConnection = new FriendConnection();
	  friendConnection.setFriend(friend);
	  friends.add(friendConnection);
	  
	  user.setFriendsConnection(friends);
	  
	  UserFriendResponse resp = new UserFriendResponse();
	  List<String> friendEmails = new ArrayList<>();
	  friendEmails.add(friendEmail);
	  resp.setFriends(friendEmails);
	  resp.setCount(1);
	  
	  when(userRepository.queryUnique(any(UserSpecification.class))).thenReturn(user);
	  assertEquals(userService.getUserFriends("erwin@example.com"), resp); 
	  verify(userRepository, times(1)).queryUnique(any(UserSpecification.class));
	}
	
	
	@Test
	public void testfindCommonFriendsSuccess() {
	  User friend = new User();
	  friend.setEmail("firstFriend@example.com");
	  List<User> friends = new ArrayList<>();
	  friends.add(friend);
	  when(userRepository.query(any(CommonFriendSpecification.class))).thenReturn(friends);
	  
	  CommonFriendsResponse resp = new CommonFriendsResponse();
	  resp.setFriends(friends.stream().map(e -> e.getEmail()).collect(Collectors.toList()));
	  resp.setCount(friends.size());

	  List<String> emails = new ArrayList<>();
	  emails.add("firstUser@example.com");
	  emails.add("secondUser@example.com");
	  
	  assertEquals(userService.findCommonFriends(emails), resp); 
	  verify(userRepository, times(1)).query(any(CommonFriendSpecification.class));
	}
	
	@Test
	public void testSubscribeToUpdateSuccess() {
	  SubscribeToUpdateDTO dto = new SubscribeToUpdateDTO();
	  dto.setRequestor("erwin@example.com");
	  dto.setTarget("target@example.com");
	  
	  User user = new User();
	  when(userRepository.queryUnique(any(UserSpecification.class))).thenReturn(user);
	  
	  assertEquals(userService.subscribeToUpdate(dto), new SuccessResponse()); 
	  verify(userRepository, times(2)).queryUnique(any(UserSpecification.class));
	}
	
	@Test
	public void testBlockUpdateSuccess() {
	  BlockUpdateDTO dto = new BlockUpdateDTO();
	  dto.setRequestor("erwin@example.com");
	  dto.setTarget("target@example.com");
	  
	  User user = new User();
	  when(userRepository.queryUnique(any(UserSpecification.class))).thenReturn(user);
	  
	  assertEquals(userService.blockUpdate(dto), new SuccessResponse()); 
	  verify(userRepository, times(2)).queryUnique(any(UserSpecification.class));
	}
	
	@Test
	public void testgetUsersCanReceiveUpdateFromSuccess() {
	GetUsersCanReceiveUpdateDTO dto = new GetUsersCanReceiveUpdateDTO();
	  dto.setSender("erwin@example.com");
	  dto.setText("target@example.com");
	  
	  User friend = new User();
	  friend.setEmail("friend@example.com");
	  
	  FriendConnection friendConnection = new FriendConnection();
	  friendConnection.setFriend(friend);
	  
	  List<FriendConnection> friendConnections = new ArrayList<>();
	  friendConnections.add(friendConnection);
	  
	  User hater = new User();
	  hater.setEmail("friend@example.com");
	  
	  UserUpdateSubscription haterSubscriber = new UserUpdateSubscription();
	  haterSubscriber.setRequestor(hater);
	  
	  List<UserUpdateSubscription> haters = new ArrayList<>();
	  haters.add(haterSubscriber);
	  
	  User fan = new User();
	  fan.setEmail("fans@example.com");
	  
	  UserUpdateSubscription fansSubscriber = new UserUpdateSubscription();
	  fansSubscriber.setRequestor(fan);
	  
	  List<UserUpdateSubscription> fans = new ArrayList<>();
	  fans.add(fansSubscriber);
	  
	  User user = new User();
	  user.setFriendsConnection(friendConnections);
	  user.setMyHaters(haters);
	  user.setMyFans(fans);
	  
	  
	  when(userRepository.queryUnique(any(UserSpecification.class))).thenReturn(user);
	  
	  Set<String> emails = new HashSet<>();
	  emails.add("target@example.com");
	  emails.add("fans@example.com");
	  
	  UserCanReceiveUpdateResponse resp = new UserCanReceiveUpdateResponse();
	  resp.setRecipients(emails);
	  
	  assertEquals(userService.getUsersCanReceiveUpdateFrom(dto), resp);
	  verify(userRepository, times(1)).queryUnique(any(UserSpecification.class));
	}

}
