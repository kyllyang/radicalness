package org.kyll.cdm.core.dao;

import org.kyll.base.persistence.impl.JdbcTemplateDao;
import org.kyll.cdm.core.entity.Participator;
import org.springframework.stereotype.Repository;

/**
 * User: Kyll
 * Date: 2017-08-03 14:39
 */
@Repository
public class ParticipatorDao extends JdbcTemplateDao<Participator, Long> {
}
