package org.kyll.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

/**
 * User: Kyll
 * Date: 2017-07-31 09:41
 * 接口异常。
 * 通常使用在外部接口调用发生错误时抛出。
 */
@Slf4j
public class InterfaceException extends BaseException {
	public InterfaceException(String wellKindMessage) {
		this(wellKindMessage, null);
	}

	public InterfaceException(String wellKindMessage, Throwable cause) {
		this(null, wellKindMessage, cause);
	}

	public InterfaceException(Object data, String wellKindMessage, Throwable cause) {
		super(data, wellKindMessage, cause);
	}

	@Override
	protected Logger getLog() {
		return log;
	}
}
