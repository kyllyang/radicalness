package org.kyll.tax.compiler.service;

import lombok.extern.slf4j.Slf4j;
import org.kyll.tax.compiler.common.Oper;
import org.kyll.tax.compiler.domain.OperFile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
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
			File file = new File(operFile.getTargetPath());
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
			String path = operFile.getPath();
			String targetPath = operFile.getTargetPath();

			log.info("复制文件 " + path + " ->" + targetPath);
			if (path.endsWith(".bizx")) {

			} else {
				File targetFile = new File(targetPath);
				if (!targetFile.exists()) {
					try {
						if (targetFile.createNewFile()) {
							log.info("文件建立 " + targetFile);
						} else {
							log.info("文件建立失败 " + targetFile);
						}
					} catch (IOException e) {
						log.error("文件建立异常", e);
					}
				}

				try (FileChannel sourceFileChannel = new FileInputStream(new File(path)).getChannel();
				     FileChannel targetFileChannel = new FileOutputStream(targetFile).getChannel()) {
					targetFileChannel.write(sourceFileChannel.map(FileChannel.MapMode.READ_ONLY, 0, sourceFileChannel.size()));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void delete(List<OperFile> operFileList) {
		for (OperFile operFile : operFileList) {
			File file = new File(operFile.getTargetPath());
			if (file.delete()) {
				log.info("删除文件 " + file);
			} else {
				log.error("删除文件失败 " + file);
			}
		}
	}
}
