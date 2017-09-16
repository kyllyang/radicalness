package org.kyll.tax.compiler.service;

import lombok.extern.slf4j.Slf4j;
import org.kyll.tax.compiler.common.Oper;
import org.kyll.tax.compiler.domain.OperFile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User: Kyll
 * Date: 2017-09-15 16:10
 */
@Slf4j
@Component
public class CommandService {
	public void execute(List<OperFile> operFileList) {
		mkdir(operFileList.stream().filter(operFile -> Oper.MKDIR == operFile.getOper()).collect(Collectors.toList()));
		copy(operFileList.stream().filter(operFile -> Oper.COPY == operFile.getOper()).collect(Collectors.toList()));
		delete(operFileList.stream().filter(operFile -> Oper.DELETE == operFile.getOper()).collect(Collectors.toList()));
	}

	private void mkdir(List<OperFile> operFileList) {
		for (OperFile operFile : operFileList) {
			File file = new File(operFile.getPath());
			if (file.exists()) {
				log.info("目录已存在 " + file);
			} else {
				if (file.mkdirs()) {
					log.info("建立目录 " + file);
				} else {
					log.error("建立目录失败 " + file);
				}
			}
		}
	}

	private void copy(List<OperFile> operFileList) {
		for (OperFile operFile : operFileList) {
			File file = new File(operFile.getPath());
			log.info("复制文件 " + file);
		}
	}

	private void delete(List<OperFile> operFileList) {
		for (OperFile operFile : operFileList) {
			File file = new File(operFile.getPath());
			if (file.delete()) {
				log.info("删除文件 " + file);
			} else {
				log.error("删除文件失败 " + file);
			}
		}
	}
}
