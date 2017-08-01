package org.kyll.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

/**
 * User: Kyll
 * Date: 2017-07-31 09:43
 * 不支持的异常。
 * 通常使用在方法暂未实现，或者不需要执行此操作时抛出。
 */
@Slf4j
public class UnsupportedException extends BaseException {
	public UnsupportedException(String wellKindMessage) {
		this(wellKindMessage, null);
	}

	public UnsupportedException(String wellKindMessage, Throwable cause) {
		this(null, wellKindMessage, cause);
	}

	public UnsupportedException(Object data, String wellKindMessage, Throwable cause) {
		super(data, wellKindMessage, cause);
	}

	@Override
	protected Logger getLog() {
		return log;
	}
}
