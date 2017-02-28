package com.keifer.core.cache.redis.config;

public interface JedisConfig {

	public int getMaxTotal();

	public int getMaxIdle();

	public long getMaxWaitMillis();

	public boolean isTestOnBorrow();

	public String getHostname();

	// 分片模式： 多个端口用逗号分隔；单机模式：一个端口
	public String getPorts();

	public int getRedisTimeout();

	public String getRedisPassword();

	public boolean isOpenLocalCache();

	public int getLocalCacheExpiredTime();

	public String getKvCacheType();

	public String getDeployType();

	public String getClusterNodes();
}
