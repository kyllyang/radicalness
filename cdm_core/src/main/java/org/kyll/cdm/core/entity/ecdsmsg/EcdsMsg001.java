package org.kyll.cdm.core.entity.ecdsmsg;

import java.math.BigDecimal;

/**
 * 出票信息登记报文
 */
public class EcdsMsg001 extends EcdsMsg {
	private String comrclDrftIsseDt;
	private String drwrCdtRatgAgcy;
	private String drwrCdtRatgDueDt;
	private String drwrElctrncSgntr;
	private String comrclDrftDueDt;
	private String comrclDrftRmrk;
	private BigDecimal comrclDrftIsseAmt;
	private String accptrAcctSvcr;
	private String comrclDrftBanEndrsmtMk;
	private String accptrAcctId;
	private String pyeeNm;
	private String pyeeAcctSvcr;
	private String drwrAcctId;
	private String drwrAcctSvcr;
	private String comrclDrftTp;
	private String pyeeAcctId;
	private String accptrNm;
	private String drwrNm;
	private String drwrCmonId;
	private String drwrCdtRatgs;
	private String drwrRole;

	public EcdsMsg001() {
	}

	public String getComrclDrftIsseDt() {
		return this.comrclDrftIsseDt;
	}

	public void setComrclDrftIsseDt(String comrclDrftIsseDt) {
		this.comrclDrftIsseDt = comrclDrftIsseDt;
	}

	public String getDrwrCdtRatgAgcy() {
		return this.drwrCdtRatgAgcy;
	}

	public void setDrwrCdtRatgAgcy(String drwrCdtRatgAgcy) {
		this.drwrCdtRatgAgcy = drwrCdtRatgAgcy;
	}

	public String getDrwrCdtRatgDueDt() {
		return this.drwrCdtRatgDueDt;
	}

	public void setDrwrCdtRatgDueDt(String drwrCdtRatgDueDt) {
		this.drwrCdtRatgDueDt = drwrCdtRatgDueDt;
	}

	public String getDrwrElctrncSgntr() {
		return this.drwrElctrncSgntr;
	}

	public void setDrwrElctrncSgntr(String drwrElctrncSgntr) {
		this.drwrElctrncSgntr = drwrElctrncSgntr;
	}

	public String getComrclDrftDueDt() {
		return this.comrclDrftDueDt;
	}

	public void setComrclDrftDueDt(String comrclDrftDueDt) {
		this.comrclDrftDueDt = comrclDrftDueDt;
	}

	public String getComrclDrftRmrk() {
		return this.comrclDrftRmrk;
	}

	public void setComrclDrftRmrk(String comrclDrftRmrk) {
		this.comrclDrftRmrk = comrclDrftRmrk;
	}

	public BigDecimal getComrclDrftIsseAmt() {
		return this.comrclDrftIsseAmt;
	}

	public void setComrclDrftIsseAmt(BigDecimal comrclDrftIsseAmt) {
		this.comrclDrftIsseAmt = comrclDrftIsseAmt;
	}

	public String getAccptrAcctSvcr() {
		return this.accptrAcctSvcr;
	}

	public void setAccptrAcctSvcr(String accptrAcctSvcr) {
		this.accptrAcctSvcr = accptrAcctSvcr;
	}

	public String getComrclDrftBanEndrsmtMk() {
		return this.comrclDrftBanEndrsmtMk;
	}

	public void setComrclDrftBanEndrsmtMk(String comrclDrftBanEndrsmtMk) {
		this.comrclDrftBanEndrsmtMk = comrclDrftBanEndrsmtMk;
	}

	public String getAccptrAcctId() {
		return this.accptrAcctId;
	}

	public void setAccptrAcctId(String accptrAcctId) {
		this.accptrAcctId = accptrAcctId;
	}

	public String getPyeeNm() {
		return this.pyeeNm;
	}

	public void setPyeeNm(String pyeeNm) {
		this.pyeeNm = pyeeNm;
	}

	public String getPyeeAcctSvcr() {
		return this.pyeeAcctSvcr;
	}

	public void setPyeeAcctSvcr(String pyeeAcctSvcr) {
		this.pyeeAcctSvcr = pyeeAcctSvcr;
	}

	public String getDrwrAcctId() {
		return this.drwrAcctId;
	}

	public void setDrwrAcctId(String drwrAcctId) {
		this.drwrAcctId = drwrAcctId;
	}

	public String getDrwrAcctSvcr() {
		return this.drwrAcctSvcr;
	}

	public void setDrwrAcctSvcr(String drwrAcctSvcr) {
		this.drwrAcctSvcr = drwrAcctSvcr;
	}

	public String getComrclDrftTp() {
		return this.comrclDrftTp;
	}

	public void setComrclDrftTp(String comrclDrftTp) {
		this.comrclDrftTp = comrclDrftTp;
	}

	public String getPyeeAcctId() {
		return this.pyeeAcctId;
	}

	public void setPyeeAcctId(String pyeeAcctId) {
		this.pyeeAcctId = pyeeAcctId;
	}

	public String getAccptrNm() {
		return this.accptrNm;
	}

	public void setAccptrNm(String accptrNm) {
		this.accptrNm = accptrNm;
	}

	public String getDrwrNm() {
		return this.drwrNm;
	}

	public void setDrwrNm(String drwrNm) {
		this.drwrNm = drwrNm;
	}

	public String getDrwrCmonId() {
		return this.drwrCmonId;
	}

	public void setDrwrCmonId(String drwrCmonId) {
		this.drwrCmonId = drwrCmonId;
	}

	public String getDrwrCdtRatgs() {
		return this.drwrCdtRatgs;
	}

	public void setDrwrCdtRatgs(String drwrCdtRatgs) {
		this.drwrCdtRatgs = drwrCdtRatgs;
	}

	public String getDrwrRole() {
		return this.drwrRole;
	}

	public void setDrwrRole(String drwrRole) {
		this.drwrRole = drwrRole;
	}

}
