package services;

import java.util.List;

import com.google.inject.ImplementedBy;

import dto.BlockUpdateDTO;
import dto.CreateFriendConnectionDTO;
import dto.SubscribeToUpdateDTO;
import responses.CommonFriendsResponse;
import responses.SuccessResponse;
import responses.UserFriendResponse;

@ImplementedBy(UserServiceImpl.class)
public interface IUserService {

	public SuccessResponse createFriendConnection(CreateFriendConnectionDTO dto);
	
	public UserFriendResponse getUserFriends(String userEmail);
	
	public CommonFriendsResponse findCommonFriends(List<String> emailList);
	
	public SuccessResponse subscribeToUpdate(SubscribeToUpdateDTO dto);
	
	public SuccessResponse blockUpdate(BlockUpdateDTO dto);
}
