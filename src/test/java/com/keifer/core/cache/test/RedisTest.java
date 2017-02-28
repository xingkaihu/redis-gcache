package com.keifer.core.cache.test;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.keifer.core.cache.CompositeCache;
import com.keifer.core.cache.guice.DefaultInjectContext;

import redis.clients.jedis.JedisPool;

public class RedisTest {

	CompositeCache compositeCache = null;

	@Before
	public void setup() {
		compositeCache = DefaultInjectContext.getProvider(CompositeCache.class);
	}

	@Test
	public void set() {
		boolean result = compositeCache.set("name1", "keifer123");
		Assert.assertEquals(true, result);
	}

	@Test
	public void get() {
		String result = compositeCache.getString("name1");
		Assert.assertEquals("keifer123", result);
	}

	@Test
	public void getTargetObjct() {
		JedisPool result = compositeCache.getRedisTargetObject();
		Assert.assertEquals(true, result != null);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getNodes() {
		Map<String, Object> result = compositeCache.getClusterNodes();
		Assert.assertNull(result);
	}
}
