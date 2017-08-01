package org.kyll.base.service;

import org.kyll.base.persistence.Condition;
import org.kyll.base.persistence.Entity;
import org.kyll.common.paginated.Dataset;
import org.kyll.common.paginated.Paginated;
import org.kyll.common.paginated.Sort;

import java.io.Serializable;
import java.util.List;

/**
 * User: Kyll
 * Date: 2014-11-08 13:01
 */
public interface Service<E extends Entity, P extends Serializable> {
	E get(P id);

	List<E> find(Condition condition, Sort... sorts);

	List<E> find(Condition condition, List<Sort> sortList);

	Dataset<E> find(Condition condition, Paginated paginated);

	void save(E entity);

	@SuppressWarnings("unchecked")
	void delete(P... ids);
}
