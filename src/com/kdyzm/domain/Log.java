package com.kdyzm.domain;

import java.util.Date;

/**
 * 日志实体类
 * @author kdyzm
 *
 */
public class Log {
	private String logId;					//日志消息标识ID
	private String operator="";				//操作人
	private Date operatorDate=new Date();	//操作日期
	private String operatorName;			//操作的名称（方法名）
	private String operateParams;			//操作参数
	private String operateResult="";			//操作结果（success|failure）
	private String resultMessage="";			//结果消息
/***********************华丽的分割线**************************************************/
	public String getOperator() {
		return operator;
	}
	public String getLogId() {
		return logId;
	}
	public void setLogId(String logId) {
		this.logId = logId;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Date getOperatorDate() {
		return operatorDate;
	}
	public void setOperatorDate(Date operatorDate) {
		this.operatorDate = operatorDate;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getOperateParams() {
		return operateParams;
	}
	public void setOperateParams(String operateParams) {
		this.operateParams = operateParams;
	}
	public String getOperateResult() {
		return operateResult;
	}
	public void setOperateResult(String operateResult) {
		this.operateResult = operateResult;
	}
	public String getResultMessage() {
		return resultMessage;
	}
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
}
