package com.keifer.core.cache;

import java.util.List;
import java.util.Map;

public interface Cache<T> {

	/**
	 * 先从本地缓存读，读到就返回，读不到再从redis读，以优化读性能
	 */
	public Object get(String key);

	public String getString(String key);

	public Object hget(String hashtable, String key);

	public String hgetString(String hashtable, String key);

	public boolean remove(String key);

	public boolean hremove(String hashtable, String key);

	public boolean hset(String hashtable, String key, String value);

	public boolean hset(String hashtable, String key, Object value);

	<E> List<E> hvalues(String hashtable);

	public boolean set(String key, String value);

	public boolean set(String key, Object value);

	public boolean set(String key, Object value, Integer expiredTime);

	public Map<String, Object> getClusterNodes();

	public T getTargetObject();

	// list
	public boolean lset(String key, int expiredTime, String... values);

	public long llen(String key);

	public String lget(String key);

}
