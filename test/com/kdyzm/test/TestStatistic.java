package com.kdyzm.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.kdyzm.domain.User;
import com.kdyzm.domain.statistic.QuestionStatisticModel;
import com.kdyzm.service.StatisticService;
import com.kdyzm.service.UserService;
/**
 * 测试数据统计的结果
 * @author kdyzm
 *
 */
public class TestStatistic {
	private static ApplicationContext context;
	static{
		context=new ClassPathXmlApplicationContext("spring/applicationContext.xml");
	}
	public static void main(String[] args) {
		StatisticService statisticService=(StatisticService) context.getBean("statisticService");
//		QuestionStatisticModel questionStatisticModel=statisticService.statics(6);
//		System.out.println(questionStatisticModel);
	}
	
}
