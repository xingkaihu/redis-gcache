package com.keifer.core.cache.test;

import org.junit.Before;
import org.junit.Test;

import com.keifer.core.cache.CompositeCache;
import com.keifer.core.cache.guice.DefaultInjectContext;

import redis.clients.jedis.JedisCluster;

public class RedisClusterTest {

	CompositeCache compositeCache = null;

	@Before
	public void setup() {
		compositeCache = DefaultInjectContext.getProvider(CompositeCache.class);
	}

	@Test
	public void set() {
		System.out.println(compositeCache.getClusterNodes().size());
		compositeCache.set("name", "keifer123");
	}

	@Test
	public void get() {
		String result = compositeCache.getString("name");
		System.out.println("result====>>>" + result);
	}

	@Test
	public void getTargetObjct() {
		JedisCluster result = compositeCache.getRedisClusterTargetObject();
		System.out.println("result------>>" + result);
	}
}
