package org.kyll.cdm.core.dao;

import org.kyll.base.persistence.impl.JdbcTemplateDao;
import org.kyll.cdm.core.entity.TradeDetail;
import org.springframework.stereotype.Repository;

/**
 * User: Kyll
 * Date: 2017-08-03 16:16
 */
@Repository
public class TradeDetailDao extends JdbcTemplateDao<TradeDetail, Long> {
}
