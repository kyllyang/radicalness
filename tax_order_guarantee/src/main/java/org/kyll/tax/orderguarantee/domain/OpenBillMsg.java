package org.kyll.tax.orderguarantee.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * User: Kyll
 * Date: 2017-09-13 15:25
 */
@Entity
@Table(name = "T_TAX_OPEN_BILL_MSG")
public class OpenBillMsg {
	@Id
	@Getter @Setter private String openIdx;
	@Getter @Setter private String taxDate;
	@Getter @Setter private String acc;
	@Getter @Setter private String cstmId;
	@Getter @Setter private String accType;
	@Getter @Setter private String prodCode;
	@Getter @Setter private BigDecimal amt;
	@Getter @Setter private BigDecimal taxRate;
	@Getter @Setter private BigDecimal taxAmt;
	@Getter @Setter private String summ;
	@Getter @Setter private String tranSeqno;
	@Getter @Setter private String accInst;
	@Getter @Setter private String taxpayerId;
	@Getter @Setter private String billType;
	@Getter @Setter private String openStat;
	@Getter @Setter private String exchFlag;
	@Getter @Setter private String exchReqPoint;
	@Getter @Setter private String postTlr;
	@Getter @Setter private String postPhone;
	@Getter @Setter private String postAddr;

	public OpenBillMsg() {
	}
}
