/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.jobs;

import com.bowlink.rr.service.importManager;
import com.bowlink.rr.service.reportManager;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 *
 * @author gchan
 */
public class reportMonitoringJob implements Job {
	
	@Autowired
    private reportManager reportmanager;
	
    
    @Override
    public void execute(JobExecutionContext context)  throws JobExecutionException {
    	try {
            SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
            reportmanager.reportStatusMonitoring(Arrays.asList(2));       
        } catch (Exception ex) {
            try {
                throw new Exception("Error occurred checking report status schedule task",ex);
            } catch (Exception ex1) {
                Logger.getLogger(reportMonitoringJob.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
    
}
