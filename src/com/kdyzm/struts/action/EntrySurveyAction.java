package com.kdyzm.struts.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kdyzm.datasource.SurveyToken;
import com.kdyzm.domain.Answer;
import com.kdyzm.domain.Page;
import com.kdyzm.domain.Survey;
import com.kdyzm.domain.User;
import com.kdyzm.service.AnswerService;
import com.kdyzm.service.PageService;
import com.kdyzm.service.SurveyService;
import com.kdyzm.struts.action.aware.UserAware;
import com.kdyzm.struts.action.base.BaseAction;
import com.kdyzm.utils.StringUtils;
import com.opensymphony.xwork2.ActionContext;

/**
 * 参与调查的Action,因为比较复杂，所以直接抽取出来单独的一个Action
 * @author kdyzm
 *
 */
@Controller("entryServeyAction")
@Scope("prototype")
public class EntrySurveyAction extends BaseAction<Survey> implements SessionAware,ParameterAware,UserAware,ServletContextAware,RequestAware{
	private static final long serialVersionUID = -6174312592542400730L;
	private User user;
	private ServletContext servletConetxt;
	private Map<String,Object>request;
	private Map<String,String[]>parameters;
	private Integer pageId;
	@Resource(name="pageService")
	private PageService pageService;
	//这里使用缓存代理
	@Resource(name="surveyService")
	private SurveyService surveyService;
	@Resource(name="answerService")
	private AnswerService answerService;
	//使用current_survey常量作为session中的key值保存当前的Survey对象
	private String CURRENT_SURVEY="current_survey";
	//使用all_parameters常量作为Session中的key值保存所有的答案对象
	private String ALL_PARAMETERS="all_parameters";
	public Integer getPageId() {
		return pageId;
	}
	public void setPageId(Integer pageId) {
		this.pageId = pageId;
	}
	//跳转到参与调查的界面，该界面显示所有可用的调查
	public String toEntrySurveyPage() throws Exception{
		Collection<Survey>surveys=surveyService.getAllAvailableSurveys();
		ActionContext.getContext().put("surveys", surveys);
//		IteratorStatus x;
		return "toEntrySurveyPage";
	}
	@Override
	public void setUser(User user) {
		this.user=user;
	}
	
	//判断logo是否存在如果存在返回路径，如果不存在，返回默认路径
	public String getLogoPath(String logoPath){
		System.out.println(logoPath);
		if(logoPath!=null){
			String realPath=this.servletConetxt.getRealPath(logoPath);
			File file=new File(realPath);
			if(file.exists()){
				return servletConetxt.getContextPath()+logoPath;
			}
		}
		return servletConetxt.getContextPath()+"/upload/defaultLogo.jpg";
	}
	@Override
	public void setServletContext(ServletContext context) {
		this.servletConetxt=context;
	}
	//跳转到答题界面！ 这需要将某个页面的所有问题都传递到前端去
	/**
	 * 在这个方法中必须初始化两个参数current_survey和parameters
	 * @return
	 * @throws Exception
	 */
	public String toSurveyPage() throws Exception{
		//重新获取survey对象
		this.model=this.surveyService.getModelById(this.model.getSurveyId());
		float requestPage;
		//得到请求页,实际上就是orderNo
		if(parameters.get("requestPage")!=null){
			requestPage=Float.parseFloat(parameters.get("requestPage")[0]);
		}else{
			requestPage=this.model.getMinOrderNo();
		}
		Page responsePage=this.surveyService.getResponsePage(this.model,requestPage);
		ServletActionContext.getContext().put("page",responsePage);
		
		//将两个参数保存到Session中去
		this.session.put(this.CURRENT_SURVEY, this.model);
		this.session.put(ALL_PARAMETERS, new HashMap<String,Map<String,String[]>>());
		return "surveyPage";
	}

	@Override
	public void setRequest(Map<String, Object> request) {
		this.request=request;
	}
	@Override
	public void setParameters(Map<String, String[]> parameters) {
		this.parameters=parameters;
	}
	
	/**
	 * 完成对调查页面的保存动作，包括上一步、下一步、完成、退出动作的保存。
	 * 上一步
	 */
	public String doEntrySurvey() throws Exception{
		Page oldPage=pageService.getPage(pageId);
		String submitType=this.getSubmitType();
		//上一页
		if(submitType.endsWith("pre")){
			//将答案保存到Session中
			mergeIntoSession();
			Page page=pageService.getPrePage(oldPage);
			ActionContext.getContext().put("page", page);
			return "surveyPage";
		}
		//下一页
		if(submitType.endsWith("next")){
			mergeIntoSession();
			Page page=pageService.getNextPage(oldPage);
			ActionContext.getContext().put("page", page);
			return "surveyPage";
		}
		//完成
		if(submitType.endsWith("done")){
			//首先保存住所有的答案到Session
			mergeIntoSession();
			//然后保存所有的答案到数据库
			saveAllAnswers();
			return "toEntrySurveyPageAction";
		}
		//退出
		if(submitType.endsWith("exit")){
			//首先清除掉session中的所有答案
			clearAnswersOnSession();	
			//返回到前端的调查页面，显示出所有可用的调查
			return "toEntrySurveyPageAction";
		}
		return "";
	}
	//保存所有的答案到数据库
	private void saveAllAnswers() {
		/*
		 * 使用一个单独的Map封装所有的单选按钮矩阵题型
		 * Integer类型是questionId
		 * String类型是该题目中的所有单选按钮选项的集合
		 */
		Map<Integer,String>matrixRadioMap=new HashMap<Integer,String>();
		//使用一个集合保存住所有的答案
		List<Answer>answers=new ArrayList<Answer>();
		Map<String,Map<String,String[]>>allAnswers=(Map<String, Map<String, String[]>>) this.session.get(this.ALL_PARAMETERS);
		for(Map<String,String[]>map:allAnswers.values()){
			String key;
			String[] values;
			for(Map.Entry<String, String[]>entry:map.entrySet()){
				key=entry.getKey();
				values=entry.getValue();
				//首先将所有q开头的参数都挑选出来，表明是答案的选项
				if(key.startsWith("q")){
					//这是最普通的一种答案，只是q开头，然后后面跟上一个题目的标号id
					if(!key.contains("other")&&!key.contains("_")){
						Answer answer=new Answer();
						answer.setAnswerIndexs(StringUtils.arr2String(values));			//设置所有的答案选项
//						answer.setAnswerTime(new Date());				//设置答题时间,这个字段先不要填写
						answer.setQuestionId(getQuestionId(key));				//设置问题id
						answer.setSurveyId(getSurveyId());				//设置调查id
//						answer.setUuid(null);					//设置回答批次：回答批次不能乱写，一次回答中的所有回答都必须使用一个回答批次
						//处理其他项，默认所有题目都带有其他项，这样方便处理，注意，这里必须在map变量中查找
						String[] otherValuesArr=map.get(key+"other");
						answer.setOtherAnswer(StringUtils.arr2String(otherValuesArr));			//如果有其它答案的话设置其它答案
						answers.add(answer);
					}else if(key.contains("_")){		//如果name属性值中带有"_"，表名一定是矩阵式单选按钮的问题
						Integer questionId=getMatrixRadioQuestionId(key);
						String oldValue=matrixRadioMap.get(questionId);
						if(oldValue!=null){
							matrixRadioMap.put(questionId, oldValue+","+StringUtils.arr2String(values));
						}else{
							matrixRadioMap.put(questionId,StringUtils.arr2String(values));
						}
					}
				}
			}
			//最后集中处理单选按钮矩阵式问题，将所有答案保存到anwers中
			saveRadioMatrixMapToAnwers(answers,matrixRadioMap);
		}
		
		/**
		 * 在这里创建SurveyToken对象并绑定到当前线程
		 * TODO 使用分库把保存答案：除了修改配置文件之外，还需要修改该处。
		 */
		writeAnswersToDB(answers);
	}
	/**
	 * 将答案全部写到数据库
	 * @param answers
	 */
	private void writeAnswersToDB(List<Answer> answers) {
		SurveyToken surveyToken=new SurveyToken();
		Survey survey=this.surveyService.getModelById(getSurveyId());
		surveyToken.setSurvey(survey);
		SurveyToken.bind(surveyToken);
		this.answerService.saveAllAnswers(answers);
	}
	//将矩阵式单选按钮的问题保存到answes中
	private void saveRadioMatrixMapToAnwers(List<Answer> answers, Map<Integer, String> matrixRadioMap) {
		Integer key;
		String value;
		for(Map.Entry<Integer, String>entry:matrixRadioMap.entrySet()){
			key=entry.getKey();
			value=entry.getValue();
			Answer answer=new Answer();
			answer.setAnswerIndexs(value);
//			answer.setAnswerTime(new Date());
			answer.setQuestionId(key);
			answer.setSurveyId(getSurveyId());
			answers.add(answer);
		}
	}
	//获取单选按钮矩阵题型的题目标识Id
	private Integer getMatrixRadioQuestionId(String key) {
		return Integer.parseInt(key.substring(1,key.indexOf("_")));
	}
	//根据Session中的信息得到SurveyId的信息
	private Integer getSurveyId() {
		Survey survey=(Survey) this.session.get(this.CURRENT_SURVEY);
		return survey.getSurveyId();
	}
	private Integer getQuestionId(String questionName) {
		return Integer.parseInt(questionName.substring(1));
	}
	//清除掉Session中的所有答案
	private void clearAnswersOnSession() {
		this.session.remove(this.ALL_PARAMETERS);
	}
	//将Parameters中的相关答案选项取出保存到Session中
	private void mergeIntoSession() {
		Map<String,Map<String,String[]>>allAnswers=(Map<String, Map<String, String[]>>) this.session.get(this.ALL_PARAMETERS);
		//将当前页面上的答案集合保存到所有答案的集合中
		allAnswers.put(pageId+"", this.parameters);			//将传递过来的所有参数全部保存起来
	}
	//得到提交类型，包括四种类型，上一题、下一题、完成、退出四中动作
	private String getSubmitType() {
		for(String name:this.parameters.keySet()){
			if(name.startsWith("submit_")){
				return name;
			}
		}
		return null;
	}
	private Map<String,Object>session;
	@Override
	public void setSession(Map<String, Object> session) {
		this.session=session;
	}
	
	public String setTag(String pageId,String name,String value,String tag){
		Map<String,Map<String,String[]>>allAnswers=(Map<String, Map<String, String[]>>) this.session.get(this.ALL_PARAMETERS);
		Map<String,String[]>pageAnswers=allAnswers.get(pageId);
		if(pageAnswers!=null){
			String []values=pageAnswers.get(name);
			if(values!=null){
				for(String temp:values){
					if(temp.equals(value)){
						return tag;
					}
				}
			}
		}
		return "";
	}
	
	public String setText(String pageId,String name){
		Map<String,Map<String,String[]>>allAnswers=(Map<String, Map<String, String[]>>) this.session.get(this.ALL_PARAMETERS);
		Map<String,String[]>pageAnswers=allAnswers.get(pageId);
		if(pageAnswers!=null){
			String []values=pageAnswers.get(name);
			if(values!=null){
				return "value='"+values[0]+"'";
			}
		}
		return "";
	}
}
