/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.service.impl;

import com.bowlink.rr.dao.masterClientIndexDAO;
import com.bowlink.rr.model.programMCIAlgorithms;
import com.bowlink.rr.model.programMCIFields;
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
    public List<programMCIAlgorithms> getProgramMCIAlgorithms(Integer programId) throws Exception {
        return masterClientIndexDAO.getProgramMCIAlgorithms(programId);
    }
    
    @Override
    @Transactional
    public List<programMCIFields> getProgramMCIFields(Integer mciId) throws Exception {
        return masterClientIndexDAO.getProgramMCIFields(mciId);
    }
    
    @Override
    @Transactional
    public Integer createMCIAlgorithm(programMCIAlgorithms newMCIAlgorithm) throws Exception {
        return masterClientIndexDAO.createMCIAlgorithm(newMCIAlgorithm);
    }
    
    @Override
    @Transactional
    public void updateMCIAlgorithm(programMCIAlgorithms MCIAlgorithm) throws Exception {
        masterClientIndexDAO.updateMCIAlgorithm(MCIAlgorithm);
    }
    
    @Override
    @Transactional
    public void createMCIAlgorithmFields(programMCIFields newField) throws Exception {
        masterClientIndexDAO.createMCIAlgorithmFields(newField);
    }
    
    @Override
    @Transactional
    public programMCIAlgorithms getMCIAlgorithm(Integer mciId) throws Exception {
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
