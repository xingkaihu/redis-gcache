package com.keifer.core.cache.utils;

public class MqConstant {
	public static final String DEFAULT_CHARACTER = "utf-8";

	// mysql locker
	public static final String MESSAGE_CHECK_MYSQL_LOCK_NAME = "MQ_MSG_CHECK_SJOB_LOCKER_lock_CDieZvb76fGKlW3a";

	static final String DEFAULT_SPI_META_NAME_PREFIX = "Default";
	public static final String GUICE_CONTEXT_SPI_NAME = "com.google.inject.Module$guice_name";
	public static final String MESSAGE_HANDLER_SPI_NAME = "com.hfbank.mq.handler.MessageHandler$impl1";
	public static final String RECORD_SERVICE_SPI_NAME = "com.hfbank.mq.db.service.RecordService$impl1";
	public static final String MESSAGE_SERVICE_SPI_NAME = "com.hfbank.mq.db.service.MessageService$impl1";
	public static final String DEFAULT_SERVICE_IMPL_PREFIX = "Default";

	public static final String DEFAULT_DELIMITER = ",";
	public static final String CONFIG_UNIQUE_DELIMITER="$$";
	public static final String REDIS_CONFIG_PREFIX = "redis_";
	public static final String JDBC_CONFIG_PREFIX = "jdbc_";
	
	public static final String EXTENSION_SUPPORT_CONFIG_SPI_NAME = "DefaultExtensionConfig";
	
	public enum SpiName {
		GuiceConetxt(GUICE_CONTEXT_SPI_NAME), 
		MessageHandler(MESSAGE_HANDLER_SPI_NAME,"DefaultMessageHandler"), 
		RecordService(RECORD_SERVICE_SPI_NAME,"DefaultRecordService"), 
		MessageService(MESSAGE_SERVICE_SPI_NAME, "DefaultMessageService");

		private String name;
		private String defaultName;

		private SpiName(String name) {
			this.name = name;
		}

		public String getN() {
			return name;
		}

		public String getDN() {
			return defaultName;
		}

		private SpiName(String name, String defaultName) {
			this.name = name;
			this.defaultName = defaultName;
		}
	}

	public enum MQClientRequestProtocol {
		TCP, HTTP;

		public static MQClientRequestProtocol getDefault() {
			return HTTP;
		}
	}

	public enum MQExchangeServerTyper {
		JETTY, NETTY;

		public static MQExchangeServerTyper getDefault() {
			return JETTY;
		}
	}

	public enum MQClientSerializerTyper {
		JSON, BYTES;

		public static MQClientSerializerTyper getDefault() {
			return JSON;
		}
	}
}
