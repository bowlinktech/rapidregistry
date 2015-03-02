/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.dao.impl;

import com.bowlink.rr.dao.surveyDAO;
import com.bowlink.rr.model.AnswerTypes;
import com.bowlink.rr.model.EngagementSurveys;
import com.bowlink.rr.model.SurveyQuestionChoices;
import com.bowlink.rr.model.SurveyChangeLogs;
import com.bowlink.rr.model.SurveyPages;
import com.bowlink.rr.model.SurveyQuestions;
import com.bowlink.rr.model.surveys;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author chadmccue
 */
@Repository
public class surveyDAOImpl implements surveyDAO {

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * The 'getActiveSurveys' function will return a list of active surveys for the passed in programId.
     *
     * @param programId The id of the program to return surveys.
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<surveys> getActiveSurveys(Integer programId) throws Exception {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(surveys.class);
        criteria.add(Restrictions.eq("programId", programId));
        criteria.add(Restrictions.eq("status", true));
        return criteria.list();
    }

    /**
     * The 'getProgramSurveys' function will return a list of surveys for the passed in programId.
     *
     * @param programId The id of the program to return surveys.
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<surveys> getProgramSurveys(Integer programId) throws Exception {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(surveys.class);
        criteria.add(Restrictions.eq("programId", programId));

        List<surveys> surveyList = criteria.list();
        /**
         * now we loop and calculate times taken *
         */
        for (surveys survey : surveyList) {
            survey.setTimesTaken(surveyTakenTimes(survey.getId()));
        }

        return surveyList;
    }

    @SuppressWarnings("unchecked")
    public List<surveys> getProgramSurveysByTitle(surveys survey) throws Exception {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(surveys.class);
        criteria.add(Restrictions.eq("programId", survey.getProgramId()));
        criteria.add(Restrictions.eq("title", survey.getTitle()));
        return criteria.list();
    }

    public Integer saveSurvey(surveys survey) throws Exception {
        Integer lastId = null;
        lastId = (Integer) sessionFactory.getCurrentSession().save(survey);
        return lastId;
    }

    public Integer surveyTakenTimes(Integer surveyId) throws Exception {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(EngagementSurveys.class);
        criteria.add(Restrictions.eq("surveyId", surveyId));
        return criteria.list().size();
    }

    @SuppressWarnings("unchecked")
    public surveys getSurveyById(Integer surveyId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(surveys.class);
        criteria.add(Restrictions.eq("id", surveyId));
        List<surveys> surveyList = criteria.list();
        if (surveyList.size() == 0) {
            return null;
        } else {
            return surveyList.get(0);
        }
    }

    @Override
    public void updateSurvey(surveys survey) throws Exception {
        sessionFactory.getCurrentSession().update(survey);
    }

    @Override
    public void saveChangeLogs(SurveyChangeLogs scl) throws Exception {
        sessionFactory.getCurrentSession().save(scl);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SurveyChangeLogs> getSurveyChangeLogs(Integer surveyId) throws Exception {
        String sql = "select surveyId, systemUserId, notes, scl.dateCreated, firstName as userFirstName, lastName as userLastName from "
                + " survey_changeLogs scl, users where users.id = scl.systemUserId and surveyId = :surveyId "
                + " order by scl.datecreated desc;";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql)
                .setResultTransformer(Transformers.aliasToBean(SurveyChangeLogs.class)).setParameter("surveyId", surveyId);
        List<SurveyChangeLogs> surveyChangeLogsList = query.list();
        return surveyChangeLogsList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AnswerTypes> getAnswerTypes() throws Exception {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AnswerTypes.class);
        criteria.addOrder(Order.desc("answerType"));
        return criteria.list();

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SurveyPages> getSurveyPages(Integer surveyId, boolean getQuestions) throws Exception {
        
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SurveyPages.class);
        criteria.add(Restrictions.eq("surveyId", surveyId));
        criteria.addOrder(Order.asc("pageNum"));

        List<SurveyPages> surveyPagesList = criteria.list();
        List<SurveyPages> surveyPagesListForReturn = surveyPagesList;

        if (getQuestions) {
            surveyPagesListForReturn = new ArrayList<SurveyPages>();
            //we populate the survey questions here 
            for (SurveyPages sp : surveyPagesList) {
                List<SurveyQuestions> sqs = getSurveyQuestions(sp.getId());
                sp.setSurveyQuestions(sqs);
                surveyPagesListForReturn.add(sp);
            }
        }

        return surveyPagesListForReturn;
    }
    
    /**
     * The 'getSurveyPagesByPageNum' function will return a list of pages for the passed in survey that 
     * have a page number equal or greater than where the new page will be inserted. This will be used 
     * to update current page numbers to get the new page in the correct spot.
     * 
     * @param surveyId  The id of the selected survey
     * @param nextPageNum   The new page number
     * @return
     * @throws Exception 
     */
    @Override
    public List<SurveyPages> getSurveyPagesByPageNum(Integer surveyId, Integer nextPageNum) throws Exception {
        
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SurveyPages.class);
        criteria.add(Restrictions.eq("surveyId", surveyId));
        criteria.add(Restrictions.ge("pageNum", nextPageNum));
        criteria.addOrder(Order.asc("pageNum"));
        
         List<SurveyPages> surveyPagesList = criteria.list();
         
         return surveyPagesList;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SurveyQuestions> getSurveyQuestions(Integer surveyPageId) throws Exception {
        
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SurveyQuestions.class);
        criteria.add(Restrictions.eq("surveyPageId", surveyPageId));
        criteria.add(Restrictions.eq("hide", false));
        criteria.addOrder(Order.asc("questionNum"));
        return criteria.list();
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<SurveyQuestions> getAllSurveyQuestions(Integer surveyId) throws Exception {
        
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SurveyQuestions.class);
        criteria.add(Restrictions.eq("surveyId", surveyId));
        criteria.add(Restrictions.eq("hide", false));
        criteria.addOrder(Order.asc("questionNum"));
        return criteria.list();
    }


    @Override
    public Integer createSurveyPage(SurveyPages surveyPage) throws Exception {
        Integer lastId = null;
        lastId = (Integer) sessionFactory.getCurrentSession().save(surveyPage);
        return lastId;
    }

    @Override
    public void updateSurveyPage(SurveyPages surveyPage) throws Exception {
        sessionFactory.getCurrentSession().update(surveyPage);
    }

    @Override
    @SuppressWarnings("unchecked")
    public SurveyPages getSurveyPageById(Integer pageId) throws Exception {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SurveyPages.class);
        criteria.add(Restrictions.eq("id", pageId));
        List<SurveyPages> surveyPages = criteria.list();
        return surveyPages.get(0);
    }

    @Override
    @SuppressWarnings("unchecked")
    public SurveyQuestions getSurveyQuestionById(Integer questionId) throws Exception {
        
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SurveyQuestions.class);
        criteria.add(Restrictions.eq("id", questionId));
        List<SurveyQuestions> question = criteria.list();
        return question.get(0);
    }

    @Override
    public Integer saveNewSurveyQuestion(SurveyQuestions surveyQuestion) throws Exception {
        Integer lastId = null;
        lastId = (Integer) sessionFactory.getCurrentSession().save(surveyQuestion);
        return lastId;
    }
    
    @Override
    public void saveSurveyQuestion(SurveyQuestions surveyQuestion) throws Exception {
        sessionFactory.getCurrentSession().update(surveyQuestion);
    }
    
    /**
     * The 'getQuestionForSelectedPage' function will return the list of questions for the passed in
     * page.
     * 
     * @param pageId        The id of the selected page
     * @param questionId    The id of the current question so we don't return that
     * @return
     * @throws Exception 
     */
    @Override
    public List getQuestionForSelectedPage(Integer pageId, Integer questionId) throws Exception {
        Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT id, question, questionNum FROM survey_questions where id <> :questionId and hide = false and surveyPageId = :pageId order by questionNum asc")
                .setParameter("questionId", questionId)
                .setParameter("pageId", pageId);

        return query.list();
    }
    
    /**
     * The 'getQuestionChoices' function will return the list of choices set for a question.
     * 
     * @param questionId    The id of the selected question
     * @return  This function will return a list of survey question choices
     * @throws Exception 
     */
    @Override
    public List<SurveyQuestionChoices> getQuestionChoices(Integer questionId) throws Exception {
        
        SurveyQuestions questionDetails = getSurveyQuestionById(questionId);
        
        Query query;
        
        if(questionDetails.isAlphabeticallySort()) {
            query = sessionFactory.getCurrentSession().createQuery("from SurveyQuestionChoices where questionId = :questionId order by choiceText asc");
        
        }
        else {
            query = sessionFactory.getCurrentSession().createQuery("from SurveyQuestionChoices where questionId = :questionId order by id asc");
        
        }
        
        query.setParameter("questionId", questionId);
        
        return query.list();
    }
    
    /**
     * The 'removeQuestionChoices' function will remove the choices associated to the passed in question.
     * 
     * @param questionId The id of the selected question.
     * @throws Exception 
     */
    @Override
    public void removeQuestionChoices(Integer questionId) throws Exception {
        Query deleteQuestionChoices = sessionFactory.getCurrentSession().createQuery("delete from SurveyQuestionChoices where questionId = :questionId");
        deleteQuestionChoices.setParameter("questionId", questionId);
        deleteQuestionChoices.executeUpdate();
    }
    
    /**
     * The 'saveQuestionChoice' function will save the question choices.
     * 
     * @param questionChoice The object containing the question choice details
     * @throws Exception 
     */
    @Override
    public void saveQuestionChoice(SurveyQuestionChoices questionChoice) throws Exception {
        sessionFactory.getCurrentSession().save(questionChoice);
    }
}
