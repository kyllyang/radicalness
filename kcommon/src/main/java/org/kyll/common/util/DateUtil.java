package org.kyll.common.util;

import org.kyll.common.Const;
import org.kyll.common.exception.SystemException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: Kyll
 * Date: 2017-07-31 11:36
 * 日期工具类。
 * 传递 null 值，返回 null 值
 */
public class DateUtil {
	public static Date now() {
		return new Date();
	}

	public static Date parseDate(String str) {
		return parse(str, Const.PATTERN_DATE);
	}

	public static Date parseDateCompact(String str) {
		return parse(str, Const.PATTERN_DATE_COMPACT);
	}

	public static Date parseDatetime(String str) {
		return parse(str, Const.PATTERN_DATE_TIME);
	}

	public static Date parseDatetimeCompact(String str) {
		return parse(str, Const.PATTERN_DATE_TIME_COMPACT);
	}

	public static Date parse(String str, String pattern) {
		Date date = null;
		if (StringUtil.isNotBlank(str)) {
			try {
				date = new SimpleDateFormat(pattern).parse(str);
			} catch (ParseException e) {
				throw new RuntimeException(new SystemException(String.format(Const.EC_MSG_PARSE, Const.PN_DATE, str, pattern), e));
			}
		}
		return date;
	}

	public static String formatDate(Date date) {
		return format(date, Const.PATTERN_DATE);
	}

	public static String formatDateCompact(Date date) {
		return format(date, Const.PATTERN_DATE_COMPACT);
	}

	public static String formatDatetime(Date date) {
		return format(date, Const.PATTERN_DATE_TIME);
	}

	public static String formatDatetimeCompact(Date date) {
		return format(date, Const.PATTERN_DATE_TIME_COMPACT);
	}

	public static String format(Date date, String pattern) {
		return date == null ? null : new SimpleDateFormat(pattern).format(date);
	}

	/**
	 * 去掉日期中的时分秒
	 * @param date 日期
	 * @return 不带时分秒的日期
	 */
	public static Date removeHMS(Date date) {
		return parseDate(formatDate(date));
	}
}
