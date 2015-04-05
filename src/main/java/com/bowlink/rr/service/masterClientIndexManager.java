/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.service;

import com.bowlink.rr.model.program_MCIAlgorithms;
import com.bowlink.rr.model.program_MCIFields;
import java.util.List;

/**
 *
 * @author chadmccue
 */
public interface masterClientIndexManager {
    
    List<program_MCIAlgorithms> getProgramUploadMCIalgorithms(Integer programId) throws Exception;
    
    List<program_MCIFields> getProgramUploadMCIFields(Integer mciId) throws Exception;
    
    Integer createMCIAlgorithm(program_MCIAlgorithms newMCIAlgorithm) throws Exception;
    
    void updateMCIAlgorithm(program_MCIAlgorithms MCIAlgorithm) throws Exception;
    
    void createMCIAlgorithmFields(program_MCIFields newField) throws Exception;
    
    program_MCIAlgorithms getMCIAlgorithm(Integer mciId) throws Exception;
   
    void removeAlgorithmField(Integer algorithmFieldId) throws Exception;
    
    void removeAlgorithm(Integer algorithmId) throws Exception;
    
}
