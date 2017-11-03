package org.kyll.tax.cccc.dao;

import org.kyll.base.repository.DefaultRepository;
import org.kyll.tax.cccc.domain.DataMsgYq;

import java.util.List;

/**
 * User: Kyll
 * Date: 2017-10-11 09:15
 */
public interface DataMsgYqDao extends DefaultRepository<DataMsgYq, String> {
	List<DataMsgYq> findByTranDateGreaterThanEqualAndTranDateLessThanEqualAndMsgStatEqualsOrderByTranDateDesc(String beginTranDate, String endTranDate, String msgStat);
	List<DataMsgYq> findByTranDateGreaterThanEqualAndTranDateLessThanEqualOrderByTranDateDesc(String beginTranDate, String endTranDate);
	List<DataMsgYq> findByTranDate(String tranDate);
}
