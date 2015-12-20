package com.kdyzm.dao.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.kdyzm.dao.base.impl.BaseDaoImpl;
import com.kdyzm.domain.Answer;

@Repository("answerDao")
public class AnswerDaoImpl extends BaseDaoImpl<Answer>{
	//获取回答的人数的方法
	public int getQuestionResponseCount(String hql, Integer questionId) {
		Query query=this.sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, questionId);
		Long result=(Long) query.uniqueResult();
		return result.intValue();
	}

	public int getOptionResponseAmount(String hql,Integer questionId, String optionIndex) {
		Query query=this.sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, questionId);
		query.setParameter(1, optionIndex);
		Long result=(Long) query.uniqueResult();
		return result.intValue();
	}

}
