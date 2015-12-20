package com.kdyzm.service.impl;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kdyzm.dao.impl.AnswerDaoImpl;
import com.kdyzm.dao.impl.PageDaoImpl;
import com.kdyzm.dao.impl.QuestionDaoImpl;
import com.kdyzm.domain.Page;
import com.kdyzm.domain.Question;
import com.kdyzm.service.PageService;
@Service("pageService")
public class PageServiceImpl implements PageService{
	@Resource(name="pageDao")
	private PageDaoImpl pageDao;
	@Resource(name="questionDao")
	private QuestionDaoImpl questionDao;
	@Resource(name="answerDao")
	private AnswerDaoImpl answerDao;

	@Override
	public void savePage(Page model) {
		this.pageDao.saveEntity(model);
	}
	//删除页面的方法，首先需要删除掉页面上所有问题的所有答案，然后删除所有问题，最后删除页面
	@Override
	public void deletePage(Page page) {
		page=pageDao.getEntityById(page.getPageId());
		Set<Question> questions=page.getQuestions();
		//首先删除掉页面上的所有问题和答案
		String hql="delete from Answer a where a.questionId=?";
		for(Question question:questions){
			answerDao.batchEntityByHql(hql, question.getQuestionId());
		}
		//删除掉所有问题
		hql="delete from Question q where page.pageId=?";
		questionDao.batchEntityByHql(hql, page.getPageId());
		//删除掉当前页面
		hql="delete from Page p where p.pageId=?";
		pageDao.batchEntityByHql(hql, page.getPageId());
	}
	@Override
	public Page getPage(Integer pageId) {
		return this.pageDao.getEntityById(pageId);
	}
	//更新页面信息的方法
	@Override
	public void updatePage(Page page) {
		this.pageDao.updateEntity(page);
	}
	@Override
	public boolean isLastPage(Page page) {
		String hql="from Page where survey.surveyId=? order by orderNo desc";
		List<Page>pages=this.pageDao.findEntitysByHQL(hql,page.getSurvey().getSurveyId());
		return pages.get(0).getPageId()==page.getPageId();
	}
	@Override
	public boolean isFirstPage(Page page) {
		String hql="from Page where survey.surveyId=? order by orderNo asc";
		List<Page>pages=this.pageDao.findEntitysByHQL(hql,page.getSurvey().getSurveyId());
		return pages.get(0).getPageId()==page.getPageId();
	}
	@Override
	public Page getNextPage(Page page) {
		String hql="from Page p where survey.surveyId=? and orderNo>? order by orderNo asc";
		List<Page>pages=this.pageDao.findEntitysByHQL(hql,page.getSurvey().getSurveyId(),page.getOrderNo());
		return pages.get(0);
	}
	@Override
	public Page getPrePage(Page page) {
		String hql="from Page p where survey.surveyId=? and orderNo<? order by orderNo desc";
		List<Page>pages=this.pageDao.findEntitysByHQL(hql,page.getSurvey().getSurveyId(),page.getOrderNo());
		return pages.get(0);
	}
	@Override
	public void addNewPage(Page copyPage) {
		this.pageDao.saveEntity(copyPage);
	}
	
}
