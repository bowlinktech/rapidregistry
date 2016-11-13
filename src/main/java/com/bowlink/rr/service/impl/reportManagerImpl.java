/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bowlink.rr.service.impl;

import com.bowlink.rr.dao.reportDAO;
import com.bowlink.rr.model.programOrgHierarchyDetails;
import com.bowlink.rr.model.programReports;
import com.bowlink.rr.model.reportCrossTab;
import com.bowlink.rr.model.reportCrossTabCWData;
import com.bowlink.rr.model.reportCrossTabEntity;
import com.bowlink.rr.model.reportDetails;
import com.bowlink.rr.model.reportType;
import com.bowlink.rr.model.reports;
import com.bowlink.rr.service.reportManager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
    
    @Override
    @Transactional
    public List<Integer> getProgramReports(Integer programId) throws Exception {
        return reportDAO.getProgramReports(programId);
    }
    
    @Override
    @Transactional
    public void saveProgramReports(programReports report) throws Exception {
        reportDAO.saveProgramReports(report);
    }
    
    @Override
    @Transactional
    public void deleteProgramReports(Integer programId) throws Exception {
        reportDAO.deleteProgramReports(programId);
    }


	@Override
	@Transactional
	public List<reportType> getAllReportTypes() throws Exception {
		return reportDAO.getAllReportTypes();
	}


	@Override
	@Transactional
	public List<reportDetails> getAllForReportType(Integer programId,
			Integer reportTypeId) throws Exception {
		return reportDAO.getAllForReportType(programId, reportTypeId);	
		}


	@Override
	@Transactional
	public reportType getReportTypeById(Integer reportTypeId) throws Exception {
		return reportDAO.getReportTypeById(reportTypeId);
	}


	@Override
	@Transactional
	public reportDetails getReportDetailsById(Integer reportId, boolean aggregated)
			throws Exception {
		return reportDAO.getReportDetailsById(reportId, aggregated);
	}


	@Override
	@Transactional
	public List<reportCrossTab> getCrossTabsByReportId(Integer reportId, List <Integer> statusIds)
			throws Exception {
		return reportDAO.getCrossTabsByReportId(reportId, statusIds);
	}


	@Override
	@Transactional
	public List<reportCrossTabEntity> getCrossTabEntitiesByReportId(
			Integer reportId) throws Exception {
		return reportDAO.getCrossTabEntitiesByReportId(reportId);
	}


	@Override
	@Transactional
	public List<reportCrossTabCWData> getReportCrossTabCWDataByCTId(
			Integer crossTabId) throws Exception {
		return reportDAO.getReportCrossTabCWDataByCTId(crossTabId);
	}


	@Override
	@Transactional
	public List<programOrgHierarchyDetails> getHierarchiesForAggregatedReport(
			Integer hierarchyId, Integer reportId, String matchField) throws Exception {
		return reportDAO.getHierarchiesForAggregatedReport(hierarchyId, reportId, matchField );
	}


	@Override
	@Transactional
	public void updateReportDetails(reportDetails reportDetails)
			throws Exception {
		 reportDAO.updateReportDetails(reportDetails);
		
	}


	@Override
	@Transactional
	public Integer createReportDetails(reportDetails reportDetails)
			throws Exception {
		return reportDAO.createReportDetails(reportDetails);
	}
	
	@Override
	@Transactional
	public reportCrossTab getCrossTabsById (Integer crossTabId) throws Exception {
		return reportDAO.getCrossTabsById (crossTabId);
	}


	@Override
	@Transactional
	public Integer createCrossTabReport(reportCrossTab reportCrossTab)
			throws Exception {
		return reportDAO.createCrossTabReport (reportCrossTab);
	}


	@Override
	@Transactional
	public void updateCrossTabReport(reportCrossTab reportCrossTab)
			throws Exception {
		reportDAO.updateCrossTabReport (reportCrossTab);
	}


	@Override
	@Transactional
	public void deleteCrossTabReport(Integer crossTabId) throws Exception {
		reportDAO.deleteCrossTabReport(crossTabId);
		
	}


	@Override
	@Transactional
	public void deleteCrossTabReportCWDataByCTId(Integer crossTabId)
			throws Exception {
		reportDAO.deleteCrossTabReportCWDataByCTId(crossTabId);
	}

}
