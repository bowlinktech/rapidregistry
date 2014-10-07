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
@Table(name = "DATAELEMENTS")
public class dataElements {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;
    
    @Column(name = "ELEMENTNAME", nullable = false)
    private String elementName;
    
    @Column(name = "SAVETOTABLENAME", nullable = false)
    private String saveToTableName;
    
    @Column(name = "SAVETOTABLECOL", nullable = false)
    private String saveToTableCol;
    
    @Column(name = "STATUS", nullable = false)
    private boolean status = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    public String getSaveToTableName() {
        return saveToTableName;
    }

    public void setSaveToTableName(String saveToTableName) {
        this.saveToTableName = saveToTableName;
    }

    public String getSaveToTableCol() {
        return saveToTableCol;
    }

    public void setSaveToTableCol(String saveToTableCol) {
        this.saveToTableCol = saveToTableCol;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    
}
