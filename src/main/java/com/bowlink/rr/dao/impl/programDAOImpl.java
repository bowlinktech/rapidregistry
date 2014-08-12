/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bowlink.rr.dao.impl;

import com.bowlink.rr.dao.programDAO;
import com.bowlink.rr.model.patientSharing;
import com.bowlink.rr.model.program;
import com.bowlink.rr.model.programActivityCodes;
import com.bowlink.rr.model.programDemoDataElements;
import com.bowlink.rr.model.programHealthDataElements;
import com.bowlink.rr.model.programModules;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
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
     * @return This function will returna a list of program objects.
     */
    @Override
    public List<program> getOtherPrograms(Integer programId) throws Exception {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(program.class);
        criteria.add(Restrictions.ne("id", programId));
        
        return criteria.list();
        
    }
    
    /**
     * The 'getSharedPrograms' function will return a list of programs the passed in program is sharing
     * patient data with.
     * 
     * @param programId     The id of the program to search shared programs
     * 
     * @return This function will return a list of shared program Ids
     */
    @Override
    public List<Integer> getSharedPrograms(Integer programId) throws Exception {
        
        List<Integer> sharedPrograms = new ArrayList<Integer>();
        
        Query query = sessionFactory.getCurrentSession().createQuery("from patientSharing where programId = :programId");
        query.setParameter("programId", programId);

        List<patientSharing> programList = query.list();
        
        if(programList.size() > 0) {
            for(patientSharing sharedProgram : programList) {
                sharedPrograms.add(sharedProgram.getSharingProgramId());
            }
        }
        
        return sharedPrograms;
        
    }
    
    /**
     * The '/savePatientSharing' function will save the patient sharing between the selected program and list of other
     * programs
     * 
     * @param newpatientshare   The object holding the patientshare
     */
    @Override
    public void savePatientSharing(patientSharing newpatientshare) throws Exception {
       sessionFactory.getCurrentSession().save(newpatientshare);
    }
    
    /**
     * The 'deletePatientSharing' function will remove all the patient sharing for the selected program
     * 
     * @param programId The id of the selected program to delete all patient sharing.
     */
    @Override
    public void deletePatientSharing(Integer programId) throws Exception {
        
         /** Need to first delete current associations **/
        Query q1 = sessionFactory.getCurrentSession().createQuery("delete from patientSharing where programId = :programId");
        q1.setParameter("programId", programId);
        q1.executeUpdate();
    }
    
    
    /**
     * The 'getProgramModules' function will return a list of modules the passed in program is using
     * 
     * @param programId     The id of the program to search shared programs
     * 
     * @return This function will return a list of module Ids
     */
    @Override
    public List<Integer> getProgramModules(Integer programId) throws Exception {
        
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
    
    
    /**
     * The 'getProgramDemoFields' function will return a list of selected demographic fields associated with
     * the passed in program Id.
     * 
     * @param programId     The id of the program to retrieve demographic fields
     * 
     * @return This function will return a list of data elements
     */
    @Override
    public List<programDemoDataElements> getProgramDemoFields(Integer programId) throws Exception {
        Query query = sessionFactory.getCurrentSession().createQuery("from programDemoDataElements where programId = :programId");
        query.setParameter("programId", programId);
        
        return query.list();
    }
    
    /**
     * The 'deleteDemoFields' function will remove all data fields saved for the passed in program.
     * 
     * @param programId The id that will contain the selected program
     * 
     * @throws Exception 
     */
    @Override
    public void deleteDemoFields(Integer programId) throws Exception {
        Query deleteTranslations = sessionFactory.getCurrentSession().createQuery("delete from programDemoDataElements where programId = :programId");
        deleteTranslations.setParameter("programId", programId);
        deleteTranslations.executeUpdate();
    }
    
    /**
     * The 'saveDemoFields' function will save all selected demo fields for the passed in program.
     * 
     * @param field         The programDemoDataElemets object to save
     * @throws Exception 
     */
    @Override
    public void saveDemoFields(programDemoDataElements field) throws Exception {
        sessionFactory.getCurrentSession().save(field);
    }
    
    /**
     * The 'getProgramHealthFields' function will return a list of selected health fields associated with
     * the passed in program Id.
     * 
     * @param programId     The id of the program to retrieve demographic fields
     * 
     * @return This function will return a list of data elements
     */
    @Override
    public List<programHealthDataElements> getProgramHealthFields(Integer programId) throws Exception {
        Query query = sessionFactory.getCurrentSession().createQuery("from programHealthDataElements where programId = :programId");
        query.setParameter("programId", programId);
        
        return query.list();
    }
    
    /**
     * The 'deleteHealthFields' function will remove all health data fields saved for the passed in program.
     * 
     * @param programId The id that will contain the selected program
     * 
     * @throws Exception 
     */
    @Override
    public void deleteHealthFields(Integer programId) throws Exception {
        Query deleteTranslations = sessionFactory.getCurrentSession().createQuery("delete from programHealthDataElements where programId = :programId");
        deleteTranslations.setParameter("programId", programId);
        deleteTranslations.executeUpdate();
    }
    
    /**
     * The 'saveHealthFields' function will save all selected health fields for the passed in program.
     * 
     * @param field         The programHealthDataElements object to save
     * @throws Exception 
     */
    @Override
    public void saveHealthFields(programHealthDataElements field) throws Exception {
        sessionFactory.getCurrentSession().save(field);
    }
    
    /**
     * The 'getActivityCodes' function will return a list of associated activity codes for the passed
     * in program Id
     * 
     * @param programId The id of the program to search on
     * @return  This function will return a list of program activity code objects.
     * @throws Exception 
     */
    @Override
    public List<programActivityCodes> getActivityCodes(Integer programId) throws Exception {
        Query query = sessionFactory.getCurrentSession().createQuery("from programActivityCodes where programId = :programId");
        query.setParameter("programId", programId);
        
        return query.list();
    }
}
