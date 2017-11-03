package org.kyll.tax.cccc.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * User: Kyll
 * Date: 2017-10-11 09:12
 */
@ToString
@Entity
@Table(name = "T_TAX_DATA_MSG_YQ")
public class DataMsgYq {
	@Id
	@Getter @Setter private String taxId;
	@Getter @Setter private String tranDate;
	@Getter @Setter private String tranSeqno;
	@Getter @Setter private String dtlSeqno;
	@Getter @Setter private String dataMsg;
	@Getter @Setter private String msgStat;
}
