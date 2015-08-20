/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.service.impl;

import com.bowlink.rr.dao.documentDAO;
import com.bowlink.rr.model.documentFolder;
import com.bowlink.rr.service.documentManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author chadmccue
 */
@Service
public class documentManagerImpl implements documentManager {
    
    @Autowired
    documentDAO documentDAO;
    
    @Override
    @Transactional
    public void saveFolder(documentFolder folder) throws Exception {
        documentDAO.saveFolder(folder);
    }
}
