package exceptions;

public class RequestPayloadException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RequestPayloadException() {
		super("Unsupported Request Payload", "Unsupported Request Payload", 2, "", 400);
	}
}
