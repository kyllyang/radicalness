package org.kyll.cdm.core.service;

import org.kyll.cdm.core.entity.ecdsmsg.EcdsMsg;
import org.kyll.cdm.core.entity.ecdsmsg.EcdsMsgResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: Kyll
 * Date: 2017-08-02 13:52
 */
@Service
@Transactional(readOnly = true)
public class EcdsMsgService {

	public void save(EcdsMsg ecdsMsg) {

	}

	public void save(EcdsMsgResult ecdsMsgResult) {

	}
}
