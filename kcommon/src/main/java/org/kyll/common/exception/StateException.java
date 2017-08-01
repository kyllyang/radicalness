package org.kyll.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

/**
 * User: Kyll
 * Date: 2017-07-31 09:42
 * 状态异常。
 * 通常使用在已存在的数据与要求的数据不符时抛出。
 */
@Slf4j
public class StateException extends BaseException {
	public StateException(String wellKindMessage) {
		this(wellKindMessage, null);
	}

	public StateException(String wellKindMessage, Throwable cause) {
		this(null, wellKindMessage, cause);
	}

	public StateException(Object data, String wellKindMessage, Throwable cause) {
		super(data, wellKindMessage, cause);
	}

	@Override
	protected Logger getLog() {
		return log;
	}
}
