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

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.bowlink.rr.validator.NoHtml;

/**
 *
 * @author chadmccue
 */
@Entity
@Table(name = "SURVEYS")
public class surveys {
    
    @Transient
    private List<SurveyPages> surveyPages;

    
    @Transient
    private String encryptedId = null;

    @Transient
    private String encryptedSecret = null;

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

    @NotEmpty
    @NoHtml
    @Column(name = "TITLE", nullable = false)
    private String title = "";

    @NotEmpty
    @NoHtml
    @Column(name = "DONEBUTTONTEXT", nullable = false)
    private String doneButtonText = "Done";

    @NotEmpty
    @NoHtml
    @Column(name = "NEXTBUTTONTEXT", nullable = false)
    private String nextButtonText = "Next";

    @NotEmpty
    @NoHtml
    @Column(name = "PREVBUTTONTEXT", nullable = false)
    private String prevButtonText = "Previous";

    @Column(name = "REMINDERSTATUS", nullable = false)
    private boolean reminderStatus = false;

    @NoHtml
    @Column(name = "REMINDERTEXT", nullable = true)
    private String reminderText = "";

    @Column(name = "DONEBTNACTIVITYCODEID", nullable = true)
    private Integer doneBtnActivityCodeId = 0;

    @Column(name = "NEXTBTNACTIVITYCODEID", nullable = true)
    private Integer nextBtnActivityCodeId = 0;

    @Column(name = "PREVBTNACTIVITYCODEID", nullable = true)
    private Integer prevBtnActivityCodeId = 0;

    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    @Column(name = "DATECREATED", nullable = true)
    private Date dateCreated = new Date();

    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    @Column(name = "DATEMODIFIED", nullable = true)
    private Date dateModified = new Date();
    
    @Column(name = "associateToProgram", nullable = false)
    private boolean associateToProgram = false;
    
    @Column(name = "surveyTag", nullable = false)
    private String surveyTag = "";
    
    @Column(name = "showInLeftCol", nullable = false)
    private boolean showInLeftCol = true;

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

    public Integer getdoneBtnActivityCodeId() {
        return doneBtnActivityCodeId;
    }

    public void setdoneBtnActivityCodeId(Integer doneBtnActivityCodeId) {
        this.doneBtnActivityCodeId = doneBtnActivityCodeId;
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

    public String getEncryptedId() {
        return encryptedId;
    }

    public void setEncryptedId(String encryptedId) {
        this.encryptedId = encryptedId;
    }

    public List<SurveyPages> getSurveyPages() {
        return surveyPages;
    }

    public void setSurveyPages(List<SurveyPages> surveyPages) {
        this.surveyPages = surveyPages;
    }

    public Integer getNextBtnActivityCodeId() {
        return nextBtnActivityCodeId;
    }

    public void setNextBtnActivityCodeId(Integer nextBtnActivityCodeId) {
        this.nextBtnActivityCodeId = nextBtnActivityCodeId;
    }

    public Integer getPrevBtnActivityCodeId() {
        return prevBtnActivityCodeId;
    }

    public void setPrevBtnActivityCodeId(Integer prevBtnActivityCodeId) {
        this.prevBtnActivityCodeId = prevBtnActivityCodeId;
    }

    public String getEncryptedSecret() {
        return encryptedSecret;
    }

    public void setEncryptedSecret(String encryptedSecret) {
        this.encryptedSecret = encryptedSecret;
    }

    public Integer getDoneBtnActivityCodeId() {
        return doneBtnActivityCodeId;
    }

    public void setDoneBtnActivityCodeId(Integer doneBtnActivityCodeId) {
        this.doneBtnActivityCodeId = doneBtnActivityCodeId;
    }

    public boolean isAssociateToProgram() {
        return associateToProgram;
    }

    public void setAssociateToProgram(boolean associateToProgram) {
        this.associateToProgram = associateToProgram;
    }

    public String getSurveyTag() {
        return surveyTag;
    }

    public void setSurveyTag(String surveyTag) {
        this.surveyTag = surveyTag;
    }

    public boolean isShowInLeftCol() {
        return showInLeftCol;
    }

    public void setShowInLeftCol(boolean showInLeftCol) {
        this.showInLeftCol = showInLeftCol;
    }
    
    
}
