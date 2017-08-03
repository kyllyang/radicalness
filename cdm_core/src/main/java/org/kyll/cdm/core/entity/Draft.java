package org.kyll.cdm.core.entity;

import org.kyll.base.persistence.Entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * User: Kyll
 * Date: 2017-08-02 10:58
 * 票据信息
 */
public class Draft extends Entity<Long> {
	private String idNb;
	private String sts;// 票据状态
	private String period;// 票据生命周期。出票未有票号 0000;已经出票 0001;作废(004报文) 0002; 已结清 0003
	private String tp;
	private BigDecimal isseAmt;
	private Date isseDt;
	private Date dueDt;
	private String banEndrsmtMk;
	private String rmrk;
	private Long drwr;
	private Long accptr;
	private Long pyee;

	public Draft() {
	}

	public String getIdNb() {
		return idNb;
	}

	public void setIdNb(String idNb) {
		this.idNb = idNb;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
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

	public Long getDrwr() {
		return drwr;
	}

	public void setDrwr(Long drwr) {
		this.drwr = drwr;
	}

	public Long getAccptr() {
		return accptr;
	}

	public void setAccptr(Long accptr) {
		this.accptr = accptr;
	}

	public Long getPyee() {
		return pyee;
	}

	public void setPyee(Long pyee) {
		this.pyee = pyee;
	}
}
