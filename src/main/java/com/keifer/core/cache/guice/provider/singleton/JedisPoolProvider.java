package com.keifer.core.cache.guice.provider.singleton;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.keifer.core.cache.redis.config.JedisSupportEnum;
import com.keifer.core.cache.utils.ConfigUtils;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Singleton
public class JedisPoolProvider implements Provider<JedisPool> {

	private final JedisPool jedisPool;

	@Inject
	public JedisPoolProvider(Provider<JedisPoolConfig> jedisPoolConfigProvider) {
		jedisPool = new JedisPool(jedisPoolConfigProvider.get(),
				ConfigUtils.getValue(JedisSupportEnum.redis_hostname.val()),
				ConfigUtils.getValue(JedisSupportEnum.redis_ports.val(), Integer.class),
				ConfigUtils.getValue(JedisSupportEnum.redis_timeout.val(), Integer.class),
				ConfigUtils.getValue(JedisSupportEnum.redis_password.val()));
	}

	@Override
	public JedisPool get() {
		return jedisPool;
	}
}
