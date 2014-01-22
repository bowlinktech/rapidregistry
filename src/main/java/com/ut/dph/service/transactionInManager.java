/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ut.dph.service;

import com.ut.dph.model.batchUploadSummary;
import com.ut.dph.model.batchUploads;
import com.ut.dph.model.fieldSelectOptions;
import com.ut.dph.model.transactionAttachment;
import com.ut.dph.model.transactionIn;
import com.ut.dph.model.transactionInRecords;
import com.ut.dph.model.transactionTarget;
import com.ut.dph.model.custom.ConfigForInsert;

import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author chadmccue
 */
public interface transactionInManager {
    
    String getFieldValue(String tableName, String tableCol, int idValue);
    
    List<fieldSelectOptions> getFieldSelectOptions(int fieldId, int configId);
    
    Integer submitBatchUpload(batchUploads batchUpload);
    
    void submitBatchUploadSummary(batchUploadSummary summary);
    
    void submitBatchUploadChanges(batchUploads batchUpload);
    
    Integer submitTransactionIn(transactionIn transactionIn);
    
    void submitTransactionInChanges(transactionIn transactionIn);
    
    Integer submitTransactionInRecords(transactionInRecords records);
    
    void submitTransactionInRecordsUpdates(transactionInRecords records);
    
    void submitTransactionTranslatedInRecords(int transactionId, int transactionRecordId, int configId);
    
    List<batchUploads> getpendingBatches(int userId, int orgId);
    
    List<transactionIn> getBatchTransactions(int batchId, int userId);
    
    List<transactionIn> getsentTransactions(int orgId);
    
    batchUploads getBatchDetails(int batchId);
    
    transactionIn getTransactionDetails(int transactionId);
    
    transactionInRecords getTransactionRecords(int transactionId);
    
    transactionInRecords getTransactionRecord(int recordId);
    
    void submitTransactionTarget(transactionTarget transactionTarget);
    
    transactionTarget getTransactionTargetDetails(int transactionTargetId);
    
    void submitTransactionTargetChanges(transactionTarget transactionTarget);
    
    transactionTarget getTransactionTarget(int batchUploadId, int transactionInId);
    
    String uploadAttachment(MultipartFile fileUpload, String orgName);
    
    Integer submitAttachment(transactionAttachment attachment);
    
    transactionAttachment getAttachmentById(int attachmentId);
    
    void submitAttachmentChanges(transactionAttachment attachment);
    
    List<transactionAttachment> getAttachmentsByTransactionId(int transactionInId);
    
    void removeAttachmentById(int attachmentId);
    
    boolean processTransactions(int batchUploadId);
    
    boolean insertSingleToMessageTables(ConfigForInsert configForInsert);
    
    boolean insertMultiToMessageTables(ConfigForInsert configForInsert);
    
    List <ConfigForInsert> setConfigForInsert(int configId, int batchUploadId);
    
    List <Integer> getConfigIdsForBatch (int batchUploadId);
    
    List <Integer> getTransWithMultiValues (ConfigForInsert config);

    void clearMessageTables(int batchId);
    
     Map<String,String> uploadBatchFile(int configId, MultipartFile fileUpload);
       
}
