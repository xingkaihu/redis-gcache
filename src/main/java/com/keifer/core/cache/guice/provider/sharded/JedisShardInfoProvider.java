package com.keifer.core.cache.guice.provider.sharded;

import com.google.inject.Provider;
import com.keifer.core.cache.redis.config.JedisSupportEnum;
import com.keifer.core.cache.utils.ConfigUtils;
import com.keifer.core.cache.utils.StringUtils;

import redis.clients.jedis.JedisShardInfo;

public class JedisShardInfoProvider implements Provider<JedisShardInfo> {

	private final JedisShardInfo jedisShardInfo;

	public JedisShardInfoProvider() {
		jedisShardInfo = new JedisShardInfo(ConfigUtils.getValue(JedisSupportEnum.redis_hostname.val()),
				Integer.parseInt(StringUtils.getPorts(ConfigUtils.getValue(JedisSupportEnum.redis_ports.val()))[0]),
				ConfigUtils.getValue(JedisSupportEnum.redis_password.val()));
	}

	@Override
	public JedisShardInfo get() {
		return jedisShardInfo;
	}

}
