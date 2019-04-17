/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.model;

import com.bowlink.rr.validator.NoHtml;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author gchan
 */
@Entity
@Table(name = "put_sftpInfo")
public class programUploadTypeSFTPInfo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;
    
    @Column(name = "PROGRAMID", nullable = false)
    private Integer programId = 0;
    
    @NotEmpty
    @NoHtml
    @Column(name = "username", nullable = false)
    private String username;
    
    @NotEmpty
    @NoHtml
    @Column(name = "password", nullable = false)
    private String password;
    
    @NotEmpty
    @NoHtml
    @Column(name = "sftpPath", nullable = false)
    private String sftpPath;

    @Column(name = "programUploadTypeId", nullable = false)
    private Integer programUploadTypeId = 0;
    
    @Column(name = "STATUS", nullable = false)
    private boolean status = true;
    
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    @Column(name = "DATECREATED", nullable = true)
    private Date dateCreated = new Date();

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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSftpPath() {
		return sftpPath;
	}

	public void setSftpPath(String sftpPath) {
		this.sftpPath = sftpPath;
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

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

}
