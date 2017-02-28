package com.keifer.core.cache.guice.provider;

import com.google.inject.Provider;
import com.keifer.core.cache.redis.config.JedisSupportEnum;
import com.keifer.core.cache.utils.ConfigUtils;

import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolConfigProvider implements Provider<JedisPoolConfig> {

	private final JedisPoolConfig config;

	public JedisPoolConfigProvider() {
		this.config = new JedisPoolConfig();
		config.setMaxTotal(ConfigUtils.getValue(JedisSupportEnum.redis_max_total.val(), Integer.class));
		config.setMaxIdle(ConfigUtils.getValue(JedisSupportEnum.redis_max_idle.val(), Integer.class));
		config.setMaxWaitMillis(ConfigUtils.getValue(JedisSupportEnum.redis_max_wait_millis.val(), Integer.class));
		config.setTestOnBorrow(ConfigUtils.getValue(JedisSupportEnum.redis_test_on_borrow.val(), Boolean.class));
	}

	@Override
	public JedisPoolConfig get() {
		return config;
	}
}
