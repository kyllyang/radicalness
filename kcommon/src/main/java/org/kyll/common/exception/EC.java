package org.kyll.common.exception;

/**
 * User: Kyll
 * Date: 2017-07-30 04:13
 */
public enum EC {
	SYSTEM, // 不应该出现的异常。出现此异常代表程序错误。
	DATABASE, // 数据库异常
	NETWORK // 网络异常
	;
}
