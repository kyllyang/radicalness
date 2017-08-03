package org.kyll.base.persistence.impl;

import org.kyll.base.persistence.Dao;

import java.util.List;
import java.util.Map;

/**
 * User: Kyll
 * Date: 2017-08-03 16:56
 */
public class SimpleHibernateDao implements Dao {
	@Override
	public Map<String, Object> queryForOne(String sql) {
		return null;
	}

	@Override
	public List<Map<String, Object>> queryForList(String sql) {
		return null;
	}

	@Override
	public void execute(String sql) {

	}
}
