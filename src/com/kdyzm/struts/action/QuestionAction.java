package com.kdyzm.struts.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kdyzm.domain.Page;
import com.kdyzm.domain.Question;
import com.kdyzm.domain.User;
import com.kdyzm.service.PageService;
import com.kdyzm.service.QuestionService;
import com.kdyzm.struts.action.aware.UserAware;
import com.kdyzm.struts.action.base.BaseAction;
@Controller("questionAction")
@Scope("prototype")
public class QuestionAction extends BaseAction<Question> implements UserAware{
	private static final long serialVersionUID = 6719422012675245200L;
	@Resource(name="questionService")
	private QuestionService questionService;
	private User user;
	//在这里使用属性并提供get/set方法能够在返回到前端页面的时候直接压栈
	private int pageId;
	private int surveyId;
	@Resource(name="pageService")
	private PageService pageService;
	public int getSurveyId() {
		return surveyId;
	}
	public void setSurveyId(int surveyId) {
		this.surveyId = surveyId;
	}
	public int getPageId() {
		return pageId;
	}
	public void setPageId(int pageId) {
		this.pageId = pageId;
	}
	@Override
	public void setUser(User user) {
		this.user=user;
	}
	//转发到选择问题类型的页面上去
	public String toSelectQuestionTypePage() throws Exception{
		return "selectQuestionTypePage";
	}
	//跳转到问题设计的页面上去
	public String toDesignQuestionPage() throws Exception{
		return this.model.getQuestionType()+"";
	}
	//保存Question对象的方法
	public String saveQuestion() throws Exception{
		Page page=pageService.getPage(pageId);
		this.model.setPage(page);
		this.questionService.saveQuestion(this.model);
		return "toDesignSurveyPageAction";
	}
	//删除问题的方法
	public String deleteQuestion() throws Exception{
		Question question=questionService.getQuestion(this.model.getQuestionId());
		questionService.deleteQuestion(question);
		return "toDesignSurveyPageAction";
	}
	//编辑问题的方法
	public String editQuestion() throws Exception{
		this.model=this.questionService.getQuestion(this.model.getQuestionId());
		return this.model.getQuestionType()+"";
	}
}
