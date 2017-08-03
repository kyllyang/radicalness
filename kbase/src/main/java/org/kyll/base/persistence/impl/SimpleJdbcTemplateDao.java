package org.kyll.base.persistence.impl;

import org.kyll.base.persistence.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * User: Kyll
 * Date: 2017-08-03 16:40
 */
public abstract class SimpleJdbcTemplateDao implements Dao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

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

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
}
