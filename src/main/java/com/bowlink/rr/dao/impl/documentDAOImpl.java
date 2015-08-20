/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.dao.impl;

import com.bowlink.rr.dao.documentDAO;
import com.bowlink.rr.model.documentFolder;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author chadmccue
 */
@Repository
public class documentDAOImpl implements documentDAO {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    /**
     * The 'saveFolder' function will save the new folder
     * @param folderDetails
     * @return
     * @throws Exception 
     */
    public void saveFolder(documentFolder folderDetails) throws Exception {
        sessionFactory.getCurrentSession().save(folderDetails);

    }
    
}
