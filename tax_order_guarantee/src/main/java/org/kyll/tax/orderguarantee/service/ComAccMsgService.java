package org.kyll.tax.orderguarantee.service;

import org.kyll.base.service.DefaultService;
import org.kyll.tax.orderguarantee.domain.ComAccMsg;
import org.kyll.tax.orderguarantee.domain.ComAccMsgDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: Kyll
 * Date: 2017-09-07 14:52
 */
@Service
@Transactional(readOnly = true)
public class ComAccMsgService extends DefaultService<ComAccMsg, String, ComAccMsgDao> {
	public List<ComAccMsg> findListByAccName(String accName) {
		return dao.findByAccNameLike(accName);
	}
}
