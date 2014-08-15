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
import com.bowlink.rr.model.programMPI;
import com.bowlink.rr.model.programMPIFields;
import com.bowlink.rr.model.programModules;
import com.bowlink.rr.model.programReports;
import com.bowlink.rr.model.programAdmin;
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
     * The 'getUsedActivityCodes' function will check to see if the passed in activity code is associated with the
     * passed in program Id
     * 
     * @param codeId    The id of the activity code passed in.
     * @param programId The id of the program to search on
     * @return  This function will return either true or false.
     * @throws Exception 
     */
    @Override
    public boolean getUsedActivityCodes(Integer programId, Integer codeId) throws Exception {
        Query query = sessionFactory.getCurrentSession().createQuery("from programActivityCodes where programId = :programId and codeId = :codeId");
        query.setParameter("programId", programId);
        query.setParameter("codeId", codeId);
        
        if(query.list().isEmpty()) {
            return false;
        }
        else {
            return true;
        }
        
    }
    
    /**
     * The 'saveProgramActivityCode' function will save the associated activity codes to the passed in 
     * program.
     * 
     * @param   newCodeAssoc    The new activity code and program object
     */
    @Override
    public void saveProgramActivityCode(programActivityCodes newCodeAssoc) throws Exception {
        sessionFactory.getCurrentSession().save(newCodeAssoc);
    }
    
    /**
     * The 'removeProgramActivityCodes' function will remove the associated activity codes with the passed in
     * program.
     * 
     * @param programId     The id of the passed in program
     */
    @Override
    public void removeProgramActivityCodes(Integer programId) throws Exception {
        Query deleteActivityCodes = sessionFactory.getCurrentSession().createQuery("delete from programActivityCodes where programId = :programId");
        deleteActivityCodes.setParameter("programId", programId);
        deleteActivityCodes.executeUpdate();
    }
    
    /**
     * The 'getProgramReports' function will return a list of reports the passed in program is using
     * 
     * @param programId     The id of the program to search used reports
     * 
     * @return This function will return a list of report Ids
     */
    @Override
    public List<Integer> getProgramReports(Integer programId) throws Exception {
        
        List<Integer> usedReports = new ArrayList<Integer>();
        
        Query query = sessionFactory.getCurrentSession().createQuery("from programReports where programId = :programId");
        query.setParameter("programId", programId);

        List<programReports> reportList = query.list();
        
        if(reportList.size() > 0) {
            for(programReports report : reportList) {
                usedReports.add(report.getReportId());
            }
        }
        
        return usedReports;
        
    }
    
    /**
     * The '/saveProgramReports' function will save the list of associated program reports
     * programs
     * 
     * @param report   The object holding the selected report
     */
    @Override
    public void saveProgramReports(programReports report) throws Exception {
       sessionFactory.getCurrentSession().save(report);
    }
    
    
    /**
     * The 'deleteProgramReports' function will remove all the reports for the selected program
     * 
     * @param programId The id of the selected program to delete all reports.
     */
    @Override
    public void deleteProgramReports(Integer programId) throws Exception {
        
         /** Need to first delete current associations **/
        Query q1 = sessionFactory.getCurrentSession().createQuery("delete from programReports where programId = :programId");
        q1.setParameter("programId", programId);
        q1.executeUpdate();
    }
    
    /**
     * The 'getProgramMPIAlgorithms' function will return a list of the programs in the system.
     * 
     * @return The function will return a list of progams in the system
     */
    @Override
    public List<programMPI> getProgramMPIAlgorithms(Integer programId) throws Exception {
        Query query = sessionFactory.getCurrentSession().createQuery("from programMPI where programId = :programId order by id asc");
        query.setParameter("programId", programId);

        List<programMPI> MPIList = query.list();
        return MPIList;
    }
    
    /**
     * The 'getProgramMPIFields' function will get a list of fields associated to teach MPI Algorithm.
     * 
     * @param mpiId The id of the MPI Algorithm
     * @return  This function will return a list of MPI Fields
     * @throws Exception 
     */
    @Override
    public List<programMPIFields> getProgramMPIFields(Integer mpiId) throws Exception {
        Query query = sessionFactory.getCurrentSession().createQuery("from programMPIFields where mpiId = :mpiId order by id asc");
        query.setParameter("mpiId", mpiId);

        List<programMPIFields> fieldList = query.list();
        return fieldList;
    }
    
    /**
     * The 'createMPIAlgorithm' function will create the new MPI Algorithm
     * 
     * @param newMPIAlgorithm   The object that holds the new algorithm
     * 
     * @return This function will return the id from the new algorithm
     */
    @Override
    public Integer createMPIAlgorithm(programMPI newMPIAlgorithm) throws Exception {
        Integer lastId = null;

        lastId = (Integer) sessionFactory.getCurrentSession().save(newMPIAlgorithm);

        return lastId;
    }
    
    /**
     * The 'updateMPIAlgorithm' function will update the selected MPI Algorithm
     * 
     * @param MPIAlgorithm   The object that holds the selected algorithm
     * 
     * @return This function will not return anything
     */
    @Override
    public void updateMPIAlgorithm(programMPI MPIAlgorithm) throws Exception {
        sessionFactory.getCurrentSession().update(MPIAlgorithm);
    }
    
    
    /**
     * The 'createMPIAlgorithmFields' function will save the fields associated to the MPI 
     * algorithm.
     * 
     * 
     */
    @Override
    public void createMPIAlgorithmFields(programMPIFields newField) throws Exception {
        sessionFactory.getCurrentSession().save(newField);
    }
    
    /**
     * The 'getMPIAlgorithm' function will return the details of the passed in mpi algorithm.
     * 
     * @param mpiId The id of the selected MPI algorithm
     * @return
     * @throws Exception 
     */
    @Override
    public programMPI getMPIAlgorithm(Integer mpiId) throws Exception {
        Query query = sessionFactory.getCurrentSession().createQuery("from programMPI where id = :mpiId");
        query.setParameter("mpiId", mpiId);

        return (programMPI) query.uniqueResult();
    }

    /**
     * The 'removeAlgorithmField' function will remove the selected field from the MPI algorithm
     * 
     * @param algorithmFieldId  The id of the selected MPI Algorithm field
     */
    @Override
    public void removeAlgorithmField(Integer algorithmFieldId) throws Exception {
        Query deleteAlgorithmField = sessionFactory.getCurrentSession().createQuery("delete from programMPIFields where id = :algorithmFieldId");
        deleteAlgorithmField.setParameter("algorithmFieldId", algorithmFieldId);
        deleteAlgorithmField.executeUpdate();
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
    public void saveAdminProgram(programAdmin adminProgram) throws Exception {
        sessionFactory.getCurrentSession().save(adminProgram);
    }
}
