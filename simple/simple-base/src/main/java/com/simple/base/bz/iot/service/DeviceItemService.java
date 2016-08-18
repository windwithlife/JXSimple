package com.simple.base.bz.iot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.simple.base.bz.iot.dao.DeviceItemRepository;
import com.simple.base.bz.iot.entity.DeviceItem;


@Service
public class DeviceItemService {
	@Autowired
	DeviceItemRepository deviceItemDao;
	public List<DeviceItem> getDevices(){
		List<DeviceItem> items = deviceItemDao.findAll();
		///for(DeviceItem tmp:items){
		//	System.out.println(tmp.getType().getName());
		//}
		return items;
	}
	public DeviceItem getDeviceTypeById(Long id){
		return deviceItemDao.findOne(id);
	}
	public DeviceItem save(DeviceItem dt){
		return this.deviceItemDao.save(dt);
	}
	public void remove(Long id){
		this.deviceItemDao.delete(id);
	}
}
