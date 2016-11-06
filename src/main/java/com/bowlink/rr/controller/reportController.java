package com.bowlink.rr.controller;

import com.bowlink.rr.model.program;
import com.bowlink.rr.model.programReports;
import com.bowlink.rr.model.reports;
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
@RequestMapping(value={"/sysAdmin/reports","/sysAdmin/programs/{programName}/canned-reports"})
public class reportController {
    
    @Autowired
    reportManager reportmanager;
    
    @Autowired
    programManager programmanager;
    
    /**
     * The '' request will serve up the canned report list page.
     *
     * @param request
     * @param response
     * 
     * @return	the canned report list page view
     * @throws Exception
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ModelAndView listReports(HttpServletRequest request, HttpServletResponse response, HttpSession session, RedirectAttributes redirectAttr) throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/reports");

        /* Get the list of programs in the system */
        List<reports> reports = reportmanager.getAllReports();

        mav.addObject("reports", reports);

        return mav;

    }
    
    /**
     * The 'report.create' GET request will be used to create a new system report
     *
     * @return The blank report page
     *
     * @Objects (1) An object that will hold the blank report
     */
    @RequestMapping(value = "/report.create", method = RequestMethod.GET)
    @ResponseBody public ModelAndView newreportForm(HttpSession session) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/reports/details");

        //Create a new blank provider.
        reports report = new reports();
       
        mav.addObject("btnValue", "Create");
        mav.addObject("reportdetails", report);

        return mav;
    }
    
    /**
     * The 'create_report' POST request will handle submitting the new report.
     *
     * @param reportdetails    The object containing the report form fields
     * @param result        The validation result
     * @param redirectAttr  The variable that will hold values that can be read after the redirect
     *
     * @throws Exception
     */
    @RequestMapping(value = "/create_report", method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView createreport(@Valid @ModelAttribute(value = "reportdetails") reports reportdetails, BindingResult result, HttpSession session) throws Exception {

        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/sysAdmin/reports/details");
            mav.addObject("btnValue", "Create");
            return mav;
        }
        
        reportmanager.createReport(reportdetails);

        ModelAndView mav = new ModelAndView("/sysAdmin/reports/details");
        mav.addObject("success", "reportCreated");
        return mav;
    }
    
    /**
     * The 'report.edit' GET request will be used to get the details for the clicked report
     *
     * @return The report details page with the clicked report populated
     *
     */
    @RequestMapping(value = "/report.edit", method = RequestMethod.GET)
    public @ResponseBody ModelAndView reportForm(@RequestParam Integer reportId, HttpSession session) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/reports/details");

        //Create a new blank provider.
        reports reportDetails = reportmanager.getReportById(reportId);
       
        mav.addObject("btnValue", "Update");
        mav.addObject("reportdetails", reportDetails);

        return mav;
    }
    
    /**
     * The 'update_report' POST request will handle submitting the report changes.
     *
     * @param reportdetails    The object containing the report form fields
     * @param result        The validation result
     * @param redirectAttr  The variable that will hold values that can be read after the redirect
     *
     * @throws Exception
     */
    @RequestMapping(value = "/update_report", method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView updateReport(@Valid @ModelAttribute(value = "reportdetails") reports reportdetails, BindingResult result, HttpSession session) throws Exception {

        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/sysAdmin/reports/details");
            mav.addObject("btnValue", "Update");
            return mav;
        }
        
        reportmanager.updateReport(reportdetails);

        ModelAndView mav = new ModelAndView("/sysAdmin/reports/details");
        mav.addObject("success", "reportUpdated");
        return mav;
    }
    
    /**
     * The '/{programName}/canned-reports' GET request will display the page to associate canned reports to a program.
     *
     * @param programName	The {programName} will be the program name with spaces removed.
     *
     * @return	Will return the program reports page.
     *
     * @throws Exception
     *
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView programReports(HttpSession session) throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/programReports");
        mav.addObject("id", session.getAttribute("programId"));

        program programDetails = programmanager.getProgramById((Integer) session.getAttribute("programId"));
        mav.addObject("programDetails", programDetails);

        List<Integer> useReports = reportmanager.getProgramReports((Integer) session.getAttribute("programId"));

        List<reports> availReports = reportmanager.getAllReports();

        if (!useReports.isEmpty()) {
            for (reports report : availReports) {

                if (useReports.contains(report.getId())) {
                    report.setUseReport(true);
                }
            }
        }

        mav.addObject("availReports", availReports);

        return mav;

    }
    

    /**
     * The '/{programName}/canned-reports' POST request will save the program report form.
     *
     * @param List<Integer>	The list of reportIds to use.
     *
     * @return	Will return the program details page.
     *
     * @throws Exception
     *
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ModelAndView saveprogramReports(@RequestParam List<Integer> reportIds, @RequestParam String action, RedirectAttributes redirectAttr, HttpSession session) throws Exception {

        Integer selProgramId = (Integer) session.getAttribute("programId");

        if (reportIds.isEmpty()) {
            reportmanager.deleteProgramReports(selProgramId);
        } else {
            reportmanager.deleteProgramReports(selProgramId);

            for (Integer reportId : reportIds) {

                programReports report = new programReports();

                report.setProgramId(selProgramId);
                report.setId(reportId);

                reportmanager.saveProgramReports(report);
            }
        }

        redirectAttr.addFlashAttribute("savedStatus", "updatedprogramreports");

        if (action.equals("save")) {
            ModelAndView mav = new ModelAndView(new RedirectView("canned-reports"));
            return mav;
        } else {
            ModelAndView mav = new ModelAndView(new RedirectView("../../programs"));
            return mav;
        }

    }
    
}
