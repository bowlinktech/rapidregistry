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
@Table(name = "PROGRAM_PATIENTFIELDVALUES")
public class programPatientFieldValues {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;
    
    @Column(name = "PATIENTFIELDID", nullable = false)
    private int patientFieldId;
    
    @Column(name = "VALUEID", nullable = false)
    private int valueId;
    
    @Column(name = "VALUEDISPLAYTEXT", nullable = false)
    private String valueDisplayText;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getpatientFieldId() {
        return patientFieldId;
    }

    public void setpatientFieldId(int patientFieldId) {
        this.patientFieldId = patientFieldId;
    }

    public int getValueId() {
        return valueId;
    }

    public void setValueId(int valueId) {
        this.valueId = valueId;
    }

    public String getValueDisplayText() {
        return valueDisplayText;
    }

    public void setValueDisplayText(String valueDisplayText) {
        this.valueDisplayText = valueDisplayText;
    }
    
    
}
