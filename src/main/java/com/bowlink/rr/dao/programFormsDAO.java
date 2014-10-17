/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.dao;

import com.bowlink.rr.model.programEngagementFields;
import com.bowlink.rr.model.programEngagementSections;
import com.bowlink.rr.model.programPatientFields;
import com.bowlink.rr.model.programPatientSections;
import java.util.List;

/**
 *
 * @author chadmccue
 */
public interface programFormsDAO {
    
    List<programPatientSections> getPatientSections(Integer programId) throws Exception;
    
    List<programPatientFields> getPatientFieldsByProgramId(Integer programId) throws Exception;
    
    programPatientSections getPatientSectionById(Integer sectionId) throws Exception;
    
    List<programPatientFields> getPatientFields(Integer programId, Integer sectionId) throws Exception;
    
    List<programPatientFields> getAllPatientFields(Integer programId) throws Exception;
    
    void deletePatientFields(Integer programId, Integer SectionId) throws Exception;
    
    void savePatientFields(programPatientFields field) throws Exception;
    
    void savePatientSection(programPatientSections patientSection) throws Exception;
    
    programPatientSections getPatientSectionBydspPos(Integer dspPos, Integer programId);
    
    List<programEngagementSections> getEngagementSections(Integer programId) throws Exception;
    
    List<programEngagementFields> getEngagementFieldsByProgramId(Integer programId) throws Exception;
    
    programEngagementSections getEngagementSectionById(Integer sectionId) throws Exception;
    
    List<programEngagementFields> getEngagementFields(Integer programId, Integer sectionId) throws Exception;
    
    void deleteEngagementFields(Integer programId, Integer SectionId) throws Exception;
    
    void saveEngagementFields(programEngagementFields field) throws Exception;
    
    void saveEngagementSection(programEngagementSections engagementSection) throws Exception;
    
    programEngagementSections getEngagementSectionBydspPos(Integer dspPos, Integer programId);
    
    programPatientFields getPatientFieldById(Integer fieldId) throws Exception;
    
}
