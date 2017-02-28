#### Redis分布式缓存项目(基于google guice)
##### 1.支持redis3.x
##### 2.支持三种模式：单机模式，分片模式，集群模式
##### 3.支持基于扩展点的方式配置redis参数，同事也支持配置文件的方式。项目中测试代码中默认给出的示例是基于扩展点的方式。
	如果需要基于properties配置文件，添加如下代码即可：
	PropertiesLoaderSupport.loadProperties(binder, MqConstant.DEFAULT_REDIS_CONFIG_FILE_NAME_IN_CLASSPATH);

##### 详细使用文档待完善