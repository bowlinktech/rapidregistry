/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bowlink.rr.dao;

import com.bowlink.rr.model.crosswalks;
import com.bowlink.rr.model.demoDataElements;
import com.bowlink.rr.model.healthDataElements;
import java.util.List;

/**
 *
 * @author chadmccue
 */
public interface dataElementDAO {
    
    List<demoDataElements> getDemoDataElements() throws Exception;
    
    List<healthDataElements> getHealthDataElements() throws Exception;
    
    List<crosswalks> getCrosswalks(int page, int maxResults, int programId);
    
    Integer createCrosswalk(crosswalks crosswalkDetails);

    Long checkCrosswalkName(String name, int orgId);

    double findTotalCrosswalks(int orgId);

    crosswalks getCrosswalk(int cwId);
    
    @SuppressWarnings("rawtypes")
    List getCrosswalkData(int cwId);
    
    @SuppressWarnings("rawtypes")
    List getDelimiters();
    
    String getDelimiterChar(int id);
    
    @SuppressWarnings("rawtypes")
    List getValidationTypes();
    
    String getDemoFieldName(int fieldId);
    
    String getHealthFieldName(int fieldId);
    
    String getCrosswalkName(int cwId);
    
    String getValidationName(int validationId);
    
    @SuppressWarnings("rawtypes")
    List getInformationTables();

    @SuppressWarnings("rawtypes")
    List getTableColumns(String tableName);
    
    void saveDemoField(demoDataElements formField) throws Exception;
    
    void saveHealthField(healthDataElements formField) throws Exception;
    
    demoDataElements getDemoFieldDetails(Integer fieldId) throws Exception;
    
    healthDataElements getHealthFieldDetails(Integer fieldId) throws Exception;

    
}
