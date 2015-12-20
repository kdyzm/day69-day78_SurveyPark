package com.kdyzm.listener;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import com.kdyzm.domain.security.Right;
import com.kdyzm.service.RightService;

/**
 * 初始化权限数据的监听类
 * 该监听器的作用就是将所有的权限放入ServletContext中
 * Spring容器初始化的时候struts2还没有初始化，所以不能使用struts2的ServletContextAware获取SerlvetContext对象。
 * 但是spring提供了相同的机制获取ServletContext对象，而且使用的方法和接口也是完全相同。
 * 这里还有一个非常重要的东西：注入sc一定在前。
 * 
 * 直接使用注解注入到spring容器，不需要对配置文件进行修改
 * @author kdyzm
 *
 */
@Component
public class InitRightListener implements ApplicationListener,ServletContextAware{
	private ServletContext sc;
	@Resource(name="rightService")
	private RightService rightService;
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		//这里所有的ApplicationContext的事件都会不获到，所以必须进行判断已进行分类处理
		if(event instanceof ContextRefreshedEvent){
			Collection<Right> rights=rightService.getAllRights();
			Map<String,Right>rightMap=new HashMap<String,Right>();
			for(Right right: rights){
				System.out.println(right.getRightUrl()+":"+right.getCommon());
				rightMap.put(right.getRightUrl(), right);
			}
			if(sc!=null){
				sc.setAttribute("all_rights_map", rightMap);
				System.out.println("初始化RightMap成功！");
			}else{
				System.out.println("ServletContext对象为空，初始化RightMap对象失败！");
			}
		}
	}
	
	//注入ServletContext
	@Override
	public void setServletContext(ServletContext servletContext) {
		System.out.println("注入ServletContext对象");
		this.sc=servletContext;
	}

}
