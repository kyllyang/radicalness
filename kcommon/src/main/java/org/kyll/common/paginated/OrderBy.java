package org.kyll.common.paginated;

/**
 * User: Kyll
 * Date: 2017-08-03 14:07
 */
public enum OrderBy {
	ASC("asc"),
	DESC("desc");

	private String value;

	OrderBy(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
