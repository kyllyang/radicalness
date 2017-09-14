package org.kyll.tax.orderguarantee.service;

import org.kyll.base.service.DefaultService;
import org.kyll.tax.orderguarantee.dao.OpenBillMsgDao;
import org.kyll.tax.orderguarantee.domain.OpenBillMsg;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: Kyll
 * Date: 2017-09-13 15:31
 */
@Service
@Transactional(readOnly = true)
public class OpenBillMsgService extends DefaultService<OpenBillMsg, String, OpenBillMsgDao> {
}
