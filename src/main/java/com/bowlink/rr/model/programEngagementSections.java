/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.model;

import com.bowlink.rr.validator.NoHtml;

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

/**
 *
 * @author chadmccue
 */
@Entity
@Table(name = "PROGRAM_ENGAGEMENTSECTIONS")
public class programEngagementSections {
    
	@Transient
	private List<programEngagementSection_MCIAlgorithms> mciAlgorithms;
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;
    
    @Column(name = "PROGRAMID", nullable = false)
    private int programId;
    
    @Column(name = "STATUS", nullable = false)
    private boolean status = true;
    
    @NotEmpty
    @NoHtml
    @Column(name = "SECTIONNAME", nullable = true)
    private String sectionName = "";
    
    @Column(name = "DSPPOS", nullable = true)
    private int dspPos = 1;
    
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    @Column(name = "DATECREATED", nullable = true)
    private Date dateCreated = new Date();

	public List<programEngagementSection_MCIAlgorithms> getMciAlgorithms() {
		return mciAlgorithms;
	}

	public void setMciAlgorithms(List<programEngagementSection_MCIAlgorithms> mciAlgorithms) {
		this.mciAlgorithms = mciAlgorithms;
	}

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

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
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

}
