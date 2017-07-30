package org.kyll.springbootfirst.module.service;

import org.kyll.base.service.impl.JdbcTemplateService;
import org.kyll.springbootfirst.module.condition.UserCondition;
import org.kyll.springbootfirst.module.dao.UserDao;
import org.kyll.springbootfirst.module.entity.User;
import org.springframework.stereotype.Service;

/**
 * User: Kyll
 * Date: 2017-07-30 03:10
 */
@Service
public class UserService extends JdbcTemplateService<Long, User, UserCondition, UserDao> {
}
