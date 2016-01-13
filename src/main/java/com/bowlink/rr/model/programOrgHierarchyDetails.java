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
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author chadmccue
 */
@Entity
@Table(name = "PROGRAMORGHIERARCHY_DETAILS")
public class programOrgHierarchyDetails {
    
    @Transient
    private Boolean isAssociated = false, createFolders = false;
    
    @Transient
    private String encryptedId = null;

    @Transient
    private String encryptedSecret = null;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;
    
    @Column(name = "PROGRAMHIERARCHYID", nullable = false)
    private int programHierarchyId;
    
    @Column(name = "STATUS", nullable = true)
    private boolean status = true;
    
    @NotEmpty
    @Column(name = "NAME", nullable = false)
    private String name;
    
    @Column(name = "ADDRESS", nullable = true)
    private String address = "";
    
    @Column(name = "ADDRESS2", nullable = true)
    private String address2 = "";
    
    @Column(name = "CITY", nullable = true)
    private String city = "";
    
    @Column(name = "STATE", nullable = true)
    private String state = "";
    
    @Column(name = "ZIPCODE", nullable = true)
    private String zipCode = "";
    
    @Column(name = "phoneNumber", nullable = true)
    private String phoneNumber = "";
    
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    @Column(name = "DATECREATED", nullable = true)
    private Date dateCreated = new Date();
    
    @NotEmpty
    @Column(name = "displayId", nullable = true)
    private String displayId = "99999";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProgramHierarchyId() {
        return programHierarchyId;
    }

    public void setProgramHierarchyId(int programHierarchyId) {
        this.programHierarchyId = programHierarchyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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

    public Boolean getIsAssociated() {
        return isAssociated;
    }

    public void setIsAssociated(Boolean isAssociated) {
        this.isAssociated = isAssociated;
    }

    public Boolean getCreateFolders() {
        return createFolders;
    }

    public void setCreateFolders(Boolean createFolders) {
        this.createFolders = createFolders;
    }
    
    /**
     * @return the displayId
     */
    public String getDisplayId() {
        return displayId;
    }

    /**
     * @param displayId the displayId to set
     */
    public void setDisplayId(String displayId) {
        this.displayId = displayId;
    }
    
}
