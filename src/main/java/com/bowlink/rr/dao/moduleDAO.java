/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bowlink.rr.dao;

import com.bowlink.rr.model.modules;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author chadmccue
 */
@Repository
public interface moduleDAO {
    
    Integer createModule(modules module) throws Exception;
    
    List<modules> getAllModules() throws Exception;
    
}
