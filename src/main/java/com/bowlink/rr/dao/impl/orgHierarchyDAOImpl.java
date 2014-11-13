/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.dao.impl;

import com.bowlink.rr.dao.orgHierarchyDAO;
import com.bowlink.rr.model.programOrgHierarchy;
import com.bowlink.rr.model.userProgramHierarchy;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author chadmccue
 */
@Repository
public class orgHierarchyDAOImpl implements orgHierarchyDAO {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    /**
     * The 'getProgramOrgHierarchy' function will return a list of organization hierarchy entries for the selected
     * program.
     * 
     * @param programId The id of the selected program.
     * @return This function will return a list of organization hierarchy objects.
     * @throws Exception 
     */
    @Override
    public List<programOrgHierarchy> getProgramOrgHierarchy(Integer programId) throws Exception {
        Query query = sessionFactory.getCurrentSession().createQuery("from programOrgHierarchy where programId = :programId order by dspPos asc");
        query.setParameter("programId", programId);

        List<programOrgHierarchy> orgList = query.list();
        return orgList;
    }
    
    /**
     * The 'getOrgHierarchyById' function will return the details of the selected organization hierarchy.
     * 
     * @param id The id of the selected organization hierarchy.
     * @return  This function will return a single organization hierarchy object.
     * @throws Exception 
     */
    @Override
    public programOrgHierarchy getOrgHierarchyById(Integer id) throws Exception {
        return (programOrgHierarchy) sessionFactory.getCurrentSession().get(programOrgHierarchy.class, id);
    }
    
    /**
     * The 'saveOrgHierarchy' function will save/update the passed in organization hierarchy object.
     * 
     * @param hierarchyDetails  The programOrgHierarchy object containing the form fields.
     * @throws Exception 
     */
    @Override
    public void saveOrgHierarchy(programOrgHierarchy hierarchyDetails) throws Exception {
        sessionFactory.getCurrentSession().saveOrUpdate(hierarchyDetails);
    }
    
    /**
     * The 'getProgramOrgHierarchyBydspPos' function will return the organization hierarchy that currently
     * has the dspPos set to the dspPos passed in.
     * 
     * @param dspPos    The display position that we need to find
     * @param programId The id of the program the organization hierarchy being updated belongs to.
     * @return 
     */
    @Override
    public programOrgHierarchy getProgramOrgHierarchyBydspPos(Integer dspPos, Integer programId) {
        Query query = sessionFactory.getCurrentSession().createQuery("from programOrgHierarchy where programId = :programId and dspPos = :dspPos");
        query.setParameter("programId", programId);
        query.setParameter("dspPos", dspPos);
        
        return (programOrgHierarchy) query.uniqueResult();
    }
    
    /**
     * The 'getProgramOrgHierarchyItems' function will return the organization hierarchy entries for the passed in programId, level and assocId.
     * 
     * @param programId The id of the selected program
     * @param level     The hierarchy level to return.
     * @param assocId   0 or the selected hierarchy to find associated sub levels.
     * @return
     * @throws Exception 
     */
    @Override
    public List getProgramOrgHierarchyItems(Integer programId, Integer level, Integer assocId) throws Exception {
        
        String sqlQuery = "SELECT a.id, a.name from programOrgHierarchy_details a inner join programOrgHierarchy b on a.programHierarchyId = b.id";
        
        if(assocId > 0) {
            sqlQuery += " inner join programOrgHierarchy_assoc c on c.programHierarchyId = a.id and c.associatedWith = " + assocId;
        }
        
        sqlQuery += " where b.programId = :programId and b.dspPos = :level";
        
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery)
                .setParameter("programId", programId)
                .setParameter("level", level);

        return query.list();
    }
    
    /**
     * The 'saveUserProgramHierarchy' function will save the authorized program organizational 
     * hierarchies for the user.
     * 
     * @param hierarchy The userProgramHierarchy object to save
     * @throws Exception 
     */
    @Override
    public void saveUserProgramHierarchy(userProgramHierarchy hierarchy) throws Exception {
        sessionFactory.getCurrentSession().saveOrUpdate(hierarchy);
    }
    
    /**
     * The 'getUserProgramHierarchy' function will return a list of authorized hierarchy for the selected user and 
     * program.
     * 
     * @param programId The id of the selected program
     * @param userId    The id of the selected user
     * @return  This function will return a list of userProgramHierarchy objects
     * @throws Exception 
     */
    @Override
    public List<userProgramHierarchy> getUserProgramHierarchy(Integer programId, Integer userId) throws Exception {
        
        String sqlQuery = "select a.id, a.systemUserId, a.programId, a.programHierarchyId, a.orgHierarchyDetailId, a.dateCreated, b.name as hierarchyName from user_authorizedOrgHierarchy a inner join programOrgHierarchy_details b on b.id = a.orgHierarchyDetailId where a.programId = " + programId + " and a.systemUserId = " + userId;
        
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery) 
        .setResultTransformer(Transformers.aliasToBean(userProgramHierarchy.class)
        );
        
        
        return query.list();
    }
}
