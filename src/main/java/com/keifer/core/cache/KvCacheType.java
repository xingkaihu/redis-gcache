package com.keifer.core.cache;

import org.apache.commons.lang3.StringUtils;

/**
 * 分布式缓存的类型：redis
 * 
 * @author keifer
 */
public enum KvCacheType {

	REDIS("redis", DeployType.SINGLETON, com.keifer.core.cache.redis.RedisCache.class), REDIS_CLUSTER("redis",
			DeployType.CLUSTER, com.keifer.core.cache.redis.cluster.ClusterRedisCache.class), REDIS_SHARED(
					"redis", DeployType.SHARED, com.keifer.core.cache.redis.sharded.ShardedRedisCache.class);

	private String type;
	private DeployType dtype;
	private Class<?> targetClazz;

	private KvCacheType(String type, DeployType dtype, Class<?> targetClazz) {
		this.type = type;
		this.dtype = dtype;
		this.targetClazz = targetClazz;
	}

	public String getType() {
		return type;
	}

	public DeployType getDtype() {
		return dtype;
	}

	public Class<?> getTargetClazz() {
		return targetClazz;
	}

	public static void check(KvCacheType ktype, Class<?> clazz) {
		if (ktype.getTargetClazz() != clazz) {
			throw new RuntimeException("expected class: " + clazz.getName() + ",but not found.");
		}
	}

	public static KvCacheType getKvCacheType(String type, String deployType) {
		if (StringUtils.isBlank(type))
			return KvCacheType.REDIS;
		else {
			deployType = DeployType.getDeployType(deployType).getType();
		}
		KvCacheType[] types = KvCacheType.values();
		for (KvCacheType ctype : types) {
			if (ctype.getType().equalsIgnoreCase(type) && ctype.getDtype().getType().equalsIgnoreCase(deployType)) {
				return ctype;
			}
		}
		return KvCacheType.REDIS;
	}

	public String toString() {
		return "KvCacheType: type=" + this.getType() + ",DeployType=[ " + this.getDtype().toString() + " ],targetClazz="
				+ this.getTargetClazz();
	}
}
