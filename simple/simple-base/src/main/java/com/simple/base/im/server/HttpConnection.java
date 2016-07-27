package com.simple.base.im.server;

import io.netty.channel.Channel;

public class HttpConnection implements IConnection {

	private Channel ch = null;
	private boolean bActive = true;
	public HttpConnection(Channel ch){
		this.ch = ch;
	}
	@Override
	public void writeResponse(String command, Object response) {
		if (this.isActive()){
			// TODO Auto-generated method stub
			HttpResponseWriter.getInstance().writeAndFlush(this.ch, command, response);
		}else{
			return;
		}
	}
	@Override
	public void close() {
		// TODO Auto-generated method stub
		this.bActive = false;
	}
	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return this.bActive;
	}

	
}
