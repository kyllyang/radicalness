package org.kyll.base.service;

import org.kyll.base.persistence.Condition;
import org.kyll.base.persistence.Dao;
import org.kyll.base.persistence.Entity;
import org.kyll.common.paginated.Dataset;
import org.kyll.common.paginated.Paginated;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * User: Kyll
 * Date: 2017-07-30 03:12
 */
public abstract class DefaultService<P extends Serializable, E extends Entity, C extends Condition,  D extends Dao<P, E>> implements Service<P, E, C> {
	@Autowired
	protected D dao;

	@Override
	public E get(P id) {
		return dao.get(id);
	}

	@Override
	public abstract List<E> find(C condition);

	@Override
	public abstract Dataset<E> find(C condition, Paginated paginated);

	@Override
	public void save(E entity) {
		dao.saveOrUpdate(entity);
	}

	@SafeVarargs
	@Override
	public final void delete(P... ids) {
		dao.delete(ids);
	}
}
