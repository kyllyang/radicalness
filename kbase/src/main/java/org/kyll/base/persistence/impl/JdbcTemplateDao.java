package org.kyll.base.persistence.impl;

import org.kyll.base.persistence.Dao;
import org.kyll.base.persistence.Entity;
import org.kyll.common.Const;
import org.kyll.common.paginated.Dataset;
import org.kyll.common.paginated.Paginated;
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
public abstract class JdbcTemplateDao<P extends Serializable, E extends Entity> implements Dao<P, E> {
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
	public void save(E entity) {

	}

	@Override
	public void update(E entity) {

	}

	@Override
	public void saveOrUpdate(E entity) {

	}

	@SuppressWarnings("unchecked")
	@Override
	public void batchSave(E... entities) {

	}

	@Override
	public void batchSave(Collection<E> entities) {

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
