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
 * @author chadmccue
 */
@Entity
@Table(name = "put_algorithms")
public class programUploadTypeAlgorithm {
    
    @Transient
    private List<programUploadTypeAlgorithmFields> fields = null;
    
    @Transient
    private Integer programId = null;
    
    @Transient
    private String actionName  = null;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;
    
    @Column(name = "STATUS", nullable = false)
    private boolean status = false;
    
    @Column(name = "ACTIONId", nullable = false)
    private Integer actionId = 5;
    
    @Column(name = "categoryId", nullable = false)
    private Integer categoryId = 1;
    
    @Column(name = "programUploadTypeId", nullable = false)
    private Integer programUploadTypeId;
    
    @Column(name = "processOrder", nullable = false)
    private Integer processOrder;    
    
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    @Column(name = "DATECREATED", nullable = true)
    private Date dateCreated = new Date();

	public List<programUploadTypeAlgorithmFields> getFields() {
		return fields;
	}

	public void setFields(List<programUploadTypeAlgorithmFields> fields) {
		this.fields = fields;
	}

	public Integer getProgramId() {
		return programId;
	}

	public void setProgramId(Integer programId) {
		this.programId = programId;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Integer getActionId() {
		return actionId;
	}

	public void setActionId(Integer actionId) {
		this.actionId = actionId;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getProgramUploadTypeId() {
		return programUploadTypeId;
	}

	public void setProgramUploadTypeId(Integer programUploadTypeId) {
		this.programUploadTypeId = programUploadTypeId;
	}

	public Integer getProcessOrder() {
		return processOrder;
	}

	public void setProcessOrder(Integer processOrder) {
		this.processOrder = processOrder;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

}
