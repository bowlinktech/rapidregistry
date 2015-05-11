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
    
    @Column(name = "categoryId", nullable = false)
    private Integer categoryId = null;
    
    @Column(name = "errorId", nullable = false)
    private Integer errorId = null;
    
    @Column(name = "fieldId", nullable = false)
    private Integer fieldId = null;
    
    @Column(name = "dspPos", nullable = false)
    private Integer dspPos = null;
    
    
    @Column(name = "errorData", nullable = true)
    private String errorData;
    
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    @Column(name = "DATECREATED", nullable = true)
    private Date dateCreated = new Date();

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

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getErrorId() {
		return errorId;
	}

	public void setErrorId(Integer errorId) {
		this.errorId = errorId;
	}

	public Integer getFieldId() {
		return fieldId;
	}

	public void setFieldId(Integer fieldId) {
		this.fieldId = fieldId;
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

	public Integer getDspPos() {
		return dspPos;
	}

	public void setDspPos(Integer dspPos) {
		this.dspPos = dspPos;
	}
}
