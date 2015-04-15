/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.dao.impl;

import com.bowlink.rr.dao.masterClientIndexDAO;
import com.bowlink.rr.model.algorithmCategories;
import com.bowlink.rr.model.algorithmMatchingActions;
import com.bowlink.rr.model.programUploadTypeAlgorithm;
import com.bowlink.rr.model.programUploadTypeAlgorithmFields;

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
public class masterClientIndexDAOImpl implements masterClientIndexDAO {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    /**
     * The 'getProgramUploadMCIalgorithms' function will return a list of the programs in the system.
     * 
     * @return The function will return a list of algorithms for a particular section of a program
     */
    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public List<programUploadTypeAlgorithm> getProgramUploadTypeAlgorithm(Integer programUploadTypeId) throws Exception {
        Query query = sessionFactory.getCurrentSession().createQuery(" from programUploadTypeAlgorithm where programUploadTypeId = :programUploadTypeId "
        		+ " order by categoryId, processOrder asc");
        query.setParameter("programUploadTypeId", programUploadTypeId);
		List<programUploadTypeAlgorithm> MCIList = query.list();
        return MCIList;
    }
    
    /**
     * The 'getProgramMCIFields' function will get a list of fields associated to each MCI Algorithm.
     * 
     * @param algorithmId The id of the MCI Algorithm
     * @return  This function will return a list of MCI Fields
     * @throws Exception 
     */
    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public List<programUploadTypeAlgorithmFields> getMCIAlgorithmFields(Integer algorithmId) throws Exception {
        Query query = sessionFactory.getCurrentSession().createQuery("from programUploadTypeAlgorithmFields where algorithmId = :algorithmId order by id asc");
        query.setParameter("algorithmId", algorithmId);

        List<programUploadTypeAlgorithmFields> fieldList = query.list();
        return fieldList;
    }
    
    /**
     * The 'createMCIAlgorithm' function will create the new MCI Algorithm
     * 
     * @param newMCIAlgorithm   The object that holds the new algorithm
     * 
     * @return This function will return the id from the new algorithm
     */
    @Override
    public Integer createMCIAlgorithm(programUploadTypeAlgorithm newMCIAlgorithm) throws Exception {
        Integer lastId = null;

        lastId = (Integer) sessionFactory.getCurrentSession().save(newMCIAlgorithm);

        return lastId;
    }
    
    /**
     * The 'updateMCIAlgorithm' function will update the selected MCI Algorithm
     * 
     * @param MCIAlgorithm   The object that holds the selected algorithm
     * 
     * @return This function will not return anything
     */
    @Override
    public void updateMCIAlgorithm(programUploadTypeAlgorithm MCIAlgorithm) throws Exception {
        sessionFactory.getCurrentSession().update(MCIAlgorithm);
    }
    
    
    /**
     * The 'createMCIAlgorithmFields' function will save the fields associated to the MCI 
     * algorithm.
     * 
     * 
     */
    @Override
    public void createMCIAlgorithmFields(programUploadTypeAlgorithmFields newField) throws Exception {
        sessionFactory.getCurrentSession().save(newField);
    }
    
    /**
     * The 'getMCIAlgorithm' function will return the details of the passed in MCI algorithm.
     * 
     * @param algorithmId The id of the selected MCI algorithm
     * @return
     * @throws Exception 
     */
    @Override
    @Transactional
    public programUploadTypeAlgorithm getMCIAlgorithm(Integer algorithmid) throws Exception {
        Query query = sessionFactory.getCurrentSession().createQuery(" from programUploadTypeAlgorithm where id = :algorithmid order by processOrder");
        query.setParameter("algorithmid", algorithmid);

        return (programUploadTypeAlgorithm) query.uniqueResult();
    }

    /**
     * The 'removeAlgorithmField' function will remove the selected field from the MCI algorithm
     * 
     * @param algorithmFieldId  The id of the selected MCI Algorithm field
     */
    @Override
    public void removeAlgorithmField(Integer algorithmFieldId) throws Exception {
        Query deleteAlgorithmField = sessionFactory.getCurrentSession().createQuery("delete from programUploadTypeAlgorithmFields where id = :algorithmFieldId");
        deleteAlgorithmField.setParameter("algorithmFieldId", algorithmFieldId);
        deleteAlgorithmField.executeUpdate();
    }
    
    /**
     * The 'removeAlgorithm' function will remove the selected field from the MCI algorithm
     * 
     * @param algorithmId  The id of the selected MCI Algorithm field
     */
    @Override
    public void removeAlgorithm(Integer algorithmId) throws Exception {
        Query deleteAlgorithmField = sessionFactory.getCurrentSession().createQuery("delete from programUploadTypeAlgorithmFields where algorithmid = :algorithmId");
        deleteAlgorithmField.setParameter("algorithmId", algorithmId);
        deleteAlgorithmField.executeUpdate();
        
        Query deleteAlgorithm = sessionFactory.getCurrentSession().createQuery("delete from programUploadTypeAlgorithm where id = :algorithmId");
        deleteAlgorithm.setParameter("algorithmId", algorithmId);
        deleteAlgorithm.executeUpdate();
    }

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public Integer getMaxProcessOrder(Integer categoryId, Integer importTypeId) throws Exception {
		Query query = sessionFactory.getCurrentSession().createQuery("from programUploadTypeAlgorithm "
				+ "where programUploadTypeId = :programUploadTypeId and categoryId = :categoryId order by processOrder desc");
        query.setParameter("programUploadTypeId", importTypeId);
        query.setParameter("categoryId", categoryId);
        
        List<programUploadTypeAlgorithm> algorithmList = query.list();
        if (algorithmList.size() != 0) {
        	return algorithmList.get(0).getProcessOrder();
        } else {
        	return 0;
        }
       
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public programUploadTypeAlgorithm getMCIAlgorithmByProcessOrder(
			Integer processOrder, Integer programUploadTypeId, Integer categoryId) throws Exception {
		Query query = sessionFactory.getCurrentSession().createQuery(" from programUploadTypeAlgorithm "
				+ " where processOrder = :processOrder and programUploadTypeId = :programUploadTypeId "
				+ " and categoryId = :categoryId "
				+ " order by processOrder desc");
        query.setParameter("programUploadTypeId", programUploadTypeId);
        query.setParameter("processOrder", processOrder);
        query.setParameter("categoryId", categoryId);
        
        List<programUploadTypeAlgorithm> algorithmList = query.list();
        if (algorithmList.size() != 0) {
        	return algorithmList.get(0);
        } else {
        	return null;
        }
	}
	
	/**
     * This method returns a list of available action that can be assigned to a patient match or visit match
     */
    
	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<algorithmMatchingActions> getAlgorithmMatchingActions(Boolean status) throws Exception {
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(algorithmMatchingActions.class);

        if (status != null) {
            criteria.add(Restrictions.eq("status", status));
        } 
        
		List<algorithmMatchingActions> actionList = criteria.list();      
        
		return actionList;
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<algorithmCategories> getAlgorithmCategories(Boolean status)
			throws Exception {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(algorithmCategories.class);
        
		if (status != null) {
            criteria.add(Restrictions.eq("status", status));
        } 
        
		List<algorithmCategories> categoryList = criteria.list();      
        
		return categoryList;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<algorithmCategories> getCategoriesForUploadType(
			Integer importTypeId) throws Exception {
		
		Query query;
        query = sessionFactory.getCurrentSession().createSQLQuery("SELECT distinct cat.id, displayText "
        		+ " FROM put_algorithms put, lu_algorithmCategories cat where cat.id = put.categoryId "
        		+ " and programuploadtypeId = :importTypeId")
                .setParameter("importTypeId", importTypeId);
            
            query.setResultTransformer(Transformers.aliasToBean(algorithmCategories.class));
            
            return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<programUploadTypeAlgorithm> getPUTAlgorithmByCategory(
			Integer catId, Integer importTypeId) throws Exception {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(programUploadTypeAlgorithm.class);
        criteria.add(Restrictions.eq("categoryId", catId));
        criteria.add(Restrictions.eq("programUploadTypeId", importTypeId)); 
        criteria.addOrder(Order.asc("processOrder"));
        
		List<programUploadTypeAlgorithm> algorithmList = criteria.list();      
        
		return algorithmList;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public algorithmMatchingActions getActionById(Integer actionId)
			throws Exception {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(algorithmMatchingActions.class);
        criteria.add(Restrictions.eq("id", actionId));
        
		List<algorithmMatchingActions> actionList = criteria.list();      
        if (actionList.size() > 0) {
        	return actionList.get(0);
        } else {
        	return null;
        }
		
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public algorithmCategories getCategoryById(Integer categoryId)
			throws Exception {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(algorithmCategories.class);
        criteria.add(Restrictions.eq("id", categoryId));
        
		List<algorithmCategories> catList = criteria.list();      
        if (catList.size() > 0) {
        	return catList.get(0);
        } else {
        	return null;
        }
	}
	
}
