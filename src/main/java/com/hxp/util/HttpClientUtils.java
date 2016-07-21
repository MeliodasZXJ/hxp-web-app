package com.hxp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

public class HttpClientUtils {
    private static final CloseableHttpClient httpClient;

    public static final String CHARSET_UTF8 = "UTF-8";
    private static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

    static {
        RequestConfig config = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(15000).build();
        httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
    }

    public static String doGet(String url, Map<String, String> params) {
        return doGet(url, params, CHARSET_UTF8);
    }

    public static String doPost(String url, Map<String, String> params) {
        return doPost(url, params, null, CHARSET_UTF8);
    }

    public static String doPost(String url, Map<String, String> params, Map<String, String> headers) {
        return doPost(url, params, headers, CHARSET_UTF8);
    }

    /**
     * HTTP Get 获取内容
     * @param url  请求的url地址 ?之前的地址
     * @param params 请求的参数
     * @param charset    编码格式
     * @return    页面内容
     */
    public static String doGet(String url, Map<String, String> params, String charset) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        try {
            if (params != null && !params.isEmpty()) {
                List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String value = entry.getValue();
                    if (value != null) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
                url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
            }
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpGet.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, "utf-8");
            }
            EntityUtils.consume(entity);
            response.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * HTTP Post 获取内容
     * @param url  请求的url地址 ?之前的地址
     * @param params 请求的参数
     * @param charset    编码格式
     * @return    页面内容
     */
    public static String doPost(String url, Map<String, String> params, Map<String, String> headers, String charset) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        try {
            HttpPost httpPost = new HttpPost(url);
            List<NameValuePair> pairs = null;
            if (params != null && !params.isEmpty()) {
                pairs = new ArrayList<NameValuePair>(params.size());
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String value = entry.getValue();
                    if (value != null) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
            }
            if (headers != null && !headers.isEmpty()) {
                Set<String> keys = headers.keySet();
                for (Iterator<String> i = keys.iterator(); i.hasNext();) {
                    String key = (String) i.next();
                    httpPost.addHeader(key, headers.get(key));

                }
            }
            if (pairs != null && pairs.size() > 0) {
                httpPost.setEntity(new UrlEncodedFormEntity(pairs, CHARSET_UTF8));
            }
            CloseableHttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, "utf-8");
            }
            EntityUtils.consume(entity);
            response.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 数据请求接口
     * @param httpsUrl
     * @param xmlStr
     * @param isProxy
     * @param proxyHost
     * @param proxyPort
     * @return
     */
    public static String post(String httpsUrl, String xmlStr,boolean isProxy, String proxyHost,int proxyPort){

        SSLContext sslContext=null;
        try {
            URL url = new URL(httpsUrl);
            HttpURLConnection conn = null;
            if(isProxy){
                @SuppressWarnings("static-access")
                Proxy proxy = new Proxy(Proxy.Type.DIRECT.HTTP,new InetSocketAddress(proxyHost, proxyPort));
                conn =(HttpURLConnection) url.openConnection(proxy);
            }else {
                conn=(HttpURLConnection) url.openConnection();
            }
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");

            conn.setRequestProperty("Content-Length", String.valueOf(xmlStr.getBytes().length));
            conn.setUseCaches(false);
            logger.info("请求地址:===" + httpsUrl);
            logger.info("请求参数:====" + xmlStr);
            //设置为gbk可以解决服务器接收时读取的数据中文乱码问题
            conn.getOutputStream().write(xmlStr.getBytes("utf-8"));
            conn.getOutputStream().flush();
            conn.getOutputStream().close();
            // 获取输入流
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = null;
            BufferedReader bufferedReader = null;
            String responseResult = "";
            int code = conn.getResponseCode();
            if (HttpsURLConnection.HTTP_OK == code) {
                inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                bufferedReader = new BufferedReader(inputStreamReader);
                String str = null;
                StringBuffer buffer = new StringBuffer();
                while ((str = bufferedReader.readLine()) != null) {
                    buffer.append(str);
                }
                responseResult = buffer.toString();
                   logger.info("京东返回结果:====" + responseResult);
                return responseResult;
            }
        }catch (MalformedURLException e) {
            logger.error("error1=================================" + e.getMessage(), e);
        } catch (IOException e) {
            logger.error("error2=================================" + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("error3================================="+e.getMessage(), e);
        }
        return null;
    }




    public static CloseableHttpClient getHttpClientInstance() {
        return httpClient;
    }

    public static void main(String[] args) {
        String getData = doGet("http://www.oschina.net/", null);
        System.out.println(getData);
        System.out.println("----------------------分割线-----------------------");
        String postData = doPost("http://www.oschina.net/", null);
        System.out.println(postData);
    }


}
