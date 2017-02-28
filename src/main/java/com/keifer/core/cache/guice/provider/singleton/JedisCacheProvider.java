package com.keifer.core.cache.guice.provider.singleton;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.keifer.core.cache.redis.RedisCache;
import com.keifer.core.cache.redis.RedisCacheImpl;
import com.keifer.core.cache.redis.config.JedisSupportEnum;
import com.keifer.core.cache.utils.ConfigUtils;

import redis.clients.jedis.JedisPool;

@Singleton
public class JedisCacheProvider implements Provider<RedisCache> {

	private final RedisCacheImpl redisCache;

	@Inject
	public JedisCacheProvider(Provider<JedisPool> jedisPoolProvider) {
		redisCache = new RedisCacheImpl();
		redisCache.setJedisPool(jedisPoolProvider.get());
		redisCache.setLocal(ConfigUtils.getValue(JedisSupportEnum.switch_on_local_cache.val(), Boolean.class));
		redisCache.setLocalExpiredTime(ConfigUtils.getValue(JedisSupportEnum.local_cache_timeout.val(), Integer.class));
	}

	@Override
	public RedisCache get() {
		return redisCache;
	}

}
