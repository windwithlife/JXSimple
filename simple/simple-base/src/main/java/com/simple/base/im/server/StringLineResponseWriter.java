package com.simple.base.im.server;



import io.netty.channel.Channel;

public class StringLineResponseWriter implements ResponseWriter {

	private static StringLineResponseWriter instance = new StringLineResponseWriter();
	public static StringLineResponseWriter getInstance(){
		return instance;
	}
	
	private StringLineResponseWriter(){
		
	}
	@Override
	public boolean writeAndFlush(Channel ch, String command, Object response) {
		// TODO Auto-generated method stub
		try {
			ch.writeAndFlush(response);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	

}
