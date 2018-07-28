package services;

import java.util.List;

import com.google.inject.ImplementedBy;

import dtos.BlockUpdateDTO;
import dtos.CreateFriendConnectionDTO;
import dtos.GetUsersCanReceiveUpdateDTO;
import dtos.SubscribeToUpdateDTO;
import responses.CommonFriendsResponse;
import responses.SuccessResponse;
import responses.UserCanReceiveUpdateResponse;
import responses.UserFriendResponse;

@ImplementedBy(UserServiceImpl.class)
public interface IUserService {

	public SuccessResponse createFriendConnection(CreateFriendConnectionDTO dto);
	
	public UserFriendResponse getUserFriends(String userEmail);
	
	public CommonFriendsResponse findCommonFriends(List<String> emailList);
	
	public SuccessResponse subscribeToUpdate(SubscribeToUpdateDTO dto);
	
	public SuccessResponse blockUpdate(BlockUpdateDTO dto);
	
	public UserCanReceiveUpdateResponse getUsersCanReceiveUpdateFrom(GetUsersCanReceiveUpdateDTO dto);
}
