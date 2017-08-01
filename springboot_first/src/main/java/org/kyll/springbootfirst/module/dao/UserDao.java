package org.kyll.springbootfirst.module.dao;

import org.kyll.base.persistence.impl.JdbcTemplateDao;
import org.kyll.springbootfirst.module.entity.User;
import org.springframework.stereotype.Repository;

/**
 * User: Kyll
 * Date: 2017-07-30 03:08
 */
@Repository
public class UserDao extends JdbcTemplateDao<User, Long> {
}
