package repositories;

import java.util.List;

import com.avaje.ebean.Model;
import com.avaje.ebean.PagedList;

import specifications.ISpecification;

public interface IRepository <T extends Model, V extends ISpecification<T>>{

	default void add(T model){
		model.save();
	}
	
	default void update(T model){
		model.update();
	}
	
	default void remove(T model){
		model.delete();
	}
	
	List<T> getAll();
	
	PagedList<T> findPagedList(int pageIndex, int pageSize);
	
	T findById(Long id);
	
	T queryUnique(V spec);
	
	List<T> query(V spec);
}
