package com.luanscn.tina.test.design.other.impl;

import com.luanscn.tina.test.design.other.First;
import com.luanscn.tina.test.design.other.Second;
import com.luanscn.tina.test.util.TestUtil;

public class Impl implements First,Second{

	@Override
	public void printWorld() {
		// TODO Auto-generated method stub
		TestUtil.Println("World");
	}

	@Override
	public void printHello() {
		// TODO Auto-generated method stub
		TestUtil.Println("Hello");
	}

}
