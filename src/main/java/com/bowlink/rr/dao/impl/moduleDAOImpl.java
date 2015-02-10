/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bowlink.rr.dao.impl;

import com.bowlink.rr.dao.moduleDAO;
import com.bowlink.rr.model.modules;
import com.bowlink.rr.model.programModules;
import com.bowlink.rr.model.userProgramModules;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author chadmccue
 */
@Repository
public class moduleDAOImpl implements moduleDAO {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    /**
     * The 'createModule" function will create the new module .
     *
     * @Table	lu_programModules
     *
     * @param	module	This will hold the module object from the form
     *
     * @return the function will return the id of the new module
     *
     */
    @Override
    public Integer createModule(modules module) {
        Integer lastId = null;

        lastId = (Integer) sessionFactory.getCurrentSession().save(module);

        return lastId;
    }
    
    /**
     * The 'getAllModules' function will return a list of the modules in the system.
     * 
     * @return The function will return a list of modules in the system
     */
    @Override
    public List<modules> getAllModules() throws Exception {
        Query query = sessionFactory.getCurrentSession().createQuery("from modules order by moduleName asc");

        List<modules> moduleList = query.list();
        return moduleList;
    }
    
    /**
     * The 'getModulesByProgram' function will return a list of modules the passed in program is using
     * 
     * @param programId     The id of the program to search shared programs
     * 
     * @return This function will return a list of module Ids
     */
    @Override
    public List<Integer> getModulesByProgram(Integer programId) throws Exception {
        
        List<Integer> usedModules = new ArrayList<Integer>();
        
        Query query = sessionFactory.getCurrentSession().createQuery("from programModules where programId = :programId");
        query.setParameter("programId", programId);

        List<programModules> moduleList = query.list();
        
        if(moduleList.size() > 0) {
            for(programModules module : moduleList) {
                usedModules.add(module.getModuleId());
            }
        }
        
        return usedModules;
        
    }
    
    /**
     * The 'getUsedModulesByProgram' function will return a list of modules the passed in program is using
     * 
     * @param programId     The id of the program to search shared programs
     * 
     * @return This function will return a list of programModules objects
     */
    @Override
    public List<programModules> getUsedModulesByProgram(Integer programId) throws Exception {
        
        String sqlQuery = "select a.id, a.programId, a.moduleId, a.dspPos, b.moduleName as displayName from program_modules a inner join lu_programModules b on b.id = a.moduleId where a.programId = " + programId;
        
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery) 
        .setResultTransformer(Transformers.aliasToBean(programModules.class)
        );
        
        return query.list();
        
    }
    
    /**
     * The '/saveProgramModules' function will save the list of associated program modules
     * programs
     * 
     * @param newpatientshare   The object holding the patientshare
     */
    @Override
    public void saveProgramModules(programModules module) throws Exception {
       sessionFactory.getCurrentSession().save(module);
    }
    
    
    /**
     * The 'deleteProgramModules' function will remove all the modules for the selected program
     * 
     * @param programId The id of the selected program to delete all modules.
     */
    @Override
    public void deleteProgramModules(Integer programId) throws Exception {
        
        /** Need to first delete current associations **/
        Query q1 = sessionFactory.getCurrentSession().createQuery("delete from programModules where programId = :programId");
        q1.setParameter("programId", programId);
        q1.executeUpdate();
    }
    
    /**
     * The 'getUsedModulesByUser' function will return a list of used modules for a user and program.
     * 
     * @param programId The selected program id.
     * @param userId    The selected user id.
     * @return
     * @throws Exception 
     */
    @Override
    public List<userProgramModules> getUsedModulesByUser(Integer programId, Integer userId) throws Exception {
        Query query = sessionFactory.getCurrentSession().createQuery("from userProgramModules where programId = :programId and systemUserId = :userId");
        query.setParameter("programId", programId);
        query.setParameter("userId", userId);

        List<userProgramModules> moduleList = query.list();
        
        return moduleList;
    }
    
    
    /**
     * The 'removeUsedModulesByUser' function will remove the current list of associated program modules for the selected user and
     * program.
     * 
     * @param programId The selected programId
     * @param userId    The selected userId
     * @throws Exception 
     */
    @Override
    public void removeUsedModulesByUser(Integer programId, Integer userId) throws Exception {
        
        /** Need to first delete current associations **/
        Query q1 = sessionFactory.getCurrentSession().createQuery("delete from userProgramModules where programId = :programId and systemUserId = :userId");
        q1.setParameter("programId", programId);
        q1.setParameter("userId", userId);
        q1.executeUpdate();
    }
    
    /**
     * The 'saveUsedModulesByUser' function will save the selected program modules for the user.
     * 
     * @param module    The userProgramModules object
     * @throws Exception 
     */
    @Override
    public void saveUsedModulesByUser(userProgramModules module) throws Exception {
         sessionFactory.getCurrentSession().save(module);
    }
    
    
    /**
     * The 'getAvailableModules' function will return a list of modules available for the passed in programId
     *
     */
    @Override
    @SuppressWarnings("rawtypes")
    public List getAvailableModules(Integer programId) {
        Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT a.moduleId, b.moduleName from program_modules a inner join lu_programModules b on b.id = a.moduleId where programId = :programId ")
                .setParameter("programId", programId);

        return query.list();
    }
    
}
