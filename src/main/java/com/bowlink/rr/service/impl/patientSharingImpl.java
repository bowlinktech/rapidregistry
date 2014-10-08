/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.service.impl;

import com.bowlink.rr.dao.patientSharingDAO;
import com.bowlink.rr.model.programPatientSharing;
import com.bowlink.rr.service.patientSharing;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author chadmccue
 */
@Service
public class patientSharingImpl implements patientSharing {
    
    @Autowired
    patientSharingDAO patientSharingDAO;
    
    @Override
    @Transactional
    public  List<Integer> getSharedPrograms(Integer programId) throws Exception {
        return patientSharingDAO.getSharedPrograms(programId);
    }
    
    @Override
    @Transactional
    public void savePatientSharing(programPatientSharing newpatientshare) throws Exception {
        patientSharingDAO.savePatientSharing(newpatientshare);
    }
    
    @Override
    @Transactional
    public void deletePatientSharing(Integer programId) throws Exception {
        patientSharingDAO.deletePatientSharing(programId);
    }
    
}
