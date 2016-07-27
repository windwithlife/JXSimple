package com.simple.base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simple.base.api.entity.User;
import com.simple.base.api.service.UserService;
import com.simple.base.im.server.NettyServer;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	@Autowired
	NettyServer nioServer;
	@RequestMapping("/register")
	public long register(@RequestBody User user){
		//nioServer.startServer();
		return userService.register(user);
	}
}
