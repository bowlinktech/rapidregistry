/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.service.impl;

import com.bowlink.rr.dao.importDAO;
import com.bowlink.rr.model.programUploadTypes;
import com.bowlink.rr.model.programUploadTypesFormFields;
import com.bowlink.rr.service.importManager;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author chadmccue
 */
@Service
public class importManagerImpl implements importManager {
    
    @Autowired
    importDAO importDAO;
    
    @Override
    @Transactional
    public List<programUploadTypes> getUploadTypes(Integer programId) throws Exception {
        return importDAO.getUploadTypes(programId);
    }
    
    @Override
    @Transactional
    public programUploadTypes getUploadTypeById(Integer importTypeId) throws Exception {
        return importDAO.getUploadTypeById(importTypeId);
    }
    
    @Override
    @Transactional
    public void saveUploadType(programUploadTypes importTypeDetails) throws Exception {
        importDAO.saveUploadType(importTypeDetails);
    }
    
    @Override
    @Transactional
    public List<programUploadTypesFormFields> getImportTypeFields(Integer importTypeId) throws Exception {
        return importDAO.getImportTypeFields(importTypeId);
    }
    
    @Override
    @Transactional
    public void deleteUploadTypeFields(Integer importTypeId) throws Exception {
        importDAO.deleteUploadTypeFields(importTypeId);
    }
    
    @Override
    @Transactional
    public Integer saveUploadTypeField(programUploadTypesFormFields field) throws Exception {
        return importDAO.saveUploadTypeField(field);
    }
    
    @Override
    @Transactional
    public programUploadTypesFormFields getUploadTypeFieldById(Integer fieldId) throws Exception {
        return importDAO.getUploadTypeFieldById(fieldId);
    }
    
    @Override
    @Transactional
    public void saveImportField(programUploadTypesFormFields fieldDetails) throws Exception {
        importDAO.saveImportField(fieldDetails);
    }
    
    @Override
    @Transactional
    public void removeImportType(Integer importTypeId) throws Exception {
        importDAO.removeImportType(importTypeId);
    }
}
