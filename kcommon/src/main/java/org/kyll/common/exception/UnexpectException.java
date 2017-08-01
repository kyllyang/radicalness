package org.kyll.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

/**
 * User: Kyll
 * Date: 2017-07-31 09:43
 * 不期望的异常。
 * 通常使用在声明捕获的异常之外，开发者没有考虑到的情况抛出。
 */
@Slf4j
public class UnexpectException extends BaseException {
	public UnexpectException(String wellKindMessage) {
		this(wellKindMessage, null);
	}

	public UnexpectException(String wellKindMessage, Throwable cause) {
		this(null, wellKindMessage, cause);
	}

	public UnexpectException(Object data, String wellKindMessage, Throwable cause) {
		super(data, wellKindMessage, cause);
	}

	@Override
	protected Logger getLog() {
		return log;
	}
}
