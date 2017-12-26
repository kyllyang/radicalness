package org.kyll.tax.compiler.common;

/**
 * User: Kyll
 * Date: 2017-09-15 15:44
 */
public class Config {
	public static final String PROJECT_PATH = "C:\\Users\\Administrator\\IdeaProjects\\tax\\";
	public static final String CLASS_SOURCE_PATH = PROJECT_PATH + "out\\production\\GLTax\\";
	public static final String WEB_SOURCE_PATH = PROJECT_PATH + "GLTax\\com.gl.tax\\src\\webcontent\\";
	public static final String TARGET_PATH = "C:\\Users\\Administrator\\work\\temp\\tax\\deploy\\defaultEAR\\default.war\\";
	public static final String CLASS_TARGET_PATH = TARGET_PATH + "WEB-INF\\classes\\";
	public static final String WEB_TARGET_PATH = TARGET_PATH + "tax\\";
	public static final String BIZ_TARGET_PATH = TARGET_PATH + "WEB-INF\\_srv\\work\\user\\com.gl.tax\\";
	public static final String EAR_PATH = "C:\\Users\\Administrator\\work\\temp\\tax\\ear";

	public static final String[] CMD_SVN_STATUS = {"svn", "status", PROJECT_PATH};
	public static final String[] CMD_SVN_LOG = {"svn", "log", "-v", "-r", null, PROJECT_PATH};
	public static final String[] CMD_DEPLOY = {"java", "-cp", "C:\\Users\\Administrator\\server\\Oracle\\Middleware\\Oracle_Home\\wlserver\\server\\lib\\weblogic.jar", "weblogic.Deployer", "-adminurl", "iiop://127.0.0.1:7001", "-username", "weblogic", "-password", "12345678", "-redeploy", "-name", "defaultEAR"};
	public static final String[] CMD_JAR_WAR = {"C:\\Program Files\\Java\\jdk1.6.0_18\\bin\\jar", "cvf", "..\\..\\..\\ear\\default.war", "*"};
	public static final String[] CMD_JAR_EAR = {"C:\\Program Files\\Java\\jdk1.6.0_18\\bin\\jar", "cvf", "..\\default0825cs.ear", "*"};
}
