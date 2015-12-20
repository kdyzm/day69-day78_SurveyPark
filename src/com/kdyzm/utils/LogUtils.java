package com.kdyzm.utils;

import java.util.Calendar;

/**
 * 专门针对日志生成流程定义的工具类
 * @author kdyzm
 *
 */
public class LogUtils {
	//动态生成日志表名的方法
	public static String createGenerateLogsTableName(int offset){
		Calendar calendar=Calendar.getInstance();
//		month=(month+offset-1)%month+1;
		//计算偏移之后的动态表名
		calendar.add(Calendar.MONTH, offset);
		int year=calendar.get(Calendar.YEAR);
		int month=calendar.get(Calendar.MONTH)+1;
		return "logs_"+year+"_"+month;
	}
}
