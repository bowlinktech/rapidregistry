/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.dao.impl;

import com.bowlink.rr.dao.programFormsDAO;
import com.bowlink.rr.model.programEngagementFields;
import com.bowlink.rr.model.programEngagementSections;
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
    
    /**
     * The 'savePatientSection' function will save all selected patient section for the passed in program.
     * 
     * @param patientSection         The programPatientSections object to save
     * @throws Exception 
     */
    @Override
    public void savePatientSection(programPatientSections patientSection) throws Exception {
        sessionFactory.getCurrentSession().saveOrUpdate(patientSection);
    }
    
    /**
     * The 'getPatientSectionBydspPos' function will return the an patient section that currently
     * has the dspPos set to the dspPos passed in, will ignore section that is to be updated.
     * 
     * @param sectionId The id of the section that wants the new display Position
     * @param dspPos    The display position that we need to find
     * @param programId The id of the program the section being updated belongs to.
     * @return 
     */
    @Override
    public programPatientSections getPatientSectionBydspPos(Integer dspPos, Integer programId) {
        Query query = sessionFactory.getCurrentSession().createQuery("from programPatientSections where programId = :programId and dspPos = :dspPos");
        query.setParameter("programId", programId);
        query.setParameter("dspPos", dspPos);
        
        return (programPatientSections) query.uniqueResult();
    }
    
    
    /**
     * The 'getEngagementSections' function will return a list of sections created for the passed in programId.
     * 
     * @param programId The id that will contain the selected program
     * 
     * @return  This function will return a list of program engagement sections.
     * @throws Exception 
     */
    @Override
    public List<programEngagementSections> getEngagementSections(Integer programId) throws Exception {
        Query query = sessionFactory.getCurrentSession().createQuery("from programEngagementSections where programId = :programId");
        query.setParameter("programId", programId);
        
        return query.list();
    }
    
    /**
     * The 'getEngagementFieldsByProgramId' function will return a list of engagement fields associated with
     * the passed in program Id.
     * 
     * @param programId     The id of the program to retrieve engagement fields
     *  
     * @return This function will return a list of data elements
     */
    @Override
    public List<programEngagementFields> getEngagementFieldsByProgramId(Integer programId) throws Exception {
        Query query = sessionFactory.getCurrentSession().createQuery("from programEngagementFields where programId = :programId");
        query.setParameter("programId", programId);
        
        return query.list();
    }
    
    /**
     * The 'getEngagementSectionById' function will return the details for the clicked section.
     * 
     * @param sectionId The id of the selected section.
     * @return
     * @throws Exception 
     */
    @Override
    public programEngagementSections getEngagementSectionById(Integer sectionId) throws Exception {
        return (programEngagementSections) sessionFactory.getCurrentSession().get(programEngagementSections.class, sectionId); 
    }
    
    
    /**
     * The 'getEngagementFields' function will return a list of engagement fields associated with
     * the passed in program Id and section.
     * 
     * @param programId     The id of the program to retrieve engagement section fields
     * @param sectionId The id that will contain the selected section
     *  
     * @return This function will return a list of data elements
     */
    @Override
    public List<programEngagementFields> getEngagementFields(Integer programId, Integer sectionId) throws Exception {
        Query query = sessionFactory.getCurrentSession().createQuery("from programEngagementFields where programId = :programId and sectionId = :sectionId");
        query.setParameter("programId", programId);
        query.setParameter("sectionId", sectionId);
        
        return query.list();
    }
    
    /**
     * The 'deleteEngagementFields' function will remove all engagement fields for the passed in program and section.
     * 
     * @param programId The id that will contain the selected program
     * @param sectionId The id that will contain the selected section
     * 
     * @throws Exception 
     */
    @Override
    public void deleteEngagementFields(Integer programId, Integer sectionId) throws Exception {
        Query deleteFields = sessionFactory.getCurrentSession().createQuery("delete from programEngagementFields where programId = :programId and sectionId = :sectionId");
        deleteFields.setParameter("programId", programId);
        deleteFields.setParameter("sectionId", sectionId);
        deleteFields.executeUpdate();
    }
    
    /**
     * The 'saveEngagementFields' function will save all selected engagement fields for the passed in program.
     * 
     * @param field         The programEngagementFields object to save
     * @throws Exception 
     */
    @Override
    public void saveEngagementFields(programEngagementFields field) throws Exception {
        sessionFactory.getCurrentSession().save(field);
    }
    
    /**
     * The 'saveEngagementSection' function will save all selected engagement section for the passed in program.
     * 
     * @param engagementSection         The programEngagementSections object to save
     * @throws Exception 
     */
    @Override
    public void saveEngagementSection(programEngagementSections engagementSection) throws Exception {
        sessionFactory.getCurrentSession().saveOrUpdate(engagementSection);
    }
    
    /**
     * The 'getEngagementSectionBydspPos' function will return the an engagement section that currently
     * has the dspPos set to the dspPos passed in, will ignore section that is to be updated.
     * 
     * @param sectionId The id of the section that wants the new display Position
     * @param dspPos    The display position that we need to find
     * @param programId The id of the program the section being updated belongs to.
     * @return 
     */
    @Override
    public programEngagementSections getEngagementSectionBydspPos(Integer dspPos, Integer programId) {
        Query query = sessionFactory.getCurrentSession().createQuery("from programEngagementSections where programId = :programId and dspPos = :dspPos");
        query.setParameter("programId", programId);
        query.setParameter("dspPos", dspPos);
        
        return (programEngagementSections) query.uniqueResult();
    }
    
}
