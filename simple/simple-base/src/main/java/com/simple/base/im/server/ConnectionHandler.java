package com.simple.base.im.server;


import com.simple.core.util.SerializationTools;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
//import io.netty.handler.codec.http.websocketx.*;
//import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class ConnectionHandler extends SimpleChannelInboundHandler {
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
			throws Exception {
		// TODO Auto-generated method stub
		super.userEventTriggered(ctx, evt);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub
		//super.exceptionCaught(ctx, cause);
		System.out.println("have exception....");//cause.printStackTrace();
	}

	//private RequestCachePool dispatcher = new RequestCachePool();
	
	public ConnectionHandler(){
		
		
		//dispatcher.init();
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelActive(ctx);
		
		ConnectionManager.getInstance().putConnection(ctx.channel(), new MessageConnection(ctx.channel()));
		
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		
		// TODO Auto-generated method stub
		super.channelInactive(ctx);
		ConnectionManager.getInstance().removeConnection(ctx.channel());
		
	}

	@Override
	public void channelRead(ChannelHandlerContext arg0, Object msg)
			throws Exception {
		// TODO Auto-generated method stub
		if (null == msg){
			return;
		}
		//super.channelRead(arg0, msg);
	   // System.out.println("server side channel read....");
	    if (msg instanceof String){
	    	System.out.println("recieved websocket package from:" + arg0.channel().remoteAddress().toString());
			String str = (String)msg;
			int command = SerializationTools.getInstance().getCommand(str);
			Object obj = SerializationTools.getInstance().transferToObjectByRequestKey(str,String.valueOf(command));
			Request req= new Request(obj);
			req.setChannel(arg0.channel());
			req.setCommand(command + "");
		
			
			RequestCachePool.getInstance().pushToPool(req);
	    }
	    if (msg instanceof Message){
			Header header = ((Message) msg).getHeader();
			int command = header.getCommand();
	    	Object obj = SerializationTools.getInstance().transferToObjectByRequestKey(((Message) msg).getData(),String.valueOf(command));
			Request req = new Request(obj);
			req.setChannel(arg0.channel());
			req.setCommand(command + "");
			req.setSession(header.getSessionid());
			if (((Message) msg).getHeader().getCommand() == 0){
				System.out.println("recieved device ping package!");
				arg0.writeAndFlush(msg);
				return;
			}
				
			RequestCachePool.getInstance().pushToPool(req);
			
		}else{
			
		}
	}

	@Override
	protected void channelRead0(ChannelHandlerContext arg0, Object arg1)
			throws Exception {
	
	}

}
