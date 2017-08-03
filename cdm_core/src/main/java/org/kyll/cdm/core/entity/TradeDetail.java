package org.kyll.cdm.core.entity;

import org.kyll.base.persistence.Entity;

import java.util.Date;

/**
 * User: Kyll
 * Date: 2017-08-02 11:48
 */
public class TradeDetail extends Entity<Long> {
	private Long tradeId;
	private Long ecdsMsgId;
	private String ecdsMsgType;
	private String direction;// 报文方向。 发送 0000; 接收 0001
	private Date dt;// 发送或者接收时间
	private Long returnEcdsMsgId;// 发送之后返回的报文ID

	public TradeDetail() {
	}

	public Long getTradeId() {
		return tradeId;
	}

	public void setTradeId(Long tradeId) {
		this.tradeId = tradeId;
	}

	public Long getEcdsMsgId() {
		return ecdsMsgId;
	}

	public void setEcdsMsgId(Long ecdsMsgId) {
		this.ecdsMsgId = ecdsMsgId;
	}

	public String getEcdsMsgType() {
		return ecdsMsgType;
	}

	public void setEcdsMsgType(String ecdsMsgType) {
		this.ecdsMsgType = ecdsMsgType;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public Date getDt() {
		return dt;
	}

	public void setDt(Date dt) {
		this.dt = dt;
	}

	public Long getReturnEcdsMsgId() {
		return returnEcdsMsgId;
	}

	public void setReturnEcdsMsgId(Long returnEcdsMsgId) {
		this.returnEcdsMsgId = returnEcdsMsgId;
	}
}
