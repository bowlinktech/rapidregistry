/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.dao.impl;

import com.bowlink.rr.dao.programFormsDAO;
import com.bowlink.rr.model.programPatientFields;
import com.bowlink.rr.model.programPatientSections;
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
public class programFormsDAOImpl implements programFormsDAO {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    /**
     * The 'getPatientSections' function will return a list of sections created for the passed in programId.
     * 
     * @param programId The id that will contain the selected program
     * 
     * @return  This function will return a list of program patient sections.
     * @throws Exception 
     */
    @Override
    public List<programPatientSections> getPatientSections(Integer programId) throws Exception {
        Query query = sessionFactory.getCurrentSession().createQuery("from programPatientSections where programId = :programId");
        query.setParameter("programId", programId);
        
        return query.list();
    }
    
    /**
     * The 'getPatientFieldsByProgramId' function will return a list of patient fields associated with
     * the passed in program Id.
     * 
     * @param programId     The id of the program to retrieve demographic fields
     *  
     * @return This function will return a list of data elements
     */
    @Override
    public List<programPatientFields> getPatientFieldsByProgramId(Integer programId) throws Exception {
        Query query = sessionFactory.getCurrentSession().createQuery("from programPatientFields where programId = :programId");
        query.setParameter("programId", programId);
        
        return query.list();
    }
    
    /**
     * The 'getPatientSectionById' function will return the details for the clicked section.
     * 
     * @param sectionId The id of the selected section.
     * @return
     * @throws Exception 
     */
    @Override
    public programPatientSections getPatientSectionById(Integer sectionId) throws Exception {
        return (programPatientSections) sessionFactory.getCurrentSession().get(programPatientSections.class, sectionId); 
    }
    
    
    /**
     * The 'getPatientFields' function will return a list of patient fields associated with
     * the passed in program Id and section.
     * 
     * @param programId     The id of the program to retrieve demographic fields
     * @param sectionId The id that will contain the selected section
     *  
     * @return This function will return a list of data elements
     */
    @Override
    public List<programPatientFields> getPatientFields(Integer programId, Integer sectionId) throws Exception {
        Query query = sessionFactory.getCurrentSession().createQuery("from programPatientFields where programId = :programId and sectionId = :sectionId");
        query.setParameter("programId", programId);
        query.setParameter("sectionId", sectionId);
        
        return query.list();
    }
    
    /**
     * The 'deletePatientFields' function will remove all patient fields for the passed in program and section.
     * 
     * @param programId The id that will contain the selected program
     * @param sectionId The id that will contain the selected section
     * 
     * @throws Exception 
     */
    @Override
    public void deletePatientFields(Integer programId, Integer sectionId) throws Exception {
        Query deleteFields = sessionFactory.getCurrentSession().createQuery("delete from programPatientFields where programId = :programId and sectionId = :sectionId");
        deleteFields.setParameter("programId", programId);
        deleteFields.setParameter("sectionId", sectionId);
        deleteFields.executeUpdate();
    }
    
    /**
     * The 'savePatientFields' function will save all selected patient fields for the passed in program.
     * 
     * @param field         The programDemoDataElemets object to save
     * @throws Exception 
     */
    @Override
    public void savePatientFields(programPatientFields field) throws Exception {
        sessionFactory.getCurrentSession().save(field);
    }
    
}
