package repositories;

import com.google.inject.ImplementedBy;

import models.UserUpdateSubscription;
import specification.ISpecification;

@ImplementedBy(UserUpdateSubscriptionRepository.class)
public interface IUserUpdateSubscriptionRepository
		extends IRepository<UserUpdateSubscription, ISpecification<UserUpdateSubscription>> {

}
