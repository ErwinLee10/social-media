package repositories;

import com.google.inject.ImplementedBy;

import models.Friend;
import specification.ISpecification;

@ImplementedBy(FriendRepository.class)
public interface IFriendRepository extends IRepository<Friend, ISpecification<Friend>> {

}
