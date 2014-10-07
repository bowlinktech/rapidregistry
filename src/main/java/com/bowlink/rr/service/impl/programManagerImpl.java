/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bowlink.rr.service.impl;

import com.bowlink.rr.dao.programDAO;
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
import com.bowlink.rr.model.programPatientSections;
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
    public  List<Integer> getSharedPrograms(Integer programId) throws Exception {
        return programDAO.getSharedPrograms(programId);
    }
    
    @Override
    @Transactional
    public void savePatientSharing(programPatientSharing newpatientshare) throws Exception {
        programDAO.savePatientSharing(newpatientshare);
    }
    
    @Override
    @Transactional
    public void deletePatientSharing(Integer programId) throws Exception {
        programDAO.deletePatientSharing(programId);
    }
    
    @Override
    @Transactional
    public List<Integer> getProgramModules(Integer programId) throws Exception {
        return programDAO.getProgramModules(programId);
    }
    
    @Override
    @Transactional
    public void saveProgramModules(programModules module) throws Exception {
        programDAO.saveProgramModules(module);
    }
    
    @Override
    @Transactional
    public void deleteProgramModules(Integer programId) throws Exception {
        programDAO.deleteProgramModules(programId);
    }
    
    @Override
    @Transactional
    public List<programPatientSections> getPatientSections(Integer programId) throws Exception {
        return getPatientSections(programId);
    }
    
    @Override
    @Transactional
    public List<programPatientFields> getPatientFieldsByProgramId(Integer programId) throws Exception {
        return getPatientFieldsByProgramId(programId);
    }
    
    @Override
    @Transactional
    public programPatientSections getPatientSectionById(Integer sectionId) throws Exception {
        return getPatientSectionById(sectionId);
    }
    
    @Override
    @Transactional
    public List<programPatientFields> getPatientFields(Integer programId, Integer sectionId) throws Exception {
        return programDAO.getPatientFields(programId, sectionId);
    }
    
    @Override
    @Transactional
    public void deletePatientFields(Integer programId, Integer sectionId) throws Exception {
        programDAO.deletePatientFields(programId, sectionId);
    }
    
    @Override
    @Transactional
    public void savePatientFields(programPatientFields field) throws Exception {
        programDAO.savePatientFields(field);
    }
    
    @Override
    @Transactional
    public List<programEngagementFields> getProgramHealthFields(Integer programId) throws Exception {
        return programDAO.getProgramHealthFields(programId);
    }
    
    @Override
    @Transactional
    public void deleteHealthFields(Integer programId) throws Exception {
        programDAO.deleteHealthFields(programId);
    }
    
    @Override
    @Transactional
    public void saveHealthFields(programEngagementFields field) throws Exception {
        programDAO.saveHealthFields(field);
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

}
