package org.kyll.cdm.core.dao;

import org.kyll.base.persistence.impl.JdbcTemplateDao;
import org.kyll.cdm.core.entity.Trade;
import org.springframework.stereotype.Repository;

/**
 * User: Kyll
 * Date: 2017-08-03 15:19
 */
@Repository
public class TradeDao extends JdbcTemplateDao<Trade, Long> {
}
