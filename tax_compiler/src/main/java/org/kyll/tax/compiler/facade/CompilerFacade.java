package org.kyll.tax.compiler.facade;

import lombok.extern.slf4j.Slf4j;
import org.kyll.tax.compiler.common.Config;
import org.kyll.tax.compiler.domain.OperFile;
import org.kyll.tax.compiler.domain.SvnRow;
import org.kyll.tax.compiler.service.CommandService;
import org.kyll.tax.compiler.service.OperFileService;
import org.kyll.tax.compiler.service.SvnRowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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

	public void execute() {
		List<SvnRow> svnRowList = svnRowService.readSvnRowList(Config.PROJECT_PATH);
		List<OperFile> operFileList = operFileService.convert(svnRowList);
		commandService.execute(operFileList);
	}
}
