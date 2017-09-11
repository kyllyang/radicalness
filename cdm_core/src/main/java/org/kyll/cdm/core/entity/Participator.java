package org.kyll.cdm.core.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * User: Kyll
 * Date: 2017-08-02 11:20
 */
public class Participator {
	@Getter @Setter private Long id;
	@Getter @Setter private String code;
	@Getter @Setter private String role;
	@Getter @Setter private String nm;
	@Getter @Setter private String cmonId;
	@Getter @Setter private String elctrncSgntr;
	@Getter @Setter private String acctId;
	@Getter @Setter private String acctAcctSvcr;
	@Getter @Setter private String agcyAcctAcctSvcr;
	@Getter @Setter private String cdtRatgs;
	@Getter @Setter private String cdtRatgAgcy;
	@Getter @Setter private Date cdtRatgDueDt;

	public Participator() {
	}
}
