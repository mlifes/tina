package com.luanscn.tina.test.design.singleton;

import java.util.Date;

import com.luanscn.tina.test.util.TestUtil;

/**
 * 饿汉模式<br>
 * 1、对于频繁使用的对象，可以省略创建对象所花费的时间，对于那些重量级对象而言，是非常可观的一笔开销<br>
 * 2、由于new 操作次数的减少，因而对于系统内存的使用频率将会降低，这将减轻GC压力，缩短GC停顿时间<br>
 * 3、缺点：无法对instance 实例做延迟加载。当调用该类中的方法时，该实例即被创建！
 * 在一些地方，我们却需要实例仅仅在被调用getInstace时被创建。
 * @see LazySingleton
 * */
public class HungrySingleton extends Singleton{
	
	private HungrySingleton(){
		
		TestUtil.Println("当我创建此饿汉单例的时间为：" + new Date());
		
	}
	
	private static HungrySingleton instance = new HungrySingleton();
	
	public static HungrySingleton getInstance(){
		TestUtil.Println("当我调用饿汉方法的时间为：" + new Date());
		return instance;
	}
	
	public static void otherMethod(){
		TestUtil.Println("调用其他方法的时间为：" + new Date());
	}
}
