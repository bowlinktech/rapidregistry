/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.service;

import com.bowlink.rr.model.programUpload_MCIalgorithms;
import com.bowlink.rr.model.programUpload_MCIFields;
import java.util.List;

/**
 *
 * @author chadmccue
 */
public interface masterClientIndexManager {
    
    List<programUpload_MCIalgorithms> getProgramUploadMCIalgorithms(Integer programId) throws Exception;
    
    List<programUpload_MCIFields> getProgramUploadMCIFields(Integer mciId) throws Exception;
    
    Integer createMCIAlgorithm(programUpload_MCIalgorithms newMCIAlgorithm) throws Exception;
    
    void updateMCIAlgorithm(programUpload_MCIalgorithms MCIAlgorithm) throws Exception;
    
    void createMCIAlgorithmFields(programUpload_MCIFields newField) throws Exception;
    
    programUpload_MCIalgorithms getMCIAlgorithm(Integer mciId) throws Exception;
   
    void removeAlgorithmField(Integer algorithmFieldId) throws Exception;
    
    void removeAlgorithm(Integer algorithmId) throws Exception;
    
}
