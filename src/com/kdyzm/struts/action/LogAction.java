package com.kdyzm.struts.action;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kdyzm.domain.Log;
import com.kdyzm.service.LogService;
import com.kdyzm.struts.action.base.BaseAction;
import com.opensymphony.xwork2.ActionContext;
@Controller
@Scope("prototype")
public class LogAction extends BaseAction<Log>{
	@Resource(name="logService")
	private LogService logService;
	private static final long serialVersionUID = -1136113519139723083L;
	//跳转到日志管理界面
	public String findAllLogs() throws Exception{
		Collection<Log> logList=this.logService.findAllEntities();
		ActionContext.getContext().put("logList", logList);
		return "toAllLogsPage";
	}
}
