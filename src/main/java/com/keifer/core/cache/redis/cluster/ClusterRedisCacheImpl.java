package com.keifer.core.cache.redis.cluster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.keifer.core.cache.CacheHelper;
import com.keifer.core.cache.utils.LoggerUtil;

import redis.clients.jedis.JedisCluster;
import redis.clients.util.SafeEncoder;

public class ClusterRedisCacheImpl implements ClusterRedisCache {

	private final CacheHelper cacheHelper = new CacheHelper();

	private JedisCluster jedisCluster;

	@Override
	public Object get(String key) {
		byte[] bytes = jedisCluster.get(SafeEncoder.encode(key));
		return cacheHelper.bytesToObject(bytes);
	}

	@Override
	public String getString(String key) {
		return jedisCluster.get(key);
	}

	@Override
	public Object hget(String hashtable, String key) {
		byte[] bytes = jedisCluster.hget(SafeEncoder.encode(hashtable), SafeEncoder.encode(key));
		return cacheHelper.bytesToObject(bytes);
	}

	@Override
	public boolean remove(String key) {
		boolean tag = true;
		try {
			jedisCluster.del(key);
		} catch (Exception e) {
			LoggerUtil.getDefaultLogger().error(e.getLocalizedMessage(), e);
			tag = false;
		}
		return tag;
	}

	@Override
	public boolean hset(String hashtable, String key, String value) {
		boolean tag = true;
		try {
			jedisCluster.hset(hashtable, key, value);
		} catch (Exception e) {
			LoggerUtil.getDefaultLogger().error(e.getLocalizedMessage(), e);
			tag = false;
		}
		return tag;
	}

	@Override
	public boolean set(String key, Object value, Integer expiredTime) {
		boolean tag = true;
		try {
			jedisCluster.set(SafeEncoder.encode(key), cacheHelper.objectToBytes(value));
			if (expiredTime > 0) {
				jedisCluster.expire(SafeEncoder.encode(key), expiredTime);
			}
		} catch (Exception e) {
			LoggerUtil.getDefaultLogger().error(e.getLocalizedMessage());
			tag = false;
		}
		return tag;
	}

	@Override
	public boolean set(String key, Object value) {
		return set(key, value, 0);
	}

	@Override
	public String hgetString(String hashtable, String key) {
		return jedisCluster.hget(hashtable, key);
	}

	@Override
	public boolean hset(String hashtable, String key, Object value) {
		boolean tag = true;
		try {
			jedisCluster.hset(SafeEncoder.encode(hashtable), SafeEncoder.encode(key), cacheHelper.objectToBytes(value));
		} catch (Exception e) {
			LoggerUtil.getDefaultLogger().error(e.getLocalizedMessage());
			tag = false;
		}
		return tag;
	}

	@Override
	public boolean set(String key, String value) {
		boolean tag = true;
		try {
			jedisCluster.set(key, value);
		} catch (Exception e) {
			LoggerUtil.getDefaultLogger().error(e.getLocalizedMessage());
			tag = false;
		}
		return tag;
	}

	@Override
	public Map<String, Object> getClusterNodes() {
		Map<String, Object> clusterNodes = Maps.newHashMap();
		clusterNodes.putAll(jedisCluster.getClusterNodes());
		return clusterNodes;
	}

	@Override
	public JedisCluster getTargetObject() {
		return this.jedisCluster;
	}

	@Override
	public boolean lset(String key, int expiredTime, String... values) {
		boolean tag = true;
		try {
			jedisCluster.lpush(key, values);
			if (expiredTime > 0) {
				jedisCluster.expire(key, expiredTime);
			}
		} catch (Exception e) {
			LoggerUtil.getDefaultLogger().error(e.getLocalizedMessage());
			tag = false;
		}
		return tag;
	}

	@Override
	public long llen(String key) {
		return jedisCluster.llen(key);
	}

	@Override
	public String lget(String key) {
		try {
			return jedisCluster.lpop(key);
		} catch (Exception e) {
			LoggerUtil.getDefaultLogger().error(e.getLocalizedMessage());
		}
		return "";
	}

	@Override
	public boolean hremove(String hashtable, String key) {
		boolean tag = true;
		try {
			jedisCluster.hdel(hashtable, key);
		} catch (Exception e) {
			LoggerUtil.getDefaultLogger().error(e.getLocalizedMessage(), e);
			tag = false;
		}
		return tag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E> List<E> hvalues(String hashtable) {
		List<E> entities = new ArrayList<E>();
		try {
			Collection<byte[]> result = jedisCluster.hvals(SafeEncoder.encode(hashtable));
			for (byte[] value : result) {
				entities.add((E) cacheHelper.bytesToObject(value));
			}
		} catch (Exception e) {
			LoggerUtil.getDefaultLogger().error(e.getLocalizedMessage());
		}
		return entities;
	}

}
