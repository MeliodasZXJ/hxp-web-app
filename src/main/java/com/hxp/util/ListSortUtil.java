package com.hxp.util;


import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * List按照指定字段排序工具类
 * 
 * @param <T>
 */

public class ListSortUtil<T> {
	private static Logger logger = Logger.getLogger(ListSortUtil.class);
	/**
	 * @param targetList
	 *            目标排序List
	 * @param sortField
	 *            排序字段(实体类属性名)
	 * @param sortMode
	 *            排序方式（asc or desc）
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void sort(List<T> targetList, final String sortField,
			final String sortMode) {

		Collections.sort(targetList, new Comparator() {
			public int compare(Object obj1, Object obj2) {
				int retVal = 0;
				try {
					// 首字母转大写
					String newStr = sortField.substring(0, 1).toUpperCase()
							+ sortField.replaceFirst("\\w", "");
					String methodStr = "get" + newStr;

					Method method1 = ((T) obj1).getClass().getMethod(methodStr, null);
					Method method2 = ((T) obj2).getClass().getMethod(methodStr,null);
					if (sortMode != null && "desc".equals(sortMode)) {
						retVal = method2.invoke(((T) obj2), null).toString().compareTo(method1.invoke(((T) obj1), null).toString()); // 倒序
					} else {
						retVal = method1.invoke(((T) obj1), null).toString().compareTo(method2.invoke(((T) obj2), null).toString()); // 正序
					}
				} catch (Exception e) {
					throw Exceptions.unchecked(e);
				}
				return retVal;
			}
		});
	}
	
	/**
	 * @param targetList
	 *            目标排序List
	 * @param sortField
	 *            排序字段(实体类属性名)
	 * @param sortMode
	 *            排序方式（asc or desc）
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void sortByInt(List<T> targetList, final String sortField,
			final String sortMode) {

		Collections.sort(targetList, new Comparator() {
			public int compare(Object obj1, Object obj2) {
				int retVal = 0;
				try {
					// 首字母转大写
					String newStr = sortField.substring(0, 1).toUpperCase()
							+ sortField.replaceFirst("\\w", "");
					String methodStr = "get" + newStr;

					Method method1 = ((T) obj1).getClass().getMethod(methodStr, null);
					Method method2 = ((T) obj2).getClass().getMethod(methodStr,null);
					if (sortMode != null && "desc".equals(sortMode)) {
						retVal = Integer.parseInt(method2.invoke(((T) obj2), null).toString()) > Integer.parseInt(method1.invoke(((T) obj1), null).toString())?1:-1;
					} else {
						retVal = Integer.parseInt(method2.invoke(((T) obj1), null).toString()) > Integer.parseInt(method1.invoke(((T) obj2), null).toString())?1:-1;
					}
				} catch (Exception e) {
					throw Exceptions.unchecked(e);
				}
				return retVal;
			}
		});
	}
	
	
	/**
	 * @param targetList
	 *            目标排序List
	 * @param sortField
	 *            排序字段(实体类属性名)
	 * @param sortMode
	 *            排序方式（asc or desc） BY tanfy 2015-12-21--价格 使用
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void sortByDouble(List<T> targetList, final String sortField,
			final String sortMode) {
		Collections.sort(targetList, new Comparator() {
			public int compare(Object obj1, Object obj2) {
				int retVal = 0;
				try {
					// 首字母转大写
					String newStr = sortField.substring(0, 1).toUpperCase()
							+ sortField.replaceFirst("\\w", "");
					String methodStr = "get" + newStr;

					Method method1 = ((T) obj1).getClass().getMethod(methodStr, null);
					Method method2 = ((T) obj2).getClass().getMethod(methodStr,null);
					if (sortMode != null && "desc".equals(sortMode)) {
						Object ob1  = method2.invoke(((T) obj1), null) ;
						Object ob2  = method2.invoke(((T) obj2), null) ;
						if(ob1 != null && ob2 != null){
							retVal =  (Double.parseDouble(ob2.toString()) > Double.parseDouble(ob1.toString())?1:-1);
						}
					} else {
						Object ob1  = method2.invoke(((T) obj1), null) ;
						Object ob2  = method2.invoke(((T) obj2), null) ;
						if(ob1 != null && ob2 != null){
							retVal =  (Double.parseDouble(ob1.toString()) > Double.parseDouble(ob2.toString())?1:-1);
						}
					}
				} catch (Exception e) {
					throw Exceptions.unchecked(e);
				}
				return retVal;
			}
		});
	}

}