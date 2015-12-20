package com.kdyzm.dao.impl;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import com.kdyzm.dao.base.impl.BaseDaoImpl;
import com.kdyzm.domain.Survey;
import com.kdyzm.domain.User;
@Repository("surveyDao")
public class SurveyDaoImpl extends BaseDaoImpl<Survey>{

	public Collection<Survey> getAllEnties(User user) {
		String hql="from Survey where user.userId=?";
		return this.findEntityByHQL(hql,user.getUserId());
	}

}
