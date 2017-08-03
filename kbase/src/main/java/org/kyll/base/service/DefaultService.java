package org.kyll.base.service;

import org.kyll.base.persistence.Condition;
import org.kyll.base.persistence.Entity;
import org.kyll.base.persistence.EntityDao;
import org.kyll.common.paginated.Dataset;
import org.kyll.common.paginated.Paginated;
import org.kyll.common.paginated.Sort;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * User: Kyll
 * Date: 2017-07-30 03:12
 */
public abstract class DefaultService<E extends Entity, P extends Serializable, D extends EntityDao<E, P>> implements Service<E, P> {
	@Autowired
	private D dao;

	@Override
	public E get(P id) {
		return dao.get(id);
	}

	@Override
	public List<E> find(Condition condition, Sort... sorts) {
		return dao.find(condition, sorts);
	}

	@Override
	public List<E> find(Condition condition, List<Sort> sortList) {
		return dao.find(condition, sortList);
	}

	@Override
	public Dataset<E> find(Condition condition, Paginated paginated) {
		return dao.find(condition, paginated);
	}

	@Override
	public void save(E entity) {
		dao.save(entity);
	}

	@SafeVarargs
	@Override
	public final void delete(P... ids) {
		dao.delete(ids);
	}

	protected D getDao() {
		return this.dao;
	}
}
