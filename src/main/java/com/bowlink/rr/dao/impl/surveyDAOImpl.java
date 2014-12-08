/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.dao.impl;

import com.bowlink.rr.dao.surveyDAO;
import com.bowlink.rr.model.EngagementSurveys;
import com.bowlink.rr.model.SurveyChangeLogs;
import com.bowlink.rr.model.surveys;
import com.bowlink.rr.model.userProgramModules;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
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
	public surveys getSurveysById(Integer surveyId) throws Exception {
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
	
}
