package com.simple.base.im.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simple.base.api.entity.User;
import com.simple.base.api.service.UserService;
import com.simple.base.im.server.NettyServer;

@RestController
public class NIOController {
	
	@Autowired
	NettyServer nioServer;
	@RequestMapping("/server/start")
	public long register(){
		nioServer.startServer();
		return 0;
		//return userService.register(user);
	}
}
