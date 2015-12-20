package com.kdyzm.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kdyzm.dao.impl.AnswerDaoImpl;
import com.kdyzm.dao.impl.QuestionDaoImpl;
import com.kdyzm.domain.Question;
import com.kdyzm.service.QuestionService;
@Service("questionService")
public class QuestionServiceImpl implements QuestionService{
	@Resource(name="questionDao")
	private QuestionDaoImpl questionDao;
	@Resource(name="answerDao")
	private AnswerDaoImpl answerDao;

	@Override
	public void saveQuestion(Question model) {
		this.questionDao.saveOrUpdateEntity(model);
	}
	//获取Question对象的方法
	@Override
	public Question getQuestion(Integer questionId) {
		return questionDao.getEntityById(questionId);
	}
	//删除问题的同时需要将所有的问题的答案也同时删除掉
	@Override
	public void deleteQuestion(Question question) {
		String hql="delete from Answer a where a.questionId=?";
		questionDao.batchEntityByHql(hql, question.getQuestionId());
		
		hql="delete from Question q where q.questionId=?";
		this.questionDao.batchEntityByHql(hql, question.getQuestionId());
	}
}
