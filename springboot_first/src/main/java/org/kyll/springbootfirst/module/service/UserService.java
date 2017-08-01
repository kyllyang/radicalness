package org.kyll.springbootfirst.module.service;

import org.kyll.base.service.DefaultService;
import org.kyll.springbootfirst.module.dao.UserDao;
import org.kyll.springbootfirst.module.entity.User;
import org.springframework.stereotype.Service;

/**
 * User: Kyll
 * Date: 2017-07-30 03:10
 */
@Service
public class UserService extends DefaultService<User, Long, UserDao> {
}
