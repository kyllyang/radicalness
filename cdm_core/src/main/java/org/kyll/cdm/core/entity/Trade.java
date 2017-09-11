package org.kyll.cdm.core.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * User: Kyll
 * Date: 2017-08-02 11:32
 */
public class Trade {
	@Getter @Setter private Long id;
	@Getter @Setter private Long draftId;
	@Getter @Setter private String tp;
	@Getter @Setter private Date sendDt;
	@Getter @Setter private Date receiveDt;
	@Getter @Setter private Long sender;
	@Getter @Setter private Long receiver;
	@Getter @Setter private String direction;// 交易方向。 发送 0000; 接收 0001
	@Getter @Setter private String status;// 交易状态。默认 0000; 当发送或接收 032 时修改为 0032 表示撤销; 当发送 004 时修改为 0004

	public Trade() {
	}
}
