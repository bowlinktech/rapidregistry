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


@Entity
@Table(name = "reportcrosstabcwdata")
public class reportCrossTabCWData {
    
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;
	
	@Column(name = "reportCrossTabId", nullable = false)
    private int reportCrossTabId = 0;
    
    @Column(name = "combineCWDataId", nullable = false)
    private String combineCWDataId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getReportCrossTabId() {
		return reportCrossTabId;
	}

	public void setReportCrossTabId(int reportCrossTabId) {
		this.reportCrossTabId = reportCrossTabId;
	}

	public String getCombineCWDataId() {
		return combineCWDataId;
	}

	public void setCombineCWDataId(String combineCWDataId) {
		this.combineCWDataId = combineCWDataId;
	}

}
