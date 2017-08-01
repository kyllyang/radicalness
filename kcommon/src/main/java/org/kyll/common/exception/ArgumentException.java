package org.kyll.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

/**
 * User: Kyll
 * Date: 2017-07-31 09:42
 * 参数异常。
 * 通常使用在数据验证失败时抛出。
 */
@Slf4j
public class ArgumentException extends BaseException {
	public ArgumentException(String wellKindMessage) {
		this(wellKindMessage, null);
	}

	public ArgumentException(String wellKindMessage, Throwable cause) {
		this(null, wellKindMessage, cause);
	}

	public ArgumentException(Object data, String wellKindMessage, Throwable cause) {
		super(data, wellKindMessage, cause);
	}

	@Override
	protected Logger getLog() {
		return log;
	}
}
