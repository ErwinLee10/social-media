package services;

import com.google.inject.ImplementedBy;

import dto.CreateFriendConnectionDTO;

@ImplementedBy(UserServiceImpl.class)
public interface IUserService {

	public void createFriendConnection(CreateFriendConnectionDTO dto);
}
