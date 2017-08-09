package org.kyll.cdm.core.dao;

import org.kyll.cdm.core.entity.Draft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * User: Kyll
 * Date: 2017-08-02 11:00
 */
@Repository
public interface DraftDao extends JpaRepository<Draft, Long> {
}
