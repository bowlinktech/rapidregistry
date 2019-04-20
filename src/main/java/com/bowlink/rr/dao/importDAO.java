/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.dao;

import com.bowlink.rr.model.User;
import com.bowlink.rr.model.MoveFilesLog;
import com.bowlink.rr.model.configuration;
import com.bowlink.rr.model.delimiters;
import com.bowlink.rr.model.errorCodes;
import com.bowlink.rr.model.fieldsAndCols;
import com.bowlink.rr.model.fileTypes;
import com.bowlink.rr.model.programUploadRecordValues;
import com.bowlink.rr.model.programUploadTypeSFTPInfo;
import com.bowlink.rr.model.programUploadTypes;
import com.bowlink.rr.model.programUploadTypesFormFields;
import com.bowlink.rr.model.programUpload_Errors;
import com.bowlink.rr.model.programUploads;
import java.util.List;

import org.springframework.stereotype.Repository;

/**
 *
 * @author chadmccue
 */
@Repository
public interface importDAO {
    
    List<programUploadTypes> getUploadTypes(Integer programId) throws Exception;
    
    programUploadTypes getUploadTypeById(Integer importTypeId) throws Exception;
    
    void saveUploadType(programUploadTypes importTypeDetails) throws Exception;
    
    List<programUploadTypesFormFields> getImportTypeFields(Integer importTypeId) throws Exception;
    
    void deleteUploadTypeFieldsByStatus(Integer importTypeId, String status) throws Exception;
    
    Integer saveUploadTypeField(programUploadTypesFormFields field) throws Exception;
    
    programUploadTypesFormFields getUploadTypeFieldById(Integer fieldId) throws Exception;
    
    void saveImportField(programUploadTypesFormFields fieldDetails) throws Exception;
    
    void removeImportType(Integer importTypeId) throws Exception;
    
    List <fileTypes> getFileTypes (Integer fileTypeId) throws Exception;
    
    List <programUploads> getProgramUploads(Integer statusId) throws Exception;
    
    void updateProgramUpload (programUploads programUpload) throws Exception;
    
    Integer saveProgramUpload (programUploads programUpload) throws Exception;
    
    programUploads getProgramUpload(Integer programUpload) throws Exception;
    
    programUploadTypes getProgramUploadType (Integer programUploadTypeId) throws Exception;
    
    List <programUploadTypes> getProgramUploadTypes (boolean usesHEL, boolean checkHEL, Integer status) throws Exception;
    
    List <programUploadTypes> getDistinctHELPaths (Integer status) throws Exception;
    
    Integer insertMoveFilesLog(MoveFilesLog moveJob) throws Exception;
    
    void updateMoveFilesLogRun(MoveFilesLog moveJob) throws Exception;
    
    boolean movePathInUse (MoveFilesLog moveJob) throws Exception;
    
    programUploads getProgramUploadByAssignedId(programUploads pu);
    
    List <programUploadTypes> getProgramUploadTypesByUserId (Integer systemUserId, Integer statusId) throws Exception;
    
    List<User> getUsersForProgramUploadTypes(Integer statusId);
    
    delimiters getDelimiter (Integer delimId) throws Exception;
    
    void insertError(programUpload_Errors uploadError) throws Exception;
    
    List <programUpload_Errors> getProgramUploadErrorList (Integer id, String type) throws Exception;
    
    List <errorCodes> getErrorCodes (Integer status) throws Exception;
    
    List <programUploads> getProgramUploadsByImportType (Integer importTypeId) throws Exception;
    
    void dropLoadTable(String loadTableName) throws Exception;
    
    void createLoadTable (String loadTableName) throws Exception;
    
    void indexLoadTable (String loadTableName) throws Exception;
    
    void insertLoadData(programUploadTypes put, String loadTableName, String fileWithPath) throws Exception;
    
    void updateLoadTable(String loadTableName, Integer programUploadId) throws Exception;
    
    void insertUploadRecords (String loadTableName, Integer programUploadId) throws Exception;
    
    void insertUploadRecordDetails (Integer programUploadId) throws Exception;
    
    void insertUploadRecordDetailsData(String loadTableName);
    
    void insertFailedRequiredFields(programUploadTypesFormFields putField, Integer programUploadId, Integer programUploadRecordId) throws Exception;
  
    void updateStatusForErrorRecord(Integer programUploadId, Integer statusId, Integer programUploadRecordId) throws Exception;
    
    void genericValidation(programUploadTypesFormFields putField, Integer validationTypeId, Integer programUploadId, Integer programUploadRecordId)  throws Exception;

    List<programUploadRecordValues> getFieldColAndValues (Integer programUploadId, programUploadTypesFormFields putField) throws Exception;
    
    List <programUploadRecordValues> getFieldColAndValueByProgramUploadRecordId (programUploadTypesFormFields putField, Integer programUploadRecordId) throws Exception;
    
    void updateFieldValue(programUploadRecordValues prv, String newValue) throws Exception;
    
    List <programUploadTypesFormFields> getFieldDetailByTableAndColumn (String tableName, String columnName, Integer programUploadTypeId, Integer useField) throws Exception;
    
    void insertInvalidPermission (Integer permissionField,Integer hierarchyFieldId, programUploads programUpload, Integer programHierarchyId) throws Exception;
    
    void updateProgramHierarchyId (Integer programUploadId, Integer programUploadRecordId, Integer dspPos) throws Exception;
    
    List <String> getAlgorithmTables (Integer algorithmId, String type) throws Exception;
    
    boolean hasTable (String tableName, Integer algorithmId) throws Exception;
    
    void insertNewProgramPatients (programUploads programUpload, Integer programUploadRecordId) throws Exception; 

    void updateProgramPatientIdInUploadRecord (programUploads programUpload, Integer programUploadRecordId) throws Exception;  
    
    void changeProgramUploadRecordStatus (programUploads programUpload, Integer programUploadRecordId, Integer oldStatusId, Integer newStatusId) throws Exception;

    boolean checkMultiValue (programUploads programUpload, String tableName) throws Exception;
    
    void insertStoragePatients(fieldsAndCols fieldsAndColumns, programUploads programUpload, Integer programUploadRecordId) throws Exception;
    
    List <fieldsAndCols> selectInsertTableAndColumns (programUploads programUpload, String tableName);
    
    void insertStorageEngagements(fieldsAndCols fieldsAndColumns, programUploads programUpload, Integer programUploadRecordId) throws Exception;
    
    void updateEngagementIdForProgramUploadRecord (programUploads programUpload, Integer programUploadRecordId) throws Exception;
    
    void blanksToNull (Integer fColumn, programUploads programUpload, Integer programUploadRecordId) throws Exception;
    
    List <Integer> getFColumnsForProgramUploadType (programUploads programUpload) throws Exception;
    
    void updateFormFieldStatus(Integer programUploadTypeId, String status) throws Exception;
    
    void deleteFormFieldsFromAlgorithms (Integer programUploadTypeId) throws Exception;
    
    List <String> getNonMainTablesForProgramUploadType (Integer programUploadTypeId) throws Exception;
    
    boolean usesMultiValue (Integer programUploadTypeId, String tableName) throws Exception;
    
    boolean multiRow (Integer programUploadTypeId, String tableName) throws Exception;
    
    void insertSingleStorageTable(fieldsAndCols fieldsAndColumns, programUploads programUpload, String tableName, Integer programUploadRecordId, List<Integer> skipRecordIds) throws Exception;
    
    List<Integer> getBlankRecordIds (fieldsAndCols fieldsAndColumns, programUploads programUpload, Integer programUploadRecordId) throws Exception;
    
    List<Integer> getListRecordIds (fieldsAndCols fieldsAndColumns, programUploads programUpload, Integer programUploadRecordId) throws Exception;

    Integer countSubString(String col, Integer programUploadRecordId) throws Exception;
    
    void insertMultiValToMessageTables(fieldsAndCols fieldsAndColumns, Integer subStringCounter, Integer programUploadRecordId, String tableName, programUploads programUpload) throws Exception;
    
    boolean checkMultiRowSetUp(Integer programUploadTypeId, String tableName) throws Exception;
    
    List<configuration> getHELConfigs(Integer orgId) throws Exception;
    
    List<programUploadTypes> getParentUploadTypes(Integer programId) throws Exception;
    
    void saveSFTPInfo (programUploadTypeSFTPInfo sftpInfo) throws Exception;
    
    void updateSFTPInfo (programUploadTypeSFTPInfo sftpInfo) throws Exception;
    
    programUploadTypeSFTPInfo getSFTPInfoById (Integer sftpId) throws Exception;
    
    programUploadTypeSFTPInfo getSFTPInfoByPUTId (Integer programUploadTypeId) throws Exception;
    
}



