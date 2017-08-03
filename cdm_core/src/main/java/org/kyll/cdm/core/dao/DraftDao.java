package org.kyll.cdm.core.dao;

import org.kyll.base.persistence.impl.JdbcTemplateDao;
import org.kyll.cdm.core.entity.Draft;
import org.springframework.stereotype.Repository;

/**
 * User: Kyll
 * Date: 2017-08-02 11:00
 */
@Repository
public class DraftDao extends JdbcTemplateDao<Draft, Long> {
}
