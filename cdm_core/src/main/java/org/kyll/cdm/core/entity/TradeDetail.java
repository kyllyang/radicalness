package org.kyll.cdm.core.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * User: Kyll
 * Date: 2017-08-02 11:48
 */
public class TradeDetail {
	@Getter @Setter private Long id;
	@Getter @Setter private Long tradeId;
	@Getter @Setter private Long ecdsMsgId;
	@Getter @Setter private String ecdsMsgType;
	@Getter @Setter private String direction;// 报文方向。 发送 0000; 接收 0001
	@Getter @Setter private Date dt;// 发送或者接收时间
	@Getter @Setter private Long returnEcdsMsgId;// 发送之后返回的报文ID

	public TradeDetail() {
	}
}
