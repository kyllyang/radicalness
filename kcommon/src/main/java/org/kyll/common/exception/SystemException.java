package org.kyll.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

/**
 * User: Kyll
 * Date: 2017-07-31 09:30
 * 系统异常。
 * 通常使用在应对JAVA抛出的异常封装。
 */
@Slf4j
public class SystemException extends BaseException {
	public SystemException(String wellKindMessage) {
		this(wellKindMessage, null);
	}

	public SystemException(String wellKindMessage, Throwable cause) {
		this(null, wellKindMessage, cause);
	}

	public SystemException(Object data, String wellKindMessage, Throwable cause) {
		super(data, wellKindMessage, cause);
	}

	@Override
	protected Logger getLog() {
		return log;
	}
}
