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
 * @author gchan
 */
@Entity
@Table(name = "programUpload_errors")
public class programUpload_Errors {
   
	@Transient 
	private String errorDesc;
	
	@Transient 
	private String errorDisplayText;
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;
    
    @Column(name = "programUploadId", nullable = false)
    private Integer programUploadId = null;
    
    @Column(name = "engagementId", nullable = false)
    private Integer engagementId = null;
    
    @Column(name = "errorId", nullable = false)
    private Integer errorId = null;
    
    @Column(name = "fieldNo", nullable = false)
    private Integer fieldNo = null;
    
    @Column(name = "errorData", nullable = true)
    private String errorData;
    
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    @Column(name = "DATECREATED", nullable = true)
    private Date dateCreated = new Date();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getProgramUploadId() {
		return programUploadId;
	}

	public void setProgramUploadId(Integer programUploadId) {
		this.programUploadId = programUploadId;
	}

	public Integer getEngagementId() {
		return engagementId;
	}

	public void setEngagementId(Integer engagementId) {
		this.engagementId = engagementId;
	}

	public Integer getErrorId() {
		return errorId;
	}

	public void setErrorId(Integer errorId) {
		this.errorId = errorId;
	}

	public Integer getFieldNo() {
		return fieldNo;
	}

	public void setFieldNo(Integer fieldNo) {
		this.fieldNo = fieldNo;
	}

	public String getErrorData() {
		return errorData;
	}

	public void setErrorData(String errorData) {
		this.errorData = errorData;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	public String getErrorDisplayText() {
		return errorDisplayText;
	}

	public void setErrorDisplayText(String errorDisplayText) {
		this.errorDisplayText = errorDisplayText;
	}

}
