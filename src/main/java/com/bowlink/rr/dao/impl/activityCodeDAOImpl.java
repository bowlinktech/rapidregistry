/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bowlink.rr.dao.impl;

import com.bowlink.rr.dao.activityCodeDAO;
import com.bowlink.rr.model.activityCodes;
import com.bowlink.rr.model.programActivityCodes;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author chadmccue
 */
@Repository
public class activityCodeDAOImpl implements activityCodeDAO {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    /**
     * The 'createActivityCode' function will submit the new activity code. 
     *
     * @param   codeDetails The object containing the activity code details
     * @throws Exception 
     */
    @Override
    public void createActivityCode(activityCodes codeDetails) throws Exception {
        sessionFactory.getCurrentSession().save(codeDetails);
    }
    
    /**
     * The 'createActivityCode' function will submit the activity code changes.
     * 
     * @param   codeDetails The object containing the activity code details
     * 
     * @param codeDetails
     * @throws Exception 
     */
    @Override
    public void updateActivityCode(activityCodes codeDetails) throws Exception {
        sessionFactory.getCurrentSession().update(codeDetails);
    }

    /**
     * The 'getActivityCodes' function will return a list of all activity codes. If a programId is
     * passed in then it will return all the activity codes not already associated to the program.
     *
     * @param programId The id of the selected program.
     */
    @Override
    public List<activityCodes> getActivityCodes(Integer programId) throws Exception {
        
        if(programId > 0) {
            
            Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT * FROM lu_activityCodes where id not in (select codeId from program_activityCodes where programId = :programId)")
                .setParameter("programId", programId);
            
            query.setResultTransformer(Transformers.aliasToBean(activityCodes.class));
            
            return query.list();
            
            
        }
        else {
            Query query = sessionFactory.getCurrentSession().createQuery("from activityCodes order by code asc");

            return query.list();
        }

    }
    
    /**
     * The 'getActivityCodeById' function will return the details of the activity code for the passed in
     * code id.
     * 
     * @param codeId    The id of the passed in code
     * @return  This function will return a activityCodes object
     * @throws Exception 
     */
    @Override
    public activityCodes getActivityCodeById(Integer codeId) throws Exception {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(activityCodes.class);
        criteria.add(Restrictions.eq("id", codeId));

        activityCodes codeDetails = (activityCodes) criteria.uniqueResult(); 
        
        return codeDetails;
    }
    
    /**
     * The 'getAllProgramActivityCodes' function will return all the activity codes associated for the
     * passed in program Id
     * 
     * @param programId The id of the program to search on
     * @return  This function will return either true or false.
     * @throws Exception 
     */
    @Override
    public List<activityCodes> getActivityCodesByProgram(Integer programId) throws Exception {
        
         Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT * FROM lu_activityCodes where id in (select codeId from program_activityCodes where programId = :programId)")
            .setParameter("programId", programId);

        query.setResultTransformer(Transformers.aliasToBean(activityCodes.class));

        return query.list();
        
    }
    
    /**
     * The 'getActivityCodesByProgram' function will check to see if the passed in activity code is associated with the
     * passed in program Id
     * 
     * @param codeId    The id of the activity code passed in.
     * @param programId The id of the program to search on
     * @return  This function will return either true or false.
     * @throws Exception 
     */
    @Override
    public boolean getActivityCodesByProgram(Integer programId, Integer codeId) throws Exception {
        Query query = sessionFactory.getCurrentSession().createQuery("from programActivityCodes where programId = :programId and codeId = :codeId");
        query.setParameter("programId", programId);
        query.setParameter("codeId", codeId);
        
        if(query.list().isEmpty()) {
            return false;
        }
        else {
            return true;
        }
        
    }
    
    /**
     * The 'saveProgramActivityCode' function will save the associated activity codes to the passed in 
     * program.
     * 
     * @param   newCodeAssoc    The new activity code and program object
     */
    @Override
    public void saveProgramActivityCode(programActivityCodes newCodeAssoc) throws Exception {
        sessionFactory.getCurrentSession().save(newCodeAssoc);
    }
    
    /**
     * The 'removeProgramActivityCodes' function will remove the associated activity codes with the passed in
     * program.
     * 
     * @param programId     The id of the passed in program
     */
    @Override
    public void removeProgramActivityCodes(Integer programId) throws Exception {
        Query deleteActivityCodes = sessionFactory.getCurrentSession().createQuery("delete from programActivityCodes where programId = :programId");
        deleteActivityCodes.setParameter("programId", programId);
        deleteActivityCodes.executeUpdate();
    }
    
}
