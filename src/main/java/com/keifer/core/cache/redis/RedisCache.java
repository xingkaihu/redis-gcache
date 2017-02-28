package com.keifer.core.cache.redis;

import com.keifer.core.cache.Cache;
import com.keifer.core.cache.local.LocalCache;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public interface RedisCache extends Cache<JedisPool> {

	public Jedis getJedis();

	public void returnJedis(Jedis jedis);

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

	public JedisPool getJedisPool();

	public void setJedisPool(JedisPool jedisPool);

	public void setLocal(boolean local);

	public LocalCache<String, Object> getLocalCache();

	// public void setJedis(Jedis jedis);

}
