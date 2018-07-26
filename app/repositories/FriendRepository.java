package repositories;

import java.util.List;

import com.avaje.ebean.Model.Find;

import models.Friend;
import specification.ISpecification;

public class FriendRepository extends BaseRepository<Friend, ISpecification<Friend>> implements IFriendRepository{

	public FriendRepository() {
		super(new Find<Long,Friend>(){});
	}

	@Override
	public Friend queryUnique(ISpecification<Friend> spec) {
		return spec.toExpressionList(find).findUnique();
	}

	@Override
	public List<Friend> query(ISpecification<Friend> spec) {
		return spec.toExpressionList(find).findList();
	}

}
