package org.kyll.cdm.core.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * User: Kyll
 * Date: 2017-08-02 16:02
 * 票据信息
 */
public class ComrclDrft {
	private String tp;
	private BigDecimal isseAmt;
	private Date isseDt;
	private Date dueDt;
	private String banEndrsmtMk;
	private String rmrk;

	public ComrclDrft() {
	}

	public String getTp() {
		return tp;
	}

	public void setTp(String tp) {
		this.tp = tp;
	}

	public BigDecimal getIsseAmt() {
		return isseAmt;
	}

	public void setIsseAmt(BigDecimal isseAmt) {
		this.isseAmt = isseAmt;
	}

	public Date getIsseDt() {
		return isseDt;
	}

	public void setIsseDt(Date isseDt) {
		this.isseDt = isseDt;
	}

	public Date getDueDt() {
		return dueDt;
	}

	public void setDueDt(Date dueDt) {
		this.dueDt = dueDt;
	}

	public String getBanEndrsmtMk() {
		return banEndrsmtMk;
	}

	public void setBanEndrsmtMk(String banEndrsmtMk) {
		this.banEndrsmtMk = banEndrsmtMk;
	}

	public String getRmrk() {
		return rmrk;
	}

	public void setRmrk(String rmrk) {
		this.rmrk = rmrk;
	}
}
