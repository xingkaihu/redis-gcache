package com.keifer.core.cache.module.support;

import java.util.Map;
import java.util.Properties;

import com.google.common.collect.Maps;
import com.google.inject.Binder;
import com.google.inject.name.Names;

/**
 * 配置属性文件加载类
 * 
 * @author keifer
 *
 */
public abstract class ConfigLoaderSupport {

	static Map<String, String> configCache = Maps.newConcurrentMap();

	public static void loadProperties(Binder binder, Properties... properties) {
		for (Properties pro : properties) {
			Names.bindProperties(binder, pro);
			configCache.putAll(Maps.fromProperties(pro));
		}
	}

	public static void loadProperties(Binder binder, String... fileProperties) {
		for (String file : fileProperties) {
			Properties pro = new ClassPathResource(file).getProperties();
			Names.bindProperties(binder, pro);
			configCache.putAll(Maps.fromProperties(pro));
		}
	}

	public static Map<String, String> getConfigMap() {
		return configCache;
	}

	public static String getProperty(String key) {
		return getConfigMap().get(key);
	}
	
}
