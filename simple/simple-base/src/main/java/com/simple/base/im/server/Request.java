package com.simple.base.im.server;

import io.netty.channel.Channel;

public class Request {
	private String sessionId;
	//private String path;
	//private int commandId;
	private String command;
	private Object input;
	private Channel ch;
	public Request(Object obj){
		
		this.input     = obj;
		//this.ch        = ch;
	}
	
	public Object getInput(){
		return this.input;
	}
	public String getSession(){
		return this.sessionId;
	}
	public String getCommand(){
		return this.command;
	}
	public Channel getChannel(){
		return this.ch;
	}
	public void setChannel(Channel ch){
		this.ch = ch;
	}
	public void setCommand(String command){
		this.command = command;
		//this.path = String.valueOf(id);
	}
	/*public void setRequestPath(String path){
		this.path = path;
	}
	public String getRequestPath(){
		return this.path;
	}
	*/
	public void setSession(String session){
		this.sessionId = session;
	}
}
