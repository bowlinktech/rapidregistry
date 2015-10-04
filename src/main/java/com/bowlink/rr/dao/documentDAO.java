/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.dao;

import com.bowlink.rr.model.documentFolder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author chadmccue
 */
@Repository
public interface documentDAO {
    
    void saveFolder(documentFolder folder) throws Exception;
    
    documentFolder getFolderDetailsByName (documentFolder folder) throws Exception;
    
    void updateFolder(documentFolder folder) throws Exception;
}
