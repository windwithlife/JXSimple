package com.simple.core.database;

/**
 * 涓哄鏉俿ql鑷畾涔夊瓧娈靛睘鎬х粺璁℃眹鎬绘暟鎹�
 * 
 * @author user
 * 
 */
public class SummaryField {

	private String propertyName;
	private String propertyValue;

	public SummaryField(String propertyName, String propertyValue) {
		this.propertyName = propertyName;
		this.propertyValue = propertyValue;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}

}
