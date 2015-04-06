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
@Table(name = "programEngagementSection_MCIAlgorithms")
public class programEngagementSection_MCIAlgorithms {
    
    @Transient
    private List<programEngagementSection_mciFields> fields = null;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;
    
    @Transient
    private Integer programId = null;
    
    @Column(name = "STATUS", nullable = false)
    private boolean status = false;
    
    @Column(name = "ACTION", nullable = false)
    private Integer action = 3;
    
    @Column(name = "programEngagementSectionId", nullable = false)
    private Integer programEngagementSectionId;
    
    @Column(name = "processOrder", nullable = false)
    private Integer processOrder = 1;
    
    
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    @Column(name = "DATECREATED", nullable = true)
    private Date dateCreated = new Date();


	public List<programEngagementSection_mciFields> getFields() {
		return fields;
	}


	public void setFields(List<programEngagementSection_mciFields> fields) {
		this.fields = fields;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Integer getProgramId() {
		return programId;
	}


	public void setProgramId(Integer programId) {
		this.programId = programId;
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


	public Integer getProgramEngagementSectionId() {
		return programEngagementSectionId;
	}


	public void setProgramEngagementSectionId(Integer programEngagementSectionId) {
		this.programEngagementSectionId = programEngagementSectionId;
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
