package org.kyll.cdm.core.entity.ecdsmsg;

import lombok.Getter;
import lombok.Setter;

/**
 * User: Kyll
 * Date: 2016-07-26 17:59
 */
public abstract class EcdsMsg {
	@Getter @Setter private Long id;
	@Getter @Setter private Long ecdsMsgId;
	@Getter @Setter private String uniqueness;

	@Getter @Setter private String origReceiver;
	@Getter @Setter private String msgType;
	@Getter @Setter private String msgVersion;
	@Getter @Setter private String msgIdId;
	@Getter @Setter private String msgIdCreDtTm;
	@Getter @Setter private String ccy;

	public EcdsMsg() {
	}
}
