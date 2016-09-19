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
    
    @Transient
    private String associatedWith = "";
    
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
    
    @Column(name = "website", nullable = true)
    private String website = "";
    
    @Column(name = "email", nullable = true)
    private String email = "";
    
    @Column(name = "primaryContactName", nullable = true)
    private String primaryContactName = "";
    
    @Column(name = "primaryContactPhone", nullable = true)
    private String primaryContactPhone = "";
    
    @Column(name = "primaryContactEmail", nullable = true)
    private String primaryContactEmail = "";
    
    @Column(name = "county", nullable = true)
    private String county = "";
    
    @Column(name = "ReligiousFaithBasedOrganization", nullable = false)
    private boolean ReligiousFaithBasedOrganization = false;
    
    @Column(name = "fundingaffiliations", nullable = false)
    private String fundingaffiliations = "";
    
    @Column(name = "servingTribalPopulations", nullable = false)
    private boolean servingTribalPopulations = false;
    
    @Column(name = "organizationType", nullable = true)
    private String organizationType = "";
    
    @Column(name = "organizationSector", nullable = true)
    private String organizationSector = "";

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

    public String getAssociatedWith() {
        return associatedWith;
    }

    public void setAssociatedWith(String associatedWith) {
        this.associatedWith = associatedWith;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPrimaryContactName() {
        return primaryContactName;
    }

    public void setPrimaryContactName(String primaryContactName) {
        this.primaryContactName = primaryContactName;
    }

    public String getPrimaryContactPhone() {
        return primaryContactPhone;
    }

    public void setPrimaryContactPhone(String primaryContactPhone) {
        this.primaryContactPhone = primaryContactPhone;
    }

    public String getPrimaryContactEmail() {
        return primaryContactEmail;
    }

    public void setPrimaryContactEmail(String primaryContactEmail) {
        this.primaryContactEmail = primaryContactEmail;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public boolean isServingTribalPopulations() {
        return servingTribalPopulations;
    }

    public void setServingTribalPopulations(boolean servingTribalPopulations) {
        this.servingTribalPopulations = servingTribalPopulations;
    }

    public String getOrganizationType() {
        return organizationType;
    }

    public void setOrganizationType(String organizationType) {
        this.organizationType = organizationType;
    }

    public String getOrganizationSector() {
        return organizationSector;
    }

    public void setOrganizationSector(String organizationSector) {
        this.organizationSector = organizationSector;
    }

    public boolean isReligiousFaithBasedOrganization() {
        return ReligiousFaithBasedOrganization;
    }

    public void setReligiousFaithBasedOrganization(boolean ReligiousFaithBasedOrganization) {
        this.ReligiousFaithBasedOrganization = ReligiousFaithBasedOrganization;
    }

    public String getFundingaffiliations() {
        return fundingaffiliations;
    }

    public void setFundingaffiliations(String fundingaffiliations) {
        this.fundingaffiliations = fundingaffiliations;
    }
    
    
    
}
