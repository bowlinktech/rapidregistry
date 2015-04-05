/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bowlink.rr.service;

import com.bowlink.rr.model.engagementMatchingActions;
import com.bowlink.rr.model.program;
import com.bowlink.rr.model.programAdmin;
import com.bowlink.rr.model.programAvailableTables;
import com.bowlink.rr.model.programPatientEntryMethods;
import com.bowlink.rr.model.programUploadTypes;

import java.util.List;

/**
 *
 * @author chadmccue
 */
public interface programManager {
    
    Integer createProgram(program newProgram) throws Exception;
    
    program getProgramByName(String programName) throws Exception;
    
    program getProgramByName(String programName, Integer programId) throws Exception;
    
    List<program> getAllPrograms() throws Exception;
    
    void updateProgram(program program) throws Exception;
    
    program getProgramById(Integer programId) throws Exception;
    
    List<program> getOtherPrograms(Integer programId) throws Exception;
    
    List<program> getProgramsByAdminisrator(Integer userId) throws Exception;
    
    List<programPatientEntryMethods> getPatientEntryMethods(Integer programId) throws Exception;
    
    programPatientEntryMethods getPatientEntryMethodBydspPos(Integer dspPos, Integer programId) throws Exception;
    
    List<programAvailableTables> getAvailableTablesForSurveys(Integer programId) throws Exception;
    
    programAvailableTables getProgramAvailableTable(Integer id) throws Exception;
    
    void saveProgramAvailableTables(programAvailableTables availableTable) throws Exception;
    
    void deleteProgramAvailableTable(Integer id) throws Exception;
    
    programPatientEntryMethods getpatientEntryMethodDetails(Integer id) throws Exception;
    
    void saveProgramPatientEntryMethod(programPatientEntryMethods entryMethod) throws Exception;
    
    void deletePatientEntryMethod(Integer id) throws Exception;
    
    List<programAdmin> getProgramAdministrators(Integer programId) throws Exception;
    
    void saveAdminProgram(programAdmin adminProgram) throws Exception;
    
    void removeAdminProgram(Integer programId, Integer adminid) throws Exception;
    
    List<program> getAvailbleProgramsForUser(Integer userId) throws Exception;
    
    List <engagementMatchingActions> getEngagementMatchingActions () throws Exception;
    
    List<programUploadTypes> getProgramUploadTypes(Integer programId) throws Exception;
    
}
