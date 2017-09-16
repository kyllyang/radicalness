package org.kyll.tax.compiler.common;

/**
 * User: Kyll
 * Date: 2017-09-15 15:44
 */
public class Config {
	public static final String PROJECT_PATH = "C:\\Users\\Administrator\\IdeaProjects\\tax\\";
	public static final String CLASS_SOURCE_PATH = PROJECT_PATH + "out\\production\\GLTax\\";
	public static final String WEB_SOURCE_PATH = PROJECT_PATH + "GLTax\\com.gl.tax\\src\\webcontent\\";
	public static final String TARGET_PATH = "C:\\Users\\Administrator\\work\\temp\\tax\\";
	public static final String CLASS_TARGET_PATH = TARGET_PATH + "war\\WEB-INF\\classes\\";
	public static final String WEB_TARGET_PATH = TARGET_PATH + "war\\tax\\";

	public static final String[] CMD_SVN_STATUS = {"svn", "status", null};
}
