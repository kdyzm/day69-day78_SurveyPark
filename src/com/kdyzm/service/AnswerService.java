package com.kdyzm.service;

import java.util.List;

import com.kdyzm.domain.Answer;

public interface AnswerService {

	void saveAnswer(Answer answer);

	void saveAllAnswers(List<Answer> answers);

}
