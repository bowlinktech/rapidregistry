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
@Table(name = "put_formfields")
public class programUploadTypesFormFields {
    
    @Transient
    private String fieldName;
    
    @Transient
    private String validationName;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;
    
    @Column(name = "PROGRAMUPLOADTYPEID", nullable = false)
    private Integer programUploadTypeId;
    
    @Column(name = "FIELDID", nullable = false)
    private Integer fieldId;
    
    @Column(name = "REQUIREDFIELD", nullable = false)
    private boolean requiredField = false;
    
    @Column(name = "VALIDATIONID", nullable = false)
    private int validationId = 1;
    
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    @Column(name = "DATECREATED", nullable = false)
    private Date dateCreated = new Date();
    
    @Column(name = "DSPPOS", nullable = false)
    private int dspPos = 1;
    
    @Column(name = "useField", nullable = false)
    private boolean useField = true;
    
    @Column(name = "multiValue", nullable = false)
    private boolean multiValue = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getProgramUploadTypeId() {
        return programUploadTypeId;
    }

    public void setProgramUploadTypeId(Integer programUploadTypeId) {
        this.programUploadTypeId = programUploadTypeId;
    }

    public Integer getFieldId() {
        return fieldId;
    }

    public void setFieldId(Integer fieldId) {
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

    public String getValidationName() {
        return validationName;
    }

    public void setValidationName(String validationName) {
        this.validationName = validationName;
    }

    public int getDspPos() {
        return dspPos;
    }

    public void setDspPos(int dspPos) {
        this.dspPos = dspPos;
    }

	public boolean isUseField() {
		return useField;
	}

	public void setUseField(boolean useField) {
		this.useField = useField;
	}

	public boolean isMultiValue() {
		return multiValue;
	}

	public void setMultiValue(boolean multiValue) {
		this.multiValue = multiValue;
	}

}
