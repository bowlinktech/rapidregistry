/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bowlink.rr.model;

import java.util.Date;
import java.util.Map;
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
@Table(name = "PROGRAMPROFILES_FIELDS")
public class programProfileFields {
    
    @Transient
    private String fieldName, cwName, validationName;
    
    @Transient
    private Integer searchDspPos = 0, sectionId = 0;
    
    @Transient
    private boolean autoPopulate, searchField = false, 
            summaryField = false, hideField = false, readOnly = false;
    
    @Transient
    Map<String, String> defaultValues;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;
    
    @Column(name = "PROGRAMID", nullable = false)
    private int programId;
    
    @Column(name = "FIELDID", nullable = false)
    private int fieldId = 0;
    
    @Column(name = "REQUIREDFIELD", nullable = false)
    private boolean requiredField = false;
    
    @Column(name = "VALIDATIONID", nullable = false)
    private int validationId = 1;
    
    @Column(name = "CROSSWALKID", nullable = true)
    private int crosswalkId = 0;
    
    @Column(name = "DSPPOS", nullable = true)
    private int dspPos = 1;
    
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    @Column(name = "DATECREATED", nullable = true)
    private Date dateCreated = new Date();
    
    @Column(name = "FIELDDISPLAYNAME", nullable = true)
    private String fieldDisplayname = "";
    
    @Column(name = "DEFAULTVALUE", nullable = true)
    private String defaultValue = "";
    
    @Column(name = "CUSTOMFIELDID", nullable = false)
    private int customfieldId = 0;
    
    @Column(name = "MINFIELDVALUE", nullable = false)
    private int minFieldValue = 0;
    
    @Column(name = "MAXFIELDVALUE", nullable = false)
    private int maxFieldValue = 0;
    
    @Column(name = "FIELDTAG", nullable = true)
    private String fieldTag = "";
    
    @Column(name = "dataGridColumn", nullable = false)
    private boolean dataGridColumn = false;
    
    @Column(name = "summaryFieldDisplayName", nullable = true)
    private String summaryFieldDisplayName = "";
    
    @Column(name = "fieldHelp", nullable = true)
    private String fieldHelp = "";
    
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

    public int getFieldId() {
        return fieldId;
    }

    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }

    public boolean isRequiredField() {
        return requiredField;
    }

    public void setRequiredField(boolean requiredField) {
        this.requiredField = requiredField;
    }

    public int getValidationId() {
        return validationId;
    }

    public void setValidationId(int validationId) {
        this.validationId = validationId;
    }

    public int getCrosswalkId() {
        return crosswalkId;
    }

    public void setCrosswalkId(int crosswalkId) {
        this.crosswalkId = crosswalkId;
    }

    public int getDspPos() {
        return dspPos;
    }

    public void setDspPos(int dspPos) {
        this.dspPos = dspPos;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getCwName() {
        return cwName;
    }

    public void setCwName(String cwName) {
        this.cwName = cwName;
    }

    public String getValidationName() {
        return validationName;
    }

    public void setValidationName(String validationName) {
        this.validationName = validationName;
    }

    public String getFieldDisplayname() {
        return fieldDisplayname;
    }

    public void setFieldDisplayname(String fieldDisplayname) {
        this.fieldDisplayname = fieldDisplayname;
    }

    public boolean isAutoPopulate() {
        return autoPopulate;
    }

    public void setAutoPopulate(boolean autoPopulate) {
        this.autoPopulate = autoPopulate;
    }
    
    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public int getCustomfieldId() {
        return customfieldId;
    }

    public void setCustomfieldId(int customfieldId) {
        this.customfieldId = customfieldId;
    }

    public int getMinFieldValue() {
        return minFieldValue;
    }

    public void setMinFieldValue(int minFieldValue) {
        this.minFieldValue = minFieldValue;
    }

    public int getMaxFieldValue() {
        return maxFieldValue;
    }

    public void setMaxFieldValue(int maxFieldValue) {
        this.maxFieldValue = maxFieldValue;
    }

    public Map<String, String> getDefaultValues() {
        return defaultValues;
    }

    public void setDefaultValues(Map<String, String> defaultValues) {
        this.defaultValues = defaultValues;
    }

    public boolean isDataGridColumn() {
        return dataGridColumn;
    }

    public void setDataGridColumn(boolean dataGridColumn) {
        this.dataGridColumn = dataGridColumn;
    }

    public boolean isSearchField() {
        return searchField;
    }

    public void setSearchField(boolean searchField) {
        this.searchField = searchField;
    }

    public boolean isSummaryField() {
        return summaryField;
    }

    public void setSummaryField(boolean summaryField) {
        this.summaryField = summaryField;
    }

    public boolean isHideField() {
        return hideField;
    }

    public void setHideField(boolean hideField) {
        this.hideField = hideField;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public Integer getSearchDspPos() {
        return searchDspPos;
    }

    public void setSearchDspPos(Integer searchDspPos) {
        this.searchDspPos = searchDspPos;
    }

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    public String getFieldTag() {
        return fieldTag;
    }

    public void setFieldTag(String fieldTag) {
        this.fieldTag = fieldTag;
    }

    public String getSummaryFieldDisplayName() {
        return summaryFieldDisplayName;
    }

    public void setSummaryFieldDisplayName(String summaryFieldDisplayName) {
        this.summaryFieldDisplayName = summaryFieldDisplayName;
    }

    public String getFieldHelp() {
        return fieldHelp;
    }

    public void setFieldHelp(String fieldHelp) {
        this.fieldHelp = fieldHelp;
    }
    
    
}
