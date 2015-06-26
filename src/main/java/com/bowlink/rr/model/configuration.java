package com.bowlink.rr.model;
/**
 * 
 * @author gchan
 */
public class configuration {

	private Integer orgId = 0;
	private Integer configId = 0;
	private String filelocation;
	private Integer encodingId = 0;
	private Integer fileTypeId;
	private String fileType;
	private String fileExt;
	private Integer maxFileSize;
	private String delimChar;
	
	public Integer getOrgId() {
		return orgId;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	public Integer getConfigId() {
		return configId;
	}
	public void setConfigId(Integer configId) {
		this.configId = configId;
	}
	public String getFilelocation() {
		return filelocation;
	}
	public void setFilelocation(String filelocation) {
		this.filelocation = filelocation;
	}
	public Integer getEncodingId() {
		return encodingId;
	}
	public void setEncodingId(Integer encodingId) {
		this.encodingId = encodingId;
	}
	public Integer getFileTypeId() {
		return fileTypeId;
	}
	public void setFileTypeId(Integer fileTypeId) {
		this.fileTypeId = fileTypeId;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFileExt() {
		return fileExt;
	}
	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}
	public Integer getMaxFileSize() {
		return maxFileSize;
	}
	public void setMaxFileSize(Integer maxFileSize) {
		this.maxFileSize = maxFileSize;
	}
	public String getDelimChar() {
		return delimChar;
	}
	public void setDelimChar(String delimChar) {
		this.delimChar = delimChar;
	}
}
