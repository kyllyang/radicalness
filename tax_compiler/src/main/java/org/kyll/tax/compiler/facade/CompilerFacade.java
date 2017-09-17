package org.kyll.tax.compiler.facade;

import lombok.extern.slf4j.Slf4j;
import org.kyll.tax.compiler.service.CommandService;
import org.kyll.tax.compiler.service.JarService;
import org.kyll.tax.compiler.service.OperFileService;
import org.kyll.tax.compiler.service.SvnRowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * User: Kyll
 * Date: 2017-09-14 08:51
 */
@Slf4j
@Component
public class CompilerFacade {
	@Autowired
	private SvnRowService svnRowService;
	@Autowired
	private OperFileService operFileService;
	@Autowired
	private CommandService commandService;
	@Autowired
	private JarService jarService;

	public void execute() {
		commandService.execute(operFileService.convert(svnRowService.readSvnRowList()));

		jarService.jarWar();
		jarService.jarEar();
	}
}
