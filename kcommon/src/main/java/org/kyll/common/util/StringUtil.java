package org.kyll.common.util;

import org.kyll.common.Const;
import strman.Strman;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * User: Kyll
 * Date: 2017-07-31 11:36
 * 字符串工具类
 */
public class StringUtil {
	/**
	 * 如果字符串为 null， 则返回 ""
	 * @param str 字符串
	 * @return 非 null 字符串
	 */
	public static String toEmptyIf(String str) {
		return isBlank(str) ? Const.STRING_EMPTY : str;
	}

	public static String toEmptyIf(Object value) {
		return value == null ? Const.STRING_EMPTY : value.toString();
	}

	public static String trim(String str) {
		return isBlank(str) ? str : Strman.leftTrim(Strman.rightTrim(str));
	}

	public static boolean isBlank(String str) {
		return Strman.isBlank(str);
	}

	public static boolean isNotBlank(String str) {
		return !Strman.isBlank(str);
	}

	public static String getFirstChar(String str) {
		return isBlank(str) ? str : String.valueOf(str.charAt(0));
	}

	public static String join(List<?> list, String separator) {
		return join(list.toArray(new Object[list.size()]), separator);
	}

	public static String join(Object[] objects, String separator) {
		String[] strs = new String[objects.length];
		for (int i = 0; i < objects.length; i++) {
			strs[i] = objects[i].toString();
		}
		return Strman.join(strs, separator);
	}

	public static String[] split(String str, String... delimiteds) {
		char c = delimiteds[0].charAt(0);

		int pos = str.indexOf(c);
		if (pos < 0) {
			return new String[]{str};
		}
		List<String> strList = new ArrayList<>();
		if (pos == 0) {
			strList.add("");
		} else {
			strList.add(str.substring(0, pos));
		}
		while (pos >= 0) {
			int end = str.indexOf(c, pos + 1);
			if (end < 0) {
				end = str.length();
			}
			if (end - pos == 1) {
				strList.add("");
			} else {
				strList.add(str.substring(pos + 1, end));
			}
			pos = str.indexOf(c, pos + 1);
		}
		return strList.toArray(new String[strList.size()]);
	}

	/**
	 * 将驼峰字符串中大写字母和数字前插入下划线
	 * @param camel 驼峰字符串
	 * @return 字符串
	 */
	public static String camelStringInsertUnderline(String camel) {
		List<Integer> indexList = new ArrayList<Integer>();
		for (int i = 0, length = camel.length(); i < length; i++) {
			char c = camel.charAt(i);

			int size = indexList.size();
			if (size == 0) {
				indexList.add(i);
			} else {
				char pc = camel.charAt(i - 1);
				if (Character.isDigit(pc) == Character.isDigit(c)) {
					if (Character.isLowerCase(pc) && Character.isUpperCase(c)) {
						indexList.add(i);
					}
				} else {
					indexList.add(i);
				}
			}
		}

		StringBuilder result = new StringBuilder();
		for (int i = 0, size = indexList.size() - 1; i < size; i++) {
			result.append(camel.substring(indexList.get(i), indexList.get(i + 1))).append("_");
		}
		result.append(camel.substring(indexList.get(indexList.size() - 1)));

		return result.toString();
	}

	/**
	 * 判断是否以字符串开头
	 * @param str 原字符串
	 * @param strs 比较字符串
	 * @return 如果以其中任意一个比较字符串开头， 则返回 true， 否则 flase
	 */
	public static boolean startsWith(String str, String... strs) {
		boolean result = false;
		for (String s : strs) {
			if (str.startsWith(s)) {
				result = true;
				break;
			}
		}
		return result;
	}

	/**
	 * 生成 UUID 字符串，去掉“-”
	 * @return 字符串
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 随机生字符串，包含[0-9][a-z][A-Z]
	 * @param length 长度
	 * @return 字符串
	 */
	public static String randomString(int length) {
		StringBuilder shortBuffer = new StringBuilder();
		String uuid = uuid();
		for (int i = 0; i < length; i++) {
			shortBuffer.append(Const.PURE_CHARS[Integer.parseInt(uuid.substring(i * 4, i * 4 + 4), 16) % 0x3E]);
		}
		return shortBuffer.toString();
	}
}
