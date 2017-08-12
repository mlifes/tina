package com.luanscn.tina.test.design.agency.impl;

import com.luanscn.tina.test.design.agency.IDBQuery;

/**
 * 真实主题
 * */
public class DBQuery implements IDBQuery{
	
	public DBQuery(){
		try{
			Thread.sleep(1000);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	
	@Override
	public String request() {
		return "request string!";
	}

}
