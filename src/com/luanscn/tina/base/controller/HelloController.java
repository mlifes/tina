package com.luanscn.tina.base.controller;

import com.luanscn.tina.base.controller.base.BaseController;

public class HelloController extends BaseController{
	
	public void index(){
		renderText("Hello JFinal World.");
	}
}
