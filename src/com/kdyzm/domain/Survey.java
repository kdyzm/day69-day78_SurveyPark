package com.kdyzm.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 改类是调查类
 * @author kdyzm
 *
 */
public class Survey implements Serializable{
	private static final long serialVersionUID = -6778406510696641356L;
	private Integer surveyId;			//对应着调查id
	private String title="未命名";		//对应着调查名称
	private String preText="上一页";		//对应着翻页的上一个提示
	private String nextText="下一页";	//对应着下一页的提示
	private String exitText="退出";		//对应着退出的提示
	private String doneText="完成";	//对应着完成的提示文本
	private Date createDate=new Date();	//对应着创建的日期
	private String logoPath;			//使用该字段保存图标的位置，保存的是相对位置
	//调查和用户之间是多对一的关系
	private User user;
	//调查和Page之间是一对多的关系
	private transient Set<Page>pages=new HashSet<Page>();
	
	//添加一个调查是否可用的字段，表示打开或者关闭调查
	private boolean closed;
	
	/**
	 * TODO 在数据库库中没有定义，但是需要在配置文件中定义并带到前端页面中使用
	 */
	private float maxOrderNo;			//最小页序
	private float minOrderNo;			//最大页序
	
	//定义几个常量，方便判断是哪种类型的提交
	private  String submit_next="下一页";
	private String submit_pre="上一页";
	private String submit_done="提交";
	private String submit_exit="退出";
/*********************************************************************/
	public Set<Page> getPages() {
		return pages;
	}
	
	public String getSubmit_next() {
		return submit_next;
	}
	
	
	public void setSubmit_next(String submit_next) {
		this.submit_next = submit_next;
	}
	
	
	public String getSubmit_pre() {
		return submit_pre;
	}
	
	
	public void setSubmit_pre(String submit_pre) {
		this.submit_pre = submit_pre;
	}
	
	
	public String getSubmit_done() {
		return submit_done;
	}
	
	
	public void setSubmit_done(String submit_done) {
		this.submit_done = submit_done;
	}
	
	
	public String getSubmit_exit() {
		return submit_exit;
	}
	
	
	public void setSubmit_exit(String submit_exit) {
		this.submit_exit = submit_exit;
	}
	
	
		public float getMaxOrderNo() {
		return maxOrderNo;
	}
	
	public void setMaxOrderNo(float maxOrderNo) {
		this.maxOrderNo = maxOrderNo;
	}
	
	public float getMinOrderNo() {
		return minOrderNo;
	}
	
	public void setMinOrderNo(float minOrderNo) {
		this.minOrderNo = minOrderNo;
	}

	public String getLogoPath() {
		return logoPath;
	}
	
	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public boolean isClosed() {
		return closed;
	}
	
	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	public Integer getSurveyId() {
		return surveyId;
	}
	
	public void setSurveyId(Integer surveyId) {
		this.surveyId = surveyId;
	}

	public void setPages(Set<Page> pages) {
		this.pages = pages;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPreText() {
		return preText;
	}

	public void setPreText(String preText) {
		this.preText = preText;
	}

	public String getNextText() {
		return nextText;
	}

	public void setNextText(String nextText) {
		this.nextText = nextText;
	}

	public String getExitText() {
		return exitText;
	}

	public void setExitText(String exitText) {
		this.exitText = exitText;
	}

	public String getDoneText() {
		return doneText;
	}

	public void setDoneText(String doneText) {
		this.doneText = doneText;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
