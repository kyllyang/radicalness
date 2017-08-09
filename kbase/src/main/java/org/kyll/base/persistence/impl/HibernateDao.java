package org.kyll.base.persistence.impl;

import org.kyll.base.persistence.Condition;
import org.kyll.base.persistence.Entity;
import org.kyll.base.persistence.EntityDao;
import org.kyll.common.paginated.Dataset;
import org.kyll.common.paginated.Paginated;
import org.kyll.common.paginated.Sort;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * User: Kyll
 * Date: 2017-08-01 08:49
 */
public class HibernateDao<E extends Entity, P extends Serializable> extends SimpleHibernateDao implements EntityDao<E, P> {
	@Override
	public E get(P id) {
		return null;
	}

	@Override
	public E get(String fieldName, String fieldValue) {
		return null;
	}

	@Override
	public List<E> getAll() {
		return null;
	}

	@Override
	public List<E> find(String fieldName, String fieldValue) {
		return null;
	}

	@Override
	public List<E> find(String fieldName, String fieldValue, Sort... sorts) {
		return null;
	}

	@Override
	public List<E> find(String fieldName, String fieldValue, List<Sort> sortList) {
		return null;
	}

	@Override
	public List<E> find(String xql) {
		return null;
	}

	@Override
	public Dataset<E> find(String xql, Paginated paginated) {
		return null;
	}

	@Override
	public void insert(E entity) {

	}

	@Override
	public void update(E entity) {

	}

	@Override
	public void save(E entity) {

	}

	@Override
	public void batchInsert(E... entities) {

	}

	@Override
	public void batchInsert(Collection<E> entities) {

	}

	@Override
	public void batchUpdate(E... entities) {

	}

	@Override
	public void batchUpdate(Collection<E> entities) {

	}

	@Override
	public void delete(P... ids) {

	}

	@Override
	public void delete(E entity) {

	}

	@Override
	public void delete(Collection<E> entities) {

	}
}
