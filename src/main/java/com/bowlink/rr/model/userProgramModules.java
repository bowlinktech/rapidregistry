/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bowlink.rr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 *
 * @author chadmccue
 */
@Entity
@Table(name = "USER_PROGRAMMODULES")
public class userProgramModules {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;
    
    @Column(name = "SYSTEMUSERID", nullable = false)
    private Integer systemUserId = null;
    
    @Column(name = "PROGRAMID", nullable = false)
    private Integer programId = null;
    
    @Column(name = "MODULEID", nullable = false)
    private Integer moduleId = null;
    
    @Column(name = "ALLOWCREATE", nullable = false)
    private boolean allowCreate = false;
    
    @Column(name = "ALLOWEDIT", nullable = false)
    private boolean allowEdit = false;
    
    @Column(name = "ALLOWDELETE", nullable = false)
    private boolean allowDelete = false;
    
    @Column(name = "ALLOWLEVEL1", nullable = false)
    private boolean allowLevel1 = false;
    
    @Column(name = "ALLOWLEVEL2", nullable = false)
    private boolean allowLevel2 = false;
    
    @Column(name = "ALLOWLEVEL3", nullable = false)
    private boolean allowLevel3 = false;
    
    @Column(name = "ALLOWRECONCILE", nullable = false)
    private boolean allowReconcile = false;
    
    @Column(name = "ALLOWIMPORT", nullable = false)
    private boolean allowImport = false;
    
    @Column(name = "ALLOWEXPORT", nullable = false)
    private boolean allowExport = false;
    

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

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public Integer getSystemUserId() {
        return systemUserId;
    }

    public void setSystemUserId(Integer systemUserId) {
        this.systemUserId = systemUserId;
    }

    public boolean getAllowCreate() {
        return allowCreate;
    }

    public void setAllowCreate(boolean allowCreate) {
        this.allowCreate = allowCreate;
    }

    public boolean getAllowEdit() {
        return allowEdit;
    }

    public void setAllowEdit(boolean allowEdit) {
        this.allowEdit = allowEdit;
    }

    public boolean getAllowDelete() {
        return allowDelete;
    }

    public void setAllowDelete(boolean allowDelete) {
        this.allowDelete = allowDelete;
    }

    public boolean getAllowLevel1() {
        return allowLevel1;
    }

    public void setAllowLevel1(boolean allowLevel1) {
        this.allowLevel1 = allowLevel1;
    }

    public boolean getAllowLevel2() {
        return allowLevel2;
    }

    public void setAllowLevel2(boolean allowLevel2) {
        this.allowLevel2 = allowLevel2;
    }

    public boolean getAllowLevel3() {
        return allowLevel3;
    }

    public void setAllowLevel3(boolean allowLevel3) {
        this.allowLevel3 = allowLevel3;
    }

    public boolean getAllowReconcile() {
        return allowReconcile;
    }

    public void setAllowReconcile(boolean allowReconcile) {
        this.allowReconcile = allowReconcile;
    }

    public boolean getAllowImport() {
        return allowImport;
    }

    public void setAllowImport(boolean allowImport) {
        this.allowImport = allowImport;
    }

    public boolean getAllowExport() {
        return allowExport;
    }

    public void setAllowExport(boolean allowExport) {
        this.allowExport = allowExport;
    }
    
}
