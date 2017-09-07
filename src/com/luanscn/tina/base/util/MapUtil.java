package com.luanscn.tina.base.util;

import java.util.Map;

/**
 * MapUtil
 * */
public class MapUtil {
	
	public static Object getMapObj(Map<String,Object> map,String key){
		return  map.get(key);
	}
	
	public static String getMapStr(Map<String,Object> map,String key){
		
		Object object = map.get(key);
		
		if(object == null) {
			return null;
		}
		
		return object.toString();
	}
	
	public static Integer getMapInt(Map<String,Object> map,String key){
		Object val = map.get(key);
		if (val instanceof Double){
			return ((Double)val).intValue();
		}else if (val instanceof Float){
			return ((Float)val).intValue();
		}else if(val instanceof Long){
			return ((Long)val).intValue();
		}else if(val instanceof Integer){
			return ((Integer)val).intValue();
		}else if(val instanceof String){
			return Integer.valueOf((String)val).intValue();
		}else{
			return null;
		}
	}
	
	public static Long getMapLong(Map<String,Object> map,String key){
		Object val = map.get(key);
		if (val instanceof String){
			return Long.valueOf((String)val);
		}else if(val instanceof Long){
			return (Long)val;
		}else if(val instanceof Integer){
			return Long.valueOf((Integer)val);
		}else{
			return Long.valueOf(val.toString());
		}
	}
	
	public static Double getMapDouble(Map<String,Object> map,String key){
		return (Double) map.get(key);
	}
	
	public static boolean isMapExist(Map<String,Object> map,String key){
		return  map.containsKey(key);
	}
}
