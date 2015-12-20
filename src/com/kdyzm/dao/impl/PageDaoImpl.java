package com.kdyzm.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kdyzm.dao.base.impl.BaseDaoImpl;
import com.kdyzm.domain.Page;
@Repository("pageDao")
public class PageDaoImpl extends BaseDaoImpl<Page> {

	public List<Page> findEntitysByHQL(String hql, Integer integer) {
		return this.sessionFactory.getCurrentSession().createQuery(hql).setParameter(0, integer).list();
	}

	public List<Page> findEntitysByHQL(String hql, Integer surveyId, float orderNo) {
		return this.sessionFactory.getCurrentSession().createQuery(hql).setParameter(0, surveyId).setParameter(1,orderNo).list();
	}

}
