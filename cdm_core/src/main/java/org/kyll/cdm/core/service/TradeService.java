package org.kyll.cdm.core.service;

import org.kyll.base.service.DefaultService;
import org.kyll.cdm.core.dao.TradeDao;
import org.kyll.cdm.core.entity.Trade;
import org.springframework.stereotype.Service;

/**
 * User: Kyll
 * Date: 2017-08-02 13:53
 */
@Service
public class TradeService extends DefaultService<Trade, Long, TradeDao> {
}
