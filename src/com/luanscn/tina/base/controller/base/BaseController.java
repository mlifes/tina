package com.luanscn.tina.base.controller.base;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jfinal.core.Controller;

/**
 * 控制层抽象类：所有控制类都必须继承此类
 * */
public abstract class BaseController extends Controller{
	/**
	 * 消息发生器
	 * */
	protected Logger logger = Logger.getLogger(getClass());
	/**
	 * 取得参数列表
	 * */
	protected Map<String, Object> paramsMap;
	
	public void index() {
		
		getResponse().addHeader("Access-Control-Allow-Origin","*");	
		Map<String, Object> result = null;
		String contentType = getRequest().getContentType();
		System.out.println("contentType:"+contentType);
		if(contentType != null && contentType.indexOf("multipart")==0){
			getFiles();
		}
		
		try {
			
			//取得参数并解密，存到paramsMap
			getParams();

			renderJson(result);
			
		} catch (Exception ex) {
			
			ex.printStackTrace();
			result = new HashMap<>();
			result.put("status", 0);
			renderJson(result);
		}
	}
	
	protected void getParams() throws Exception{
		
		try {
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	protected Object getParamObj(String key) {
		return  paramsMap.get(key);
	}
	
	protected String getParamStr(String key) {
		Object object = paramsMap.get(key);
		if(object == null) {
			return null;
		}
		
		return object.toString();
	}

	protected Integer getParamInt(String key) {
		return (new  Double((Double)paramsMap.get(key))).intValue();
	}
	
	protected Double getParamDouble(String key){
		return (Double) paramsMap.get(key);
	}
	
	protected boolean isParamExist(String key) {
		return  paramsMap.containsKey(key);
	}
	
	/**
	 * 获取当前调用方法的名称
	 * @return
	 */
	protected String getFunctionName() {
		return (String) paramsMap.get("functionName");
	}
}
