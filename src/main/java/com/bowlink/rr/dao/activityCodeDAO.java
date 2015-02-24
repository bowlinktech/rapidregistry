/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bowlink.rr.dao;

import com.bowlink.rr.model.activityCodes;
import com.bowlink.rr.model.programActivityCodes;
import java.util.List;

/**
 *
 * @author chadmccue
 */
public interface activityCodeDAO {
    
    List<activityCodes> getActivityCodes(Integer programId) throws Exception;
    
    List<activityCodes> getActivityCodesByProgram(Integer programId) throws Exception;
    
    activityCodes getActivityCodeById(Integer codeId) throws Exception;
    
    void createActivityCode(activityCodes codeDetails) throws Exception;
    
    void updateActivityCode(activityCodes codeDetails) throws Exception;
    
    boolean getActivityCodesByProgram (Integer programId, Integer codeId) throws Exception;
    
    void saveProgramActivityCode(programActivityCodes newCodeAssoc) throws Exception;
    
    void removeProgramActivityCodes(Integer programId) throws Exception;
    
}
