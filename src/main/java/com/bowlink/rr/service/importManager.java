/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.service;

import com.bowlink.rr.model.User;
import com.bowlink.rr.model.MoveFilesLog;
import com.bowlink.rr.model.delimiters;
import com.bowlink.rr.model.errorCodes;
import com.bowlink.rr.model.fieldsAndCols;
import com.bowlink.rr.model.fileTypes;
import com.bowlink.rr.model.programUploadRecordValues;
import com.bowlink.rr.model.programUploadTypes;
import com.bowlink.rr.model.programUploadTypesFormFields;
import com.bowlink.rr.model.programUpload_Errors;
import com.bowlink.rr.model.programUploads;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author chadmccue
 */
public interface importManager {
    
    List<programUploadTypes> getUploadTypes(Integer programId) throws Exception;
    
    programUploadTypes getUploadTypeById(Integer importTypeId) throws Exception;
    
    void saveUploadType(programUploadTypes importTypeDetails) throws Exception;
    
    List<programUploadTypesFormFields> getImportTypeFields(Integer importTypeId) throws Exception;
    
    void deleteUploadTypeFieldsByStatus(Integer importTypeId, String status) throws Exception;
    
    Integer saveUploadTypeField(programUploadTypesFormFields field) throws Exception;
    
    programUploadTypesFormFields getUploadTypeFieldById(Integer fieldId) throws Exception;
    
    void saveImportField(programUploadTypesFormFields fieldDetails) throws Exception;
    
    String removeImportType(Integer importTypeId) throws Exception;
    
    List <fileTypes> getFileTypes (Integer fileTypeId) throws Exception;
    
    void processUploadedFiles();
    
    void processUploadedFile(programUploads pu) throws Exception;
    
    Integer moveFileToHEL(programUploads pu);
    
    List <programUploads> getProgramUploads(Integer statusId) throws Exception;
    
    void updateProgramUpload (programUploads programUpload) throws Exception;
    
    Integer saveProgramUpload (programUploads programUpload) throws Exception;
    
    programUploads getProgramUpload(Integer programUpload) throws Exception;
    
    programUploadTypes getProgramUploadType (Integer programUploadTypeId) throws Exception;
    
    void processRRFiles() throws Exception;
    
    void moveHELFilestoRR ();
    
    Integer processRRFile(programUploads programUpload) throws Exception;
    
    void moveHELFiletoRR (programUploadTypes programUploadType) throws Exception;
    
    List <programUploadTypes> getProgramUploadTypes (boolean usesHEL, boolean checkHEL, Integer status) throws Exception;

    void sendImportErrorEmail (String subject, Exception ex) throws Exception;
    
    List <programUploadTypes> getDistinctHELPaths (Integer status) throws Exception;

    Integer insertMoveFilesLog(MoveFilesLog moveJob) throws Exception;
    
    void updateMoveFilesLogRun(MoveFilesLog moveJob) throws Exception;
    
    boolean movePathInUse (MoveFilesLog moveJob) throws Exception;

    public void moveFilesByPath(String inPath) throws Exception;
    
    programUploads getProgramUploadByAssignedId(programUploads pu);
    
    List <programUploadTypes> getProgramUploadTypesByUserId (Integer systemUserId, Integer statusId) throws Exception;
    
    List<User> getUsersForProgramUploadTypes(Integer statusId);
    
    delimiters getDelimiter (Integer delimId) throws Exception;
    
    String saveUploadedFile(programUploads pu, MultipartFile fileUpload) throws Exception;
    
    Integer chkUploadedFile(programUploads pu, File processFile) throws Exception;

    void insertError(programUpload_Errors uploadError) throws Exception;
    
    List <programUpload_Errors> getProgramUploadErrorList (Integer id, String type) throws Exception;
    
    List <errorCodes> getErrorCodes (Integer status) throws Exception;
    
    Integer submitUploadFile(Integer userId,Integer programUploadTypeId,MultipartFile uploadedFile) throws Exception;
 
    List <programUploads> getProgramUploadsByImportType (Integer importTypeId) throws Exception;
    
    void loadFile (programUploads pu) throws Exception;
    
    programUploads getProgramUploadOnly(Integer programUploadId) throws Exception;
    
    void clearUpload (Integer programUploadId) throws Exception;
    
    void dropLoadTable(String loadTableName) throws Exception;
    
    void createLoadTable (String loadTableName) throws Exception;
    
    void indexLoadTable (String loadTableName) throws Exception;
    
    void insertLoadData(programUploadTypes put, String loadTableName, String fileWithPath) throws Exception;
    
    void updateLoadTable(String loadTableName, Integer programUploadId) throws Exception;
    
    void insertUploadRecords (String loadTableName, Integer programUploadId) throws Exception;
    
    void insertUploadRecordDetails (Integer programUploadId) throws Exception;
    
    void insertUploadRecordDetailsData (String loadTableName);
    
    void insertFailedRequiredFields(programUploadTypesFormFields putField, Integer programUploadId, Integer programUploadRecordId) throws Exception;
    
    void updateStatusForErrorRecord(Integer programUploadId, Integer statusId, Integer programUploadRecordId) throws Exception;
    
    void runValidations(Integer programUploadId, programUploadTypesFormFields putField, Integer programUploadRecordId) throws Exception;
   
    void genericValidation(programUploadTypesFormFields putField, Integer validationTypeId, Integer programUploadId, Integer programUploadRecordId) throws Exception;
    
    void dateValidation(programUploadTypesFormFields putField, Integer programUploadId, Integer programUploadRecordId) throws Exception;
    
    void urlValidation(programUploadTypesFormFields putField, Integer programUploadId, Integer programUploadRecordId) throws Exception;
    
    List<programUploadRecordValues> getFieldColAndValues (Integer programUploadId, programUploadTypesFormFields putField) throws Exception;
    
    List <programUploadRecordValues> getFieldColAndValueByProgramUploadRecordId (programUploadTypesFormFields putField, Integer programUploadRecordId) throws Exception;

    boolean isValidURL(String url);
    
    String formatDateForDB(Date date);

    Date convertDate(String date);

    String chkMySQLDate(String date);
    
    void updateFieldValue(programUploadRecordValues prv, String newValue) throws Exception;
    
    boolean recheckLongDate(String longDateVal, String convertedDate);
    
    Date convertLongDate(String dateValue);
    
    List <programUploadTypesFormFields> getFieldDetailByTableAndColumn (String tableName, String columnName, Integer programUploadTypeId, Integer useField) throws Exception;
    
    void insertInvalidPermission (Integer permissionField, Integer hierarchyFieldId, programUploads programUpload, Integer programHierarchyId) throws Exception;
    
    void runMultiValueValidations(Integer programUploadId, programUploadTypesFormFields putField, Integer programUploadRecordId) throws Exception;
    
    void updateProgramHierarchyId (Integer programUploadId, Integer programUploadRecordId, Integer dspPos) throws Exception;
    
    List <String> getOtherAlgorithmTables (Integer algorithmId) throws Exception;
    
    boolean hasTable (String tableName, Integer algorithmId) throws Exception;
    
    boolean insertRecords (programUploads programUpload, Integer programUploadRecordId) throws Exception;
    
    void insertNewProgramPatients (programUploads programUpload, Integer programUploadRecordId) throws Exception;    
    
    void updateProgramPatientIdInUploadRecord (programUploads programUpload, Integer programUploadRecordId) throws Exception; 
    
    void changeProgramUploadRecordStatus (programUploads programUpload, Integer programUploadRecordId, Integer oldStatusId, Integer newStatusId) throws Exception;
    
    boolean checkMultiValue (programUploads programUpload, String tableName) throws Exception;
    
    void insertStoragePatients(fieldsAndCols fieldsAndColumns, programUploads programUpload, Integer programUploadRecordId) throws Exception;
    
    void insertStorageEngagements(fieldsAndCols fieldsAndColumns, programUploads programUpload, Integer programUploadRecordId) throws Exception;
    
    List <fieldsAndCols> selectSingleInsertTableAndColumns (programUploads programUpload, String tableName);
    
    void updateEngagementIdForProgramUploadRecord (programUploads programUpload, Integer programUploadRecordId) throws Exception;
    
    void blanksToNull (programUploads programUpload, Integer programUploadRecordId) throws Exception;
    
    List <Integer> getFColumnsForProgramUploadType (programUploads programUpload) throws Exception;
    
    void updateFormFieldStatus(Integer programUploadTypeId, String status) throws Exception;
    
    void deleteFormFieldsFromAlgorithms (Integer programUploadTypeId) throws Exception;
}
