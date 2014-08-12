/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bowlink.rr.dao;

import com.bowlink.rr.model.activityCodes;
import java.util.List;

/**
 *
 * @author chadmccue
 */
public interface activityCodeDAO {
    
    List<activityCodes> getActivityCodes(Integer programId) throws Exception;
    
    activityCodes getActivityCodeById(Integer codeId) throws Exception;
    
}
