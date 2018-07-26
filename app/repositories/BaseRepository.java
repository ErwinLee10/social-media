package repositories;

import java.util.List;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Find;

import specification.ISpecification;

import com.avaje.ebean.PagedList;


public abstract class BaseRepository<T extends Model, V extends ISpecification<T>> implements IRepository<T, V> {

	protected  Find<Long, T> find;
	
	public BaseRepository(Find<Long, T> find){
		this.find = find;
	}
	
	@Override
	public List<T> getAll() {
		return find.all();
	}
	
	@Override
	public PagedList<T> findPagedList(int pageIndex, int pageSize){
		return find.findPagedList(pageIndex, pageSize);
	}

	@Override
	public T findById(Long id) {
		return find.byId(id);
	}
}
