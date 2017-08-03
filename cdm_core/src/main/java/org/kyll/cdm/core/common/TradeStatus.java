package org.kyll.cdm.core.common;

/**
 * User: Kyll
 * Date: 2017-08-03 15:13
 */
public enum TradeStatus {
	NORMAL("0000"),
	DESTRUCTION("0004"),
	CANCELLATION("0032");

	private String value;

	TradeStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
