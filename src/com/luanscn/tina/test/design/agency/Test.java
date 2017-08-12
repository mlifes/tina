package com.luanscn.tina.test.design.agency;

import com.luanscn.tina.test.design.agency.impl.DBQueryProxy;
import com.luanscn.tina.test.util.TestUtil;
/**
 * 
 * 
 * 
 * 
 * */
public class Test {

	public static void main(String[] args) {
		//使用的是里氏替换原则
		IDBQuery q = new DBQueryProxy();//使用代理
		TestUtil.Println(q.request());//在真正使用时才创建真实对象
	}

}
