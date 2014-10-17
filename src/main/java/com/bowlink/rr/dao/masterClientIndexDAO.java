/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.dao;

import com.bowlink.rr.model.programMCIAlgorithms;
import com.bowlink.rr.model.programMCIFields;
import java.util.List;


/**
 *
 * @author chadmccue
 */
public interface masterClientIndexDAO {
    
    List<programMCIAlgorithms> getProgramMCIAlgorithms(Integer programId) throws Exception;
    
    List<programMCIFields> getProgramMCIFields(Integer mciId) throws Exception;
    
    Integer createMCIAlgorithm(programMCIAlgorithms newMCIAlgorithm) throws Exception;
    
    void updateMCIAlgorithm(programMCIAlgorithms MCIAlgorithm) throws Exception;
    
    void createMCIAlgorithmFields(programMCIFields newField) throws Exception;
    
    programMCIAlgorithms getMCIAlgorithm(Integer mpiId) throws Exception;
    
    void removeAlgorithmField(Integer algorithmFieldId) throws Exception;
    
    void removeAlgorithm(Integer algorithmId) throws Exception;
}
