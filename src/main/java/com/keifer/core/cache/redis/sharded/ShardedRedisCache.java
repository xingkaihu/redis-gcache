package com.keifer.core.cache.redis.sharded;

import com.keifer.core.cache.Cache;
import com.keifer.core.cache.local.LocalCache;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public interface ShardedRedisCache extends Cache<ShardedJedisPool> {

	public ShardedJedis getJedis();

	public void returnJedis(ShardedJedis jedis);

	/**
	 * 获取cache封装后的对象
	 */
	public Object getByRedis(String key);

	public Object hgetByRedis(String hashtable, String key);

	/**
	 * 判key 和value 是否存在
	 */
	public long setNx(String lockKey, String value);

	/**
	 * 重新存放键值
	 */
	public String getSet(String lockKey, String value);

	/**
	 * 获取value
	 * 
	 * @param lockKey
	 * @param value
	 * @return
	 */
	public String getValue(String lockKey);

	public long getTTL(String key);

	public boolean setByRedis(String key, Object value, Integer expiredTime);

	public boolean hsetByRedis(String hashtable, String key, String value);

	public ShardedJedisPool getJedisPool();

	public void setJedisPool(ShardedJedisPool jedisPool);

	public void setLocal(boolean local);

	public LocalCache<String, Object> getLocalCache();

}
