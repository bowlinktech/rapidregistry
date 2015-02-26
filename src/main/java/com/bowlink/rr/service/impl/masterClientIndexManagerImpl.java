/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.service.impl;

import com.bowlink.rr.dao.masterClientIndexDAO;
import com.bowlink.rr.model.programUpload_MCIalgorithms;
import com.bowlink.rr.model.programUpload_MCIFields;
import com.bowlink.rr.service.masterClientIndexManager;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author chadmccue
 */
@Service
public class masterClientIndexManagerImpl implements masterClientIndexManager {
    
    @Autowired
    masterClientIndexDAO masterClientIndexDAO;
    
    @Override
    @Transactional
    public List<programUpload_MCIalgorithms> getProgramUploadMCIalgorithms(Integer programId) throws Exception {
        return masterClientIndexDAO.getProgramUploadMCIalgorithms(programId);
    }
    
    @Override
    @Transactional
    public List<programUpload_MCIFields> getProgramUploadMCIFields(Integer mciId) throws Exception {
        return masterClientIndexDAO.getProgramUploadMCIFields(mciId);
    }
    
    @Override
    @Transactional
    public Integer createMCIAlgorithm(programUpload_MCIalgorithms newMCIAlgorithm) throws Exception {
        return masterClientIndexDAO.createMCIAlgorithm(newMCIAlgorithm);
    }
    
    @Override
    @Transactional
    public void updateMCIAlgorithm(programUpload_MCIalgorithms MCIAlgorithm) throws Exception {
        masterClientIndexDAO.updateMCIAlgorithm(MCIAlgorithm);
    }
    
    @Override
    @Transactional
    public void createMCIAlgorithmFields(programUpload_MCIFields newField) throws Exception {
        masterClientIndexDAO.createMCIAlgorithmFields(newField);
    }
    
    @Override
    @Transactional
    public programUpload_MCIalgorithms getMCIAlgorithm(Integer mciId) throws Exception {
        return masterClientIndexDAO.getMCIAlgorithm(mciId);
    }
    
    @Override
    @Transactional
    public void removeAlgorithmField(Integer algorithmFieldId) throws Exception {
        masterClientIndexDAO.removeAlgorithmField(algorithmFieldId);
    }
    
    @Override
    @Transactional
    public void removeAlgorithm(Integer algorithmId) throws Exception {
        masterClientIndexDAO.removeAlgorithm(algorithmId);
    }
    
}
