/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bowlink.rr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author chadmccue
 */
@Entity
@Table(name = "environmentalstrategyquestions")
public class environmentalstrategyquestions {
   
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int id;
    
    @Column(name = "programId", nullable = false)
    private int programId;
    
    @Column(name = "environmentalStrategy", nullable = false)
    private String environmentalStrategy;
    
    @Column(name = "question", nullable = false)
    private String question;
    
    @Column(name = "required", nullable = false)
    private boolean required = false;
    
    @Column(name = "validationId", nullable = false)
    private int validationId;
    
    @Column(name = "copyToReach", nullable = false)
    private boolean copyToReach = false;
    
    @Column(name = "copyToCount", nullable = false)
    private boolean copyToCount = false;
    
    @Column(name = "defaultValue", nullable = true)
    private String defaultValue = "";
    
    @Column(name = "qTag", nullable = true)
    private String qTag  = "";
    
    @Column(name = "sumForCount", nullable = false)
    private boolean sumForCount = false;
    
    @Column(name = "sumForReach", nullable = false)
    private boolean sumForReach = false;
    
    @Column(name = "maxFieldValue", nullable = false)
    private int maxFieldValue = 0;
     
    @Column(name = "maxCharactersAllowed", nullable = false)
    private int maxCharactersAllowed = 0;

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

    public String getEnvironmentalStrategy() {
        return environmentalStrategy;
    }

    public void setEnvironmentalStrategy(String environmentalStrategy) {
        this.environmentalStrategy = environmentalStrategy;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public int getValidationId() {
        return validationId;
    }

    public void setValidationId(int validationId) {
        this.validationId = validationId;
    }

    public boolean isCopyToReach() {
        return copyToReach;
    }

    public void setCopyToReach(boolean copyToReach) {
        this.copyToReach = copyToReach;
    }

    public boolean isCopyToCount() {
        return copyToCount;
    }

    public void setCopyToCount(boolean copyToCount) {
        this.copyToCount = copyToCount;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getqTag() {
        return qTag;
    }

    public void setqTag(String qTag) {
        this.qTag = qTag;
    }

    public boolean isSumForCount() {
        return sumForCount;
    }

    public void setSumForCount(boolean sumForCount) {
        this.sumForCount = sumForCount;
    }

    public boolean isSumForReach() {
        return sumForReach;
    }

    public void setSumForReach(boolean sumForReach) {
        this.sumForReach = sumForReach;
    }

    public int getMaxFieldValue() {
        return maxFieldValue;
    }

    public void setMaxFieldValue(int maxFieldValue) {
        this.maxFieldValue = maxFieldValue;
    }

    public int getMaxCharactersAllowed() {
        return maxCharactersAllowed;
    }

    public void setMaxCharactersAllowed(int maxCharactersAllowed) {
        this.maxCharactersAllowed = maxCharactersAllowed;
    }
    
    
    
}
