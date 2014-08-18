/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bowlink.rr.dao;

import com.bowlink.rr.model.patientSharing;
import com.bowlink.rr.model.program;
import com.bowlink.rr.model.programActivityCodes;
import com.bowlink.rr.model.programDemoDataElements;
import com.bowlink.rr.model.programHealthDataElements;
import com.bowlink.rr.model.programMPI;
import com.bowlink.rr.model.programMPIFields;
import com.bowlink.rr.model.programModules;
import com.bowlink.rr.model.programReports;
import com.bowlink.rr.model.programAdmin;
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
    
    List<programDemoDataElements> getProgramDemoFields(Integer programId) throws Exception;
    
    void deleteDemoFields(Integer programId) throws Exception;
    
    void saveDemoFields(programDemoDataElements field) throws Exception;
    
    List<programHealthDataElements> getProgramHealthFields(Integer programId) throws Exception;
    
    void deleteHealthFields(Integer programId) throws Exception;
    
    void saveHealthFields(programHealthDataElements field) throws Exception;
    
    boolean getUsedActivityCodes (Integer programId, Integer codeId) throws Exception;
    
    void saveProgramActivityCode(programActivityCodes newCodeAssoc) throws Exception;
    
    void removeProgramActivityCodes(Integer programId) throws Exception;
    
    List<Integer> getProgramReports(Integer programId) throws Exception;
    
    void saveProgramReports(programReports report) throws Exception;
    
    void deleteProgramReports(Integer programId) throws Exception;
    
    List<programMPI> getProgramMPIAlgorithms(Integer programId) throws Exception;
    
    List<programMPIFields> getProgramMPIFields(Integer mpiId) throws Exception;
    
    Integer createMPIAlgorithm(programMPI newMPIAlgorithm) throws Exception;
    
    void updateMPIAlgorithm(programMPI MPIAlgorithm) throws Exception;
    
    void createMPIAlgorithmFields(programMPIFields newField) throws Exception;
    
    programMPI getMPIAlgorithm(Integer mpiId) throws Exception;
    
    void removeAlgorithmField(Integer algorithmFieldId) throws Exception;
    
    List<programAdmin> getProgramAdministrators(Integer programId) throws Exception;
    
    void saveAdminProgram(programAdmin adminProgram) throws Exception;
    
    void removeAdminProgram(Integer programId, Integer adminid) throws Exception;
    
}
