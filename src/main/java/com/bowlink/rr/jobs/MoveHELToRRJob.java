/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.jobs;

import com.bowlink.rr.service.importManager;

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
public class MoveHELToRRJob implements Job {
	
	@Autowired
    private importManager importmanager;
	
    
    @Override
    public void execute(JobExecutionContext context)  throws JobExecutionException {
    	try {
            SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
            importmanager.moveHELtoRR();          
        } catch (Exception ex) {
            try {
                throw new Exception("Error occurred moving HEL processed files back to RR schedule task",ex);
            } catch (Exception ex1) {
                Logger.getLogger(ProcessRRFilesJob.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
    
}
