package com.keifer.core.cache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.keifer.core.cache.utils.LoggerUtil;

import redis.clients.util.SafeEncoder;

public class CacheHelper {

	public byte[] encodeKey(String key) {
		return SafeEncoder.encode(key);
	}

	public Object bytesToObject(byte[] bytes) {
		if (bytes == null || bytes.length == 0) {
			return null;
		}
		try {
			ObjectInputStream inputStream;
			inputStream = new ObjectInputStream(new ByteArrayInputStream(bytes));
			Object obj = inputStream.readObject();
			return obj;
		} catch (Exception e) {
			LoggerUtil.getDefaultLogger().error("read byte[] to Object exception : ", e);
		}

		return null;
	}

	public byte[] objectToBytes(Object value) {
		if (value == null) {
			return null;
		}
		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream outputStream;
		try {
			outputStream = new ObjectOutputStream(arrayOutputStream);
			outputStream.writeObject(value);
		} catch (IOException e) {
			LoggerUtil.getDefaultLogger().error("write object to byte[] exception : ", e);
		} finally {
			try {
				arrayOutputStream.close();
			} catch (IOException e) {
				LoggerUtil.getDefaultLogger().error("ByteArrayOutputStream close exception : ", e);
			}
		}
		return arrayOutputStream.toByteArray();
	}
}
