/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bowlink.rr.service.impl;

import com.bowlink.rr.dao.activityCodeDAO;
import com.bowlink.rr.model.activityCodeAssocCategories;
import com.bowlink.rr.model.activityCodeCategories;
import com.bowlink.rr.model.activityCodes;
import com.bowlink.rr.model.programActivityCodes;
import com.bowlink.rr.model.programOrgHierarchyDetailActivityCodes;
import com.bowlink.rr.service.activityCodeManager;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author chadmccue
 */
@Service
public class activityCodeManagerImpl implements activityCodeManager {
    
    @Autowired
    activityCodeDAO activityCodeDAO;
    
    @Override
    @Transactional
    public List<activityCodeCategories> getActivityCodeCategories() throws Exception {
        return activityCodeDAO.getActivityCodeCategories();
    }
    
    @Override
    @Transactional
    public void createActivityCodeCategory(activityCodeCategories categoryDetails) throws Exception {
        activityCodeDAO.createActivityCodeCategory(categoryDetails);
    }
    
    @Override
    @Transactional
    public void updateActivityCodeCategory(activityCodeCategories categoryDetails) throws Exception {
        activityCodeDAO.updateActivityCodeCategory(categoryDetails);
    }
    
    @Override
    @Transactional
    public activityCodeCategories getActivityCodeCategoryById(Integer categoryId) throws Exception {
        return activityCodeDAO.getActivityCodeCategoryById(categoryId);
    }
    
    @Override
    @Transactional
    public List<activityCodes> getActivityCodes(Integer programId, Integer categoryId) throws Exception {
        return activityCodeDAO.getActivityCodes(programId, categoryId);
    }
    
    @Override
    @Transactional
    public List<activityCodes> getActivityCodesByProgram(Integer programId) throws Exception {
        return activityCodeDAO.getActivityCodesByProgram(programId);
    }
    
    @Override
    @Transactional
    public activityCodes getActivityCodeById(Integer codeId) throws Exception {
        return activityCodeDAO.getActivityCodeById(codeId);
    }
    
    @Override
    @Transactional
    public List<activityCodeAssocCategories> getSelActivityCodeCategories(Integer codeId) throws Exception {
        return activityCodeDAO.getSelActivityCodeCategories(codeId);
    }
    
    @Override
    @Transactional
    public void createActivityCode(activityCodes codeDetails) throws Exception {
        activityCodeDAO.createActivityCode(codeDetails);
    }
    
    @Override
    @Transactional
    public void updateActivityCode(activityCodes codeDetails) throws Exception {
        activityCodeDAO.updateActivityCode(codeDetails);
    }
    
     
    @Override
    @Transactional
    public boolean getActivityCodesByProgram (Integer programId, Integer codeId) throws Exception {
        return activityCodeDAO.getActivityCodesByProgram(programId, codeId);
    }
    
    @Override
    @Transactional
    public void saveProgramActivityCode(programActivityCodes newCodeAssoc) throws Exception {
        activityCodeDAO.saveProgramActivityCode(newCodeAssoc);
    }
    
    @Override
    @Transactional
    public void removeProgramActivityCodes(Integer programId) throws Exception {
        activityCodeDAO.removeProgramActivityCodes(programId);
    }
    
    @Override
    @Transactional
    public void saveActivityCodeCategoryAssoc(activityCodeAssocCategories newAssoc) throws Exception {
        activityCodeDAO.saveActivityCodeCategoryAssoc(newAssoc);
    }
    
    @Override
    @Transactional
    public void removeCategoryAssoc(Integer id) throws Exception {
        activityCodeDAO.removeCategoryAssoc(id);
    }
    
    @Override
    @Transactional
    public void saveActivityCodesForEntity(List<Integer> selActivityCodes, Integer entityItemId) throws Exception {
        
        try {
            /* First remove existing activity code entity associations */
            activityCodeDAO.removeEntityActivityCodes(entityItemId);
        } catch (Exception ex) {
            Logger.getLogger(activityCodeManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for(Integer codeId : selActivityCodes) {
            programOrgHierarchyDetailActivityCodes newCodeAssoc = new programOrgHierarchyDetailActivityCodes();
            newCodeAssoc.setCodeId(codeId);
            newCodeAssoc.setDetailId(entityItemId);
            
            try {
                activityCodeDAO.saveEntityActivityCodes(newCodeAssoc);
            } catch (Exception ex) {
                Logger.getLogger(activityCodeManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
}
