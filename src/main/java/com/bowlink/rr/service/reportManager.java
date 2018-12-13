/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bowlink.rr.service;

import com.bowlink.rr.model.programOrgHierarchyDetails;
import com.bowlink.rr.model.programReports;
import com.bowlink.rr.model.reportCrossTab;
import com.bowlink.rr.model.reportCrossTabCWData;
import com.bowlink.rr.model.reportCrossTabEntity;
import com.bowlink.rr.model.reportDetails;
import com.bowlink.rr.model.reportRequest;
import com.bowlink.rr.model.reportType;
import com.bowlink.rr.model.reports;

import java.util.List;

/**
 *
 * @author chadmccue
 */
public interface reportManager {
    
    Integer createReport(reports report) throws Exception;
    
    List<reports> getAllReports() throws Exception;
    
    reports getReportById(Integer reportId) throws Exception;
    
    void updateReport(reports reportDetails) throws Exception;
    
    List<Integer> getProgramReports(Integer programId) throws Exception;
    
    void saveProgramReports(programReports report) throws Exception;
    
    void deleteProgramReports(Integer programId) throws Exception;
    
    List<reportType> getAllReportTypes() throws Exception;
    
    List<reportDetails> getAggregateReportForReportType(Integer programId, Integer reportTypeId) throws Exception;
    
    reportType getReportTypeById(Integer reportTypeId) throws Exception;
    
    reportDetails getReportDetailsById(Integer reportId) throws Exception;
    
    List <reportCrossTab> getCrossTabsByReportId (Integer reportId, List<Integer> statusIds) throws Exception;
    
    List <reportCrossTabEntity> getCrossTabEntitiesByReportId (Integer reportId) throws Exception;
    
    List<reportCrossTabCWData> getReportCrossTabCWDataByCTId (Integer crossTabId) throws Exception;
    
    List<programOrgHierarchyDetails> getHierarchiesForAggregatedReport (Integer hierarchyId, Integer reportId, String matchField) throws Exception;
    
    void updateReportDetails(reportDetails reportDetails) throws Exception;
    
    Integer createReportDetails(reportDetails reportDetails) throws Exception;
    
    reportCrossTab getCrossTabsById (Integer crossTabId) throws Exception;
    
    Integer createCrossTabReport(reportCrossTab reportCrossTab) throws Exception;
    
    void updateCrossTabForm(reportCrossTab reportCrossTab) throws Exception;
    
    void deleteCrossTabReport(Integer crossTabId) throws Exception;
    
    void deleteCrossTabReportCWDataByCTId (Integer crossTabId) throws Exception;
    
    void createReportCrossTabCWData (reportCrossTabCWData crossTabCWData) throws Exception;
    
    List <String> getCombineCWDataByCTId (Integer crossTabId) throws Exception;
    
    void reportStatusMonitoring (List<Integer> statusList) throws Exception;
    
    List<reportRequest> getReportRequestsByStatus (List<Integer> statusList)  throws Exception;
    
    void sendReportErrorEmail(String subject, String messageBody) throws Exception;
    
    void updateReportRequestStatus(Integer reportRequestId, Integer statusId) throws Exception;
}
