/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.service.impl;

import com.bowlink.rr.dao.orgHierarchyDAO;
import com.bowlink.rr.model.programOrgHierarchy;
import com.bowlink.rr.service.orgHierarchyManager;
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
}
