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
import com.bowlink.rr.model.programUploadTypesFormFields;
import com.bowlink.rr.service.dataElementManager;
import com.bowlink.rr.service.importManager;
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
    
    @Autowired
    importManager importmanager;
    
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
    		programUploadTypesFormFields putFormField = importmanager.getUploadTypeFieldById(field.getPutFormFieldId());
			String fieldName = dataelementmanager.getfieldName(putFormField.getFieldId());
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
    	newField = setFieldActionSQL(newField);
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
	public Integer getMaxProcessOrder(Integer categoryId, Integer importTypeId) throws Exception {
		return masterClientIndexDAO.getMaxProcessOrder(categoryId, importTypeId);
	}

	@Override
	@Transactional
	public void reorderAlgorithm(Integer categoryId, Integer importTypeId) throws Exception {
		//first we get all algorithm for section 
		List<programUploadTypeAlgorithm> algorithms = masterClientIndexDAO.getPUTAlgorithmByCategory(categoryId, importTypeId, false);
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
			Integer processOrder, Integer programUploadTypeId, Integer categoryId) throws Exception {
		return masterClientIndexDAO.getMCIAlgorithmByProcessOrder(processOrder, programUploadTypeId, categoryId);
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
		List<algorithmCategories> algorithmCategoriesNewList = new ArrayList <algorithmCategories> ();
		// loop categories and get algorithms
		for (algorithmCategories category : algorithmCategories) {
			category = setAlgorithmsForOneImportCategory(category.getId(), importTypeId);
			algorithmCategoriesNewList.add(category);
		}
		
		
		return algorithmCategoriesNewList;
	}

	@Override
	public algorithmMatchingActions getActionById(Integer actionId)
			throws Exception {
		return masterClientIndexDAO.getActionById(actionId);
	}

	
	@Override
	public algorithmCategories getCategoryById(Integer categoryId)
			throws Exception {
		return masterClientIndexDAO.getCategoryById(categoryId);
	}

	@Override
	public algorithmCategories setAlgorithmsForOneImportCategory(
			Integer categoryId, Integer importTypeId) throws Exception {
		return setAlgorithmsForOneImportCategory(categoryId, importTypeId, false, false);
		
	}

	@Override
	public algorithmCategories setAlgorithmsForOneImportCategory (
			Integer categoryId, Integer importTypeId, boolean setDataElement, boolean getActiveOnly)
			throws Exception {
		
		algorithmCategories category = getCategoryById(categoryId);
		if (getActiveOnly) {
			category.setAlgorithms(masterClientIndexDAO.getPUTAlgorithmByCategory(category.getId(), importTypeId, true));
		} else {
			category.setAlgorithms(masterClientIndexDAO.getPUTAlgorithmByCategory(category.getId(), importTypeId, false));
		}
		for (programUploadTypeAlgorithm algorithm : category.getAlgorithms()) {
			//need to set action name
			algorithm.setActionName(getActionById(algorithm.getAction()).getDisplayText());
			//this set fields and names
			algorithm.setFields(getMCIAlgorithmFields(algorithm.getId()));	
			if (setDataElement) {
				for (programUploadTypeAlgorithmFields field : algorithm.getFields()) {
					programUploadTypesFormFields putFormField = importmanager.getUploadTypeFieldById(field.getPutFormFieldId());
					field.setDataElement(dataelementmanager.getFieldDetails(putFormField.getFieldId()));
					field.setPutField(putFormField);
				}
			}
		}
		
		return category;
	}

	@Override
	public programUploadTypeAlgorithmFields setFieldActionSQL(
			programUploadTypeAlgorithmFields putField) throws Exception {
		switch (putField.getAction()) {
			case "equals":
				putField.setActionSQL("=");
				break;
				
			case "does not equal":
					putField.setActionSQL("!=");
					break;
			default:
					putField.setActionSQL("=");
				break;		
		}
		return putField;
	}

	
}
