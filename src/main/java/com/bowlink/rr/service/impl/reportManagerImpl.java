/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bowlink.rr.service.impl;

import com.bowlink.rr.dao.reportDAO;
import com.bowlink.rr.model.reports;
import com.bowlink.rr.service.reportManager;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author chadmccue
 */
@Service
public class reportManagerImpl implements reportManager {
    
    @Autowired
    reportDAO reportDAO;
    
    @Override
    @Transactional
    public Integer createReport(reports report) throws Exception {
        Integer lastId = null;
        lastId = (Integer) reportDAO.createReport(report);
        return lastId;
    }
    
   
    @Override
    @Transactional
    public List<reports> getAllReports() throws Exception {
        return reportDAO.getAllReports();
    }
    
    @Override
    @Transactional
    public reports getReportById(Integer reportId) throws Exception {
        return reportDAO.getReportById(reportId);
    }
    
    @Override
    @Transactional
    public void updateReport(reports reportDetails) throws Exception {
        reportDAO.updateReport(reportDetails);
    }
    
}
