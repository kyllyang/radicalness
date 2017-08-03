package org.kyll.cdm.core.facade;

import com.longtop.efmp.cdg.srv.bs.ICdgSrvBS;
import com.longtop.efmp.cdg.xmlvo.CdgResultVO;
import org.kyll.cdm.core.common.CdgConfig;
import org.kyll.cdm.core.common.CdgResult;
import org.kyll.cdm.core.common.Convertor;
import org.kyll.cdm.core.common.TradeDirection;
import org.kyll.cdm.core.entity.Trade;
import org.kyll.cdm.core.entity.TradeDetail;
import org.kyll.cdm.core.entity.ecdsmsg.EcdsMsg;
import org.kyll.cdm.core.entity.ecdsmsg.EcdsMsgResult;
import org.kyll.cdm.core.service.EcdsMsgService;
import org.kyll.cdm.core.service.TradeDetailService;
import org.kyll.common.util.BeanUtil;
import org.kyll.common.util.DateUtil;
import org.kyll.common.util.SqlUtil;
import org.kyll.common.util.ValueUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * User: Kyll
 * Date: 2017-08-02 13:45
 */
@Service
public class EcdsMsgFacade {
	@Autowired
	private CdgConfig cdgConfig;
	@Autowired
	@Qualifier("cdgService")
	private ICdgSrvBS cdgService;
	@Autowired
	private EcdsMsgService ecdsMsgService;
	@Autowired
	private TradeDetailService tradeDetailService;

	@Async
	public void send(Trade trade, EcdsMsg ecdsMsg) {
		ecdsMsgService.save(ecdsMsg);

		TradeDetail tradeDetail = new TradeDetail();
		tradeDetail.setTradeId(trade.getId());
		tradeDetail.setEcdsMsgId(ecdsMsg.getEcdsMsgId());
		tradeDetail.setEcdsMsgType(ecdsMsg.getMsgType());
		tradeDetail.setDirection(TradeDirection.SEND.getValue());
		tradeDetail.setDt(DateUtil.now());
		tradeDetail.setReturnEcdsMsgId(null);
		tradeDetailService.save(tradeDetail);

		CdgResultVO cdgResultVO;
		try {
			cdgResultVO = cdgService.doRequest(cdgConfig.getAppCode(), ecdsMsg.getUniqueness(), Convertor.toIBasVO(ecdsMsg));
		} catch (Exception e) {
			cdgResultVO = new CdgResultVO();
			cdgResultVO.setResultFlag(CdgResult.FAILURE.getValue());
			cdgResultVO.setResultMsg(SqlUtil.toVarchar2(ValueUtil.toString(e)));
		}

		EcdsMsgResult ecdsMsgResult = new EcdsMsgResult();
		BeanUtil.copyProperties(cdgResultVO, ecdsMsgResult);
		ecdsMsgService.save(ecdsMsgResult);

		if (CdgResult.SUCCESS.equals(cdgResultVO.getResultFlag())) {
			ecdsMsg.setMsgIdId(cdgResultVO.getMsgIdId());
			ecdsMsg.setMsgIdCreDtTm(cdgResultVO.getMsgIdCreDt() + cdgResultVO.getMsgIdCreTm());
			ecdsMsgService.save(ecdsMsg);
		}
	}
}
