package org.kyll.cdm.core.dao;

import org.kyll.base.repository.DefaultRepository;
import org.kyll.cdm.core.entity.Participator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * User: Kyll
 * Date: 2017-08-03 14:39
 */
@Repository
public interface ParticipatorDao extends DefaultRepository<Participator, Long> {
}
