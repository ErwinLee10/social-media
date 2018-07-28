package repositories;

import com.google.inject.ImplementedBy;

import models.User;
import specifications.ISpecification;

@ImplementedBy(UserRepository.class)
public interface IUserRepository extends IRepository<User, ISpecification<User>> {

}
