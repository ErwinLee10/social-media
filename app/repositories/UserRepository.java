package repositories;

import java.util.List;

import com.avaje.ebean.Model.Find;

import models.User;
import specification.ISpecification;

public class UserRepository extends BaseRepository<User, ISpecification<User>> implements IUserRepository{

	public UserRepository() {
		super(new Find<Long,User>(){});
	}

	@Override
	public User queryUnique(ISpecification<User> spec) {
		return spec.toExpressionList(find).findUnique();
	}

	@Override
	public List<User> query(ISpecification<User> spec) {
		return spec.toExpressionList(find).findList();
	}

}
