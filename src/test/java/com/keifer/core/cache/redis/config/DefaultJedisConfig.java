package com.keifer.core.cache.redis.config;

import com.keifer.core.cache.redis.config.JedisConfig;

public class DefaultJedisConfig implements JedisConfig {

	// singleton mode or sharded mode
	private String hostname = "10.22.90.111";
	private String ports = "16379";
	private boolean openLocalCache = false;
	private int localCacheExpiredTime = 3600;

	private int maxTotal = 2000;
	private int maxIdle = 50;
	private long maxWaitMillis = 1000;
	private boolean testOnBorrow = true;

	// common config
	private int redisTimeout = 3600;
	private String redisPassword = "keifer";
	private String kvCacheType = "redis";
	private String deployType = "singleton";

	// cluster mode
	private String clusterNodes = "";

	public DefaultJedisConfig() {
	}

	public int getMaxTotal() {
		return this.maxTotal;
	}

	public int getMaxIdle() {
		return this.maxIdle;
	}

	public long getMaxWaitMillis() {
		return this.maxWaitMillis;
	}

	public boolean isTestOnBorrow() {
		return this.testOnBorrow;
	}

	public String getHostname() {
		return this.hostname;
	}

	public String getPorts() {
		return this.ports;
	}

	public int getRedisTimeout() {
		return this.redisTimeout;
	}

	public String getRedisPassword() {
		return this.redisPassword;
	}

	public boolean isOpenLocalCache() {
		return this.openLocalCache;
	}

	public int getLocalCacheExpiredTime() {
		return this.localCacheExpiredTime;
	}

	public String getKvCacheType() {
		return this.kvCacheType;
	}

	public String getDeployType() {
		return this.deployType;
	}

	public String getClusterNodes() {
		return this.clusterNodes;
	}

	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public void setMaxWaitMillis(long maxWaitMillis) {
		this.maxWaitMillis = maxWaitMillis;
	}

	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public void setPorts(String ports) {
		this.ports = ports;
	}

	public void setRedisTimeout(int redisTimeout) {
		this.redisTimeout = redisTimeout;
	}

	public void setRedisPassword(String redisPassword) {
		this.redisPassword = redisPassword;
	}

	public void setOpenLocalCache(boolean openLocalCache) {
		this.openLocalCache = openLocalCache;
	}

	public void setLocalCacheExpiredTime(int localCacheExpiredTime) {
		this.localCacheExpiredTime = localCacheExpiredTime;
	}

	public void setKvCacheType(String kvCacheType) {
		this.kvCacheType = kvCacheType;
	}

	public void setDeployType(String deployType) {
		this.deployType = deployType;
	}

	public void setClusterNodes(String clusterNodes) {
		this.clusterNodes = clusterNodes;
	}
}
