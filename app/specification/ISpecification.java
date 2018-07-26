package specification;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Find;

public interface ISpecification<T extends Model> {
	ExpressionList<T> toExpressionList(Find<Long, T> find);
}
