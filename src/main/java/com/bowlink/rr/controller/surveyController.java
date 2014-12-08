/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.controller;


import com.bowlink.rr.model.SurveyChangeLogs;
import com.bowlink.rr.model.User;
import com.bowlink.rr.model.activityCodes;
import com.bowlink.rr.model.surveys;
import com.bowlink.rr.model.userActivityLog;
import com.bowlink.rr.service.activityCodeManager;
import com.bowlink.rr.service.surveyManager;
import com.bowlink.rr.service.userManager;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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

/**
 *
 * @author chadmccue
 */
@Controller
@RequestMapping("/programAdmin/surveys")
public class surveyController {
    
    @Autowired
    surveyManager surveymanager;
 
    @Autowired
    activityCodeManager activitycodemanager;
    
    @Autowired
    userManager usermanager;
    
    
    private String controllerName = "survey";
 
    /**
     * The '' request will display the list of surveys.
     *
     * @param request
     * @param response
     * @return	the program admin survey view
     * @throws Exception
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView listSurveys(HttpServletRequest request, HttpServletResponse response, 
    		HttpSession session, RedirectAttributes redirectAttr) throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/surveys");

        /* Get the list of programs in the system */
        List<surveys> surveys = surveymanager.getProgramSurveys((Integer) session.getAttribute("selprogramId"));
        
        mav.addObject("surveys", surveys);

        return mav;

    }
    

    /**
     * The create will start a blank page for surveys
     *
     * @param request
     * @param response
     * @return	the program create survey view
     * @throws Exception
     */
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView newSurvey(HttpServletRequest request, 
    		HttpServletResponse response, HttpSession session, RedirectAttributes redirectAttr) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("survey", new surveys ());
        /* Get the list of programs in the system */
        List<activityCodes> activityCodes = activitycodemanager.getActivityCodes(0);
        mav.addObject("activityCodes", activityCodes);
        mav.addObject("create", "create");
        mav.setViewName("/surveys/create");
        return mav;

    }
    
    /**
     * The create will start a blank page for surveys
     *
     * @param request
     * @param response
     * @return	page 1 of the survey
     * @throws Exception
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView createSurvey(@Valid @ModelAttribute(value = "survey") surveys survey, 
    		BindingResult result,
    		@RequestParam String action,
    		HttpSession session, RedirectAttributes redirectAttr) throws Exception {
    	
    	ModelAndView mav = new ModelAndView();
        
        if (result.hasErrors()) {
        	List<activityCodes> activityCodes = activitycodemanager.getActivityCodes(0);
            mav.addObject("activityCodes", activityCodes);
            mav.addObject("create", "create");
            mav.setViewName("/surveys/create");
            return mav;
        }
        
        Integer programId = (Integer) session.getAttribute("selprogramId");
        survey.setProgramId(programId);
        
        // TODO see if Duplicates Allowed  means TITLE
        List <surveys> existingTitle = surveymanager.getProgramSurveysByTitle(survey);
		if (existingTitle.size() != 0) {
        	List<activityCodes> activityCodes = activitycodemanager.getActivityCodes(0);
            mav.addObject("activityCodes", activityCodes);
        	mav.addObject("existingTitle", "Title is in use by this program already.");
        	mav.addObject("create", "create");
        	mav.setViewName("/surveys/create");
        	return mav;
        } 
        
        //insert survey into db
        Integer surveyId = surveymanager.saveSurvey(survey);
        
        if (action.equals("save")) {
            redirectAttr.addFlashAttribute("savedStatus", "updated");
            mav = new ModelAndView(new RedirectView("/programAdmin/surveys/page"));
            session.setAttribute("surveyId", surveyId);
            return mav;
        } else {
            mav = new ModelAndView(new RedirectView("/programAdmin/surveys"));
            return mav;
        }

    }
    
    /**
     * @param i
     * @param session
     * @param redirectAttr
     * @param authentication
     * @return
     * @throws Exception
     * This allows admins to view and modify the survey
     * Permission will be tracked
     */
    
    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public ModelAndView viewSurveyDetails(@RequestParam String i,
    		HttpSession session, RedirectAttributes redirectAttr, Authentication authentication) throws Exception {
        	ModelAndView mav = new ModelAndView();
        	mav.setViewName("/surveys/details");
        	mav.addObject("edit", "edit");
        //i -       
        Integer surveyId = 0;
        //see if param is valid
        try {
        	surveyId = Integer.parseInt(i);
        } catch (Exception ex) {
        	//log here
        	try {
	        	userActivityLog ua = new userActivityLog();
	        	ua.setActivityDesc("accessed denied for survey - survey is not numeric");
	        	ua.setController(controllerName);
	        	ua.setPageAccessed("/details");
	        	User userDetails = (User)session.getAttribute("userDetails");
	        	ua.setSystemUserId(userDetails.getId());
	            usermanager.insertUserLog (ua);
        	} catch (Exception ex1) {
        		ex1.printStackTrace();
        	}
        	mav.addObject("notValid", "Survey is not valid or you do not have permission.");
        	return mav;
        }
        
        //we look up survey and send back survey object      
        surveys survey = surveymanager.getSurveysById(surveyId);
        if (survey != null) {
        	//make sure we are in the right program
        	if(survey.getProgramId() == (Integer) session.getAttribute("selprogramId")) {
	        	List<activityCodes> activityCodes = activitycodemanager.getActivityCodes(0);
	            mav.addObject("activityCodes", activityCodes);        
	            mav.addObject("survey", survey);
	            mav.addObject("surveyTitle", survey.getTitle());
	            
        	} else {
        		//log here
            	try {
    	        	userActivityLog ua = new userActivityLog();
    	        	ua.setActivityDesc("accessed denied for survey - program Id mismatch");
    	        	ua.setController(controllerName);
    	        	ua.setSurveyId(surveyId);
    	        	ua.setPageAccessed("/details");
    	        	User userDetails = (User)session.getAttribute("userDetails");
    	        	ua.setSystemUserId(userDetails.getId());
    	            usermanager.insertUserLog (ua);
            	} catch (Exception ex1) {
            		ex1.printStackTrace();
            	}
        	}
        }
        
        return mav;
    }
    
    
    @RequestMapping(value = "/details", method = RequestMethod.POST)
    public ModelAndView editSurvey(@Valid @ModelAttribute(value = "survey") surveys survey, 
    		BindingResult result,
    		@RequestParam String action,
    		HttpSession session, RedirectAttributes redirectAttr) throws Exception {
    	
    	ModelAndView mav = new ModelAndView();
    	mav.addObject("edit", "edit");
    	
        if (result.hasErrors()) {
        	List<activityCodes> activityCodes = activitycodemanager.getActivityCodes(0);
            mav.addObject("activityCodes", activityCodes);
            mav.setViewName("/surveys/details");
            return mav;
        }
        
        Integer programId = (Integer) session.getAttribute("selprogramId");
        survey.setProgramId(programId);
        
        
        //update survey in db
        if(survey.getProgramId() == (Integer) session.getAttribute("selprogramId")) {
        	surveymanager.updateSurvey(survey);
        } else {
        	 mav = new ModelAndView(new RedirectView("/programAdmin/details"));
             return mav;
        }
        if (action.equals("save")) {
            redirectAttr.addFlashAttribute("savedStatus", "updated");
            mav = new ModelAndView(new RedirectView("/programAdmin/surveys/page"));
            session.setAttribute("surveyId", survey.getId());
            return mav;
        } else {
            mav = new ModelAndView(new RedirectView("/programAdmin/surveys"));
            return mav;
        }

    }
    
    
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ModelAndView newSurveyPage(HttpServletRequest request, 
    		HttpServletResponse response, HttpSession session, RedirectAttributes redirectAttr) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("survey", new surveys ());
        /* Get the list of programs in the system */
        List<activityCodes> activityCodes = activitycodemanager.getActivityCodes(0);
        mav.addObject("activityCodes", activityCodes);
        mav.addObject("create", "create");
        mav.setViewName("/surveys/page");
        return mav;

    }
    
    /**
     * This method returns the change log - save note box
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/saveNote", method = RequestMethod.GET)
    @ResponseBody public ModelAndView newNoteBox() throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("btnValue", "Save Note");
        mav.setViewName("/programAdmin/surveys/saveNote");
        return mav;
    }
    
    
    @RequestMapping(value = "/saveNote", method = RequestMethod.POST)
    @ResponseBody public ModelAndView saveNote(HttpServletRequest request, 
    		HttpServletResponse response, HttpSession session, 
    		RedirectAttributes redirectAttr) throws Exception {
        ModelAndView mav = new ModelAndView();
        //surveyId
        SurveyChangeLogs scl = new SurveyChangeLogs();
        
        scl.setNotes(request.getParameter("notes"));
        scl.setSurveyId(Integer.parseInt(request.getParameter("surveyId")));
        User userDetails = (User)session.getAttribute("userDetails");
        scl.setSystemUserId(userDetails.getId());
        //insert here
        surveymanager.saveChangeLogs(scl);
        
        mav.setViewName("/programAdmin/surveys/saveNote");
        return mav;
    }
    
    /**
     * This method returns the modal with change logs info
     * @param session
     * @param i
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/viewChangeLog", method = RequestMethod.GET)
    @ResponseBody public ModelAndView viewChangeLog(HttpSession session, @RequestParam String i) throws Exception {
        ModelAndView mav = new ModelAndView();
        
        /** we check to make sure i is a number **/
        
        
        /** we get the i info and make sure it matches the program id of the survey **/
        
        mav.setViewName("/programAdmin/surveys/changeLog");
        return mav;
    }
    
}
