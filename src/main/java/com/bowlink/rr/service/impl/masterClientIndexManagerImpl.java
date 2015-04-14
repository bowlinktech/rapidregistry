/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.service.impl;

import com.bowlink.rr.dao.masterClientIndexDAO;
import com.bowlink.rr.model.algorithmCategories;
import com.bowlink.rr.model.algorithmMatchingActions;
import com.bowlink.rr.model.programUploadTypeAlgorithm;
import com.bowlink.rr.model.programUploadTypeAlgorithmFields;
import com.bowlink.rr.service.dataElementManager;
import com.bowlink.rr.service.masterClientIndexManager;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author chadmccue
 */
@Service
public class masterClientIndexManagerImpl implements masterClientIndexManager {
    
    @Autowired
    masterClientIndexDAO masterClientIndexDAO;
    
    @Autowired
    dataElementManager dataelementmanager;
    
    @Override
    @Transactional
    public List<programUploadTypeAlgorithm> getProgramUploadTypeAlgorithm(Integer programUploadTypeId) throws Exception {
    	List<programUploadTypeAlgorithm> algorithmList = masterClientIndexDAO.getProgramUploadTypeAlgorithm(programUploadTypeId);
    	if (algorithmList.size() > 0) {
    		for (programUploadTypeAlgorithm algorithm : algorithmList) {
    			//we set the fields
    			algorithm.setFields(getMCIAlgorithmFields(algorithm.getId()));
    		}
    	}
    	return algorithmList;
    }
    
    @Override
    @Transactional
    public List<programUploadTypeAlgorithmFields> getMCIAlgorithmFields(Integer algorithmId) throws Exception {
    	//need to set field names
    	List<programUploadTypeAlgorithmFields> fieldList = masterClientIndexDAO.getMCIAlgorithmFields(algorithmId);
    	//set fieldName
    	for (programUploadTypeAlgorithmFields field : fieldList) {
    		String fieldName = dataelementmanager.getfieldName(field.getFieldId());
    		field.setFieldName(fieldName);
    	}
    	
        return fieldList;
    }
    
    @Override
    @Transactional
    public Integer createMCIAlgorithm(programUploadTypeAlgorithm newMCIAlgorithm) throws Exception {
        return masterClientIndexDAO.createMCIAlgorithm(newMCIAlgorithm);
    }
    
    @Override
    @Transactional
    public void updateMCIAlgorithm(programUploadTypeAlgorithm MCIAlgorithm) throws Exception {
        masterClientIndexDAO.updateMCIAlgorithm(MCIAlgorithm);
    }
    
    @Override
    @Transactional
    public void createMCIAlgorithmFields(programUploadTypeAlgorithmFields newField) throws Exception {
        masterClientIndexDAO.createMCIAlgorithmFields(newField);
    }
    
    @Override
    @Transactional
    public programUploadTypeAlgorithm getMCIAlgorithm(Integer algorithmId) throws Exception {
        return masterClientIndexDAO.getMCIAlgorithm(algorithmId);
    }
    
    @Override
    @Transactional
    public void removeAlgorithmField(Integer algorithmFieldId) throws Exception {
        masterClientIndexDAO.removeAlgorithmField(algorithmFieldId);
    }
    
    @Override
    @Transactional
    public void removeAlgorithm(Integer algorithmId) throws Exception {
        masterClientIndexDAO.removeAlgorithm(algorithmId);
    }

	@Override
	public Integer getMaxProcessOrder(Integer sectionId) throws Exception {
		return masterClientIndexDAO.getMaxProcessOrder(sectionId);
	}

	@Override
	@Transactional
	public void reorderAlgorithm(Integer programUploadTypeId) throws Exception {
		//first we get all algorithm for section 
		List<programUploadTypeAlgorithm> algorithms = getProgramUploadTypeAlgorithm(programUploadTypeId);
		//we loop through and reorder
		int order = 1;
		for (programUploadTypeAlgorithm algorithm : algorithms) {
			algorithm.setProcessOrder(order);
			updateMCIAlgorithm(algorithm);
			order ++;
		}
		
	}

	@Override
	public programUploadTypeAlgorithm getMCIAlgorithmByProcessOrder(
			Integer processOrder, Integer programUploadTypeId) throws Exception {
		return masterClientIndexDAO.getMCIAlgorithmByProcessOrder(processOrder, programUploadTypeId);
	}

	@Override
	@Transactional
	public List<algorithmMatchingActions> getAlgorithmMatchingActions(Boolean status) throws Exception {
		return masterClientIndexDAO.getAlgorithmMatchingActions(status);
	}

	@Override
	public List<algorithmCategories> getAlgorithmCategories(Boolean status)
			throws Exception {
		return masterClientIndexDAO.getAlgorithmCategories(status);
	}

	@Override
	public List<algorithmCategories> getAlgorithmsByCatForUploadType(
			Integer importTypeId) throws Exception {
		//1. get the categories
		List<algorithmCategories> algorithmCategories = masterClientIndexDAO.getCategoriesForUploadType(importTypeId);
		// loop categories and get algorithms
		for (algorithmCategories category : algorithmCategories) {
			category.setAlgorithms(masterClientIndexDAO.getPUTAlgorithmByCategory(category.getId(), importTypeId));
			for (programUploadTypeAlgorithm algorithm : category.getAlgorithms()) {
				//this set fields and names
				algorithm.setFields(masterClientIndexDAO.getMCIAlgorithmFields(algorithm.getId()));				
			}
		}
		
		
		return algorithmCategories;
	}
	
}
