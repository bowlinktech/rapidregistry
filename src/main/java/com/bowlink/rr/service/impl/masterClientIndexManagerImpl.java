/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.service.impl;

import com.bowlink.rr.dao.masterClientIndexDAO;
import com.bowlink.rr.model.programEngagementSections;
import com.bowlink.rr.model.programEngagementSection_MCIAlgorithms;
import com.bowlink.rr.model.programEngagementSection_mciFields;
import com.bowlink.rr.service.dataElementManager;
import com.bowlink.rr.service.masterClientIndexManager;

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
    public List<programEngagementSection_MCIAlgorithms> getEngagementSectionMCIalgorithms(Integer programSectionId) throws Exception {
    	List<programEngagementSection_MCIAlgorithms> algorithmList = masterClientIndexDAO.getEngagementSectionMCIalgorithms(programSectionId);
    	if (algorithmList.size() > 0) {
    		for (programEngagementSection_MCIAlgorithms algorithm : algorithmList) {
    			//we set the fields
    			algorithm.setFields(getMCIAlgorithmFields(algorithm.getId()));
    		}
    	}
    	return algorithmList;
    }
    
    @Override
    @Transactional
    public List<programEngagementSection_mciFields> getMCIAlgorithmFields(Integer mciId) throws Exception {
    	//need to set field names
    	List<programEngagementSection_mciFields> fieldList = masterClientIndexDAO.getMCIAlgorithmFields(mciId);
    	//set fieldName
    	for (programEngagementSection_mciFields field : fieldList) {
    		String fieldName = dataelementmanager.getfieldName(field.getFieldId());
    		field.setFieldName(fieldName);
    	}
    	
        return fieldList;
    }
    
    @Override
    @Transactional
    public Integer createMCIAlgorithm(programEngagementSection_MCIAlgorithms newMCIAlgorithm) throws Exception {
        return masterClientIndexDAO.createMCIAlgorithm(newMCIAlgorithm);
    }
    
    @Override
    @Transactional
    public void updateMCIAlgorithm(programEngagementSection_MCIAlgorithms MCIAlgorithm) throws Exception {
        masterClientIndexDAO.updateMCIAlgorithm(MCIAlgorithm);
    }
    
    @Override
    @Transactional
    public void createMCIAlgorithmFields(programEngagementSection_mciFields newField) throws Exception {
        masterClientIndexDAO.createMCIAlgorithmFields(newField);
    }
    
    @Override
    @Transactional
    public programEngagementSection_MCIAlgorithms getMCIAlgorithm(Integer mciId) throws Exception {
        return masterClientIndexDAO.getMCIAlgorithm(mciId);
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
	public List<programEngagementSections> getMCIAlgorithms(
			List<programEngagementSections> engagementSections)
			throws Exception {
		
		for (programEngagementSections pes : engagementSections) {
			 pes.setMciAlgorithms(getEngagementSectionMCIalgorithms(pes.getId()));		 
		}
		return engagementSections;
	}

	@Override
	public Integer getMaxProcessOrder(Integer sectionId) throws Exception {
		return masterClientIndexDAO.getMaxProcessOrder(sectionId);
	}

	@Override
	@Transactional
	public void reorderAlgorithm(Integer sectionId) throws Exception {
		//first we get all algorithm for section 
		List<programEngagementSection_MCIAlgorithms> algorithms = getEngagementSectionMCIalgorithms(sectionId);
		//we loop through and reorder
		int order = 1;
		for (programEngagementSection_MCIAlgorithms algorithm : algorithms) {
			algorithm.setProcessOrder(order);
			updateMCIAlgorithm(algorithm);
			order ++;
		}
		
	}

}
