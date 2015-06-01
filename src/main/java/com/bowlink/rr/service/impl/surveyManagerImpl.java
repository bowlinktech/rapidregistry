/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.service.impl;

import com.bowlink.rr.dao.surveyDAO;
import com.bowlink.rr.model.AnswerTypes;
import com.bowlink.rr.model.SurveyQuestionChoices;
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
    public surveys getSurveyById(Integer surveyId) throws Exception {
        /**
         * first we get the survey details*
         */
        surveys survey = surveyDAO.getSurveyById(surveyId);
        /**
         * now we get the pages *
         */
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
    public List<SurveyChangeLogs> getSurveyChangeLogs(Integer surveyId) throws Exception {
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
        List<SurveyPages> surveyPages = surveyDAO.getSurveyPages(surveyId, getQuestions);
        if (getQuestions) {
            /**
             * now we loop and get page questions *
             */
            for (SurveyPages page : surveyPages) {
                page.setSurveyQuestions(getSurveyQuestions(page.getId()));
            }
        }
        return surveyPages;
    }

    @Override
    @Transactional
    public List<SurveyQuestions> getSurveyQuestions(Integer surveyPageId) throws Exception {
        List<SurveyQuestions> surveyQuestions = surveyDAO.getSurveyQuestions(surveyPageId);
        
        return surveyQuestions;
    }
    
     @Override
    @Transactional
    public List <SurveyQuestions> getAllSurveyQuestions(Integer surveyId) throws Exception {
         return surveyDAO.getAllSurveyQuestions(surveyId);
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

    @Override
    @Transactional
    public SurveyQuestions getSurveyQuestionById(Integer questionId) throws Exception {
        SurveyQuestions question = surveyDAO.getSurveyQuestionById(questionId);
        
        return question;
    }
    
    @Override
    @Transactional
    public List<SurveyPages> getSurveyPagesByPageNum(Integer surveyId, Integer nextPageNum) throws Exception {
        return surveyDAO.getSurveyPagesByPageNum(surveyId, nextPageNum);
    }
    
    @Override
    @Transactional
    public Integer saveNewSurveyQuestion(SurveyQuestions surveyQuestion) throws Exception {
        return surveyDAO.saveNewSurveyQuestion(surveyQuestion);
    }
    
    @Override
    @Transactional
    public void saveSurveyQuestion(SurveyQuestions surveyQuestion) throws Exception {
        surveyDAO.saveSurveyQuestion(surveyQuestion);
    }
    
    @Override
    @Transactional
    public List getQuestionForSelectedPage(Integer pageId, Integer questionId) throws Exception {
        return surveyDAO.getQuestionForSelectedPage(pageId, questionId);
    }
    
    @Override
    @Transactional
    public List<SurveyQuestionChoices> getQuestionChoices(Integer questionId) throws Exception {
        return surveyDAO.getQuestionChoices(questionId);
    }
    
    @Override
    @Transactional
    public void removeQuestionChoices(Integer questionId) throws Exception {
        surveyDAO.removeQuestionChoices(questionId);
    }
    
    @Override
    @Transactional
    public void saveQuestionChoice(SurveyQuestionChoices questionChoice) throws Exception {
        surveyDAO.saveQuestionChoice(questionChoice);
    }
    
    @Override
    @Transactional
    public void deleteSurveyPage(Integer pageId) throws Exception {
        surveyDAO.deleteSurveyPage(pageId);
    }
    
    @Override
    @Transactional
    public void removeDateRows(Integer questionId) throws Exception {
        surveyDAO.removeDateRows(questionId);
    }
    
    @Override
    @Transactional
    public Integer copySurvey(Integer surveyId) throws Exception {
        
        /* Get the details of the passed in survey */
        surveys surveyDetails = surveyDAO.getSurveyById(surveyId);
        
        surveys newSurvey = new surveys();
        newSurvey.setProgramId(surveyDetails.getProgramId());
        newSurvey.setStatus(surveyDetails.getStatus());
        newSurvey.setRequireEngagement(surveyDetails.getRequireEngagement());
        newSurvey.setAllowEngagement(surveyDetails.getAllowEngagement());
        newSurvey.setDuplicatesAllowed(surveyDetails.getDuplicatesAllowed());
        newSurvey.setTitle(surveyDetails.getTitle()+" Copy");
        newSurvey.setDoneButtonText(surveyDetails.getDoneButtonText());
        newSurvey.setdoneBtnActivityCodeId(surveyDetails.getDoneBtnActivityCodeId());
        newSurvey.setNextButtonText(surveyDetails.getNextButtonText());
        newSurvey.setNextBtnActivityCodeId(surveyDetails.getNextBtnActivityCodeId());
        newSurvey.setPrevButtonText(surveyDetails.getPrevButtonText());
        newSurvey.setPrevBtnActivityCodeId(surveyDetails.getPrevBtnActivityCodeId());
        newSurvey.setReminderStatus(surveyDetails.getReminderStatus());
        newSurvey.setReminderText(surveyDetails.getReminderText());
        
        Integer newSurveyId = surveyDAO.saveSurvey(newSurvey);
        
        /* Get survey pages */
        List<SurveyPages> surveyPages = surveyDAO.getSurveyPages(surveyId, false);
        
        if(surveyPages != null && !surveyPages.isEmpty()) {
            
            for(SurveyPages page : surveyPages) {
                Integer currPageId = page.getId();
                
                SurveyPages newSurveyPage = new SurveyPages();
                newSurveyPage.setSurveyId(newSurveyId);
                newSurveyPage.setPageTitle(page.getPageTitle());
                newSurveyPage.setPageDesc(page.getPageDesc());
                newSurveyPage.setPageNum(page.getPageNum());
                
                Integer newSurveyPageId = surveyDAO.createSurveyPage(newSurveyPage);
                
                /* Get the page questions */
                List<SurveyQuestions> surveyQuestions = surveyDAO.getAllSurveyQuestions(surveyId);
                
                if(surveyQuestions != null && !surveyQuestions.isEmpty()) {
                    
                    for(SurveyQuestions question : surveyQuestions) {
                        
                        Integer currQuestionId = question.getId();
                        
                        SurveyQuestions newQuestion = new SurveyQuestions();
                        newQuestion.setSurveyId(newSurveyId);
                        newQuestion.setSurveyPageId(newSurveyPageId);
                        newQuestion.setHide(question.isHide());
                        newQuestion.setRequired(question.isRequired());
                        newQuestion.setRequiredResponse(question.getRequiredResponse());
                        newQuestion.setDspQuestionId(question.getDspQuestionId());
                        newQuestion.setQuestion(question.getQuestion());
                        newQuestion.setAnswerTypeId(question.getAnswerTypeId());
                        newQuestion.setColumnsDisplayed(question.getColumnsDisplayed());
                        newQuestion.setAllowMultipleAns(question.isAllowMultipleAns());
                        newQuestion.setSaveToFieldId(question.getSaveToFieldId());
                        newQuestion.setAutoPopulateFromField(question.isAutoPopulateFromField());
                        newQuestion.setQuestionNum(question.getQuestionNum());
                        newQuestion.setValidationId(question.getValidationId());
                        newQuestion.setAlphabeticallySort(question.isAlphabeticallySort());
                        newQuestion.setChoiceLayout(question.getChoiceLayout());
                        newQuestion.setPopulateFromTable(question.getPopulateFromTable());
                        newQuestion.setDeleted(question.isDeleted());
                        newQuestion.setOtherOption(question.isOtherOption());
                        newQuestion.setOtherLabel(question.getOtherLabel());
                        newQuestion.setOtherDspChoice(question.getOtherDspChoice());
                        newQuestion.setDateFormatType(question.getDateFormatType());
                        newQuestion.setDateType(question.getDateType());
                        newQuestion.setIncludeTime(question.isIncludeTime());
                        
                        Integer newQuestionId = surveyDAO.saveNewSurveyQuestion(newQuestion);
                        
                        List<SurveyQuestionChoices> surveyQuestionChoices = surveyDAO.getQuestionChoices(currQuestionId);
                        
                        if(surveyQuestionChoices != null && !surveyQuestionChoices.isEmpty()) {
                            
                            for(SurveyQuestionChoices questionChoice : surveyQuestionChoices) {
                                
                                SurveyQuestionChoices newQuestionChoice = new SurveyQuestionChoices();
                                newQuestionChoice.setQuestionId(newQuestionId);
                                newQuestionChoice.setSkipToPageId(questionChoice.getSkipToPageId());
                                newQuestionChoice.setSkipToQuestionId(questionChoice.getSkipToQuestionId());
                                newQuestionChoice.setChoiceText(questionChoice.getChoiceText());
                                newQuestionChoice.setActivityCodeId(questionChoice.getActivityCodeId());
                                newQuestionChoice.setDefAnswer(questionChoice.isDefAnswer());
                                newQuestionChoice.setChoiceValue(questionChoice.getChoiceValue());
                                newQuestionChoice.setSkipToEnd(questionChoice.isSkipToEnd());
                                
                                surveyDAO.saveQuestionChoice(newQuestionChoice);
                            }
                            
                        }
                    }
                }
            }
        }
        
        return newSurveyId;
    }
}

