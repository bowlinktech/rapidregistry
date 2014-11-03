/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.dao.impl;

import com.bowlink.rr.dao.surveyDAO;
import com.bowlink.rr.model.program;
import com.bowlink.rr.model.surveys;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
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
    public List<surveys> getProgramSurveys(Integer programId) throws Exception {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(surveys.class);
        criteria.add(Restrictions.eq("programId", programId));
        
        return criteria.list();
    }
    
}
