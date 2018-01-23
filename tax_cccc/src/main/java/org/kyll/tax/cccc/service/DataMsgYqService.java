package org.kyll.tax.cccc.service;

import org.kyll.base.service.DefaultService;
import org.kyll.tax.cccc.dao.DataMsgYqDao;
import org.kyll.tax.cccc.domain.DataMsgYq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * User: Kyll
 * Date: 2017-10-11 15:49
 */
@Service
@Transactional(readOnly = true)
public class DataMsgYqService extends DefaultService<DataMsgYq, String, DataMsgYqDao> {
	@Autowired
	private EntityManager entityManager;

	public List<DataMsgYq> findList() {
	//	return dao.findByTranDateGreaterThanEqualAndTranDateLessThanEqualAndMsgStatEqualsOrderByTranDateDesc("20171013", "20171013", "2");
		return dao.findByTranDate("20171030");
	}

	public List<Object[]> query(String sql) {
		List list = entityManager.createNativeQuery(sql).getResultList();
		return list;
	}
}
