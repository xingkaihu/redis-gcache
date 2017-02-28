package com.keifer.core.cache.guice.provider;

import com.google.inject.Provider;
import com.keifer.core.cache.CompositeCache;
import com.keifer.core.cache.DefaultCacheImpl;
import com.keifer.core.cache.redis.config.JedisSupportEnum;
import com.keifer.core.cache.utils.ConfigUtils;

public class CompositeCacheProvider implements Provider<CompositeCache> {

	private final DefaultCacheImpl compositeCache;

	public CompositeCacheProvider() {
		this.compositeCache = new DefaultCacheImpl();
		compositeCache.setKvCacheType(ConfigUtils.getValue(JedisSupportEnum.redis_cache_type.val()));
		compositeCache.setDeployType(ConfigUtils.getValue(JedisSupportEnum.redis_deploy_type.val()));
	}

	@Override
	public CompositeCache get() {
		return compositeCache;
	}
}
