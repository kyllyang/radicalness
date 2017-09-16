package org.kyll.tax.compiler.common;

/**
 * User: Kyll
 * Date: 2017-09-15 14:57
 */
public enum Oper {
	MKDIR("mkdir"),
	COPY("cp"),
	DELETE("rm");

	private String value;

	Oper(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
