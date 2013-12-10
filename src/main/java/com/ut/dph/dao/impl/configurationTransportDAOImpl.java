package com.ut.dph.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ut.dph.dao.configurationTransportDAO;
import com.ut.dph.model.configurationFTPFields;
import com.ut.dph.model.configurationFormFields;
import com.ut.dph.model.configurationSchedules;
import com.ut.dph.model.configurationTransport;

@Service
public class configurationTransportDAOImpl implements configurationTransportDAO {

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * The 'getTransportDetails' function will return the details of the transport method for the passed in configuration id
     *
     * @param configId	Holds the id of the selected configuration
     *
     * @Return	This function will return a list of configurationTransport objects
     */
    @SuppressWarnings("unchecked")
    public List<configurationTransport> getTransportDetails(int configId) {
        Query query = sessionFactory.getCurrentSession().createQuery("from configurationTransport where configId = :configId");
        query.setParameter("configId", configId);
        return query.list();
    }

    /**
     * The 'getTransportDetailsByTransportMethod' function will return the details of the transport method for the passed in configuration id and passed in transport method
     *
     * @param configId	Holds the id of the selected configuration
     * @param transportMethod	Holds the selected transport method
     *
     * @Return	This function will return a configurationTransport object
     */
    public configurationTransport getTransportDetailsByTransportMethod(int configId, int transportMethod) {
        Query query = sessionFactory.getCurrentSession().createQuery("from configurationTransport where configId = :configId and transportMethod = :transportMethod");
        query.setParameter("configId", configId);
        query.setParameter("transportMethod", transportMethod);

        return (configurationTransport) query.uniqueResult();
    }

    /**
     * The 'setupOnlineForm' function will complete the set up for the online form for the new configuration. Every configuration will have an associated online form.
     *
     * @param	configId	Holds the id of the new configuration
     * @param	messageTypeid	Holds the id of the selected message type
     *
     * @Return	This function does not return anything
     */
    @Override
    public void setupOnlineForm(int configId, int messageTypeId) {
        int transportId = 0;

        configurationTransport transportDetails = new configurationTransport();
        transportDetails.settransportMethod(2);
        transportDetails.setconfigId(configId);

        transportId = (Integer) sessionFactory.getCurrentSession().save(transportDetails);

        copyMessageTypeFields(configId, messageTypeId, transportId);
        
        //Call the function that will automatically create the schedule for the online form.
        configurationSchedules schedule = new configurationSchedules();
        schedule.setconfigId(configId);
        schedule.settransportMethod(2);
        schedule.settype(0);
        
        sessionFactory.getCurrentSession().save(schedule);
        
    }

    /**
     * The 'updateTransportDetails' function will update the configuration transport details
     *
     * @param	transportDetails	The details of the transport form
     * @param	clearFields	This will hold a variable that will determine if we clear out existing configuration details.
     *
     * @return	this function does not return anything
     */
    public Integer updateTransportDetails(configurationTransport transportDetails, int clearFields) {

	//if clearFields == 1 then we need to clear out the configuration form fields, mappings and data
        //translations. This will allow the admin to change the configuration transport method after
        //one was previously selected. This will only be available while the configuration is not active.
        if (clearFields == 1) {
            //Delete the existing form fields
            Query deleteFields = sessionFactory.getCurrentSession().createSQLQuery("DELETE from configurationFormFields where configId = :configId and transportDetailId = :transportDetailId");
            deleteFields.setParameter("configId", transportDetails.getconfigId());
            deleteFields.setParameter("transportDetailId", transportDetails.getId());
            deleteFields.executeUpdate();

        }

        if (transportDetails.getId() > 0) {
            sessionFactory.getCurrentSession().update(transportDetails);
            return transportDetails.getId();
        } else {
            int detailId = (Integer) sessionFactory.getCurrentSession().save(transportDetails);
            return detailId;
        }

    }

    /**
     * The 'getTransportMethods' function will return a list of available transport methods
     *
     */
    @Override
    @SuppressWarnings("rawtypes")
    @Transactional
    public List getTransportMethods() {
        Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT id, transportMethod FROM ref_transportMethods order by transportMethod asc");

        return query.list();
    }

    /**
     * The 'copyMessageTypeFields' function will copy the form fields for the selected message type for the selected configuration.
     *
     * @param	configId	The id of the selected configuration messageTypeId	The id of the selected message type to copy the form fields
     *
     * @return	This function does not return anything
     */
    @Transactional
    public void copyMessageTypeFields(int configId, int messageTypeId, int transportDetailId) {
        Query query = sessionFactory.getCurrentSession().createSQLQuery("INSERT INTO configurationFormFields (messageTypeFieldId, configId, transportDetailId, fieldNo, fieldDesc, fieldLabel, validationType, required, bucketNo, bucketDspPos, useField, saveToTableName, saveToTableCol) SELECT id, :configId, :transportDetailId, fieldNo,  fieldDesc, fieldLabel, validationType, required, bucketNo, bucketDspPos, 1, saveToTableName, saveToTableCol FROM messageTypeFormFields where messageTypeId = :messageTypeId");
        query.setParameter("configId", configId);
        query.setParameter("transportDetailId", transportDetailId);
        query.setParameter("messageTypeId", messageTypeId);

        query.executeUpdate();

    }

    /**
     * The 'getConfigurationFields' function will return a list of saved form fields for the selected configuration.
     *
     * @param	configId	Will hold the id of the configuration we want to return fields for
     *
     * @return	This function will return a list of configuration form fields
     */
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<configurationFormFields> getConfigurationFields(int configId, int transportDetailId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(configurationFormFields.class)
                .add(Restrictions.eq("configId", configId))
                .add(Restrictions.eq("transportDetailId", transportDetailId))
                .addOrder(Order.asc("bucketNo"))
                .addOrder(Order.asc("bucketDspPos"));

        return criteria.list();
    }

    /**
     * The 'updateConfigurationFormFields' function will update the configuration form field settings
     *
     * @param formField	object that will hold the form field settings
     *
     * @return This function will not return anything
     */
    public void updateConfigurationFormFields(configurationFormFields formField) {
        sessionFactory.getCurrentSession().update(formField);
    }
    
    /**
    * The 'getTransportFTPDetails' function will return the FTP information for the 
    * passed in transportDetailId.
    * 
    * @param transportDetailsId     the id of the selected transport method
    * 
    * @return This function will return a list of FTP details
    */
    @Override
    @Transactional
    public List<configurationFTPFields> getTransportFTPDetails(int transportDetailId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(configurationFTPFields.class)
                .add(Restrictions.eq("transportId", transportDetailId));

        return criteria.list();
    }
    
    
    /**
     * The 'saveTransportFTP' function will save the transport FTP
     * information into the DB.
     * 
     * @param   FTPFields   The FTP form fields
     * 
     * @return this function will not return anything.
     */
    @Override
    @Transactional
    public void saveTransportFTP(configurationFTPFields FTPFields) {
        sessionFactory.getCurrentSession().saveOrUpdate(FTPFields);
    }

   

}
