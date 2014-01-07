package com.ut.dph.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ut.dph.dao.configurationDAO;
import com.ut.dph.model.Macros;
import com.ut.dph.model.Organization;
import com.ut.dph.model.User;
import com.ut.dph.model.configuration;
import com.ut.dph.model.configurationConnection;
import com.ut.dph.model.configurationConnectionReceivers;
import com.ut.dph.model.configurationConnectionSenders;
import com.ut.dph.model.configurationDataTranslations;
import com.ut.dph.model.configurationMessageSpecs;
import com.ut.dph.model.configurationSchedules;
import com.ut.dph.model.configurationTransport;
import com.ut.dph.model.messageType;

@Service
public class configurationDAOImpl implements configurationDAO {

    @Autowired
    private SessionFactory sessionFactory;
   

    /**
     * The 'createConfiguration' function will create a new configuration
     *
     * @Table	configurations
     *
     * @param	configuration	Will hold the configuration object from the form
     *
     * @return The function will return the id of the created configuration
     */
    @Override
    public Integer createConfiguration(configuration configuration) {
        Integer lastId;

        lastId = (Integer) sessionFactory.getCurrentSession().save(configuration);

        return lastId;
    }

    /**
     * The 'updateConfiguration' function will update a selected configuration details
     *
     * @Table	configurations
     *
     * @param	configuration	Will hold the configuration object from the field
     *
     */
    @Override
    public void updateConfiguration(configuration configuration) {
        sessionFactory.getCurrentSession().update(configuration);
    }

    /**
     * The 'getConfigurationById' function will return a configuration based on the id passed in.
     *
     * @Table configurations
     *
     * @param	configId	This will hold the configuration id to find
     *
     * @return	This function will return a single configuration object
     */
    @Override
    public configuration getConfigurationById(int configId) {
        return (configuration) sessionFactory.
                getCurrentSession().
                get(configuration.class, configId);
    }

    /**
     * The 'getConfigurationsByOrgId' function will return a list of configurations for the organization id passed in
     *
     * @Table configurations
     *
     * @param	orgId	This will hold the organization id to find
     *
     * @return	This function will return a list of configuration object
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<configuration> getConfigurationsByOrgId(int orgId, String searchTerm) {
        
        if (!"".equals(searchTerm)) {
           
            //get a list of message type id's that match the term passed in
            List<Integer> msgTypeIdList = new ArrayList<Integer>();
            Criteria findMsgTypes = sessionFactory.getCurrentSession().createCriteria(messageType.class);
            findMsgTypes.add(Restrictions.like("name", "%" + searchTerm + "%"));
            List<messageType> msgTypes = findMsgTypes.list();

            for (messageType msgType : msgTypes) {
                msgTypeIdList.add(msgType.getId());
            }

            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(configuration.class);

             if (msgTypeIdList.isEmpty()) {
                msgTypeIdList.add(0);
            }

            criteria.add(Restrictions.eq("orgId", orgId));
            criteria.add(Restrictions.or(
                    Restrictions.in("messageTypeId", msgTypeIdList)
                )
            )
            .addOrder(Order.desc("dateCreated"));

            return criteria.list();
        } 
        else {
            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(configuration.class);
            criteria.add(Restrictions.eq("orgId", orgId));
            criteria.addOrder(Order.desc("dateCreated"));
            return criteria.list();
        }
    }
    
    /**
     * The 'getActiveConfigurationsByOrgId' function will return a list of active configurations for the organization id passed in
     *
     * @Table configurations
     *
     * @param	orgId	This will hold the organization id to find
     *
     * @return	This function will return a list of configuration object
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<configuration> getActiveConfigurationsByOrgId(int orgId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(configuration.class);
        criteria.add(Restrictions.eq("orgId", orgId));
        criteria.add(Restrictions.eq("status", true));
        
        return criteria.list();
    }

    /**
     * The 'getOrganizationByName' function will return a single organization based on the name passed in.
     *
     * @Table	organizations
     *
     * @param	cleanURL	Will hold the 'clean' organization name from the url
     *
     * @return	This function will return a single organization object
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<configuration> getConfigurationByName(String configName) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(configuration.class);
        criteria.add(Restrictions.like("configName", configName));
        return criteria.list();
    }

    /**
     * The 'getConfigurations' function will return a list of the configurations in the system
     *
     * @Table	configurations
     *
     * @param	page	This will hold the value of page being viewed (used for pagination) 
     * @param    maxResults	This will hold the value of the maximum number of results we want to send back to the list page
     *
     * @Return	This function will return a list of configuration objects
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<configuration> getConfigurations(int page, int maxResults) {
        Query query = sessionFactory.getCurrentSession().createQuery("from configuration order by dateCreated desc");

        //By default we want to return the first result
        int firstResult = 0;

        //If viewing a page other than the first we then need to figure out
        //which result to start with
        if (page > 1) {
            firstResult = (maxResults * (page - 1));
        }
        query.setFirstResult(firstResult);

        //Set the max results to display
        query.setMaxResults(maxResults);

        List<configuration> configurationList = query.list();
        return configurationList;
    }

    /**
     * The 'getDataTranslations' function will return a list of data translations saved for the passed in configuration/transport method.
     *
     * @param	configId	The id of the configuration we want to return associated translations for.
     * @param	transportMethod	The selected transport method for the configuration
     *
     * @return	This function will return a list of translations
     */
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<configurationDataTranslations> getDataTranslations(int configId) {
        Query query = sessionFactory.getCurrentSession().createQuery("from configurationDataTranslations where configId = :configId order by processOrder asc");
        query.setParameter("configId", configId);

        return query.list();
    }

    /**
     * The 'findConfigurations' function will return a list of configurations based on a search term passed in. The function will search configurations on the following fields configName, orgName and messageTypeName
     *
     * @Table	configurations
     *
     * @param	searchTerm	Will hold the term used search configurations on
     *
     * @return	This function will return a list of configuration objects
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<configuration> findConfigurations(String searchTerm) {

        if (!"".equals(searchTerm)) {
            //get a list of organization id's that match the term passed in
            List<Integer> orgIdList = new ArrayList<Integer>();
            Criteria findOrgs = sessionFactory.getCurrentSession().createCriteria(Organization.class);
            findOrgs.add(Restrictions.like("orgName", "%" + searchTerm + "%"));
            List<Organization> orgs = findOrgs.list();

            for (Organization org : orgs) {
                orgIdList.add(org.getId());
            }
            

            //get a list of message type id's that match the term passed in
            List<Integer> msgTypeIdList = new ArrayList<Integer>();
            Criteria findMsgTypes = sessionFactory.getCurrentSession().createCriteria(messageType.class);
            findMsgTypes.add(Restrictions.like("name", "%" + searchTerm + "%"));
            List<messageType> msgTypes = findMsgTypes.list();

            for (messageType msgType : msgTypes) {
                msgTypeIdList.add(msgType.getId());
            }

            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(configuration.class);

            if (orgIdList.isEmpty()) {
                orgIdList.add(0);
            }
            if (msgTypeIdList.isEmpty()) {
                msgTypeIdList.add(0);
            }

            criteria.add(Restrictions.or(
                    Restrictions.in("orgId", orgIdList),
                    Restrictions.in("messageTypeId", msgTypeIdList)
            )
            )
                    .addOrder(Order.desc("dateCreated"));

            return criteria.list();
        } else {
            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(configuration.class);
            criteria.addOrder(Order.desc("dateCreated"));
            return criteria.list();
        }

    }

    /**
     * The 'totalConfigs' function will return the total number of active configurations in the system. This will be used for pagination when viewing the list of configurations
     *
     * @Table configurations
     *
     * @return This function will return the total configurations
     */
    @Override
    public Long findTotalConfigs() {
        Query query = sessionFactory.getCurrentSession().createQuery("select count(*) as totalConfigs from configuration");

        Long totalConfigs = (Long) query.uniqueResult();

        return totalConfigs;
    }

    /**
     * The 'getLatestConfigurations' function will return a list of the latest configurations that have been added to the system and activated.
     *
     * @Table	organizations
     *
     * @param	maxResults	This will hold the value of the maximum number of results we want to send back to the page
     *
     * @Return	This function will return a list of organization objects
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<configuration> getLatestConfigurations(int maxResults) {
        Query query = sessionFactory.getCurrentSession().createQuery("from configuration order by dateCreated desc");

        //Set the max results to display
        query.setMaxResults(maxResults);

        List<configuration> configurationList = query.list();
        return configurationList;
    }

    /**
     * The 'getTotalConnections' function will return the total number of active connections set up for a configuration.
     *
     * @Table configurationCrosswalks
     *
     * @param	configId The id of the configuration to find connections for.
     *
     * @Return	The total number of active connections set up for a configurations
     *
     */
    @Override
    public Long getTotalConnections(int configId) {

        Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT count(*) FROM configurationConnections where configId = :configId and status = 1")
                .setParameter("configId", configId);

        BigInteger totalCount = (BigInteger) query.uniqueResult();

        Long totalConnections = totalCount.longValue();

        return totalConnections;
    }

    /**
     * The 'updateCompletedSteps' function will update the steps completed for a passe in configurations. This column will be used to determine when you can activate a configuration and when you can access certain steps in the configuration creation process.
     *
     * @param	configId	This will hold the id of the configuration to update stepCompleted	This will hold the completed step number
     */
    @Override
    @Transactional
    public void updateCompletedSteps(int configId, int stepCompleted) {

        Query query = sessionFactory.getCurrentSession().createSQLQuery("UPDATE configurations set stepsCompleted = :stepCompleted where id = :configId")
                .setParameter("stepCompleted", stepCompleted)
                .setParameter("configId", configId);

        query.executeUpdate();
    }

    /**
     * The 'getFileTypes' function will return a list of available file types
     *
     */
    @SuppressWarnings("rawtypes")
    @Override
    @Transactional
    public List getFileTypes() {
        Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT id, fileType FROM ref_fileTypes order by id asc");

        return query.list();
    }

    /**
     * The 'getFieldName' function will return the name of a field based on the fieldId passed in. This is used for display purposes to show the actual field lable instead of a field name.
     *
     * @param fieldId	This will hold the id of the field to retrieve
     *
     * @Return This function will return a string (field name)
     */
    @Override
    @Transactional
    public String getFieldName(int fieldId) {
        Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT fieldDesc FROM configurationFormFields where id = :fieldId")
                .setParameter("fieldId", fieldId);

        String fieldName = (String) query.uniqueResult();

        return fieldName;
    }

    /**
     * The 'deleteDataTranslations' function will remove all data translations for the passed in configuration / transport method.
     *
     * @param	configId	The id of the configuration to remove associated translations
     * @param	transportMethod	The transport method for the configuration
     *
     */
    @Override
    @Transactional
    public void deleteDataTranslations(int configId) {
        Query deleteTranslations = sessionFactory.getCurrentSession().createQuery("delete from configurationDataTranslations where configId = :configId");
        deleteTranslations.setParameter("configId", configId);
        deleteTranslations.executeUpdate();
    }

    /**
     * The 'saveDataTranslations' function will save the submitted translations for the selected message type
     *
     * @param translations	the configurationDataTranslations object
     *
     */
    @Override
    @Transactional
    public void saveDataTranslations(configurationDataTranslations translations) {
        sessionFactory.getCurrentSession().save(translations);
    }

    /**
     * The 'getMacros' function will return a list of available system macros.
     *
     * @return list of macros
     */
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<Macros> getMacros() {
        Query query = sessionFactory.getCurrentSession().createQuery("from Macros order by macro_short_name asc");
        return query.list();
    }
    
    /**
     * The 'getMacroById' function will return the macro details for the passed in macro id.
     *
     * @param macroId The value of the macro to retrieve details
     *
     * @return macros object
     */
    public Macros getMacroById(int macroId) {
        return (Macros) sessionFactory.getCurrentSession().get(Macros.class, macroId);
    }
    
    /**
     * The 'getAllConnections' function will return the list of configuration connections
     * in the system.
     *
     * @Table	configurationConnections
     *
     * @param	page        This will hold the value of page being viewed (used for pagination) 
     * @param   maxResults  This will hold the value of the maximum number of results we want to send back to the list page
     *
     * @Return	This function will return a list of configurationConnection objects
     */
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<configurationConnection> getAllConnections(int page, int maxResults) {
        Query query = sessionFactory.getCurrentSession().createQuery("from configurationConnection order by dateCreated desc");

        //By default we want to return the first result
        int firstResult = 0;

        //If viewing a page other than the first we then need to figure out
        //which result to start with
        if (page > 1) {
            firstResult = (maxResults * (page - 1));
        }
        query.setFirstResult(firstResult);

        //Set the max results to display
        query.setMaxResults(maxResults);

        List<configurationConnection> connections = query.list();
        return connections;
    }
    
    /**
     * The 'getConnectionsByConfiguration' will return a list of target connections
     * for a passed in configuration;
     * 
     * @param configId  The id of the configuration to search connections for.
     * 
     * @return This function will return a list of configurationConnection objects
     */
    @Override
    @Transactional
    public List<configurationConnection> getConnectionsByConfiguration(int configId) {
        Query query = sessionFactory.getCurrentSession().createQuery("from configurationConnection where sourceConfigId = :configId");
        query.setParameter("configId", configId);
        
        List<configurationConnection> connections = query.list();
        return connections;
    }
    
    /**
     * The 'saveConnection' function will save the new connection
     * 
     * @param connection    The object holding the new connection
     * 
     * @return This function does not return anything.
     */
    @Override
    @Transactional
    public Integer saveConnection(configurationConnection connection) {
        Integer connectionId;

        connectionId = (Integer) sessionFactory.getCurrentSession().save(connection);

        return connectionId;
        
    }
    
    /**
     * The 'saveConnectionSenders' function will save the list of users selected
     * to be authorized to send transactions for a configuration connection.
     * 
     * @table configurationConnectionSenders
     * 
     * @param senders The configurationConnectionSenders object
     * 
     * @return This function does not return anything
     */
    @Override
    @Transactional
    public void saveConnectionSenders(configurationConnectionSenders senders) {
        sessionFactory.getCurrentSession().save(senders);
    }
  
    /**
     * The 'saveConnectionReceivers' function will save the list of users selected
     * to be authorized to receive transactions for a configuration connection.
     * 
     * @table configurationConnectionReceivers
     * 
     * @param receivers The configurationConnectionSenders object
     * 
     * @return This function does not return anything
     */
    @Override
    @Transactional
    public void saveConnectionReceivers(configurationConnectionReceivers receivers) {
        sessionFactory.getCurrentSession().save(receivers);
    }
    
    /**
     * The 'getConnection' function will return a connection based on the id passed in.
     *
     * @Table configurationConnections
     *
     * @param	connectionid	This will hold the connection id to find
     *
     * @return	This function will return a single connection object
     */
    @Override
    public configurationConnection getConnection(int connectionId) {
        return (configurationConnection) sessionFactory.getCurrentSession().get(configurationConnection.class, connectionId);
    }
    
    /**
     * The 'getConnectionSenders' function will return a list of authorized users who are set up
     * to create new messages for the passed in connectionId
     * 
     * @param connectionId The id of the configuration connection to get a list of users
     * 
     * @return This function will return a list of configurationConnectionSenders objects
     */
    @Override
    @Transactional
    public List<configurationConnectionSenders> getConnectionSenders(int connectionId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(configurationConnectionSenders.class);
        criteria.add(Restrictions.eq("connectionId", connectionId));
        
        return criteria.list();
    }
  
    /**
     * The 'getConnectionReceivers' function will return a list of authorized users who are set up
     * to receive messages for the passed in connectionId
     * 
     * @param connectionId The id of the configuration connection to get a list of users
     * 
     * @return This function will return a list of configurationConnectionSenders objects
     */
    @Override
    @Transactional
    public List<configurationConnectionReceivers> getConnectionReceivers(int connectionId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(configurationConnectionReceivers.class);
        criteria.add(Restrictions.eq("connectionId", connectionId));
        
        return criteria.list();
    }
    
    /**
     * The 'removeConnectionSenders' function will remove the authorized senders for the passed in connectionId.
     * 
     * @param connectionId The connection Id to remove senders for
     * 
     * @return THis function does not return anything
     */
    @Override
    @Transactional
    public void removeConnectionSenders(int connectionId) {
        Query query = sessionFactory.getCurrentSession().createSQLQuery("DELETE from configurationConnectionSenders where connectionId = :connectionId")
                .setParameter("connectionId", connectionId);

        query.executeUpdate();
    }
  
    /**
     * The 'removeConnectionReceivers' function will remove the authorized receivers for the passed in connectionId.
     * 
     * @param connectionId The connection Id to remove receivers for
     * 
     * @return THis function does not return anything
     */
    @Override
    @Transactional
    public void removeConnectionReceivers(int connectionId) {
        Query query = sessionFactory.getCurrentSession().createSQLQuery("DELETE from configurationConnectionReceivers where connectionId = :connectionId")
                .setParameter("connectionId", connectionId);

        query.executeUpdate();
    }
    
    /**
     * The 'updateConnection' function will update the status of the
     * passed in connection
     * 
     * @param connection    The object holding the connection
     * 
     * @return This function does not return anything.
     */
    @Override
    @Transactional
    public void updateConnection(configurationConnection connection) {
        sessionFactory.getCurrentSession().update(connection);
    }



    /**
     * The 'getScheduleDetails' function will return the details of the schedule for the passed in configuration id and transport method.
     *
     * @param configId The id for the configuration
     *
     * @return The function will return a configurationSchedules object containing the details for the schedule.
     */
    public configurationSchedules getScheduleDetails(int configId) {
        Query query = sessionFactory.getCurrentSession().createQuery("from configurationSchedules where configId = :configId");
        query.setParameter("configId", configId);

        configurationSchedules scheduleDetails = (configurationSchedules) query.uniqueResult();

        return scheduleDetails;

    }

    /**
     * The 'saveSchedule' function will create or update the configuration schedule passed in.
     *
     * @param scheduleDetails The object that holds the configuration schedule
     *
     * @return This function does not return anything.
     */
    public void saveSchedule(configurationSchedules scheduleDetails) {
        sessionFactory.getCurrentSession().saveOrUpdate(scheduleDetails);
    }
    
    
    /**
     * The 'getMessageSpecs' function will return the message specs for the passing configuration
     * ID.
     * 
     * @param configId  The configuration Id to find message specs for
     * 
     * @return  This function will return the configuartionMessageSpec object.
     */
    @Override
    public configurationMessageSpecs getMessageSpecs(int configId) {
        Query query = sessionFactory.getCurrentSession().createQuery("from configurationMessageSpecs where configId = :configId");
        query.setParameter("configId", configId);
        
         return (configurationMessageSpecs) query.uniqueResult();
    }
    
    /**
     * The 'updateMessageSpecs' function will save/update the configuration message
     * specs.
     * 
     * @param   messageSpecs The object that will hold the values from the message spec form
     * @param   clearField   The value that will determine to clear out existing form fields
     *                       if a new file is uploaded.
     * 
     * @return This function does not return anything.
     */
    @Override
    public void updateMessageSpecs(configurationMessageSpecs messageSpecs, int transportDetailId, int clearFields) {
        
        //if clearFields == 1 then we need to clear out the configuration form fields, mappings and data
        //translations. This will allow the admin to change the configuration transport method after
        //one was previously selected. This will only be available while the configuration is not active.
        if (clearFields == 1) {
            //Delete the existing form fields
            Query deleteFields = sessionFactory.getCurrentSession().createSQLQuery("DELETE from configurationFormFields where configId = :configId and transportDetailId = :transportDetailId");
            deleteFields.setParameter("configId", messageSpecs.getconfigId());
            deleteFields.setParameter("transportDetailId", transportDetailId);
            deleteFields.executeUpdate();
        }
        
        sessionFactory.getCurrentSession().saveOrUpdate(messageSpecs);
        
    }
    
    /**
     * The 'getActiveConfigurationByUserId' function will return a list of configurations
     * set up for ERG and for the passed in userId
     * 
     * @param   userId  
     * 
     * @return This function will return a list of ERG configurations.
     */
    @Override
    public List<configuration> getActiveERGConfigurationsByUserId(int userId) {
        
        /* Find all SENDER connections for the passed in user */
        Criteria findAuthConnections = sessionFactory.getCurrentSession().createCriteria(configurationConnectionSenders.class);
        findAuthConnections.add(Restrictions.eq("userId", userId));
        
        /* This variables (senderConnections) will hold the list of authorized connections */
        List<configurationConnectionSenders> senderConnections = findAuthConnections.list();
        
        /* 
        Create an emtpy array that will hold the list of configurations associated to the
        found connections.
        */
        List<Integer> senderConfigList = new ArrayList<Integer>();
        
        if (senderConnections.isEmpty()) {
            senderConfigList.add(0);
        }
        else {
            /* Search the connections by connectionId to pull the sourceConfigId */
            for(configurationConnectionSenders connection : senderConnections) {
               Criteria findConnectionDetails = sessionFactory.getCurrentSession().createCriteria(configurationConnection.class);
               findConnectionDetails.add(Restrictions.eq("id", connection.getconnectionId()));
               configurationConnection connectionDetails = (configurationConnection) findConnectionDetails.uniqueResult();
               
               /* Add the sourceConfigId to the array */
               senderConfigList.add(connectionDetails.getsourceConfigId());
               findConnectionDetails = null;
            }
        }
        
        /* 
        Query to get a list of all ERG configurations that the logged in
        user is authorized to create
        */
        List<Integer> ergConfigList = new ArrayList<Integer>();
        Criteria findERGConfigs = sessionFactory.getCurrentSession().createCriteria(configurationTransport.class);
        findERGConfigs.add(Restrictions.or(
                Restrictions.eq("transportMethodId",2),
                Restrictions.eq("errorHandling",1)
        )).add(Restrictions.and(Restrictions.in("configId",senderConfigList))); 
       
        
        List<configurationTransport> ergConfigs = findERGConfigs.list();

        for (configurationTransport config : ergConfigs) {
            ergConfigList.add(config.getconfigId());
        }
        
        if (ergConfigList.isEmpty()) {
            ergConfigList.add(0);
        }
        
        /*
        Finally query the configuration table to get all configurations in the authorized list
        of configuration Ids.
        */
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(configuration.class);
        criteria.add(Restrictions.eq("status", true));
        criteria.add(Restrictions.and(
                Restrictions.in("id", ergConfigList)
        ));
        
        return criteria.list();
        
    }
}
