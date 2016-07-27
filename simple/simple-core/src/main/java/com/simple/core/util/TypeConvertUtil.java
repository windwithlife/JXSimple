package com.simple.core.util;

import org.apache.commons.lang3.StringUtils;

public class TypeConvertUtil {

	public static Integer transferToIntType(Object obj) {
		Integer val = null;
		if (null == obj) {
			return null;
		}
		if (obj instanceof Integer) {
			val = (Integer) obj;
		}
		else if (obj instanceof Double) {
			Double t = (Double) obj;
			val = new Integer(t.intValue());
		}
		else if (obj instanceof String) {
			String t = (String) obj;
			if (StringUtils.isBlank(t)) {
				val = 0;
			}else{
				val = Integer.parseInt(t);
			}
		}
		return val;
	}

}
