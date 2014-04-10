package com.ut.dph.service;

import java.util.List;

import com.ut.dph.model.Crosswalks;
import com.ut.dph.model.messageType;
import com.ut.dph.model.messageTypeDataTranslations;
import com.ut.dph.model.messageTypeFormFields;
import com.ut.dph.model.validationType;

public interface messageTypeManager {
	
	Integer createMessageType(messageType messagetype);
	
	void updateMessageType(messageType messagetype);
	
	void deleteMessageType(int messageTypeId);
		  
	messageType getMessageTypeById(int messageTypeId);
	
	messageType getMessageTypeByName(String name);
	
	List<messageType> getMessageTypes();
	
	List<messageType> getLatestMessageTypes(int maxResults);
	
	List<messageType> getActiveMessageTypes();
        
        List<messageType> getAvailableMessageTypes(int orgId);
	  
	Long findTotalMessageTypes();
	
	List<messageTypeFormFields> getMessageTypeFields(int messageTypeId);
	
	void updateMessageTypeFields(messageTypeFormFields formField);
        
        void saveMessageTypeFields(messageTypeFormFields formField);
	
	@SuppressWarnings("rawtypes")
	List getInformationTables();
        
        @SuppressWarnings("rawtypes")
	List getAllTables();
	
	@SuppressWarnings("rawtypes")
	List getTableColumns(String tableName);
	
	@SuppressWarnings("rawtypes")
	List getValidationTypes();
        
        @SuppressWarnings("rawtypes")
	String getValidationById(int id);
	
	@SuppressWarnings("rawtypes")
	List getDelimiters();
	
	Long getTotalFields(int messageTypeId);
	
	List<Crosswalks> getCrosswalks(int page, int maxResults, int orgId);
	
	Integer createCrosswalk(Crosswalks crosswalkDetails);
	
	double findTotalCrosswalks(int orgId);
	
	Crosswalks getCrosswalk(int cwId);
	
	@SuppressWarnings("rawtypes")
	List getCrosswalkData(int cwId);
	
	void saveDataTranslations(messageTypeDataTranslations translations);
	
	List<messageTypeDataTranslations> getMessageTypeTranslations(int messageTypeId);
	
	String getFieldName(int fieldId);
	
	String getCrosswalkName(int cwId);
	
	Long checkCrosswalkName(String name, int orgId);
	
	void deleteDataTranslations(int messageTypeId);
	
	List <validationType> getValidationTypes1 ();
	


}
