package com.simple.base.im.server;

import io.netty.channel.Channel;

public class Response {

	
		private String sessionId;
		private String command;
		private Channel ch;
		//private Object output;
		public Response(Request req){
			this.sessionId = req.getSession();
			this.command = req.getCommand();
		    this.ch        = req.getChannel();
		}
		
		public void write(Object msg){
			this.ch.writeAndFlush(msg);
		}
		public String getSession(){
			return this.sessionId;
		}
		public String getCommand(){
			return this.command;
		}
		public boolean doResponse(Object obj){
			IConnection conn = ConnectionManager.getInstance().getConnect(this.ch);
			if ((conn != null) && (conn.isActive())){
				conn.writeResponse(this.command + "", obj);
				return true;
			}else{
				return false;
			}
		}
	}
