/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bowlink.rr.service;

import com.bowlink.rr.model.modules;
import com.bowlink.rr.model.programModules;
import com.bowlink.rr.model.userProgramModules;
import java.util.List;

/**
 *
 * @author chadmccue
 */
public interface moduleManager {
    
    Integer createModule(modules module) throws Exception;
    
    List<modules> getAllModules() throws Exception;
    
    List<Integer> getModulesByProgram(Integer programId) throws Exception;
    
    void saveProgramModules(programModules module) throws Exception;
    
    void deleteProgramModules(Integer programId) throws Exception;
    
    List<programModules> getUsedModulesByProgram(Integer programId) throws Exception;
     
    List<userProgramModules> getUsedModulesByUser(Integer programId, Integer userId) throws Exception;
     
    void removeUsedModulesByUser(Integer programId, Integer userId) throws Exception;
    
    void saveUsedModulesByUser(userProgramModules module) throws Exception;
    
    List getAvailableModules(Integer programId) throws Exception;
    
    Integer getLastProgramUsedModuleByPos(Integer programId) throws Exception;
    
}
