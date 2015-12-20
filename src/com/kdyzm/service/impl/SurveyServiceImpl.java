package com.kdyzm.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kdyzm.dao.impl.AnswerDaoImpl;
import com.kdyzm.dao.impl.PageDaoImpl;
import com.kdyzm.dao.impl.QuestionDaoImpl;
import com.kdyzm.dao.impl.SurveyDaoImpl;
import com.kdyzm.domain.Answer;
import com.kdyzm.domain.Page;
import com.kdyzm.domain.Question;
import com.kdyzm.domain.Survey;
import com.kdyzm.domain.User;
import com.kdyzm.service.SurveyService;
@Service("surveyService")
public class SurveyServiceImpl implements SurveyService{
	@Resource(name="surveyDao")
	private SurveyDaoImpl surveyDao;
	@Resource(name="pageDao")
	private PageDaoImpl pageDao;
	@Resource(name="questionDao")
	private QuestionDaoImpl questionDao;
	@Resource(name="answerDao")
	private AnswerDaoImpl answerDao;
	//创建新的空的调查
	@Override
	public void createNewSurvey(User user) {
		Survey survey=new Survey();
		survey.setUser(user);
		
		Page page=new Page();
		page.setSurvey(survey);
		survey.getPages().add(page);
		
		pageDao.saveEntity(page);
		surveyDao.saveEntity(survey);
	}
	@Override
	public List<Survey> findMySurveys(User user) {
		String hql="from Survey where user.userId=?";
		List<Survey> surveys=this.surveyDao.findEntityByHQL(hql, user.getUserId());
		return surveys;
	}
	@Override
	public Survey getModelById(int surveyId) {
		Survey survey=this.surveyDao.getEntityById(surveyId);
		return survey;
	}
	//编辑调查之后的更新操作
	@Override
	public void updateSurvey(Survey model) {
		this.surveyDao.updateEntity(model);
	}
	/**
	 * 删除调查的方法，手下删除掉调查中的所有页面的所有问题的答案；然后删除掉调查中的所有页面的所有问题；
	 * 然后删除掉调查中的所有页面；最后删除掉当前的调查
	 */
	@Override
	public void deleteSurvey(Survey survey) {
		survey=this.surveyDao.getEntityById(survey.getSurveyId());
		String hql;
		Set<Page>pages=survey.getPages();
		for(Page page:pages){
			Set<Question>questions=page.getQuestions();
			//首先删除掉当前调查中的所有页面中的所有问题的答案
			for(Question question:questions){
				hql="delete from Answer a where a.questionId=?";
				answerDao.batchEntityByHql(hql, question.getQuestionId());
			}
			//然后删除掉当前调查中的所有页面中的所有问题
			hql="delete from Question where page.pageId=?";
			questionDao.batchEntityByHql(hql, page.getPageId());
		}
		//然后删除掉当前调查中的所有页面
		hql="delete from Page where survey.surveyId=?";
		pageDao.batchEntityByHql(hql, survey.getSurveyId());
		//最后删除掉Survey对象本身
		hql="delete from Survey s where s.surveyId=?";
		surveyDao.batchEntityByHql(hql, survey.getSurveyId());
	}
	/**
	 * 清除调查的方法，也就是将答案全部删除的方法
	 */
	@Override
	public void clearSurvey(Survey model) {
		model=this.surveyDao.getEntityById(model.getSurveyId());
		String hql="delete from Answer a where a.surveyId=?";
		answerDao.batchEntityByHql(hql, model.getSurveyId());
	}
	/**
	 * 打开或者关闭调查的方法
	 */
	@Override
	public void updateSurveyClosedState(Survey model) {
		System.out.println("访问了SurveyService的updateSurveyClosedState方法！");
		model=this.getModelById(model.getSurveyId());
		if(model.isClosed()){
			model.setClosed(false);
		}else{
			model.setClosed(true);
		}
		String hql="update Survey s set closed=? where s.surveyId=?";
		surveyDao.batchEntityByHql(hql, model.isClosed(),model.getSurveyId());
	}
	//获取所有Survey对象列表的方法
	@Override
	public Collection<Survey> getAllSurveys(User user) {
		return this.surveyDao.getAllEnties(user);
	}
	//获取所有可用调查的方法
	@Override
	public Collection<Survey> getAllAvailableSurveys() {
		String hql="from Survey s where closed=true";
		return this.surveyDao.sessionFactory.getCurrentSession().createQuery(hql).list();
	}
	//根据survey对象和orderNo获取相应页
	@Override
	public Page getResponsePage(Survey survey, float orderNo) {
		Set<Page>pages=survey.getPages();
		for(Page page:pages){
			if(page.getOrderNo()==orderNo){
				return page;
			}
		}
		return null;
	}
}
