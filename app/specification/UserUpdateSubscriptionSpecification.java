package specification;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Model.Find;

import models.UserUpdateSubscription;

public class UserUpdateSubscriptionSpecification implements ISpecification<UserUpdateSubscription> {

	private String requestor;
	private String target;

	public UserUpdateSubscriptionSpecification(String requestor, String target) {
		this.requestor = requestor;
		this.target = target;
	}

	public String getRequestor() {
		return requestor;
	}

	public void setRequestor(String requestor) {
		this.requestor = requestor;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	@Override
	public ExpressionList<UserUpdateSubscription> toExpressionList(Find<Long, UserUpdateSubscription> find) {
		ExpressionList<UserUpdateSubscription> exp = find.where().eq("requestor.email", requestor).eq("target.email",
				target);
		return exp;
	}
}
