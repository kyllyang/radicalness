package org.kyll.tax.compiler.service;

import lombok.extern.slf4j.Slf4j;
import org.kyll.tax.compiler.common.Config;
import org.kyll.tax.compiler.common.Oper;
import org.kyll.tax.compiler.domain.OperFile;
import org.kyll.tax.compiler.domain.SvnRow;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Kyll
 * Date: 2017-09-15 14:55
 */
@Slf4j
@Component
public class OperFileService {
	public List<OperFile> convert(List<SvnRow> svnRowList) {
		List<OperFile> operFileList = new ArrayList<>();
		for (SvnRow svnRow : svnRowList) {
			String path = svnRow.getPath();
			File file = new File(Config.PROJECT_PATH + path);
			if (file.isDirectory()) {
				if ("A".equals(svnRow.getType())) {
					OperFile operFile = new OperFile();
					operFile.setOper(Oper.MKDIR);
					if (path.contains("\\src\\webcontent\\") || path.contains("/src/webcontent/")) {
						operFile.setTargetPath(Config.WEB_TARGET_PATH + path.substring(indexOf(path, "\\src\\webcontent\\") + 16));
					} else {
						operFile.setTargetPath(Config.CLASS_TARGET_PATH + path.substring(indexOf(path, "\\src\\") + 5) + ";" + Config.BIZ_TARGET_PATH + path.substring(path.indexOf("\\src\\") + 5));
					}
					operFileList.add(operFile);
				}
			} else {
				if (path.endsWith(".java")) {
					OperFile operFile = new OperFile();
					operFile.setOper("D".equals(svnRow.getType()) ? Oper.DELETE : Oper.COPY);

					String classPath = path.substring(indexOf(path, "\\src\\") + 5).replace(".java", ".class");
					operFile.setPath(Config.CLASS_SOURCE_PATH + classPath);
					operFile.setTargetPath(Config.CLASS_TARGET_PATH + classPath);

					operFileList.add(operFile);
				} else if (path.endsWith(".bizx") || path.endsWith(".compositex")) {
					OperFile operFile = new OperFile();
					operFile.setOper("D".equals(svnRow.getType()) ? Oper.DELETE : Oper.COPY);

					String filePath = path.substring(indexOf(path, "\\src\\") + 5);
					operFile.setPath(Config.CLASS_SOURCE_PATH + filePath);
					operFile.setTargetPath(Config.BIZ_TARGET_PATH + filePath.substring(0, filePath.length() - 1));

					operFileList.add(operFile);
				} else if (path.endsWith(".jsp") || path.endsWith(".png") || path.endsWith(".jpg") || path.endsWith(".gif") || path.endsWith(".doc") || path.endsWith(".docx") || path.endsWith(".exe")) {
					OperFile operFile = new OperFile();
					operFile.setOper("D".equals(svnRow.getType()) ? Oper.DELETE : Oper.COPY);

					String jspPath = path.substring(indexOf(path, "\\src\\webcontent\\") + 16);
					operFile.setPath(Config.WEB_SOURCE_PATH + jspPath);
					operFile.setTargetPath(Config.WEB_TARGET_PATH + jspPath);

					operFileList.add(operFile);
				} else {
					log.warn("忽略：" + path);
				}
			}
		}
		return operFileList;
	}

	private int indexOf(String fullStr, String str) {
		int result = fullStr.indexOf(str);
		if (result == -1) {
			result = fullStr.indexOf(str.replace("\\", "/"));
		}
		return result;
	}
}
