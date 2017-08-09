package org.kyll.cdm.core.ui.ctrl;

import org.kyll.cdm.core.entity.Draft;
import org.kyll.cdm.core.service.DraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * User: Kyll
 * Date: 2017-08-09 22:17
 */
@Controller
@RequestMapping(value="/ctrl/draft")
public class DraftController {
	@Autowired
	private DraftService draftService;

	@RequestMapping("/all")
	@ResponseBody
	List<Draft> all() { ;
		return draftService.findAll();
	}
}
