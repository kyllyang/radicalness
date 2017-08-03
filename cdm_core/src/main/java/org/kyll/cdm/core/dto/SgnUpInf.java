package org.kyll.cdm.core.dto;

/**
 * User: Kyll
 * Date: 2017-08-02 15:37
 */
public class SgnUpInf {
	// 承兑回复信息
	private String accptrSgnUpDt;
	private String accptrSgnUpMk;
	private String ucondlPrmsMrk;
	private String accptncAgrmtNb;

	// 收票回复信息
	private String pyeeSgnUpDt;
	private String pyeeSgnUpMk;

	// 背书、贴现、回购式贴现赎回、转贴现、回购式转贴现赎回、再贴现、回购式再贴现赎回、央行卖出商票、保证、质押、质押解除、提示付款、同意清偿回复信息
	private String dt;
	private String sgnUpMk;

	// 贴现回复信息
	private String dscntAgrmtNb;

	// 提示付款、逾期提示付款回复信息
	private String dshnrCd;
	private String dshnrRsn;
}
