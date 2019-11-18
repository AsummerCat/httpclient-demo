package com.linjingc.httpclientdemo.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Http请求工具类
 * HttpClient
 */
@Log4j2
public class HttpUtils {
	public static String POST_SENT = "http://localhost:8070/sent/postMethod";

	public static String GET_SENT = "http://localhost:8070/sent/getMethod";


	public static void main(String[] args) {
		Map<String, Object> params = new HashMap<>(16);
		params.put("name", "小明");
		params.put("age", "16");
		String result = HttpUtils.doPost(POST_SENT, params);
		System.out.println(result);
	}


	public static String post(String url, JSONObject param) {
		return doPostToJson(url, param);
	}

	public static String post(String url, Map<String, Object> params) {
		return doPost(url, params);
	}









	/**
	 * Http发送json请求
	 *
	 * @param url
	 * @param param
	 * @return
	 */
	private static String doPostToJson(String url, JSONObject param) {
		//返回值
		String result = null;
		// 获取httpClient
		CloseableHttpClient httpclient = HttpClients.createDefault();

		CloseableHttpResponse response = null;
		try {
			//创建post请求
			HttpPost httpPost = new HttpPost(url);
			// 设置请求和传输超时时间
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
			httpPost.setConfig(requestConfig);
			// 提交参数发送请求
			if (param != null && !param.isEmpty()) {
				StringEntity data = new StringEntity(param.toString(), "utf-8");
				// post方法中，加入json数据
				httpPost.setEntity(data);
			}
			//http请求消息头
			httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
			//发起请求
			response = httpclient.execute(httpPost);
			/**请求发送成功，并得到响应**/
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				/**读取服务器返回过来的json字符串数据**/
				result = EntityUtils.toString(response.getEntity());
				return result;
			}else{
				throw new RuntimeException("请求失败返回状态码--->"+response.getStatusLine().getStatusCode());
			}

		} catch (Exception e) {
			log.error("请求失败--->" + url, e);
		} finally {
			try {
				if (response != null) {
					EntityUtils.consume(response.getEntity());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * Http发送普通表单请求
	 *
	 * @param url
	 * @param params
	 * @return
	 */
	private static String doPost(String url, Map<String, Object> params) {
		//返回值
		String result = null;

		// 获取httpClient
		CloseableHttpClient httpclient = HttpClients.createDefault();

		CloseableHttpResponse response = null;
		try {
//创建post请求
			HttpPost httpPost = new HttpPost(url);
			// 设置请求和传输超时时间
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
			httpPost.setConfig(requestConfig);
			// 提交参数发送请求
			if (params != null && !params.isEmpty()) {
				List<NameValuePair> parameForToken = new ArrayList<>();
				//发起post请求所需要的参数，如果有多个，就add多个
				for (Map.Entry<String, Object> entry : params.entrySet()) {
					//发起post请求所需要的参数，如果有多个，就add多
					parameForToken.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
				}
				//封装FORM表单实体对象,作用传递参数    需要指定UTF-8
				UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(parameForToken, "utf-8");
				httpPost.setEntity(urlEncodedFormEntity);
			}

			//表单消息头
			httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

			//http请求消息头
			httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
			//发起请求
			response = httpclient.execute(httpPost);
			/**请求发送成功，并得到响应**/
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				/**读取服务器返回过来的json字符串数据**/
				result = EntityUtils.toString(response.getEntity());
				return result;
			}else{
				throw new RuntimeException("请求失败返回状态码--->"+response.getStatusLine().getStatusCode());
			}

		} catch (Exception e) {
			log.error("请求失败--->" + url, e);
		} finally {
			try {
				if (response != null) {
					EntityUtils.consume(response.getEntity());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
