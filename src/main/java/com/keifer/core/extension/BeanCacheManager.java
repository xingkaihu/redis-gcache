package com.keifer.core.extension;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

/**
 * bean缓存的管理器
 * 
 * @author keifer
 *
 */
public class BeanCacheManager {

	public static Map<String, Object> beanCache = Maps.newConcurrentMap();

	/**
	 * 存储缓存中的对象
	 * 
	 * @param beanId
	 *            缓存中的key,每个bean的beanId是唯一的.如果相同,最后添加的将覆盖之前存储的bean
	 * @param bean
	 *            需要缓存的Bean
	 */
	public static void putBean(String beanId, Object bean) {
		Preconditions.checkArgument(null != bean, "缓存的对象不能是null");
		beanCache.put(StringUtils.isBlank(beanId) ? bean.getClass().getName() : StringUtils.trim(beanId), bean);
	}

	/**
	 * 获取缓存中的对象
	 * 
	 * @param beanId
	 *            缓存中的key,每个bean的beanId是唯一的
	 * @return 获取的Object类型的对象,需要强制转换类型
	 */
	public static Object getBean(String beanId) {
		Preconditions.checkArgument(StringUtils.isNotBlank(beanId), "参数不能是null或空字符串");
		return beanCache.get(StringUtils.trim(beanId));
	}

	/**
	 * 
	 * 获取缓存中的对象
	 * 
	 * @param clazz
	 *            获取对象的class对象
	 * @param beanId
	 *            缓存中的key,每个bean的beanId是唯一的
	 * @return 返回Class类型的实例对象
	 */
	public static <T> T getBean(Class<T> clazz, String beanId) {
		Preconditions.checkArgument(null != clazz, "参数clazz不能是null");
		beanId = StringUtils.isBlank(beanId) ? clazz.getName() : StringUtils.trim(beanId);
		return clazz.cast(getBean(beanId));
	}

	/**
	 * 删除缓存中的对象
	 * 
	 * @param beanId
	 *            缓存中的key,每个bean的beanId是唯一的
	 */
	public static void removeBean(String beanId) {
		Preconditions.checkArgument(StringUtils.isNotBlank(beanId), "参数不能是null或空字符串");
		beanCache.remove(beanId);
	}

	/**
	 * 存储缓存中的对象：没有传递beanId,默认是类的全名
	 * 
	 * @param bean
	 *            需要缓存的Bean
	 */
	public static void putBean(Object bean) {
		Preconditions.checkArgument(null != bean, "参数bean不能是null");
		putBean(bean.getClass().getName(), bean);
	}

	/**
	 * 
	 * 获取缓存中的对象：没有传递beanId,默认是类的全名
	 * 
	 * @param clazz
	 *            获取对象的class对象
	 * @return 返回Class类型的实例对象
	 */
	public static <T> T getBean(Class<T> clazz) {
		Preconditions.checkArgument(null != clazz, "参数clazz不能是null");
		return getBean(clazz, clazz.getName());
	}

	/**
	 * 删除缓存中的对象：没有传递beanId,默认是类的全名
	 */
	public static void removeBean(Class<?> clazz) {
		Preconditions.checkArgument(null != clazz, "参数clazz不能是null");
		beanCache.remove(clazz.getName());
	}

	/**
	 * 清除缓存中所有的存储
	 */
	public static void clearCache() {
		beanCache.clear();
	}
}
