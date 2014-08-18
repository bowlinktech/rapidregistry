package com.bowlink.rr.controller;


import com.bowlink.rr.model.activityCodes;
import com.bowlink.rr.service.activityCodeManager;
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
@RequestMapping("/sysAdmin/activity-codes")
public class activityCodeController {
    
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
    @RequestMapping(value = "", method = RequestMethod.GET)
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
    
}
