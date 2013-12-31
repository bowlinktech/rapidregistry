/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ut.dph.model;

import java.util.Date;
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
@Table(name = "CONFIGURATIONCONNECTIONS")
public class configurationConnection {
    
    @Transient
    private configuration srcConfigDetails = null;
    
    @Transient
    private configuration tgtConfigDetails = null;
    
    @Transient
    private String targetOrgName = null;
    
    @Transient
    private int targetOrgId = 0;
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;

    @Column(name = "SOURCECONFIGID", nullable = false)
    private int sourceConfigId;
    
    @Column(name = "TARGETCONFIGID", nullable = false)
    private int targetConfigId;
    
    @Column(name = "DATECREATED", nullable = true)
    private Date dateCreated = new Date();

    @Column(name = "STATUS", nullable = false)
    private boolean status = true;
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getsourceConfigId() {
        return sourceConfigId;
    }

    public void setsourceConfigId(int sourceConfigId) {
        this.sourceConfigId = sourceConfigId;
    }
    
    public int gettargetConfigId() {
        return targetConfigId;
    }

    public void settargetConfigId(int targetConfigId) {
        this.targetConfigId = targetConfigId;
    }
    
    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    public configuration getsrcConfigDetails() {
        return srcConfigDetails;
    }
    
    public void setsrcConfigDetails(configuration srcConfigDetails) {
        this.srcConfigDetails = srcConfigDetails;
    }
    
    public configuration gettgtConfigDetails() {
        return tgtConfigDetails;
    }
    
    public void settgtConfigDetails(configuration tgtConfigDetails) {
        this.tgtConfigDetails = tgtConfigDetails;
    }
    
    public String gettargetOrgName() {
        return targetOrgName;
    }
    
    public void settargetOrgName(String targetOrgName) {
        this.targetOrgName = targetOrgName;
    }
    
    public int gettargetOrgId() {
        return targetOrgId;
    }
    
    public void settargetOrgId(int targetOrgId) {
        this.targetOrgId = targetOrgId;
    }
    
}