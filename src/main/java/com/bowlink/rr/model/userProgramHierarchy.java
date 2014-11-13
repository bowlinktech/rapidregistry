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
@Table(name = "USER_AUTHORIZEDORGHIERARCHY")
public class userProgramHierarchy {
    
    @Transient
    private String hierarchyName;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;
    
    @Column(name = "SYSTEMUSERID", nullable = false)
    private Integer systemUserId = null;
    
    @Column(name = "PROGRAMID", nullable = false)
    private Integer programId = null;
    
    @Column(name = "PROGRAMHIERARCHYID", nullable = false)
    private Integer programHierarchyId = null;
    
    @Column(name = "ORGHIERARCHYDETAILID", nullable = false)
    private Integer orgHierarchyDetailId = null;
    
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    @Column(name = "DATECREATED", nullable = true)
    private Date dateCreated = new Date(); 

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getSystemUserId() {
        return systemUserId;
    }

    public void setSystemUserId(Integer systemUserId) {
        this.systemUserId = systemUserId;
    }

    public Integer getProgramId() {
        return programId;
    }

    public void setProgramId(Integer programId) {
        this.programId = programId;
    }

    public Integer getProgramHierarchyId() {
        return programHierarchyId;
    }

    public void setProgramHierarchyId(Integer programHierarchyId) {
        this.programHierarchyId = programHierarchyId;
    }

    public Integer getOrgHierarchyDetailId() {
        return orgHierarchyDetailId;
    }

    public void setOrgHierarchyDetailId(Integer orgHierarchyDetailId) {
        this.orgHierarchyDetailId = orgHierarchyDetailId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getHierarchyName() {
        return hierarchyName;
    }

    public void setHierarchyName(String hierarchyName) {
        this.hierarchyName = hierarchyName;
    }
    
   
}
