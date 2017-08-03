package org.kyll.common.util;

import org.kyll.common.Const;

/**
 * User: Kyll
 * Date: 2017-08-03 20:20
 */
public class SqlUtil {
	public static String toVarchar2(String str) {
		return str == null ? null : (str.length() <= Const.INTEGER_FOUR_THOUSAND ? str : str.substring(Const.INTEGER_ZERO, Const.INTEGER_FOUR_THOUSAND));
	}
}
