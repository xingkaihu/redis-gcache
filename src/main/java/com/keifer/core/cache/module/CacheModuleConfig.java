package com.keifer.core.cache.module;

import com.google.inject.Binder;
import com.google.inject.name.Names;
import com.keifer.core.cache.CompositeCache;
import com.keifer.core.cache.DeployType;
import com.keifer.core.cache.guice.provider.CompositeCacheProvider;
import com.keifer.core.cache.guice.provider.JedisPoolConfigProvider;
import com.keifer.core.cache.guice.provider.cluster.JedisClusterProvider;
import com.keifer.core.cache.guice.provider.sharded.JedisShardInfoProvider;
import com.keifer.core.cache.guice.provider.sharded.JedisShardInfoProvider2;
import com.keifer.core.cache.guice.provider.sharded.ShardedJedisPoolProvider;
import com.keifer.core.cache.guice.provider.sharded.SharededRedisCacheProvider;
import com.keifer.core.cache.guice.provider.singleton.JedisCacheProvider;
import com.keifer.core.cache.guice.provider.singleton.JedisPoolProvider;
import com.keifer.core.cache.redis.RedisCache;
import com.keifer.core.cache.redis.cluster.JedisClusterFactory;
import com.keifer.core.cache.redis.config.JedisSupportEnum;
import com.keifer.core.cache.redis.sharded.ShardedRedisCache;
import com.keifer.core.cache.utils.ConfigUtils;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

public class CacheModuleConfig implements ModuleConfig {

	public void configure(Binder binder) {
		this.doBefore(binder);

		// bind bean to gucie container
		// binder.bind(JedisConfig.class).to(DefaultJedisConfig.class);
		binder.bind(JedisPoolConfig.class).toProvider(JedisPoolConfigProvider.class).asEagerSingleton();

		DeployType type = DeployType.getDeployType(ConfigUtils.getValue(JedisSupportEnum.redis_deploy_type.val()));
		switch (type) {
		case SINGLETON:
			// singleton redis mode
			binder.bind(JedisPool.class).toProvider(JedisPoolProvider.class).asEagerSingleton();
			binder.bind(RedisCache.class).toProvider(JedisCacheProvider.class).asEagerSingleton();
			break;
		case SHARED:
			// sharded redis mode
			binder.bind(JedisShardInfo.class).annotatedWith(Names.named("redis.jedisShardInfo"))
					.toProvider(JedisShardInfoProvider.class).asEagerSingleton();
			binder.bind(JedisShardInfo.class).annotatedWith(Names.named("redis.jedisShardInfo2"))
					.toProvider(JedisShardInfoProvider2.class).asEagerSingleton();
			binder.bind(ShardedJedisPool.class).toProvider(ShardedJedisPoolProvider.class).asEagerSingleton();
			binder.bind(ShardedRedisCache.class).toProvider(SharededRedisCacheProvider.class).asEagerSingleton();
			break;
		case CLUSTER:
		default:
			// cluster redis mode
			binder.bind(JedisClusterFactory.class).toProvider(JedisClusterProvider.class).asEagerSingleton();
			break;
		}
		binder.bind(CompositeCache.class).toProvider(CompositeCacheProvider.class).asEagerSingleton();

		this.doAfter(binder);
	}

	public void doBefore(Binder binder) {
		// bind properties config
		// PropertiesLoaderSupport.loadProperties(binder, MqConstant.DEFAULT_REDIS_CONFIG_FILE_NAME_IN_CLASSPATH);
	}

	@Override
	public void doAfter(Binder binder) {
	}
}
