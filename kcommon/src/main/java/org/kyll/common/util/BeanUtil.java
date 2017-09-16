package org.kyll.common.util;

import org.kyll.common.Const;
import org.kyll.common.exception.SystemException;
import org.springframework.cglib.beans.BeanCopier;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Kyll
 * Date: 2017-07-31 11:37
 */
public class BeanUtil {
	/**
	 * 复制对象属性。
	 * @param dest 目标对象
	 * @param orig 源对象
	 */
	public static void copyProperties(Object orig, Object dest) {
		String beanKey = dest.getClass().getName() + Const.SYMBOL_UNDERLINE + orig.getClass().getName();

		BeanCopier copier = BEAN_COPIER_MAP.get(beanKey);
		if (copier == null) {
			copier = BeanCopier.create(orig.getClass(), dest.getClass(), false);
			BEAN_COPIER_MAP.put(beanKey, copier);
		}

		copier.copy(orig, dest, null);
	}

	/**
	 * 实例化一个对象。传入 null 值，返回 null 值。
	 * @param className 类的完全限定名
	 * @return 实例化的对象
	 */
	public static Object newInstance(String className) {
		Object object = null;
		if (StringUtil.isNotBlank(className)) {
			try {
				object = newInstance(Class.forName(className));
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(new SystemException(String.format(Const.EC_MSG_CLASSNOTFOUND, className), e));
			}
		}
		return object;
	}

	/**
	 * 实例化一个对象。传入 null 值，返回 null 值。
	 * @param clazz 类
	 * @param <T> 泛型
	 * @return 实例化的对象
	 */
	public static <T> T newInstance(Class<T> clazz) {
		T t = null;
		if (clazz != null) {
			try {
				t = clazz.newInstance();
			} catch (InstantiationException e) {
				throw new RuntimeException(new SystemException(String.format(Const.EC_MSG_INSTANTIATION, clazz), e));
			} catch (IllegalAccessException e) {
				throw new RuntimeException(new SystemException(String.format(Const.EC_MSG_ILLEGALACCESS_CLASS, clazz), e));
			}
		}
		return t;
	}

	/**
	 * 根据属性名称获取类中的属性
	 * @param clazz 类
	 * @param fieldName 属性名称
	 * @return 属性类
	 */
	public static Field getFieldByName(Class clazz, String fieldName) {
		Field field;
		try {
			field = clazz.getField(fieldName);
		} catch (NoSuchFieldException e) {
			throw new RuntimeException(new SystemException(String.format(Const.EC_MSG_NOSUCHFIELD, clazz, fieldName), e));
		}
		return field;
	}

	public static Object invoke(Object object, Method method, Object... args) {
		Object result = null;
		try {
			result = method.invoke(object, args);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(new SystemException(String.format(Const.EC_MSG_ILLEGALACCESS_METHOD, object.getClass().toString(), method.getName()), e));
		} catch (InvocationTargetException e) {
			throw new RuntimeException(new SystemException(String.format(Const.EC_MSG_INVOCATIONTARGET, object.getClass().toString(), method.getName()), e));
		}
		return result;
	}

	/**
	 * 获取本类及其父类所有的属性名称列表
	 * @param clazz 类
	 * @return 属性名称列表
	 */
	public static List<String> getFieldNameList(Class clazz, Class suspendSuperClass) {
		List<String> list = new ArrayList<>();
		for (Field field : clazz.getDeclaredFields()) {
			list.add(field.getName());
		}

		if (suspendSuperClass != null) {
			Class superClass = clazz.getSuperclass();
			list.addAll(getFieldNameList(superClass, superClass == suspendSuperClass ? null : suspendSuperClass));
		}

		return list;
	}

	public static Class getGenericEntityClass(Object object) {
		ParameterizedType parameterizedType = (ParameterizedType) object.getClass().getGenericSuperclass();
		Type[] types = parameterizedType.getActualTypeArguments();
		return types == null ? null : (Class) types[0];
	}

	private static final Map<String, BeanCopier> BEAN_COPIER_MAP = new HashMap<String, BeanCopier>();
}
