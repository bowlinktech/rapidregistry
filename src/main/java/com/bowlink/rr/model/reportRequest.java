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
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author gchan
 */
@Entity
@Table(name = "reportRequests")
public class reportRequest {

    @Transient
    private reportStatus statusInfo;

    @Transient
    private Integer programHeirarchyId, uniqueId;

    @Transient
    private String encryptedId = null, encryptedSecret = null, pathToFolder, createdBy, selectedSites, startDate, endDate, selectedDateRange;

    @Transient
    private List<Integer> reportIds, entity3Ids, codeIds;

    @Transient
    private List<reportRequestReport> reportRequestReports;

    @Transient
    private reportRequestDisplay reportRequestDisplay;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;

    @Column(name = "programId", nullable = false)
    private int programId = 0;

    @Column(name = "systemUserId", nullable = false)
    private int systemUserId = 0;

    @Column(name = "STARTDATE", nullable = true)
    private Date startDateTime = null;

    @Column(name = "ENDDATE", nullable = true)
    private Date endDateTime = null;

    @Column(name = "startProcessTime", nullable = true)
    private Date startProcessTime = null;

    @Column(name = "endProcessTime", nullable = true)
    private Date endProcessTime = null;

    @Column(name = "statusId", nullable = false)
    private int statusId = 1;

    @Column(name = "reportTypeId", nullable = false)
    private int reportTypeId = 0;

    @Column(name = "fileTypeId", nullable = false)
    private int fileTypeId = 6; //default is PDF

    @Column(name = "notifcationSent", nullable = false)
    private boolean notifcationSent = false;

    @Column(name = "reportFileName", nullable = true)
    private String reportFileName;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(name = "dateCreated", nullable = false)
    private Date dateCreated = new Date();

    @Column(name = "displayCodeOnly", nullable = false)
    private boolean displayCodeOnly = true;
    
    @Column(name = "TOTALRECORDS", nullable = false)
    private Integer totalRecords = 0;


    public reportStatus getStatusInfo() {
        return statusInfo;
    }

    public void setStatusInfo(reportStatus statusInfo) {
        this.statusInfo = statusInfo;
    }

    public Integer getProgramHeirarchyId() {
        return programHeirarchyId;
    }

    public void setProgramHeirarchyId(Integer programHeirarchyId) {
        this.programHeirarchyId = programHeirarchyId;
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

    public List<Integer> getReportIds() {
        return reportIds;
    }

    public void setReportIds(List<Integer> reportIds) {
        this.reportIds = reportIds;
    }

    public List<reportRequestReport> getReportRequestReports() {
        return reportRequestReports;
    }

    public void setReportRequestReports(
            List<reportRequestReport> reportRequestReports) {
        this.reportRequestReports = reportRequestReports;
    }

    public List<Integer> getEntity3Ids() {
        return entity3Ids;
    }

    public void setEntity3Ids(List<Integer> entity3Ids) {
        this.entity3Ids = entity3Ids;
    }

    public String getPathToFolder() {
        return pathToFolder;
    }

    public void setPathToFolder(String pathToFolder) {
        this.pathToFolder = pathToFolder;
    }

    public List<Integer> getCodeIds() {
        return codeIds;
    }

    public void setCodeIds(List<Integer> codeIds) {
        this.codeIds = codeIds;
    }

    public reportRequestDisplay getReportRequestDisplay() {
        return reportRequestDisplay;
    }

    public void setReportRequestDisplay(reportRequestDisplay reportRequestDisplay) {
        this.reportRequestDisplay = reportRequestDisplay;
    }

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

    public int getSystemUserId() {
        return systemUserId;
    }

    public void setSystemUserId(int systemUserId) {
        this.systemUserId = systemUserId;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
    	endDateTime.setHours(23);
    	endDateTime.setMinutes(59);
    	endDateTime.setSeconds(59);
    	this.endDateTime = endDateTime;
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

    public int getReportTypeId() {
        return reportTypeId;
    }

    public void setReportTypeId(int reportTypeId) {
        this.reportTypeId = reportTypeId;
    }

    public int getFileTypeId() {
        return fileTypeId;
    }

    public void setFileTypeId(int fileTypeId) {
        this.fileTypeId = fileTypeId;
    }

    public boolean isNotifcationSent() {
        return notifcationSent;
    }

    public void setNotifcationSent(boolean notifcationSent) {
        this.notifcationSent = notifcationSent;
    }

    public String getReportFileName() {
        return reportFileName;
    }

    public void setReportFileName(String reportFileName) {
        this.reportFileName = reportFileName;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getSelectedSites() {
        return selectedSites;
    }

    public void setSelectedSites(String selectedSites) {
        this.selectedSites = selectedSites;
    }

    public Integer getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(Integer uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getSelectedDateRange() {
        return selectedDateRange;
    }

    public void setSelectedDateRange(String selectedDateRange) {
        this.selectedDateRange = selectedDateRange;
    }

    public boolean isDisplayCodeOnly() {
        return displayCodeOnly;
    }

    public void setDisplayCodeOnly(boolean displayCodeOnly) {
        this.displayCodeOnly = displayCodeOnly;
    }

    public Integer getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Integer totalRecords) {
        this.totalRecords = totalRecords;
    }

    
}
