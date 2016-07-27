package com.simple.base.im.service;


import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.RequestMapping;

import com.simple.base.im.server.ConnectionManager;
import com.simple.base.im.server.IConnection;



@NIOService

@HandlerMapping(path ="forwarding")
public class ForwardingService {

	@HandlerMapping(path ="toUser", id="20001")
	public boolean forwardToUser(com.simple.base.im.entity.ForwardCommand cmd){
		if ((cmd.deviceId ==null) || (cmd.deviceId.equalsIgnoreCase(""))){
			return false;
		}
		IConnection targetConnection = ConnectionManager.getInstance().getConnect(cmd.deviceId);
		if ((null != targetConnection) && (targetConnection.isActive())){
			targetConnection.writeResponse("30001", cmd);
			System.out.println("Request is :" + cmd.cmd);
			System.out.println("work is handling and transmitting to device:" + cmd.deviceId);
			return true;
		}else{
			System.out.println("forwarding is failed because of the disarrivable to deivice connection");
			return false;
		}
				

	}


}
