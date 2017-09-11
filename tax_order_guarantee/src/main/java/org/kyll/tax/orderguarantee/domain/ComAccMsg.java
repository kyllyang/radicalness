package org.kyll.tax.orderguarantee.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * User: Kyll
 * Date: 2017-09-07 14:46
 */
@Entity
@Table(name = "T_TAX_COM_ACC_MSG")
public class ComAccMsg {
	@Getter @Setter private String acc;
	@Getter @Setter private String accName;
	@Id
	@Getter @Setter private String cstmId;
	@Getter @Setter private String openInst;
	@Getter @Setter private String permitNo;
	@Getter @Setter private String orgInstCode;
	@Getter @Setter private String accAddr;
	@Getter @Setter private String accPhone;
	@Getter @Setter private String taxpayerId;
	@Getter @Setter private String taxpayerName;
	@Getter @Setter private String loginType;
	@Getter @Setter private String openAccBank;
	@Getter @Setter private String openAcc;
	@Getter @Setter private String postTlr;
	@Getter @Setter private String postPhone;
	@Getter @Setter private String postAddr;
	@Getter @Setter private String accStat;
	@Getter @Setter private String flag;
	@Getter @Setter private String handFlag;

	public ComAccMsg() {
	}

	@Override
	public String toString() {
		return "ComAccMsg{" +
				"acc='" + acc + '\'' +
				", accName='" + accName + '\'' +
				", cstmId='" + cstmId + '\'' +
				", openInst='" + openInst + '\'' +
				", permitNo='" + permitNo + '\'' +
				", orgInstCode='" + orgInstCode + '\'' +
				", accAddr='" + accAddr + '\'' +
				", accPhone='" + accPhone + '\'' +
				", taxpayerId='" + taxpayerId + '\'' +
				", taxpayerName='" + taxpayerName + '\'' +
				", loginType='" + loginType + '\'' +
				", openAccBank='" + openAccBank + '\'' +
				", openAcc='" + openAcc + '\'' +
				", postTlr='" + postTlr + '\'' +
				", postPhone='" + postPhone + '\'' +
				", postAddr='" + postAddr + '\'' +
				", accStat='" + accStat + '\'' +
				", flag='" + flag + '\'' +
				", handFlag='" + handFlag + '\'' +
				'}';
	}
}
