package com.keifer.core.extension;

import com.google.common.base.Preconditions;
import com.keifer.core.cache.utils.LoggerUtil;

/**
 * 获取扩展点的工具类
 * 
 * @author keifer
 *
 */
public class ExtensionLoaderUtils {

	/**
	 * 有缓存：获取单实例的扩展实现类
	 */
	public static <T> T getExtensionSingleInstance(Class<T> clazz, String spiName, String defaultName) {
		T t = BeanCacheManager.getBean(clazz, spiName);
		if (null == t) {
			t = ExtensionLoader.getExtensionLoader(clazz).getExtension(spiName);
			if (null == t) {
				t = BeanCacheManager.getBean(clazz, defaultName);
				if (null == t) {
					LoggerUtil.getCacheLogger().warn(
							"expected extension instance is null with spiName ( {} ), to use defalut spiName ( {} )",
							spiName, defaultName);
					t = ExtensionLoader.getExtensionLoader(clazz).getExtension(defaultName);
					Preconditions.checkArgument(null != t, "Extension instance must not be null with class " + clazz);
					BeanCacheManager.putBean(defaultName, t);
				}
			} else {
				BeanCacheManager.putBean(spiName, t);
			}
		}
		return t;
	}

	/**
	 * 有缓存：获取单实例的扩展实现类
	 */
	public static <T> T getExtensionSingleInstance(Class<T> clazz, String spiName) {
		T t = BeanCacheManager.getBean(clazz, spiName);
		if (null == t) {
			t = ExtensionLoader.getExtensionLoader(clazz).getExtension(spiName);
			if (null == t) {
				Preconditions.checkArgument(null != t, "Extension instance must not be null with class " + clazz);
			} else {
				BeanCacheManager.putBean(spiName, t);
			}
		}
		return t;
	}

	/**
	 * 有缓存：获取单实例的扩展实现类
	 */
	public static <T> T getExtensionSingleInstance(Class<T> clazz, String spiName, String defaultName,
			boolean enableDefault) {
		if (enableDefault) {
			return getExtensionSingleInstance(clazz, spiName, defaultName);
		} else {
			return getExtensionSingleInstance(clazz, spiName);
		}
	}

	/**
	 * 有缓存：获取默认单实例的扩展实现类
	 */
	public static <T> T getDefaultExtensionSingleInstance(Class<T> clazz, String defaultName) {
		LoggerUtil.getCacheLogger().warn("expected extension instance is null, to use defalut spiName ( {} )",
				defaultName);
		T t = BeanCacheManager.getBean(clazz, defaultName);
		if (null == t) {
			t = ExtensionLoader.getExtensionLoader(clazz).getExtension(defaultName);
			if (null == t) {
				Preconditions.checkArgument(null != t, "default Extension instance is null with class " + clazz);
			} else {
				BeanCacheManager.putBean(defaultName, t);
			}
		}
		return t;
	}

	/**
	 * 无缓存：获取扩展实现类
	 */
	public static <T> T getExtensionInstance(Class<T> clazz, String spiName, String defaultName) {
		T t = ExtensionLoader.getExtensionLoader(clazz).getExtension(spiName);
		if (null == t) {
			LoggerUtil.getCacheLogger().warn(
					"expected extension instance is null with spi name ( {} ), to use defalut spiName ( {} )", spiName,
					defaultName);
			t = ExtensionLoader.getExtensionLoader(clazz).getExtension(defaultName);
			Preconditions.checkArgument(null != t, "Extension instance must not be null with class " + clazz);
		}
		return t;
	}

	public static <T> T getExtensionInstance(Class<T> clazz, String spiName) {
		T t = ExtensionLoader.getExtensionLoader(clazz).getExtension(spiName);
		if (null == t) {
			Preconditions.checkArgument(null != t, "Extension instance must not be null with class " + clazz);
		}
		return t;
	}
}
