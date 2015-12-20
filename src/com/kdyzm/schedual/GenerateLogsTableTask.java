package com.kdyzm.schedual;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.kdyzm.service.LogService;
import com.kdyzm.utils.LogUtils;
/**
 * 创建的石英任务：使用spring集成的石英调度，动态生成日志表
 * @author kdyzm
 *
 */
public class GenerateLogsTableTask extends QuartzJobBean{
	private LogService logService;
	public LogService getLogService() {
		return logService;
	}
	public void setLogService(LogService logService) {
		this.logService = logService;
	}
	/**
	 * 执行调度任务的方法
	 * 每月15号创建下两个月需要用到的日志表
	 */
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		String tableName=LogUtils.createGenerateLogsTableName(1);
		String sql="create table if not exists "+tableName+" like logs";
		this.logService.executeSql(sql);
		System.out.println(tableName+" 表生成了！");
		tableName=LogUtils.createGenerateLogsTableName(2);
		sql="create table if not exists "+tableName+" like logs";
		this.logService.executeSql(sql);
		System.out.println(tableName+" 表生成了！");
	}
}
