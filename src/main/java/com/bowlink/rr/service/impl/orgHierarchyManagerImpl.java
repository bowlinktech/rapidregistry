/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.service.impl;

import com.bowlink.rr.dao.orgHierarchyDAO;
import com.bowlink.rr.model.documentFolder;
import com.bowlink.rr.model.programOrgHierarchy;
import com.bowlink.rr.model.programOrgHierarchyAssoc;
import com.bowlink.rr.model.programOrgHierarchyDetails;
import com.bowlink.rr.model.userProgramHierarchy;
import com.bowlink.rr.reference.fileSystem;
import com.bowlink.rr.service.documentManager;
import com.bowlink.rr.service.orgHierarchyManager;
import com.bowlink.rr.service.programManager;
import java.io.File;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author chadmccue
 */
@Service
public class orgHierarchyManagerImpl implements orgHierarchyManager {
    
    @Autowired
    orgHierarchyDAO orgHierarchyDAO;
    
    @Autowired
    programManager programManager;
    
    @Autowired
    documentManager documentManager;
    
    @Override
    @Transactional
    public  List<programOrgHierarchy> getProgramOrgHierarchy(Integer programId) throws Exception {
        return orgHierarchyDAO.getProgramOrgHierarchy(programId);
    }
    
    @Override
    @Transactional
    public programOrgHierarchy getOrgHierarchyById(Integer id) throws Exception {
        return orgHierarchyDAO.getOrgHierarchyById(id);
    }
    
    @Override
    @Transactional
    public void saveOrgHierarchy(programOrgHierarchy hierarchyDetails) throws Exception {
        
        orgHierarchyDAO.saveOrgHierarchy(hierarchyDetails);
    }
    
    @Override
    @Transactional
    public programOrgHierarchy getProgramOrgHierarchyBydspPos(Integer dspPos, Integer programId) throws Exception {
        return orgHierarchyDAO.getProgramOrgHierarchyBydspPos(dspPos, programId);
    }
    
    @Override
    @Transactional
    public List getProgramOrgHierarchyItems(Integer programId, Integer level, Integer assocId) throws Exception {
        return orgHierarchyDAO.getProgramOrgHierarchyItems(programId, level, assocId);
    }
    
    @Override
    @Transactional
    public void saveUserProgramHierarchy(userProgramHierarchy hierarchy) throws Exception {
         orgHierarchyDAO.saveUserProgramHierarchy(hierarchy);
    }
    
    @Override
    @Transactional
    public  List<userProgramHierarchy> getUserProgramHierarchy(Integer programId, Integer userId) throws Exception {
        return orgHierarchyDAO.getUserProgramHierarchy(programId, userId);
    }
    
    @Override
    @Transactional
    public List<userProgramHierarchy> getUserAssociatedEntities(Integer programId, Integer userId, Integer entityId) throws Exception {
        return orgHierarchyDAO.getUserAssociatedEntities(programId, userId, entityId);
    }
    
    @Override
    @Transactional
    public void removeUserProgramHierarchy(Integer entityId, Integer userId) throws Exception {
        orgHierarchyDAO.removeUserProgramHierarchy(entityId, userId);
    }
    
    @Override
    @Transactional
    public  List<programOrgHierarchyDetails> getProgramHierarchyItems(Integer hierarchyId) throws Exception {
        return orgHierarchyDAO.getProgramHierarchyItems(hierarchyId);
    }
    
    @Override
    @Transactional
    public  List<programOrgHierarchyDetails> getProgramHierarchyItemsByAssoc(Integer hierarchyId, Integer assocId) throws Exception {
        return orgHierarchyDAO.getProgramHierarchyItemsByAssoc(hierarchyId, assocId);
    }
    
    @Override
    @Transactional
    public  programOrgHierarchyDetails getProgramHierarchyItemDetails(Integer itemId) throws Exception {
        return orgHierarchyDAO.getProgramHierarchyItemDetails(itemId);
    }
    
    @Override
    @Transactional
    public void saveOrgHierarchyItem(programOrgHierarchyDetails entityItemDetails) throws Exception {
        
        /* See if the item hierarchy level is the top one */
        programOrgHierarchy hierarchyDetails = orgHierarchyDAO.getOrgHierarchyById(entityItemDetails.getProgramHierarchyId());
        if(hierarchyDetails.getDspPos() == 1) {
            
            if(entityItemDetails.getCreateFolders() == true) {
                
                documentFolder folder = new documentFolder();
                folder.setCountyFolder(true);
                folder.setAdminOnly(false);
                folder.setFolderName(entityItemDetails.getName());
                folder.setParentFolderId(0);
                folder.setProgramId(hierarchyDetails.getProgramId());
                
                documentManager.saveFolder(folder);
                
                /* Create a document folder */
                String registryName = programManager.getProgramById(hierarchyDetails.getProgramId()).getProgramName().replaceAll(" ", "-").toLowerCase();

                //Set the directory to save the brochures to
                fileSystem dir = new fileSystem();
                dir.setDir(registryName, "documents/");

                File newDirectory = new File(dir.getDir() + entityItemDetails.getName());

                newDirectory.mkdir();
            }
        }
        
        orgHierarchyDAO.saveOrgHierarchyItem(entityItemDetails);
    }
    
    
    @Override
    @Transactional
    public void saveOrgHierarchyAssociation(programOrgHierarchyAssoc newAssoc) throws Exception {
        orgHierarchyDAO.saveOrgHierarchyAssociation(newAssoc);
    }
    
    @Override
    @Transactional
    public void removeOrgHierarchyAssociation(Integer itemId, Integer entityId) throws Exception {
        orgHierarchyDAO.removeOrgHierarchyAssociation(itemId, entityId);
    }
    
    @Override
    @Transactional
    public List<programOrgHierarchyAssoc> getAssociatedItems(Integer itemId) throws Exception {
        return orgHierarchyDAO.getAssociatedItems(itemId);
    }
}
