/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.service;

import com.bowlink.rr.model.surveys;
import java.util.List;

/**
 *
 * @author chadmccue
 */
public interface surveyManager {
    
    List<surveys> getActiveSurveys(Integer programId) throws Exception;
    
}
