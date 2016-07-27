package com.simple.base.im.service;

import org.springframework.stereotype.Service;

import com.simple.base.im.entity.RegisterDeviceRequest;
import com.simple.base.im.entity.RegisterDeviceResponse;
import com.simple.base.im.server.ConnectionManager;
import com.simple.base.im.server.IConnection;
import com.simple.base.im.server.Request;



@NIOService
@HandlerMapping(path="device")
public class DeviceService {
	
	@HandlerMapping(id="10001", path="registerDevice")
	public RegisterDeviceResponse registerDevice(RegisterDeviceRequest request, Request requestContext){
		System.out.println("register device type is:" + request.deviceType + " id:" + request.deviceId);
		
		IConnection originConnection = ConnectionManager.getInstance().getConnect(requestContext.getChannel());
		ConnectionManager.getInstance().putConnection(request.deviceId, originConnection);
		
		RegisterDeviceResponse response = new RegisterDeviceResponse();
		response.statusCode = 0;
		return response;
	}

}
