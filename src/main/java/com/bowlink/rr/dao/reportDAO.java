/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bowlink.rr.dao;

import com.bowlink.rr.model.programReports;
import com.bowlink.rr.model.reports;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author chadmccue
 */
@Repository
public interface reportDAO {
    
    Integer createReport(reports report) throws Exception;
    
    List<reports> getAllReports() throws Exception;
    
    reports getReportById(Integer reportId) throws Exception;
    
    void updateReport(reports reportDetails) throws Exception;
    
    List<Integer> getProgramReports(Integer programId) throws Exception;
    
    void saveProgramReports(programReports report) throws Exception;
    
    void deleteProgramReports(Integer programId) throws Exception;
    
}
