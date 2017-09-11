package org.kyll.cdm.core.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * User: Kyll
 * Date: 2017-08-02 10:58
 * 票据信息
 */
@Entity
@Table(name = "CORE_DRAFT")
public class Draft  {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CORE_DRAFT" )
	@SequenceGenerator(name = "SEQ_CORE_DRAFT", allocationSize = 30)
	@Getter @Setter private Long id;
	@Getter @Setter private String idNb;
	@Getter @Setter private String sts;// 票据状态
	@Getter @Setter private String period;// 票据生命周期。出票未有票号 0000;已经出票 0001;作废(004报文) 0002; 已结清 0003
	@Getter @Setter private String tp;
	@Getter @Setter private BigDecimal isseAmt;
	@Getter @Setter private Date isseDt;
	@Getter @Setter private Date dueDt;
	@Getter @Setter private String banEndrsmtMk;
	@Getter @Setter private String rmrk;
	@Getter @Setter private Long drwr;
	@Getter @Setter private Long accptr;
	@Getter @Setter private Long pyee;

	public Draft() {
	}
}
