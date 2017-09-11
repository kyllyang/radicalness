package org.kyll.common.util;

import org.kyll.common.Const;
import org.kyll.common.paginated.Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * User: Kyll
 * Date: 2017-08-03 20:30
 */
public class ValueUtil {
	public static String toVarchar2(String str) {
		return str == null ? null : (str.length() <= Const.INTEGER_FOUR_THOUSAND ? str : str.substring(Const.INTEGER_ZERO, Const.INTEGER_FOUR_THOUSAND));
	}

	public static String toString(Exception e) {
		StringBuilder sb = new StringBuilder(e.getMessage() + "\r\n");
		sb.append(e.getCause()).append("\r\n");
		for (StackTraceElement ste : e.getStackTrace()) {
			sb.append("at ").append(ste.getClassName()).append(".").append(ste.getMethodName()).append("(").append(ste.getFileName()).append(":").append(ste.getLineNumber()).append(")\r\n");
		}
		return sb.toString();
	}

	public static org.springframework.data.domain.Sort toSpringDataDomainSort(Sort... sorts) {
		return toSpringDataDomainSort(Arrays.asList(sorts));
	}

	public static org.springframework.data.domain.Sort toSpringDataDomainSort(List<Sort> sortList) {
		List<String> propertyList = new ArrayList<>();
		sortList.forEach(sort -> propertyList.add(sort.getProperty()));
		return new org.springframework.data.domain.Sort(org.springframework.data.domain.Sort.Direction.fromString(sortList.get(0).getOrderBy().getValue()), propertyList);
	}
}
