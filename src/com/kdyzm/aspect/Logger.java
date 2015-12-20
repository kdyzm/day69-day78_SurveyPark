package com.kdyzm.aspect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.aspectj.lang.ProceedingJoinPoint;

import com.kdyzm.domain.Log;
import com.kdyzm.domain.User;
import com.kdyzm.service.LogService;
import com.kdyzm.utils.StringUtils;

public class Logger {
	private LogService logService;
	public LogService getLogService() {
		return logService;
	}
	public void setLogService(LogService logService) {
		this.logService = logService;
	}
	//通知方法
	public Object record(ProceedingJoinPoint joinPoint) throws Throwable{
		Log log=new Log();
		try{
			//获取操作人
			HttpServletRequest request=ServletActionContext.getRequest();
			if(request!=null){
				HttpSession session=request.getSession();
				User user=(User) session.getAttribute("user");
				if(user!=null){
					log.setOperator(user.getUserId()+"-"+user.getEmail());//设置操作人
				}
			}
			
			//设置方法名
			String methodName=joinPoint.getSignature().getName();
			log.setOperatorName(methodName);
			
			//获取参数列表
			Object[] params=joinPoint.getArgs();
			log.setOperateParams(StringUtils.arr2String(params));
			
			//操作结果和结果消息的获取
			Object obj=joinPoint.proceed();
			log.setOperateResult("SUCCESS");
			
			if(obj!=null){
				log.setResultMessage(obj.toString());
			}
			return obj;			//返回执行结果
		}catch(Exception e){
			log.setOperateResult("FAILURE");
			log.setResultMessage(e.getMessage());
		}finally{
			logService.saveLog(log);
		}
		return null;
	}
}
