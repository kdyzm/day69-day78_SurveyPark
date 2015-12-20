package com.kdyzm.struts.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kdyzm.domain.Page;
import com.kdyzm.domain.Question;
import com.kdyzm.domain.Survey;
import com.kdyzm.domain.User;
import com.kdyzm.service.PageService;
import com.kdyzm.service.QuestionService;
import com.kdyzm.service.SurveyService;
import com.kdyzm.struts.action.aware.UserAware;
import com.kdyzm.struts.action.base.BaseAction;
import com.opensymphony.xwork2.ActionContext;
@Controller("pageAction")
@Scope("prototype")
public class PageAction extends BaseAction<Page> implements UserAware{
	private static final long serialVersionUID = -3358080895111692755L;
	@Resource(name="pageService")
	private PageService pageService;
	@Resource(name="surveyService")
	private SurveyService surveyService;
	private User user;
	@Override
	public void setUser(User user) {
		this.user=user;
	}
	//首先是增加页的方法,跳转到增加页的界面上去
	public String toAddPagePage() throws Exception{
		HttpServletRequest request=ServletActionContext.getRequest();
		String surveyId=request.getParameter("surveyId");
		ActionContext.getContext().put("surveyId", surveyId);
		return "toAddPagePage";
	}
	//保存新建的页面的方法
	public String saveNewPage() throws Exception{
		System.out.println("即将保存新的页面！");
		HttpServletRequest request=ServletActionContext.getRequest();
		String surveyId=request.getParameter("surveyId");
		ActionContext.getContext().put("surveyId", surveyId);
		Survey survey=this.surveyService.getModelById(Integer.parseInt(surveyId));
		this.model.setSurvey(survey);
		this.pageService.savePage(this.model);
		return "toDesignSurveyPageAction";
	}
	//修改页标题的方法
	public String toEditPageTitlePage() throws Exception{
		HttpServletRequest request=ServletActionContext.getRequest();
		String surveyId=request.getParameter("surveyId");
		ActionContext.getContext().put("surveyId", surveyId);
		this.model=this.pageService.getPage(this.model.getPageId());
		return "toEditPageTitlePage";
	}
	//删除页的方法
	public String deletePage() throws Exception{
		HttpServletRequest request=ServletActionContext.getRequest();
		String surveyId=request.getParameter("surveyId");
		ActionContext.getContext().put("surveyId", surveyId);
		this.pageService.deletePage(this.model);
		return "toDesignSurveyPageAction";
	}
	//修改页面标题的方法
	public String updatePageInfo() throws Exception{
		HttpServletRequest request=ServletActionContext.getRequest();
		String surveyId=request.getParameter("surveyId");
		ActionContext.getContext().put("surveyId", surveyId);
		Page page=this.pageService.getPage(this.model.getPageId());
		page.setTitle(this.model.getTitle());
		this.pageService.updatePage(page);
		return "toDesignSurveyPageAction";
	}
	private String surveyId;
	public String getSurveyId() {
		return surveyId;
	}
	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}
	//跳转到复制/移动页的页面上去
	public String toSelectTargetPage() throws Exception{
		//获取原页面的详细信息
		this.model=this.pageService.getPage(this.model.getPageId());
		//获取所有的Survey对象的列表
		Collection<Survey>surveys=this.surveyService.getAllSurveys(user);
		ActionContext.getContext().put("surveys", surveys);
		ActionContext.getContext().put("sourcePage", this.model);
		return "selectTargetPage";
	}
	//移动或者复制页的方法
	public String copyOrRemovePage() throws Exception{
		HttpServletRequest request=ServletActionContext.getRequest();
		String oldPageId=request.getParameter("oldPageId");
		String newPageId=request.getParameter("newPageId");
		String position=request.getParameter("position");
		//原来的页面
		Page oldPage=pageService.getPage(Integer.parseInt(oldPageId));
		//目标页面
		Page newPage=pageService.getPage(Integer.parseInt(newPageId));
		//原来的调查
		Survey oldSurvey=oldPage.getSurvey();
		//目标调查
		Survey newSurvey=newPage.getSurvey();
		//如果oldSurvey的surveyId和newSurvey的surveyId相同，表示是同一个调查中的页面，那么只需要进行移动即可
		if(oldSurvey.getSurveyId().equals(newSurvey.getSurveyId())){
			//执行移动页面的动作
			doMovePage(position,oldPage,newPage);
		}else{		//如果不是同一个调查中的页面，则需要使用深度复制
			doCopyPage(position,oldPage,newPage);
		}
		//转发到设计调查的页面上去
		ActionContext.getContext().getValueStack().push(newSurvey);
		return "toDesignSurveyPageAction";
	}
	@Resource(name="questionService")
	private QuestionService questionService;
	//不同页面之间使用页面复制，深度复制
	private void doCopyPage(String position, Page oldPage, Page newPage) {
		Page copyPage=(Page) this.copyPage(oldPage);
		//第一种情况是position是0，代表是放到目标页的前面
		if("0".equals(position)){
			//如果是放到目标页的前面的话，需要考虑目标页是不是第一页的情况
			if(isFirstPage(newPage)){
				//是第一页的话使用第一页的orderNo-0.01
				copyPage.setOrderNo(newPage.getOrderNo()-0.01F);
			}else{
				//否则的话取平均值
				copyPage.setOrderNo((this.getPrePage(newPage).getOrderNo()+newPage.getOrderNo())/2);
			}
		}else if("1".equals(position)){//第二种情况是position是1，带包是放到目标页的后面
			//如果是放到目标页的后面，需要考虑目标页是不是最后一页的情况
			if(isLastPage(newPage)){
				copyPage.setOrderNo(newPage.getOrderNo()+0.01F);
			}else{
				//否则的话取平均值
				copyPage.setOrderNo((this.getNextPage(newPage).getOrderNo()+newPage.getOrderNo())/2);
			}
		}
		float temp=copyPage.getOrderNo();
		copyPage.setSurvey(newPage.getSurvey());
		pageService.addNewPage(copyPage);
		copyPage.setOrderNo(temp);
		pageService.updatePage(copyPage);
		for(Question question:copyPage.getQuestions()){
			questionService.saveQuestion(question);
		}
	}
	//实现深度复制的方法
	//在实现深度复制之前必须修改Bean类中的相关字段，
	//比如Page类中的pageId必须加上transient修饰，还有Question类中的questionId字段也必须加上transient修饰
	private Serializable copyPage(Page oldPage) {
		oldPage.getQuestions().size();
		try {
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			ObjectOutputStream oos=new ObjectOutputStream(baos);
			oos.writeObject(oldPage);
			ByteArrayInputStream bais=new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois=new ObjectInputStream(bais);
			Serializable serializable=(Serializable) ois.readObject();
			return serializable;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	//执行移动页面的动作
	/**
	 * 移动的规则就是：
	 * 如果是最前面的页面，则将页面的orderNo设置为第一个页面的orderNo-0.01，
	 * 如果是最后面的一个页面，则将页面的orderNo设置为最后一个页面的orderN+0.01,
	 * 如果是中间的一个页面，则使用两边orderNo的平均值
	 * 
	 * @param position
	 * @param oldPage
	 * @param newPage
	 */
	private void doMovePage(String position, Page oldPage, Page newPage) {
		//第一种情况是position是0，代表是放到目标页的前面
		if("0".equals(position)){
			//如果是放到目标页的前面的话，需要考虑目标页是不是第一页的情况
			if(isFirstPage(newPage)){
				//是第一页的话使用第一页的orderNo-0.01
				oldPage.setOrderNo(newPage.getOrderNo()-0.01F);
			}else{
				//否则的话取平均值
				oldPage.setOrderNo((this.getPrePage(newPage).getOrderNo()+newPage.getOrderNo())/2);
			}
		}else if("1".equals(position)){//第二种情况是position是1，带包是放到目标页的后面
			//如果是放到目标页的后面，需要考虑目标页是不是最后一页的情况
			if(isLastPage(newPage)){
				oldPage.setOrderNo(newPage.getOrderNo()+0.01F);
			}else{
				//否则的话取平均值
				oldPage.setOrderNo((this.getNextPage(newPage).getOrderNo()+newPage.getOrderNo())/2);
			}
		}
		pageService.updatePage(oldPage);
	}
	//得到下一个Page对象
	private Page getNextPage(Page newPage) {
		return pageService.getNextPage(newPage);
	}
	//得到前一个Page对象
	private Page getPrePage(Page newPage) {
		return pageService.getPrePage(newPage);
	}
	//判断是否是最后一页的方法
	private boolean isLastPage(Page page){
		return pageService.isLastPage(page);
	}
	private boolean isFirstPage(Page page){
		return pageService.isFirstPage(page);
	}
}
