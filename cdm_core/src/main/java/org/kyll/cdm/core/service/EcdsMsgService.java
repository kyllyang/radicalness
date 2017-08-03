package org.kyll.cdm.core.service;

import org.kyll.base.persistence.impl.SimpleJdbcTemplateDao;
import org.kyll.cdm.core.dao.SimpleDao;
import org.kyll.cdm.core.entity.ecdsmsg.EcdsMsg;
import org.kyll.cdm.core.entity.ecdsmsg.EcdsMsgResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * User: Kyll
 * Date: 2017-08-02 13:52
 */
@Service
public class EcdsMsgService {
	@Autowired
	private SimpleDao dao;

	public void save(EcdsMsg ecdsMsg) {

	}

	public void save(EcdsMsgResult ecdsMsgResult) {

	}
}
