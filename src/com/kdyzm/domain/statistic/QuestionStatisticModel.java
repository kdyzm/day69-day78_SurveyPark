package com.kdyzm.domain.statistic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.kdyzm.domain.Question;

/**
 * 问题统计模型
 * 所有的统计数据都需要封装到该类中
 * 只需要获取该类，那么所有种类的统计图都能够获取到
 * @author kdyzm
 *
 */
public class QuestionStatisticModel implements Serializable{
	private static final long serialVersionUID = 1670744326119856169L;
	//对应的那个问题
	private Question question;		
	//回答的人数
	private int count;	
	//问题中每个选项的统计情况
	private List<OptionStatisticModel> osms=new ArrayList<OptionStatisticModel>();//这个直接初始化的时候赋值，方便以后调用
/**********************************************************************************/
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<OptionStatisticModel> getOsms() {
		return osms;
	}
	public void setOsms(List<OptionStatisticModel> osms) {
		this.osms = osms;
	}
}
