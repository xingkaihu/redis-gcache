package com.keifer.core.cache.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志工具类
 * 
 * @author keifer
 */
public class LoggerUtil {
	private static Logger defaultLogger = LoggerFactory.getLogger("def");
	private static Logger cacheLogger = LoggerFactory.getLogger("cache.log");

	public Logger getLogger(String loggerName) {
		return LoggerFactory.getLogger(loggerName);
	}

	public static Logger getCacheLogger() {
		return cacheLogger;
	}

	public static Logger getDefaultLogger() {
		return defaultLogger;
	}
}
