package com.bowlink.rr.controller;


import com.bowlink.rr.model.program;
import com.bowlink.rr.model.programOrgHierarchy;
import com.bowlink.rr.model.programOrgHierarchyDetails;
import com.bowlink.rr.model.reportCrossTab;
import com.bowlink.rr.model.reportCrossTabEntity;
import com.bowlink.rr.model.reportDetails;
import com.bowlink.rr.model.reportType;
import com.bowlink.rr.service.dataElementManager;
import com.bowlink.rr.service.orgHierarchyManager;
import com.bowlink.rr.service.programManager;
import com.bowlink.rr.service.reportManager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author chadmccue
 */
@Controller
@RequestMapping(value={"/sysAdmin/aggregated-reports","/sysAdmin/programs/{programName}/aggregated-reports"})
public class aggregatedReportController {
    
    @Autowired
    programManager programmanager;
    
    @Autowired
    reportManager reportmanager;
    
    @Autowired
    orgHierarchyManager orghierarchymanager;
    
    @Autowired
    dataElementManager dataelementmanager;
    
    
 
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView programActivityCodes(@RequestParam(value = "rtId", required = false) Integer reportTypeId, HttpSession session) throws Exception {
        
       

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/aggregatedReports");
        mav.addObject("id", session.getAttribute("programId"));

        program programDetails = programmanager.getProgramById((Integer) session.getAttribute("programId"));
        mav.addObject("programDetails", programDetails);

        List<reportType> rtTypes = reportmanager.getAllReportTypes();
        
        if(reportTypeId == null) {
        	reportTypeId = 0;
        } else {
        	//we get report details
        	reportType rtDetails = reportmanager.getReportTypeById(reportTypeId);
        	
        	mav.addObject("rtDetails", rtDetails);	
        }
        
        List<reportDetails> repList = reportmanager.getAllForReportType(programDetails.getId(), reportTypeId);
    	
        mav.addObject("repList", repList);	
        mav.addObject("allAggRepTypes", rtTypes);
        mav.addObject("selRepType", reportTypeId);

        return mav;

    }  
    
    @RequestMapping(value = "reportDetails", method = RequestMethod.GET)
    public ModelAndView crossTabDetails(@RequestParam(value = "r", required = true) Integer reportId, HttpSession session) throws Exception {
        
    	ModelAndView mav = new ModelAndView();
        
        Integer programId = (Integer) session.getAttribute("programId");
        mav.setViewName("/reportDetails");
        mav.addObject("id", programId);
        
        program programDetails = programmanager.getProgramById(programId);
        mav.addObject("programDetails", programDetails);
        //we get report details
        reportDetails details = reportmanager.getReportDetailsById(reportId, true);
        //we get crosstab details
        List <reportCrossTab> ctList = reportmanager.getCrossTabsByReportId(reportId);
        for (reportCrossTab crossTab : ctList)
        {
        	crossTab.setCombineCWDataId(reportmanager.getReportCrossTabCWDataByCTId(crossTab.getId()));
        	crossTab.setCwDataCol(dataelementmanager.getCrosswalkDataByCWId(crossTab.getCwIdCol()));
        	crossTab.setCwDataRow(dataelementmanager.getCrosswalkDataByCWId(crossTab.getCwIdRow()));
        }
        
        details.setReportCrossTabEntities(reportmanager.getCrossTabEntitiesByReportId(reportId));
        details.setReportCrossTabs(ctList);
        
        //we get program entities - for projects
        List<programOrgHierarchy> hierarchyList = orghierarchymanager.getProgramOrgHierarchy(programId);
        
        //get selected aggregated fields
        List<programOrgHierarchyDetails> entities = reportmanager.getHierarchiesForAggregatedReport(hierarchyList.get(1).getId(), reportId, "entity2Id");

        mav.addObject("entities", entities);
        mav.addObject("hierarchyList", hierarchyList);
        mav.addObject("reportDetail", details);
        

        return mav;

    }  
}
