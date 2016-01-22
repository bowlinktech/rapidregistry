/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.dao.impl;

import com.bowlink.rr.dao.orgHierarchyDAO;
import com.bowlink.rr.model.programOrgHierarchy;
import com.bowlink.rr.model.programOrgHierarchyAssoc;
import com.bowlink.rr.model.programOrgHierarchyDetails;
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
    
    /**
     * The 'getUserAssociatedEntities' function will return a list of authorized hierarchy for the selected user and 
     * program.
     * 
     * @param programId The id of the selected program
     * @param userId    The id of the selected user
     * @return  This function will return a list of userProgramHierarchy objects
     * @throws Exception 
     */
    @Override
    public List<userProgramHierarchy> getUserAssociatedEntities(Integer programId, Integer userId, Integer entityId) throws Exception {
        
        Query query = sessionFactory.getCurrentSession().createQuery("from userProgramHierarchy where programId = :programId and systemUserId = :userId and programHierarchyId = :entityId");
        query.setParameter("programId", programId);
        query.setParameter("userId", userId);
        query.setParameter("entityId", entityId);

        List<userProgramHierarchy> itemList = query.list();
        return itemList;
        
    }
    
    /**
     * The 'removeUserProgramHierarchy' function will remove the selected hierarchy for the user and program.
     * 
     * @param Id
     * @throws Exception 
     */
    @Override
    public void removeUserProgramHierarchy(Integer entityId, Integer userId) throws Exception {
       
        Query removeProgram = sessionFactory.getCurrentSession().createQuery("delete from userProgramHierarchy where systemUserId = :userId and programHierarchyId = :entityId");
        removeProgram.setParameter("entityId", entityId);
        removeProgram.setParameter("userId", userId);
        removeProgram.executeUpdate();
        
    }
    
    /**
     * The 'getProgramHierarchyItems' function will return a list of items associated to the selected hierarchy.
     * 
     * @param hierarchyId The id of the selected program hierarchy.
     * @return This function will return a list of hierarchy items.
     * @throws Exception 
     */
    public List<programOrgHierarchyDetails> getProgramHierarchyItems(Integer hierarchyId) throws Exception {
        Query query = sessionFactory.getCurrentSession().createQuery("from programOrgHierarchyDetails where programHierarchyId = :hierarchyId order by name, id asc");
        query.setParameter("hierarchyId", hierarchyId);

        List<programOrgHierarchyDetails> itemList = query.list();
        return itemList;
    }
    
    @Override
    public List<programOrgHierarchyDetails> getProgramHierarchyItemsByAssoc(Integer hierarchyId, Integer assocId) throws Exception {
        
        String sqlQuery = "select * from programOrgHierarchy_Details where programHierarchyId = " + hierarchyId + " and id in (select programHierarchyId from programOrgHierarchy_assoc where associatedWith = "+assocId+") order by id asc";
        
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery) 
        .setResultTransformer(Transformers.aliasToBean(programOrgHierarchyDetails.class)
        );
        
        List<programOrgHierarchyDetails> itemList = query.list();
        return itemList;
    }
    
    /**
     * The 'getProgramHierarchyItemDetails' function will return a list of items associated to the selected hierarchy.
     * 
     * @param itemId The id of the selected program hierarchy.
     * @return This function will return a list of hierarchy items.
     * @throws Exception 
     */
    @Override
    public programOrgHierarchyDetails getProgramHierarchyItemDetails(Integer itemId) throws Exception {
        Query query = sessionFactory.getCurrentSession().createQuery("from programOrgHierarchyDetails where id = :itemId");
        query.setParameter("itemId", itemId);

        return (programOrgHierarchyDetails) query.uniqueResult();
    }
    
    /**
     * The 'saveOrgHierarchyItem' function will save the new entity item
     * 
     * @param entityItemDetails The details of the entity item
     * @throws Exception 
     */
    @Override
    public void saveOrgHierarchyItem(programOrgHierarchyDetails entityItemDetails) throws Exception {
    	if (entityItemDetails.getId() == 0) {
    		sessionFactory.getCurrentSession().saveOrUpdate(entityItemDetails);
    	} else {
    		sessionFactory.getCurrentSession().update(entityItemDetails);
    	}
    }
    /**
     * The 'saveOrgHierarchyAssociation' function will save the new association.
     * @param newAssoc
     * @throws Exception 
     */
    @Override
    public void saveOrgHierarchyAssociation(programOrgHierarchyAssoc newAssoc) throws Exception {
        sessionFactory.getCurrentSession().save(newAssoc);
    }
    
    /**
     * The 'removeOrgHierarchyAssocation' will remove the association for the passed in item and 
     * entityId.
     * @param itemId
     * @throws Exception 
     */
    @Override
    public void removeOrgHierarchyAssociation(Integer itemId) throws Exception {
        Query removeAssociation = sessionFactory.getCurrentSession().createQuery("delete from programOrgHierarchyAssoc where programHierarchyId = :itemId");
        removeAssociation.setParameter("itemId", itemId);
        removeAssociation.executeUpdate();
    }
    
    /**
     * The 'removeOrgHierarchyAssocation' will remove the association for the passed in item and 
     * entityId.
     * @param itemId
     * @param entityId
     * @throws Exception 
     */
    @Override
    public void removeOrgHierarchyAssociation(Integer itemId, Integer entityId) throws Exception {
        Query removeAssociation = sessionFactory.getCurrentSession().createQuery("delete from programOrgHierarchyAssoc where programHierarchyId = :itemId and associatedWith = :associatedWith");
        removeAssociation.setParameter("itemId", itemId);
        removeAssociation.setParameter("associatedWith", entityId);
        removeAssociation.executeUpdate();
    }
    
    /**
     * The 'getAssociatedItems' function will return the associated entities for the selected entity.
     * 
     * @param itemId The selected entity.
     * @return
     * @throws Exception 
     */
    @Override
    public List<programOrgHierarchyAssoc> getAssociatedItems(Integer itemId) throws Exception {
        Query query = sessionFactory.getCurrentSession().createQuery("from programOrgHierarchyAssoc where programHierarchyId = :itemId");
        query.setParameter("itemId", itemId);

        return query.list();
    }
    
    /**
     * The 'getProgramHierarchyItemDetailsByName' function will return a list of items associated to the selected hierarchy.
     * 
     * @param name The id of the selected program hierarchy.
     * @return This function will return a list of hierarchy items.
     * @throws Exception 
     */
    @Override
    public programOrgHierarchyDetails getProgramHierarchyItemDetailsByName(programOrgHierarchyDetails newEntity) throws Exception {
        Query query = sessionFactory.getCurrentSession().createQuery(
                "from programOrgHierarchyDetails where name = :name and "
                + " programHierarchyId = :programHierarchyId");
        query.setParameter("name", newEntity.getName());
        query.setParameter("programHierarchyId", newEntity.getProgramHierarchyId());
         
        if (query.list().size() > 0) {
            return (programOrgHierarchyDetails) query.list().get(0);
        } else  {
           return new programOrgHierarchyDetails();
        }
    }
}
