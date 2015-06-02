/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bowlink.rr.dao;

import com.bowlink.rr.model.activityCodeAssocCategories;
import com.bowlink.rr.model.activityCodeCategories;
import com.bowlink.rr.model.activityCodes;
import com.bowlink.rr.model.programActivityCodes;
import com.bowlink.rr.model.programOrgHierarchyDetailActivityCodes;
import java.util.List;

/**
 *
 * @author chadmccue
 */
public interface activityCodeDAO {
    
    List<activityCodeCategories> getActivityCodeCategories() throws Exception;
    
    void createActivityCodeCategory(activityCodeCategories categoryDetails) throws Exception;
    
    void updateActivityCodeCategory(activityCodeCategories categoryDetails) throws Exception;
    
    activityCodeCategories getActivityCodeCategoryById(Integer categoryId) throws Exception;
    
    List<activityCodes> getActivityCodes(Integer programId, Integer categoryId) throws Exception;
    
    List<activityCodes> getActivityCodesByProgram(Integer programId) throws Exception;
    
    activityCodes getActivityCodeById(Integer codeId) throws Exception;
    
    List<activityCodeAssocCategories> getSelActivityCodeCategories(Integer codeId) throws Exception;
    
    void createActivityCode(activityCodes codeDetails) throws Exception;
    
    void updateActivityCode(activityCodes codeDetails) throws Exception;
    
    boolean getActivityCodesByProgram (Integer programId, Integer codeId) throws Exception;
    
    void saveProgramActivityCode(programActivityCodes newCodeAssoc) throws Exception;
    
    void removeProgramActivityCodes(Integer programId) throws Exception;
    
    void saveActivityCodeCategoryAssoc(activityCodeAssocCategories newAssoc) throws Exception;
    
    void removeCategoryAssoc(Integer id) throws Exception;
    
    void removeEntityActivityCodes(Integer entityItemId) throws Exception;
    
    void saveEntityActivityCodes(programOrgHierarchyDetailActivityCodes newCodeAssoc) throws Exception;
    
    List<Integer> getActivityCodesForEntity(Integer entityItemId) throws Exception;
    
}
