/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bowlink.rr.dao.impl;

import com.bowlink.rr.dao.moduleDAO;
import com.bowlink.rr.model.modules;
import com.bowlink.rr.model.programModules;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
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
    
}
