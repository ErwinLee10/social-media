package repositories;

import com.google.inject.ImplementedBy;

import models.FriendConnection;
import specifications.ISpecification;

@ImplementedBy(FriendRepository.class)
public interface IFriendRepository extends IRepository<FriendConnection, ISpecification<FriendConnection>> {

}
