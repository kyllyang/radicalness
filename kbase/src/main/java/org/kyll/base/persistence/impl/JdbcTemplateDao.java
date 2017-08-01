package org.kyll.base.persistence.impl;

import org.kyll.base.persistence.Condition;
import org.kyll.base.persistence.Dao;
import org.kyll.base.persistence.Entity;
import org.kyll.common.Const;
import org.kyll.common.paginated.Dataset;
import org.kyll.common.paginated.Paginated;
import org.kyll.common.paginated.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * User: Kyll
 * Date: 2014-10-28 11:31
 */
public class JdbcTemplateDao<E extends Entity, P extends Serializable> implements Dao<E, P> {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public E get(P id) {
		return null;
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
	public List<E> find(Condition condition, Sort... sorts) {
		return null;
	}

	@Override
	public List<E> find(Condition condition, List<Sort> sortList) {
		return null;
	}

	@Override
	public Dataset<E> find(Condition condition, Paginated paginated) {
		return null;
	}

	public List<E> find(String sql) {
		return null;
	}

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

	@Override
	public Map<String, Object> queryForOne(String sql) {
		return jdbcTemplate.queryForMap(sql);
	}

	@Override
	public List<Map<String, Object>> queryForList(String sql) {
		return jdbcTemplate.queryForList(sql);
	}

	@Override
	public void execute(String sql) {
		jdbcTemplate.execute(sql);
	}

	private String createCountSql(String sql) {
		return null;
	}

	private String createPaginatedSql(String sql, Paginated paginated) {
		return null;
	}
}
