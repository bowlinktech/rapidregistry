/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.dao.impl;

import com.bowlink.rr.dao.importDAO;
import com.bowlink.rr.model.crosswalks;
import com.bowlink.rr.model.fileTypes;
import com.bowlink.rr.model.programUploadTypes;
import com.bowlink.rr.model.programUploadTypesFormFields;

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
public class importDAOImpl implements importDAO {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    /**
     * The 'getUploadTypes' function will return a list of upload types set up for the program.
     * 
     * @return The function will return a list of upload types in the system
     */
    @Override
    public List<programUploadTypes> getUploadTypes(Integer programId) throws Exception {
        
        String sqlQuery = "select id, programId, name, dateCreated, status, useHEL from programUploadTypes where programId = " + programId;
        
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery) 
        .setResultTransformer(Transformers.aliasToBean(programUploadTypes.class)
        );
        
        return query.list();
    }
    
    
    
    /**
     * The 'getUploadTypeById' function will return the details for the clicked upload type.
     * 
     * @param uploadTypeId The id of the selected upload type.
     * @return
     * @throws Exception 
     */
    @Override
    public programUploadTypes getUploadTypeById(Integer importTypeId) throws Exception {
        return (programUploadTypes) sessionFactory.getCurrentSession().get(programUploadTypes.class, importTypeId); 
    }
    
    /**
     * The 'saveUploadType' function will save all selected program upload type.
     * 
     * @param patientSection         The programPatientSections object to save
     * @throws Exception 
     */
    @Override
    public void saveUploadType(programUploadTypes importTypeDetails) throws Exception {
        sessionFactory.getCurrentSession().saveOrUpdate(importTypeDetails);
    }
    
    /**
     * The 'getImportTypeFields' function will return the fields associated the passed in upload type.
     * 
     * @param uploadTypeId  The id of the clicked upload type
     * @return
     * @throws Exception 
     */
    @Override
    public List<programUploadTypesFormFields> getImportTypeFields(Integer importTypeId) throws Exception {
        Query query = sessionFactory.getCurrentSession().createQuery("from programUploadTypesFormFields where programUploadTypeId = :importTypeId order by dspPos asc");
        query.setParameter("importTypeId", importTypeId);
        
        return query.list();
    }
    
    /**
     * The 'deleteUploadTypeFields' function will remove all the fields for the passed in upload type
     * 
     * @param uploadTypeId  The id of the clicked upload type
     * @throws Exception 
     */
    @Override
    public void deleteUploadTypeFields(Integer importTypeId) throws Exception {
        Query deleteFields = sessionFactory.getCurrentSession().createQuery("delete from programUploadTypesFormFields where programUploadTypeId = :importTypeId");
        deleteFields.setParameter("importTypeId", importTypeId);
        deleteFields.executeUpdate();
    }
    
    /**
     * The 'saveUploadTypeField' function will save the new upload type field
     * 
     * @param field The object containing the new upload field
     * @return
     * @throws Exception 
     */
    @Override
    public Integer saveUploadTypeField(programUploadTypesFormFields field) throws Exception {
        Integer lastId = null;

        lastId = (Integer) sessionFactory.getCurrentSession().save(field);

        return lastId;
    }
    
    /**
     * The 'getUploadTypeFieldById' function will return the details of the selected upload type field.
     * 
     * @param fieldId   The id of the clicked field
     * @return
     * @throws Exception 
     */
    @Override
    public programUploadTypesFormFields getUploadTypeFieldById(Integer fieldId) throws Exception {
        return (programUploadTypesFormFields) sessionFactory.getCurrentSession().get(programUploadTypesFormFields.class, fieldId); 
    }
    
    
    /**
     * The 'saveImportField' function will save the changes to the selected import field.
     * @param fieldDetails
     * @throws Exception 
     */
    @Override
    public void saveImportField(programUploadTypesFormFields fieldDetails) throws Exception {
        sessionFactory.getCurrentSession().saveOrUpdate(fieldDetails);
    }
    
    /**
     * The 'removeImportType' function will remove all the fields for the passed in upload type
     * 
     * @param importTypeId  The id of the clicked upload type
     * @throws Exception 
     */
    @Override
    public void removeImportType(Integer importTypeId) throws Exception {
        
        Query deleteFields = sessionFactory.getCurrentSession().createQuery("delete from programUploadTypesFormFields where programUploadTypeId = :importTypeId");
        deleteFields.setParameter("importTypeId", importTypeId);
        deleteFields.executeUpdate();
        
        Query deleteImportType = sessionFactory.getCurrentSession().createQuery("delete from programUploadTypes where id = :importTypeId");
        deleteImportType.setParameter("importTypeId", importTypeId);
        deleteImportType.executeUpdate();
    }


    /**
     * The 'getFileTypes' function will return a list of file types and its id
     * 
     * @param fileTypeId  The id of the clicked file type
     * Use 0 if we would like the entire list
     * @throws Exception 
     */
	@Override
	public List <fileTypes> getFileTypes(Integer fileTypeId) throws Exception {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(fileTypes.class);
		
		if (fileTypeId != 0) {
			criteria.add(Restrictions.eq("id", fileTypeId));
		}
		List <fileTypes> fileTypeList =  criteria.list();
		
		return fileTypeList;
	}
    
}
