/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.dao.impl;

import com.bowlink.rr.dao.surveyDAO;
import com.bowlink.rr.model.AnswerTypes;
import com.bowlink.rr.model.EngagementSurveys;
import com.bowlink.rr.model.SurveyAnswers;
import com.bowlink.rr.model.SurveyChangeLogs;
import com.bowlink.rr.model.SurveyPages;
import com.bowlink.rr.model.SurveyQuestions;
import com.bowlink.rr.model.surveys;
import com.bowlink.rr.model.userProgramModules;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
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
        /** now we loop and calculate times taken **/
        for (surveys survey : surveyList) {
        	survey.setTimesTaken(surveyTakenTimes (survey.getId()));
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
    
	public Integer surveyTakenTimes (Integer surveyId) throws Exception {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(EngagementSurveys.class);
        criteria.add(Restrictions.eq("surveyId", surveyId));
        return criteria.list().size();
	}
	
    @SuppressWarnings("unchecked")
	public surveys getSurveyById(Integer surveyId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(surveys.class);
        criteria.add(Restrictions.eq("id", surveyId));
        List <surveys> surveyList = criteria.list();
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
    public List <SurveyChangeLogs> getSurveyChangeLogs (Integer surveyId) throws Exception {
    	String sql ="select surveyId, systemUserId, notes, scl.dateCreated, firstName as userFirstName, lastName as userLastName from "
    			+ " survey_changeLogs scl, users where users.id = scl.systemUserId and surveyId = :surveyId "
    			+ " order by scl.datecreated desc;";
    	 Query query = sessionFactory.getCurrentSession().createSQLQuery(sql)
                .setResultTransformer(Transformers.aliasToBean(SurveyChangeLogs.class)).setParameter("surveyId", surveyId);
		List <SurveyChangeLogs> surveyChangeLogsList = query.list();
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
	public List<SurveyPages> getSurveyPages(Integer surveyId,
			boolean getQuestions) throws Exception {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SurveyPages.class);
        criteria.add(Restrictions.eq("surveyId", surveyId));
        criteria.addOrder(Order.asc("pageNum"));
        
        List <SurveyPages> surveyPagesList = criteria.list();
        List <SurveyPages> surveyPagesListForReturn = surveyPagesList;
        
        if (getQuestions) {
        	surveyPagesListForReturn = new ArrayList<SurveyPages>();
        	//we populate the survey questions here 
        	for (SurveyPages sp : surveyPagesList) {
        		List <SurveyQuestions> sqs = getSurveyQuestions(sp.getId());
            	sp.setSurveyQuestions(sqs);
            	surveyPagesListForReturn.add(sp);
            }
        }
        
        return surveyPagesListForReturn;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<SurveyQuestions> getSurveyQuestions(Integer surveyPageId)
			throws Exception {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SurveyQuestions.class);
        criteria.add(Restrictions.eq("surveyPageId", surveyPageId));
        criteria.addOrder(Order.asc("questionNum"));      
        return criteria.list();
	}
	
	/** survey answers will be ordered by Id.  When an answer is changed we will delete old entry
	 * and insert a new one 
	 * **/
	@Override
	@SuppressWarnings("unchecked")
	public List<SurveyAnswers> getSurveyAnswers(Integer questionId)
			throws Exception {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SurveyAnswers.class);
        criteria.add(Restrictions.eq("questionId", questionId));
        criteria.addOrder(Order.asc("answerOrder"));      
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
	
}



