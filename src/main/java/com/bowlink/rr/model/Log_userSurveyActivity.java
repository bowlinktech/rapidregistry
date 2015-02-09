package com.bowlink.rr.model;

import com.bowlink.rr.validator.NoHtml;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author gchan
 */
@Entity
@Table(name = "Log_userSurveyActivity")
public class Log_userSurveyActivity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;
    
    @Column(name = "systemUserId", nullable = true)
    private int systemUserId = 0;
    
    @Column(name = "surveyId", nullable = true)
    private int surveyId = 0;
    
    @Column(name = "pageId", nullable = true)
    private int pageId = 0;
    
    @Column(name = "questionId", nullable = true)
    private int questionId = 0;
    
    @Column(name = "answerId", nullable = true)
    private int answerId = 0;
    
    @Column(name = "programId", nullable = true)
    private int programId = 0;
    
    @NoHtml
    @Column(name = "activityDesc", nullable = true)
    private String activityDesc;
    
    @NoHtml
    @Column(name = "pageAccessed", nullable = true)
    private String pageAccessed;
   
    @NoHtml
    @Column(name = "controller", nullable = true)
    private String controller;
    
    
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    @Column(name = "DATECREATED", nullable = true)
    private Date dateCreated = new Date();


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
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


	public int getPageId() {
		return pageId;
	}


	public void setPageId(int pageId) {
		this.pageId = pageId;
	}


	public int getQuestionId() {
		return questionId;
	}


	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}


	public int getAnswerId() {
		return answerId;
	}


	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}


	public String getActivityDesc() {
		return activityDesc;
	}


	public void setActivityDesc(String activityDesc) {
		this.activityDesc = activityDesc;
	}


	public String getPageAccessed() {
		return pageAccessed;
	}


	public void setPageAccessed(String pageAccessed) {
		this.pageAccessed = pageAccessed;
	}


	public String getController() {
		return controller;
	}


	public void setController(String controller) {
		this.controller = controller;
	}


	public Date getDateCreated() {
		return dateCreated;
	}


	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}


	public int getProgramId() {
		return programId;
	}


	public void setProgramId(int programId) {
		this.programId = programId;
	}
 
}
