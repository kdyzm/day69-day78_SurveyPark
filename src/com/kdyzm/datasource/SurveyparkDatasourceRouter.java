package com.kdyzm.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.kdyzm.domain.Survey;

/**
 * 自定义数据源路由器
 * 	有一个默认的实现类，该类是以传播属性来路由数据的。
 * @author kdyzm
 *
 */
public class SurveyparkDatasourceRouter extends AbstractRoutingDataSource{

	/**
	 * 该方法实际上确定了一个数据向哪里存放的策略
	 * 在这里使用id的就属性来确定
	 * 如果答案id是偶数，就想lsn_surveypark数据库中的answer表（主表）中存放
	 * 如果答案的id是奇数，就向lsn_surveypark1数据库中的answer表（从表）中存放
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		SurveyToken surveyToken=SurveyToken.getSurveyToken();
		if(surveyToken!=null){
			Survey survey=surveyToken.getSurvey();
			int surveyId=survey.getSurveyId();
			System.out.println("Survey对象不为空，值为："+surveyId);
			/**
			 * 在这里必须解除绑定
			 * 如果不在这里解除绑定的话就会将log日志写入到lsn_surveypark1数据库中。
			 * 由于lsn_surveypark1数据库中没有log表，所以一定会报错
			 */
			SurveyToken.unbind();
			return (surveyId%2)==0?"even":"odd";	//如果是偶数返回even字符串，如果是奇数返回odd字符串
		}
		System.out.println("survey对象为空");
		return null;
	}
	
}
