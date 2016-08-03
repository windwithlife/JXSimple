package com.simple.base.components.nio.framework.stringline;

import com.simple.base.components.nio.framework.Request;
import com.simple.core.util.JsonUtils;
import com.simple.core.util.SerializationTools;

public class StringLineRequest extends Request {
	//public final static String CMD_IOT = "1001";
	@Override
	public Object getRequest(Class<?> cls){
		return JsonUtils.getInstance().toJsonObject((String)input, cls);
    	
    }
	
	public StringLineRequest(String input){
		int command = SerializationTools.getInstance().getCommand(input);
		this.command = command + "";
		this.input = input;
	}
}
