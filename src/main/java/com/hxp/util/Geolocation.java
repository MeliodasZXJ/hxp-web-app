package com.hxp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

/**
 * 地理位置类
 * @author
 */
public class Geolocation {

	/** IP定位 API URL */
	public static String IP_API_URL_PRE = "http://api.map.baidu.com/location/ip?ak=maK12K2f0MIQqVFEPvq6PlSb&coor=bd09ll&ip=";
	/** GEOCODING API URL 提供从地址到经纬度坐标的转换服务 */
	public static String GEOCODER_API_URL_PRE = "http://api.map.baidu.com/geocoder/v2/?output=json&ak=maK12K2f0MIQqVFEPvq6PlSb&address=";

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = JSON.parseObject(jsonText);
			return json;
		} finally {
			is.close();
		}
	}

	/**
	 * 调用IP定位API
	 * @param ip
	 * @return 地址解析的结果（json格式）
	 */
	public static JSONObject GetPlaceByIp(String ip) throws IOException, JSONException {
		// 这里调用百度的ip定位api服务 详见 http://api.map.baidu.com/lbsapi/cloud/ip-location-api.htm
		JSONObject json = readJsonFromUrl(IP_API_URL_PRE + ip);
		String status = json.get("status").toString();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("status", status);// 状态码
		if ("0".equals(status)) {// 正常
			JSONObject content = (JSONObject) json.get("content");
			String province = ((JSONObject) content.get("address_detail")).get("province").toString();
			jsonObject.put("province", province);
			String city = ((JSONObject) content.get("address_detail")).get("city").toString();
			jsonObject.put("city", city);

			// 经度值
			String longitude;
			// 纬度值
			String latitude;
			longitude = ((JSONObject) content.get("point")).get("x").toString();
			latitude = ((JSONObject) content.get("point")).get("y").toString();
			jsonObject.put("longitude", longitude);
			jsonObject.put("latitude", latitude);
		}
		return jsonObject;
	}

	/**
	 * 调用Geocoding API 从地址到经纬度坐标转换
	 * @param ip
	 * @return 地址解析的结果（json格式）
	 */
	public static JSONObject GetLngLatByAddress(String address) throws IOException, JSONException {
		// 这里调用百度调用Geocoding API服务 详见 http://api.map.baidu.com/lbsapi/cloud/webservice-geocoding.htm
		JSONObject json = readJsonFromUrl(GEOCODER_API_URL_PRE + address);
		String status = json.get("status").toString();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("status", status);// 状态码
		if ("0".equals(status)) {// 正常
			JSONObject location = (JSONObject) ((JSONObject) json.get("result")).get("location");
			// 经度值
			String longitude = location.get("lng").toString();
			// 纬度值
			String latitude = location.get("lat").toString();
			jsonObject.put("longitude", longitude);
			jsonObject.put("latitude", latitude);
		}
		return jsonObject;
	}

	public static void main(String[] args) throws IOException, JSONException {
		// 这里调用百度的ip定位api服务 详见
		JSONObject json = GetPlaceByIp("114.113.116.130");
		System.out.println(json.toString());
		System.out.println("城市：" + json.get("city"));
		System.out.println("经度：" + json.get("longitude") + "，纬度：" + json.get("latitude"));
		// 这里调用百度调用Geocoding API服务
		JSONObject json1 = GetLngLatByAddress("北京市朝阳区丽泽中一路1号博雅国际中心");
		System.out.println(json1.toString());
	}

}
