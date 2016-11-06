package com.bowlink.rr.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "reportcrosstab")
public class reportCrossTab {
	
	@Transient
	List <reportCrossTabCWData> combineCWDataId;
	
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
    
    @Column(name = "tableTitle", nullable = true)
    private String tableTitle;

	public List<reportCrossTabCWData> getCombineCWDataId() {
		return combineCWDataId;
	}

	public void setCombineCWDataId(List<reportCrossTabCWData> combineCWDataId) {
		this.combineCWDataId = combineCWDataId;
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

	public String getTableTitle() {
		return tableTitle;
	}

	public void setTableTitle(String tableTitle) {
		this.tableTitle = tableTitle;
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

}
