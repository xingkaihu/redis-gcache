package com.keifer.core.cache.redis.config;

import com.keifer.core.cache.utils.MqConstant;

/**
 * jedis配置相关字段定义，配置文件应该以该定义为参考配置
 * 
 * @author keifer
 *
 */
public enum JedisSupportEnum {
	redis_max_total(JedisConst.redis_max_total), redis_max_idle(JedisConst.redis_max_idle), redis_max_wait_millis(
			JedisConst.redis_max_wait_millis), redis_test_on_borrow(JedisConst.redis_test_on_borrow), redis_hostname(
					JedisConst.redis_hostname), redis_ports(JedisConst.redis_ports), redis_timeout(
							JedisConst.redis_timeout), redis_password(JedisConst.redis_password), switch_on_local_cache(
									JedisConst.switch_on_local_cache), local_cache_timeout(
											JedisConst.local_cache_timeout), redis_cache_type(
													JedisConst.redis_cache_type), redis_deploy_type(
															JedisConst.redis_deploy_type), reids_cluster_nodes(
																	JedisConst.reids_cluster_nodes);

	private JedisSupportEnum(String value) {
		this.value = value;
	}

	private String value;

	public String val() {
		return value;
	}

	interface JedisConst {
		public static final String redis_max_total = MqConstant.REDIS_CONFIG_PREFIX + "maxTotal";
		public static final String redis_max_idle = MqConstant.REDIS_CONFIG_PREFIX + "maxIdle";
		public static final String redis_max_wait_millis = MqConstant.REDIS_CONFIG_PREFIX + "maxWaitMillis";
		public static final String redis_test_on_borrow = MqConstant.REDIS_CONFIG_PREFIX + "testOnBorrow";
		public static final String redis_hostname = MqConstant.REDIS_CONFIG_PREFIX + "hostname";
		public static final String redis_ports = MqConstant.REDIS_CONFIG_PREFIX + "ports";
		public static final String redis_timeout = MqConstant.REDIS_CONFIG_PREFIX + "redisTimeout";
		public static final String redis_password = MqConstant.REDIS_CONFIG_PREFIX + "redisPassword";
		public static final String switch_on_local_cache = MqConstant.REDIS_CONFIG_PREFIX + "openLocalCache";
		public static final String local_cache_timeout = MqConstant.REDIS_CONFIG_PREFIX + "localCacheExpiredTime";
		public static final String redis_cache_type = MqConstant.REDIS_CONFIG_PREFIX + "kvCacheType";
		public static final String redis_deploy_type = MqConstant.REDIS_CONFIG_PREFIX + "deployType";
		public static String reids_cluster_nodes = MqConstant.REDIS_CONFIG_PREFIX + "clusterNodes";
	}
}
