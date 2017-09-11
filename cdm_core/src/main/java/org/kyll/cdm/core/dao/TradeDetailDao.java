package org.kyll.cdm.core.dao;

import org.kyll.base.repository.DefaultRepository;
import org.kyll.cdm.core.entity.TradeDetail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User: Kyll
 * Date: 2017-08-03 16:16
 */
public interface TradeDetailDao extends DefaultRepository<TradeDetail, Long> {
}
