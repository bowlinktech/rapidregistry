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

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.bowlink.rr.validator.NoHtml;

/**
 *
 * @author gchan
 */
@Entity
@Table(name = "patientActivities")
public class patientActivities {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;
    
    @NotEmpty
    @Column(name = "programPatientId", nullable = false)
    private int programPatientId;
   
    @NotEmpty
    @Column(name = "engagementId", nullable = false)
    private int engagementId;
    
    @NotEmpty
    @Column(name = "activityCodeId", nullable = false)
    private int activityCodeId;
    
    @NoHtml
    @Column(name = "activityCode", nullable = false)
    private String activityCode;
    
    @Column(name = "desc", nullable = false)
    private String desc;
    
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    @Column(name = "DATECREATED", nullable = true)
    private Date dateCreated = new Date();
    
    @NotEmpty
    @Column(name = "systemUserId", nullable = false)
    private int systemUserId;
    
    @Column(name = "surveyId", nullable = false)
    private int surveyId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProgramPatientId() {
		return programPatientId;
	}

	public void setProgramPatientId(int programPatientId) {
		this.programPatientId = programPatientId;
	}

	public int getEngagementId() {
		return engagementId;
	}

	public void setEngagementId(int engagementId) {
		this.engagementId = engagementId;
	}

	public int getActivityCodeId() {
		return activityCodeId;
	}

	public void setActivityCodeId(int activityCodeId) {
		this.activityCodeId = activityCodeId;
	}

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public int getSystemUserId() {
		return systemUserId;
	}

	public void setSystemUserId(int systemUserId) {
		this.systemUserId = systemUserId;
	}

	public int getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(int surveyId) {
		this.surveyId = surveyId;
	}
    
}
