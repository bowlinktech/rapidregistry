/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.dao;

import com.bowlink.rr.model.programPatientSharing;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author chadmccue
 */
@Repository
public interface patientSharingDAO {
    
    List<Integer> getSharedPrograms(Integer programId) throws Exception;
     
    void savePatientSharing(programPatientSharing newpatientshare) throws Exception;
    
    void deletePatientSharing(Integer programId) throws Exception;
    
}
