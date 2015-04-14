/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.dao;

import com.bowlink.rr.model.User;
import com.bowlink.rr.model.MoveFilesLog;
import com.bowlink.rr.model.algorithmCategories;
import com.bowlink.rr.model.algorithmMatchingActions;
import com.bowlink.rr.model.delimiters;
import com.bowlink.rr.model.errorCodes;
import com.bowlink.rr.model.fileTypes;
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
    
    void deleteUploadTypeFields(Integer importTypeId) throws Exception;
    
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
    
    programUploads getProgramUploadByAssignedFileName(programUploads pu);
    
    List <programUploadTypes> getProgramUploadTypesByUserId (Integer systemUserId, Integer statusId) throws Exception;
    
    List<User> getUsersForProgramUploadTypes(Integer statusId);
    
    delimiters getDelimiter (Integer delimId) throws Exception;
    
    void insertError(programUpload_Errors uploadError) throws Exception;
    
    List <programUpload_Errors> getProgramUploadErrorList (Integer id, String type) throws Exception;
    
    List <errorCodes> getErrorCodes (Integer status) throws Exception;
  
}

