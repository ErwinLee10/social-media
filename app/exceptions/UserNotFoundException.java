package exceptions;

public class UserNotFoundException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNotFoundException() {
		super("User Not Found", "User Not Found", 1, "", 404);
	}
}
