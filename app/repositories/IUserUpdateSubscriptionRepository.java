package repositories;

import com.google.inject.ImplementedBy;

import models.UserUpdateSubscription;
import specifications.ISpecification;

@ImplementedBy(UserUpdateSubscriptionRepository.class)
public interface IUserUpdateSubscriptionRepository
		extends IRepository<UserUpdateSubscription, ISpecification<UserUpdateSubscription>> {

}
