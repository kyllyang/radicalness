package org.kyll.tax.compiler.service;

import lombok.extern.slf4j.Slf4j;
import org.kyll.common.util.StringUtil;
import org.kyll.tax.compiler.common.Config;
import org.kyll.tax.compiler.domain.SvnRow;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Kyll
 * Date: 2017-09-15 14:25
 */
@Slf4j
@Component
public class SvnRowService {
	public List<SvnRow> readSvnRowList() {
		Config.CMD_SVN_STATUS[2] = Config.PROJECT_PATH;

		List<String> rowList = new ArrayList<>();
		Process process = null;
		try {
			process = Runtime.getRuntime().exec(Config.CMD_SVN_STATUS);
		} catch (IOException e) {
			log.error("SVN 命令执行异常", e);
			System.exit(1);
		}

		try (BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
			String line;
			while ((line = in.readLine()) != null) {
				log.info(line);
				rowList.add(line);
			}
		} catch (IOException e) {
			log.error("SVN 命令执行异常", e);
			System.exit(1);
		}

		List<SvnRow> svnRowList = new ArrayList<>();
		for (String row : rowList) {
			SvnRow svnRow = new SvnRow();
			svnRow.setType(StringUtil.getFirstChar(row.trim()));
			svnRow.setPath(row.substring(8));
			svnRowList.add(svnRow);
		}

		try {
			if (0 == process.waitFor()) {
				log.info("SVN 命令执行成功");
			} else {
				log.info("SVN 命令执行失败");
			}
		} catch (InterruptedException e) {
			log.error("SVN 命令执行异常", e);
		}

		return svnRowList;
	}
}
