package repositories;

import com.google.inject.ImplementedBy;

import models.FriendConnection;
import specifications.ISpecification;

@ImplementedBy(FriendConnectionRepository.class)
public interface IFriendConnectionRepository extends IRepository<FriendConnection, ISpecification<FriendConnection>> {

}
