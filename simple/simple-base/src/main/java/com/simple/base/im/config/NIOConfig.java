package com.simple.base.im.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.simple.base.im.server.NettyServer;

@Configuration  
public class NIOConfig {
	@Bean
	public NettyServer nettyServer(){
		NettyServer server =  new NettyServer(6000);
		return server;
	}
}
