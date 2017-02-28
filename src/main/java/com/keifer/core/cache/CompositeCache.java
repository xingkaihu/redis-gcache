package com.keifer.core.cache;

import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedisPool;

@SuppressWarnings("rawtypes")
public interface CompositeCache extends Cache {

	public ShardedJedisPool getSharedRedisTargetObject();

	public JedisPool getRedisTargetObject();

	public JedisCluster getRedisClusterTargetObject();

}
