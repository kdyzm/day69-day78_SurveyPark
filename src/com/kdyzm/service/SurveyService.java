package com.kdyzm.service;

import java.util.Collection;
import java.util.List;

import com.kdyzm.domain.Answer;
import com.kdyzm.domain.Page;
import com.kdyzm.domain.Survey;
import com.kdyzm.domain.User;

public interface SurveyService{

	void createNewSurvey(User user);

	List<Survey> findMySurveys(User user);

	Survey getModelById(int i);

	void updateSurvey(Survey model);

	void deleteSurvey(Survey model);

	void clearSurvey(Survey model);

	void updateSurveyClosedState(Survey model);

	Collection<Survey> getAllSurveys(User user);

	Collection<Survey> getAllAvailableSurveys();

	Page getResponsePage(Survey model, float requestPage);

}
