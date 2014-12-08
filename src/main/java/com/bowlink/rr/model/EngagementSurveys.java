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
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author gchan
 */
@Entity
@Table(name = "engagementSurveys")
public class EngagementSurveys {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;
    
    @Column(name = "engagementId", nullable = false)
    private int engagementId;
    
    @Column(name = "programPatientId", nullable = false)
    private int programPatientId;
    
    @Column(name = "surveyId", nullable = false)
    private int surveyId;
    
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    @Column(name = "dateStarted", nullable = true)
    private Date dateStarted = new Date();

    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    @Column(name = "dateCompleted", nullable = true)
    private Date dateCompleted = new Date();
    
    @Column(name = "systemUserId", nullable = false)
    private int systemUserId;
    
    @Column(name = "lastAnsQuestionId", nullable = false)
    private int lastAnsQuestionId;
    
    @Column(name = "completed", nullable = false)
    private boolean completed;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEngagementId() {
		return engagementId;
	}

	public void setEngagementId(int engagementId) {
		this.engagementId = engagementId;
	}

	public int getProgramPatientId() {
		return programPatientId;
	}

	public void setProgramPatientId(int programPatientId) {
		this.programPatientId = programPatientId;
	}

	public int getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(int surveyId) {
		this.surveyId = surveyId;
	}

	public Date getDateStarted() {
		return dateStarted;
	}

	public void setDateStarted(Date dateStarted) {
		this.dateStarted = dateStarted;
	}

	public Date getDateCompleted() {
		return dateCompleted;
	}

	public void setDateCompleted(Date dateCompleted) {
		this.dateCompleted = dateCompleted;
	}

	public int getSystemUserId() {
		return systemUserId;
	}

	public void setSystemUserId(int systemUserId) {
		this.systemUserId = systemUserId;
	}

	public int getLastAnsQuestionId() {
		return lastAnsQuestionId;
	}

	public void setLastAnsQuestionId(int lastAnsQuestionId) {
		this.lastAnsQuestionId = lastAnsQuestionId;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

}
