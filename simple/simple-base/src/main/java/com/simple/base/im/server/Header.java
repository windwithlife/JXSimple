package com.simple.base.im.server;


public class Header implements Cloneable {
	public static final int HEAD_LENGHT = 27;
	/** 数据包长 **/
    private int length = 0;
    /** 命令 **/
    private int command;
    /** 数据编码格式。已定义：0：UTF-8，1：GBK，2：GB2312，3：ISO8859-1 **/
    private byte encode = 0;
    /** 加密类型。0表示不加密 **/
    private byte encrypt = 0;
    /** 用于扩展协议。暂未定义任何值 **/
    private byte extend1 = 0;
    /** 会话ID 16长度**/
    private String sessionid; 
   
    @Override
    public Header clone() {
        try {
            return (Header) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
 
    public Header() {
 
    }

    public Header(byte encode, byte encrypt, byte extend1,String session,
             int length, int command) {
        this.encode = encode;
        this.encrypt = encrypt;
        this.extend1 = extend1;
        this.sessionid = session;
        this.length = length;
        this.command = command;
    }
    public Header(int commandId,String session, int length) {
        this.sessionid = session;
        this.length = length;
        this.command = commandId;
    }
    public static String creatSession(){
    	String stime = String.valueOf(System.currentTimeMillis());
      	//System.out.println("created session:"+ stime);
        String s = "***" + stime;	
        if (s.length() > 16){
        	return s.substring(0,16);
        }else{
        	return s;
        }
    	
    }
    public byte getEncode(){
    	return this.encode;
    }
 
    public byte getEncrypt(){
    	return this.encrypt;
    }
    
    public byte getExtend1(){
    	return this.extend1;
    }
    
    public String getSessionid(){
    	//return this.sessionid;
    	return this.sessionid;
    	
    }
    public int getCommand(){
    	return this.command;
    }
    public int getLength(){
    	return this.length;
    }
    public void setLenth(int len){
    	this.length = len;
    }
    public int getDataLength(){
    	return this.length - HEAD_LENGHT;
    }
    @Override
    public String toString() {
        return "header [encode=" + encode + ",encrypt=" + encrypt + ",extend1="
                + extend1 + ",sessionid=" + sessionid
                + ",length=" + length + ",commandId=" + command + "]";
    }
 
}