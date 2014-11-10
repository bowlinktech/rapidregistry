/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bowlink.rr.service.impl;

import com.bowlink.rr.dao.moduleDAO;
import com.bowlink.rr.model.modules;
import com.bowlink.rr.model.programModules;
import com.bowlink.rr.model.userProgramModules;
import com.bowlink.rr.service.moduleManager;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author chadmccue
 */
@Service
public class moduleManagerImpl implements moduleManager {
    
    @Autowired
    moduleDAO moduleDAO;
    
    @Override
    @Transactional
    public Integer createModule(modules module) throws Exception {
        Integer lastId = null;
        lastId = (Integer) moduleDAO.createModule(module);
        return lastId;
    }
    
   
    @Override
    @Transactional
    public List<modules> getAllModules() throws Exception {
        return moduleDAO.getAllModules();
    }
    
    @Override
    @Transactional
    public List<Integer> getModulesByProgram(Integer programId) throws Exception {
        return moduleDAO.getModulesByProgram(programId);
    }
    
    @Override
    @Transactional
    public void saveProgramModules(programModules module) throws Exception {
        moduleDAO.saveProgramModules(module);
    }
    
    @Override
    @Transactional
    public void deleteProgramModules(Integer programId) throws Exception {
        moduleDAO.deleteProgramModules(programId);
    }
    
    @Override
    @Transactional
    public  List<programModules> getUsedModulesByProgram(Integer programId) throws Exception {
        return moduleDAO.getUsedModulesByProgram(programId);
    }
    
    @Override
    @Transactional
    public List<userProgramModules> getUsedModulesByUser(Integer programId, Integer userId) throws Exception {
        return moduleDAO.getUsedModulesByUser(programId, userId);
    }
    
    @Override
    @Transactional
    public void removeUsedModulesByUser(Integer programId, Integer userId) throws Exception {
        moduleDAO.removeUsedModulesByUser(programId, userId);
    }
    
    @Override
    @Transactional
    public void saveUsedModulesByUser(userProgramModules module) throws Exception {
        moduleDAO.saveUsedModulesByUser(module);
    }
    
    @Override
    @Transactional
    public List getAvailableModules(Integer programId) throws Exception {
        return moduleDAO.getAvailableModules(programId);
    }
    
}
