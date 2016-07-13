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
import org.hibernate.validator.constraints.NotEmpty;

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
    
    @NotEmpty
    @NoHtml
    @Column(name = "ELEMENTNAME", nullable = false)
    private String elementName;
    
    @NotEmpty
    @NoHtml
    @Column(name = "SAVETOTABLENAME", nullable = false)
    private String saveToTableName;
    
    @NotEmpty
    @NoHtml
    @Column(name = "SAVETOTABLECOL", nullable = false)
    private String saveToTableCol;
    
    @Column(name = "STATUS", nullable = false)
    private boolean status = false;
    
    @Column(name = "ANSWERTYPE", nullable = false) 
    private Integer answerType = 3;
    
    @Column(name = "POPULATEFROMTABLE", nullable = true)
    private String populateFromTable;
    
    @Column(name = "ALLOWMULTIPLEANS", nullable = false)
    private Boolean allowMultipleAns = false;

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

    public Integer getAnswerType() {
        return answerType;
    }

    public void setAnswerType(Integer answerType) {
        this.answerType = answerType;
    }

    public String getPopulateFromTable() {
        return populateFromTable;
    }

    public void setPopulateFromTable(String populateFromTable) {
        this.populateFromTable = populateFromTable;
    }

    public Boolean getAllowMultipleAns() {
        return allowMultipleAns;
    }

    public void setAllowMultipleAns(Boolean allowMultipleAns) {
        this.allowMultipleAns = allowMultipleAns;
    }
    
}
