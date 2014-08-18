/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bowlink.rr.service.impl;

import com.bowlink.rr.dao.activityCodeDAO;
import com.bowlink.rr.model.activityCodes;
import com.bowlink.rr.service.activityCodeManager;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author chadmccue
 */
@Service
public class activityCodeManagerImpl implements activityCodeManager {
    
    @Autowired
    activityCodeDAO activityCodeDAO;
    
    @Override
    @Transactional
    public List<activityCodes> getActivityCodes(Integer programId) throws Exception {
        return activityCodeDAO.getActivityCodes(programId);
    }
    
    @Override
    @Transactional
    public activityCodes getActivityCodeById(Integer codeId) throws Exception {
        return activityCodeDAO.getActivityCodeById(codeId);
    }
    
    @Override
    @Transactional
    public void createActivityCode(activityCodes codeDetails) throws Exception {
        activityCodeDAO.createActivityCode(codeDetails);
    }
    
    @Override
    @Transactional
    public void updateActivityCode(activityCodes codeDetails) throws Exception {
        activityCodeDAO.updateActivityCode(codeDetails);
    }
    
}
