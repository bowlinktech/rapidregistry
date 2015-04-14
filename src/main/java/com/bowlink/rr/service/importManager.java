/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.service;

import com.bowlink.rr.model.algorithmCategories;
import com.bowlink.rr.model.User;
import com.bowlink.rr.model.MoveFilesLog;
import com.bowlink.rr.model.algorithmMatchingActions;
import com.bowlink.rr.model.delimiters;
import com.bowlink.rr.model.errorCodes;
import com.bowlink.rr.model.fileTypes;
import com.bowlink.rr.model.programUploadTypes;
import com.bowlink.rr.model.programUploadTypesFormFields;
import com.bowlink.rr.model.programUpload_Errors;
import com.bowlink.rr.model.programUploads;
import java.io.File;
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
    
    void deleteUploadTypeFields(Integer importTypeId) throws Exception;
    
    Integer saveUploadTypeField(programUploadTypesFormFields field) throws Exception;
    
    programUploadTypesFormFields getUploadTypeFieldById(Integer fieldId) throws Exception;
    
    void saveImportField(programUploadTypesFormFields fieldDetails) throws Exception;
    
    void removeImportType(Integer importTypeId) throws Exception;
    
    List <fileTypes> getFileTypes (Integer fileTypeId) throws Exception;
    
    void processUploadedFiles();
    
    void processUploadedFile(programUploads pu) throws Exception;
    
    Integer moveFileToHEL(programUploads pu);
    
    List <programUploads> getProgramUploads(Integer statusId) throws Exception;
    
    void updateProgramUpload (programUploads programUpload) throws Exception;
    
    Integer saveProgramUpload (programUploads programUpload) throws Exception;
    
    programUploads getProgramUpload(Integer programUpload) throws Exception;
    
    programUploadTypes getProgramUploadType (Integer programUploadTypeId) throws Exception;
    
    void processRRFiles();
    
    void moveHELFilestoRR ();
    
    void processRRFile(programUploads programUpload) throws Exception;
    
    void moveHELFiletoRR (programUploadTypes programUploadType) throws Exception;
    
    List <programUploadTypes> getProgramUploadTypes (boolean usesHEL, boolean checkHEL, Integer status) throws Exception;

    void sendImportErrorEmail (String subject, Exception ex) throws Exception;
    
    List <programUploadTypes> getDistinctHELPaths (Integer status) throws Exception;

    Integer insertMoveFilesLog(MoveFilesLog moveJob) throws Exception;
    
    void updateMoveFilesLogRun(MoveFilesLog moveJob) throws Exception;
    
    boolean movePathInUse (MoveFilesLog moveJob) throws Exception;

    public void moveFilesByPath(String inPath) throws Exception;
    
    programUploads getProgramUploadByAssignedFileName(programUploads pu);
    
    List <programUploadTypes> getProgramUploadTypesByUserId (Integer systemUserId, Integer statusId) throws Exception;
    
    List<User> getUsersForProgramUploadTypes(Integer statusId);
    
    delimiters getDelimiter (Integer delimId) throws Exception;
    
    String saveUploadedFile(programUploads pu, MultipartFile fileUpload) throws Exception;
    
    Integer chkUploadedFile(programUploads pu, File processFile) throws Exception;

    void insertError(programUpload_Errors uploadError) throws Exception;
    
    List <programUpload_Errors> getProgramUploadErrorList (Integer id, String type) throws Exception;
    
    List <errorCodes> getErrorCodes (Integer status) throws Exception;
    
    Integer submitUploadFile(Integer userId,Integer programUploadTypeId,MultipartFile uploadedFile) throws Exception;
    
}
