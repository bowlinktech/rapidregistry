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
@Table(name = "programUploads")
public class programUploads {
    
	@Transient
    processStatus processStatus;
	
	@Transient
	Integer patientMCIRecords;
	
	@Transient
	Integer engagementMCIRecords;
	
	@Transient 
	String systemUserName;
	
	@Transient
    private String encryptedId = null;

    @Transient
    private String encryptedSecret = null;
    
    @Transient
    private String formDateUpload = null;
    
    
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;
    
    @Column(name = "helBatchUploadId", nullable = true)
    private Integer helBatchUploadId = 0;
    
    @Column(name = "PROGRAMID", nullable = false)
    private Integer programId = null;
    
    @Column(name = "PROGRAMUPLOADTYPEID", nullable = false)
    private Integer programUploadTypeId = null;
    
    @Column(name = "uploadedAsProgramUploadTypeId", nullable = true)
    private Integer uploadedAsProgramUploadTypeId = null;
    
    @Column(name = "SYSTEMUSERID", nullable = false)
    private Integer systemUserId = null;
    
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    @Column(name = "DATEUPLOADED", nullable = true)
    private Date dateUploaded = new Date();
    
    @Column(name = "TOTALROWS", nullable = false)
    private Integer totalRows = 0;
    
    @Column(name = "TOTALINERROR", nullable = false)
    private Integer totalInError = 0;
    
    @Column(name = "uploadedFileName", nullable = true)
    private String uploadedFileName;

    @Column(name = "assignedFileName", nullable = true)
    private String assignedFileName;

    @Column(name = "inFileExt", nullable = true)
    private String inFileExt;
    
    @Column(name = "outFileExt", nullable = true)
    private String outFileExt;
    
    
    @Column(name = "statusId", nullable = false)
    private Integer statusId = 0;
    
    @Column(name = "transportId", nullable = false)
    private Integer transportId = 0;
    
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    @Column(name = "statusDateTime", nullable = true)
    private Date statusDateTime = new Date();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getHelBatchUploadId() {
		return helBatchUploadId;
	}

	public void setHelBatchUploadId(Integer helBatchUploadId) {
		this.helBatchUploadId = helBatchUploadId;
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

	public Date getDateUploaded() {
		return dateUploaded;
	}

	public void setDateUploaded(Date dateUploaded) {
		this.dateUploaded = dateUploaded;
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

	public String getUploadedFileName() {
		return uploadedFileName;
	}

	public void setUploadedFileName(String uploadedFileName) {
		this.uploadedFileName = uploadedFileName;
	}

	public String getAssignedFileName() {
		return assignedFileName;
	}

	public void setAssignedFileName(String assignedFileName) {
		this.assignedFileName = assignedFileName;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public Integer getTransportId() {
		return transportId;
	}

	public void setTransportId(Integer transportId) {
		this.transportId = transportId;
	}

	public Date getStatusDateTime() {
		return statusDateTime;
	}

	public void setStatusDateTime(Date statusDateTime) {
		this.statusDateTime = statusDateTime;
	}

	public processStatus getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(processStatus processStatus) {
		this.processStatus = processStatus;
	}

	public String getInFileExt() {
		return inFileExt;
	}

	public void setInFileExt(String inFileExt) {
		this.inFileExt = inFileExt;
	}

	public String getOutFileExt() {
		return outFileExt;
	}

	public void setOutFileExt(String outFileExt) {
		this.outFileExt = outFileExt;
	}

	public Integer getUploadedAsProgramUploadTypeId() {
		return uploadedAsProgramUploadTypeId;
	}

	public void setUploadedAsProgramUploadTypeId(
			Integer uploadedAsProgramUploadTypeId) {
		this.uploadedAsProgramUploadTypeId = uploadedAsProgramUploadTypeId;
	}

	public Integer getPatientMCIRecords() {
		return patientMCIRecords;
	}

	public void setPatientMCIRecords(Integer patientMCIRecords) {
		this.patientMCIRecords = patientMCIRecords;
	}

	public Integer getEngagementMCIRecords() {
		return engagementMCIRecords;
	}

	public void setEngagementMCIRecords(Integer engagementMCIRecords) {
		this.engagementMCIRecords = engagementMCIRecords;
	}

	public String getSystemUserName() {
		return systemUserName;
	}

	public void setSystemUserName(String systemUserName) {
		this.systemUserName = systemUserName;
	}

	public String getEncryptedId() {
		return encryptedId;
	}

	public void setEncryptedId(String encryptedId) {
		this.encryptedId = encryptedId;
	}

	public String getEncryptedSecret() {
		return encryptedSecret;
	}

	public void setEncryptedSecret(String encryptedSecret) {
		this.encryptedSecret = encryptedSecret;
	}

	public String getFormDateUpload() {
		return formDateUpload;
	}

	public void setFormDateUpload(String formDateUpload) {
		this.formDateUpload = formDateUpload;
	}

}
