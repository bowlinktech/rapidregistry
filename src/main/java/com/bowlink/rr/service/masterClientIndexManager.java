/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.service;

import com.bowlink.rr.model.programEngagementSections;
import com.bowlink.rr.model.programEngagementSection_MCIAlgorithms;
import com.bowlink.rr.model.programEngagementSection_mciFields;

import java.util.List;

/**
 *
 * @author chadmccue
 */
public interface masterClientIndexManager {
    
    List<programEngagementSection_MCIAlgorithms> getEngagementSectionMCIalgorithms(Integer programId) throws Exception;
    
    List<programEngagementSection_mciFields> getMCIAlgorithmFields(Integer mciId) throws Exception;
    
    Integer createMCIAlgorithm(programEngagementSection_MCIAlgorithms newMCIAlgorithm) throws Exception;
    
    void updateMCIAlgorithm(programEngagementSection_MCIAlgorithms MCIAlgorithm) throws Exception;
    
    void createMCIAlgorithmFields(programEngagementSection_mciFields newField) throws Exception;
    
    programEngagementSection_MCIAlgorithms getMCIAlgorithm(Integer mciId) throws Exception;
   
    void removeAlgorithmField(Integer algorithmFieldId) throws Exception;
    
    void removeAlgorithm(Integer algorithmId) throws Exception;
    
    List <programEngagementSections> getMCIAlgorithms(List <programEngagementSections> engagementSections) throws Exception;
  
    Integer getMaxProcessOrder (Integer sectionId) throws Exception;
    
    void reorderAlgorithm (Integer sectionId) throws Exception;
    
    programEngagementSection_MCIAlgorithms getMCIAlgorithmByProcessOrder(Integer processOrder, Integer sectionId) throws Exception;
}
