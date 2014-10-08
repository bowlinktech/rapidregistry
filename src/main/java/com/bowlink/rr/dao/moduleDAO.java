/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bowlink.rr.dao;

import com.bowlink.rr.model.modules;
import com.bowlink.rr.model.programModules;
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
    
    List<Integer> getModulesByProgram(Integer programId) throws Exception;
    
    void saveProgramModules(programModules module) throws Exception;
    
    void deleteProgramModules(Integer programId) throws Exception;
    
}
