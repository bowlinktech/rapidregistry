/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.jobs;

import com.bowlink.rr.model.User;
import com.bowlink.rr.service.userManager;

import java.util.List;
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
public class PWChangeJob implements Job {
	
	@Autowired
    private userManager usermanager;
    
    @Override
    public void execute(JobExecutionContext context)  throws JobExecutionException {
        try {
            SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
            List <User> userlist = usermanager.getAllUsers();
			   for (User user : userlist) {
				   //READ PW AND UPDATE
				   user.setEmail(user.getEmail().trim());
				   user = usermanager.encryptPW(user);
				   usermanager.updateUser(user);  				   
			   }
        } catch (Exception ex) {
        	ex.printStackTrace();
            try {
                throw new Exception("Error occurred trying to update password from schedule task",ex);
            } catch (Exception ex1) {
            	ex1.printStackTrace();
                Logger.getLogger(PWChangeJob.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        
        
    }
    
}