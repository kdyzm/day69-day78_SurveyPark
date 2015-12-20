package com.kdyzm.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 答案实体类
 * @author kdyzm
 *
 */
public class Answer implements Serializable{
	private static final long serialVersionUID = 7119126638965650753L;
	private Integer answerId;			//答案ID
	private String answerIndexs;		//选项的索引
	private String otherAnswer;			//其余选项
	private String uuid;				//答案的批次
	private Date answerTime=new Date();			//提交答案的时间
	
	private Integer questionId	;		//关联到的问题实体id
	//下面的是冗余的字段，仅仅提供冗余功能，方便查询，所以不要建立他们之间的外键联系
	private Integer surveyId;			//关联到的调查实体id
/*****************************华丽的分割线*********************************************/
	public Integer getAnswerId() {
		return answerId;
	}
	public void setAnswerId(Integer answerId) {
		this.answerId = answerId;
	}
	public String getAnswerIndexs() {
		return answerIndexs;
	}
	public void setAnswerIndexs(String answerIndexs) {
		this.answerIndexs = answerIndexs;
	}
	public String getOtherAnswer() {
		return otherAnswer;
	}
	public void setOtherAnswer(String otherAnswer) {
		this.otherAnswer = otherAnswer;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public Date getAnswerTime() {
		return answerTime;
	}
	public void setAnswerTime(Date answerTime) {
		this.answerTime = answerTime;
	}
	public Integer getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}
	public Integer getSurveyId() {
		return surveyId;
	}
	public void setSurveyId(Integer surveyId) {
		this.surveyId = surveyId;
	}
}
