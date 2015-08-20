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
@Table(name = "DOCUMENTFOLDERS")
public class documentFolder {
    
    @Transient
    private String encryptedId = null;

    @Transient
    private String encryptedSecret = null;
    
    @Transient
    private List<documentFolder> subfolders = null;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;
    
    @Column(name = "PROGRAMID", nullable = false)
    private int programId;
    
    @Column(name = "SYSTEMUSERID", nullable = false)
    private int systemUserId;
    
    @Column(name = "FOLDERNAME", nullable = false)
    private String folderName = "";
    
    @Column(name = "COUNTYFOLDER", nullable = false)
    private Boolean countyFolder = false;
    
    @Column(name = "ADMINONLY", nullable = false)
    private Boolean adminOnly = false;
    
    @Column(name = "PARENTFOLDERID", nullable = false)
    private Integer parentFolderId = 0;
    
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    @Column(name = "DATECREATED", nullable = true)
    private Date dateCreated = new Date();

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

    public int getSystemUserId() {
        return systemUserId;
    }

    public void setSystemUserId(int systemUserId) {
        this.systemUserId = systemUserId;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public Boolean getCountyFolder() {
        return countyFolder;
    }

    public void setCountyFolder(Boolean countyFolder) {
        this.countyFolder = countyFolder;
    }

    public Boolean getAdminOnly() {
        return adminOnly;
    }

    public void setAdminOnly(Boolean adminOnly) {
        this.adminOnly = adminOnly;
    }

    public Integer getParentFolderId() {
        return parentFolderId;
    }

    public void setParentFolderId(Integer parentFolderId) {
        this.parentFolderId = parentFolderId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getEncryptedId() {
        return encryptedId;
    }

    public void setEncryptedId(String encryptedId) {
        this.encryptedId = encryptedId;
    }

    public String getEncryptedSecret() {
        return encryptedSecret;
    }

    public void setEncryptedSecret(String encryptedSecret) {
        this.encryptedSecret = encryptedSecret;
    }

    public List<documentFolder> getSubfolders() {
        return subfolders;
    }

    public void setSubfolders(List<documentFolder> subfolders) {
        this.subfolders = subfolders;
    }
    
}
