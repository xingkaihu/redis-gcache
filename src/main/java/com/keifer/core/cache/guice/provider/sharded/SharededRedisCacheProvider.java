package com.keifer.core.cache.guice.provider.sharded;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.keifer.core.cache.redis.config.JedisSupportEnum;
import com.keifer.core.cache.redis.sharded.ShardedRedisCache;
import com.keifer.core.cache.redis.sharded.ShardedRedisCacheImpl;
import com.keifer.core.cache.utils.ConfigUtils;

import redis.clients.jedis.ShardedJedisPool;

@Singleton
public class SharededRedisCacheProvider implements Provider<ShardedRedisCache> {

	private final ShardedRedisCacheImpl redisCache;

	@Inject
	public SharededRedisCacheProvider(Provider<ShardedJedisPool> shardedJedisPoolProvider) {
		this.redisCache = new ShardedRedisCacheImpl();
		redisCache.setJedisPool(shardedJedisPoolProvider.get());
		redisCache.setLocal(ConfigUtils.getValue(JedisSupportEnum.switch_on_local_cache.val(), Boolean.class));
		redisCache.setLocalExpiredTime(ConfigUtils.getValue(JedisSupportEnum.local_cache_timeout.val(), Integer.class));
	}

	@Override
	public ShardedRedisCache get() {
		return redisCache;
	}
}
