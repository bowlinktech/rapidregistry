package com.bowlink.rr.controller;

import com.bowlink.rr.model.Log_userSurveyActivity;
import com.bowlink.rr.model.User;
import com.bowlink.rr.model.crosswalks;
import com.bowlink.rr.model.program;
import com.bowlink.rr.model.programOrgHierarchy;
import com.bowlink.rr.model.programOrgHierarchyDetails;
import com.bowlink.rr.model.reportCrossTab;
import com.bowlink.rr.model.reportCrossTabCWData;
import com.bowlink.rr.model.reportDetails;
import com.bowlink.rr.model.reportType;
import com.bowlink.rr.service.dataElementManager;
import com.bowlink.rr.service.orgHierarchyManager;
import com.bowlink.rr.service.programManager;
import com.bowlink.rr.service.reportManager;
import com.bowlink.rr.service.userManager;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
    userManager usermanager;
    
    @Autowired
    orgHierarchyManager orghierarchymanager;
    
    @Autowired
    dataElementManager dataelementmanager;
    
    private String controllerName = "aggregatedReportControllers";
 
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
        List <reportCrossTab> ctList = reportmanager.getCrossTabsByReportId(reportId, Arrays.asList(1,2));
        for (reportCrossTab crossTab : ctList)
        {
        	crossTab.setCombineCWDataStringList(reportmanager.getCombineCWDataByCTId(crossTab.getId()));
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
        mav.addObject("report", details);

       

        return mav;

    }  
    
    
    /**
     * The 'saveReportTitle.do' POST request will submit the report title change.
     * 
     * @param pageId    The id of the current page.
     * @param pageTitle The new page title
     * @param session
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "savePageTitle.do", method = RequestMethod.POST)
    public @ResponseBody String savePageTitle(@RequestParam(value = "reportId", required = true) Integer reportId, @RequestParam(value = "reportTitle", required = true) String reportTitle, HttpSession session) throws Exception {

        //get page info
        reportDetails report = reportmanager.getReportDetailsById(reportId, false);
        report.setReportName(reportTitle);

        // update 
        reportmanager. updateReportDetails(report);
        return reportTitle;
    }
    
    
    
    @RequestMapping(value = "getCrossTabForm.do", method = RequestMethod.GET)
    @ResponseBody public ModelAndView viewCrossTableTableForm(HttpSession session, @RequestParam(value = "crossTabId", required = true) Integer crossTabId) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/programs/aggregatedReports/crossTabForm");
        //get cw for program
        Integer programId = (Integer) session.getAttribute("programId");
        
        List<crosswalks> crosswalks = dataelementmanager.getCrosswalks(0, 0, programId);
        mav.addObject("crosswalks", crosswalks);

        //get crosstab info
        reportCrossTab details = reportmanager.getCrossTabsById(crossTabId);
        
        mav.addObject("details", details);
        mav.addObject("btnValue", "Update");
        
        return mav;
    } 
    
    @RequestMapping(value = "updateCrossTabForm", method = RequestMethod.POST)
    public @ResponseBody ModelAndView saveCrossTabReport(
    		@Valid @ModelAttribute(value = "details") reportCrossTab details, 
    		BindingResult result, HttpSession session) throws Exception {
    	
    	 Integer programId = (Integer) session.getAttribute("programId");
    	 ModelAndView mav = new ModelAndView();
    	
           
    	if (result.hasErrors()) {
    		mav.setViewName("/sysAdmin/programs/aggregatedReports/crossTabForm");
            List<crosswalks> crosswalks = dataelementmanager.getCrosswalks(0, 0, programId);
            mav.addObject("crosswalks", crosswalks);
            mav.addObject("message", "detailsFailed");
            
            if(details.getId() > 0) {
                mav.addObject("btnValue", "Update");
            } else {
                mav.addObject("btnValue", "Add");
            }
            return mav;
        } else {
        	if(details.getId() > 0) {
        		reportmanager.updateCrossTabForm(details);
        		details.setCombineCWDataStringList(reportmanager.getCombineCWDataByCTId(details.getId()));
                details.setCwDataCol(dataelementmanager.getCrosswalkDataByCWId(details.getCwIdCol()));
        		details.setCwDataRow(dataelementmanager.getCrosswalkDataByCWId(details.getCwIdRow()));
                mav.addObject("btnValue", "Update");
                mav.addObject("crossTab", details);
            	mav.setViewName("/sysAdmin/programs/aggregatedReports/crossTabTable");
            	
            } else {
            	//set max dspPos
         	   details.setDspPos(reportmanager.getCrossTabsByReportId (details.getReportId(), Arrays.asList(1,2)).size() + 1);
         	   Integer ctrId = reportmanager.createCrossTabReport(details);
               details.setId(ctrId);
               details.setCombineCWDataStringList(reportmanager.getCombineCWDataByCTId(details.getId()));
               details.setCwDataCol(dataelementmanager.getCrosswalkDataByCWId(details.getCwIdCol()));
               details.setCwDataRow(dataelementmanager.getCrosswalkDataByCWId(details.getCwIdRow()));
               mav.setViewName("/sysAdmin/programs/aggregatedReports/returnText"); 
               mav.addObject("returnText", "addedNewCrossTabItem");
            }
        	return mav;
        }
    }
    
    @RequestMapping(value = "newCrossTabTableForm.do", method = RequestMethod.GET)
    @ResponseBody public ModelAndView newCrossTableTableForm(HttpSession session, @RequestParam(value = "reportId", required = true) Integer reportId) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/programs/aggregatedReports/crossTabForm");
        //get cw for program
        Integer programId = (Integer) session.getAttribute("programId");
        
        List<crosswalks> crosswalks = dataelementmanager.getCrosswalks(0, 0, programId);
        mav.addObject("crosswalks", crosswalks);

        //get crosstab info
        reportCrossTab details = new reportCrossTab();
        details.setDspPos(reportmanager.getCrossTabsByReportId (details.getReportId(),  Arrays.asList(1,2)).size() + 1);
        details.setReportId(reportId);
        mav.addObject("details", details);
        mav.addObject("btnValue", "Add");
        return mav;
    } 
    
    @RequestMapping(value = "removeCrossTable.do", method = RequestMethod.POST)
    @ResponseBody public Integer removeCrossTableTable(HttpSession session, @RequestParam(value = "crossTabId", required = true) Integer crossTabId) throws Exception {
        
    	//we are going to delete it instead of changing its status as it is easy to set up
    	//we delete from reportcrosstabcwdata
    	reportmanager.deleteCrossTabReportCWDataByCTId(crossTabId);
    	
    	//we delete from reportcrosstab
    	reportmanager.deleteCrossTabReport(crossTabId);
    	
    	//log user
    	try {
            Log_userSurveyActivity ua = new Log_userSurveyActivity();
            ua.setActivityDesc("Deleted Crosstab Table");
            ua.setController(controllerName);
            ua.setPageAccessed("removeCrossTable.do");
            ua.setActivityDesc("crossTabId" + crossTabId);
            ua.setProgramId((Integer) session.getAttribute("programId"));
            User userDetails = (User) session.getAttribute("userDetails");
            ua.setSystemUserId(userDetails.getId());
            usermanager.insertUserLog(ua);
        } catch (Exception ex1) {
            ex1.printStackTrace();
        }
    	
        return 1;
    } 
    
    @RequestMapping(value = "updateCrossTabCWData", method = RequestMethod.POST)
    @ResponseBody public Integer updateCrossTabCW(HttpSession session, 
    		@RequestParam(value = "cwDataSet", required = true) List <String> cwDataSet,
    		@RequestParam(value = "cwCrossTabId", required = true) Integer crossTabId) 
    		throws Exception {
        
        //first we delete existing set
        reportmanager.deleteCrossTabReportCWDataByCTId(crossTabId);
        
    	//we loop through list and save 
    	for (String cwData : cwDataSet) {
    		reportCrossTabCWData combinedData = new reportCrossTabCWData();
    		combinedData.setCombineCWDataId(cwData);
    		combinedData.setReportCrossTabId(crossTabId);
    		reportmanager.createReportCrossTabCWData(combinedData);	
    	}
    	
    	//log user
    	try {
            Log_userSurveyActivity ua = new Log_userSurveyActivity();
            ua.setActivityDesc("Updated Crosstab Table");
            ua.setController(controllerName);
            ua.setPageAccessed("updateCrossTabCWData");
            ua.setActivityDesc("crossTabId" + crossTabId);
            ua.setProgramId((Integer) session.getAttribute("programId"));
            User userDetails = (User) session.getAttribute("userDetails");
            ua.setSystemUserId(userDetails.getId());
            usermanager.insertUserLog(ua);
        } catch (Exception ex1) {
            ex1.printStackTrace();
        }
    	
        return 1;
    }   
    
    
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "getCWDataList.do", method = RequestMethod.GET)
    public @ResponseBody List getModules(@RequestParam(value = "crossTabId", required = true) Integer crossTabId) throws Exception {
    	List <String> combineDataList  = reportmanager.getCombineCWDataByCTId(crossTabId);
        return combineDataList;
    }
    
    
}
