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
@Table(name = "CUSTOMPROGRAMFIELDS")
public class customProgramFields {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;
    
    @Column(name = "PROGRAMID", nullable = false)
    private int programId;
    
    @Column(name = "FIELDNAME", nullable = true)
    private String fieldName = "";
    
    @Column(name = "SAVETOTABLE", nullable = true)
    private String saveToTable = "";
    
    @Column(name = "SAVETOTABLECOL", nullable = true)
    private String saveToTableCol = "";
    
    @Column(name = "ANSWERTYPE", nullable = false) 
    private Integer answerType = 3;
    
    @Column(name = "ALLOWMULTIPLEANS", nullable = false)
    private Boolean allowMultipleAns = false;
    
    @Column(name = "POPULATEFROMTABLE", nullable = true)
    private String populateFromTable = "";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getSaveToTable() {
        return saveToTable;
    }

    public void setSaveToTable(String saveToTable) {
        this.saveToTable = saveToTable;
    }

    public String getSaveToTableCol() {
        return saveToTableCol;
    }

    public void setSaveToTableCol(String saveToTableCol) {
        this.saveToTableCol = saveToTableCol;
    }

    public Integer getAnswerType() {
        return answerType;
    }

    public void setAnswerType(Integer answerType) {
        this.answerType = answerType;
    }

    public Boolean getAllowMultipleAns() {
        return allowMultipleAns;
    }

    public void setAllowMultipleAns(Boolean allowMultipleAns) {
        this.allowMultipleAns = allowMultipleAns;
    }

    public String getPopulateFromTable() {
        return populateFromTable;
    }

    public void setPopulateFromTable(String populateFromTable) {
        this.populateFromTable = populateFromTable;
    }
}
