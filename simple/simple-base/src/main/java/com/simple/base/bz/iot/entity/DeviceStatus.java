package com.simple.base.bz.iot.entity;

import java.io.Serializable;


import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class DeviceStatus implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id; // 编号
	private int status; // 角色描述,UI界面显示使用
	private int temperature;


	public DeviceStatus() {
		this.status = 0;
		this.temperature =0;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public int getStatus() {
		return this.status;
	}

	public void setStatus(int s) {
		this.status = s;
	}

	public int getTemperature() {
		return this.temperature;
	}

	public void setTemperature(int t) {
		this.temperature = t;
	}

	@Override
	public String toString() {
		return "DeviceType DATA: [id=" + id + ", status=" + status + ", temperature=" + temperature
				+ "]";
	}
}
