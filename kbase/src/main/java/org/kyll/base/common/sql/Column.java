package org.kyll.base.common.sql;

/**
 * User: Kyll
 * Date: 2017-08-07 16:48
 */
public class Column {
	private StringBuilder sb = new StringBuilder();
	private String columnName;

	public Column(String columnName) {
		this.columnName = columnName;
	}

	public Column eq(Column column) {
		sb.append(column.columnName);
		return this;
	}

	public Column eq(String value) {
		sb.append(columnName).append(" = '").append(value).append("'");
		return this;
	}

	public String toSql() {
		return sb.toString();
	}
}
