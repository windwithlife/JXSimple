package com.simple.base.im.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;




public class MessageEncoder extends MessageToByteEncoder<Message> {

	
	@Override
	public boolean acceptOutboundMessage(Object msg) throws Exception {
		// TODO Auto-generated method stub
		//return super.acceptOutboundMessage(msg);
		if (!(msg instanceof Message)) {
			 return false;
		}else{
			return true;
		}
	}

	

	@Override
	protected void encode(ChannelHandlerContext arg0, Message arg1, ByteBuf out)
			throws Exception {
		// TODO Auto-generated method stub
		Object msg = arg1;
		
		 
        Message message = (Message) msg;
        String buffer = (String)message.getData();
        Header header = message.getHeader();
 
        //ChannelBuffer allBuffer = ChannelBuffers.dynamicBuffer();
        out.writeInt(MessageDecoder.PACKAGE_TAG);
        out.writeInt(header.getLength());
        out.writeInt(header.getCommand());
        out.writeBytes(header.getSessionid().getBytes());
       
        out.writeByte(header.getEncode());
        out.writeByte(header.getEncrypt());
        out.writeByte(header.getExtend1());
   
        
        if ((buffer != null) && (header.getLength()  >0)){
        	out.writeBytes(buffer.getBytes());
        }
        
	}

}


