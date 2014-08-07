/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bowlink.rr.service;

import com.bowlink.rr.model.mailMessage;
import org.springframework.scheduling.annotation.Async;

/**
 *
 * @author chadmccue
 */
public interface emailMessageManager {
    
    @Async
    void sendEmail(mailMessage messageDetails) throws Exception;
    
}
