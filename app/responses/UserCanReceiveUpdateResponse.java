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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((recipients == null) ? 0 : recipients.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserCanReceiveUpdateResponse other = (UserCanReceiveUpdateResponse) obj;
		if (recipients == null) {
			if (other.recipients != null)
				return false;
		} else if (!recipients.equals(other.recipients))
			return false;
		return true;
	}
}
