package services;

import javax.inject.Inject;

import com.avaje.ebean.Ebean;

import dto.CreateFriendConnectionDTO;
import models.Friend;
import models.User;
import repositories.IFriendRepository;
import repositories.IUserRepository;
import specification.UserSpecification;

public class UserServiceImpl implements IUserService{

	@Inject
	IUserRepository userRepository;
	
	@Inject
	IFriendRepository friendRepository;
	
	
	private static final int CREATE_FRIEND_SUPPORTED_SIZE = 2;
	
	@Override
	public void createFriendConnection(CreateFriendConnectionDTO dto) {
		if(dto.getFriends().size() != CREATE_FRIEND_SUPPORTED_SIZE) {
			//TODO throw exception
		}
		
		UserSpecification userSpec = new UserSpecification();
		userSpec.setEmail(dto.getFriends().get(0));
		User firstUser = userRepository.queryUnique(userSpec);
		
		userSpec = new UserSpecification();
		userSpec.setEmail(dto.getFriends().get(1));
		User secondUser = userRepository.queryUnique(userSpec);
		
		Ebean.beginTransaction();
		
		try{
			Friend firstFriendRelation = new Friend();
			firstFriendRelation.setUser(firstUser);
			firstFriendRelation.setFriend(secondUser);
			
			Friend secondFriendRelation = new Friend();
			secondFriendRelation.setUser(secondUser);
			secondFriendRelation.setFriend(firstUser);
			
			friendRepository.add(firstFriendRelation);
			friendRepository.add(secondFriendRelation);
			Ebean.commitTransaction();
		}finally {
			Ebean.endTransaction();
		}
	}   
}
