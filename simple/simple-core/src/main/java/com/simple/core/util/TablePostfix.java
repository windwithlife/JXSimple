package com.simple.core.util;

import java.util.Date; 
import java.text.SimpleDateFormat;

/**
 * 鏍规嵁鏃堕棿鏍煎紡鑾峰彇鏃堕棿
 * @author peter
 *
 */
public class TablePostfix {

	private static TablePostfix instance = new TablePostfix();
	private static SimpleDateFormat df ;
	private String postfixFormat="";
	private TablePostfix()  {}
	public static TablePostfix getInstance()  {
	    return instance; 
	}	    
	
	private SimpleDateFormat getSimpleDateFormat(String strTimeFormat) {
			if (postfixFormat != strTimeFormat) {
				postfixFormat = strTimeFormat;
				df = new SimpleDateFormat(strTimeFormat);
			}
			
			if (df == null) 
				df = new SimpleDateFormat(strTimeFormat);
		return df;
	}
	
	private String getTablePostfix(String strTimeFormat) {
	      return getSimpleDateFormat(strTimeFormat).format(new Date());// new Date()涓鸿幏鍙栧綋鍓嶇郴缁熸椂闂�	      
	}
	
	public String getUserDetailsTablePostfix(String strPostfixFormat) {
		return getTablePostfix(strPostfixFormat);
	}
	
	public String getUserPrimaryTablePostfix(String strPostfixFormat) {
		return getTablePostfix(strPostfixFormat);
	}
}
