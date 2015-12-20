package com.kdyzm.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.kdyzm.service.LogService;
import com.kdyzm.utils.LogUtils;

public class TestSpring {
	private static ApplicationContext context;
	static{
		context=new ClassPathXmlApplicationContext("spring/applicationContext.xml");
	}
	public static void main(String[] args) {
		LogService logService=(LogService) context.getBean("logService");
		String tableName=LogUtils.createGenerateLogsTableName(1);
		String sql="create table if not exists "+tableName+" like logs";
		logService.executeSql(sql);
		System.out.println(tableName+" 表生成了！");
		tableName=LogUtils.createGenerateLogsTableName(2);
		sql="create table if not exists "+tableName+" like logs";
		logService.executeSql(sql);
		System.out.println(tableName+" 表生成了！");
	}
	
}
