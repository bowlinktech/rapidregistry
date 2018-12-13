/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bowlink.rr.dao.impl;

import com.bowlink.rr.dao.reportDAO;
import com.bowlink.rr.model.programOrgHierarchyDetails;
import com.bowlink.rr.model.programReports;
import com.bowlink.rr.model.reportCrossTab;
import com.bowlink.rr.model.reportCrossTabCWData;
import com.bowlink.rr.model.reportCrossTabEntity;
import com.bowlink.rr.model.reportDetails;
import com.bowlink.rr.model.reportRequest;
import com.bowlink.rr.model.reportType;
import com.bowlink.rr.model.reports;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    @SuppressWarnings("unchecked")
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
    @SuppressWarnings("unchecked")
	@Override
    public List<Integer> getProgramReports(Integer programId) throws Exception {
        
        List<Integer> usedReports = new ArrayList<Integer>();
        
        Query query = sessionFactory.getCurrentSession().createQuery("from programReports where programId = :programId");
        query.setParameter("programId", programId);

        List<programReports> reportList = query.list();
        
        if(reportList.size() > 0) {
            for(programReports report : reportList) {
                usedReports.add(report.getId());
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

	@SuppressWarnings("unchecked")
	@Override
	public List<reportType> getAllReportTypes() throws Exception {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(reportType.class);
		criteria.addOrder( Order.asc("reportType"));
        return criteria.list();
        
       
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<reportDetails> getAggregateReportForReportType(Integer programId,
			Integer reportTypeId) throws Exception {
		
		String sql = ("SELECT reportdetails.* FROM reportaggregatedetails, reportdetails  "
				+ " where programId = :programId "
				+ " and reportaggregatedetails.reportId = reportDetails.id ");
		
		if (reportTypeId > 0) {
        	sql = sql  + " and reportTypeId = :reportTypeId";
        }
		
		sql = sql + " order by reportName";
		
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql)
                .setResultTransformer(
                        Transformers.aliasToBean(reportDetails.class))
                .setParameter("programId", programId);
        if (reportTypeId > 0) {
        	query.setParameter("reportTypeId", reportTypeId);
        }
		List <reportDetails> reportList = query.list();
        return reportList;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public reportType getReportTypeById(Integer reportTypeId) throws Exception {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(reportType.class);
		criteria.add(Restrictions.eq("id", reportTypeId));
        List <reportType> repTypeList = criteria.list();
        if (repTypeList.size() > 0) {
        	return repTypeList.get(0);
        } else {
        	return null;
        }
	}

	@Override
	@SuppressWarnings("unchecked")
	public reportDetails getReportDetailsById(Integer reportId)
			throws Exception {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(reportDetails.class);
		criteria.add(Restrictions.eq("id", reportId));
        List <reportDetails> repList = criteria.list();
		
        if (repList.size() > 0) {
        	return repList.get(0);
        } else {
        	return null;
        }
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<reportCrossTab> getCrossTabsByReportId(Integer reportId, List<Integer> statusIds)
			throws Exception {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(reportCrossTab.class);
		criteria.add(Restrictions.eq("reportId", reportId));
		criteria.add(Restrictions.in("statusId", statusIds));
		criteria.addOrder( Order.asc("dspPos"));
		List <reportCrossTab> reportCrossTabs = criteria.list();
		
        return reportCrossTabs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<reportCrossTabEntity> getCrossTabEntitiesByReportId(
			Integer reportId) throws Exception {
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(reportCrossTabEntity.class);
			criteria.add(Restrictions.eq("reportId", reportId));
			criteria.addOrder( Order.asc("entity1Id"));
			criteria.addOrder( Order.asc("entity2Id"));
			criteria.addOrder( Order.asc("entity3Id"));
			
			List <reportCrossTabEntity> reportCrossTabs = criteria.list();
			
	        return reportCrossTabs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<reportCrossTabCWData> getReportCrossTabCWDataByCTId(
			Integer crossTabId) throws Exception {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(reportCrossTabCWData.class);
		criteria.add(Restrictions.eq("reportCrossTabId", crossTabId));
		List <reportCrossTabCWData> dataList = criteria.list();
		return dataList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<programOrgHierarchyDetails> getHierarchiesForAggregatedReport(
			Integer hierarchyId, Integer reportId, String matchField)
			throws Exception {
		String sql = ("select programorghierarchy_details.*, "
				+ "case when reportcrosstabentity.id is not null and reportId = :reportId then 1 else 0 end isSelected"
				+ " from programorghierarchy_details left outer join reportcrosstabentity "
				+ " on  reportcrosstabentity." + matchField  +" = programorghierarchy_details.id  "
				+ " where programhierarchyId = :hierarchyId");

        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql)
                .setResultTransformer(
                        Transformers.aliasToBean(programOrgHierarchyDetails.class))
                .setParameter("hierarchyId", hierarchyId);

        query.setParameter("reportId", reportId);
        
        List<programOrgHierarchyDetails> putList = query.list();
        return putList;
	}

	@Override
	public void updateReportDetails(reportDetails reportDetails)
			throws Exception {
		sessionFactory.getCurrentSession().update(reportDetails);	
	}

	@Override
	public Integer createReportDetails(reportDetails reportDetails)
			throws Exception {
		Integer lastId = null;

        lastId = (Integer) sessionFactory.getCurrentSession().save(reportDetails);

        return lastId;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public reportCrossTab getCrossTabsById(Integer crossTabId) throws Exception {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(reportCrossTab.class);
		criteria.add(Restrictions.eq("id", crossTabId));
		List <reportCrossTab> dataList = criteria.list();
		if (dataList.size() > 0) {
			return dataList.get(0);
		} else  {
			return new reportCrossTab();
		}
		
	}

	@Override
	public Integer createCrossTabReport(reportCrossTab reportCrossTab)
			throws Exception {
		Integer lastId = null;
		lastId = (Integer) sessionFactory.getCurrentSession().save(reportCrossTab);
		return lastId;
	}

	@Override
	public void updateCrossTabForm(reportCrossTab reportCrossTab)
			throws Exception {
		sessionFactory.getCurrentSession().update(reportCrossTab);
	}

	@Override
	public void deleteCrossTabReport(Integer crossTabId) throws Exception {
		Query q1 = sessionFactory.getCurrentSession().createQuery("delete from reportCrossTab where id = :crossTabId");
        q1.setParameter("crossTabId", crossTabId);
        q1.executeUpdate();
		
	}

	@Override
	public void deleteCrossTabReportCWDataByCTId(Integer crossTabId)
			throws Exception {
		Query q1 = sessionFactory.getCurrentSession().createQuery("delete from reportCrossTabCWData where reportCrossTabId = :crossTabId");
        q1.setParameter("crossTabId", crossTabId);
        q1.executeUpdate();
	}

	@Override
	public void createReportCrossTabCWData(reportCrossTabCWData crossTabCWData)
			throws Exception {
		sessionFactory.getCurrentSession().save(crossTabCWData);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getCombineCWDataByCTId(Integer crossTabId)
			throws Exception {
		String sql = ("select combineCWDataId from reportcrosstabcwdata where reportCrossTabId = :crossTabId");

        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql).addScalar("combineCWDataId", StandardBasicTypes.STRING)
        		.setParameter("crossTabId", crossTabId);
        
        return query.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public List<reportRequest> getReportDetailsByStatus(List<Integer> statusList)
			throws Exception {
		Query q = sessionFactory.getCurrentSession().createSQLQuery("select id, startProcessTime, programId from reportRequests where DATE_ADD(startProcessTime, INTERVAL 3 minute) < now() and statusId in (:statusList) order by startProcessTime ").setResultTransformer(
                Transformers.aliasToBean(reportRequest.class));
                q.setParameterList("statusList", statusList);
		
		return q.list();
	}

	@Override
	@Transactional
	public void updateReportRequestStatus(Integer reportRequestId, Integer statusId) throws Exception {
		Query q1 = sessionFactory.getCurrentSession().createSQLQuery("update reportRequests set statusId = :statusId where "
				+ " id = :reportRequestId");
        q1.setParameter("reportRequestId", reportRequestId);
        q1.setParameter("statusId", statusId);
        q1.executeUpdate();
	}

}
