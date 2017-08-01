package org.kyll.springbootfirst.module.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * User: Kyll
 * Date: 2017-07-30 03:44
 */
@Slf4j
@Service
public class QueryService {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Map<String, Object>> query() {
		return jdbcTemplate.queryForList("select * from BCL_BIZ_NO t");
	}
}
