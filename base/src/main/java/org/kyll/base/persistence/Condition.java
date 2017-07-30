package org.kyll.base.persistence;

import java.io.Serializable;

/**
 * User: Kyll
 * Date: 2017-07-30 03:51
 * @param <P> 主键
 */
public abstract class Condition<P extends Serializable> {
	private P id;

	public P getId() {
		return id;
	}

	public void setId(P id) {
		this.id = id;
	}
}
