package responses;

import java.util.Set;

public class UserCanReceiveUpdateResponse extends SuccessResponse {

	Set<String> recipients;

	public Set<String> getRecipients() {
		return recipients;
	}

	public void setRecipients(Set<String> recipients) {
		this.recipients = recipients;
	}

}
