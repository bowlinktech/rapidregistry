/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bowlink.rr.model;


/**
 *
 * @author grace
 */
public class fieldsAndCols {
    
    String storageFields = null;
    String fColumns = null;
    String checkForDelimSQL = null;
    String splitFieldSQL = null;
    
	public String getStorageFields() {
		return storageFields;
	}
	public void setStorageFields(String storageFields) {
		this.storageFields = storageFields;
	}
	public String getfColumns() {
		return fColumns;
	}
	public void setfColumns(String fColumns) {
		this.fColumns = fColumns;
	}
	public String getCheckForDelimSQL() {
		return checkForDelimSQL;
	}
	public void setCheckForDelimSQL(String checkForDelimSQL) {
		this.checkForDelimSQL = checkForDelimSQL;
	}
	public String getSplitFieldSQL() {
		return splitFieldSQL;
	}
	public void setSplitFieldSQL(String splitFieldSQL) {
		this.splitFieldSQL = splitFieldSQL;
	}

}
