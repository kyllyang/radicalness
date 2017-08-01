package org.kyll.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

/**
 * User: Kyll
 * Date: 2017-07-31 09:41
 * 数据库异常。
 * 通常使用在数据库相关操作失败时抛出。
 */
@Slf4j
public class DatabaseException extends BaseException {
	public DatabaseException(String wellKindMessage) {
		this(wellKindMessage, null);
	}

	public DatabaseException(String wellKindMessage, Throwable cause) {
		this(null, wellKindMessage, cause);
	}

	public DatabaseException(Object data, String wellKindMessage, Throwable cause) {
		super(data, wellKindMessage, cause);
	}

	@Override
	protected Logger getLog() {
		return log;
	}
}
