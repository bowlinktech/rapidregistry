/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.dao;

import com.bowlink.rr.model.algorithmCategories;
import com.bowlink.rr.model.algorithmMatchingActions;
import com.bowlink.rr.model.programUploadTypeAlgorithm;
import com.bowlink.rr.model.programUploadTypeAlgorithmFields;

import java.util.List;


/**
 *
 * @author chadmccue
 */
public interface masterClientIndexDAO {
    
    List<programUploadTypeAlgorithm> getProgramUploadTypeAlgorithm(Integer importTypeId) throws Exception;
    
    List<programUploadTypeAlgorithmFields> getMCIAlgorithmFields(Integer algorithmId) throws Exception;
    
    Integer createMCIAlgorithm(programUploadTypeAlgorithm newMCIAlgorithm) throws Exception;
    
    void updateMCIAlgorithm(programUploadTypeAlgorithm MCIAlgorithm) throws Exception;
    
    void createMCIAlgorithmFields(programUploadTypeAlgorithmFields newField) throws Exception;
    
    programUploadTypeAlgorithm getMCIAlgorithm(Integer algorithmId) throws Exception;
    
    void removeAlgorithmField(Integer algorithmFieldId) throws Exception;
    
    void removeAlgorithm(Integer algorithmId) throws Exception;
    
    Integer getMaxProcessOrder (Integer categoryId, Integer importTypeId) throws Exception;
    
    programUploadTypeAlgorithm getMCIAlgorithmByProcessOrder(Integer processOrder, Integer importTypeId, Integer categoryId) throws Exception;
    
    List <algorithmMatchingActions> getAlgorithmMatchingActions (Boolean status) throws Exception;
    
    List <algorithmCategories> getAlgorithmCategories (Boolean status) throws Exception;
    
    List <algorithmCategories> getCategoriesForUploadType (Integer importTypeId) throws Exception;
    
    List <programUploadTypeAlgorithm> getPUTAlgorithmByCategory (Integer catId, Integer importTypeId) throws Exception;
    
    algorithmMatchingActions getActionById (Integer actionId) throws Exception;
    
    
    
}
