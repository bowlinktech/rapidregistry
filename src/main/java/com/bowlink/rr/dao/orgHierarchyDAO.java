/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.dao;

import com.bowlink.rr.model.programOrgHierarchy;
import com.bowlink.rr.model.programOrgHierarchyAssoc;
import com.bowlink.rr.model.programOrgHierarchyDetails;
import com.bowlink.rr.model.userProgramHierarchy;
import java.util.List;

/**
 *
 * @author chadmccue
 */
public interface orgHierarchyDAO {
    
    List<programOrgHierarchy> getProgramOrgHierarchy(Integer programId) throws Exception;
    
    programOrgHierarchy getOrgHierarchyById(Integer id) throws Exception;
    
    void saveOrgHierarchy(programOrgHierarchy hierarchyDetails) throws Exception;
    
    programOrgHierarchy getProgramOrgHierarchyBydspPos(Integer dspPos, Integer programId) throws Exception;
    
    List getProgramOrgHierarchyItems(Integer programId, Integer level, Integer assocId) throws Exception;
    
    void saveUserProgramHierarchy(userProgramHierarchy hierarchy) throws Exception;
    
    List<userProgramHierarchy> getUserProgramHierarchy(Integer programId, Integer userId) throws Exception;
    
    List<userProgramHierarchy> getUserAssociatedEntities(Integer programId, Integer userId, Integer entityId) throws Exception;
    
    void removeUserProgramHierarchy(Integer entityId, Integer userId) throws Exception;
    
    List<programOrgHierarchyDetails> getProgramHierarchyItems(Integer hierarchyId) throws Exception;
    
    List<programOrgHierarchyDetails> getProgramHierarchyItemsByAssoc(Integer hierarchyId, Integer assocId) throws Exception;
    
    programOrgHierarchyDetails getProgramHierarchyItemDetails(Integer itemId) throws Exception;
    
    void saveOrgHierarchyItem(programOrgHierarchyDetails entityItemDetails) throws Exception;
    
    void saveOrgHierarchyAssociation(programOrgHierarchyAssoc newAssoc) throws Exception;
    
    void removeOrgHierarchyAssociation(Integer itemId, Integer entityId) throws Exception;
    
    List<programOrgHierarchyAssoc> getAssociatedItems(Integer itemId) throws Exception;
    
    programOrgHierarchyDetails getProgramHierarchyItemDetailsByName(programOrgHierarchyDetails newEntity) throws Exception;
    
}
