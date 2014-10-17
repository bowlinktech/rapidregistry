package com.bowlink.rr.controller;


import com.bowlink.rr.model.activityCodes;
import com.bowlink.rr.model.program;
import com.bowlink.rr.model.programActivityCodes;
import com.bowlink.rr.service.activityCodeManager;
import com.bowlink.rr.service.programManager;
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
@RequestMapping(value={"/sysAdmin/activity-codes","/sysAdmin/programs/{programName}/activity-codes"})
public class activityCodeController {
    
    @Autowired
    programManager programmanager;
    
    @Autowired
    activityCodeManager activitycodemanager;
    
    /**
     * The '' request will serve up the activity code list page.
     *
     * @param request
     * @param response
     * 
     * @return	the activity code list page view
     * @throws Exception
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ModelAndView listActivityCodes(HttpServletRequest request, HttpServletResponse response, HttpSession session, RedirectAttributes redirectAttr) throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/activityCodes");

        /* Get the list of programs in the system */
        List<activityCodes> activityCodes = activitycodemanager.getActivityCodes(0);

        mav.addObject("activityCodes", activityCodes);

        return mav;

    }
    
    /**
     * The 'code.create' GET request will be used to create a new system activity code
     *
     * @return The blank activity code page
     *
     * @Objects (1) An object that will hold the blank activity code
     */
    @RequestMapping(value = "/code.create", method = RequestMethod.GET)
    @ResponseBody public ModelAndView newActivityCodeForm(HttpSession session) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/activityCodes/details");

        //Create a new blank provider.
        activityCodes code = new activityCodes();
       
        mav.addObject("btnValue", "Create");
        mav.addObject("codedetails", code);

        return mav;
    }
    
    /**
     * The 'create_activityCode' POST request will handle submitting the new activity code.
     *
     * @param mpidetails    The object containing the activity code form fields
     * @param result        The validation result
     * @param redirectAttr  The variable that will hold values that can be read after the redirect
     *
     *
     * @Objects	(1) The object containing all the information for the clicked org
     * @throws Exception
     */
    @RequestMapping(value = "/create_activityCode", method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView createActivityCode(@Valid @ModelAttribute(value = "codedetails") activityCodes codedetails, BindingResult result, HttpSession session) throws Exception {

        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/sysAdmin/activityCodes/details");
            mav.addObject("btnValue", "Create");
            return mav;
        }

       activitycodemanager.createActivityCode(codedetails);
        
        ModelAndView mav = new ModelAndView("/sysAdmin/activityCodes/details");
        mav.addObject("success", "codeCreated");
        return mav;
    }
    
    /**
     * The 'code.edit' GET request will be used to create a new system activity code
     *
     * @return The blank activity code page
     *
     * @Objects (1) An object that will hold the blank activity code
     */
    @RequestMapping(value = "/code.edit", method = RequestMethod.GET)
    public @ResponseBody ModelAndView activityCodeForm(@RequestParam Integer codeId, HttpSession session) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/activityCodes/details");

        //Create a new blank provider.
        activityCodes codeDetails = activitycodemanager.getActivityCodeById(codeId);
       
        mav.addObject("btnValue", "Update");
        mav.addObject("codedetails", codeDetails);

        return mav;
    }
    
    /**
     * The 'update_activityCode' POST request will handle submitting the activity code changes.
     *
     * @param mpidetails    The object containing the activity code form fields
     * @param result        The validation result
     * @param redirectAttr  The variable that will hold values that can be read after the redirect
     *
     *
     * @Objects	(1) The object containing all the information for the clicked org
     * @throws Exception
     */
    @RequestMapping(value = "/update_activityCode", method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView updateActivityCode(@Valid @ModelAttribute(value = "codedetails") activityCodes codedetails, BindingResult result, HttpSession session) throws Exception {

        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/sysAdmin/activityCodes/details");
            mav.addObject("btnValue", "Update");
            return mav;
        }

       activitycodemanager.updateActivityCode(codedetails);
        
        ModelAndView mav = new ModelAndView("/sysAdmin/activityCodes/details");
        mav.addObject("success", "codeUpdated");
        return mav;
    }
    
    
    /**
     * The '/{programName}/activity-codes' GET request will display the program activity code page.
     *
     * @param programName	The {programName} will be the program name with spaces removed.
     *
     * @return	Will return the program details page.
     *
     * @throws Exception
     *
    */ 
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView programActivityCodes(HttpSession session) throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/programActivityCodes");
        mav.addObject("id", session.getAttribute("programId"));

        program programDetails = programmanager.getProgramById((Integer) session.getAttribute("programId"));
        mav.addObject("programDetails", programDetails);

        List<activityCodes> activityCodes = activitycodemanager.getActivityCodes(0);

        for (activityCodes code : activityCodes) {

            boolean codeBeingUsed = activitycodemanager.getActivityCodesByProgram((Integer) session.getAttribute("programId"), code.getId());

            code.setSelected(codeBeingUsed);

        }

        mav.addObject("availactivityCodes", activityCodes);

        return mav;

    }

    /**
     * The '/{programName}/activity-codes' GET request will display the program activity code page.
     *
     * @param programName	The {programName} will be the program name with spaces removed.
     * @param action
     * @param activityCodeList
     * @param redirectAttr
     * @param session
     * @return
     * @throws Exception
    */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ModelAndView saveProgramActivityCodes(@RequestParam String action, @RequestParam(value = "activityCodeList", required = false) List<Integer> activityCodeList, RedirectAttributes redirectAttr, HttpSession session) throws Exception {

        if (activityCodeList == null) {
            activitycodemanager.removeProgramActivityCodes((Integer) session.getAttribute("programId"));
        } else {
            activitycodemanager.removeProgramActivityCodes((Integer) session.getAttribute("programId"));

            for (Integer code : activityCodeList) {
                programActivityCodes newCodeAssoc = new programActivityCodes();
                newCodeAssoc.setCodeId(code);
                newCodeAssoc.setProgramId((Integer) session.getAttribute("programId"));

                activitycodemanager.saveProgramActivityCode(newCodeAssoc);
            }

        }

        redirectAttr.addFlashAttribute("savedStatus", "codesupdated");

        if (action.equals("save")) {
            ModelAndView mav = new ModelAndView(new RedirectView("activity-codes"));
            return mav;
        } else {
            ModelAndView mav = new ModelAndView(new RedirectView("../../programs"));
            return mav;
        }

    }
    
}
