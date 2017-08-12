package com.luanscn.tina.test.design.singleton;
/**
 * 测试类
 * */
public class Test {

	public static void main(String[] args) {
		Singleton s ;
		s = HungrySingleton.getInstance();
		s = LazySingleton.getInstance();
		s = StaticSingleton.getInstance();
	}

}
