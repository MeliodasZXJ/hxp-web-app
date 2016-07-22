package test;

import java.util.Map;

import com.alibaba.fastjson.JSON;

/** 
* @author  作者 E-mail: liuyang
* @date 创建时间：2016年7月21日 下午11:04:33 
* @version 2.0 
* @parameter  
* @since  
* @return  
*/
public class Test {

	public static void main(String[] args) {
	

		
	
	//	String aa = "{ \"code\":\"2000\",\"message\":\"患者端查询视频详情成功.\", \"returnStatus\":true,\"total\":0,\"value\":{\"collNumber\":null,\"commentNumber\":null,\"createTime\":\"2016-05-12 15:25:18\",\"id\":40,\"image\":\"shopLevelIcon20160512152518.png\", \"videoType\":\"1\"} }";
	
		
		String bb = "{\"content\":\"aaaaaaaaa\",\"extra\":\"657359288ccd4e5d9a130d520048efba\",\"imageUri\":\"http:www.baidu.com\"}";

		Map<String,Object> map = (Map<String,Object>)JSON.parse(bb);
		System.out.println(map.keySet());
		
		System.out.println(map.get("content"));
		
	}

}
