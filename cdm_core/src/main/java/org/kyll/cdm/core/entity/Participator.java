package org.kyll.cdm.core.entity;

import org.kyll.base.persistence.Entity;

import java.util.Date;

/**
 * User: Kyll
 * Date: 2017-08-02 11:20
 */
public class Participator extends Entity<Long> {
	private String code;
	private String role;
	private String nm;
	private String cmonId;
	private String elctrncSgntr;
	private String acctId;
	private String acctAcctSvcr;
	private String agcyAcctAcctSvcr;
	private String cdtRatgs;
	private String cdtRatgAgcy;
	private Date cdtRatgDueDt;

	public Participator() {
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getNm() {
		return nm;
	}

	public void setNm(String nm) {
		this.nm = nm;
	}

	public String getCmonId() {
		return cmonId;
	}

	public void setCmonId(String cmonId) {
		this.cmonId = cmonId;
	}

	public String getElctrncSgntr() {
		return elctrncSgntr;
	}

	public void setElctrncSgntr(String elctrncSgntr) {
		this.elctrncSgntr = elctrncSgntr;
	}

	public String getAcctId() {
		return acctId;
	}

	public void setAcctId(String acctId) {
		this.acctId = acctId;
	}

	public String getAcctAcctSvcr() {
		return acctAcctSvcr;
	}

	public void setAcctAcctSvcr(String acctAcctSvcr) {
		this.acctAcctSvcr = acctAcctSvcr;
	}

	public String getAgcyAcctAcctSvcr() {
		return agcyAcctAcctSvcr;
	}

	public void setAgcyAcctAcctSvcr(String agcyAcctAcctSvcr) {
		this.agcyAcctAcctSvcr = agcyAcctAcctSvcr;
	}

	public String getCdtRatgs() {
		return cdtRatgs;
	}

	public void setCdtRatgs(String cdtRatgs) {
		this.cdtRatgs = cdtRatgs;
	}

	public String getCdtRatgAgcy() {
		return cdtRatgAgcy;
	}

	public void setCdtRatgAgcy(String cdtRatgAgcy) {
		this.cdtRatgAgcy = cdtRatgAgcy;
	}

	public Date getCdtRatgDueDt() {
		return cdtRatgDueDt;
	}

	public void setCdtRatgDueDt(Date cdtRatgDueDt) {
		this.cdtRatgDueDt = cdtRatgDueDt;
	}
}
