/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.service.impl;

import com.bowlink.rr.dao.surveyDAO;
import com.bowlink.rr.model.SurveyChangeLogs;
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
	public surveys getSurveysById(Integer surveyId) throws Exception {
		return surveyDAO.getSurveysById(surveyId);
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
	
}
