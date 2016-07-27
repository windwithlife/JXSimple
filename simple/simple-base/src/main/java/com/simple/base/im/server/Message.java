package com.simple.base.im.server;

import com.simple.core.util.SerializationTools;

public class Message {
    /** 头消息 **/
    private Header header;
    /** 数据 **/
    private String data;
 
    public Message() {
 
    }
 
    public Message(Header header) {
        this.header = header;
    }
 
    public Message(Header header, String data) {
        this.header = header;
        this.data = data;
    }
    
    public Header getHeader(){
    	return this.header;
    }
    public String getData(){
    	return data;
    }
    public boolean isPing(){
    	if (this.header.getCommand() == 0){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    public static Message createMsg(int command, Object input){
   
    	String data = null;
    	Header h  = null;
    	if (input != null){
    		data = SerializationTools.getInstance().transferToData(input);
    	    h    = new Header(command, Header.creatSession(), data.getBytes().length + Header.HEAD_LENGHT);
    	}else{
    		h    = new Header(command, Header.creatSession(), 0 + Header.HEAD_LENGHT);
    		data = null;
    	}
    	
    	
    	
    	return new Message(h, data);
    	
    }
}