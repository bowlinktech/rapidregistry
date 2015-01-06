/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.dao;

import com.bowlink.rr.model.AnswerTypes;
import com.bowlink.rr.model.SurveyAnswers;
import com.bowlink.rr.model.SurveyChangeLogs;
import com.bowlink.rr.model.SurveyPages;
import com.bowlink.rr.model.SurveyQuestions;
import com.bowlink.rr.model.surveys;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 *
 * @author chadmccue
 */
@Repository
public interface surveyDAO {
    
    List<surveys> getActiveSurveys(Integer programId) throws Exception;
    
    List<surveys> getProgramSurveys(Integer programId) throws Exception;
    
    List<surveys> getProgramSurveysByTitle(surveys survey) throws Exception;
    
    Integer saveSurvey (surveys survey) throws Exception;
    
    Integer surveyTakenTimes (Integer surveyId) throws Exception;
    
    surveys getSurveyById(Integer surveyId) throws Exception;
    
    void updateSurvey (surveys survey) throws Exception;
    
    void saveChangeLogs(SurveyChangeLogs scl) throws Exception;
    
    List <SurveyChangeLogs> getSurveyChangeLogs (Integer surveyId) throws Exception;
    
    List <AnswerTypes> getAnswerTypes () throws Exception;
    
    List <SurveyPages> getSurveyPages(Integer surveyId, boolean getQuestions) throws Exception;
    
    List <SurveyQuestions> getSurveyQuestions(Integer surveyPageId) throws Exception;
    
    List<SurveyAnswers> getSurveyAnswers(Integer questionId) throws Exception;
    
}
