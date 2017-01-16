package com.wbximy.crawler.tools;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import org.apache.log4j.Logger;

public class GetterSetter {
	static Logger logger = Logger.getLogger(GetterSetter.class);

	/**
	 * java反射bean的get方法
	 * 
	 * @param claz
	 * @param fieldName属性名
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Method getGetter(Class claz, String fieldName) {
		StringBuilder sb = new StringBuilder();
		sb.append("get").append(fieldName.substring(0, 1).toUpperCase())
				.append(fieldName.substring(1));
		try {
			Class[] types = new Class[] {};
			return claz.getMethod(sb.toString(), types);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * java反射bean的set方法
	 * 
	 * @param claz
	 * @param fieldName属性名
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Method getSetter(Class claz, String fieldName) {
		try {
			Class[] parameterTypes = new Class[1];
			Field field = claz.getDeclaredField(fieldName);
			parameterTypes[0] = field.getType();// 返回参数类型
			StringBuilder sb = new StringBuilder();
			sb.append("set").append(fieldName.substring(0, 1).toUpperCase())
					.append(fieldName.substring(1));
			Method method = claz.getMethod(sb.toString(), parameterTypes);
			return method;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 执行set方法
	 * 
	 * @param t执行对象
	 * @param fieldName属性名
	 * @param value值
	 */

	public static <T> void invokeSetter(T t, String fieldName, Object value) {
		// logger.info(t);
		Method method = getSetter(t.getClass(), fieldName);
		try {
			method.invoke(t, new Object[] { value });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 执行get方法
	 * 
	 * @param t执行对象
	 * @param fieldName属性名
	 */
	public static <T> Object invokeGetter(T t, String fieldName) {
		Method method = getGetter(t.getClass(), fieldName);
		try {
			return method.invoke(t, new Object[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static <T> T updateField(T obj, String key, Object value) {
		Field[] fields = obj.getClass().getDeclaredFields();
		
		for (Field field : fields) {
			String fieldname = field.getName();
			if (!fieldname.equals(key)) continue;
			TypeConverter.cvt(obj, field, value);
		}
		return obj;
	}
	
	public static <T> T updateField(T obj, Map<String, Object> dataMap) {
		Field[] fields = obj.getClass().getDeclaredFields();

		for (Field field : fields) {
			String fieldname = field.getName();
			if (!dataMap.containsKey(fieldname))
				continue;
			TypeConverter.cvt(obj, field, dataMap.get(fieldname));
		}
		return obj;
	}

}
