/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bowlink.rr.dao.impl;

import com.bowlink.rr.dao.reportDAO;
import com.bowlink.rr.model.reports;
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
    
}
