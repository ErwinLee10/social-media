package specifications;

import org.apache.commons.lang3.StringUtils;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Model.Find;

import models.FriendConnection;

public class FriendConnectionSpecification implements ISpecification<FriendConnection> {

	private String userEmail;
	private String friendEmail;

	@Override
	public ExpressionList<FriendConnection> toExpressionList(Find<Long, FriendConnection> find) {
		ExpressionList<FriendConnection> exp = find.where();
		if(!StringUtils.isEmpty(userEmail)) {
			exp.addAll(find.where().eq("user.email", userEmail));
		}
		if(!StringUtils.isEmpty(friendEmail)) {
			exp.addAll(find.where().eq("friend.email", friendEmail));
		}
		return exp;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getFriendEmail() {
		return friendEmail;
	}

	public void setFriendEmail(String friendEmail) {
		this.friendEmail = friendEmail;
	}

}
