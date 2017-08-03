package org.kyll.cdm.core.entity.ecdsmsg;

import org.kyll.base.persistence.Entity;

/**
 * User: Kyll
 * Date: 2016-07-26 17:59
 */
public abstract class EcdsMsg extends Entity<Long> {
	private Long ecdsMsgId;
	private String uniqueness;

	private String origReceiver;
	private String msgType;
	private String msgVersion;
	private String msgIdId;
	private String msgIdCreDtTm;
	private String ccy;

	public EcdsMsg() {
	}

	public Long getEcdsMsgId() {
		return ecdsMsgId;
	}

	public void setEcdsMsgId(Long ecdsMsgId) {
		this.ecdsMsgId = ecdsMsgId;
	}

	public String getUniqueness() {
		return uniqueness;
	}

	public void setUniqueness(String uniqueness) {
		this.uniqueness = uniqueness;
	}

	public String getOrigReceiver() {
		return origReceiver;
	}

	public void setOrigReceiver(String origReceiver) {
		this.origReceiver = origReceiver;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getMsgVersion() {
		return msgVersion;
	}

	public void setMsgVersion(String msgVersion) {
		this.msgVersion = msgVersion;
	}

	public String getMsgIdId() {
		return msgIdId;
	}

	public void setMsgIdId(String msgIdId) {
		this.msgIdId = msgIdId;
	}

	public String getMsgIdCreDtTm() {
		return msgIdCreDtTm;
	}

	public void setMsgIdCreDtTm(String msgIdCreDtTm) {
		this.msgIdCreDtTm = msgIdCreDtTm;
	}

	public String getCcy() {
		return ccy;
	}

	public void setCcy(String ccy) {
		this.ccy = ccy;
	}
}
