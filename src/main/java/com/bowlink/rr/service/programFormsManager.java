/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.service;

import com.bowlink.rr.model.programPatientFields;
import com.bowlink.rr.model.programPatientSections;
import java.util.List;

/**
 *
 * @author chadmccue
 */
public interface programFormsManager {
     
    List<programPatientSections> getPatientSections(Integer programId) throws Exception;
    
    List<programPatientFields> getPatientFieldsByProgramId(Integer programId) throws Exception;
    
    programPatientSections getPatientSectionById(Integer sectionId) throws Exception;
    
    List<programPatientFields> getPatientFields(Integer programId, Integer sectionId) throws Exception;
    
    void deletePatientFields(Integer programId, Integer SectionId) throws Exception;
    
    void savePatientFields(programPatientFields field) throws Exception;
    
    
}
