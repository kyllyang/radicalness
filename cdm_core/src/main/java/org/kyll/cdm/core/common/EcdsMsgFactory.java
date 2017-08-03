package org.kyll.cdm.core.common;

import org.kyll.cdm.core.dto.ComrclDrft;
import org.kyll.cdm.core.entity.Participator;
import org.kyll.cdm.core.entity.ecdsmsg.EcdsMsg;
import org.kyll.cdm.core.entity.ecdsmsg.EcdsMsg001;
import org.kyll.common.util.DateUtil;

/**
 * User: Kyll
 * Date: 2017-08-03 15:22
 */
public class EcdsMsgFactory {
	public static EcdsMsg create(EcdsMsgType ecdsMsgType, ComrclDrft comrclDrft, Participator drwr, Participator accptr, Participator pyee) {
		EcdsMsg001 vo = new EcdsMsg001();
		vo.setMsgType(ecdsMsgType.getValue());

		vo.setComrclDrftTp(comrclDrft.getTp());
		vo.setComrclDrftIsseAmt(comrclDrft.getIsseAmt());
		vo.setComrclDrftIsseDt(DateUtil.formatDateCompact(comrclDrft.getIsseDt()));
		vo.setComrclDrftDueDt(DateUtil.formatDateCompact(comrclDrft.getDueDt()));
		vo.setComrclDrftBanEndrsmtMk(comrclDrft.getBanEndrsmtMk());
		vo.setComrclDrftRmrk(comrclDrft.getRmrk());

		vo.setDrwrRole(drwr.getRole());
		vo.setDrwrNm(drwr.getNm());
		vo.setDrwrCmonId(drwr.getCmonId());
		vo.setDrwrElctrncSgntr(drwr.getElctrncSgntr());
		vo.setDrwrAcctId(drwr.getAcctId());
		vo.setDrwrAcctSvcr(drwr.getAcctAcctSvcr());
		vo.setDrwrCdtRatgs(drwr.getCdtRatgs());
		vo.setDrwrCdtRatgAgcy(drwr.getCdtRatgAgcy());
		vo.setDrwrCdtRatgDueDt(DateUtil.formatDateCompact(drwr.getCdtRatgDueDt()));

		vo.setAccptrNm(accptr.getNm());
		vo.setAccptrAcctId(accptr.getAcctId());
		vo.setAccptrAcctSvcr(accptr.getAcctAcctSvcr());

		vo.setPyeeNm(pyee.getNm());
		vo.setPyeeAcctId(pyee.getAcctId());
		vo.setPyeeAcctSvcr(pyee.getAcctAcctSvcr());

		return vo;
	}
}
