package org.kyll.base.common.sql;

import org.kyll.base.persistence.Entity;
import org.kyll.common.util.BeanUtil;
import org.kyll.common.util.StringUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Kyll
 * Date: 2017-08-07 15:38
 */
public class Sql {
	private StringBuilder sb = new StringBuilder();
	private Class entityClass;
	private List<String> columnNameList;
	private Map<String, String> fieldColumnMap;

	public Sql(Class<? extends Entity> entityClass) {
		this(entityClass, "t");
	}

	public Sql(Class<? extends Entity> entityClass, String alias) {
		this.entityClass = entityClass;

		this.columnNameList = new ArrayList<>();
		this.fieldColumnMap = new LinkedHashMap<>();
		for (String fieldName : BeanUtil.getFieldNameList(entityClass, Entity.class)) {
			String columnName = alias + "." + StringUtil.camelStringInsertUnderline(fieldName).toLowerCase();
			this.columnNameList.add(columnName);
			this.fieldColumnMap.put(fieldName, columnName);
		}
	}

	public Sql select(Column column) {
		sb.append(column.toSql());
		return this;
	}

	public Sql from() {
		if (sb.length() == 0) {
			sb.append(createSelectSql());
		}

		sb.append(createFromSql());
		return this;
	}

	public Sql where(Column column) {
		sb.append(createWhere()).append(column.toSql());
		return this;
	}

	public Column column(String fieldName) {
		return new Column(fieldColumnMap.get(fieldName));
	}

	private String table() {
		return StringUtil.camelStringInsertUnderline(entityClass.getSimpleName()).toLowerCase();
	}

	private String createSelectSql() {
		return String.format("select %s ", StringUtil.join(columnNameList, ", "));
	}

	private String createFromSql() {
		return String.format("from %s t ", table());
	}

	private String createWhere() {
		return "where ";
	}
}
