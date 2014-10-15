/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.service.impl;

import com.bowlink.rr.dao.programFormsDAO;
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
        return programFormsDAO.getPatientFields(programId, sectionId);
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
    
}
