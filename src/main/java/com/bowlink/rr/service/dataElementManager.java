/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bowlink.rr.service;

import com.bowlink.rr.model.crosswalks;
import com.bowlink.rr.model.dataElements;
import java.util.List;

/**
 *
 * @author chadmccue
 */
public interface dataElementManager {
    
    List<dataElements> getdataElements() throws Exception;
    
    List<crosswalks> getCrosswalks(int page, int maxResults, int programId);
    
    Integer createCrosswalk(crosswalks crosswalkDetails);

    Long checkCrosswalkName(String name, int orgId);

    double findTotalCrosswalks(int orgId);

    crosswalks getCrosswalk(int cwId);
    
    @SuppressWarnings("rawtypes")
    List getCrosswalkData(int cwId);
    
    @SuppressWarnings("rawtypes")
    List getDelimiters();
    
    @SuppressWarnings("rawtypes")
    List getValidationTypes();
    
    String getfieldName(int fieldId);
    
    String getCrosswalkName(int cwId);
    
    String getValidationName(int validationId);
    
    @SuppressWarnings("rawtypes")
    List getInformationTables();
    
    @SuppressWarnings("rawtypes")
    List getAllTables();

    @SuppressWarnings("rawtypes")
    List getTableColumns(String tableName);
    
    void saveField(dataElements formField) throws Exception;
    
    dataElements getFieldDetails(Integer fieldId) throws Exception;
    
    @SuppressWarnings("rawtypes")
    List getAnswerTypes();
}
