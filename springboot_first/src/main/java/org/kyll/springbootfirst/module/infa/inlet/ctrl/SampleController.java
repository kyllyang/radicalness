package org.kyll.springbootfirst.module.infa.inlet.ctrl;

import org.kyll.springbootfirst.module.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * User: Kyll
 * Date: 2017-07-29 21:17
 */
@Controller
@RequestMapping(value="/ctrl/module/sample")
public class SampleController {
	@Autowired
	private QueryService queryService;

	@RequestMapping("/home")
	@ResponseBody
	String home(@Value("${name}") String name) { ;
		return "Hello " + name + ", " + queryService.query();
	}
}
