package com.bowlink.rr.model;

import java.util.List;

import com.bowlink.rr.validator.NoHtml;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author gchan
 */
@Entity
@Table(name = "lu_algorithmCategories")
public class algorithmCategories {
	
	@Transient
	private List<programUploadTypeAlgorithm> algorithms;
	
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;
    
    @NotEmpty
    @NoHtml
    @Column(name = "mapToTable", nullable = false)
    private String mapToTable;
    
    @NotEmpty
    @NoHtml
    @Column(name = "mapToColumn", nullable = false)
    private String mapToColumn;

    @NotEmpty
    @NoHtml
    @Column(name = "displayText", nullable = false)
    private String displayText;
    
    @Column(name = "STATUS", nullable = false)
    private Boolean status = true;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMapToTable() {
		return mapToTable;
	}

	public void setMapToTable(String mapToTable) {
		this.mapToTable = mapToTable;
	}

	public String getMapToColumn() {
		return mapToColumn;
	}

	public void setMapToColumn(String mapToColumn) {
		this.mapToColumn = mapToColumn;
	}

	public String getDisplayText() {
		return displayText;
	}

	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public List<programUploadTypeAlgorithm> getAlgorithms() {
		return algorithms;
	}

	public void setAlgorithms(List<programUploadTypeAlgorithm> algorithms) {
		this.algorithms = algorithms;
	}
}
