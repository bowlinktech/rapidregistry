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
@Table(name = "programUpload_mcialgorithms")
public class programUpload_MCIalgorithms {
    
    @Transient
    private List<programUpload_MCIFields> fields = null;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;
    
    @Column(name = "programUploadTypeId", nullable = false)
    private Integer programUploadTypeId = null;
    
    @Column(name = "STATUS", nullable = false)
    private boolean status = false;
    
    @Column(name = "ACTION", nullable = false)
    private Integer action = 3;
    
    @Column(name = "mci_CategoryId", nullable = false)
    private Integer mci_CategoryId = 1;
    
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    @Column(name = "DATECREATED", nullable = true)
    private Date dateCreated = new Date();

	public List<programUpload_MCIFields> getFields() {
		return fields;
	}

	public void setFields(List<programUpload_MCIFields> fields) {
		this.fields = fields;
	}

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

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Integer getAction() {
		return action;
	}

	public void setAction(Integer action) {
		this.action = action;
	}

	public Integer getMci_CategoryId() {
		return mci_CategoryId;
	}

	public void setMci_CategoryId(Integer mci_CategoryId) {
		this.mci_CategoryId = mci_CategoryId;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

}