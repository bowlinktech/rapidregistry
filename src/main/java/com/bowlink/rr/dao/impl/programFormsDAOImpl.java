/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.dao.impl;

import com.bowlink.rr.dao.programFormsDAO;
import com.bowlink.rr.model.programEngagementFieldValues;
import com.bowlink.rr.model.programEngagementFields;
import com.bowlink.rr.model.programEngagementSections;
import com.bowlink.rr.model.programPatientFieldValues;
import com.bowlink.rr.model.programPatientFields;
import com.bowlink.rr.model.programPatientSections;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
        Query query = sessionFactory.getCurrentSession().createQuery("from programPatientFields where programId = :programId order by dspPos asc");
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
     * The 'getPatientFieldById' function will return the details of a passed in patient field.
     * 
     * @param fieldId The fieldId is the id for the passed in patient field
     * @return This function will return a single programPatientFields object
     * @throws Exception 
     */
    @Override
    public programPatientFields getPatientFieldById(Integer fieldId) throws Exception {
        return (programPatientFields) sessionFactory.getCurrentSession().get(programPatientFields.class, fieldId); 
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
        Query query = sessionFactory.getCurrentSession().createQuery("from programPatientFields where programId = :programId and sectionId = :sectionId order by dspPos asc");
        query.setParameter("programId", programId);
        query.setParameter("sectionId", sectionId);
        
        return query.list();
    }
    
    /**
     * The 'getAllPatientFields' function will return a list of patient fields associated with
     * the passed in program Id.
     * 
     * @param programId     The id of the program to retrieve demographic fields
     *  
     * @return This function will return a list of data elements
     */
    @Override
    public List<programPatientFields> getAllPatientFields(Integer programId) throws Exception {
        Query query = sessionFactory.getCurrentSession().createQuery("from programPatientFields where programId = :programId order by dspPos asc");
        query.setParameter("programId", programId);
        
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
     * The 'deletePatientFields' function will remove an patient field for the passed in field id.
     * 
     * @param fieldId The id that will contain the selected field
     * 
     * @throws Exception 
     */
    @Override
    public void deletePatientField(Integer fieldId) throws Exception {
        Query deleteFields = sessionFactory.getCurrentSession().createQuery("delete from programPatientFields where id = :fieldId");
        deleteFields.setParameter("fieldId", fieldId);
        deleteFields.executeUpdate();
    }
    
    /**
     * The 'savePatientFields' function will save all selected patient fields for the passed in program.
     * 
     * @param field         The programDemoDataElemets object to save
     * @throws Exception 
     */
    @Override
    public Integer savePatientFields(programPatientFields field) throws Exception {
        Integer lastId = null;

        lastId = (Integer) sessionFactory.getCurrentSession().save(field);

        return lastId;
    }
    
    /**
     * The 'savePatientField' function will save all selected patient field for the passed in program.
     * 
     * @param field         The programDemoDataElemets object to save
     * @throws Exception 
     */
    @Override
    public void savePatientField(programPatientFields field) throws Exception {
        sessionFactory.getCurrentSession().update(field);
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
     * The 'savePatientFieldValueFieldId' function will update the patientFieldId.
     */
    @Override
    public void savePatientFieldValueFieldId(Integer oldFieldId, Integer newFieldId) throws Exception {
        Query query = sessionFactory.getCurrentSession().createSQLQuery("UPDATE program_patientFieldValues set patientFieldId = :newFieldId where patientFieldId = :oldFieldId")
                .setParameter("newFieldId", newFieldId)
                .setParameter("oldFieldId", oldFieldId);
        
        query.executeUpdate();
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
        Query query = sessionFactory.getCurrentSession().createQuery("from programEngagementFields where programId = :programId order by dspPos asc");
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
        Query query = sessionFactory.getCurrentSession().createQuery("from programEngagementFields where programId = :programId and sectionId = :sectionId order by dspPos asc");
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
     * The 'deleteEngagementField' function will remove an engagement field for the passed in field id.
     * 
     * @param fieldId The id that will contain the selected field
     * 
     * @throws Exception 
     */
    @Override
    public void deleteEngagementField(Integer fieldId) throws Exception {
        Query deleteFields = sessionFactory.getCurrentSession().createQuery("delete from programEngagementFields where id = :fieldId");
        deleteFields.setParameter("fieldId", fieldId);
        deleteFields.executeUpdate();
    }
    
    /**
     * The 'saveEngagementFields' function will save all selected engagement fields for the passed in program.
     * 
     * @param field         The programEngagementFields object to save
     * @throws Exception 
     */
    @Override
    public Integer saveEngagementFields(programEngagementFields field) throws Exception {
        Integer lastId = null;

        lastId = (Integer) sessionFactory.getCurrentSession().save(field);

        return lastId;
    }
    
    /**
     * The 'saveEngagementField' function will save all selected engagement field for the passed in program.
     * 
     * @param field         The programEngagementFields object to save
     * @throws Exception 
     */
    @Override
    public void saveEngagementField(programEngagementFields field) throws Exception {
        sessionFactory.getCurrentSession().update(field);
    }
    
    /**
     * The 'saveEngagementFieldValueFieldId' function will update the engagementFieldId.
     */
    @Override
    public void saveEngagementFieldValueFieldId(Integer oldFieldId, Integer newFieldId) throws Exception {
        Query query = sessionFactory.getCurrentSession().createSQLQuery("UPDATE program_engagementFieldValues set engagementFieldId = :newFieldId where engagementFieldId = :oldFieldId")
                .setParameter("newFieldId", newFieldId)
                .setParameter("oldFieldId", oldFieldId);
        
        query.executeUpdate();
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
    
    /**
     * The 'getEngagementFieldById' function will return the details of a passed in engagement field.
     * 
     * @param fieldId The fieldId is the id for the passed in engagement field
     * @return This function will return a single programPatientFields object
     * @throws Exception 
     */
    @Override
    public programEngagementFields getEngagementFieldById(Integer fieldId) throws Exception {
        return (programEngagementFields) sessionFactory.getCurrentSession().get(programEngagementFields.class, fieldId); 
    }
    
    /**
     * The 'removeProgramFieldValues' will remove the selected field values for the passed in field Id.
     * 
     * @param fieldId   The id of the selected field
     * @throws Exception 
     */
    @Override
    @Transactional
    public void removeProgramFieldValues(Integer fieldId) throws Exception {
        Query deleteFields = sessionFactory.getCurrentSession().createQuery("delete from programPatientFieldValues where patientFieldId = :fieldId");
        deleteFields.setParameter("fieldId", fieldId);
        deleteFields.executeUpdate();
    }
    
    /**
     * The 'savePatientFieldValue' function will save the selected field values to the field
     * 
     * @param newFieldValue The object holding the selected field value;
     * @throws Exception 
     */
    @Override
    @Transactional
    public void savePatientFieldValue(programPatientFieldValues newFieldValue) throws Exception {
         sessionFactory.getCurrentSession().save(newFieldValue);
    }
    
    /**
     * The 'removeEngagementFieldValues' will remove the selected field values for the passed in field Id.
     * 
     * @param fieldId   The id of the selected field
     * @throws Exception 
     */
    @Override
    @Transactional
    public void removeEngagementFieldValues(Integer fieldId) throws Exception {
        Query deleteFields = sessionFactory.getCurrentSession().createQuery("delete from programEngagementFieldValues where engagementFieldId = :fieldId");
        deleteFields.setParameter("fieldId", fieldId);
        deleteFields.executeUpdate();
    }
    
    /**
     * The 'saveEngagementFieldValue' function will save the selected field values to the field
     * 
     * @param newFieldValue The object holding the selected field value;
     * @throws Exception 
     */
    @Override
    @Transactional
    public void saveEngagementFieldValue(programEngagementFieldValues newFieldValue) throws Exception {
        sessionFactory.getCurrentSession().save(newFieldValue);
    }
    
    @Override
    @Transactional
    public List<programPatientFieldValues> getPatientFieldValues(Integer fieldId) throws Exception {
        Query query = sessionFactory.getCurrentSession().createQuery("from programPatientFieldValues where patientFieldId = :fieldId");
        query.setParameter("fieldId", fieldId);
        
        return query.list();
    }
    
    @Override
    @Transactional
    public List<programEngagementFieldValues> getEngagementFieldValues(Integer fieldId) throws Exception {
        Query query = sessionFactory.getCurrentSession().createQuery("from programEngagementFieldValues where engagementFieldId = :fieldId");
        query.setParameter("fieldId", fieldId);
        
        return query.list();
    }
    
}
