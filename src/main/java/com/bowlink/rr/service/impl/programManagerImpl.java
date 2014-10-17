/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bowlink.rr.service.impl;

import com.bowlink.rr.dao.programDAO;
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
import com.bowlink.rr.model.programPatientSearchFields;
import com.bowlink.rr.model.programPatientSections;
import com.bowlink.rr.model.programPatientSummaryFields;
import com.bowlink.rr.service.programManager;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author chadmccue
 */
@Service
public class programManagerImpl implements programManager {
    
    @Autowired
    programDAO programDAO;
    
    @Override
    @Transactional
    public Integer createProgram(program newProgram) throws Exception {
        Integer lastId = null;
        lastId = (Integer) programDAO.createProgram(newProgram);
        return lastId;
    }
    
    @Override
    @Transactional
    public program getProgramByName(String programName) throws Exception {
        
        /* Need to remove hyphens */
        String programNameSearch = programName.replace("-", " ");
        
        return programDAO.getProgramByName(programNameSearch);
        
    }
    
    @Override
    @Transactional
    public program getProgramByName(String programName, Integer programId) throws Exception {
        
        /* Need to remove hyphens */
        String programNameSearch = programName.replace("-", " ");
        
        return programDAO.getProgramByName(programNameSearch, programId);
        
    }
    
    @Override
    @Transactional
    public List<program> getAllPrograms() throws Exception {
        return programDAO.getAllPrograms();
    }
    
    @Override
    @Transactional
    public void updateProgram(program program) throws Exception {
        programDAO.updateProgram(program);
    }
    
    @Override
    @Transactional
    public program getProgramById(Integer programId) throws Exception {
        return programDAO.getProgramById(programId);
    }
    
    @Override
    @Transactional
    public List<program> getOtherPrograms(Integer programId) throws Exception {
        return programDAO.getOtherPrograms(programId);
    }
    
    @Override
    @Transactional
    public boolean getUsedActivityCodes (Integer programId, Integer codeId) throws Exception {
        return programDAO.getUsedActivityCodes(programId, codeId);
    }
    
    @Override
    @Transactional
    public void saveProgramActivityCode(programActivityCodes newCodeAssoc) throws Exception {
        programDAO.saveProgramActivityCode(newCodeAssoc);
    }
    
    @Override
    @Transactional
    public void removeProgramActivityCodes(Integer programId) throws Exception {
        programDAO.removeProgramActivityCodes(programId);
    }
    
    @Override
    @Transactional
    public List<Integer> getProgramReports(Integer programId) throws Exception {
        return programDAO.getProgramReports(programId);
    }
    
    @Override
    @Transactional
    public void saveProgramReports(programReports report) throws Exception {
        programDAO.saveProgramReports(report);
    }
    
    @Override
    @Transactional
    public void deleteProgramReports(Integer programId) throws Exception {
        programDAO.deleteProgramReports(programId);
    }
    
    @Override
    @Transactional
    public List<programMCIAlgorithms> getProgramMCIAlgorithms(Integer programId) throws Exception {
        return programDAO.getProgramMCIAlgorithms(programId);
    }
    
    @Override
    @Transactional
    public List<programMCIFields> getProgramMCIFields(Integer mciId) throws Exception {
        return programDAO.getProgramMCIFields(mciId);
    }
    
    @Override
    @Transactional
    public Integer createMCIAlgorithm(programMCIAlgorithms newMCIAlgorithm) throws Exception {
        return programDAO.createMCIAlgorithm(newMCIAlgorithm);
    }
    
    @Override
    @Transactional
    public void updateMCIAlgorithm(programMCIAlgorithms MCIAlgorithm) throws Exception {
        programDAO.updateMCIAlgorithm(MCIAlgorithm);
    }
    
    @Override
    @Transactional
    public void createMCIAlgorithmFields(programMCIFields newField) throws Exception {
        programDAO.createMCIAlgorithmFields(newField);
    }
    
    @Override
    @Transactional
    public programMCIAlgorithms getMCIAlgorithm(Integer mciId) throws Exception {
        return programDAO.getMCIAlgorithm(mciId);
    }
    
    @Override
    @Transactional
    public void removeAlgorithmField(Integer algorithmFieldId) throws Exception {
        programDAO.removeAlgorithmField(algorithmFieldId);
    }
    
    @Override
    @Transactional
    public List<programAdmin> getProgramAdministrators(Integer programId) throws Exception {
        return programDAO.getProgramAdministrators(programId);
    }
    
    @Override
    @Transactional
    public void saveAdminProgram(programAdmin adminProgram) throws Exception {
        programDAO.saveAdminProgram(adminProgram);
    }
    
    @Override
    @Transactional
    public void removeAdminProgram(Integer programId, Integer adminid) throws Exception {
        programDAO.removeAdminProgram(programId, adminid);
    }
    
    @Override
    @Transactional
    public List<programPatientEntryMethods> getPatientEntryMethods(Integer programId) throws Exception {
        return programDAO.getPatientEntryMethods(programId);
    }
    
    @Override
    @Transactional
    public List<programAvailableTables> getAvailableTablesForSurveys(Integer programId) throws Exception {
        return programDAO.getAvailableTablesForSurveys(programId);
    }
    
    @Override
    @Transactional
    public void saveProgramAvailableTables(programAvailableTables availableTable) throws Exception {
        programDAO.saveProgramAvailableTables(availableTable);
    }
    
    @Override
    @Transactional
    public programAvailableTables getProgramAvailableTable(Integer id) throws Exception {
        return programDAO.getProgramAvailableTable(id);
    }
    
    @Override
    @Transactional
    public void deleteProgramAvailableTable(Integer id) throws Exception {
        programDAO.deleteProgramAvailableTable(id);
    }
    
    
    @Override
    @Transactional
    public programPatientEntryMethods getpatientEntryMethodDetails(Integer id) throws Exception {
        return programDAO.getpatientEntryMethodDetails(id);
    }
    
    @Override
    @Transactional
    public void saveProgramPatientEntryMethod(programPatientEntryMethods entryMethod) throws Exception {
        programDAO.saveProgramPatientEntryMethod(entryMethod);
    }
    
    @Override
    @Transactional
    public void deletePatientEntryMethod(Integer id) throws Exception {
        programDAO.deletePatientEntryMethod(id);
    }
    
    @Override
    @Transactional
    public List<programPatientSearchFields> getProgramSearchFields(Integer programId) throws Exception {
        return programDAO.getProgramSearchFields(programId);
    }

    @Override
    @Transactional
    public void saveProgramPatientSearchField(programPatientSearchFields searchField) throws Exception {
        programDAO.saveProgramPatientSearchField(searchField);
    }
    
    @Override
    @Transactional
    public void deleteProgramPatientSearchField(Integer id) throws Exception {
        programDAO.deleteProgramPatientSearchField(id);
    }
    
    @Override
    @Transactional
    public List<programPatientSummaryFields> getProgramSummaryFields(Integer programId) throws Exception {
        return programDAO.getProgramSummaryFields(programId);
    }

    @Override
    @Transactional
    public void saveProgramPatientSummaryField(programPatientSummaryFields summaryField) throws Exception {
        programDAO.saveProgramPatientSummaryField(summaryField);
    }
    
    @Override
    @Transactional
    public void deleteProgramPatientSummaryField(Integer id) throws Exception {
        programDAO.deleteProgramPatientSummaryField(id);
    }
}
