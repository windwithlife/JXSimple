package com.simple.base.im.server;

import io.netty.channel.Channel;

public class WebSocketConnection implements IConnection {
	private boolean isActive = true;
	private Channel ch = null;
	public WebSocketConnection(Channel ch){
		this.ch  = ch;
	}
	
	@Override
	public void writeResponse(String command, Object response) {
		// TODO Auto-generated method stub
		WebSocketResponseWriter.getInstance().writeAndFlush(this.ch, command, response);
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
