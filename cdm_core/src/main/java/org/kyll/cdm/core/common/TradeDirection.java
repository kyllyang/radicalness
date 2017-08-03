package org.kyll.cdm.core.common;

/**
 * User: Kyll
 * Date: 2017-08-03 15:09
 */
public enum TradeDirection {
	SEND("0000"),
	RECEIVE("0001");

	private String value;

	TradeDirection(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
