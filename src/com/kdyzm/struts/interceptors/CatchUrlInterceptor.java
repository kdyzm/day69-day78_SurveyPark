package com.kdyzm.struts.interceptors;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.kdyzm.service.RightService;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.Interceptor;
/**
 * 该拦截在发布之后应当删除掉
 * @author kdyzm
 *
 */
public class CatchUrlInterceptor implements Interceptor{
	private static final long serialVersionUID = 6747245610234756713L;

	@Override
	public void destroy() {
		System.out.println("捕获URL拦截器被销毁！");
	}

	@Override
	public void init() {
		System.out.println("捕获URL拦截器初始化！");
	}
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionProxy actionProxy=invocation.getProxy();
		String namespace=actionProxy.getNamespace();
		String actionName=actionProxy.getActionName();
		if(namespace==null||"/".equals(namespace)){
			namespace="";
		}
		String url=namespace+"/"+actionName;
		ServletContext sc=ServletActionContext.getServletContext();
		ApplicationContext context=WebApplicationContextUtils.getWebApplicationContext(sc);
		RightService rightService=(RightService) context.getBean("rightService");
		rightService.appendRightByUrl(url+".action");
		return invocation.invoke();
	}
}
