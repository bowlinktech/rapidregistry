/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.service;

import com.bowlink.rr.model.programEngagementFieldValues;
import com.bowlink.rr.model.programEngagementFields;
import com.bowlink.rr.model.programEngagementSections;
import com.bowlink.rr.model.programPatientFieldValues;
import com.bowlink.rr.model.programPatientFields;
import com.bowlink.rr.model.programPatientSections;
import com.bowlink.rr.model.programProfileFieldValues;
import com.bowlink.rr.model.programProfileFields;
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
    
    List<programPatientFields> getAllPatientFields(Integer programId) throws Exception;
    
    void deletePatientFields(Integer programId, Integer SectionId) throws Exception;
    
    void deletePatientField(Integer fieldId) throws Exception;
    
    Integer savePatientFields(programPatientFields field) throws Exception;
    
    void savePatientField(programPatientFields field) throws Exception;
    
    void savePatientFieldValueFieldId(Integer oldFieldId, Integer newFieldId) throws Exception;
    
    void savePatientSection(programPatientSections patientSection) throws Exception;
    
    programPatientSections getPatientSectionBydspPos(Integer dspPos, Integer programId);
    
    List<programEngagementSections> getEngagementSections(Integer programId) throws Exception;
    
    List<programEngagementFields> getEngagementFieldsByProgramId(Integer programId) throws Exception;
    
    programEngagementSections getEngagementSectionById(Integer sectionId) throws Exception;
    
    List<programEngagementFields> getEngagementFields(Integer programId, Integer sectionId) throws Exception;
    
    void deleteEngagementFields(Integer programId, Integer SectionId) throws Exception;
    
    void deleteEngagementField(Integer fieldId) throws Exception;
    
    Integer saveEngagementFields(programEngagementFields field) throws Exception;
    
    void saveEngagementField(programEngagementFields field) throws Exception;
    
    void saveEngagementFieldValueFieldId(Integer oldFieldId, Integer newFieldId) throws Exception;
    
    void saveEngagementSection(programEngagementSections engagementSection) throws Exception;
    
    programEngagementSections getEngagementSectionBydspPos(Integer dspPos, Integer programId);
    
    programPatientFields getPatientFieldById(Integer fieldId) throws Exception;
    
    programEngagementFields getEngagementFieldById(Integer fieldId) throws Exception;
    
    void removeProgramFieldValues(Integer fieldId) throws Exception;
    
    void savePatientFieldValue(programPatientFieldValues newFieldValue) throws Exception;
    
    void removeEngagementFieldValues(Integer fieldId) throws Exception;
    
    void saveEngagementFieldValue(programEngagementFieldValues newFieldValue) throws Exception;
    
    List<programPatientFieldValues> getPatientFieldValues(Integer fieldId) throws Exception;
    
    List<programEngagementFieldValues> getEngagementFieldValues(Integer fieldId) throws Exception;
    
    List<programProfileFieldValues> getProgramProfileFieldValues(Integer fieldId) throws Exception;
    
    List getFieldsForProgram(Integer programId) throws Exception;
    
    List<programProfileFields> getProgramProfileFields(Integer programId) throws Exception;
    
    void deleteProgramProfileFields(Integer programId) throws Exception;
    
    void deleteProgramProfileField(Integer fieldId) throws Exception;
    
    Integer saveProgramProfileFields(programProfileFields field) throws Exception;
    
    void saveProgramProfileField(programProfileFields field) throws Exception;
    
    void saveProgramProfileFieldValueFieldId(Integer oldFieldId, Integer newFieldId) throws Exception;
    
    programProfileFields getProgramProfileFieldById(Integer fieldId) throws Exception;
                    
}
