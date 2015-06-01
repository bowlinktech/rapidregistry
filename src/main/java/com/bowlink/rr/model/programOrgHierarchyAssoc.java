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
@Table(name = "PROGRAMORGHIERARCHY_ASSOC")
public class programOrgHierarchyAssoc {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;
    
    @Column(name = "PROGRAMHIERARCHYID", nullable = false)
    private int programHierarchyId;
   
    @Column(name = "ASSOCIATEDWITH", nullable = false)
    private int associatedWith;
    
    @Column(name = "DSPPOS", nullable = true)
    private int dspPos = 1;

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

    public int getAssociatedWith() {
        return associatedWith;
    }

    public void setAssociatedWith(int associatedWith) {
        this.associatedWith = associatedWith;
    }

    public int getDspPos() {
        return dspPos;
    }

    public void setDspPos(int dspPos) {
        this.dspPos = dspPos;
    }
    
}
