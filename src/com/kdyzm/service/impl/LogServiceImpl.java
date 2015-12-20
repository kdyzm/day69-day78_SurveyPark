package com.kdyzm.service.impl;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kdyzm.dao.base.BaseDao;
import com.kdyzm.domain.Log;
import com.kdyzm.service.LogService;
import com.kdyzm.service.base.impl.BaseServiceImpl;
@Service("logService")
public class LogServiceImpl extends BaseServiceImpl<Log> implements LogService{
	@Override
	@Resource(name="logDao")
	public void setBaseDao(BaseDao<Log> baseDao) {
		super.setBaseDao(baseDao);
	}
	//保存日志的方法
	public void saveLog(Log log){
		this.saveEntity(log);
	}
	@Override
	public Collection<Log> findAllEntitiesByDesc() {
		String hql="from Log l order by l.operatorDate desc";
		return this.baseDao.findEntityByHQL(hql);
	}
}
