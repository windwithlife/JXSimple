package com.simple.base.im.server;

import java.util.List;

import io.netty.buffer.ByteBuf;

import io.netty.channel.ChannelHandlerContext;

import io.netty.handler.codec.ByteToMessageDecoder;


public class MessageDecoder extends ByteToMessageDecoder {
	
	 /**头文件长度**/
    //public static final int HEAD_LENGHT = 27;
    /** 包头标志 **/
    public static final int PACKAGE_TAG = 0x01;

	

	// 第一个参数为信息最大长度，超过这个长度回报异常，
	// 第二参数为长度属性的起始（偏移）位，我们的协议中长度是0到第3个字节，所以这里写0，
	// 第三个参数为“长度属性”的长度，我们是4个字节，所以写4，
	// 第四个参数为长度调节值，在总长被定义为包含包头长度时，修正信息长度，
	// 第五个参数为跳过的字节数，根据需要我们跳过前4个字节，以便接收端直接接受到不含“长度属性”的内容。

	@Override
	protected void decode(ChannelHandlerContext arg0, ByteBuf arg1,
			List<Object> arg2) throws Exception {
		// TODO Auto-generated method stub
		//because the method is invoked when the channel is inactived! 
		if (arg1.readableBytes() <=0){
			return;
		}
		
       
		ByteBuf buffer = arg1;
		int tag =  buffer.readInt();
        
        if (tag != PACKAGE_TAG) {
        	buffer.resetReaderIndex();
        	//arg0.fireChannelRead(arg1);
        	arg2.add(arg1.readBytes(arg1.readableBytes()));
        	return;
        }

        
		if (buffer.readableBytes() < Header.HEAD_LENGHT) {
			 buffer.resetReaderIndex();
	         System.out.println("need to read more data bytes...");
	         return;
        }
        
		int length = buffer.readInt();
        int commandId = buffer.readInt();
        byte sessionByte[] = new byte[16];
        buffer.readBytes(sessionByte);
        String session = new String(sessionByte);
        
        byte encode = buffer.readByte();
        byte encrypt = buffer.readByte();
        byte extend1 = buffer.readByte();
        
        int dataLength =  (length - Header.HEAD_LENGHT);
       
        
 
        if (buffer.readableBytes() < dataLength) {
            buffer.resetReaderIndex();
            System.out.println("data have error format or need to read more data bytes...");
            return;
        }else{
        	ByteBuf buf = buffer.readBytes(dataLength); 
        	String data = buf.toString(io.netty.util.CharsetUtil.UTF_8);
        	 
            Header header = new Header(encode, encrypt, extend1, session,
                     length, commandId);
            
            Message message = new Message(header, data);
    		
    		arg2.add(message);
        }
        
	}


}