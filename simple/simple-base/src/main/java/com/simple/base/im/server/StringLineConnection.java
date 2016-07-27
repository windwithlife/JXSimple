package com.simple.base.im.server;

import io.netty.channel.Channel;

public class StringLineConnection implements IConnection{
	private Channel ch = null;
	private boolean isActive = true;
	private ResponseWriter writer = StringLineResponseWriter.getInstance();
	public StringLineConnection(Channel ch){
		this.ch = ch;
		this.isActive = true;
	}
	
	public void writeResponse(String command, Object response){
		this.writer.writeAndFlush(this.ch, command, response);
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		this.isActive = false;
	}

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return this.isActive;
	}
}
