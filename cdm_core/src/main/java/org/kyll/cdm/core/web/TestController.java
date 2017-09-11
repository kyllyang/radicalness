package org.kyll.cdm.core.web;

import org.kyll.cdm.core.facade.EcdsMsgFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * User: Kyll
 * Date: 2017-08-03 22:16
 */
@Controller
@RequestMapping(value="/test")
public class TestController {
	@Autowired
	private EcdsMsgFacade ecdsMsgFacade;

	@RequestMapping("/ecdsMsg")
	@ResponseBody
	public String home() {
		return null;
	}
}
