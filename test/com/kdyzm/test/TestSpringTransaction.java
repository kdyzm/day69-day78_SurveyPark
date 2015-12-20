package com.kdyzm.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.kdyzm.domain.User;
import com.kdyzm.service.UserService;

/**
 * 测试spring事务管理的配置是否正确
 * @author kdyzm
 *
 */
public class TestSpringTransaction {
	private static ApplicationContext context;
	static{
		context=new ClassPathXmlApplicationContext("spring/applicationContext.xml");
	}
	public static void main(String[] args) {
		UserService userService=(UserService) context.getBean("userService");
//		userService.saveUser(null);
	}
}
