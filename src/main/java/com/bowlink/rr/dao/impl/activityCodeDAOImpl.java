/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bowlink.rr.dao.impl;

import com.bowlink.rr.dao.activityCodeDAO;
import com.bowlink.rr.model.activityCodes;
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
     * The 'getActivityCodes' function will return a list of all activity codes. If a programId is
     * passed in then it will return all the activity codes not already associated to the program.
     *
     * @param programId The id of the selected program.
     */
    @Override
    public List<activityCodes> getActivityCodes(Integer programId) throws Exception {
        
        if(programId > 0) {
            
            Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT * FROM activityCodes where id not in (select codeId from programActivityCodes where programId = :programId)")
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
    
}
