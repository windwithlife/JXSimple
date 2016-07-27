package com.simple.base.im.server;



import io.netty.channel.Channel;

public class MessageResponseWriter implements ResponseWriter {

	private static MessageResponseWriter instance = new MessageResponseWriter();
	public static MessageResponseWriter getInstance(){
		return instance;
	}
	
	private MessageResponseWriter(){
		
	}
	@Override
	public boolean writeAndFlush(Channel ch, String command, Object response) {
		// TODO Auto-generated method stub
		try {
			
			//String data = SerializationTools.getInstance().transferToData(response);
			Message msg = Message.createMsg(Integer.valueOf(command), response);
			//String request = ((TextWebSocketFrame) frame).text();
            //System.out.println(request);
			ch.writeAndFlush(msg);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	

}
