/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bowlink.rr.dao.impl;

import com.bowlink.rr.dao.moduleDAO;
import com.bowlink.rr.model.modules;
import com.bowlink.rr.model.programModules;
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
    
}
