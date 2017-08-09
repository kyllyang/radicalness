package org.kyll.common.util;

/**
 * User: Kyll
 * Date: 2017-07-31 15:20
 */
public class JsonUtil {
	public static <T> T parse(String json, Class<T> clazz) {
		return null;
	}

	public static <C, E> C parse(String json, Class<C> collectionClass, Class<E> elementClass) {
		return null;
	}

	public static String format(Object object) {
		if (object == null) {
			return null;
		} else if (object instanceof String) {
			return object.toString();
		} else {
			return null;
		}
	}
}
