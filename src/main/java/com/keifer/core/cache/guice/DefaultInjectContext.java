package com.keifer.core.cache.guice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.inject.Binding;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import com.keifer.core.cache.module.QModule;
import com.keifer.core.cache.utils.MqConstant.SpiName;
import com.keifer.core.extension.ExtensionLoaderUtils;

/**
 * Application context based on configurations provided by hfmbank application.
 */
public class DefaultInjectContext {

	private static Injector injector;

	public  static void init() {
		if (injector == null) {
			synchronized (DefaultInjectContext.class) {
				if (injector == null) {
					QModule qmodule = ExtensionLoaderUtils.getExtensionInstance(QModule.class, SpiName.GuiceConetxt.getN());
					injector = Guice.createInjector(qmodule);
				}
			}
		}
	}

	/**
	 * 获取guice容器中的bean
	 */
	public static <T> T getInstance(Class<T> clazz) {
		init();
		return injector.getInstance(clazz);
	}

	/**
	 * 获取guice容器中的provider
	 */
	public static <T> T getProvider(Class<T> providerType) {
		init();
		return injector.getProvider(providerType).get();
	}

	/**
	 * 获取guice容器中的bean
	 * 
	 * @param clazz
	 *            服务实例的class类型
	 * @param namedAnnotation
	 *            命名注解
	 * @return 服务对象
	 */
	public static <T> T getInstance(Class<T> clazz, String namedAnnotation) {
		init();
		return injector.getInstance(Key.get(clazz, Names.named(namedAnnotation)));
	}

	/**
	 * 获取guice容器类型为clazz的所有服务实例
	 * 
	 * @param clazz
	 *            服务实例的class类型
	 * @return 服务实例集合
	 */
	public static <T> Collection<T> getInstancesOfType(Class<T> clazz) {
		init();
		List<Binding<T>> list = injector.findBindingsByType(new TypeLiteral<T>() {
		});
		List<T> result = new ArrayList<T>();
		for (Binding<T> entry : list) {
			result.add(injector.getInstance(entry.getKey()));
		}
		return result;
	}

}
