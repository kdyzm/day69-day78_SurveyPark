package com.kdyzm.datasource;

import com.kdyzm.domain.Survey;

/**
 * 令牌类
 * 封装了一些比较重要的属性
 * @author kdyzm
 *
 */
public class SurveyToken {
	private Survey survey;		//绑定的对象的值，如果只是绑定surveyId也可以，但是为了以后的方便起见，使用该对象更划算
	private static ThreadLocal<SurveyToken> t=new ThreadLocal<SurveyToken>();
	public Survey getSurvey() {
		return survey;
	}
	public void setSurvey(Survey survey) {
		this.survey = survey;
	}
	/**
	 * 绑定当前线程和SurveyToken对象之间的关系
	 */
	public static void bind(SurveyToken surveyToken){
		t.set(surveyToken);
	}
	
	/**
	 * 解除当前线程和SurveyToken对象之间的关系
	 */
	public static void unbind(){
		t.remove();
	}
	
	/**
	 * 获取SurveyToken对象的方法
	 */
	public static SurveyToken getSurveyToken(){
		return t.get();
	}
}
