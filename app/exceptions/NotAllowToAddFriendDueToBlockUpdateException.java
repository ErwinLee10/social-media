package exceptions;

public class NotAllowToAddFriendDueToBlockUpdateException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotAllowToAddFriendDueToBlockUpdateException(String user, String target) {
		super(String.format("Not Allow to add friend since user: %s is blocking update from user: %s", user, target), 
				String.format("Not Allow to add friend since user: %s is blocking update from user: %s", user, target), 
				3, "", 400);
	}
}
