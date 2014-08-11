/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bowlink.rr.dao;

import com.bowlink.rr.model.patientSharing;
import com.bowlink.rr.model.program;
import com.bowlink.rr.model.programModules;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author chadmccue
 */
@Repository
public interface programDAO {
    
    Integer createProgram(program newProgram) throws Exception;
    
    program getProgramByName(String programName) throws Exception;
    
    program getProgramByName(String programName, Integer programId) throws Exception;
    
    List<program> getAllPrograms() throws Exception;
    
    void updateProgram(program program) throws Exception;
    
    program getProgramById(Integer programId) throws Exception;
    
    List<program> getOtherPrograms(Integer programId) throws Exception;
    
    List<Integer> getSharedPrograms(Integer programId) throws Exception;
     
    void savePatientSharing(patientSharing newpatientshare) throws Exception;
    
    void deletePatientSharing(Integer programId) throws Exception;
    
    List<Integer> getProgramModules(Integer programId) throws Exception;
    
    void saveProgramModules(programModules module) throws Exception;
    
    void deleteProgramModules(Integer programId) throws Exception;
    
}
