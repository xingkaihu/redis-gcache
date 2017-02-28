package com.keifer.core.cache.utils;

import org.apache.commons.lang3.StringUtils;

import com.keifer.core.cache.module.support.ConfigLoaderSupport;

public class ConfigUtils {

	public static String getValue(String key) {
		return ConfigLoaderSupport.getProperty(key);
	}

	/**
	 * class类型支持：String，Long，Integer，Boolean
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getValue(String key, Class<T> clazz) {
		if (clazz.isAssignableFrom(Integer.class)) {
			return (T) Integer.valueOf(ConfigLoaderSupport.getProperty(key));
		} else if (clazz.isAssignableFrom(Boolean.class)) {
			return (T) Boolean.valueOf(ConfigLoaderSupport.getProperty(key));
		} else if (clazz.isAssignableFrom(Long.class)) {
			return (T) Long.valueOf(ConfigLoaderSupport.getProperty(key));
		} else if (clazz.isAssignableFrom(String.class)) {
			return (T) getValue(key);
		}
		throw new IllegalArgumentException("不支持的转换类型：" + clazz);
	}

	public static String getValue(String key, String defaultValue) {
		String result = getValue(key);
		if (StringUtils.isBlank(result)) {
			return defaultValue;
		}
		return result;
	}
}
