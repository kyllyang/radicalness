package org.kyll.base.persistence;

import java.io.Serializable;

/**
 * User: Kyll
 * Date: 2017-07-30 03:06
 * @param <P> 主键
 */
public abstract class Entity<P> implements Serializable {
	private P id;

	public P getId() {
		return id;
	}

	public void setId(P id) {
		this.id = id;
	}
}
