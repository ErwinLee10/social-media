package specifications;

import java.util.List;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Model.Find;
import com.avaje.ebean.RawSql;
import com.avaje.ebean.RawSqlBuilder;

import models.User;

public class CommonFriendSpecification implements ISpecification<User> {

	private List<String> emailList;

	public List<String> getEmail() {
		return emailList;
	}

	public void setEmail(List<String> emailList) {
		this.emailList = emailList;
	}

	@Override
	public ExpressionList<User> toExpressionList(Find<Long, User> find) {
		String query = String.format(
				"select u.id, u.email from friend f join user u on f.friend_id = u.id "
						+ "where f.user_id = (select id from user where email = '%s')"
						+ " and f.friend_id in (select friend_id from friend where user_id = (select id from user where email = '%s'))",
				emailList.get(0), emailList.get(1));

		RawSql rawSql = RawSqlBuilder.parse(query).columnMapping("u.id", "id").columnMapping("u.email", "email")
				.create();
		ExpressionList<User> exp = find.setRawSql(rawSql).where();
		return exp;
	}
}
