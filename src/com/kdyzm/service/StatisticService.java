package com.kdyzm.service;

import com.kdyzm.domain.Question;
import com.kdyzm.domain.statistic.QuestionStatisticModel;

/**
 * 统计服务类的接口
 * @author kdyzm
 *
 */
public interface StatisticService {
	public QuestionStatisticModel statics(Question question);
}
