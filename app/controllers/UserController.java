package controllers;



import javax.inject.Inject;

import dto.CreateFriendConnectionDTO;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import response.SuccessResponse;
import services.IUserService;

public class UserController extends Controller{

	@Inject
	FormFactory formFactory;
	
	@Inject
	IUserService userService;
	
	public Result createFriendConnection() throws ClassNotFoundException {
		Form<CreateFriendConnectionDTO> form =  formFactory.form(CreateFriendConnectionDTO.class).bindFromRequest();
		CreateFriendConnectionDTO dto = form.get();
		userService.createFriendConnection(dto);

		return ok(Json.toJson(new SuccessResponse()));
		
	}
}
