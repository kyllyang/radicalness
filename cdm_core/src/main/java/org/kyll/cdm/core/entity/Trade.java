package org.kyll.cdm.core.entity;

import org.kyll.base.persistence.Entity;

import java.util.Date;

/**
 * User: Kyll
 * Date: 2017-08-02 11:32
 */
public class Trade extends Entity<Long> {
	private Long draftId;
	private String tp;
	private Date sendDt;
	private Date receiveDt;
	private Long sender;
	private Long receiver;
	private String direction;// 交易方向。 发送 0000; 接收 0001
	private String status;// 交易状态。默认 0000; 当发送或接收 032 时修改为 0032 表示撤销; 当发送 004 时修改为 0004

	public Trade() {
	}

	public Long getDraftId() {
		return draftId;
	}

	public void setDraftId(Long draftId) {
		this.draftId = draftId;
	}

	public String getTp() {
		return tp;
	}

	public void setTp(String tp) {
		this.tp = tp;
	}

	public Date getSendDt() {
		return sendDt;
	}

	public void setSendDt(Date sendDt) {
		this.sendDt = sendDt;
	}

	public Date getReceiveDt() {
		return receiveDt;
	}

	public void setReceiveDt(Date receiveDt) {
		this.receiveDt = receiveDt;
	}

	public Long getSender() {
		return sender;
	}

	public void setSender(Long sender) {
		this.sender = sender;
	}

	public Long getReceiver() {
		return receiver;
	}

	public void setReceiver(Long receiver) {
		this.receiver = receiver;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
