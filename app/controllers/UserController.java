package controllers;



import javax.inject.Inject;

import dto.BlockUpdateDTO;
import dto.CreateFriendConnectionDTO;
import dto.FindCommonFriendsDTO;
import dto.GetUserFriendDTO;
import dto.GetUsersCanReceiveUpdateDTO;
import dto.SubscribeToUpdateDTO;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.IUserService;

public class UserController extends Controller{

	@Inject
	FormFactory formFactory;
	
	@Inject
	IUserService userService;
	
	public Result createFriendConnection(){
		Form<CreateFriendConnectionDTO> form =  formFactory.form(CreateFriendConnectionDTO.class).bindFromRequest();
		CreateFriendConnectionDTO dto = form.get();

		return ok(Json.toJson(userService.createFriendConnection(dto)));
		
	}
	
	public Result getUserFriends() {
		Form<GetUserFriendDTO> form =  formFactory.form(GetUserFriendDTO.class).bindFromRequest();
		GetUserFriendDTO dto = form.get();
		
		return ok(Json.toJson(userService.getUserFriends(dto.getEmail())));
	}
	
	public Result getCommonFriends() {
		Form<FindCommonFriendsDTO> form =  formFactory.form(FindCommonFriendsDTO.class).bindFromRequest();
		FindCommonFriendsDTO dto = form.get();
		
		return ok(Json.toJson(userService.findCommonFriends(dto.getFriends())));
	}
	
	public Result subscribeToUpdate() {
		Form<SubscribeToUpdateDTO> form = formFactory.form(SubscribeToUpdateDTO.class).bindFromRequest();
		SubscribeToUpdateDTO dto = form.get();
		
		return ok(Json.toJson(userService.subscribeToUpdate(dto)));
		
	}
	
	public Result blockUpdate() {
		Form<BlockUpdateDTO> form = formFactory.form(BlockUpdateDTO.class).bindFromRequest();
		BlockUpdateDTO dto = form.get();
		
		return ok(Json.toJson(userService.blockUpdate(dto)));
		
	}
	
	public Result getUsersCanReceiveUpdate() {
		Form<GetUsersCanReceiveUpdateDTO> form = formFactory.form(GetUsersCanReceiveUpdateDTO.class).bindFromRequest();
		GetUsersCanReceiveUpdateDTO dto = form.get();
		return ok(Json.toJson(userService.getUsersCanReceiveUpdateFrom(dto)));
	}
}
