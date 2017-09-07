package com.luanscn.tina.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class StringUtils {

	private static String[] randomList1 = 
			new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", 
					"k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
	
	private static String[] randomList2 = 
			new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", 
					"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
	
	private static String[] randomList3 = 
			new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
	
	// 首字母转小写
	public static String toLowerCaseFirstOne(String s) {
		if (Character.isLowerCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder())
					.append(Character.toLowerCase(s.charAt(0)))
					.append(s.substring(1)).toString();
	}

	// 首字母转大写
	public static String toUpperCaseFirstOne(String s) {
		if (Character.isUpperCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder())
					.append(Character.toUpperCase(s.charAt(0)))
					.append(s.substring(1)).toString();
	}
	
	/**
	 * 在指定的对象的前面补零
	 * @param src 数据源
	 * @param len 指定长度
	 * @return
	 */
	public static String fillZero(Long src, int len){
		if(src == null) {
			return fillZero(String.valueOf(""), len);
		}else{
			return fillZero(String.valueOf(src), len);
		}
	}
	
	public static String fillZero(long src, int len){
		return fillZero(String.valueOf(src), len);
	}
	
	/**
	 * 在指定的对象的前面补零
	 * @param src 数据源
	 * @param len 指定长度
	 * @return
	 */
	public static String fillZero(String src, int len){
		
		if(isBlank(src)) {
			src = "";
		}
		
		StringBuffer buffer = new StringBuffer();
		buffer.append(src);
		while (buffer.length() < len){
			buffer.insert(0, "0");
		}
		return buffer.toString();
	}
	
	/**
	 * 测试用方法
	 * @param param
	 */
	public static void main(String[] param){
		System.out.println(getRandomPasswd());
		System.out.println(fillZero(1111, 10));
		Long n = null;
		System.out.println(fillZero(n, 10));
	}
	
	/**
	 * 判断是否是整数型
	 * @param str
	 * @return
	 */
	public static boolean isInteger(String str){
		if (str == null) return false;
		try{
			Integer.valueOf(str);
		}catch(Throwable tr){
			return false;
		}
		return true;
	}
	
	/**
	 * 生成一个类的uid
	 * @return
	 */
	public static String getSerialVersionUID(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("-");
		int rand = 0;
		for (int i = 0; i < 18; i++){
			rand = new Double(Math.random() * 10).intValue();
			if (rand == 0 && i == 0){
				i--;
				continue;
			}
			buffer.append(rand);
		}
		buffer.append("L");
		
		return buffer.toString();
	}
	
	public static String getRandomFileName(){
		//尽管Math.ceil返回当前数值向上取整的数值，但仍为double型，因此转int型去掉小数点
		SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd-HHmmssSSS-");
		return formater.format(new Date()) + String.valueOf((int)Math.ceil(Math.random() * 100));
	}
	
	public static String getRandomString(int len){
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < len; i++){
			int j = new Double(Math.random() * 3).intValue();
			while (j > 3){
				j = new Double(Math.random() * 3).intValue();
			}
			String[] list = null;
			if (j == 0){
				list = randomList1;
			}else if(j == 1){
				list = randomList2;
			}else{
				list = randomList3;
			}
			int k = new Double(Math.random() * list.length).intValue();
			while (k >= list.length){
				k = new Double(Math.random() * list.length).intValue();
			}
			buffer.append(list[k]);
		}
		return buffer.toString();
	}
	
	public static String getRandomPasswd(){
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < 8; i++){
			int j = new Double(Math.random() * 3).intValue();
			while (j > 3){
				j = new Double(Math.random() * 3).intValue();
			}
			String[] list = null;
			if (j == 0){
				list = randomList1;
			}else if(j == 1){
				list = randomList2;
			}else{
				list = randomList3;
			}
			int k = new Double(Math.random() * list.length).intValue();
			while (k >= list.length){
				k = new Double(Math.random() * list.length).intValue();
			}
			buffer.append(list[k]);
		}
		return buffer.toString();
	}
	
	public static Date toTimeStamp(String value){
		if (value == null || "".equals(value.trim()))
			return null;
		try {
			SimpleDateFormat cvtFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return cvtFormatter.parse(value);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 获取扩展名
	 * @return
	 */
	public static String getExtendName(String str){
		if(isBlank(str) || str.indexOf(".") < 0){
			return "";
		}
		
		return str.substring(str.lastIndexOf(".") + 1);
	}
	
	/**
	 * 获取带点的扩展名
	 * @param str
	 * @return
	 */
	public static String getExtendNameWithDotted(String str){
		str = getExtendName(str);
		
		if(isBlank(str)){
			return "";
		}else{
			return "." + str;
		}
	}
	
	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str){
		if(str == null || str.trim().equals("")){
			return true;
		}
		
		return false;
	}
	
	/**
	 * 获取一个指定长度的随机码
	 * @param len
	 * @return
	 */
	public static String getRandomCode(int len){
		Random random = new Random(System.currentTimeMillis());
		StringBuilder builder = new StringBuilder();
		
		for(int i = 0; i < len; ++i){
			int pos = random.nextInt(randomList3.length);
			builder.append(randomList3[pos]);
		}
		
		return builder.toString();
	}
}
