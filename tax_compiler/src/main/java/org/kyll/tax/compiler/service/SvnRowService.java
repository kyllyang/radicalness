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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * User: Kyll
 * Date: 2017-09-15 14:25
 */
@Slf4j
@Component
public class SvnRowService {
	public List<SvnRow> readSvnStatusList() {
		List<SvnRow> svnRowList = new ArrayList<>();
		for (String row : executeSvnCmd(Config.CMD_SVN_STATUS)) {
			SvnRow svnRow = new SvnRow();
			svnRow.setType(StringUtil.getFirstChar(row.trim()));
			svnRow.setPath(row.substring(8));
			svnRowList.add(svnRow);
		}

		return svnRowList;
	}

	public List<SvnRow> readSvnLogList(String... versions) {
		Set<String> rowSet = new LinkedHashSet<>();
		for (String version : versions) {
			Config.CMD_SVN_LOG[4] = version;

			List<String> rowList = executeSvnCmd(Config.CMD_SVN_LOG);
			for (int i = 3, size = rowList.size(); i < size; i++) {
				String row = rowList.get(i);
				if (StringUtil.isNotBlank(row)) {
					rowSet.add(row);
				} else {
					break;
				}
			}
		}

		List<SvnRow> svnRowList = new ArrayList<>();
		for (String row : rowSet) {
			SvnRow svnRow = new SvnRow();
			svnRow.setType(StringUtil.getFirstChar(row.trim()));
			svnRow.setPath(row.substring(22));
			svnRowList.add(svnRow);
		}

		return svnRowList;
	}


	private List<String> executeSvnCmd(String[] cmds) {
		List<String> rowList = new ArrayList<>();
		Process process = null;
		try {
			process = Runtime.getRuntime().exec(cmds);
		} catch (IOException e) {
			log.error("SVN 命令执行异常", e);
			System.exit(1);
		}

		try (BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"))) {
			String line;
			while ((line = in.readLine()) != null) {
				log.info(line);
				rowList.add(line);
			}
		} catch (IOException e) {
			log.error("SVN 命令执行异常", e);
			System.exit(1);
		}

		try {
			if (0 == process.waitFor()) {
				log.info("SVN 命令执行成功");
			} else {
				log.info("SVN 命令执行失败");
			}
		} catch (InterruptedException e) {
			log.error("SVN 命令执行异常", e);
			System.exit(1);
		}

		return rowList;
	}
}
