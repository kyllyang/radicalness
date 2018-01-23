package org.kyll.tax.cccc.service;

import org.kyll.base.service.DefaultService;
import org.kyll.tax.cccc.dao.PrintBillMsgDao;
import org.kyll.tax.cccc.domain.PrintBillMsg;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: Kyll
 * Date: 2018-01-22 15:09
 */
@Service
@Transactional(readOnly = true)
public class PrintBillMsgService extends DefaultService<PrintBillMsg, String, PrintBillMsgDao> {
	public List<PrintBillMsg> findList(int start, int end) {
		return this.dao.findByOpenDateLessThanAndBillCertNoNotAndBillNoNotAndOpenModeInAndOpenPointNotIn(start, end);
	}
}
