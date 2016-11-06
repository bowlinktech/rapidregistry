/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.dao.impl;

import com.bowlink.rr.dao.dataElementDAO;
import com.bowlink.rr.model.crosswalkData;
import com.bowlink.rr.model.crosswalks;
import com.bowlink.rr.model.customProgramFields;
import com.bowlink.rr.model.dataElements;
import com.bowlink.rr.model.reportCrossTabCWData;

import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author chadmccue
 */
@Repository
public class dataElementDAOImpl implements dataElementDAO {
    
    @Resource(name = "myProps")
    private Properties myProps;

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * The 'getDemoDataElements' function will return a list of all available demographic data elements.
     *
     *
     */
    @Override
    public List<dataElements> getdataElements() throws Exception {

        Query query = sessionFactory.getCurrentSession().createQuery("from dataElements order by elementName asc");

        List<dataElements> fieldList = query.list();

        return fieldList;

    }
    
    /**
     * The 'getCrosswalks' function will return the list of available crosswalks to associate a message types to. This function will only return crosswalks not associated to a specific organization.
     *
     * @param page	The current crosswalk page
     * @param	maxResults	The maximum number of crosswalks to return from each query
     * @param	orgId	The organization id (default 0)
     *
     * @Table	crosswalks
     *
     * @Return	This function will return a list of crosswalks
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<crosswalks> getCrosswalks(int page, int maxResults, int programId) {

        Query query = null;

        if (programId == 0) {
            query = sessionFactory.getCurrentSession().createQuery("from crosswalks where programId = 0 order by name asc");
        } else {
            query = sessionFactory.getCurrentSession().createQuery("from crosswalks where (programId = 0 or programId = :programId) order by name asc");
            query.setParameter("programId", programId);
        }

        int firstResult = 0;

        //Set the parameters for paging
        //Set the page to load
        if (page > 1) {
            firstResult = (maxResults * (page - 1));
        }
        query.setFirstResult(firstResult);

        //Set the max results to display
        //If 0 is passed then we want all crosswalks
        if (maxResults > 0) {
            query.setMaxResults(maxResults);
        }

        return query.list();

    }

    /**
     * The 'getValidationTypes' function will return a list of available field validation types
     *
     */
    @Override
    @SuppressWarnings("rawtypes")
    public List getValidationTypes() {
        Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT id, validationType FROM lu_validationtypes order by id asc");

        return query.list();
    }

    /**
     * The 'getDemoFieldName' function will return the name of a field based on the fieldId passed in. This is used for display purposes to show the actual field lable instead of a field name.
     *
     * @param fieldId	This will hold the id of the field to retrieve
     *
     * @Return This function will return a string (field name)
     */
    @Override
    @Transactional
    public String getfieldName(int fieldId) {
        Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT elementName FROM dataElements where id = :fieldId")
                .setParameter("fieldId", fieldId);

        String fieldName = (String) query.uniqueResult();

        return fieldName;
    }
    
    /**
     * The 'getCrosswalkName' function will return the name of a crosswalk based on the id passed in.
     *
     * @param cwId	This will hold the id of the crosswalk to retrieve
     *
     * @Return This function will return a string (crosswalk name).
     */
    @Override
    @Transactional
    public String getCrosswalkName(int cwId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(crosswalks.class);
        criteria.add(Restrictions.eq("id", cwId));

        crosswalks cwDetails = (crosswalks) criteria.uniqueResult();

        String cwName = "";

        if (cwDetails.getProgramId() > 0) {
            cwName = cwDetails.getName() + " (Program Specific)";
        } else {
            cwName = cwDetails.getName();
        }

        return cwName;
    }

    
    /**
     * The 'getValidationName' function will return the name of the selected validation passed in. 
     * This is used for display purposes to show the actual validation name instead.
     *
     * @param validationId	This will hold the id of the validation type to retrieve
     *
     * @Return This function will return a string (validation name)
     */
    @Override
    @Transactional
    public String getValidationName(int validationId) {
        Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT validationType FROM lu_validationtypes where id = :validationId")
                .setParameter("validationId", validationId);

        String validationName = (String) query.uniqueResult();

        return validationName;
    }
    
    /**
     * The 'findTotalCrosswalks' function will return the total number of generic crosswalks in the system
     *
     * @param programId Will pass the programId this will help determine if I want all crosswalks or generic system only crosswalks
     *
     * @Table	crosswalks
     *
     *
     * @Return	This function will return the total number of generic crosswalks set up in the system
     */
    @Override
    public double findTotalCrosswalks(int programId) {

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(crosswalks.class);

        if (programId == 0) {
            criteria.add(Restrictions.eq("programId", 0));
        }
        else {
           Disjunction or = Restrictions.disjunction();
           or.add(Restrictions.eq("programId", 0));
           or.add(Restrictions.eq("programId", programId));
           criteria.add(or);
            
        }

        double totalCrosswalks = (double) criteria.list().size();

        return totalCrosswalks;
    }
    
    /**
     * The 'getDelimiters' function will return a list of available file delimiters
     *
     */
    @Override
    @SuppressWarnings("rawtypes")
    @Transactional
    public List getDelimiters() {
        Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT id, delimiter FROM lu_delimiters order by delimiter asc");

        return query.list();
    }

    /**
     * The 'getDelimiterChar' will return the actual character of the delimiter for the id passed into the function
     *
     * @param id	The id will hold the delimiter ID to retrieve its associated character
     *
     * @returns string
     */
    @Transactional
    public String getDelimiterChar(int id) {
        Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT delimChar FROM lu_delimiters where id = :id");
        query.setParameter("id", id);

        String delimChar = (String) query.uniqueResult();

        return delimChar;
    }
    
    /**
     *
     */
    @Override
    @Transactional
    public Long checkCrosswalkName(String name, int programId) {
        Query query = null;

        if (programId > 0) {
            query = sessionFactory.getCurrentSession().createQuery("select count(id) as total from crosswalks where name = :name and programId = :programId");
            query.setParameter("name", name);
            query.setParameter("programId", programId);
        } else {
            query = sessionFactory.getCurrentSession().createQuery("select count(id) as total from crosswalks where name = :name");
            query.setParameter("name", name);
        }

        Long cwId = (Long) query.uniqueResult();

        return cwId;
    }

    /**
     * The 'createCrosswalk" function will create the new crosswalk
     *
     * @Table	crosswalks
     *
     * @param	crosswalkDetails	This will hold the crosswalk object from the form
     *
     * @return The function will return the id of the new crosswalk
     *
     */
    @Override
    public Integer createCrosswalk(crosswalks crosswalkDetails) {
        Integer lastId = null;

        lastId = (Integer) sessionFactory.getCurrentSession().save(crosswalkDetails);

        return lastId;
    }
    
    /**
     * The 'updateCrosswalk" function will update the existing crosswalk
     *
     * @Table	crosswalks
     *
     * @param	crosswalkDetails	This will hold the crosswalk object from the form
     *
     * @return The function will return the id of the new crosswalk
     *
     */
    @Override
    public void updateCrosswalk(crosswalks crosswalkDetails) {
        sessionFactory.getCurrentSession().update(crosswalkDetails);
    }

    /**
     * The 'getCrosswalk' function will return a single crosswalk object based on the id passed in.
     *
     * @param	cwId	This will be id to find the specific crosswalk
     *
     * @return	The function will return a crosswalk object
     */
    @Override
    public crosswalks getCrosswalk(int cwId) {
        return (crosswalks) sessionFactory.getCurrentSession().get(crosswalks.class, cwId);
    }

    /**
     * The 'getDelimiters' function will return a list of available file delimiters
     *
     * @param	cwId	This will be the id of the crosswalk to return the associated data elements for
     *
     * @return	The function will return a list of data objects for the crosswalk
     *
     */
    @Override
    @SuppressWarnings("rawtypes")
    @Transactional
    public List getCrosswalkData(int cwId) {
        Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT sourceValue, targetValue, descValue, id FROM rel_crosswalkData where crosswalkId = :crosswalkid order by id asc");
        query.setParameter("crosswalkid", cwId);

        return query.list();
    }
    
    /**
     * The 'getInformationTables' function will return a list of all available information tables where we can associate fields to an actual table and column.
     */
    @Override
    @SuppressWarnings("rawtypes")
    @Transactional
    public List getInformationTables() {
        Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT distinct table_name FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = '"+ myProps.getProperty("schemaName") +"' and (TABLE_NAME = 'programpatiententities' or TABLE_NAME = 'submittedsurveys' OR TABLE_NAME = 'programprofiles' OR TABLE_NAME LIKE 'storage\\_%')");

        return query.list();
    }
    
    /**
     * The 'getAllTables' function will return a list of all available tables where we can associate fields to an actual table and column.
     */
    @Override
    @SuppressWarnings("rawtypes")
    @Transactional
    public List getAllTables() {
        Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT distinct table_name FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = '"+ myProps.getProperty("schemaName") +"' ");

        return query.list();
    }
    
    /**
     * The 'getTableColumns' function will return a list of columns from the passed in table name
     *
     */
    @Override
    @SuppressWarnings("rawtypes")
    @Transactional
    public List getTableColumns(String tableName) {
        Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = '"+ myProps.getProperty("schemaName") +"' AND TABLE_NAME = :tableName and COLUMN_NAME not in ('id', 'dateCreated') order by COLUMN_NAME")
                .setParameter("tableName", tableName);

        return query.list();
    }
    
    /**
     * The 'saveField' function will save the new data element
     * @param formField The object holding the data element object
     * @throws Exception 
     */
    @Override
    public void saveField(dataElements formField) throws Exception {
        sessionFactory.getCurrentSession().saveOrUpdate(formField);
    }
    
    
    /**
     * The 'getFieldDetails' function will return the details of the selected
     * data element.
     * 
     * @param fieldId The id of the selected field
     * @return
     * @throws Exception 
     */
    @Override
    public dataElements getFieldDetails(Integer fieldId) throws Exception {
        return (dataElements) sessionFactory.getCurrentSession().get(dataElements.class, fieldId); 
    }
    
    /**
     * The 'getAnswerTypes' function will return a list of available field types
     *
     */
    @Override
    @SuppressWarnings("rawtypes")
    @Transactional
    public List getAnswerTypes() {
        Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT id, answerType FROM lu_answerTypes order by answerType asc");

        return query.list();
    }
    
    
    /**
     * The 'getLookUpTables' function will return a list of all available look up tables where we can associate a data element to.
     */
    @Override
    @SuppressWarnings("rawtypes")
    @Transactional
    public List getLookUpTables() {
        Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT distinct table_name FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = '"+ myProps.getProperty("schemaName") +"' and TABLE_NAME LIKE 'lu\\_%'");

        return query.list();
    }
    
    @Override
    @SuppressWarnings("rawtypes")
    @Transactional
    public List getLookupTableValues(Integer fieldId) throws Exception {
        dataElements fieldDetails = getFieldDetails(fieldId);
        
        Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT id, displayText from " + fieldDetails.getPopulateFromTable() + " where status = 1 order by displayText asc");

        return query.list();
    }
    
    @Override
    @SuppressWarnings("rawtypes")
    @Transactional
    public List getLookupTableValues(String tableName) throws Exception {
        
        Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT id, displayText from " + tableName + " where status = 1 order by displayText asc");

        return query.list();
    }
    
    @Override
    @SuppressWarnings("rawtypes")
    @Transactional
    public List getLookupTableValues(String tableName, Integer programId) throws Exception {
        
        try {
            Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT id, displayText from " + tableName + " where status = 1 order by displayText asc");

            return query.list();
        }
        catch (Exception e) {
            try {
                Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT id, name from " + tableName + " where programId = " + programId + " order by name asc");
                return query.list();
            }
            catch(Exception ex) {
                return null;
            }
        }
    }
    
    /**
     * The 'getCustomFields' function will return the list of available custom program fields
     *
     * @param page	The current crosswalk page
     * @param	maxResults	The maximum number of customProgramFields to return from each query
     * @param	programId	The programId id (default 0)
     *
     * @Table	customProgramFields
     *
     * @Return	This function will return a list of customProgramFields
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<customProgramFields> getCustomFields(int page, int maxResults, int programId) {

        Query query = null;

        if (programId == 0) {
            query = sessionFactory.getCurrentSession().createQuery("from customProgramFields where programId = 0 order by saveToTableCol * 1 asc");
        } else {
            query = sessionFactory.getCurrentSession().createQuery("from customProgramFields where (programId = 0 or programId = :programId) order by saveToTableCol * 1 asc");
            query.setParameter("programId", programId);
        }

        int firstResult = 0;

        //Set the parameters for paging
        //Set the page to load
        if (page > 1) {
            firstResult = (maxResults * (page - 1));
        }
        query.setFirstResult(firstResult);

        //Set the max results to display
        //If 0 is passed then we want all crosswalks
        if (maxResults > 0) {
            query.setMaxResults(maxResults);
        }

        return query.list();

    }
    
    /**
     * The 'findTotalCustomFields' function will return the total number of custom fields in the system
     *
     * @param programId Will pass the programId 
     *
     * @Table	crosswalks
     *
     *
     * @Return	This function will return the total number of custom fields set up in the system for the passed in program
     */
    @Override
    public double findTotalCustomFields(int programId) {

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(customProgramFields.class);

        if (programId == 0) {
            criteria.add(Restrictions.eq("programId", 0));
        }
        else {
           Disjunction or = Restrictions.disjunction();
           or.add(Restrictions.eq("programId", 0));
           or.add(Restrictions.eq("programId", programId));
           criteria.add(or);
            
        }

        double totalCustomFields = (double) criteria.list().size();

        return totalCustomFields;
    }
    
    /**
     * The 'getCrosswalk' function will return the details for the passed in custom field.
     *
     * @param	fieldId	This will be id to find the specific field
     *
     * @return	The function will return a customProgramFields object
     */
    @Override
    public customProgramFields getCustomField(int fieldId) {
        return (customProgramFields) sessionFactory.getCurrentSession().get(customProgramFields.class, fieldId);
    }
    
    /**
     *
     */
    @Override
    public Long checkCustomFieldName(String name, int programId, int fieldId) {
        Query query = sessionFactory.getCurrentSession().createQuery("select count(id) as total from customProgramFields where id <> :fieldId and fieldName = :name and programId = :programId");
        query.setParameter("name", name);
        query.setParameter("programId", programId);
        query.setParameter("fieldId", fieldId);
        
        Long fieldNameId = (Long) query.uniqueResult();

        return fieldNameId;
    }
    
    /**
     * The 'saveCustomField" function will create/edit the custom field
     *
     * @Table	customProgramFields
     *
     * @param	customField	This will hold the custom field object from the form
     *
     */
    @Override
    public void saveCustomField(customProgramFields customField) {
        sessionFactory.getCurrentSession().saveOrUpdate(customField);
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<crosswalkData> getCrosswalkDataByCWId(Integer cwId)
			throws Exception {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(crosswalkData.class);
		criteria.add(Restrictions.eq("crosswalkId", cwId));
		List <crosswalkData> dataList = criteria.list();
		return dataList;
	}
}
