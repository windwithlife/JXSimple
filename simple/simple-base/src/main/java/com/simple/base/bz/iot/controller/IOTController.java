package com.simple.base.bz.iot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.simple.base.bz.iot.entity.DeviceType;
import com.simple.base.bz.iot.service.DeviceTypeService;

@Controller
@RequestMapping("/iot")
public class IOTController {
	@Autowired
	DeviceTypeService deviceTypeService;

	@RequestMapping("/deviceTypes/")
	@ResponseBody
	// @RequiresPermissions("userInfo:del")//权限管理;
	public List<DeviceType> deviceTypes() {
		return deviceTypeService.getDevices();
	}

	@RequestMapping("/deviceTypes/{id}")
	@ResponseBody
	// @RequiresPermissions("userInfo:del")//权限管理;
	public DeviceType channelpage(Long id) {
		return deviceTypeService.getDeviceTypeById(id);
		//return "index";
	}

	@RequestMapping("/deviceTypes/save")
	@ResponseBody
	// @RequiresPermissions("userInfo:del")//权限管理;
	public DeviceType save(@RequestBody DeviceType dt) {
		return deviceTypeService.save(dt);
		//return "index";
	}
	public ModelAndView handleAuthorizationException(Exception e) {
		System.out.println("A Authorization Failure, MSG: " + e.getMessage());

		ModelAndView model = new ModelAndView("403");
		model.addObject("Msg", e.getMessage());
		return model;
	}
}