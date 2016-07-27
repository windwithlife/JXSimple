package com.simple.base.im.service;

import org.springframework.stereotype.Service;

import com.simple.base.im.entity.LoginRequest;
import com.simple.base.im.entity.LoginResponse;

@NIOService
@HandlerMapping(path ="user")
public class UserNioService  {

	@HandlerMapping(id="20000")
	public LoginResponse login(LoginRequest request){
		System.out.println("UserService is dealing with...");
		return null;
	}

}
