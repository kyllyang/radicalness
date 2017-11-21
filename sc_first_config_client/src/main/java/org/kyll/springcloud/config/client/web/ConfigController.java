package org.kyll.springcloud.config.client.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: Kyll
 * Date: 2017-11-21 11:36
 */
@RestController
public class ConfigController {
	@Value("${hero}")
	private String hero;

	@RequestMapping("/hero")
	public String view() {
		return "The hero is: " + hero;
	}
}
