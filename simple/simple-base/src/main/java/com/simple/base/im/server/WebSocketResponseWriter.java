package com.simple.base.im.server;



import com.simple.core.util.SerializationTools;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class WebSocketResponseWriter implements ResponseWriter {
	private static WebSocketResponseWriter instance = new WebSocketResponseWriter();
	public static WebSocketResponseWriter getInstance(){
		return instance;
	}
	
	private WebSocketResponseWriter(){
		
	}
	@Override
	public boolean writeAndFlush(Channel ch, String command, Object response) {
		// TODO Auto-generated method stub
		try {
			String data = SerializationTools.getInstance().transferToData(response);
			//String request = ((TextWebSocketFrame) frame).text();
            //System.out.println(request);
			ch.writeAndFlush(
					new TextWebSocketFrame(data));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
