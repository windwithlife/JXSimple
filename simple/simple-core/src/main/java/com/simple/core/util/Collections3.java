/**
 * Copyright (c) 2005-2012 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.simple.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Collections宸ュ叿闆�.
 * 
 * 鍦↗DK鐨凜ollections鍜孏uava鐨凜ollections2鍚�, 鍛藉悕涓篊ollections3.
 * 
 * 鍑芥暟涓昏鐢变袱閮ㄥ垎缁勬垚锛屼竴鏄嚜鍙嶅皠鎻愬彇鍏冪礌鐨勫姛鑳斤紝浜屾槸婧愯嚜Apache Commons Collection, 浜夊彇涓嶇敤鍦ㄩ」鐩噷寮曞叆瀹冦��
 * 
 * @author calvin
 */
public class Collections3 {

	/**
	 * 鎻愬彇闆嗗悎涓殑瀵硅薄鐨勪袱涓睘鎬�(閫氳繃Getter鍑芥暟), 缁勫悎鎴怣ap.
	 * 
	 * @param collection
	 *            鏉ユ簮闆嗗悎.
	 * @param keyPropertyName
	 *            瑕佹彁鍙栦负Map涓殑Key鍊肩殑灞炴�у悕.
	 * @param valuePropertyName
	 *            瑕佹彁鍙栦负Map涓殑Value鍊肩殑灞炴�у悕.
	 */
	public static Map extractToMap(final Collection collection, final String keyPropertyName,
			final String valuePropertyName) {
		Map map = new HashMap(collection.size());

		try {
			for (Object obj : collection) {
				map.put(PropertyUtils.getProperty(obj, keyPropertyName),
						PropertyUtils.getProperty(obj, valuePropertyName));
			}
		}
		catch (Exception e) {
			throw Reflections.convertReflectionExceptionToUnchecked(e);
		}

		return map;
	}

	/**
	 * 鎻愬彇闆嗗悎涓殑瀵硅薄鐨勪竴涓睘鎬�(閫氳繃Getter鍑芥暟), 缁勫悎鎴怢ist.
	 * 
	 * @param collection
	 *            鏉ユ簮闆嗗悎.
	 * @param propertyName
	 *            瑕佹彁鍙栫殑灞炴�у悕.
	 */
	public static List extractToList(final Collection collection, final String propertyName) {
		List list = new ArrayList(collection.size());

		try {
			for (Object obj : collection) {
				list.add(PropertyUtils.getProperty(obj, propertyName));
			}
		}
		catch (Exception e) {
			throw Reflections.convertReflectionExceptionToUnchecked(e);
		}

		return list;
	}

	/**
	 * 鎻愬彇闆嗗悎涓殑瀵硅薄鐨勪竴涓睘鎬�(閫氳繃Getter鍑芥暟), 缁勫悎鎴愮敱鍒嗗壊绗﹀垎闅旂殑瀛楃涓�.
	 * 
	 * @param collection
	 *            鏉ユ簮闆嗗悎.
	 * @param propertyName
	 *            瑕佹彁鍙栫殑灞炴�у悕.
	 * @param separator
	 *            鍒嗛殧绗�.
	 */
	public static String extractToString(final Collection collection, final String propertyName, final String separator) {
		List list = extractToList(collection, propertyName);
		return StringUtils.join(list, separator);
	}

	/**
	 * 杞崲Collection鎵�鏈夊厓绱�(閫氳繃toString())涓篠tring, 涓棿浠� separator鍒嗛殧銆�
	 */
	public static String convertToString(final Collection collection, final String separator) {
		return StringUtils.join(collection, separator);
	}

	/**
	 * 杞崲Collection鎵�鏈夊厓绱�(閫氳繃toString())涓篠tring,
	 * 姣忎釜鍏冪礌鐨勫墠闈㈠姞鍏refix锛屽悗闈㈠姞鍏ostfix锛屽<div>mymessage</div>銆�
	 */
	public static String convertToString(final Collection collection, final String prefix, final String postfix) {
		StringBuilder builder = new StringBuilder();
		for (Object o : collection) {
			builder.append(prefix).append(o).append(postfix);
		}
		return builder.toString();
	}

	/**
	 * 鍒ゆ柇鏄惁涓虹┖.
	 */
	public static boolean isEmpty(Collection collection) {
		return (collection == null || collection.isEmpty());
	}

	/**
	 * 鍒ゆ柇鏄惁涓虹┖.
	 */
	public static boolean isNotEmpty(Collection collection) {
		return (collection != null && !(collection.isEmpty()));
	}

	/**
	 * 鍙栧緱Collection鐨勭涓�涓厓绱狅紝濡傛灉collection涓虹┖杩斿洖null.
	 */
	public static <T> T getFirst(Collection<T> collection) {
		if (isEmpty(collection)) {
			return null;
		}

		return collection.iterator().next();
	}

	/**
	 * 鑾峰彇Collection鐨勬渶鍚庝竴涓厓绱� 锛屽鏋渃ollection涓虹┖杩斿洖null.
	 */
	public static <T> T getLast(Collection<T> collection) {
		if (isEmpty(collection)) {
			return null;
		}

		// 褰撶被鍨嬩负List鏃讹紝鐩存帴鍙栧緱鏈�鍚庝竴涓厓绱� 銆�
		if (collection instanceof List) {
			List<T> list = (List<T>) collection;
			return list.get(list.size() - 1);
		}

		// 鍏朵粬绫诲瀷閫氳繃iterator婊氬姩鍒版渶鍚庝竴涓厓绱�.
		Iterator<T> iterator = collection.iterator();
		while (true) {
			T current = iterator.next();
			if (!iterator.hasNext()) {
				return current;
			}
		}
	}

	/**
	 * 杩斿洖a+b鐨勬柊List.
	 */
	public static <T> List<T> union(final Collection<T> a, final Collection<T> b) {
		List<T> result = new ArrayList<T>(a);
		result.addAll(b);
		return result;
	}

	/**
	 * 杩斿洖a-b鐨勬柊List.
	 */
	public static <T> List<T> subtract(final Collection<T> a, final Collection<T> b) {
		List<T> list = new ArrayList<T>(a);
		for (T element : b) {
			list.remove(element);
		}

		return list;
	}

	/**
	 * 杩斿洖a涓巄鐨勪氦闆嗙殑鏂癓ist.
	 */
	public static <T> List<T> intersection(Collection<T> a, Collection<T> b) {
		List<T> list = new ArrayList<T>();

		for (T element : a) {
			if (b.contains(element)) {
				list.add(element);
			}
		}
		return list;
	}
}
