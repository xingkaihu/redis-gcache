package com.keifer.core.cache.redis.sharded;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.keifer.core.cache.CacheHelper;
import com.keifer.core.cache.local.LocalCache;
import com.keifer.core.cache.local.LocalCacheImpl;
import com.keifer.core.cache.utils.LoggerUtil;

import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.util.SafeEncoder;

public class ShardedRedisCacheImpl implements ShardedRedisCache {

	private final CacheHelper cacheHelper = new CacheHelper();

	private final LocalCache<String, Object> localCache = new LocalCacheImpl();

	private ShardedJedisPool jedisPool;

	private boolean local = false;

	private int localExpiredTime = 60 * 60;

	public void setLocalExpiredTime(int localExpiredTime) {
		this.localExpiredTime = localExpiredTime;
	}

	public ShardedJedis getJedis() {
		return jedisPool.getResource();
	}

	public void returnJedis(ShardedJedis jedis) {
		if (jedis != null) {
			jedis.close();
		}
	}

	/**
	 * 获取cache封装后的对象
	 */
	public Object getByRedis(String key) {
		ShardedJedis jedis = jedisPool.getResource();
		Object obj = null;
		try {
			byte[] bytes = jedis.get(SafeEncoder.encode(key));
			obj = cacheHelper.bytesToObject(bytes);
		} catch (Exception e) {
			LoggerUtil.getDefaultLogger().error(e.getLocalizedMessage());
		} finally {
			returnJedis(jedis);
		}
		return obj;

	}

	public Object hgetByRedis(String hashtable, String key) {
		ShardedJedis jedis = jedisPool.getResource();
		Object obj = null;
		try {
			byte[] bytes = jedis.hget(SafeEncoder.encode(hashtable), SafeEncoder.encode(key));
			obj = cacheHelper.bytesToObject(bytes);
		} catch (Exception e) {
			LoggerUtil.getDefaultLogger().error(e.getLocalizedMessage());
		} finally {
			returnJedis(jedis);
		}
		return obj;

	}

	/**
	 * 判key 和value 是否存在
	 * 
	 * @param lockKey
	 * @param value
	 * @return
	 */
	public long setNx(String lockKey, String value) {
		ShardedJedis jedis = jedisPool.getResource();
		long isExit = 0;
		try {
			isExit = jedis.setnx(lockKey, value);
		} catch (Exception e) {
			LoggerUtil.getDefaultLogger().error(e.getLocalizedMessage());
		} finally {
			returnJedis(jedis);
		}

		return isExit;
	}

	/**
	 * 重新存放键值
	 * 
	 * @param lockKey
	 * @param value
	 * @return
	 */
	public String getSet(String lockKey, String value) {
		ShardedJedis jedis = jedisPool.getResource();
		String getValue = null;
		try {
			getValue = jedis.getSet(lockKey, String.valueOf(value));
		} catch (Exception e) {
			LoggerUtil.getDefaultLogger().error(e.getLocalizedMessage());
		} finally {
			returnJedis(jedis);
		}
		return getValue;

	}

	/**
	 * 获取value
	 * 
	 * @param lockKey
	 * @param value
	 * @return
	 */
	public String getValue(String lockKey) {
		ShardedJedis jedis = jedisPool.getResource();
		String getValue = null;
		try {
			getValue = jedis.get(lockKey);
		} catch (Exception e) {
			LoggerUtil.getDefaultLogger().error(e.getLocalizedMessage());
		} finally {
			returnJedis(jedis);
		}
		return getValue;

	}

	/**
	 * 先从本地缓存读，读到就返回，读不到再从redis读，以优化读性能
	 */
	public Object get(String key) {
		Object result;
		if (local) {
			result = localCache.get(key);
			if (result != null) {
				return result;
			}
		}
		result = getByRedis(key);
		if (result != null) {
			localCache.put(key, result, localExpiredTime);
		}
		return result;

	}

	public Object hget(String hashtable, String key) {
		Object result;
		if (local) {
			result = localCache.get(key);
			if (result != null) {
				return result;
			}
		}
		result = hgetByRedis(hashtable, key);
		if (result != null) {
			localCache.put(key, result, localExpiredTime);
		}
		return result;
	}

	public long getTTL(String key) {
		long ttl = 0;
		ShardedJedis jedis = jedisPool.getResource();
		try {
			ttl = jedis.ttl(SafeEncoder.encode(key));
		} catch (Exception e) {
			LoggerUtil.getDefaultLogger().error(e.getLocalizedMessage());
		} finally {
			returnJedis(jedis);
		}
		return ttl;
	}

	/**
	 * 只向分布式缓存中写，不需要向本地写
	 */
	public boolean set(String key, Object value, Integer expiredTime) {
		if (local) {
			localCache.put(key, value, expiredTime);
		}
		return setByRedis(key, value, expiredTime);
	}

	public boolean hset(String hashtable, String key, String value) {
		if (local) {
			localCache.put(key, value);
		}
		return hsetByRedis(hashtable, key, value);
	}

	public boolean remove(String key) {
		localCache.remove(key);
		ShardedJedis jedis = jedisPool.getResource();
		boolean tag = true;
		try {
			jedis.del(key);
		} catch (Exception e) {
			LoggerUtil.getDefaultLogger().error(e.getLocalizedMessage());
			tag = false;
		} finally {
			returnJedis(jedis);
		}
		return tag;
	}

	/**
	 * 使用默认时间保存
	 */
	public boolean set(String key, Object value) {
		if (local) {
			localCache.put(key, value, localExpiredTime);
		}
		return setByRedis(key, value, 0);
	}

	public boolean setByRedis(String key, Object value, Integer expiredTime) {
		ShardedJedis jedis = jedisPool.getResource();
		boolean tag = true;
		try {
			jedis.set(SafeEncoder.encode(key), cacheHelper.objectToBytes(value));
			if (expiredTime > 0) {
				jedis.expire(SafeEncoder.encode(key), expiredTime);
			}
		} catch (Exception e) {
			LoggerUtil.getDefaultLogger().error(e.getLocalizedMessage());
			tag = false;
		} finally {
			returnJedis(jedis);
		}
		return tag;
	}

	public boolean hsetByRedis(String hashtable, String key, String value) {
		ShardedJedis jedis = jedisPool.getResource();
		boolean tag = true;
		try {
			jedis.hset(hashtable, key, value);
		} catch (Exception e) {
			LoggerUtil.getDefaultLogger().error(e.getLocalizedMessage());
			tag = false;
		} finally {
			returnJedis(jedis);
		}
		return tag;
	}

	public boolean hsetObjectByRedis(String hashtable, String key, Object value) {
		ShardedJedis jedis = jedisPool.getResource();
		boolean tag = true;
		try {
			jedis.hset(SafeEncoder.encode(hashtable), SafeEncoder.encode(key), cacheHelper.objectToBytes(value));
		} catch (Exception e) {
			LoggerUtil.getDefaultLogger().error(e.getLocalizedMessage());
			tag = false;
		} finally {
			returnJedis(jedis);
		}
		return tag;
	}

	public ShardedJedisPool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(ShardedJedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

	public CacheHelper getCacheHelper() {
		return cacheHelper;
	}

	public boolean isLocal() {
		return local;
	}

	public void setLocal(boolean local) {
		this.local = local;
	}

	public LocalCache<String, Object> getLocalCache() {
		return localCache;
	}

	public int getLocalExpiredTime() {
		return localExpiredTime;
	}

	@Override
	public String getString(String key) {
		return getValue(key);
	}

	public String hgetStringByRedis(String hashtable, String key) {
		ShardedJedis jedis = jedisPool.getResource();
		try {
			return jedis.hget(hashtable, key);
		} catch (Exception e) {
			LoggerUtil.getDefaultLogger().error(e.getLocalizedMessage());
			return null;
		} finally {
			returnJedis(jedis);
		}
	}

	@Override
	public String hgetString(String hashtable, String key) {
		String result = null;
		if (local) {
			result = (String) localCache.get(key);
			if (result != null) {
				return result;
			}
		}
		result = hgetStringByRedis(hashtable, key);
		if (result != null) {
			localCache.put(key, result, localExpiredTime);
		}
		return result;
	}

	@Override
	public boolean hset(String hashtable, String key, Object value) {
		if (local) {
			localCache.put(key, value, localExpiredTime);
		}
		return hsetObjectByRedis(hashtable, key, value);
	}

	@Override
	public boolean set(String key, String value) {
		ShardedJedis jedis = jedisPool.getResource();
		boolean tag = true;
		try {
			jedis.set(key, value);
		} catch (Exception e) {
			tag = false;
			LoggerUtil.getDefaultLogger().error(e.getLocalizedMessage());
		} finally {
			returnJedis(jedis);
		}
		return tag;
	}

	@Override
	public Map<String, Object> getClusterNodes() {
		ShardedJedis jedis = jedisPool.getResource();
		try {
			Map<String, Object> nodes = new HashMap<String, Object>();
			Collection<JedisShardInfo> collection = jedis.getAllShardInfo();
			for (JedisShardInfo jedisShardInfo : collection) {
				String key = jedisShardInfo.getHost() + ":" + jedisShardInfo.getPort();
				if (!nodes.containsKey(key)) {
					nodes.put(key, jedisShardInfo);
				}
			}
			return nodes;
		} finally {
			returnJedis(jedis);
		}
	}

	@Override
	public ShardedJedisPool getTargetObject() {
		return this.jedisPool;
	}

	@Override
	public boolean lset(String key, int expiredTime, String... values) {
		ShardedJedis jedis = jedisPool.getResource();
		boolean tag = true;
		try {
			jedis.lpush(key, values);
			if (expiredTime > 0) {
				jedis.expire(key, expiredTime);
			}
		} catch (Exception e) {
			tag = false;
			LoggerUtil.getDefaultLogger().error(e.getLocalizedMessage());
		} finally {
			returnJedis(jedis);
		}
		return tag;
	}

	@Override
	public long llen(String key) {
		ShardedJedis jedis = jedisPool.getResource();
		try {
			return jedis.llen(key);
		} catch (Exception e) {
			LoggerUtil.getDefaultLogger().error(e.getLocalizedMessage());
		} finally {
			returnJedis(jedis);
		}
		return 0;
	}

	@Override
	public String lget(String key) {
		ShardedJedis jedis = jedisPool.getResource();
		try {
			return jedis.lpop(key);
		} catch (Exception e) {
			LoggerUtil.getDefaultLogger().error(e.getLocalizedMessage());
		} finally {
			returnJedis(jedis);
		}
		return "";
	}

	@Override
	public boolean hremove(String hashtable, String key) {
		localCache.remove(hashtable, key);
		ShardedJedis jedis = jedisPool.getResource();
		boolean tag = true;
		try {
			jedis.hdel(hashtable, key);
		} catch (Exception e) {
			LoggerUtil.getDefaultLogger().error(e.getLocalizedMessage());
			tag = false;
		} finally {
			returnJedis(jedis);
		}
		return tag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E> List<E> hvalues(String hashtable) {
		ShardedJedis jedis = jedisPool.getResource();
		List<E> entities = new ArrayList<E>();
		try {
			Collection<byte[]> result = jedis.hvals(SafeEncoder.encode(hashtable));
			for (byte[] value : result) {
				entities.add((E) cacheHelper.bytesToObject(value));
			}
		} catch (Exception e) {
			LoggerUtil.getDefaultLogger().error(e.getLocalizedMessage());
		} finally {
			returnJedis(jedis);
		}
		return entities;
	}
}
