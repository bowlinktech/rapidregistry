/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author chadmccue
 */
@Entity
@Table(name = "PROGRAMS")
public class program {

    @Transient
    private int totalProgramAdmins = 0;

    @Transient
    private boolean sharing = false;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;

    @Column(name = "STATUS", nullable = false)
    private boolean status = true;

    @NotEmpty
    @NoHtml
    @Column(name = "PROGRAMNAME", nullable = false)
    private String programName;

    @NoHtml
    @Column(name = "PROGRAMDESC", nullable = false)
    private String programDesc;

    @NotEmpty
    @NoHtml
    @Email
    @Column(name = "EMAILADDRESS", nullable = false)
    private String emailAddress;

    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    @Column(name = "DATECREATED", nullable = true)
    private Date dateCreated = new Date();

    @Column(name = "URL", nullable = true)
    private String url;

    @Column(name = "shareAcrossHierarchy", nullable = false)
    private boolean shareAcrossHierarchy = false;

    @Column(name = "visitsPerDay", nullable = false)
    private int visitsPerDay = 1;

    @Column(name = "helOrgId", nullable = false)
    private int helOrgId = 0;

    @Column(name = "knowledgebaseurl", nullable = true)
    private String knowledgebaseurl;

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public boolean getStatus() {
	return status;
    }

    public void setStatus(boolean status) {
	this.status = status;
    }

    public String getProgramName() {
	return programName;
    }

    public void setProgramName(String programName) {
	this.programName = programName;
    }

    public String getProgramDesc() {
	return programDesc;
    }

    public void setProgramDesc(String programDesc) {
	this.programDesc = programDesc;
    }

    public String getEmailAddress() {
	return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
	this.emailAddress = emailAddress;
    }

    public Date getDateCreated() {
	return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
	this.dateCreated = dateCreated;
    }

    public int getTotalProgramAdmins() {
	return totalProgramAdmins;
    }

    public void setTotalProgramAdmins(int totalProgramAdmins) {
	this.totalProgramAdmins = totalProgramAdmins;
    }

    public boolean getSharing() {
	return sharing;
    }

    public void setSharing(boolean sharing) {
	this.sharing = sharing;
    }

    public String getUrl() {
	return url;
    }

    public void setUrl(String url) {
	this.url = url;
    }

    public boolean isShareAcrossHierarchy() {
	return shareAcrossHierarchy;
    }

    public void setShareAcrossHierarchy(boolean shareAcrossHierarchy) {
	this.shareAcrossHierarchy = shareAcrossHierarchy;
    }

    public int getVisitsPerDay() {
	return visitsPerDay;
    }

    public void setVisitsPerDay(int visitsPerDay) {
	this.visitsPerDay = visitsPerDay;
    }

    public int getHelOrgId() {
	return helOrgId;
    }

    public void setHelOrgId(int helOrgId) {
	this.helOrgId = helOrgId;
    }

    public String getKnowledgebaseurl() {
	return knowledgebaseurl;
    }

    public void setKnowledgebaseurl(String knowledgebaseurl) {
	this.knowledgebaseurl = knowledgebaseurl;
    }
    
}
