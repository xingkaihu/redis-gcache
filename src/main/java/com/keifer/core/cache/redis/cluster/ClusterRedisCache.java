package com.keifer.core.cache.redis.cluster;

import com.keifer.core.cache.Cache;

import redis.clients.jedis.JedisCluster;

public interface ClusterRedisCache extends Cache<JedisCluster> {
}
