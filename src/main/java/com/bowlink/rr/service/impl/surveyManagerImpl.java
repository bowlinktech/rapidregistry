/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.service.impl;

import com.bowlink.rr.dao.surveyDAO;
import com.bowlink.rr.model.AnswerTypes;
import com.bowlink.rr.model.SurveyAnswers;
import com.bowlink.rr.model.SurveyChangeLogs;
import com.bowlink.rr.model.SurveyPages;
import com.bowlink.rr.model.SurveyQuestions;
import com.bowlink.rr.model.surveys;
import com.bowlink.rr.service.surveyManager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author chadmccue
 */
@Service
public class surveyManagerImpl implements surveyManager {
    
    @Autowired
    surveyDAO surveyDAO;
    
    @Override
    @Transactional
    public List<surveys> getActiveSurveys(Integer programId) throws Exception {
        return surveyDAO.getActiveSurveys(programId);
    }
    
    @Override
    @Transactional
    public List<surveys> getProgramSurveys(Integer programId) throws Exception {
        return surveyDAO.getProgramSurveys(programId);
    }
    
    @Override
    @Transactional
    public List<surveys> getProgramSurveysByTitle(surveys survey) throws Exception {
        return surveyDAO.getProgramSurveysByTitle(survey);
    }
    
    @Override
    @Transactional
    public Integer saveSurvey(surveys survey) throws Exception {
       return surveyDAO.saveSurvey(survey);
    }
    
    @Override
    @Transactional
    public Integer surveyTakenTimes(Integer surveyId) throws Exception {
       return surveyDAO.surveyTakenTimes(surveyId);
    }

	@Override
	@Transactional
	public surveys getSurveyById(Integer surveyId) throws Exception {
		/**first we get the survey details**/
		surveys survey = surveyDAO.getSurveyById(surveyId);
		/** now we get the pages **/
		if (survey != null) {
			survey.setSurveyPages(getSurveyPages(surveyId, true));
		}
		return survey;
	}
    
	@Override
	@Transactional
	public void updateSurvey(surveys survey) throws Exception {
		surveyDAO.updateSurvey(survey);
	}
	
	@Override
	@Transactional
	public void saveChangeLogs(SurveyChangeLogs scl) throws Exception {
		surveyDAO.saveChangeLogs(scl);
	}
	
	@Override
	@Transactional
	public List <SurveyChangeLogs> getSurveyChangeLogs (Integer surveyId)  throws Exception {
		return surveyDAO.getSurveyChangeLogs(surveyId);
	}

	@Override
	@Transactional
	public List<AnswerTypes> getAnswerTypes() throws Exception {
		return surveyDAO.getAnswerTypes();
	}

	@Override
	@Transactional
	public List<SurveyPages> getSurveyPages(Integer surveyId, boolean getQuestions) throws Exception {
		List<SurveyPages> surveyPages = surveyDAO.getSurveyPages(surveyId, getQuestions) ;
		if (getQuestions) {
		/** now we loop and get page questions **/
			for (SurveyPages page: surveyPages) {
				page.setSurveyQuestions(getSurveyQuestions(page.getId()));
			}
		}
		return surveyPages;
	}

	@Override
	public List<SurveyQuestions> getSurveyQuestions(Integer surveyPageId)
			throws Exception {
		List<SurveyQuestions> surveyQuestions = surveyDAO.getSurveyQuestions(surveyPageId);
		/** we get answers here **/
		for (SurveyQuestions question: surveyQuestions) {
			question.setSurveyAnswers(getSurveyAnswers(question.getId()));
		}
		return surveyQuestions;
	}

	@Override
	public List<SurveyAnswers> getSurveyAnswers(Integer questionId)
			throws Exception {		
		return surveyDAO.getSurveyAnswers(questionId);
	}

	@Override
	@Transactional
	public Integer createSurveyPage(SurveyPages surveyPage) throws Exception {
		return surveyDAO.createSurveyPage(surveyPage);
	}


	@Override
	@Transactional
	public void updateSurveyPage(SurveyPages surveyPage) throws Exception {
		surveyDAO.updateSurveyPage(surveyPage);		
	}

	@Override
	@Transactional
	public SurveyPages getSurveyPageById(Integer pageId) throws Exception {
		return surveyDAO.getSurveyPageById(pageId);		
	}
		
}
