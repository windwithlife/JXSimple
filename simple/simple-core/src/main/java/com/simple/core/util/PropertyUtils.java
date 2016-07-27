/*
 * $HeadURL: https://springside.googlecode.com/svn/springside3/trunk/modules/core/src/main/java/org/springside/modules/utils/PropertyUtils.java $
 * $Id: PropertyUtils.java 1075 2010-05-12 15:04:41Z calvinxiu $
 * Copyright (c) 2010 by Ericsson, all rights reserved.
 */

package com.simple.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.util.PropertiesPersister;

/**
 * Properties Util鍑芥暟.
 * 
 * @author rani
 */
@SuppressWarnings("all")
public class PropertyUtils {

	private static final String DEFAULT_ENCODING = "UTF-8";

	private static Logger logger = LoggerFactory.getLogger(PropertyUtils.class);

	private static PropertiesPersister propertiesPersister = new DefaultPropertiesPersister();
	private static ResourceLoader resourceLoader = new DefaultResourceLoader();

	/**
	 * 杞藉叆澶氫釜properties鏂囦欢, 鐩稿悓鐨勫睘鎬ф渶鍚庤浇鍏ョ殑鏂囦欢灏嗕細瑕嗙洊涔嬪墠鐨勮浇
	 * 
	 * @see org.springframework.beans.factory.config.PropertyPlaceholderConfigurer
	 */
	public static Properties loadProperties(String... locations)
			throws IOException {
		Properties props = new Properties();

		for (String location : locations) {

			logger.debug("Loading properties file from classpath:" + location);

			InputStream is = null;
			try {
				Resource resource = resourceLoader.getResource(location);
				is = resource.getInputStream();
				propertiesPersister.load(props, new InputStreamReader(is,
						DEFAULT_ENCODING));
			} catch (IOException ex) {
				logger.info("Could not load properties from classpath:"
						+ location + ": " + ex.getMessage());
			} finally {
				if (is != null) {
					is.close();
				}
			}
		}
		return props;
	}

	/**
	 * 鍙杕ap2涓殑String鏁扮粍鐨勭涓�涓�硷紝鏀剧疆涓猰ap1涓�
	 * 
	 * @param map1
	 * @param map2
	 * @param isConvert
	 *            鏄惁杞崲缂栫爜
	 * @throws UnsupportedEncodingException
	 */
	public static void map2map(Map map1, Map map2, boolean isConvert)
			throws Exception {
		for (Iterator i = map2.keySet().iterator(); i.hasNext();) {
			String key = (String) i.next();
			if (null != key) {
				String[] value = (String[]) map2.get(key);
				if (null != value && value.length > 0)
					map1.put(key,
							(isConvert ? URLDecoder.decode(value[0], "utf-8")
									: value[0]));
			}
		}
	}
}
