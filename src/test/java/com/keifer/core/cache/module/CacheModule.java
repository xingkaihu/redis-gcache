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
import com.keifer.core.cache.module.config.DefaultExtensionConfig;
import com.keifer.core.cache.module.support.ConfigLoaderSupport;
import com.keifer.core.cache.redis.RedisCache;
import com.keifer.core.cache.redis.cluster.JedisClusterFactory;
import com.keifer.core.cache.redis.config.JedisSupportEnum;
import com.keifer.core.cache.redis.sharded.ShardedRedisCache;
import com.keifer.core.cache.utils.MqConstant;
import com.keifer.core.extension.Activation;
import com.keifer.core.extension.ExtensionLoaderUtils;
import com.keifer.core.extension.SpiMeta;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

@SpiMeta(name = MqConstant.GUICE_CONTEXT_SPI_NAME)
@Activation(sequence = 10)
public class CacheModule extends BaseModule {

	public CacheModule() {
	}

	public void configure() {
		this.doBefore(this.binder());

		// bind bean to gucie container
		bind(JedisPoolConfig.class).toProvider(JedisPoolConfigProvider.class).asEagerSingleton();

		String deployType = ConfigLoaderSupport.getConfigMap().get(JedisSupportEnum.redis_deploy_type.val());
		DeployType type = DeployType.getDeployType(deployType);
		switch (type) {
		case SINGLETON:
			// singleton redis mode
			bind(JedisPool.class).toProvider(JedisPoolProvider.class).asEagerSingleton();
			bind(RedisCache.class).toProvider(JedisCacheProvider.class).asEagerSingleton();
			break;
		case SHARED:
			// sharded redis mode
			bind(JedisShardInfo.class).annotatedWith(Names.named("redis.jedisShardInfo"))
					.toProvider(JedisShardInfoProvider.class).asEagerSingleton();
			bind(JedisShardInfo.class).annotatedWith(Names.named("redis.jedisShardInfo2"))
					.toProvider(JedisShardInfoProvider2.class).asEagerSingleton();
			bind(ShardedJedisPool.class).toProvider(ShardedJedisPoolProvider.class).asEagerSingleton();
			bind(ShardedRedisCache.class).toProvider(SharededRedisCacheProvider.class).asEagerSingleton();
			break;
		case CLUSTER:
		default:
			// cluster redis mode
			bind(JedisClusterFactory.class).toProvider(JedisClusterProvider.class).asEagerSingleton();
			break;
		}
		bind(CompositeCache.class).toProvider(CompositeCacheProvider.class).asEagerSingleton();

		this.doAfter(this.binder());
	}

	@Override
	public void doBefore(Binder binder) {
		// bind properties config
		// PropertiesLoaderSupport.loadProperties(binder,
		// MqConstant.DEFAULT_REDIS_CONFIG_FILE_NAME_IN_CLASSPATH);
		// bind(JedisConfig.class).to(DefaultJedisConfig.class);
		ExtensionLoaderUtils.getExtensionSingleInstance(DefaultExtensionConfig.class,
				DefaultExtensionConfig.class.getSimpleName());
	}

	@Override
	public void doAfter(Binder binder) {
		// do nothing
	}
}
