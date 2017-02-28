package com.keifer.core.cache.redis.cluster;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.keifer.core.cache.utils.MqConstant;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class JedisClusterFactory {

	private String clustNodes;
	private JedisCluster jedisCluster;
	private Integer timeout;
	private String password;
	private Integer maxRedirections;
	private GenericObjectPoolConfig genericObjectPoolConfig;

	/* IP地址的验证 */
	private Pattern p = Pattern.compile("^.+[:]\\d{1,5}\\s*$");

	public JedisCluster getObject() throws Exception {
		return jedisCluster;
	}

	public Class<? extends JedisCluster> getObjectType() {
		return (this.jedisCluster != null ? this.jedisCluster.getClass() : JedisCluster.class);
	}

	public boolean isSingleton() {
		return true;
	}

	private Set<HostAndPort> parseHostAndPort() throws Exception {
		try {
			String[] values = StringUtils.splitByWholeSeparator(this.clustNodes, MqConstant.DEFAULT_DELIMITER);
			Set<HostAndPort> haps = new HashSet<HostAndPort>(values.length);
			for (String value : values) {
				boolean isIpPort = p.matcher(value).matches();
				if (!isIpPort) {
					throw new IllegalArgumentException("ip 或 port 不合法");
				}
				String[] ipAndPort = value.split(":");
				HostAndPort hap = new HostAndPort(ipAndPort[0], Integer.parseInt(ipAndPort[1]));
				haps.add(hap);
			}

			return haps;
		} catch (IllegalArgumentException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new Exception("解析 jedis 配置文件失败", ex);
		}
	}

	public void afterPropertiesSet() throws Exception {
		Set<HostAndPort> haps = this.parseHostAndPort();
		jedisCluster = new JedisCluster(haps, timeout, timeout, maxRedirections, password, genericObjectPoolConfig);
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public void setMaxRedirections(int maxRedirections) {
		this.maxRedirections = maxRedirections;
	}

	public void setGenericObjectPoolConfig(GenericObjectPoolConfig genericObjectPoolConfig) {
		this.genericObjectPoolConfig = genericObjectPoolConfig;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setClustNodes(String clustNodes) {
		this.clustNodes = clustNodes;
	}

}