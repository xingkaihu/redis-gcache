package com.keifer.core.cache.module.support;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.lang3.StringUtils;

/**
 * 应用上下文对象
 * 
 * @author keifer
 *
 */
public class ApplicationContext {
	private static String hostname;
	private static String ipAddress;
	static {
		try {
			// 若有多网卡，启动脚本时需要制定bind的网卡地址
			hostname = InetAddress.getLocalHost().getCanonicalHostName();
			String bindip = System.getProperty("mq.producer.bindip");
			if (StringUtils.isNotBlank(bindip)) {
				ipAddress = bindip;
			} else {
				ipAddress = InetAddress.getLocalHost().getHostAddress();
			}
		} catch (UnknownHostException e) {
			hostname = "";
			ipAddress = "";
		}
	}

	public static String getHostName() {
		return hostname;
	}

	public static String getIp() {
		return ipAddress;
	}

	public static String getCurrentThreadName() {
		return Thread.currentThread().getName();
	}

	public static String getHttpServerURLByIP(int port) {
		return "http://" + getIp() + ":" + port + "/";
	}

	public static String getHttpServerURLByHostName(int port) {
		return "http://" + getHostName() + ":" + port + "/";
	}
}
