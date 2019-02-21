/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bowlink.rr.service.impl;

import com.bowlink.rr.dao.reportDAO;
import com.bowlink.rr.model.mailMessage;
import com.bowlink.rr.model.programOrgHierarchyDetails;
import com.bowlink.rr.model.programReports;
import com.bowlink.rr.model.reportCrossTab;
import com.bowlink.rr.model.reportCrossTabCWData;
import com.bowlink.rr.model.reportCrossTabEntity;
import com.bowlink.rr.model.reportDetails;
import com.bowlink.rr.model.reportRequest;
import com.bowlink.rr.model.reportType;
import com.bowlink.rr.model.reports;
import com.bowlink.rr.service.emailMessageManager;
import com.bowlink.rr.service.reportManager;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class reportManagerImpl implements reportManager {
	
	@Resource(name = "myProps")
    private Properties myProps;

	@Autowired
    reportDAO reportDAO;
    
	@Autowired
    private emailMessageManager emailMessageManager;
	
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
	public List<reportDetails> getAggregateReportForReportType(Integer programId,
			Integer reportTypeId) throws Exception {
		return reportDAO.getAggregateReportForReportType(programId, reportTypeId);	
		}


	@Override
	@Transactional
	public reportType getReportTypeById(Integer reportTypeId) throws Exception {
		return reportDAO.getReportTypeById(reportTypeId);
	}


	@Override
	@Transactional
	public reportDetails getReportDetailsById(Integer reportId)
			throws Exception {
		return reportDAO.getReportDetailsById(reportId);
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
	public void updateCrossTabForm(reportCrossTab reportCrossTab)
			throws Exception {
		reportDAO.updateCrossTabForm (reportCrossTab);
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


	@Override
	@Transactional
	public void createReportCrossTabCWData(reportCrossTabCWData crossTabCWData)
			throws Exception {
		reportDAO.createReportCrossTabCWData(crossTabCWData);
		
	}


	@Override
	@Transactional
	public List<String> getCombineCWDataByCTId(Integer crossTabId)
			throws Exception {
		return reportDAO.getCombineCWDataByCTId(crossTabId);
		
	}


	@Override
	public void reportStatusMonitoring(List<Integer> statusList) throws Exception {
		// 1. first we grab all reportRequest with status of 2
		List <reportRequest> reportList = getReportRequestsByStatus(statusList);
		if (reportList.size() > 0) {
			String messageBody = "<br/><br/>"+ reportList.size() + " report(s) with status of " + statusList;
			messageBody = messageBody + "<br/>" + "The time stamp for the first report is " + reportList.get(0).getStartProcessTime() + ".";
			messageBody = messageBody + "<br/>" + "The programId for the first report is " + reportList.get(0).getProgramId() + ".";
			messageBody = messageBody + "<br/>" + "The Id for the first report is " + reportList.get(0).getId() + ".";
			messageBody = messageBody + "<br/><br/>Please login and run sql to view details. - select * from reportrequests order by id;";
			sendReportErrorEmail((reportList.size() + " report(s) with status of " + statusList), messageBody);
			//update the first report to 7 so it won't email admin again
			updateReportRequestStatus(reportList.get(0).getId(), 7);
		}
		
	}


	@Override
	public List<reportRequest> getReportRequestsByStatus(List<Integer> statusList)
			throws Exception {
		return reportDAO.getReportDetailsByStatus(statusList);
	}
	
	
	@Override
    public void sendReportErrorEmail(String subject, String messageBody) throws Exception {
        // send email with error
        mailMessage messageDetails = new mailMessage();
        messageDetails.settoEmailAddress("monitor@health-e-link.net");
        messageDetails.setfromEmailAddress("support@health-e-link.net");
        messageDetails.setmessageSubject(subject + " " + myProps.getProperty("server.identity"));

        StringBuilder sb = new StringBuilder();
        sb.append(new Date().toString() + "<br/>");
        sb.append(messageBody);
        messageDetails.setmessageBody(sb.toString());
        try {
            emailMessageManager.sendEmail(messageDetails);
        } catch (Exception e) {
            System.err.println("sendReportErrorEmail Error");
            e.printStackTrace();
        }

    }


	@Override
	public void updateReportRequestStatus(Integer reportRequestId, Integer statusId) throws Exception {
		reportDAO.updateReportRequestStatus(reportRequestId, statusId);
	}

}
