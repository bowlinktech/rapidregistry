/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.dao.impl;

import com.bowlink.rr.dao.surveyDAO;
import com.bowlink.rr.model.AnswerTypes;
import com.bowlink.rr.model.SurveyCategories;
import com.bowlink.rr.model.SurveyCategoryAssociation;
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
import org.springframework.transaction.annotation.Transactional;

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
        criteria.addOrder(Order.asc("title"));
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
        criteria.addOrder(Order.asc("title"));
        List<surveys> surveyList = criteria.list();
       
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
        criteria.add(Restrictions.eq("deleted", false));
        criteria.addOrder(Order.asc("questionNum"));
        return criteria.list();
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<SurveyQuestions> getAllSurveyQuestions(Integer surveyId) throws Exception {
        
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SurveyQuestions.class);
        criteria.add(Restrictions.eq("surveyId", surveyId));
        criteria.add(Restrictions.eq("deleted", false));
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
	
	if(question != null) {
	    if(!question.isEmpty()) {
		return question.get(0);
	    }
	    else {
		return null;
	    }
	}
	else {
	    return null;
	}
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
        Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT id, question, questionNum FROM survey_questions where id <> :questionId and deleted = false and surveyPageId = :pageId order by questionNum asc")
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
    
    /**
     * The 'deleteSurveyPage' function will remove the passed in page. 
     * 
     * @param pageId    The id of the selected page
     * @throws Exception 
     */
    @Override
    public void deleteSurveyPage(Integer pageId) throws Exception {
        Query deleteSurveyPage = sessionFactory.getCurrentSession().createQuery("delete from SurveyPages where id = :pageId");
        deleteSurveyPage.setParameter("pageId", pageId);
        deleteSurveyPage.executeUpdate();
    }
    
    /**
     * The 'removeDateRows' function will remove the rows associated to the passed in question.
     * 
     * @param questionId The id of the selected question.
     * @throws Exception 
     */
    @Override
    public void removeDateRows(Integer questionId) throws Exception {
        Query deleteQuestionChoices = sessionFactory.getCurrentSession().createQuery("delete from SurveyDateQuestionRows where questionId = :questionId");
        deleteQuestionChoices.setParameter("questionId", questionId);
        deleteQuestionChoices.executeUpdate();
    }
    
    @SuppressWarnings("unchecked")
    public List<surveys> getProgramSurveysByTag(surveys survey) throws Exception {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(surveys.class);
        criteria.add(Restrictions.eq("programId", survey.getProgramId()));
        criteria.add(Restrictions.eq("surveyTag", survey.getSurveyTag()));
        return criteria.list();
    }
    
    /**
     * The 'checkForDuplicateQuestionTag' function will check for duplicate question tags for a survey.
     * @param surveyId
     * @param questionTag
     * @return
     * @throws Exception 
     */
    public boolean checkForDuplicateQuestionTag(Integer surveyId, Integer questionId, String questionTag) throws Exception {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SurveyQuestions.class);
        criteria.add(Restrictions.eq("surveyId", surveyId));
        criteria.add(Restrictions.eq("questionTag", questionTag));
	criteria.add(Restrictions.ne("deleted", true));
        criteria.add(Restrictions.ne("id", questionId));
	
        List<SurveyQuestions> SurveyQuestions = criteria.list();
	
        if(SurveyQuestions != null && SurveyQuestions.size() > 0) {
            return false;
        }
        else {
            return true;
        }
    }
    
    /**
     * The 'getProgramSurveyCategories' method will return the list of available survey categories for the passed in programId.
     * 
     * @param programId
     * @return
     * @throws Exception 
     */
    @Override
    public List<SurveyCategories> getProgramSurveyCategories(Integer programId) throws Exception {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SurveyCategories.class);
        criteria.add(Restrictions.eq("programId", programId));
	
        List<SurveyCategories> SurveyCategories = criteria.list();
	
        return SurveyCategories;
    }
    
    /**
     * The 'getProgramSurveyCategory' method will return the survey/category association for the passed in surveyId and programId.
     * 
     * @param programId
     * @param surveyId
     * @return
     * @throws Exception 
     */
    @Override
    public SurveyCategoryAssociation getProgramSurveyCategory(Integer programId, Integer surveyId) throws Exception {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SurveyCategoryAssociation.class);
        criteria.add(Restrictions.eq("programId", programId));
	criteria.add(Restrictions.eq("surveyId", surveyId));
	
        List<SurveyCategoryAssociation> SurveyCategoryAssociations = criteria.list();
	
	if(SurveyCategoryAssociations.size() > 1) {
	    return (SurveyCategoryAssociation) SurveyCategoryAssociations.get(0);
	}
	else {
	    return (SurveyCategoryAssociation) criteria.uniqueResult();
	}
    }
    
    /**
     * The 'saveProgramSurveyCategoryAssociation' method will save the connection between a survey and a survey category.
     * 
     * @param surveycategoryassociation
     * @throws Exception 
     */
    @Override
    public void saveProgramSurveyCategoryAssociation(SurveyCategoryAssociation surveycategoryassociation) throws Exception {
        sessionFactory.getCurrentSession().saveOrUpdate(surveycategoryassociation);
    }
    
    /**
     * The 'removeProgramSurveyCategory' method will remove the connection between a survey and a survey category.
     * @param programId
     * @param surveyId
     * @throws Exception 
     */
    @Override
    public void removeProgramSurveyCategory(Integer programId, Integer surveyId) throws Exception {
        Query deleteSurveyCategoryAssociation = sessionFactory.getCurrentSession().createQuery("delete from SurveyCategoryAssociation where programId = :programId and surveyId = :surveyId");
        deleteSurveyCategoryAssociation.setParameter("programId", programId);
	deleteSurveyCategoryAssociation.setParameter("surveyId", surveyId);
        deleteSurveyCategoryAssociation.executeUpdate();
    }
    
    /**
     * 
     * @param surveyCategoryId
     * @return 
     */
    @Override
    public SurveyCategories getProgramSurveyCategoryById(Integer surveyCategoryId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SurveyCategories.class);
        criteria.add(Restrictions.eq("id", surveyCategoryId));
	
        List<SurveyCategories> surveycategoryList = criteria.list();
	
        if (surveycategoryList.size() == 0) {
            return null;
        } else {
            return surveycategoryList.get(0);
        }
    }
    
    /**
     * The 'saveProgramSurveyCategory' method will save the survey category details.
     * 
     * @param surveyCategoryDetails
     * @throws Exception 
     */
    @Override
    public void saveProgramSurveyCategory(SurveyCategories surveyCategoryDetails) throws Exception {
        sessionFactory.getCurrentSession().saveOrUpdate(surveyCategoryDetails);
    }
    
    /**
     * The 'deleteProgramSurveyCategoryAssociation' method will remove all connections for the passed in programId and selected survey category Id
     * @param programId
     * @param surveyCategoryId 
     */
    @Override
    public void deleteProgramSurveyCategoryAssociation(Integer programId, Integer surveyCategoryId) {
	Query deleteSurveyCategoryAssociation = sessionFactory.getCurrentSession().createQuery("delete from SurveyCategoryAssociation where programId = :programId and categoryId = :surveyCategoryId");
        deleteSurveyCategoryAssociation.setParameter("programId", programId);
	deleteSurveyCategoryAssociation.setParameter("surveyCategoryId", surveyCategoryId);
        deleteSurveyCategoryAssociation.executeUpdate();
    }
	
    /**
     * The 'deleteProgramSurveyCategory' method will remove survey category
     * @param programId
     * @param surveyCategoryId 
     */
    @Override
    public void deleteProgramSurveyCategory(Integer programId, Integer surveyCategoryId) {
	Query deleteSurveyCategory = sessionFactory.getCurrentSession().createQuery("delete from SurveyCategories where id = :surveyCategoryId and programId = :programId");
        deleteSurveyCategory.setParameter("programId", programId);
	deleteSurveyCategory.setParameter("surveyCategoryId", surveyCategoryId);
        deleteSurveyCategory.executeUpdate();
    }
    
    /**
     * The 'getQuestionsForSelectedSurvey' function will return the list of questions for the passed in
     * survey.
     * 
     * @param surveyId        The id of the selected survey
     * @return
     * @throws Exception 
     */
    @Override
    public List getQuestionsForSelectedSurvey(Integer surveyId) throws Exception {
        Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT id, question, questionNum FROM survey_questions where surveyId = :surveyId and deleted = false and answerTypeId in (1,2,3) order by questionNum asc")
	    .setParameter("surveyId", surveyId);

        return query.list();
    }
}
