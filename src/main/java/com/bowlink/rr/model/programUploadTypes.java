/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.model;

import com.bowlink.rr.validator.NoHtml;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author chadmccue
 */
@Entity
@Table(name = "PROGRAMUPLOADTYPES")
public class programUploadTypes {
    
    @Transient
    private int totalFields = 0;
    
    @Transient
    private String inFileExt;
    
    @Transient
    private String outFileExt;
	
	@Transient
	private String delimChar;
	
	@Transient
	private List<algorithmCategories> algorithmCategories;
	
	@Transient
	private List<putHELConfig> helConfigs;
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private int id;
    
    @Column(name = "PROGRAMID", nullable = false)
    private Integer programId = null;
    
    @NotEmpty
    @NoHtml
    @Column(name = "NAME", nullable = false)
    private String name;
    
    @DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    @Column(name = "DATECREATED", nullable = true)
    private Date dateCreated = new Date();
    
    @Column(name = "STATUS", nullable = false)
    private boolean status = true;
    
    @Column(name = "useHEL", nullable = false)
    private boolean useHEL = false;
    
    @NoHtml
    @Column(name = "HELPickUpPath", nullable = true)
    private String helPickUpPath;
    
    @NoHtml
    @Column(name = "HELDropPath", nullable = true)
    private String helDropPath;
    
    @Column(name = "inFileTypeId", nullable = false)
    private Integer inFileTypeId = 1;
    
    @Column(name = "outFileTypeId", nullable = false)
    private Integer outFileTypeId = 2;
    
    
    @Column(name = "MAXFILESIZE", nullable = false)
    private int maxFileSize = 0;
    
    @Column(name = "fileDelimId", nullable = false)
    private int fileDelimId = 1;
    
    @Column(name = "encodingId", nullable = false)
    private int encodingId = 1;
    
    @Column(name = "containsHeaderRow", nullable = false)
    private boolean containsHeaderRow = false;
    

	public int getTotalFields() {
		return totalFields;
	}

	public void setTotalFields(int totalFields) {
		this.totalFields = totalFields;
	}

	public String getInFileExt() {
		return inFileExt;
	}

	public void setInFileExt(String inFileExt) {
		this.inFileExt = inFileExt;
	}

	public String getOutFileExt() {
		return outFileExt;
	}

	public void setOutFileExt(String outFileExt) {
		this.outFileExt = outFileExt;
	}

	public String getDelimChar() {
		return delimChar;
	}

	public void setDelimChar(String delimChar) {
		this.delimChar = delimChar;
	}

	public List<algorithmCategories> getAlgorithmCategories() {
		return algorithmCategories;
	}

	public void setAlgorithmCategories(List<algorithmCategories> algorithmCategories) {
		this.algorithmCategories = algorithmCategories;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getProgramId() {
		return programId;
	}

	public void setProgramId(Integer programId) {
		this.programId = programId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean isUseHEL() {
		return useHEL;
	}

	public void setUseHEL(boolean useHEL) {
		this.useHEL = useHEL;
	}

	public String getHelPickUpPath() {
		return helPickUpPath;
	}

	public void setHelPickUpPath(String helPickUpPath) {
		this.helPickUpPath = helPickUpPath;
	}

	public String getHelDropPath() {
		return helDropPath;
	}

	public void setHelDropPath(String helDropPath) {
		this.helDropPath = helDropPath;
	}

	public Integer getInFileTypeId() {
		return inFileTypeId;
	}

	public void setInFileTypeId(Integer inFileTypeId) {
		this.inFileTypeId = inFileTypeId;
	}

	public int getMaxFileSize() {
		return maxFileSize;
	}

	public void setMaxFileSize(int maxFileSize) {
		this.maxFileSize = maxFileSize;
	}

	public int getFileDelimId() {
		return fileDelimId;
	}

	public void setFileDelimId(int fileDelimId) {
		this.fileDelimId = fileDelimId;
	}

	public int getEncodingId() {
		return encodingId;
	}

	public void setEncodingId(int encodingId) {
		this.encodingId = encodingId;
	}

	public Integer getOutFileTypeId() {
		return outFileTypeId;
	}

	public void setOutFileTypeId(Integer outFileTypeId) {
		this.outFileTypeId = outFileTypeId;
	}

	public boolean isContainsHeaderRow() {
		return containsHeaderRow;
	}

	public void setContainsHeaderRow(boolean containsHeaderRow) {
		this.containsHeaderRow = containsHeaderRow;
	}

	public List<putHELConfig> getHelConfigs() {
		return helConfigs;
	}

	public void setHelConfigs(List<putHELConfig> helConfigs) {
		this.helConfigs = helConfigs;
	}

}
