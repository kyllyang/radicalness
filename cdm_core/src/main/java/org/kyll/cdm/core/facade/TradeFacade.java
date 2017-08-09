package org.kyll.cdm.core.facade;

import lombok.extern.slf4j.Slf4j;
import org.kyll.cdm.core.common.DraftPeriod;
import org.kyll.cdm.core.common.EcdsMsgFactory;
import org.kyll.cdm.core.common.EcdsMsgType;
import org.kyll.cdm.core.common.TradeDirection;
import org.kyll.cdm.core.common.TradeStatus;
import org.kyll.cdm.core.common.TradeType;
import org.kyll.cdm.core.dto.AOAccnInf;
import org.kyll.cdm.core.dto.Accptnc;
import org.kyll.cdm.core.dto.CmonInf;
import org.kyll.cdm.core.dto.CntrlBkSellgDrfts;
import org.kyll.cdm.core.dto.Collztn;
import org.kyll.cdm.core.dto.ComrclDrft;
import org.kyll.cdm.core.dto.Dscnt;
import org.kyll.cdm.core.dto.Endrsmt;
import org.kyll.cdm.core.dto.Guarntee;
import org.kyll.cdm.core.dto.Issnc;
import org.kyll.cdm.core.dto.OrgnlMsgId;
import org.kyll.cdm.core.dto.OvrduePrsnttn;
import org.kyll.cdm.core.dto.Prsnttn;
import org.kyll.cdm.core.dto.Rcrs;
import org.kyll.cdm.core.dto.Rcrsa;
import org.kyll.cdm.core.dto.RdscntWthCntrlBk;
import org.kyll.cdm.core.dto.RdscntWthComrclBk;
import org.kyll.cdm.core.dto.RpdCollztn;
import org.kyll.cdm.core.dto.RpdDscnt;
import org.kyll.cdm.core.dto.RpdRdscntWthCntrlBk;
import org.kyll.cdm.core.dto.RpdRdscntWthComrclBk;
import org.kyll.cdm.core.dto.SgnUpInf;
import org.kyll.cdm.core.entity.Draft;
import org.kyll.cdm.core.entity.Participator;
import org.kyll.cdm.core.entity.Trade;
import org.kyll.cdm.core.service.DraftService;
import org.kyll.cdm.core.service.EcdsMsgRuleService;
import org.kyll.cdm.core.service.ParticipatorService;
import org.kyll.cdm.core.service.TradeService;
import org.kyll.common.Const;
import org.kyll.common.exception.BaseException;
import org.kyll.common.util.BeanUtil;
import org.kyll.common.util.DateUtil;
import org.kyll.common.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: Kyll
 * Date: 2017-08-02 13:56
 */
@Slf4j
@Service
public class TradeFacade {
	@Autowired
	private EcdsMsgRuleService ecdsMsgRuleService;
	@Autowired
	private DraftService draftService;
	@Autowired
	private ParticipatorService participatorService;
	@Autowired
	private TradeService tradeService;
	@Autowired
	private EcdsMsgFacade ecdsMsgFacade;

	/**
	 * 出票信息登记
	 * @param comrclDrft 票据信息
	 * @param drwr 出票人信息
	 * @param accptr 承兑人信息
	 * @param pyee 收款人信息
	 * @return 交易信息
	 */
	public Trade createRegister(ComrclDrft comrclDrft, Long drwr, Long accptr, Long pyee) throws BaseException {
		log.info(String.format(Const.LOG_ECDS_TRADE_START, Const.PN_TRADE_REGISTER));

		log.info(Const.LOG_VALIDATE_TRADE);
		ecdsMsgRuleService.validate(EcdsMsgType.DRAFT_REGISTER, comrclDrft, drwr, accptr, pyee);

		Draft draft = new Draft();
		BeanUtil.copyProperties(comrclDrft, draft);
		draft.setIdNb(null);
		draft.setSts(null);
		draft.setPeriod(DraftPeriod.BIRTH.getValue());
		draft.setDrwr(drwr);
		draft.setAccptr(accptr);
		draft.setPyee(pyee);

		log.info(String.format(Const.LOG_SAVE_DRAFT, JsonUtil.format(draft)));
		draftService.save(draft);

		Trade trade = new Trade();
		trade.setDraftId(draft.getId());
		trade.setTp(TradeType.REGISTER.getValue());
		trade.setSendDt(DateUtil.now());
		trade.setReceiveDt(null);
		trade.setSender(drwr);
		trade.setReceiver(participatorService.getPboc().getId());
		trade.setDirection(TradeDirection.SEND.getValue());
		trade.setStatus(TradeStatus.NORMAL.getValue());

		log.info(String.format(Const.LOG_SAVE_TRADE, JsonUtil.format(trade)));
		tradeService.save(trade);

		ecdsMsgFacade.send(
				trade,
				EcdsMsgFactory.create(
						EcdsMsgType.DRAFT_REGISTER,
						comrclDrft,
						participatorService.get(drwr),
						participatorService.get(accptr),
						participatorService.get(pyee)));

		log.info(String.format(Const.LOG_ECDS_TRADE_END, Const.PN_TRADE_REGISTER));
		return trade;
	}

	/**
	 * 提示承兑
	 * @param idNb 票据号码
	 * @param accptnc 提示承兑信息
	 * @param drwr 出票人信息
	 * @return 出票人信息
	 */
	public Trade createAcceptance(String idNb, Accptnc accptnc, Participator drwr) {
		return null;
	}

	/**
	 * 提示收票
	 * @param idNb 票据号码
	 * @param issnc 提示收款信息
	 * @param drwr 出票人信息
	 * @return 出票人信息
	 */
	public Trade createIssuance(String idNb, Issnc issnc, Participator drwr) {
		return null;
	}

	/**
	 * 撤票
	 * @param idNb 票据号码
	 * @param drwr 出票人信息
	 * @return 出票人信息
	 */
	public Trade createDestruction(String idNb, Participator drwr) {
		return null;
	}

	/**
	 * 转让背书
	 * @param idNb 票据号码
	 * @param endrsmt 背书信息
	 * @param endrsr 背书人信息
	 * @param endrsee 被背书人信息
	 * @return 交易信息
	 */
	public Trade createEndorsement(String idNb, Endrsmt endrsmt, Participator endrsr, Participator endrsee) {
		return null;
	}

	/**
	 * 贴现
	 * @param idNb 票据号码
	 * @param dscnt 贴现信息
	 * @param aoAccnInf 入账信息
	 * @param dscntPropsr 贴现申请人
	 * @param dscntBk 贴入人信息
	 * @return 交易信息
	 */
	public Trade createDiscount(String idNb, Dscnt dscnt, AOAccnInf aoAccnInf, Participator dscntPropsr, Participator dscntBk) {
		return null;
	}

	/**
	 * 回购式贴现赎回
	 * @param idNb 票据号码
	 * @param rpdDscnt 贴现赎回信息
	 * @param dscntBk 贴现赎回申请人信息（原贴入人）
	 * @return 交易信息
	 */
	public Trade createRepurchasedDiscount(String idNb, RpdDscnt rpdDscnt, Participator dscntBk) {
		return null;
	}

	/**
	 * 转贴现
	 * @param idNb 票据号码
	 * @param rdscntWthComrclBk 转贴现信息
	 * @param rqstngBkOfRdscntWthComrclBk 贴出人信息
	 * @param rcvgBkOfRdscntWthComrclBk 贴入人信息
	 * @return 交易信息
	 */
	public Trade createRediscountWithCommercialBank(String idNb, RdscntWthComrclBk rdscntWthComrclBk, Participator rqstngBkOfRdscntWthComrclBk, Participator rcvgBkOfRdscntWthComrclBk) {
		return null;
	}

	/**
	 * 回购式转贴现赎回
	 * @param idNb 票据号码
	 * @param rpdRdscntWthComrclBk 转贴现赎回信息
	 * @param orgnlRcvgBkOfRdscntWthComrclBk 转贴现赎回申请人信息（原贴入人）
	 * @return 交易信息
	 */
	public Trade createRepurchasedRediscountWithCommercialBank(String idNb, RpdRdscntWthComrclBk rpdRdscntWthComrclBk, Participator orgnlRcvgBkOfRdscntWthComrclBk) {
		return null;
	}

	/**
	 * 再贴现
	 * @param idNb 票据号码
	 * @param rdscntWthCntrlBk 再贴现信息
	 * @param rqstngBkOfRdscntWthCntrlBk 贴出人信息
	 * @param rdscntWthCntrlBkSys 贴入行信息
	 * @return 交易信息
	 */
	public Trade createRediscountWithCentralBank(String idNb, RdscntWthCntrlBk rdscntWthCntrlBk, Participator rqstngBkOfRdscntWthCntrlBk, Participator rdscntWthCntrlBkSys) {
		return null;
	}

	/**
	 * 回购式再贴现赎回
	 * @param idNb 票据号码
	 * @param rpdRdscntWthCntrlBk 再贴现赎回信息
	 * @param rdscntWthCntrlBkSys 再贴现赎回申请行信息（原贴入行）
	 * @return 交易信息
	 */
	public Trade createRepurchasedRediscountWithCentralBank(String idNb, RpdRdscntWthCntrlBk rpdRdscntWthCntrlBk, Participator rdscntWthCntrlBkSys) {
		return null;
	}

	/**
	 * 央行卖出商业汇票
	 * @param idNb 票据号码
	 * @param cntrlBkSellgDrfts 央行卖出商业汇票信息
	 * @param sellrInf 卖出人信息
	 * @param buyrInf 买入人信息
	 * @return 交易信息
	 */
	public Trade createCentralBankSellingDrafts(String idNb, CntrlBkSellgDrfts cntrlBkSellgDrfts, Participator sellrInf, Participator buyrInf) {
		return null;
	}

	/**
	 * 保证
	 * @param idNb 票据号码
	 * @param guarntee 保证信息
	 * @param warntee 被保证人信息
	 * @param guarntr 保证人信息
	 * @return 出票人信息
	 */
	public Trade createGuarantee(String idNb, Guarntee guarntee, Participator warntee, Participator guarntr) {
		return null;
	}

	/**
	 * 质押
	 * @param idNb 票据号码
	 * @param collztn 质押信息
	 * @param collztnPropsr 出质人信息
	 * @param collztnBk 质权人信息
	 * @return 出票人信息
	 */
	public Trade createCollateralization(String idNb, Collztn collztn, Participator collztnPropsr, Participator collztnBk) {
		return null;
	}

	/**
	 * 质押解除
	 * @param idNb 票据号码
	 * @param rpdCollztn 质押解除信息
	 * @param collztnBk 质押解除申请人信息（质权人）
	 * @return 出票人信息
	 */
	public Trade createRepurchasedCollateralization(String idNb, RpdCollztn rpdCollztn, Participator collztnBk) {
		return null;
	}

	/**
	 * 提示付款
	 * @param idNb 票据号码
	 * @param prsnttn 提示付款信息
	 * @param drftHldr 提示付款人信息
	 * @return 出票人信息
	 */
	public Trade createPresentation(String idNb, Prsnttn prsnttn, Participator drftHldr) {
		return null;
	}

	/**
	 * 逾期提示付款
	 * @param idNb 票据号码
	 * @param ovrduePrsnttn 逾期提示付款信息
	 * @param drftHldr 逾期提示付款人信息
	 * @return 出票人信息
	 */
	public Trade createOverduePresentation(String idNb, OvrduePrsnttn ovrduePrsnttn, Participator drftHldr) {
		return null;
	}

	/**
	 * 追索通知
	 * @param idNb 票据号码
	 * @param rcrs 追索通知信息
	 * @param rcrsr 追索人信息
	 * @param rcvgPrsnOfRcrs 被追索人信息
	 * @return 出票人信息
	 */
	public Trade createRecourseNotification(String idNb, Rcrs rcrs, Participator rcrsr, Participator rcvgPrsnOfRcrs) {
		return null;
	}

	/**
	 * 追索同意清偿
	 * @param orgnlMsgId 原申请报文标识
	 * @param idNb 票据号码
	 * @param rcrsa 同意清偿信息
	 * @param rcvgPrsnOfRcrs 同意清偿人信息（被追索人之一）
	 * @return 出票人信息
	 */
	public Trade createRecourseAgreement(OrgnlMsgId orgnlMsgId, String idNb, Rcrsa rcrsa, Participator rcvgPrsnOfRcrs) {
		return null;
	}

	/**
	 * 通用回复(签收)
	 * @param trade 原交易
	 * @param cmonInf 通用回复信息
	 * @param sgnr 回复人信息
	 * @param sgnUpInf 回复信息
	 */
	public void commonSignUp(Trade trade, CmonInf cmonInf, Participator sgnr, SgnUpInf sgnUpInf) {

	}

	/**
	 * 通用撤销
	 * @param trade 原交易
	 * @param cxlPropsr 撤销申请人信息
	 */
	public void cancellation(Trade trade, Participator cxlPropsr) {

	}
}
