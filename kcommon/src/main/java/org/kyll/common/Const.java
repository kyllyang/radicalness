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
	public static final String DEFAULT_PARTICIPATOR_PBOC = "PBOC";
	public static final String ICDGSRVBS_RESULT_FAILURE = "PBOC";

	// 模式
	public static final String PATTERN_DATE = "yyyy-MM-dd";
	public static final String PATTERN_DATE_COMPACT = "yyyyMMdd";
	public static final String PATTERN_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
	public static final String PATTERN_DATE_TIME_COMPACT = "yyyyMMddHHmmss";

	// 固定值
	public static final String UNDERLINE = "_";
	public static final Integer INTEGER_ZERO = 0;
	public static final Integer INTEGER_FOUR_THOUSAND = 4000;
	public static String[] PURE_CHARS = new String[] {
			"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
			"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
			"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
	};

	// 符号
	public static final String SYMBOL_LINE_SEPARATOR = System.getProperty("line.separator");
	public static final String SYMBOL_PERCENT = "%";
	public static final String SYMBOL_DOT = ".";
	public static final String SYMBOL_BRACKET_LEFT = "(";
	public static final String SYMBOL_BRACKET_RIGHT = ")";
	public static final String SYMBOL_COLON = ":";

	// 类名、属性名
	public static final String CLASS_FIELD_CODE = "code";
	public static final String CLASS_FIELD_SORT = "sort";

	// 专有名词
	public static final String PN_TRADE_REGISTER = "出票信息登记";
	public static final String PN_TRADE_ENDORSEMENT = "背书";
	public static final String PN_TRADE_DISCOUNT = "贴现";
	public static final String PN_TRADE_REPURCHASED_DISCOUNT = "回购式贴现赎回";
	public static final String PN_TRADE_REDISCOUNT_WITH_COMMERCIAL_BANK = "转贴现";
	public static final String PN_TRADE_REPURCHASED_REDISCOUNT_WITH_COMMERCIAL_BANK = "回购式转贴现赎回";
	public static final String PN_TRADE_REDISCOUNT_WITH_CENTRAL_BANK = "再贴现";
	public static final String PN_TRADE_REPURCHASED_REDISCOUNT_WITH_CENTRAL_BANK = "回购式再贴现赎回";
	public static final String PN_TRADE_GUARANTEE = "保证";
	public static final String PN_TRADE_COLLATERALIZATION = "质押";
	public static final String PN_TRADE_REPURCHASED_COLLATERALIZATION = "质押解除";
	public static final String PN_TRADE_PRESENTATION = "提示付款";
	public static final String PN_TRADE_OVERDUE_PRESENTATION = "逾期提示付款";
	public static final String PN_TRADE_RECOURSE_NOTIFICATION = "追索通知";
	public static final String PN_TRADE_RECOURSE_AGREEMENT = "追索同意清偿";
	public static final String PN_TRADE_CENTRAL_BANK_SELLING_DRAFTS = "央行卖出商业汇票";
	public static final String PN_DATE = "日期";
	public static final String PN_DATA = "数据";
	public static final String PN_MSG_TYPE = "报文类型";

	// 日志信息
	public static final String LOG_SAVE_ECDS_MSG = "保存报文[%s]";
	public static final String LOG_SAVE_TRADE_DETAIL = "保存交易明细[%s]";
	public static final String LOG_SAVE_ECDS_MSG_RESULT = "保存报文结果[%s]";
	public static final String LOG_SAVE_DRAFT = "保存票据[%s]";
	public static final String LOG_SAVE_TRADE = "保存交易[%s]";
	public static final String LOG_UPDATE_ECDS_MSG = "保存报文[%s]";
	public static final String LOG_VALIDATE_TRADE = "验证交易";

	public static final String LOG_ECDS_TRADE_START = "交易[%s]开始";
	public static final String LOG_ECDS_TRADE_END = "交易[%s]结束";
	public static final String LOG_ECDS_MSG_SEND_START = "发送报文开始[tradeId: %s, ecdsMsgType: %s]";
	public static final String LOG_ECDS_MSG_SEND_END = "发送报文结束";
	public static final String LOG_ECDS_MSG_SEND_ERROR = "发送报文错误";
	// 异常信息
	public static final String EC_MSG_PARSE = "%s解析错误 参数[%s] 格式[%s]";
	public static final String EC_MSG_INSTANTIATION = "对象实例化错误 参数[%s]";
	public static final String EC_MSG_ILLEGALACCESS_METHOD = "非法的方法访问 参数[class = %s, method = %s]";
	public static final String EC_MSG_ILLEGALACCESS_CLASS = "非法的类访问 参数[%s]";
	public static final String EC_MSG_CLASSNOTFOUND = "无法找到类 参数[%s]";
	public static final String EC_MSG_NOSUCHFIELD = "无法找到类中的属性 参数[class = %s, field = %s]";
	public static final String EC_MSG_INVOCATIONTARGET = "调用目标错误 参数[class = %s, method = %s]";
	public static final String EC_MSG_STATE_NOEXIST = "%s状态异常 %s不存在 参数[%s] 请参照[%s对应表]";
	public static final String EC_MSG_UNSUPPORTED = "%s不支持";
	public static final String EC_STARTER = "at ";
}
