package com.keifer.core.cache.test;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.keifer.core.cache.CompositeCache;
import com.keifer.core.cache.guice.DefaultInjectContext;

import redis.clients.jedis.ShardedJedisPool;

public class SharedRedisClusterTest {

	CompositeCache compositeCache = null;

	@Before
	public void setup() {
		compositeCache = DefaultInjectContext.getProvider(CompositeCache.class);
	}

	@Test
	public void set() {
		boolean result = compositeCache.set("name", "keifer123");
		Assert.assertEquals(true, result);
	}

	@Test
	public void get() {
		String result = compositeCache.getString("name");
		Assert.assertEquals("keifer123", result);
	}

	@Test
	public void getTargetObjct() {
		ShardedJedisPool result = compositeCache.getSharedRedisTargetObject();
		Assert.assertNotNull(result);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getNodes() {
		Map<String, Object> result = compositeCache.getClusterNodes();
		Assert.assertEquals(result.keySet().size(), 2);
	}
}
