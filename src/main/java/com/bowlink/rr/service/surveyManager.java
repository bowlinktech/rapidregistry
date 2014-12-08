/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.service;

import com.bowlink.rr.model.SurveyChangeLogs;
import com.bowlink.rr.model.surveys;

import java.util.List;

/**
 *
 * @author chadmccue
 */
public interface surveyManager {
    
    List<surveys> getActiveSurveys(Integer programId) throws Exception;
    
    List<surveys> getProgramSurveys(Integer programId) throws Exception;
    
    List<surveys> getProgramSurveysByTitle(surveys survey) throws Exception;
    
    Integer saveSurvey (surveys survey) throws Exception;
    
    Integer surveyTakenTimes (Integer surveyId) throws Exception;
    
    surveys getSurveysById(Integer surveyId) throws Exception;
    
    void updateSurvey (surveys survey) throws Exception;
    
    void saveChangeLogs(SurveyChangeLogs scl) throws Exception;
    
    List <SurveyChangeLogs> getSurveyChangeLogs (Integer surveyId) throws Exception;
    
}
