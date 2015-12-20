package com.kdyzm.dao.impl;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import com.kdyzm.dao.base.impl.BaseDaoImpl;
import com.kdyzm.domain.Log;
import com.kdyzm.utils.LogUtils;
import com.kdyzm.utils.StringUtils;
@Repository("logDao")
public class LogDaoImpl extends BaseDaoImpl<Log>{
	/**
	 * 重写父类中的方法，这里要动态指定表名，所以不能再使用hibernate提供的保存数据的方法
	 * 直接使用原生的slq语句来保存即可
	 */
	@Override
	public void saveEntity(Log log) {
		String tableName=LogUtils.createGenerateLogsTableName(0);
		String sql="insert into "+tableName+" ("
				+ " logId,operateParams,operateResult,operator,operatorDate,operatorName,resultMessage) "
				+ "values (?,?,?,?,?,?,?)";
		this.executeSql(sql,
				StringUtils.getUUIDString(),
				log.getOperateParams(),
				log.getOperateResult(),
				log.getOperator(),
				log.getOperatorDate(),
				log.getOperatorName(),
				log.getResultMessage()
				);
	}
	//重写该方法，因为该方法必须实现多表联合查询
	@Override
	public Collection<Log> findAllEntities() {
		String tableName=LogUtils.createGenerateLogsTableName(0);//当前月的日志表
		String talbeName1=LogUtils.createGenerateLogsTableName(-1);//上个月的日志表
		String sql="select * from "+tableName+" union select * from "+talbeName1+" order by operatorDate desc";
		return this.findAllEntitiesBySql(sql);
	}
}
