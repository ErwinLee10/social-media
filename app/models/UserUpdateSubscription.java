package models;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class UserUpdateSubscription extends BaseModel {

	@EmbeddedId
	private UpdateSubscriptionId id;

	@ManyToOne
	@JoinColumn(name = "requestor_id")
	private User requestor;

	@ManyToOne
	@JoinColumn(name = "target_id")
	private User target;

	@Column
	SubscriptionStatus subscriptionStatus;

	public UpdateSubscriptionId getId() {
		return id;
	}

	public void setId(UpdateSubscriptionId id) {
		this.id = id;
	}

	public SubscriptionStatus getSubscriptionStatus() {
		return subscriptionStatus;
	}

	public void setSubscriptionStatus(SubscriptionStatus subscriptionStatus) {
		this.subscriptionStatus = subscriptionStatus;
	}

	public User getRequestor() {
		return requestor;
	}

	public void setRequestor(User requestor) {
		this.requestor = requestor;
	}

	public User getTarget() {
		return target;
	}

	public void setTarget(User target) {
		this.target = target;
	}

}
