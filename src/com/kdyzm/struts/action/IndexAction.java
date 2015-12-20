package com.kdyzm.struts.action;

import com.opensymphony.xwork2.ActionSupport;

public class IndexAction extends ActionSupport{
	private static final long serialVersionUID = -7269692936415023860L;
	//跳转到首页的方法
	public String toIndexPage() throws Exception{
		return "toIndexPage";
	}
}
