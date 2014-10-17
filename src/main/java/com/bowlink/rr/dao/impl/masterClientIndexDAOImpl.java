/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.dao.impl;

import com.bowlink.rr.dao.masterClientIndexDAO;
import com.bowlink.rr.model.programMCIAlgorithms;
import com.bowlink.rr.model.programMCIFields;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author chadmccue
 */
@Repository
public class masterClientIndexDAOImpl implements masterClientIndexDAO {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    /**
     * The 'getProgramMCIAlgorithms' function will return a list of the programs in the system.
     * 
     * @return The function will return a list of programs in the system
     */
    @Override
    public List<programMCIAlgorithms> getProgramMCIAlgorithms(Integer programId) throws Exception {
        Query query = sessionFactory.getCurrentSession().createQuery("from programMCIAlgorithms where programId = :programId order by id asc");
        query.setParameter("programId", programId);

        List<programMCIAlgorithms> MCIList = query.list();
        return MCIList;
    }
    
    /**
     * The 'getProgramMCIFields' function will get a list of fields associated to teach MCI Algorithm.
     * 
     * @param mciId The id of the MCI Algorithm
     * @return  This function will return a list of MCI Fields
     * @throws Exception 
     */
    @Override
    public List<programMCIFields> getProgramMCIFields(Integer mciId) throws Exception {
        Query query = sessionFactory.getCurrentSession().createQuery("from programMCIFields where mciId = :mciId order by id asc");
        query.setParameter("mciId", mciId);

        List<programMCIFields> fieldList = query.list();
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
    public Integer createMCIAlgorithm(programMCIAlgorithms newMCIAlgorithm) throws Exception {
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
    public void updateMCIAlgorithm(programMCIAlgorithms MCIAlgorithm) throws Exception {
        sessionFactory.getCurrentSession().update(MCIAlgorithm);
    }
    
    
    /**
     * The 'createMCIAlgorithmFields' function will save the fields associated to the MCI 
     * algorithm.
     * 
     * 
     */
    @Override
    public void createMCIAlgorithmFields(programMCIFields newField) throws Exception {
        sessionFactory.getCurrentSession().save(newField);
    }
    
    /**
     * The 'getMCIAlgorithm' function will return the details of the passed in MCI algorithm.
     * 
     * @param mciId The id of the selected MCI algorithm
     * @return
     * @throws Exception 
     */
    @Override
    public programMCIAlgorithms getMCIAlgorithm(Integer mciId) throws Exception {
        Query query = sessionFactory.getCurrentSession().createQuery("from programMCIAlgorithms where id = :mciId");
        query.setParameter("mciId", mciId);

        return (programMCIAlgorithms) query.uniqueResult();
    }

    /**
     * The 'removeAlgorithmField' function will remove the selected field from the MCI algorithm
     * 
     * @param algorithmFieldId  The id of the selected MCI Algorithm field
     */
    @Override
    public void removeAlgorithmField(Integer algorithmFieldId) throws Exception {
        Query deleteAlgorithmField = sessionFactory.getCurrentSession().createQuery("delete from programMCIFields where id = :algorithmFieldId");
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
        Query deleteAlgorithmField = sessionFactory.getCurrentSession().createQuery("delete from programMCIFields where mciId = :algorithmId");
        deleteAlgorithmField.setParameter("algorithmId", algorithmId);
        deleteAlgorithmField.executeUpdate();
        
        Query deleteAlgorithm = sessionFactory.getCurrentSession().createQuery("delete from programMCIAlgorithms where id = :algorithmId");
        deleteAlgorithm.setParameter("algorithmId", algorithmId);
        deleteAlgorithm.executeUpdate();
    }
    
}
