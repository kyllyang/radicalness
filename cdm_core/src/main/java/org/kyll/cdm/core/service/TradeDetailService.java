package org.kyll.cdm.core.service;

import org.kyll.cdm.core.entity.TradeDetail;
import org.kyll.cdm.core.dao.TradeDetailDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: Kyll
 * Date: 2017-08-02 13:53
 */
@Service
@Transactional(readOnly = true)
public class TradeDetailService {
	@Autowired
	private TradeDetailDao tradeDetailDao;

	public void save(TradeDetail tradeDetail) {

	}
}
