package org.kyll.springbootfirst.module.infa.inlet.rest;

import org.kyll.springbootfirst.module.entity.User;
import org.kyll.springbootfirst.module.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: Kyll
 * Date: 2017-07-29 23:53
 */
@RestController
@RequestMapping(value="/rest/module/sample")
public class SampleRest {
	@Autowired
	private UserService userService;

	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public User getUser(@PathVariable Long id) {
		return userService.get(id);
	}
}
