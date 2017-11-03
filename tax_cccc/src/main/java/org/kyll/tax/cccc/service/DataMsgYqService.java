package org.kyll.tax.cccc.service;

import org.kyll.base.service.DefaultService;
import org.kyll.tax.cccc.dao.DataMsgYqDao;
import org.kyll.tax.cccc.domain.DataMsgYq;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: Kyll
 * Date: 2017-10-11 15:49
 */
@Service
@Transactional(readOnly = true)
public class DataMsgYqService extends DefaultService<DataMsgYq, String, DataMsgYqDao> {
	public List<DataMsgYq> findList() {
	//	return dao.findByTranDateGreaterThanEqualAndTranDateLessThanEqualAndMsgStatEqualsOrderByTranDateDesc("20171013", "20171013", "2");
		return dao.findByTranDate("20171030");
	}
}
