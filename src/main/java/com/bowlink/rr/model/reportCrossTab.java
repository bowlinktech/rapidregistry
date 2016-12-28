package com.bowlink.rr.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;


@Entity
@Table(name = "reportcrosstab")
public class reportCrossTab {
	
	@Transient
	List <reportCrossTabCWData> combineCWDataList;
	
	@Transient
	List <String> combineCWDataStringList;
	
	@Transient
	List <crosswalkData> cwDataRow;
	
	@Transient
	List <crosswalkData> cwDataCol;
    
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;
	
	@Column(name = "reportId", nullable = false)
    private int reportId = 0;
    
    @Column(name = "cwIdCol", nullable = false)
    private int cwIdCol = 0;
    
    @Column(name = "cwIdRow", nullable = false)
    private int cwIdRow = 0;
    
    @Column(name = "dspPos", nullable = false)
    private int dspPos = 1;
    
    @Column(name = "statusId", nullable = false)
    private int statusId = 1;
    
    @NotEmpty
    @Column(name = "tableTitle", nullable = false)
    private String tableTitle;
    
    @Column(name = "labelRow", nullable = false)
    private String labelRow;
    
    @Column(name = "labelCol", nullable = false)
    private String labelCol;
    

	public List<reportCrossTabCWData> getCombineCWDataList() {
		return combineCWDataList;
	}

	public void setCombineCWDataList(List<reportCrossTabCWData> combineCWDataList) {
		this.combineCWDataList = combineCWDataList;
	}

	public List<crosswalkData> getCwDataRow() {
		return cwDataRow;
	}

	public void setCwDataRow(List<crosswalkData> cwDataRow) {
		this.cwDataRow = cwDataRow;
	}

	public List<crosswalkData> getCwDataCol() {
		return cwDataCol;
	}

	public void setCwDataCol(List<crosswalkData> cwDataCol) {
		this.cwDataCol = cwDataCol;
	}

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

	public int getCwIdCol() {
		return cwIdCol;
	}

	public void setCwIdCol(int cwIdCol) {
		this.cwIdCol = cwIdCol;
	}

	public int getCwIdRow() {
		return cwIdRow;
	}

	public void setCwIdRow(int cwIdRow) {
		this.cwIdRow = cwIdRow;
	}

	public int getDspPos() {
		return dspPos;
	}

	public void setDspPos(int dspPos) {
		this.dspPos = dspPos;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public String getTableTitle() {
		return tableTitle;
	}

	public void setTableTitle(String tableTitle) {
		this.tableTitle = tableTitle;
	}

	public List<String> getCombineCWDataStringList() {
		return combineCWDataStringList;
	}

	public void setCombineCWDataStringList(List<String> combineCWDataStringList) {
		this.combineCWDataStringList = combineCWDataStringList;
	}

	public String getLabelRow() {
		return labelRow;
	}

	public void setLabelRow(String labelRow) {
		this.labelRow = labelRow;
	}

	public String getLabelCol() {
		return labelCol;
	}

	public void setLabelCol(String labelCol) {
		this.labelCol = labelCol;
	}

}
