/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bowlink.rr.dao.impl;

import com.bowlink.rr.dao.programDAO;
import com.bowlink.rr.model.program;
import com.bowlink.rr.model.programAdmin;
import com.bowlink.rr.model.programAvailableTables;
import com.bowlink.rr.model.programPatientEntryMethods;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author chadmccue
 */
@Repository
public class programDAOImpl implements programDAO {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    /**
     * The 'createProgram" function will create the new program .
     *
     * @Table	programs
     *
     * @param	newProgram	This will hold the program object from the form
     *
     * @return the function will return the id of the new program
     *
     */
    @Override
    public Integer createProgram(program newProgram) {
        Integer lastId = null;

        lastId = (Integer) sessionFactory.getCurrentSession().save(newProgram);

        return lastId;
    }
    
    /**
     * The 'getProgramByName' function will return all programs based on the programName passed in.
     *
     * @param	programName	This will used to query the programName field of the programs table
     *
     * @return	The function will return a program object
     */
    @Override
    public program getProgramByName(String programName) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(program.class);
        criteria.add(Restrictions.eq("programName", programName));
        
        if(criteria.list().size() > 0) {
            return (program) criteria.list().get(0);
        }
        else {
            return null;
        }
       
    }
    
    /**
     * The 'getProgramByName' function will return all programs based on the programName passed in.
     *
     * @param	programName	This will used to query the programName field of the programs table
     * @param	programId	This will used to find other programs with similar names
     *
     * @return	The function will return a program object
     */
    @Override
    public program getProgramByName(String programName, Integer programId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(program.class);
        criteria.add(Restrictions.eq("programName", programName));
        criteria.add(Restrictions.ne("id", programId));
        
        if(criteria.list().size() > 0) {
            return (program) criteria.list().get(0);
        }
        else {
            return null;
        }
       
    }
    
    /**
     * The 'getAllPrograms' function will return a list of the programs in the system.
     * 
     * @return The function will return a list of progams in the system
     */
    @Override
    public List<program> getAllPrograms() throws Exception {
        Query query = sessionFactory.getCurrentSession().createQuery("from program order by programName asc");

        List<program> programList = query.list();
        return programList;
    }
    
    /**
     * The 'updateProgram" function will create the new program .
     *
     * @Table	programs
     *
     * @param	newProgram	This will hold the program object from the form
     *
     * @return the function does not return any values
     *
     */
    @Override
    public void updateProgram(program program) throws Exception {
        sessionFactory.getCurrentSession().update(program);
    }
    
    
    /**
     * The 'getProgramById' function will return a program based on the id passed in
     * 
     * @param programId        The id to search for
     * 
     * @return This function will return a program object
     */
    @Override
    public program getProgramById(Integer programId) throws Exception {
       return (program) sessionFactory.getCurrentSession().get(program.class, programId); 
    }
    
    /**
     * The 'getOtherPrograms' function will return a list of programs in the system other than the
     * the one with the id passed in.
     * 
     * @param programId     The id of the program not to return
     * 
     * @return This function will returns a list of program objects.
     */
    @Override
    public List<program> getOtherPrograms(Integer programId) throws Exception {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(program.class);
        criteria.add(Restrictions.ne("id", programId));
        
        return criteria.list();
        
    }
    
    /**
     * The 'getProgramsByAdministratory' function will return a list of programs associated to the program admin
     * logging in.
     * 
     * @param userId    The id of the program admin.
     * @return
     * @throws Exception 
     */
    @Override
    public List<program> getProgramsByAdminisrator(Integer userId) throws Exception {
        
        Query query = sessionFactory.getCurrentSession().createQuery("from programAdmin where systemUserId = :systemUserId");
        query.setParameter("systemUserId", userId);
        
        List<programAdmin> adminProgramList = query.list();
        List<Integer> programIds = null;
        
        if(!adminProgramList.isEmpty()) {
            programIds = new CopyOnWriteArrayList<Integer>();
            
            for(programAdmin admin : adminProgramList) {
                programIds.add(admin.getProgramId());
            }
        }
        
        List<program> programs = null;
        
        if(!programIds.isEmpty()) {
            
            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(program.class);
            criteria.add(Restrictions.in("id", programIds));
            
            programs = criteria.list();
            
        }
        
        return programs;
        
    }
    
    /**
     * The '/getProgramAdminsitrators' function will return a list of administrators associated to the passed in
     * programId
     * 
     * @param programId The id of the program to find administrators for.
     * 
     * @Return This function will return a list of programAdmin objects
     */
    @Override
    public List<programAdmin> getProgramAdministrators(Integer programId) throws Exception {
        Query query = sessionFactory.getCurrentSession().createQuery("from programAdmin where programId = :programId");
        query.setParameter("programId", programId);

        List<programAdmin> administratorList = query.list();
        return administratorList;
    }
    
    /**
     * The 'saveAdminProgram' function will save the new program assocition to the new user.
     * 
     * @param adminProgram  The programAdmin object to save
     */
    @Override
    public void saveAdminProgram(programAdmin adminProgram) throws Exception {
        sessionFactory.getCurrentSession().save(adminProgram);
    }
    
    /**
     * The 'removeAdminProgram' function will remove the association between the selected admin and the
     * selected program.
     * 
     * @param programId The id of the selected program
     * @param adminid   The id of the selected admin
     * 
     * @throws Exception 
     */
    @Override
    public void removeAdminProgram(Integer programId, Integer adminid) throws Exception {
        Query removeAdminProgram = sessionFactory.getCurrentSession().createQuery("delete from programAdmin where userId = :adminid and programId = :programId");
        removeAdminProgram.setParameter("adminid", adminid);
        removeAdminProgram.setParameter("programId", programId);
        removeAdminProgram.executeUpdate();
    }
    
    
    /**
     * The 'getPatientEntryMethods' function will return the patient entry methods entered for a program.
     * 
     * @param programId The id of the selected program.
     * 
     * @return  This function will return a list of entry method objects
     * @throws Exception 
     */
    @Override
    public List<programPatientEntryMethods> getPatientEntryMethods(Integer programId) throws Exception {
        Query query = sessionFactory.getCurrentSession().createQuery("from programPatientEntryMethods where programId = :programId");
        query.setParameter("programId", programId);

        List<programPatientEntryMethods> entryMethods = query.list();
        return entryMethods;
    }
    
    /**
     * The 'getPatientEntryMethodBydspPos' function will return the program patient entry method that currently
     * has the dspPos set to the dspPos passed in.
     * 
     * @param dspPos    The display position that we need to find
     * @param programId The id of the program the entry method being updated belongs to.
     * @return 
     */
    @Override
    public programPatientEntryMethods getPatientEntryMethodBydspPos(Integer dspPos, Integer programId) {
        Query query = sessionFactory.getCurrentSession().createQuery("from programPatientEntryMethods where programId = :programId and displayPos = :dspPos");
        query.setParameter("programId", programId);
        query.setParameter("dspPos", dspPos);
        
        return (programPatientEntryMethods) query.uniqueResult();
    }
    
    /**
     * The 'getAvailableTablesForSurveys' function will return the list of tables available for surveys to auto populate from.
     * 
     * @param programId The id of the selected program
     * @return
     * @throws Exception 
     */
    @Override
    public List<programAvailableTables> getAvailableTablesForSurveys(Integer programId) throws Exception {
        Query query = sessionFactory.getCurrentSession().createQuery("from programAvailableTables where programId = :programId");
        query.setParameter("programId", programId);

        List<programAvailableTables> availableTables = query.list();
        return availableTables;
    }
    
    /**
     * The 'saveProgramAvailableTables" function will create/edit the passed in table .
     *
     * @Table	programs
     *
     * @param	availableTable	This will hold the table object from the form
     *
     * @return the function does not return any values
     *
     */
    @Override
    public void saveProgramAvailableTables(programAvailableTables availableTable) throws Exception {
        sessionFactory.getCurrentSession().saveOrUpdate(availableTable);
    }
    
    
    /**
     * The 'getProgramAvailableTable' function will return the saved table for the passed in id.
     * 
     * @param id The selected survey table id
     * @return
     * @throws Exception 
     */
    @Override
    public programAvailableTables getProgramAvailableTable(Integer id) throws Exception {
        return (programAvailableTables) sessionFactory.getCurrentSession().get(programAvailableTables.class, id); 
    }
    
    /**
     * The 'deleteProgramAvaiableTable' function will remove the association between the program and the table
     * 
     * @param id The association id for the program and the table.
     * @throws Exception 
     */
    @Override
    public void deleteProgramAvailableTable(Integer id) throws Exception {
        Query removeProgramTable = sessionFactory.getCurrentSession().createQuery("delete from programAvailableTables where id = :Id");
        removeProgramTable.setParameter("Id", id);
        removeProgramTable.executeUpdate();
    }
    
    /**
     * The 'getpatientEntryMethod' function will return the entry method for the clicked item.
     * 
     * @param id The id of the clicked entry method.
     * @return  This function will return a programPatientEntryMethods object
     * @throws Exception 
     */
    @Override
    public programPatientEntryMethods getpatientEntryMethodDetails(Integer id) throws Exception {
        return (programPatientEntryMethods) sessionFactory.getCurrentSession().get(programPatientEntryMethods.class, id); 
    }
    
    /**
     * The 'saveProgramPatientEntryMethod' function will save the passed in entry method.
     * 
     * @param entryMethod   The object holding the entry method values.
     * @throws Exception 
     */
    @Override
    public void saveProgramPatientEntryMethod(programPatientEntryMethods entryMethod) throws Exception {
        sessionFactory.getCurrentSession().saveOrUpdate(entryMethod);
    }
    
    /**
     * The 'deletePatientEntryMethod' function will remove the clicked program patient entry method.
     * 
     * @param id    The id of the clicked patient entry method.
     * @throws Exception 
     */
    @Override
    public void deletePatientEntryMethod(Integer id) throws Exception {
        Query removeEntryMethod = sessionFactory.getCurrentSession().createQuery("delete from programPatientEntryMethods where id = :Id");
        removeEntryMethod.setParameter("Id", id);
        removeEntryMethod.executeUpdate();
    }
    
    /**
     * The 'getAvailableProgramsForUser' function will return the list of programs not already associated with the passed in userId.
     * 
     * @param userId
     * @return
     * @throws Exception 
     */
    @Override
    public List<program> getAvailbleProgramsForUser(Integer userId) throws Exception {
        
        Query query = sessionFactory.getCurrentSession().createQuery("from programAdmin where systemUserId = :systemUserId");
        query.setParameter("systemUserId", userId);
        
        List<programAdmin> programList = query.list();
        List<Integer> programIds = null;
        
        if(!programList.isEmpty()) {
            programIds = new CopyOnWriteArrayList<Integer>();
            
            for(programAdmin program : programList) {
                programIds.add(program.getProgramId());
            }
        }
        
        List<program> programs = null;
        
        if(!programIds.isEmpty()) {
            
            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(program.class);
            criteria.add(Restrictions.not(Restrictions.in("id", programIds)));
            
            programs = criteria.list();
            
        }
        
        return programs;
        
    }
    
    
}
