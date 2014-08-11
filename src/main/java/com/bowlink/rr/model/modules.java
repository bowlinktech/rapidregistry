/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bowlink.rr.model;

import com.bowlink.rr.validator.NoHtml;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author chadmccue
 */
@Entity
@Table(name = "LU_PROGRAMMODULES")
public class modules {
    
    @Transient
    private boolean useModule = false;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;
    
    @NotEmpty
    @NoHtml
    @Column(name = "MODULENAME", nullable = false)
    private String moduleName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
    
    public boolean getUseModule() {
        return useModule;
    }

    public void setUseModule(boolean useModule) {
        this.useModule = useModule;
    }
    
}
