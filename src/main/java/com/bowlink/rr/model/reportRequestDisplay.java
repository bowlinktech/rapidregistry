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
@Table(name = "reportRequestDisplay")
public class reportRequestDisplay {

    @Transient
    private String encryptedId = null;

    @Transient
    private String encryptedSecret = null;
    
    @Transient
    private reportRequest reportRequest = null;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;

    @Column(name = "programId", nullable = false)
    private int programId = 0;

    @Column(name = "reportRequestId", nullable = false)
    private int reportRequestId = 0;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(name = "dateSubmitted", nullable = false)
    private Date dateSubmitted;

    @Column(name = "ReportType", nullable = false)
    private String ReportType;

    @Column(name = "ReportNames", nullable = true)
    private String ReportNames;

    @Column(name = "Entity3Names", nullable = true)
    private String entity3Names;

    @Column(name = "statusId", nullable = false)
    private int statusId = 0;

    @Column(name = "statusDisplay", nullable = true)
    private String statusDisplay;

    @Column(name = "systemUserId", nullable = false)
    private int systemUserId = 0;

    @Column(name = "firstName", nullable = true)
    private String firstName;

    @Column(name = "lastName", nullable = true)
    private String lastName;

    @Column(name = "datesRequested", nullable = true)
    private String datesRequested = null;

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

    public String getEntity3Names() {
        return entity3Names;
    }

    public void setEntity3Names(String entity3Names) {
        this.entity3Names = entity3Names;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getSystemUserId() {
        return systemUserId;
    }

    public void setSystemUserId(int systemUserId) {
        this.systemUserId = systemUserId;
    }

    public String getDatesRequested() {
        return datesRequested;
    }

    public void setDatesRequested(String datesRequested) {
        this.datesRequested = datesRequested;
    }

    public String getStatusDisplay() {
        return statusDisplay;
    }

    public void setStatusDisplay(String statusDisplay) {
        this.statusDisplay = statusDisplay;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getReportRequestId() {
        return reportRequestId;
    }

    public void setReportRequestId(int reportRequestId) {
        this.reportRequestId = reportRequestId;
    }

    public String getReportType() {
        return ReportType;
    }

    public void setReportType(String reportType) {
        ReportType = reportType;
    }

    public String getReportNames() {
        return ReportNames;
    }

    public void setReportNames(String reportNames) {
        ReportNames = reportNames;
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

    public Date getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(Date dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

	public reportRequest getReportRequest() {
		return reportRequest;
	}

	public void setReportRequest(reportRequest reportRequest) {
		this.reportRequest = reportRequest;
	}

}
