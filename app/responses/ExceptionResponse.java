package responses;

public class ExceptionResponse {

	private String userMessage;

	private String internalMessage;

	private int code;

	private String moreInfo = "";

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

}
