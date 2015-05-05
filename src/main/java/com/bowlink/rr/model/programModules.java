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
import javax.persistence.Transient;
/**
 *
 * @author chadmccue
 */
@Entity
@Table(name = "PROGRAM_MODULES")
public class programModules {
    
    @Transient
    private boolean useModule = false;
    
    @Transient
    private boolean allowEdit, allowCreate, allowDelete, allowLevel1, allowLevel2, allowLevel3, allowReconcile, allowImport, allowExport = false;
    
    @Transient
    private String displayName;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;
    
    @Column(name = "PROGRAMID", nullable = false)
    private Integer programId = null;
    
    @Column(name = "MODULEID", nullable = false)
    private Integer moduleId = null;
    
    @Column(name = "DSPPOS", nullable = true)
    private int dspPos = 1;
    
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

    public int getDspPos() {
        return dspPos;
    }

    public void setDspPos(int dspPos) {
        this.dspPos = dspPos;
    }

    public boolean isUseModule() {
        return useModule;
    }

    public void setUseModule(boolean useModule) {
        this.useModule = useModule;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isAllowEdit() {
        return allowEdit;
    }

    public void setAllowEdit(boolean allowEdit) {
        this.allowEdit = allowEdit;
    }

    public boolean isAllowCreate() {
        return allowCreate;
    }

    public void setAllowCreate(boolean allowCreate) {
        this.allowCreate = allowCreate;
    }

    public boolean isAllowDelete() {
        return allowDelete;
    }

    public void setAllowDelete(boolean allowDelete) {
        this.allowDelete = allowDelete;
    }

    public boolean isAllowLevel1() {
        return allowLevel1;
    }

    public void setAllowLevel1(boolean allowLevel1) {
        this.allowLevel1 = allowLevel1;
    }

    public boolean isAllowLevel2() {
        return allowLevel2;
    }

    public void setAllowLevel2(boolean allowLevel2) {
        this.allowLevel2 = allowLevel2;
    }

    public boolean isAllowLevel3() {
        return allowLevel3;
    }

    public void setAllowLevel3(boolean allowLevel3) {
        this.allowLevel3 = allowLevel3;
    }

    public boolean isAllowReconcile() {
        return allowReconcile;
    }

    public void setAllowReconcile(boolean allowReconcile) {
        this.allowReconcile = allowReconcile;
    }

    public boolean isAllowImport() {
        return allowImport;
    }

    public void setAllowImport(boolean allowImport) {
        this.allowImport = allowImport;
    }

    public boolean isAllowExport() {
        return allowExport;
    }

    public void setAllowExport(boolean allowExport) {
        this.allowExport = allowExport;
    }
    
    
    
}
