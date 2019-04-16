package com.webserver.http;

import java.util.HashMap;
import java.util.Map;

/**
 * HTTP协议规定的相关内容
 * @author Administrator
 *
 */
public class HttpContext {
	/*
	 * 资源后缀名与Content-Type响应头对应值的映射关系
	 * key:资源后缀名
	 * value:Content-Type对应的值
	 */
	private static Map<String,String> mimeMapping = new HashMap<>();
	//初始化所有静态资源
	static {
		initMimeMapping();
	}
	
	private static void initMimeMapping() {
		mimeMapping.put("html", "text/html");
		mimeMapping.put("css", "text/css");
		mimeMapping.put("js", "application/javascript");
		mimeMapping.put("png", "image/png");
		mimeMapping.put("gif", "image/gif");
		mimeMapping.put("jpg", "image/jpeg");
	}
	
	/**
	 * 根据给定的资源后缀名获取对应的Content-Type值
	 * @param ext
	 * @return
	 */
	public static String getMimeType(String ext) {
		return mimeMapping.get(ext);
	}
}
