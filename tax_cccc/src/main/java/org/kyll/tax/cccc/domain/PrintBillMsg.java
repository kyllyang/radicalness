package org.kyll.tax.cccc.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * User: Kyll
 * Date: 2018-01-22 15:07
 */
@ToString
@Entity
@Table(name = "T_TAX_PRINT_BILL_MSG")
public class PrintBillMsg {
	@Id
	@Getter @Setter private String printIdx;
	@Lob
	@Getter @Setter private String openIdx;
	@Getter @Setter private String openDate;
	@Getter @Setter private String taxDate;
	@Getter @Setter private BigDecimal totalAmt;
	@Getter @Setter private BigDecimal totalTaxAmt;
	@Getter @Setter private BigDecimal total;
	@Getter @Setter private String billCertNo;
	@Getter @Setter private String billNo;
	@Getter @Setter private String billType;
	@Getter @Setter private String openMode;
	@Getter @Setter private String inName;
	@Getter @Setter private String inTaxpayer;
	@Getter @Setter private String inAddrPhone;
	@Getter @Setter private String inBankAcc;
	@Getter @Setter private String openPoint;

	public PrintBillMsg() {
	}

	public String getPrintIdx() {
		return printIdx;
	}

	public void setPrintIdx(String printIdx) {
		this.printIdx = printIdx;
	}

	public String getOpenIdx() {
		return openIdx;
	}

	public void setOpenIdx(String openIdx) {
		this.openIdx = openIdx;
	}

	public String getOpenDate() {
		return openDate;
	}

	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}

	public String getTaxDate() {
		return taxDate;
	}

	public void setTaxDate(String taxDate) {
		this.taxDate = taxDate;
	}

	public BigDecimal getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(BigDecimal totalAmt) {
		this.totalAmt = totalAmt;
	}

	public BigDecimal getTotalTaxAmt() {
		return totalTaxAmt;
	}

	public void setTotalTaxAmt(BigDecimal totalTaxAmt) {
		this.totalTaxAmt = totalTaxAmt;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getBillCertNo() {
		return billCertNo;
	}

	public void setBillCertNo(String billCertNo) {
		this.billCertNo = billCertNo;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getOpenMode() {
		return openMode;
	}

	public void setOpenMode(String openMode) {
		this.openMode = openMode;
	}

	public String getInName() {
		return inName;
	}

	public void setInName(String inName) {
		this.inName = inName;
	}

	public String getInTaxpayer() {
		return inTaxpayer;
	}

	public void setInTaxpayer(String inTaxpayer) {
		this.inTaxpayer = inTaxpayer;
	}

	public String getInAddrPhone() {
		return inAddrPhone;
	}

	public void setInAddrPhone(String inAddrPhone) {
		this.inAddrPhone = inAddrPhone;
	}

	public String getInBankAcc() {
		return inBankAcc;
	}

	public void setInBankAcc(String inBankAcc) {
		this.inBankAcc = inBankAcc;
	}

	public String getOpenPoint() {
		return openPoint;
	}

	public void setOpenPoint(String openPoint) {
		this.openPoint = openPoint;
	}
}
