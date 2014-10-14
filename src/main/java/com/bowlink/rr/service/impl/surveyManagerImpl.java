/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.service.impl;

import com.bowlink.rr.dao.surveyDAO;
import com.bowlink.rr.model.surveys;
import com.bowlink.rr.service.surveyManager;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author chadmccue
 */
@Service
public class surveyManagerImpl implements surveyManager {
    
    @Autowired
    surveyDAO surveyDAO;
    
    @Override
    @Transactional
    public List<surveys> getActiveSurveys(Integer programId) throws Exception {
        return surveyDAO.getActiveSurveys(programId);
    }
    
}
