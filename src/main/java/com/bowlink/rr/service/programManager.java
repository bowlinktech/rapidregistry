/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bowlink.rr.service;

import com.bowlink.rr.model.programPatientSharing;
import com.bowlink.rr.model.program;
import com.bowlink.rr.model.programActivityCodes;
import com.bowlink.rr.model.programPatientFields;
import com.bowlink.rr.model.programEngagementFields;
import com.bowlink.rr.model.programMCIAlgorithms;
import com.bowlink.rr.model.programMCIFields;
import com.bowlink.rr.model.programModules;
import com.bowlink.rr.model.programReports;
import com.bowlink.rr.model.programAdmin;
import com.bowlink.rr.model.programAvailableTables;
import com.bowlink.rr.model.programPatientEntryMethods;
import com.bowlink.rr.model.programPatientSections;
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
    
    List<programPatientEntryMethods> getPatientEntryMethods(Integer programId) throws Exception;
    
    List<programAvailableTables> getAvailableTablesForSurveys(Integer programId) throws Exception;
    
    void saveProgramAvailableTables(programAvailableTables availableTable) throws Exception;
    
    
    /** Patient Fields **/
    
    List<programPatientSections> getPatientSections(Integer programId) throws Exception;
    
    List<programPatientFields> getPatientFieldsByProgramId(Integer programId) throws Exception;
    
    programPatientSections getPatientSectionById(Integer sectionId) throws Exception;
    
    List<programPatientFields> getPatientFields(Integer programId, Integer sectionId) throws Exception;
    
    void deletePatientFields(Integer programId, Integer SectionId) throws Exception;
    
    void savePatientFields(programPatientFields field) throws Exception;
    
    
    /** Engagement Fields **/
    
    List<programEngagementFields> getProgramHealthFields(Integer programId) throws Exception;
    
    void deleteHealthFields(Integer programId) throws Exception;
    
    void saveHealthFields(programEngagementFields field) throws Exception;
    
    boolean getUsedActivityCodes (Integer programId, Integer codeId) throws Exception;
    
    void saveProgramActivityCode(programActivityCodes newCodeAssoc) throws Exception;
    
    void removeProgramActivityCodes(Integer programId) throws Exception;
    
    List<Integer> getProgramReports(Integer programId) throws Exception;
    
    void saveProgramReports(programReports report) throws Exception;
    
    void deleteProgramReports(Integer programId) throws Exception;
    
    List<programMCIAlgorithms> getProgramMCIAlgorithms(Integer programId) throws Exception;
    
    List<programMCIFields> getProgramMCIFields(Integer mciId) throws Exception;
    
    Integer createMCIAlgorithm(programMCIAlgorithms newMCIAlgorithm) throws Exception;
    
    void updateMCIAlgorithm(programMCIAlgorithms MCIAlgorithm) throws Exception;
    
    void createMCIAlgorithmFields(programMCIFields newField) throws Exception;
    
    programMCIAlgorithms getMCIAlgorithm(Integer mciId) throws Exception;
   
    void removeAlgorithmField(Integer algorithmFieldId) throws Exception;
    
    List<programAdmin> getProgramAdministrators(Integer programId) throws Exception;
    
    void saveAdminProgram(programAdmin adminProgram) throws Exception;
    
    void removeAdminProgram(Integer programId, Integer adminid) throws Exception;
    
}
