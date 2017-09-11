package org.kyll.cdm.core.dao;

import org.kyll.base.repository.DefaultRepository;
import org.kyll.cdm.core.entity.Draft;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User: Kyll
 * Date: 2017-08-02 11:00
 */
public interface DraftDao extends DefaultRepository<Draft, Long> {
}
