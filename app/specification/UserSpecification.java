package specification;

import org.apache.commons.lang3.StringUtils;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Model.Find;

import models.User;

public class UserSpecification implements ISpecification<User> {

	private String email;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public ExpressionList<User> toExpressionList(Find<Long, User> find) {
		ExpressionList<User> exp = find.where();
		if(!StringUtils.isEmpty(email)) {
			exp = exp.eq("email", email);
		}	
		return exp;
	}
}
