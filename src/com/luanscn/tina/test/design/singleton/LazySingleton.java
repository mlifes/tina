package com.luanscn.tina.test.design.singleton;

import java.util.Date;

import com.luanscn.tina.test.util.TestUtil;

/**
 * 懒汉模式：主要应用是为了做延迟加载！<br>
 * 1、对于静态成员变量instance 初始值赋予null，确保系统启动时没有额外负载。<br>
 * 2、同步getInstance方法，以防止在多线程环境中，创建多个实例。<br>
 * 缺点：在实际上，因为引入了同步关键字。因此在多线程环境中，他的耗时要远大于hungrySingleton<br>
 * 方法：{@link StaticSingleton}。
 * @see HungrySingleton
 * */
public class LazySingleton extends Singleton{
	
	private LazySingleton(){
		TestUtil.Println("创建了懒汉模式在：" + new Date());
	}
	
	private static LazySingleton instance = null;
	
	public static synchronized LazySingleton getInstance(){
		if(instance == null)
			instance = new LazySingleton();
		TestUtil.Println("当我调用饿汉方法的时间为：" + new Date());
		return instance;
	}
	
	public static void otherMethod(){
		TestUtil.Println("调用其他的方法：" + new Date());
	}
}
