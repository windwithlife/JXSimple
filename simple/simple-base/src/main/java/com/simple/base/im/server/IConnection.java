package com.simple.base.im.server;

public interface IConnection {
	public void writeResponse(String command, Object response);
	public void close();
	public boolean isActive();
	
}
