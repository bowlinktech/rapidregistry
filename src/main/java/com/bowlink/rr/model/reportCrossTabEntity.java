/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bowlink.rr.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "reportcrosstabentity")
public class reportCrossTabEntity {
    
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;
	
	@Column(name = "reportId", nullable = false)
    private int reportId = 0;
    
    @Column(name = "entity1Id", nullable = false)
    private int entity1Id = 0;
    
    @Column(name = "entity2Id", nullable = false)
    private int entity2Id = 0;
    
    @Column(name = "entity3Id", nullable = false)
    private int entity3Id = 0;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getReportId() {
		return reportId;
	}

	public void setReportId(int reportId) {
		this.reportId = reportId;
	}

	public int getEntity1Id() {
		return entity1Id;
	}

	public void setEntity1Id(int entity1Id) {
		this.entity1Id = entity1Id;
	}

	public int getEntity2Id() {
		return entity2Id;
	}

	public void setEntity2Id(int entity2Id) {
		this.entity2Id = entity2Id;
	}

	public int getEntity3Id() {
		return entity3Id;
	}

	public void setEntity3Id(int entity3Id) {
		this.entity3Id = entity3Id;
	}
    
}
