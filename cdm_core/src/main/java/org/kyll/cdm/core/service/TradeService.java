package org.kyll.cdm.core.service;

import org.kyll.cdm.core.entity.Trade;
import org.kyll.cdm.core.dao.TradeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: Kyll
 * Date: 2017-08-02 13:53
 */
@Service
@Transactional(readOnly = true)
public class TradeService {
	@Autowired
	private TradeDao tradeDao;

	public void save(Trade trade) {

	}
}
