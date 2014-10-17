/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bowlink.rr.dao.impl;

import com.bowlink.rr.dao.reportDAO;
import com.bowlink.rr.model.programReports;
import com.bowlink.rr.model.reports;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author chadmccue
 */
@Repository
public class reportDAOImpl implements reportDAO {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    /**
     * The 'createReport" function will create the new report .
     *
     * @Table	cannedReports
     *
     * @param	report	This will hold the report object from the form
     *
     * @return the function will return the id of the new report
     *
     */
    @Override
    public Integer createReport(reports report) {
        Integer lastId = null;

        lastId = (Integer) sessionFactory.getCurrentSession().save(report);

        return lastId;
    }
    
    /**
     * The 'getAllReports' function will return a list of the canned reports in the system.
     * 
     * @return The function will return a list of canned reports in the system
     */
    @Override
    public List<reports> getAllReports() throws Exception {
        Query query = sessionFactory.getCurrentSession().createQuery("from reports order by reportName asc");

        List<reports> reportList = query.list();
        return reportList;
    }
    
    /**
     * The 'getReportById' function will return the details of the report for the passed in
     * id.
     * 
     * @param reportId    The id of the clicked report
     * @return  This function will return a reports object
     * @throws Exception 
     */
    @Override
    public reports getReportById(Integer reportId) throws Exception {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(reports.class);
        criteria.add(Restrictions.eq("id", reportId));

        reports reportDetails = (reports) criteria.uniqueResult(); 
        
        return reportDetails;
    }
    
    /**
     * The 'updateReport' function will submit the report changes.
     * 
     * @param   reportDetails The object containing the report details
     * 
     * @throws Exception 
     */
    @Override
    public void updateReport(reports reportDetails) throws Exception {
        sessionFactory.getCurrentSession().update(reportDetails);
    }
    
    /**
     * The 'getProgramReports' function will return a list of reports the passed in program is using
     * 
     * @param programId     The id of the program to search used reports
     * 
     * @return This function will return a list of report Ids
     */
    @Override
    public List<Integer> getProgramReports(Integer programId) throws Exception {
        
        List<Integer> usedReports = new ArrayList<Integer>();
        
        Query query = sessionFactory.getCurrentSession().createQuery("from programReports where programId = :programId");
        query.setParameter("programId", programId);

        List<programReports> reportList = query.list();
        
        if(reportList.size() > 0) {
            for(programReports report : reportList) {
                usedReports.add(report.getReportId());
            }
        }
        
        return usedReports;
        
    }
    
    /**
     * The '/saveProgramReports' function will save the list of associated program reports
     * programs
     * 
     * @param report   The object holding the selected report
     */
    @Override
    public void saveProgramReports(programReports report) throws Exception {
       sessionFactory.getCurrentSession().save(report);
    }
    
    
    /**
     * The 'deleteProgramReports' function will remove all the reports for the selected program
     * 
     * @param programId The id of the selected program to delete all reports.
     */
    @Override
    public void deleteProgramReports(Integer programId) throws Exception {
        
         /** Need to first delete current associations **/
        Query q1 = sessionFactory.getCurrentSession().createQuery("delete from programReports where programId = :programId");
        q1.setParameter("programId", programId);
        q1.executeUpdate();
    }
    
}
