package com.luanscn.tina.test.design.singleton;

import java.util.Date;

import com.luanscn.tina.test.util.TestUtil;

/**
 * 改进版：懒汉模式<br>
 * 1、当初始化的时候StaticSingleton不会初始化<br>
 * 2、使用内部类初始化的过程中，不会产生多线程问题
 * @see HungrySingleton
 * */
public class StaticSingleton extends Singleton{
	
	private StaticSingleton(){
		TestUtil.Println("创建了StaticSingleton单例模式：" + new Date());
	}
	private static class SingletonHolder{
		private static StaticSingleton instance = new StaticSingleton();
	}
	
	public static StaticSingleton getInstance(){
		TestUtil.Println("当我调用StaticSingleton饿汉方法的时间为：" + new Date());
		return SingletonHolder.instance;
	}
}
