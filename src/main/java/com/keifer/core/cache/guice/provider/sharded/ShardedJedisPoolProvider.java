package com.keifer.core.cache.guice.provider.sharded;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

@Singleton
public class ShardedJedisPoolProvider implements Provider<ShardedJedisPool> {

	private final ShardedJedisPool shardedJedisPool;

	@Inject
	public ShardedJedisPoolProvider(Provider<JedisShardInfo> jedisShardInfoProvider,
			Provider<JedisShardInfo> jedisShardInfoProvider2, JedisPoolConfig jedisPoolConfig) {
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		shards.add(jedisShardInfoProvider.get());
		shards.add(jedisShardInfoProvider2.get());
		this.shardedJedisPool = new ShardedJedisPool(jedisPoolConfig, shards);
	}

	@Override
	public ShardedJedisPool get() {
		return this.shardedJedisPool;
	}
}
