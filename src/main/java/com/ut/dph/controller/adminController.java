package com.ut.dph.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ut.dph.model.Organization;
import com.ut.dph.model.User;
import com.ut.dph.model.messageType;
import com.ut.dph.model.configuration;
import com.ut.dph.model.configurationConnection;
import com.ut.dph.model.configurationConnectionReceivers;
import com.ut.dph.model.configurationConnectionSenders;
import com.ut.dph.model.configurationTransport;
import com.ut.dph.model.systemSummary;
import com.ut.dph.service.messageTypeManager;
import com.ut.dph.service.organizationManager;
import com.ut.dph.service.configurationManager;
import com.ut.dph.service.configurationTransportManager;
import com.ut.dph.service.transactionOutManager;
import com.ut.dph.service.userManager;
import java.util.ArrayList;

/**
 * The adminController class will handle administrator page requests that fall outside specific sections.
 *
 *
 * @author chadmccue
 *
 */
@Controller
public class adminController {

    @Autowired
    private organizationManager organizationManager;

    @Autowired
    private messageTypeManager messagetypemanager;

    @Autowired
    private configurationManager configurationmanager;
    
    @Autowired
    private configurationTransportManager configurationTransportManager;
    
    @Autowired
    private userManager userManager;
    
    @Autowired
    private transactionOutManager transactionOutManager;

    private int maxResults = 3;

    /**
     * The '/administrator' request will serve up the administrator dashboard after a successful login.
     *
     * @param request
     * @param response
     * @return	the administrator dashboard view
     * @throws Exception
     */
    @RequestMapping(value = "/administrator", method = RequestMethod.GET)
    public ModelAndView listConfigurations(HttpServletRequest request, HttpServletResponse response) throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/administrator/dashboard");

		//Need to get totals for the dashboard.
        //Return the total list of organizations
        Long totalOrgs = organizationManager.findTotalOrgs();
        mav.addObject("totalOrgs", totalOrgs);

        //Return the latest organizations
        List<Organization> organizations = organizationManager.getLatestOrganizations(maxResults);
        mav.addObject("latestOrgs", organizations);

        //Return the total list of message types
        Long totalMessageTypes = messagetypemanager.findTotalMessageTypes();
        mav.addObject("totalMessageTypes", totalMessageTypes);

        //Return the latest message types created
        List<messageType> messagetypes = messagetypemanager.getLatestMessageTypes(maxResults);
        mav.addObject("latestMessageTypes", messagetypes);

        //Return the total list of configurations
        Long totalConfigs = configurationmanager.findTotalConfigs();
        mav.addObject("totalConfigs", totalConfigs);

        //Return the latest configurations
        List<configuration> configurations = configurationmanager.getLatestConfigurations(maxResults);
        mav.addObject("latestConfigs", configurations);
        
        /* Get system inbound summary */
        systemSummary summaryDetails = transactionOutManager.generateSystemWaitingSummary();
        mav.addObject("summaryDetails", summaryDetails);
        
        Organization org;
        messageType messagetype;
        configurationTransport transportDetails;
        
        for (configuration config : configurations) {
            org = organizationManager.getOrganizationById(config.getorgId());
            config.setOrgName(org.getOrgName());

            messagetype = messagetypemanager.getMessageTypeById(config.getMessageTypeId());
            config.setMessageTypeName(messagetype.getName());
            
            transportDetails = configurationTransportManager.getTransportDetails(config.getId());
            if(transportDetails != null) {
             config.settransportMethod(configurationTransportManager.getTransportMethodById(transportDetails.gettransportMethodId()));
            }
        }
        
        /* get a list of all connections in the sysetm */
        List<configurationConnection> connections = configurationmanager.getLatestConnections(maxResults);
        
        /* Loop over the connections to get the configuration details */
        if(connections != null) {
            for(configurationConnection connection : connections) {
                /* Array to holder the users */
                List<User> connectionSenders = new ArrayList<User>();
                List<User> connectonReceivers = new ArrayList<User>();
                
                configuration srcconfigDetails = configurationmanager.getConfigurationById(connection.getsourceConfigId());
                configurationTransport srctransportDetails = configurationTransportManager.getTransportDetails(srcconfigDetails.getId());
                
                srcconfigDetails.setOrgName(organizationManager.getOrganizationById(srcconfigDetails.getorgId()).getOrgName());
                srcconfigDetails.setMessageTypeName(messagetypemanager.getMessageTypeById(srcconfigDetails.getMessageTypeId()).getName());
                srcconfigDetails.settransportMethod(configurationTransportManager.getTransportMethodById(srctransportDetails.gettransportMethodId()));
                if(srctransportDetails.gettransportMethodId() == 1 && srcconfigDetails.getType() == 2) {
                     srcconfigDetails.settransportMethod("File Download");
                }
                else {
                    srcconfigDetails.settransportMethod(configurationTransportManager.getTransportMethodById(srctransportDetails.gettransportMethodId()));
                }
                
                connection.setsrcConfigDetails(srcconfigDetails);
                
                configuration tgtconfigDetails = configurationmanager.getConfigurationById(connection.gettargetConfigId());
                configurationTransport tgttransportDetails = configurationTransportManager.getTransportDetails(tgtconfigDetails.getId());
                
                tgtconfigDetails.setOrgName(organizationManager.getOrganizationById(tgtconfigDetails.getorgId()).getOrgName());
                tgtconfigDetails.setMessageTypeName(messagetypemanager.getMessageTypeById(tgtconfigDetails.getMessageTypeId()).getName());
                if(tgttransportDetails.gettransportMethodId() == 1 && tgtconfigDetails.getType() == 2) {
                     tgtconfigDetails.settransportMethod("File Download");
                }
                else {
                    tgtconfigDetails.settransportMethod(configurationTransportManager.getTransportMethodById(tgttransportDetails.gettransportMethodId()));
                }
                
                /* Get the list of connection senders */
                List<configurationConnectionSenders> senders = configurationmanager.getConnectionSenders(connection.getId());
                
                for(configurationConnectionSenders sender : senders) {
                    User userDetail = userManager.getUserById(sender.getuserId());
                    connectionSenders.add(userDetail);
                }
                connection.setconnectionSenders(connectionSenders);
                
                /* Get the list of connection receivers */
                List<configurationConnectionReceivers> receivers = configurationmanager.getConnectionReceivers(connection.getId());
                
                for(configurationConnectionReceivers receiver : receivers) {
                    User userDetail = userManager.getUserById(receiver.getuserId());
                    connectonReceivers.add(userDetail);
                }
                connection.setconnectionReceivers(connectonReceivers);
                
                
                connection.settgtConfigDetails(tgtconfigDetails);
            }
            
        }
        
        mav.addObject("connections",connections);

        return mav;
    }

}
