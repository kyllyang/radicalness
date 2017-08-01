package org.kyll.common;

/**
 * User: Kyll
 * Date: 2017-07-30 01:45
 */
public class Const {
	// 默认值
	public static final Integer DEFAULT_PAGINATED_STARTRECORD = 0;
	public static final Integer DEFAULT_PAGINATED_MAXRECORD = 20;
	public static final Integer DEFAULT_PAGINATED_DUEPAGE = 4;

	// 模式
	public static final String PATTERN_DATE = "yyyy-MM-dd";
	public static final String PATTERN_DATE_COMPACT = "yyyyMMdd";
	public static final String PATTERN_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
	public static final String PATTERN_DATE_TIME_COMPACT = "yyyyMMddHHmmss";

	// 固定值
	public static final String UNDERLINE = "_";
	public static final Integer INTEGER_ZERO = 0;
	public static String[] PURE_CHARS = new String[] {
			"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
			"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
			"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
	};

	// 专有名词
	public static final String PN_DATE = "日期";

	// 异常信息
	public static final String EC_MSG_PARSE = "%s解析错误 参数[%s] 格式[%s]";
	public static final String EC_MSG_INSTANTIATION = "对象实例化错误 参数[%s]";
	public static final String EC_MSG_ILLEGALACCESS_METHOD = "非法的方法访问 参数[class = %s, method = %s]";
	public static final String EC_MSG_ILLEGALACCESS_CLASS = "非法的类访问 参数[%s]";
	public static final String EC_MSG_CLASSNOTFOUND = "无法找到类 参数[%s]";
	public static final String EC_MSG_NOSUCHFIELD = "无法找到类中的属性 参数[class = %s, field = %s]";
	public static final String EC_MSG_INVOCATIONTARGET = "调用目标错误 参数[class = %s, method = %s]";
}
