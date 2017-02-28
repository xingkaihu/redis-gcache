package com.keifer.core.cache.redis.config;

import com.keifer.core.cache.module.config.DefaultExtensionConfig;
import com.keifer.core.cache.module.support.ConfigLoaderSupport;
import com.keifer.core.cache.utils.BeanUtils;
import com.keifer.core.cache.utils.MqConstant;
import com.keifer.core.extension.SpiMeta;

@SpiMeta(name = MqConstant.EXTENSION_SUPPORT_CONFIG_SPI_NAME)
public class CacheConfig implements DefaultExtensionConfig<JedisConfig> {

	public CacheConfig() {
		this.startConfig();
	}

	@Override
	public void startConfig() {
		ConfigLoaderSupport.getConfigMap()
				.putAll(BeanUtils.toStringMap(this.getJedisConfig(), MqConstant.REDIS_CONFIG_PREFIX));
	}

	@Override
	public JedisConfig getJedisConfig() {
		return new DefaultJedisConfig();
	}

}
