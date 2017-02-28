package com.keifer.core.cache;

import org.apache.commons.lang3.StringUtils;

/**
 * 部署模式
 * 
 * @author keifer
 */
public enum DeployType {

	CLUSTER("cluster", "集群模式"), SHARED("shared", "分片模式"), SINGLETON("singleton", "单点模式");

	private String type;
	private String description;

	private DeployType(String type, String description) {
		this.type = type;
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public String getDescription() {
		return description;
	}

	// 默认使用单点模式
	public static DeployType getDeployType(String type) {
		if (StringUtils.isBlank(type))
			return DeployType.SINGLETON;
		DeployType[] types = DeployType.values();
		for (DeployType ctype : types) {
			if (ctype.getType().equalsIgnoreCase(type)) {
				return ctype;
			}
		}
		return DeployType.SINGLETON;
	}

	public String toString() {
		return "DeployType: type=" + this.getType() + ",description=" + this.getDescription();
	}
}
