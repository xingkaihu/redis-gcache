package com.keifer.core.cache.module.config;

import com.keifer.core.extension.Scope;
import com.keifer.core.extension.Spi;

/**
 * 扩展配置接口
 * 
 * @author keifer
 */
@Spi(scope = Scope.SINGLETON)
public interface DefaultExtensionConfig<R> {

	void startConfig();

	public R getJedisConfig();

}
