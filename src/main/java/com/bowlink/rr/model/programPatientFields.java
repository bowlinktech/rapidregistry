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
@Table(name = "PROGRAM_PATIENTFIELDS")
public class programPatientFields {
    
    @Transient
    private String fieldName;
    
    @Transient
    private String cwName;
    
    @Transient
    private String validationName;
    
    @Transient
    private boolean autoPopulate;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;
    
    @Column(name = "PROGRAMID", nullable = false)
    private int programId;
    
    @Column(name = "SECTIONID", nullable = false)
    private int sectionId = 0;
    
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
    
    @Column(name = "DATAGRIDCOLUMN", nullable = false)
    private boolean dataGridColumn = false;
    
    @Column(name = "SEARCHFIELD", nullable = false)
    private boolean searchField = false;
    
    @Column(name = "SUMMARYFIELD", nullable = false)
    private boolean summaryField = false;
    
    @Column(name = "SEARCHDSPPOS", nullable = true)
    private int searchDspPos = 1;
    
    @Column(name = "HIDEFIELD", nullable = false)
    private boolean hideField = false;

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

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
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

    public int getSearchDspPos() {
        return searchDspPos;
    }

    public void setSearchDspPos(int searchDspPos) {
        this.searchDspPos = searchDspPos;
    }
    
    public boolean isAutoPopulate() {
        return autoPopulate;
    }

    public void setAutoPopulate(boolean autoPopulate) {
        this.autoPopulate = autoPopulate;
    }
    
    public boolean isHideField() {
        return hideField;
    }

    public void setHideField(boolean hideField) {
        this.hideField = hideField;
    }
    
}
