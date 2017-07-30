package org.kyll.base.service.impl;

import org.kyll.base.persistence.Condition;
import org.kyll.base.persistence.Dao;
import org.kyll.base.persistence.Entity;
import org.kyll.base.service.DefaultService;
import org.kyll.common.paginated.Dataset;
import org.kyll.common.paginated.Paginated;

import java.io.Serializable;
import java.util.List;

/**
 * User: Kyll
 * Date: 2017-07-30 04:18
 */
public class JdbcTemplateService<P extends Serializable, E extends Entity, C extends Condition,  D extends Dao<P, E>> extends DefaultService<P, E, C, D> {
	@Override
	public List<E> find(C condition) {
		return null;
	}

	@Override
	public Dataset<E> find(C condition, Paginated paginated) {
		return null;
	}
}
