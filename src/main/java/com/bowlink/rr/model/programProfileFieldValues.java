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
@Table(name = "PROGRAMPROFILES_FIELDVALUES")
public class programProfileFieldValues {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;
    
    @Column(name = "PROGRAMPROFILEFIELDID", nullable = false)
    private int programProfileFieldId;
    
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

    public int getProgramProfileFieldId() {
        return programProfileFieldId;
    }

    public void setProgramProfileFieldId(int programProfileFieldId) {
        this.programProfileFieldId = programProfileFieldId;
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
