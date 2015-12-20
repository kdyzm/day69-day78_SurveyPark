package com.kdyzm.listener;

import javax.annotation.Resource;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.kdyzm.service.LogService;
import com.kdyzm.utils.LogUtils;
/**
 * 动态生成当前月份的日志表
 * 这里一次性生成三个月份的日志表，防止服务器启动的时间是在一个月的下半个月，即在15号之后
 * @author kdyzm
 *
 */
@Component
public class InitLogTableListener implements ApplicationListener{
	@Resource(name="logService")
	private LogService logService;
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if(event instanceof ContextRefreshedEvent){
			//生成当前月的日志表
			String tableName=LogUtils.createGenerateLogsTableName(0);
			String sql="create table if not exists "+tableName+" like logs";
			logService.executeSql(sql);
			System.out.println(tableName+" 表已经生成！");
			
			//生成下一个月的日志表
			tableName=LogUtils.createGenerateLogsTableName(1);
			sql="create table if not exists "+tableName+" like logs";
			logService.executeSql(sql);
			System.out.println(tableName+" 表已经生成！");
			
			//生成第二个月的日志表
			tableName=LogUtils.createGenerateLogsTableName(2);
			sql="create table if not exists "+tableName+" like logs";
			logService.executeSql(sql);
			System.out.println(tableName+" 表已经生成！");
		}
	}
}
