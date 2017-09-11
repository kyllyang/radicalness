package org.kyll.springbootfirst.module.service;

import org.kyll.base.service.AbstractService;
import org.kyll.springbootfirst.module.dao.UserDao;
import org.kyll.springbootfirst.module.entity.User;

/**
 * User: Kyll
 * Date: 2017-07-30 03:10
 */
@org.springframework.stereotype.Service
public class UserService extends AbstractService<User, Long, UserDao> {
}
