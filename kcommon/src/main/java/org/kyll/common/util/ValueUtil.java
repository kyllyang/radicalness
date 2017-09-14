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

	public static String toSqlLike(String str) {
		return Const.SYMBOL_PERCENT + str + Const.SYMBOL_PERCENT;
	}

	public static String toString(Exception e) {
		StringBuilder sb = new StringBuilder(e.getMessage() + Const.SYMBOL_LINE_SEPARATOR);
		sb.append(e.getCause()).append(Const.SYMBOL_LINE_SEPARATOR);
		for (StackTraceElement ste : e.getStackTrace()) {
			sb.append(Const.EC_STARTER).append(ste.getClassName()).append(Const.SYMBOL_DOT).append(ste.getMethodName()).append(Const.SYMBOL_BRACKET_LEFT).append(ste.getFileName()).append(Const.SYMBOL_COLON).append(ste.getLineNumber()).append(Const.SYMBOL_BRACKET_RIGHT).append(Const.SYMBOL_LINE_SEPARATOR);
		}
		return sb.toString();
	}

	public static org.springframework.data.domain.Sort toSpringDataDomainSort(Sort... sorts) {
		return toSpringDataDomainSort(Arrays.asList(sorts));
	}

	public static org.springframework.data.domain.Sort toSpringDataDomainSort(List<Sort> sortList) {
		if (sortList == null || sortList.isEmpty()) {
			return null;
		}

		List<String> propertyList = new ArrayList<>();
		sortList.forEach(sort -> propertyList.add(sort.getProperty()));
		return new org.springframework.data.domain.Sort(org.springframework.data.domain.Sort.Direction.fromString(sortList.get(Const.INTEGER_ZERO).getOrderBy().getValue()), propertyList);
	}
}
