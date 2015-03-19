/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.model;

import com.bowlink.rr.validator.NoHtml;

import java.util.Date;
import java.util.List;

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
 * @author chadmccue
 */
@Entity
@Table(name = "programUploads")
public class programUploads {
    
	@Transient
    private programUploadTypes progUploadType;
    
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;
    
    @Column(name = "PROGRAMID", nullable = false)
    private Integer programId = null;
    
    @Column(name = "programUploadTypeId", nullable = false)
    private Integer programUploadTypeId = null;
    
    @Column(name = "systemUserId", nullable = false)
    private Integer systemUserId = null;
    
    @Column(name = "totalRows", nullable = false)
    private Integer totalRows = 0;
    
    @Column(name = "totalInError", nullable = false)
    private Integer totalInError = 0;
    
    @NotEmpty
    @NoHtml
    @Column(name = "assignedId", nullable = false)
    private String assignedId = "";
    
    @Column(name = "statusId", nullable = false)
    private Integer statusId = null;
    
    @NotEmpty
    @NoHtml
    @Column(name = "assignedFileName", nullable = true)
    private String assignedFileName = "";
    
    @NotEmpty
    @NoHtml
    @Column(name = "uploadedFileName", nullable = true)
    private String uploadedFileName = "";
    
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    @Column(name = "dateUploaded", nullable = true)
    private Date dateUploaded = new Date();

    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    @Column(name = "DATECREATED", nullable = true)
    private Date dateCreated = new Date();

    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    @Column(name = "statusDateTime", nullable = true)
    private Date statusDateTime = new Date();

	public programUploadTypes getProgUploadType() {
		return progUploadType;
	}

	public void setProgUploadType(programUploadTypes progUploadType) {
		this.progUploadType = progUploadType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getProgramId() {
		return programId;
	}

	public void setProgramId(Integer programId) {
		this.programId = programId;
	}

	public Integer getProgramUploadTypeId() {
		return programUploadTypeId;
	}

	public void setProgramUploadTypeId(Integer programUploadTypeId) {
		this.programUploadTypeId = programUploadTypeId;
	}

	public Integer getSystemUserId() {
		return systemUserId;
	}

	public void setSystemUserId(Integer systemUserId) {
		this.systemUserId = systemUserId;
	}

	public Integer getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}

	public Integer getTotalInError() {
		return totalInError;
	}

	public void setTotalInError(Integer totalInError) {
		this.totalInError = totalInError;
	}

	public String getAssignedId() {
		return assignedId;
	}

	public void setAssignedId(String assignedId) {
		this.assignedId = assignedId;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public String getAssignedFileName() {
		return assignedFileName;
	}

	public void setAssignedFileName(String assignedFileName) {
		this.assignedFileName = assignedFileName;
	}

	public String getUploadedFileName() {
		return uploadedFileName;
	}

	public void setUploadedFileName(String uploadedFileName) {
		this.uploadedFileName = uploadedFileName;
	}

	public Date getDateUploaded() {
		return dateUploaded;
	}

	public void setDateUploaded(Date dateUploaded) {
		this.dateUploaded = dateUploaded;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getStatusDateTime() {
		return statusDateTime;
	}

	public void setStatusDateTime(Date statusDateTime) {
		this.statusDateTime = statusDateTime;
	}
		
}
