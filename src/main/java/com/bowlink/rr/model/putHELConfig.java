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
 * @author gchan
 */

@Entity
@Table(name = "put_helconfigs")
public class putHELConfig {
    
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;
    
    @Column(name = "programUploadTypeId", nullable = false)
    private Integer programUploadTypeId = null;
    
    @Column(name = "helConfigId", nullable = false)
    private Integer helConfigId = null;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getProgramUploadTypeId() {
		return programUploadTypeId;
	}

	public void setProgramUploadTypeId(Integer programUploadTypeId) {
		this.programUploadTypeId = programUploadTypeId;
	}

	public Integer getHelConfigId() {
		return helConfigId;
	}

	public void setHelConfigId(Integer helConfigId) {
		this.helConfigId = helConfigId;
	}

}
