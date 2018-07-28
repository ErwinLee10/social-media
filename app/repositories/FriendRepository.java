package repositories;

import java.util.List;

import com.avaje.ebean.Model.Find;

import models.FriendConnection;
import specifications.ISpecification;

public class FriendRepository extends BaseRepository<FriendConnection, ISpecification<FriendConnection>>
		implements IFriendRepository {

	public FriendRepository() {
		super(new Find<Long, FriendConnection>() {
		});
	}

	@Override
	public FriendConnection queryUnique(ISpecification<FriendConnection> spec) {
		return spec.toExpressionList(find).findUnique();
	}

	@Override
	public List<FriendConnection> query(ISpecification<FriendConnection> spec) {
		return spec.toExpressionList(find).findList();
	}

}
