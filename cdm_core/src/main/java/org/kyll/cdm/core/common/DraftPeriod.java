package org.kyll.cdm.core.common;

/**
 * User: Kyll
 * Date: 2017-08-03 14:30
 */
public enum DraftPeriod {
	BIRTH("0000"),
	ALIVE("0001"),
	DIE_YOUNG("0002"),
	DEAD("0003");

	private String value;

	DraftPeriod(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
