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
import javax.persistence.Transient;

/**
 *
 * @author chadmccue
 */
@Entity
@Table(name = "put_AlgorithmFields")
public class programUploadTypeAlgorithmFields {
    
    @Transient
    private String fieldName = null;
    
    @Transient
    private dataElements dataElement;
    
    @Transient 
    private programUploadTypesFormFields putField;
     
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;
    
    @Column(name = "algorithmId", nullable = false)
    private Integer algorithmId = null;
    
    @Column(name = "putFormFieldId", nullable = false)
    private Integer putFormFieldId = null;
    
    @Column(name = "ACTION", nullable = false)
    private String action = "equals";
    
    @Column(name = "ACTIONSQL", nullable = false)
    private String actionSQL = "=";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

	public Integer getAlgorithmId() {
		return algorithmId;
	}

	public void setAlgorithmId(Integer algorithmId) {
		this.algorithmId = algorithmId;
	}

	public dataElements getDataElement() {
		return dataElement;
	}

	public void setDataElement(dataElements dataElement) {
		this.dataElement = dataElement;
	}

	public String getActionSQL() {
		return actionSQL;
	}

	public void setActionSQL(String actionSQL) {
		this.actionSQL = actionSQL;
	}

	public programUploadTypesFormFields getPutField() {
		return putField;
	}

	public void setPutField(programUploadTypesFormFields putField) {
		this.putField = putField;
	}

	public Integer getPutFormFieldId() {
		return putFormFieldId;
	}

	public void setPutFormFieldId(Integer putFormFieldId) {
		this.putFormFieldId = putFormFieldId;
	}

}
