package org.kyll.tax.compiler.facade;

import lombok.extern.slf4j.Slf4j;
import org.kyll.tax.compiler.service.CommandService;
import org.kyll.tax.compiler.service.DeployService;
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
	@Autowired
	private DeployService deployService;

	public void execute() {
	//	commandService.execute(operFileService.convert(svnRowService.readSvnStatusList()));
		commandService.execute(operFileService.convert(svnRowService.readSvnLogList("45104")));

		jarService.jarWar();
		jarService.jarEar();
	//	deployService.execute();
	}
}
