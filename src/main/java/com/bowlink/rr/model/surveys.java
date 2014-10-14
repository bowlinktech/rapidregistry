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
 * @author chadmccue
 */
@Entity
@Table(name = "SURVEYS")
public class surveys {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;
    
    @Column(name = "PROGRAMID", nullable = false)
    private int programId;
    
    @Column(name = "STATUS", nullable = false)
    private boolean status = true;
    
    @Column(name = "REQUIREENGAGEMENT", nullable = false)
    private boolean requireEngagement = true;
    
    @Column(name = "ALLOWENGAGEMENT", nullable = false)
    private boolean allowEngagement = true;
    
    @Column(name = "DUPLICATESALLOWED", nullable = false)
    private boolean duplicatesAllowed = false;
    
    @Column(name = "TITLE", nullable = true)
    private String title = "";
    
    @Column(name = "DONEBUTTONTEXT", nullable = false)
    private String doneButtonText = "Done";
    
    @Column(name = "NEXTBUTTONTEXT", nullable = false)
    private String nextButtonText = "Next";
    
    @Column(name = "PREVBUTTONTEXT", nullable = false)
    private String prevButtonText = "Previous";
    
    @Column(name = "REMINDERSTATUS", nullable = false)
    private boolean reminderStatus = false;
    
    @Column(name = "REMINDERTEXT", nullable = true)
    private String reminderText = "";
    
    @Column(name = "ACTIVITYCODEID", nullable = true)
    private Integer activityCodeId = 0;
    
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    @Column(name = "DATECREATED", nullable = true)
    private Date dateCreated = new Date();
    
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    @Column(name = "DATEMODIFIED", nullable = true)
    private Date dateModified = new Date();
    
    @Column(name = "SURVEYSCOL", nullable = true)
    private String surveysCol = "";

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

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean getRequireEngagement() {
        return requireEngagement;
    }

    public void setRequireEngagement(boolean requireEngagement) {
        this.requireEngagement = requireEngagement;
    }

    public boolean getAllowEngagement() {
        return allowEngagement;
    }

    public void setAllowEngagement(boolean allowEngagement) {
        this.allowEngagement = allowEngagement;
    }

    public boolean getDuplicatesAllowed() {
        return duplicatesAllowed;
    }

    public void setDuplicatesAllowed(boolean duplicatesAllowed) {
        this.duplicatesAllowed = duplicatesAllowed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDoneButtonText() {
        return doneButtonText;
    }

    public void setDoneButtonText(String doneButtonText) {
        this.doneButtonText = doneButtonText;
    }

    public String getNextButtonText() {
        return nextButtonText;
    }

    public void setNextButtonText(String nextButtonText) {
        this.nextButtonText = nextButtonText;
    }

    public String getPrevButtonText() {
        return prevButtonText;
    }

    public void setPrevButtonText(String prevButtonText) {
        this.prevButtonText = prevButtonText;
    }

    public boolean getReminderStatus() {
        return reminderStatus;
    }

    public void setReminderStatus(boolean reminderStatus) {
        this.reminderStatus = reminderStatus;
    }

    public String getReminderText() {
        return reminderText;
    }

    public void setReminderText(String reminderText) {
        this.reminderText = reminderText;
    }

    public Integer getActivityCodeId() {
        return activityCodeId;
    }

    public void setActivityCodeId(Integer activityCodeId) {
        this.activityCodeId = activityCodeId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public String getSurveysCol() {
        return surveysCol;
    }

    public void setSurveysCol(String surveysCol) {
        this.surveysCol = surveysCol;
    }
    
}
