package com.simple.base.bz.iot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.simple.base.bz.iot.entity.DeviceType;
import com.simple.base.bz.iot.service.DeviceTypeService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/iot")
public class IOTController {
	@Autowired
	DeviceTypeService deviceTypeService;

	 @ApiOperation(value="设备列表", notes="获取所有设备类型列表")
	    
	@RequestMapping(value = "/deviceTypes/", method=RequestMethod.GET)
	@ResponseBody
	public List<DeviceType> deviceTypes() {
		return deviceTypeService.getDevices();
	}

	@ApiOperation(value="设备类型", notes="根据ID取得设备类型")
	@ApiImplicitParam(name = "id", value = "设备类型ID", required = true, dataType = "Integer")
	@RequestMapping(value = "/deviceTypes/{id}", method=RequestMethod.GET)
	@ResponseBody
	public DeviceType channelpage(Long id) {
		return deviceTypeService.getDeviceTypeById(id);
		//return "index";
	}

	
	@ResponseBody
	@RequestMapping(value = "/deviceTypes/save", method=RequestMethod.POST)
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