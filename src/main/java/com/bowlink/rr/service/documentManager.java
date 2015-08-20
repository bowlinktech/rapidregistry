/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.service;

import com.bowlink.rr.model.documentFolder;

/**
 *
 * @author chadmccue
 */
public interface documentManager {
    
    void saveFolder(documentFolder folder) throws Exception;
    
}
