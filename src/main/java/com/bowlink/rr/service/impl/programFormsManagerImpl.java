/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.service.impl;

import com.bowlink.rr.dao.programFormsDAO;
import com.bowlink.rr.model.programEngagementFieldValues;
import com.bowlink.rr.model.programEngagementFields;
import com.bowlink.rr.model.programEngagementSections;
import com.bowlink.rr.model.programPatientFieldValues;
import com.bowlink.rr.model.programPatientFields;
import com.bowlink.rr.model.programPatientSections;
import com.bowlink.rr.service.programFormsManager;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author chadmccue
 */
@Service
public class programFormsManagerImpl implements programFormsManager {
    
    @Autowired
    programFormsDAO programFormsDAO;
    
    @Override
    @Transactional
    public List<programPatientSections> getPatientSections(Integer programId) throws Exception {
        return programFormsDAO.getPatientSections(programId);
    }
    
    @Override
    @Transactional
    public List<programPatientFields> getPatientFieldsByProgramId(Integer programId) throws Exception {
        return programFormsDAO.getPatientFieldsByProgramId(programId);
    }
    
    @Override
    @Transactional
    public programPatientSections getPatientSectionById(Integer sectionId) throws Exception {
        return programFormsDAO.getPatientSectionById(sectionId);
    }
    
    @Override
    @Transactional
    public List<programPatientFields> getPatientFields(Integer programId, Integer sectionId) throws Exception {
        return programFormsDAO.getPatientFields(programId, sectionId);
    }
    
    @Override
    @Transactional
    public List<programPatientFields> getAllPatientFields(Integer programId) throws Exception {
        return programFormsDAO.getAllPatientFields(programId);
    }
    
    @Override
    @Transactional
    public void deletePatientFields(Integer programId, Integer sectionId) throws Exception {
        programFormsDAO.deletePatientFields(programId, sectionId);
    }
    
    @Override
    @Transactional
    public void savePatientFields(programPatientFields field) throws Exception {
        programFormsDAO.savePatientFields(field);
    }
    
    @Override
    @Transactional
    public void savePatientField(programPatientFields field) throws Exception {
        programFormsDAO.savePatientField(field);
    }
    
    @Override
    @Transactional
    public void savePatientSection(programPatientSections patientSection) throws Exception {
        programFormsDAO.savePatientSection(patientSection);
    }
    
    @Override
    @Transactional
    public programPatientSections getPatientSectionBydspPos(Integer dspPos, Integer programId) {
        return programFormsDAO.getPatientSectionBydspPos(dspPos, programId);
    }
    
    @Override
    @Transactional
    public List<programEngagementSections> getEngagementSections(Integer programId) throws Exception {
        return programFormsDAO.getEngagementSections(programId);
    }
    
    @Override
    @Transactional
    public List<programEngagementFields> getEngagementFieldsByProgramId(Integer programId) throws Exception {
        return programFormsDAO.getEngagementFieldsByProgramId(programId);
    }
    
    @Override
    @Transactional
    public programEngagementSections getEngagementSectionById(Integer sectionId) throws Exception {
        return programFormsDAO.getEngagementSectionById(sectionId);
    }
    
    @Override
    @Transactional
    public List<programEngagementFields> getEngagementFields(Integer programId, Integer sectionId) throws Exception {
        return programFormsDAO.getEngagementFields(programId, sectionId);
    }
    
    @Override
    @Transactional
    public void deleteEngagementFields(Integer programId, Integer sectionId) throws Exception {
        programFormsDAO.deleteEngagementFields(programId, sectionId);
    }
    
    @Override
    @Transactional
    public void saveEngagementFields(programEngagementFields field) throws Exception {
        programFormsDAO.saveEngagementFields(field);
    }
    
    @Override
    @Transactional
    public void saveEngagementField(programEngagementFields field) throws Exception {
        programFormsDAO.saveEngagementField(field);
    }
    
    @Override
    @Transactional
    public void saveEngagementSection(programEngagementSections engagementSection) throws Exception {
        programFormsDAO.saveEngagementSection(engagementSection);
    }
    
    @Override
    @Transactional
    public programEngagementSections getEngagementSectionBydspPos(Integer dspPos, Integer programId) {
        return programFormsDAO.getEngagementSectionBydspPos(dspPos, programId);
    }
    
    @Override
    @Transactional
    public programPatientFields getPatientFieldById(Integer fieldId) throws Exception {
        return programFormsDAO.getPatientFieldById(fieldId);
    }
    
    @Override
    @Transactional
    public programEngagementFields getEngagementFieldById(Integer fieldId) throws Exception {
        return programFormsDAO.getEngagementFieldById(fieldId);
    }
    
    @Override
    @Transactional
    public void removeProgramFieldValues(Integer fieldId) throws Exception {
        programFormsDAO.removeProgramFieldValues(fieldId);
    }
    
    @Override
    @Transactional
    public void savePatientFieldValue(programPatientFieldValues newFieldValue) throws Exception {
         programFormsDAO.savePatientFieldValue(newFieldValue);
    }
    
    @Override
    @Transactional
    public void removeEngagementFieldValues(Integer fieldId) throws Exception {
        programFormsDAO.removeEngagementFieldValues(fieldId);
    }
    
    @Override
    @Transactional
    public void saveEngagementFieldValue(programEngagementFieldValues newFieldValue) throws Exception {
        programFormsDAO.saveEngagementFieldValue(newFieldValue); 
    }
    
    @Override
    @Transactional
    public List<programPatientFieldValues> getPatientFieldValues(Integer fieldId) throws Exception {
        return programFormsDAO.getPatientFieldValues(fieldId);
    }
    
    @Override
    @Transactional
    public List<programEngagementFieldValues> getEngagementFieldValues(Integer fieldId) throws Exception {
        return programFormsDAO.getEngagementFieldValues(fieldId);
    }
}
