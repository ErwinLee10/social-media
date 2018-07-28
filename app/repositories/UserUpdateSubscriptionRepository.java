package repositories;

import java.util.List;

import com.avaje.ebean.Model.Find;

import models.UserUpdateSubscription;
import specification.ISpecification;

public class UserUpdateSubscriptionRepository
		extends BaseRepository<UserUpdateSubscription, ISpecification<UserUpdateSubscription>>
		implements IUserUpdateSubscriptionRepository {

	public UserUpdateSubscriptionRepository() {
		super(new Find<Long,UserUpdateSubscription>(){});
	}

	@Override
	public UserUpdateSubscription queryUnique(ISpecification<UserUpdateSubscription> spec) {
		return spec.toExpressionList(find).findUnique();
	}

	@Override
	public List<UserUpdateSubscription> query(ISpecification<UserUpdateSubscription> spec) {
		return spec.toExpressionList(find).findList();
	}

}
