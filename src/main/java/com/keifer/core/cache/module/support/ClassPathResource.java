package com.keifer.core.cache.module.support;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.keifer.core.cache.utils.ClassUtils;
import com.keifer.core.cache.utils.LoggerUtil;
import com.keifer.core.cache.utils.StringUtils;

/**
 * 获取classpath下的配置文件
 * 
 * @author keifer
 *
 */
public class ClassPathResource {

	private final String path;

	private ClassLoader classLoader;

	private Class<?> clazz;

	public ClassPathResource(String path) {
		this(path, (ClassLoader) null);
	}

	public ClassPathResource(String path, ClassLoader classLoader) {
		String pathToUse = StringUtils.cleanPath(path);
		if (pathToUse.startsWith("/")) {
			pathToUse = pathToUse.substring(1);
		}
		this.path = pathToUse;
		this.classLoader = (classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader());
	}

	public InputStream getInputStream() throws IOException {
		InputStream is;
		if (this.clazz != null) {
			is = this.clazz.getResourceAsStream(this.path);
		} else if (this.classLoader != null) {
			is = this.classLoader.getResourceAsStream(this.path);
		} else {
			is = ClassLoader.getSystemResourceAsStream(this.path);
		}
		if (is == null) {
			throw new FileNotFoundException("config file " + this.path + " cannot be opened because it does not exist");
		}
		return is;
	}

	public Properties getProperties() {
		Properties properties = new Properties();
		try {
			properties.load(this.getInputStream());
		} catch (IOException e) {
			LoggerUtil.getCacheLogger().error("", e);
		}
		return properties;
	}
}
