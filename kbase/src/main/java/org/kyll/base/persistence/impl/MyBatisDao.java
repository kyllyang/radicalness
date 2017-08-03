package org.kyll.base.persistence.impl;

import org.kyll.base.persistence.Entity;
import org.kyll.base.persistence.EntityDao;

import java.io.Serializable;

/**
 * User: Kyll
 * Date: 2017-08-01 08:50
 */
public interface MyBatisDao<E extends Entity, P extends Serializable> extends EntityDao<E, P> {
}
