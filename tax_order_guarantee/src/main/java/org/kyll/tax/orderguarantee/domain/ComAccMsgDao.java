package org.kyll.tax.orderguarantee.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * User: Kyll
 * Date: 2017-09-07 14:51
 */
public interface ComAccMsgDao extends JpaRepository<ComAccMsg, String> {
	List<ComAccMsg> findByAccNameLike(String accName);
}
