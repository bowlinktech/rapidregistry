/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bowlink.rr.model.User;

/**
 *
 * @author gchan
 */
@Entity
@Table(name = "reportRequestReports")
public class reportRequestReport {
	
	
	//BIRT file name
	@Transient
    private String rptDesignFile;

	@Transient
	private String reportName;
	
	@Transient
    private List <Integer> submittedSurveyIds;
	
	@Transient
	private String reportHeader, reportFooter;
	
	@Transient
	private User userDetails;
	
	@Transient
	private reportDetails reportDetails;
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;
    
    @Column(name = "reportId", nullable = false)
    private int reportId = 0;
    
    @Column(name = "reportRequestId", nullable = false)
    private int reportRequestId = 0;
    
    @Column(name = "reportFileName", nullable = true)
    private String reportFileName;
    
    @Column(name = "startProcessTime", nullable = true)
    private Date startProcessTime = null;

    @Column(name = "endProcessTime", nullable = true)
    private Date endProcessTime = null;
    
    @Column(name = "statusId", nullable = false)
    private int statusId = 1;

	public String getRptDesignFile() {
		return rptDesignFile;
	}

	public void setRptDesignFile(String rptDesignFile) {
		this.rptDesignFile = rptDesignFile;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getReportId() {
		return reportId;
	}

	public void setReportId(int reportId) {
		this.reportId = reportId;
	}

	public int getReportRequestId() {
		return reportRequestId;
	}

	public void setReportRequestId(int reportRequestId) {
		this.reportRequestId = reportRequestId;
	}

	public String getReportFileName() {
		return reportFileName;
	}

	public void setReportFileName(String reportFileName) {
		this.reportFileName = reportFileName;
	}

	public Date getStartProcessTime() {
		return startProcessTime;
	}

	public void setStartProcessTime(Date startProcessTime) {
		this.startProcessTime = startProcessTime;
	}

	public Date getEndProcessTime() {
		return endProcessTime;
	}

	public void setEndProcessTime(Date endProcessTime) {
		this.endProcessTime = endProcessTime;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public List<Integer> getSubmittedSurveyIds() {
		return submittedSurveyIds;
	}

	public void setSubmittedSurveyIds(List<Integer> submittedSurveyIds) {
		this.submittedSurveyIds = submittedSurveyIds;
	}

	public String getReportHeader() {
		return reportHeader;
	}

	public void setReportHeader(String reportHeader) {
		this.reportHeader = reportHeader;
	}

	public String getReportFooter() {
		return reportFooter;
	}

	public void setReportFooter(String reportFooter) {
		this.reportFooter = reportFooter;
	}

	public User getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(User userDetails) {
		this.userDetails = userDetails;
	}

	public reportDetails getReportDetails() {
		return reportDetails;
	}

	public void setReportDetails(reportDetails reportDetails) {
		this.reportDetails = reportDetails;
	}

}
