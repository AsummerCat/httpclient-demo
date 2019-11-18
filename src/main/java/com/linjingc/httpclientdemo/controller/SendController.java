package com.linjingc.httpclientdemo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 接收请求类
 *
 * @author cxc
 */
@RestController
@RequestMapping("sent")
public class SendController {


	@RequestMapping(value = "postMethod", method = RequestMethod.POST)
	public String sentPost(HttpServletRequest request) {
		Map<String, String> parameters = new HashMap<>();
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String parameterName = parameterNames.nextElement();

			parameters.put(parameterName, request.getParameter(parameterName));
			System.out.println("key:" + parameterName + "-----" + "value=" + request.getParameter(parameterName));
		}
		return "SUCCESS";
	}


	@RequestMapping(value = "getMethod", method = RequestMethod.GET)
	public String sentGet(HttpServletRequest request) {
		Map<String, String> parameters = new HashMap<>();
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String parameterName = parameterNames.nextElement();

			parameters.put(parameterName, request.getParameter(parameterName));
			System.out.println("key:" + parameterName + "-----" + "value=" + request.getParameter(parameterName));
		}
		return "SUCCESS";
	}
}
