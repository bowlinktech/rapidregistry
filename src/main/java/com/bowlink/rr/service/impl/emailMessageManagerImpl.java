/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bowlink.rr.service.impl;

import com.bowlink.rr.model.mailMessage;
import com.bowlink.rr.service.emailMessageManager;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 *
 * @author chadmccue
 */
@Service
public class emailMessageManagerImpl implements emailMessageManager {
    
    private JavaMailSender mailSender;
    
    public void setMailSender(JavaMailSender mailSender) {
       this.mailSender = mailSender;
    }
    
    @Async
    public void sendEmail(mailMessage messageDetails) throws Exception {
        
        MimeMessage msg = mailSender.createMimeMessage();
        
        try {
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            
            helper.setFrom(messageDetails.getfromEmailAddress());
            helper.setTo(messageDetails.gettoEmailAddress());
            
            if(messageDetails.getccEmailAddress() != null) {
                helper.setCc(messageDetails.getccEmailAddress());
            }
            
            helper.setSubject(messageDetails.getmessageSubject());
            
            helper.setText("",messageDetails.getmessageBody());
            helper.setReplyTo(messageDetails.getfromEmailAddress());
            
             mailSender.send(msg);
             
        }
        catch (Exception e) {
            throw new Exception(e);
        }
        
        
    }
}
