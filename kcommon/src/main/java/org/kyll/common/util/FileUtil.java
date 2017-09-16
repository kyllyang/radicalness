package org.kyll.common.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Kyll
 * Date: 2017-09-15 16:34
 */
public class FileUtil {
	public static List<File> getParentsWithSelf(File file) {
		List<File> fileList = new ArrayList<>();
		fileList.add(0, file);

		File parentFile = file.getParentFile();
		if (parentFile != null) {
			fileList.addAll(0, getParentsWithSelf(parentFile));
		}

		return fileList;
	}
}
