package com.keifer.core.cache.guice.provider.cluster;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.keifer.core.cache.redis.cluster.JedisClusterFactory;
import com.keifer.core.cache.redis.config.JedisSupportEnum;
import com.keifer.core.cache.utils.ConfigUtils;

import redis.clients.jedis.JedisPoolConfig;

@Singleton
public class JedisClusterProvider implements Provider<JedisClusterFactory> {

	private final JedisClusterFactory redisCache = new JedisClusterFactory();

	@Inject
	public JedisClusterProvider(Provider<JedisPoolConfig> jedisPoolConfigProvider) {
		redisCache.setClustNodes(ConfigUtils.getValue(JedisSupportEnum.reids_cluster_nodes.val()));
		redisCache.setTimeout(ConfigUtils.getValue(JedisSupportEnum.redis_timeout.val(), Integer.class));
		redisCache.setPassword(ConfigUtils.getValue(JedisSupportEnum.redis_password.val()));
		redisCache.setGenericObjectPoolConfig(jedisPoolConfigProvider.get());
		redisCache.setMaxRedirections(6);
	}

	@Override
	public JedisClusterFactory get() {
		return redisCache;
	}

}
