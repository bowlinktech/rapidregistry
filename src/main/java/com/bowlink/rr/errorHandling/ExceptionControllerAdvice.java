/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bowlink.rr.errorHandling;

import com.bowlink.rr.model.User;
import com.bowlink.rr.model.mailMessage;
import com.bowlink.rr.service.emailMessageManager;
import com.bowlink.rr.service.userManager;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Date;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author chadmccue
 */
@ControllerAdvice
public class ExceptionControllerAdvice {
   
    @Autowired
    private emailMessageManager emailMessageManager;
    
    @Autowired
    private userManager usermanager;
 
    @ExceptionHandler(Exception.class)
    public ModelAndView exception(HttpSession session, Exception e, Authentication authentication) throws Exception {
       
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/exception");
        try {
        mailMessage messageDetails = new mailMessage();
        
        messageDetails.settoEmailAddress("gchan123@yahoo.com");
        messageDetails.setfromEmailAddress("gchan123@yahoo.com");
        messageDetails.setmessageSubject("Exception Error " + InetAddress.getLocalHost().getHostAddress());
        
        StringBuilder sb = new StringBuilder();
        
        /* If a user is logged in then send along the user details */
        if(session.getAttribute("userDetails") != null || authentication != null) {
            User userInfo = (User)session.getAttribute("userDetails");
            
            if(userInfo == null && authentication != null) {
            // see if it is an admin that is logged in
            	userInfo = usermanager.getUserByEmail(authentication.getName());          	
            }
            if (userInfo != null) {	
            	sb.append("Logged in User: " + userInfo.getFirstName() + " " + userInfo.getLastName() + " (ID: "+ userInfo.getId() + ")");
                sb.append(System.getProperty("line.separator"));
                sb.append(System.getProperty("line.separator"));
            }
        }
       
        sb.append("Error: "+ e);
        sb.append("<br /><br />");
        sb.append("Time: " + new Date());
        sb.append("<br /><br />");
        sb.append("Message: " + e.getMessage());
        sb.append("<br /><br />");
        sb.append("Stack Trace: " + Arrays.toString(e.getStackTrace()));
        
        messageDetails.setmessageBody(sb.toString());
        emailMessageManager.sendEmail(messageDetails); 
        /*mav.addObject("messageBody",sb.toString());*/
        } catch (Exception ex) {
        	ex.printStackTrace();
        	System.err.println(ex.toString() + " error at exception");
        }
        
        return mav;
    }
}
