package com.simple.base.im.server;




import com.simple.base.im.service.DispatchCenter;
import com.simple.base.im.service.SpringContext;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.string.StringDecoder;
//import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.CharsetUtil;

public class NettyServer implements Runnable{
	private boolean g_bRunning = false;

	private int port;
	private int devicePort;
	private int devicePort2;
	public NettyServer(int port) {

		this.port = port;
		this.devicePort = port + 1;
		this.devicePort2 = port + 2;

	}
    public void setPort(int port){
    	this.port = port;
    	this.devicePort = port + 1;
		this.devicePort2 = port + 2;
    }
	public NettyServer(){}
	private void runSimple() throws Exception{
		
		EventLoopGroup bossGroup = new NioEventLoopGroup();

		EventLoopGroup workerGroup = new NioEventLoopGroup();

		

		try {
			
			ServerBootstrap b = new ServerBootstrap();
           
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() { // (4)

				@Override
				public void initChannel(SocketChannel ch) throws Exception {
				
					ch.pipeline().addLast("MessageDecoder", new MessageDecoder());
	                ch.pipeline().addLast("MessageEncoder", new MessageEncoder());
					ch.pipeline().addLast(new HttpServerCodec());
					ch.pipeline().addLast(new ChunkedWriteHandler());
					ch.pipeline().addLast(new HttpObjectAggregator(64*1024));
					
					ch.pipeline().addLast("aggregator", new WebSocketServerHandler());
					ch.pipeline().addLast("MessageHandler", new ConnectionHandler()); 
					
				}

			});           
			
			b.option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true);
			ChannelFuture fb = b.bind(port).sync();
			fb.channel().closeFuture().sync();

		} finally {

		
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();

		}
	}
	public void run() {

		
		EventLoopGroup bossGroup = new NioEventLoopGroup();

		EventLoopGroup workerGroup = new NioEventLoopGroup();

		System.out.println("消息服务启动在：" + port + "\r\n即时消息(IM)服务启动在：" + this.devicePort + "\r\n设备服务启动在：" + this.devicePort2);
		//System.out.println("应用服务启动在：" + port + "\r\n设备服务启动在：" + this.devicePort);

		try {

			

			ServerBootstrap a = new ServerBootstrap();
			ServerBootstrap b = new ServerBootstrap();
			ServerBootstrap c = new ServerBootstrap();

			a.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					
					ch.pipeline().addLast("MessageDecoder", new MessageDecoder());
	                ch.pipeline().addLast("MessageEncoder", new MessageEncoder());
                    ch.pipeline().addLast("MessageHandler", new ConnectionHandler());           
			   }
			});
			
			c.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					
					ch.pipeline().addLast(new LineBasedFrameDecoder(32768));
					ch.pipeline().addLast(new StringLineEncoder());
					ch.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
                    ch.pipeline().addLast("MessageHandler", new StringLineServerHandler());           
			   }
			});
			
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() { // (4)

				@Override
				public void initChannel(SocketChannel ch) throws Exception {
				
					ch.pipeline().addLast(new HttpServerCodec());
					ch.pipeline().addLast(new ChunkedWriteHandler());
					ch.pipeline().addLast(new HttpObjectAggregator(64*1024));
					
					ch.pipeline().addLast("aggregator", new WebSocketServerHandler());
					
				}

			});           
			
			a.option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true);
			b.option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true);
			c.option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true);
			
			ChannelFuture fa = a.bind(devicePort).sync();
			ChannelFuture fb = b.bind(port).sync();
			ChannelFuture fc = c.bind(devicePort2).sync();
			fa.channel().closeFuture().sync();
			fb.channel().closeFuture().sync();
			fc.channel().closeFuture().sync();

		}catch(Exception e){
			e.printStackTrace();
		}
		finally {

		
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();

		}

	}

	public void startServer(){
		if (g_bRunning){return;}
		DispatchCenter.getInstance().LoadServiceCenter();
		try{
			Thread t = new Thread(this);
			t.start();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			g_bRunning = true;
		}
		
	}
	public static void main(String[] args) throws Exception {

		//DOMConfigurator.configureAndWatch("config/log4j.xml");
	
		int port;

		if (args.length > 0) {

			port = Integer.parseInt(args[0]);

		} else {

			port = 8000;

		}
		
		//this.context =SpringContext.createFileContext(null);
		DispatchCenter.getInstance().setApplicationSpringContext(SpringContext.createFileContext(null));
		NettyServer server = new NettyServer(port);
		server.startServer();
		
		
		
	}

}
