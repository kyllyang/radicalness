package org.kyll.base.service;

import org.kyll.base.persistence.Condition;
import org.kyll.base.persistence.Entity;
import org.kyll.common.paginated.Dataset;
import org.kyll.common.paginated.Paginated;

import java.io.Serializable;
import java.util.List;

/**
 * User: Kyll
 * Date: 2014-11-08 13:01
 */
public interface Service<P extends Serializable, E extends Entity, C extends Condition> {
	E get(P id);

	List<E> find(C condition);

	Dataset<E> find(C condition, Paginated paginated);

	void save(E entity);

	@SuppressWarnings("unchecked")
	void delete(P... ids);
}
