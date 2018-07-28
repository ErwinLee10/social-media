package exceptions;

public class BaseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userMessage;

	private String internalMessage;

	private int code;

	private String moreInfo = "";

	private int httpErrorCode;

	public BaseException(String userMessage, String internalMessage, int code, String moreInfo, int httpErrorCode) {
		super(internalMessage);
		this.userMessage = userMessage;
		this.internalMessage = internalMessage;
		this.code = code;
		this.moreInfo = moreInfo;
		this.httpErrorCode = httpErrorCode;
	}

	public String getUserMessage() {
		return userMessage;
	}

	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}

	public String getInternalMessage() {
		return internalMessage;
	}

	public void setInternalMessage(String internalMessage) {
		this.internalMessage = internalMessage;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMoreInfo() {
		return moreInfo;
	}

	public void setMoreInfo(String moreInfo) {
		this.moreInfo = moreInfo;
	}

	public int getHttpErrorCode() {
		return httpErrorCode;
	}

	public void setHttpErrorCode(int httpErrorCode) {
		this.httpErrorCode = httpErrorCode;
	}

}
