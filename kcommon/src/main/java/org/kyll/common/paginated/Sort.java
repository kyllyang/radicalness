package org.kyll.common.paginated;

/**
 * User: Kyll
 * Date: 2017-08-01 11:11
 */
public class Sort {
	private String property;
	private OrderBy orderBy;

	public Sort() {
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public OrderBy getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(OrderBy orderBy) {
		this.orderBy = orderBy;
	}

	public static Sort asc(String property) {
		Sort sort = new Sort();
		sort.setProperty(property);
		sort.setOrderBy(OrderBy.ASC);
		return sort;
	}

	public static Sort desc(String property) {
		Sort sort = new Sort();
		sort.setProperty(property);
		sort.setOrderBy(OrderBy.DESC);
		return sort;
	}
}
