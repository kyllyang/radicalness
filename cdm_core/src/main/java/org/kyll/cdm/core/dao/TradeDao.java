package org.kyll.cdm.core.dao;

import org.kyll.base.repository.DefaultRepository;
import org.kyll.cdm.core.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User: Kyll
 * Date: 2017-08-03 15:19
 */
public interface TradeDao extends DefaultRepository<Trade, Long> {
}
