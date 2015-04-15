/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.service;

import com.bowlink.rr.model.algorithmCategories;
import com.bowlink.rr.model.algorithmMatchingActions;
import com.bowlink.rr.model.programUploadTypeAlgorithm;
import com.bowlink.rr.model.programUploadTypeAlgorithmFields;

import java.util.List;

/**
 *
 * @author chadmccue
 */
public interface masterClientIndexManager {
    
	List<programUploadTypeAlgorithm> getProgramUploadTypeAlgorithm(Integer programUploadTypeId) throws Exception;
	 
    List<programUploadTypeAlgorithmFields> getMCIAlgorithmFields(Integer algorithmId) throws Exception;
    
    Integer createMCIAlgorithm(programUploadTypeAlgorithm newMCIAlgorithm) throws Exception;
    
    void updateMCIAlgorithm(programUploadTypeAlgorithm MCIAlgorithm) throws Exception;
    
    void createMCIAlgorithmFields(programUploadTypeAlgorithmFields newField) throws Exception;
    
    programUploadTypeAlgorithm getMCIAlgorithm(Integer algorithmId) throws Exception;
   
    void removeAlgorithmField(Integer algorithmFieldId) throws Exception;
    
    void removeAlgorithm(Integer algorithmId) throws Exception;
    
    Integer getMaxProcessOrder (Integer categyroId, Integer importTypeId) throws Exception;
    
    void reorderAlgorithm (Integer categyroId, Integer importTypeId) throws Exception;
    
    programUploadTypeAlgorithm getMCIAlgorithmByProcessOrder(Integer processOrder, Integer programUploadTypeId, Integer categoryId) throws Exception;
    
    List <algorithmMatchingActions> getAlgorithmMatchingActions (Boolean status) throws Exception;
    
    List <algorithmCategories> getAlgorithmCategories (Boolean status) throws Exception;
    
    List <algorithmCategories> getAlgorithmsByCatForUploadType (Integer importTypeId) throws Exception;
    
    algorithmMatchingActions getActionById (Integer actionId) throws Exception;
    
}
