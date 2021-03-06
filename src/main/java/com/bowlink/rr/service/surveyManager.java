/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.service;

import com.bowlink.rr.model.AnswerTypes;
import com.bowlink.rr.model.SurveyCategories;
import com.bowlink.rr.model.SurveyCategoryAssociation;
import com.bowlink.rr.model.SurveyQuestionChoices;
import com.bowlink.rr.model.SurveyChangeLogs;
import com.bowlink.rr.model.SurveyPages;
import com.bowlink.rr.model.SurveyQuestions;
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
    
    surveys getSurveyById(Integer surveyId) throws Exception;
    
    void updateSurvey (surveys survey) throws Exception;
    
    void saveChangeLogs(SurveyChangeLogs scl) throws Exception;
    
    List <SurveyChangeLogs> getSurveyChangeLogs (Integer surveyId) throws Exception;
    
    List <AnswerTypes> getAnswerTypes () throws Exception;
    
    List <SurveyPages> getSurveyPages(Integer surveyId, boolean getQuestions) throws Exception;
    
    List <SurveyQuestions> getSurveyQuestions(Integer surveyPageId) throws Exception;
    
    List <SurveyQuestions> getAllSurveyQuestions(Integer surveyId) throws Exception;
    
    Integer createSurveyPage (SurveyPages surveyPage) throws Exception;
    
    void updateSurveyPage (SurveyPages surveyPage) throws Exception;
    
    SurveyPages getSurveyPageById (Integer pageId) throws Exception;
    
    SurveyQuestions getSurveyQuestionById (Integer questionId) throws Exception;
    
    List<SurveyPages> getSurveyPagesByPageNum(Integer surveyId, Integer nextPageNum) throws Exception;
    
    Integer saveNewSurveyQuestion(SurveyQuestions surveyQuestion) throws Exception;
    
    void saveSurveyQuestion(SurveyQuestions surveyQuestion) throws Exception;
    
    List getQuestionForSelectedPage(Integer pageId, Integer questionId) throws Exception;
    
    List<SurveyQuestionChoices> getQuestionChoices(Integer questionId) throws Exception;
    
    void removeQuestionChoices(Integer questionId) throws Exception;
    
    void saveQuestionChoice(SurveyQuestionChoices questionChoice) throws Exception;
    
    void deleteSurveyPage(Integer pageId) throws Exception;
    
    void removeDateRows(Integer questionId) throws Exception;
    
    Integer copySurvey(Integer surveyId) throws Exception;
    
    List<surveys> getProgramSurveysByTag(surveys survey) throws Exception;
    
    boolean checkForDuplicateQuestionTag(Integer surveyId, Integer questionId, String questionTag) throws Exception;
    
    List<SurveyCategories> getProgramSurveyCategories(Integer programId) throws Exception;
    
    SurveyCategoryAssociation getProgramSurveyCategory(Integer programId, Integer surveyId) throws Exception;
    
    void saveProgramSurveyCategoryAssociation(SurveyCategoryAssociation surveycategoryassociation) throws Exception;
    
    void removeProgramSurveyCategory(Integer programId, Integer surveyId) throws Exception;
    
    SurveyCategories getProgramSurveyCategoryById(Integer surveyCategoryId) throws Exception;
    
    void saveProgramSurveyCategory(SurveyCategories surveyCategoryDetails) throws Exception;
    
    void deleteProgramSurveyCategoryAssociation(Integer programId, Integer surveyCategoryId);
	
    void deleteProgramSurveyCategory(Integer programId, Integer surveyCategoryId);
    
    List getQuestionsForSelectedSurvey(Integer surveyId) throws Exception;
}
