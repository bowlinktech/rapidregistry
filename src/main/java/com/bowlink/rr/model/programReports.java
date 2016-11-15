/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bowlink.rr.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author chadmccue
 */
@Entity
@Table(name = "reportDetails")
public class programReports {
    
	 	@Transient
	    private String reportType;

	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    @Column(name = "ID", nullable = false)
	    private int id;

	    @Column(name = "programId", nullable = false)
	    private int programId = 0;

	    @Column(name = "reportTypeId", nullable = false)
	    private int reportTypeId = 0;

	    @Column(name = "reportName", nullable = true)
	    private String reportName = null;

	    @Column(name = "reportDesc", nullable = true)
	    private String reportDesc = null;

	    @Column(name = "reportFile", nullable = true)
	    private String reportFile = null;

	    @Column(name = "status", nullable = false)
	    private boolean status = false;

	    @Column(name = "surveyId", nullable = true)
	    private int surveyId = 0;

	    @Column(name = "groupByQuestionId", nullable = true)
	    private int groupByQuestionId = 0;

	    @Column(name = "orderByQuestionId", nullable = true)
	    private int orderByQuestionId = 0;

	    @Column(name = "surveyDateQuestionId", nullable = true)
	    private int surveyDateQuestionId = 0;

	    @Column(name = "cssFile", nullable = true)
	    private String cssFile = null;

	    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
	    @Column(name = "dateCreated", nullable = true)
	    private Date dateCreated = new Date();

	    @Column(name = "addSummary", nullable = false)
	    private boolean addSummary = false;

	    @Column(name = "reportLevel", nullable = false)
	    private Integer reportLevel = 3;

	    public int getId() {
	        return id;
	    }

	    public void setId(int id) {
	        this.id = id;
	    }

	    public int getProgramId() {
	        return programId;
	    }

	    public void setProgramId(int programId) {
	        this.programId = programId;
	    }

	    public int getReportTypeId() {
	        return reportTypeId;
	    }

	    public void setReportTypeId(int reportTypeId) {
	        this.reportTypeId = reportTypeId;
	    }

	    public String getReportName() {
	        return reportName;
	    }

	    public void setReportName(String reportName) {
	        this.reportName = reportName;
	    }

	    public String getReportDesc() {
	        return reportDesc;
	    }

	    public void setReportDesc(String reportDesc) {
	        this.reportDesc = reportDesc;
	    }

	    public String getReportFile() {
	        return reportFile;
	    }

	    public void setReportFile(String reportFile) {
	        this.reportFile = reportFile;
	    }

	    public boolean isStatus() {
	        return status;
	    }

	    public void setStatus(boolean status) {
	        this.status = status;
	    }

	    public int getSurveyId() {
	        return surveyId;
	    }

	    public void setSurveyId(int surveyId) {
	        this.surveyId = surveyId;
	    }

	    public int getGroupByQuestionId() {
	        return groupByQuestionId;
	    }

	    public void setGroupByQuestionId(int groupByQuestionId) {
	        this.groupByQuestionId = 0;
	    }

	    public int getOrderByQuestionId() {
	        return orderByQuestionId;
	    }

	    public void setOrderByQuestionId(int orderByQuestionId) {
	        this.orderByQuestionId = orderByQuestionId;
	    }

	    public Date getDateCreated() {
	        return dateCreated;
	    }

	    public void setDateCreated(Date dateCreated) {
	        this.dateCreated = dateCreated;
	    }

	    public int getSurveyDateQuestionId() {
	        return surveyDateQuestionId;
	    }

	    public void setSurveyDateQuestionId(int surveyDateQuestionId) {
	        this.surveyDateQuestionId = surveyDateQuestionId;
	    }

	    public String getReportType() {
	        return reportType;
	    }

	    public void setReportType(String reportType) {
	        this.reportType = reportType;
	    }

	    public String getCssFile() {
	        return cssFile;
	    }

	    public void setCssFile(String cssFile) {
	        this.cssFile = cssFile;
	    }

	    public boolean isAddSummary() {
	        return addSummary;
	    }

	    public void setAddSummary(boolean addSummary) {
	        this.addSummary = addSummary;
	    }

		public Integer getReportLevel() {
			return reportLevel;
		}

		public void setReportLevel(Integer reportLevel) {
			this.reportLevel = reportLevel;
		}
    
}
