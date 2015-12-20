package com.kdyzm.struts.interceptors;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.kdyzm.domain.User;
import com.kdyzm.domain.security.Right;
import com.kdyzm.struts.action.aware.UserAware;
import com.kdyzm.utils.ValidateUtils;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * 只要请求了Action就会默认访问该拦截器
 * 登陆拦截器
 * @author kdyzm
 *
 */
public class LoginInterceptor implements Interceptor{
	private static final long serialVersionUID = 7321012192261008127L;

	@Override
	public void destroy() {
		System.out.println("登录拦截器被销毁！");
	}

	@Override
	public void init() {
		System.out.println("登录拦截器初始化！");
	}

	/*@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		System.out.println("被登录拦截器拦截！");
		Action action=(Action) invocation.getAction();
		if(action instanceof LoginAction ||action instanceof RegisterAction){
			System.out.println("即将进行登录或者注册，直接放行!");
			return invocation.invoke();
		}
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session=request.getSession();
		User user=(User) session.getAttribute("user");
		if(user==null){
			System.out.println("用户未登录，必须先登录再访问其他资源!即将跳转到登陆界面！");
			return "toLoginPage";
		}else{
			System.out.println("用户已经登陆，登录拦截器已经放行！");
			//如果用户名不为空，而且实现了UserAware接口，就需要调用该接口中的相应方法给类中的成员变量赋值
			//TODO 给Action中User动态赋值的方法
			if(action instanceof UserAware){
				((UserAware)action).setUser(user);
			}
			return invocation.invoke();
		}
	}*/
	/**
	 *	对登录拦截器进行改造使其成为权限过滤拦截器
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		//首先获取请求的Action的名称
		ActionProxy actionProxy=invocation.getProxy();
		String namespace=actionProxy.getNamespace();
		String actionName=actionProxy.getActionName();
		if(namespace==null||"/".equals(namespace)){
			namespace="";
		}
		HttpServletRequest request=ServletActionContext.getRequest();
		boolean result=ValidateUtils.hasRight(namespace, actionName, request, (Action)invocation.getAction());
		if(result==true){
			return invocation.invoke();
		}else{
			return "no_right_error";
		}
		/*
		String url=namespace+"/"+(actionName.contains("?")?actionName.substring(0, actionName.indexOf("?")):actionName)+".action";
		//TODO 将权限列表放入到ServletContext中的方法
		HttpSession session=request.getSession();
		ServletContext sc=session.getServletContext();
		Map<String,Right>allRights=(Map<String, Right>) sc.getAttribute("all_rights_map");
		Right right=allRights.get(url);
		//如果是公共资源直接方放过
		if(right==null||right.getCommon()){
			System.out.println("访问公共资源，即将放行！");
			return invocation.invoke();
		}else{
			User user=(User) session.getAttribute("user");
			//判断是否已经登陆
			if(user ==null){
				return "no_right_error";
			}else{
				//判断是否是超级管理员
				Action action=(Action) invocation.getAction();
				//如果实现了UserAware接口
				if(action!=null&&action instanceof UserAware){
					UserAware userAware=(UserAware) action;
					userAware.setUser(user);
				}
				//如果是超级管理员直接放行
				if(user.getSuperAdmin()){
					return invocation.invoke();
					//否则先检查是否有权限
				}else{
					if(user.hasRight(right)){
						return invocation.invoke();
					}else{
						return "no_right_error";
					}
				}
			}
		}*/
	}
}
