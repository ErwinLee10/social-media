package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UpdateSubscriptionId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "requestor_id")
	private long requestorId;

	@Column(name = "target_id")
	private long targetId;

	public long getRequestorId() {
		return requestorId;
	}

	public void setRequestorId(long requestorId) {
		this.requestorId = requestorId;
	}

	public long getTargetId() {
		return targetId;
	}

	public void setTargetId(long targetId) {
		this.targetId = targetId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (requestorId ^ (requestorId >>> 32));
		result = prime * result + (int) (targetId ^ (targetId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UpdateSubscriptionId other = (UpdateSubscriptionId) obj;
		if (requestorId != other.requestorId)
			return false;
		if (targetId != other.targetId)
			return false;
		return true;
	}

}
