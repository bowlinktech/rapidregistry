/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.dao.impl;

import com.bowlink.rr.dao.patientSharingDAO;
import com.bowlink.rr.model.programPatientSharing;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author chadmccue
 */
@Repository
public class patientSharingDAOImpl implements patientSharingDAO {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    /**
     * The 'getSharedPrograms' function will return a list of programs the passed in program is sharing
     * patient data with.
     * 
     * @param programId     The id of the program to search shared programs
     * 
     * @return This function will return a list of shared program Ids
     */
    @Override
    public List<Integer> getSharedPrograms(Integer programId) throws Exception {
        
        List<Integer> sharedPrograms = new ArrayList<Integer>();
        
        Query query = sessionFactory.getCurrentSession().createQuery("from programPatientSharing where programId = :programId");
        query.setParameter("programId", programId);

        List<programPatientSharing> programList = query.list();
        
        if(programList.size() > 0) {
            for(programPatientSharing sharedProgram : programList) {
                sharedPrograms.add(sharedProgram.getSharingProgramId());
            }
        }
        
        return sharedPrograms;
        
    }
    
    /**
     * The '/savePatientSharing' function will save the patient sharing between the selected program and list of other
     * programs
     * 
     * @param newpatientshare   The object holding the patientshare
     */
    @Override
    public void savePatientSharing(programPatientSharing newpatientshare) throws Exception {
       sessionFactory.getCurrentSession().save(newpatientshare);
    }
    
    /**
     * The 'deletePatientSharing' function will remove all the patient sharing for the selected program
     * 
     * @param programId The id of the selected program to delete all patient sharing.
     */
    @Override
    public void deletePatientSharing(Integer programId) throws Exception {
        
         /** Need to first delete current associations **/
        Query q1 = sessionFactory.getCurrentSession().createQuery("delete from programPatientSharing where programId = :programId");
        q1.setParameter("programId", programId);
        q1.executeUpdate();
    }
    
}
