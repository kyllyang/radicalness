package org.kyll.tax.orderguarantee.dao;

import org.kyll.base.repository.DefaultRepository;
import org.kyll.tax.orderguarantee.domain.ComAccMsg;

import java.util.List;

/**
 * User: Kyll
 * Date: 2017-09-07 14:51
 */
public interface ComAccMsgDao extends DefaultRepository<ComAccMsg, String> {
	List<ComAccMsg> findByAccNameLike(String accName);
}
