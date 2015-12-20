package com.kdyzm.service;

import com.kdyzm.domain.Question;

public interface QuestionService {

	void saveQuestion(Question model);

	Question getQuestion(Integer questionId);

	void deleteQuestion(Question question);

}
