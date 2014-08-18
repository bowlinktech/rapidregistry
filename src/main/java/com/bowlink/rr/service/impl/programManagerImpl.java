/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bowlink.rr.service.impl;

import com.bowlink.rr.dao.programDAO;
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
    public void savePatientSharing(patientSharing newpatientshare) throws Exception {
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
    public List<programDemoDataElements> getProgramDemoFields(Integer programId) throws Exception {
        return programDAO.getProgramDemoFields(programId);
    }
    
    @Override
    @Transactional
    public void deleteDemoFields(Integer programId) throws Exception {
        programDAO.deleteDemoFields(programId);
    }
    
    @Override
    @Transactional
    public void saveDemoFields(programDemoDataElements field) throws Exception {
        programDAO.saveDemoFields(field);
    }
    
    @Override
    @Transactional
    public List<programHealthDataElements> getProgramHealthFields(Integer programId) throws Exception {
        return programDAO.getProgramHealthFields(programId);
    }
    
    @Override
    @Transactional
    public void deleteHealthFields(Integer programId) throws Exception {
        programDAO.deleteHealthFields(programId);
    }
    
    @Override
    @Transactional
    public void saveHealthFields(programHealthDataElements field) throws Exception {
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
    public List<programMPI> getProgramMPIAlgorithms(Integer programId) throws Exception {
        return programDAO.getProgramMPIAlgorithms(programId);
    }
    
    @Override
    @Transactional
    public List<programMPIFields> getProgramMPIFields(Integer mpiId) throws Exception {
        return programDAO.getProgramMPIFields(mpiId);
    }
    
    @Override
    @Transactional
    public Integer createMPIAlgorithm(programMPI newMPIAlgorithm) throws Exception {
        return programDAO.createMPIAlgorithm(newMPIAlgorithm);
    }
    
    @Override
    @Transactional
    public void updateMPIAlgorithm(programMPI MPIAlgorithm) throws Exception {
        programDAO.updateMPIAlgorithm(MPIAlgorithm);
    }
    
    @Override
    @Transactional
    public void createMPIAlgorithmFields(programMPIFields newField) throws Exception {
        programDAO.createMPIAlgorithmFields(newField);
    }
    
    @Override
    @Transactional
    public programMPI getMPIAlgorithm(Integer mpiId) throws Exception {
        return programDAO.getMPIAlgorithm(mpiId);
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
