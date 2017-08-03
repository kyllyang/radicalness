package org.kyll.cdm.core.common;

/**
 * User: Kyll
 * Date: 2017-08-03 20:21
 */
public enum CdgResult {
	SUCCESS("1"),
	FAILURE("0");

	private String value;

	CdgResult(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
