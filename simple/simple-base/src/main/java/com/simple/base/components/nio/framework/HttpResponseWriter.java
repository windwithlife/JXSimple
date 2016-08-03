package com.simple.base.components.nio.framework;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import com.simple.core.util.SerializationTools;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpHeaders.Names;
import io.netty.handler.codec.http.HttpHeaders.Values;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class HttpResponseWriter implements ResponseWriter {

	private static HttpResponseWriter instance = new HttpResponseWriter();
	public static HttpResponseWriter getInstance(){
		return instance;
	}
	
	private HttpResponseWriter(){
		
	}
	@Override
	public boolean writeAndFlush(Channel ch, String command, Object response) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		try {
			String data = SerializationTools.getInstance().transferToData(response);
			FullHttpResponse res = new DefaultFullHttpResponse(
					HTTP_1_1, OK, Unpooled.wrappedBuffer(data.getBytes("UTF-8")));
			res.headers().set(CONTENT_TYPE, "text/plain");
			res.headers().set(Names.COOKIE,"testcookid");
			res.headers().set(CONTENT_LENGTH,
					res.content().readableBytes());
			res.headers().set(CONNECTION, Values.KEEP_ALIVE);
			 //if (HttpHeaders.isKeepAlive(req)) {
			 //   response.headers().set(CONNECTION, Values.KEEP_ALIVE);
			 //}
			ch.writeAndFlush(res);
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
