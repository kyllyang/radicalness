package org.kyll.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

/**
 * User: Kyll
 * Date: 2017-07-30 04:04
 * 异常根类。
 * 应用时抛出此类的子类。
 */
@Slf4j
public abstract class BaseException extends Exception {
	private String wellKindMessage;
	private Object data;

	/**
	 * 构造异常类
	 * @param data 异常发生时，可将必要的数据传入此值
	 * @param wellKindMessage 具有良好可读性的异常描述
	 * @param cause 异常类
	 */
	public BaseException(Object data, String wellKindMessage, Throwable cause) {
		super(wellKindMessage, cause);

		this.data = data;
		this.wellKindMessage = wellKindMessage;

		this.getLog().error(getFullMessage(), cause);
	}

	/**
	 * 获取完成的异常信息描述
	 * @return
	 */
	public String getFullMessage() {
		return wellKindMessage;// TODO
	}

	/**
	 * 获取异常发生时的数据
	 * @return
	 */
	public Object getData() {
		return data;
	}

	protected abstract Logger getLog();
}
