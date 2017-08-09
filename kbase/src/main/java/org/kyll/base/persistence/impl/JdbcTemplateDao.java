package org.kyll.base.persistence.impl;

import org.kyll.base.common.sql.Sql;
import org.kyll.base.persistence.Entity;
import org.kyll.base.persistence.EntityDao;
import org.kyll.common.Const;
import org.kyll.common.paginated.Dataset;
import org.kyll.common.paginated.Paginated;
import org.kyll.common.paginated.Sort;
import org.kyll.common.util.BeanUtil;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * User: Kyll
 * Date: 2014-10-28 11:31
 */
public abstract class JdbcTemplateDao<E extends Entity, P extends Serializable> extends SimpleJdbcTemplateDao implements EntityDao<E, P> {
	private Class<E> entityClass;

	@SuppressWarnings("unchecked")
	@PostConstruct
	private void init() {
		entityClass = BeanUtil.getGenericEntityClass(this);
	}

	@Override
	public E get(P id) {
		Sql sql = new Sql(entityClass);
		sql.from().where(sql.column("id").eq(id)).toSql();
		return getJdbcTemplate().queryForObject("", entityClass);
	}

	@Override
	public List<E> getAll() {
		return null;
	}

	public int count(String sql) {
		String countSql = this.createCountSql(sql);
		return 0;
	}

	@Override
	public E get(String fieldName, String fieldValue) {
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
	public List<E> find(String sql) {
		return null;
	}

	@Override
	public Dataset<E> find(String sql, Paginated paginated) {
		int totalRecord = this.count(sql);
		if (totalRecord > 0) {
			return Dataset.create(paginated, totalRecord, this.find(this.createPaginatedSql(sql, paginated)));
		}
		return Dataset.create(paginated, Const.INTEGER_ZERO, new ArrayList<>());
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

	@SuppressWarnings("unchecked")
	@Override
	public void batchInsert(E... entities) {

	}

	@Override
	public void batchInsert(Collection<E> entities) {

	}

	@SuppressWarnings("unchecked")
	@Override
	public void batchUpdate(E... entities) {

	}

	@Override
	public void batchUpdate(Collection<E> entities) {

	}

	@SuppressWarnings("unchecked")
	@Override
	public void delete(P... ids) {

	}

	@Override
	public void delete(E entity) {

	}

	@Override
	public void delete(Collection<E> entities) {

	}

	private String createCountSql(String sql) {
		return null;
	}

	private String createPaginatedSql(String sql, Paginated paginated) {
		return null;
	}
}
