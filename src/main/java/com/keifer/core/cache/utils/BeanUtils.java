package com.keifer.core.cache.utils;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.BeanMap;

import com.google.common.collect.Maps;

/**
 * 对Bean进行操作的相关工具方法
 * 
 */
public class BeanUtils {
	/**
	 * 将Bean对象转换成Map对象，将忽略掉值为null或size=0的属性
	 * 
	 * @param obj
	 *            对象
	 * @return 若给定对象为null则返回size=0的map对象
	 */
	public static Map<String, Object> toMap(Object obj) {
		Map<String, Object> map = Maps.newHashMap();
		if (obj == null) {
			return map;
		}
		BeanMap beanMap = new BeanMap(obj);
		Iterator<String> it = beanMap.keyIterator();
		while (it.hasNext()) {
			String name = it.next();
			Object value = beanMap.get(name);
			// 转换时会将类名也转换成属性，此处去掉
			if (value != null && !name.equals("class")) {
				map.put(name, value);
			}
		}
		return map;
	}

	public static Map<String, String> toStringMap(Object obj) {
		Map<String, String> map = Maps.newHashMap();
		if (obj == null) {
			return map;
		}
		BeanMap beanMap = new BeanMap(obj);
		Iterator<String> it = beanMap.keyIterator();
		while (it.hasNext()) {
			String name = it.next();
			Object value = beanMap.get(name);
			// 转换时会将类名也转换成属性，此处去掉
			if (value != null && !name.equals("class")) {
				map.put(name, String.valueOf(value));
			}
		}
		return map;
	}

	/**
	 * 
	 * @param obj
	 *            需要转换成Map的对象
	 * @param keyPrefix
	 *            转换成Map的key值的前缀：前缀的值 + 字段名称
	 * @return
	 */
	public static Map<String, String> toStringMap(Object obj, String keyPrefix) {
		Map<String, String> map = Maps.newHashMap();
		if (obj == null) {
			return map;
		}
		BeanMap beanMap = new BeanMap(obj);
		Iterator<String> it = beanMap.keyIterator();
		while (it.hasNext()) {
			String name = it.next();
			Object value = beanMap.get(name);
			// 转换时会将类名也转换成属性，此处去掉
			if (value != null && !name.equals("class")) {
				map.put(keyPrefix + name, String.valueOf(value));
			}
		}
		return map;
	}

	/**
	 * 该方法将给定的所有对象参数列表转换合并生成一个Map，对于同名属性，依次由后面替换前面的对象属性
	 * 
	 * @param objs
	 *            对象列表
	 * @return 对于值为null的对象将忽略掉
	 */
	public static Map<String, Object> toMap(Object... objs) {
		Map<String, Object> map = Maps.newHashMap();
		for (Object object : objs) {
			if (object != null) {
				map.putAll(toMap(object));
			}
		}
		return map;
	}

	/**
	 * 获取接口的泛型类型，如果不存在则返回null
	 * 
	 * @param clazz
	 * @return
	 */
	public static Class<?> getGenericClass(Class<?> clazz) {
		Type t = clazz.getGenericSuperclass();
		if (t instanceof ParameterizedType) {
			Type[] p = ((ParameterizedType) t).getActualTypeArguments();
			return ((Class<?>) p[0]);
		}
		return null;
	}
}