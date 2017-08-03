package org.kyll.cdm.core.dao;

import org.kyll.base.persistence.impl.JdbcTemplateDao;
import org.kyll.cdm.core.entity.EcdsMsgRule;
import org.springframework.stereotype.Repository;

/**
 * User: Kyll
 * Date: 2017-08-02 15:58
 */
@Repository
public class EcdsMsgRuleDao extends JdbcTemplateDao<EcdsMsgRule, Long> {
}
