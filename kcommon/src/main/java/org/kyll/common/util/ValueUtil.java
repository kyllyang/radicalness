package org.kyll.common.util;

/**
 * User: Kyll
 * Date: 2017-08-03 20:30
 */
public class ValueUtil {
	public static String toString(Exception e) {
		StringBuilder sb = new StringBuilder(e.getMessage() + "\r\n");
		sb.append(e.getCause()).append("\r\n");
		for (StackTraceElement ste : e.getStackTrace()) {
			sb.append("at ").append(ste.getClassName()).append(".").append(ste.getMethodName()).append("(").append(ste.getFileName()).append(":").append(ste.getLineNumber()).append(")\r\n");
		}
		return sb.toString();
	}
}
