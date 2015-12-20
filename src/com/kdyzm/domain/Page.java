package com.kdyzm.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 这个类对应着页面类
 * @author kdyzm
 *
 */
public class Page implements Serializable{
	private static final long serialVersionUID = -747120384551834075L;
	private transient Integer pageId;			//页面id
	private String title="未命名";		//页面标题
	private String description;	//页面描述
	
	//page和调查之间是多对一的关系
	private transient Survey survey;
	//page和Question之间是一对多的关系
	private Set<Question> questions=new HashSet<Question>();
	private float orderNo;		//排序的优先级,默认值和pageId相同
/*************************华丽的分割线*****************************************/
	public String getTitle() {
		return title;
	}
	public float getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(float orderNo) {
		this.orderNo = orderNo;
	}
	public Integer getPageId() {
		return pageId;
	}
	public void setPageId(Integer pageId) {
		this.pageId = pageId;
		//在设置pageId的同时同时设置orderNo
		this.orderNo=pageId;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Survey getSurvey() {
		return survey;
	}
	public void setSurvey(Survey survey) {
		this.survey = survey;
	}
	public Set<Question> getQuestions() {
		return questions;
	}
	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}
}