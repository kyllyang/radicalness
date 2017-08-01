package org.kyll.common.paginated;

/**
 * User: Kyll
 * Date: 2017-08-01 11:11
 */
public class Sort {
	private String property;
	private String direction;

	public Sort() {
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
}
