/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.controller;


import com.bowlink.rr.model.AnswerTypes;
import com.bowlink.rr.model.SurveyChangeLogs;
import com.bowlink.rr.model.SurveyPages;
import com.bowlink.rr.model.User;
import com.bowlink.rr.model.activityCodes;
import com.bowlink.rr.model.surveys;
import com.bowlink.rr.model.Log_userSurveyActivity;
import com.bowlink.rr.service.activityCodeManager;
import com.bowlink.rr.service.surveyManager;
import com.bowlink.rr.service.userManager;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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
        
        /** log user **/
        try {
        	Log_userSurveyActivity ua = new Log_userSurveyActivity();
        	ua.setActivityDesc("Accessed survey list");
        	ua.setController(controllerName);
        	ua.setPageAccessed("/surveys");
        	ua.setProgramId((Integer) session.getAttribute("selprogramId"));
        	User userDetails = (User)session.getAttribute("userDetails");
        	ua.setSystemUserId(userDetails.getId());
            usermanager.insertUserLog (ua);
    	} catch (Exception ex1) {
    		ex1.printStackTrace();
    	}
        

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
        /** add drop down for other surveys for this program **/
        List<surveys> surveys = surveymanager.getProgramSurveys((Integer) session.getAttribute("selprogramId"));
        mav.addObject("surveys", surveys);	
        mav.setViewName("/surveys/create");
        
        /** log user **/
        try {
        	Log_userSurveyActivity ua = new Log_userSurveyActivity();
        	ua.setActivityDesc("Created Survey Form");
        	ua.setController(controllerName);
        	ua.setPageAccessed("/surveys");
        	ua.setProgramId((Integer) session.getAttribute("selprogramId"));
        	User userDetails = (User)session.getAttribute("userDetails");
        	ua.setSystemUserId(userDetails.getId());
            usermanager.insertUserLog (ua);
    	} catch (Exception ex1) {
    		ex1.printStackTrace();
    	}
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
        
        //we do not allow duplicate title
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
        //we automatically add page 1
        SurveyPages sp = new SurveyPages();
        sp.setPageNum(1);
        sp.setPageTitle("");
        sp.setSurveyId(surveyId);
        surveymanager.createSurveyPage(sp);
        redirectAttr.addFlashAttribute("msg", "created");
        
        /** log user **/
        try {
        	Log_userSurveyActivity ua = new Log_userSurveyActivity();
        	ua.setActivityDesc("Created Survey");
        	ua.setController(controllerName);
        	ua.setPageAccessed("/create");
        	ua.setProgramId((Integer) session.getAttribute("selprogramId"));
        	ua.setSurveyId(surveyId);
        	User userDetails = (User)session.getAttribute("userDetails");
        	ua.setSystemUserId(userDetails.getId());
            usermanager.insertUserLog (ua);
    	} catch (Exception ex1) {
    		ex1.printStackTrace();
    	}
        
        if (action.equals("save")) {
            redirectAttr.addFlashAttribute("savedStatus", "updated");
            mav = new ModelAndView(new RedirectView("/programAdmin/surveys/details?s=" + surveyId));
            session.setAttribute("surveyId", surveyId);
            return mav;
        } else {
            mav = new ModelAndView(new RedirectView("/programAdmin/surveys"));
            return mav;
        }

    }
    
    /**
     * @param s surveyId 
     * @param session
     * @param redirectAttr
     * @param authentication
     * @return
     * @throws Exception
     * This page should contain all editable parts of a survey, all parts should display a
     * modal when clicked
     * Permission will be tracked
     */
    
    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public ModelAndView viewSurveyDetails(@RequestParam String s,
    		HttpSession session, RedirectAttributes redirectAttr, Authentication authentication) 
    				throws Exception {
        	ModelAndView mav = new ModelAndView();
        	mav.setViewName("/surveys/details");
        	mav.addObject("edit", "edit");
        
        
        //s - survey Id       
        surveys survey = checkSurveyPermission(session, s, "/details" );
        	
        if(survey != null) {
        	/**we look up survey and send back survey object  
             * we look for survey --> pages --> questions --> answers
             * we loop and display in jsp page
            **/
        	List<activityCodes> activityCodes = activitycodemanager.getActivityCodes(0);
            mav.addObject("activityCodes", activityCodes);        
            mav.addObject("survey", survey);
           
            /** add of pages drop down box **/
            List<SurveyPages> surveyPages =  surveymanager.getSurveyPages(survey.getId(), false);
            mav.addObject("surveyPages", surveyPages);	
            /** add drop down for other surveys for this program **/
            List<surveys> surveys = surveymanager.getProgramSurveys((Integer) session.getAttribute("selprogramId"));
            mav.addObject("surveys", surveys);	
            mav.addObject("surveyTitle", survey.getTitle());	            
            mav.addObject("surveyId", survey.getId());
            /* Get the list of answer types in the system */
        	List<AnswerTypes>  answerTypeList = surveymanager.getAnswerTypes();
            mav.addObject("answerTypeList", answerTypeList);  
            
            /** log user **/
            try {
            	Log_userSurveyActivity ua = new Log_userSurveyActivity();
            	ua.setActivityDesc("Accessed Survey Details");
            	ua.setController(controllerName);
            	ua.setPageAccessed("/details");
            	ua.setProgramId((Integer) session.getAttribute("selprogramId"));
            	ua.setSurveyId(survey.getId());
            	User userDetails = (User)session.getAttribute("userDetails");
            	ua.setSystemUserId(userDetails.getId());
                usermanager.insertUserLog (ua);
        	} catch (Exception ex1) {
        		ex1.printStackTrace();
        	}
            
            return mav;
        
    	} else {
        	mav.addObject("notValid", "Survey is not valid or you do not have permission.");
        	return mav;
    	}
  
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
        	Date date = new Date();
        	Timestamp timestamp = new Timestamp(date.getTime());
        	survey.setDateModified(timestamp);
        	surveymanager.updateSurvey(survey);
        } else {
        	 mav = new ModelAndView(new RedirectView("/programAdmin/details"));
             return mav;
        }
        
        redirectAttr.addFlashAttribute("msg", "surveyUpdated");
        if (action.equals("save")) {
            redirectAttr.addFlashAttribute("savedStatus", "updated");
            mav = new ModelAndView(new RedirectView("/programAdmin/surveys/details?s=" + survey.getId()));
            session.setAttribute("surveyId",survey.getId());
        } else {
        	mav = new ModelAndView(new RedirectView("/programAdmin/surveys/page"));
        }
        
        /** log user **/
        try {
        	Log_userSurveyActivity ua = new Log_userSurveyActivity();
        	ua.setActivityDesc("Saved survey change note");
        	ua.setController(controllerName);
        	ua.setPageAccessed("/details");
        	ua.setProgramId((Integer) session.getAttribute("selprogramId"));
        	ua.setSurveyId(survey.getId());
        	User userDetails = (User)session.getAttribute("userDetails");
        	ua.setSystemUserId(userDetails.getId());
            usermanager.insertUserLog (ua);
    	} catch (Exception ex1) {
    		ex1.printStackTrace();
    	}
        
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
        
        /** log user **/
        try {
        	Log_userSurveyActivity ua = new Log_userSurveyActivity();
        	ua.setActivityDesc("Saved survey change note");
        	ua.setController(controllerName);
        	ua.setPageAccessed("/saveNote");
        	ua.setProgramId((Integer) session.getAttribute("selprogramId"));
        	ua.setSurveyId(Integer.parseInt(request.getParameter("surveyId")));
        	ua.setSystemUserId(userDetails.getId());
            usermanager.insertUserLog (ua);
    	} catch (Exception ex1) {
    		ex1.printStackTrace();
    	}
        return mav;
    }
    
    /**
     * This method returns the modal with change logs info
     * @param session
     * @param s = surveyId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/changeLog", method = RequestMethod.GET)
    @ResponseBody public ModelAndView viewChangeLog(HttpSession session, @RequestParam String s) throws Exception {
        ModelAndView mav = new ModelAndView();
        
        surveys survey = checkSurveyPermission(session, s, "/changeLog" );
    	
        if(survey == null) {
        	mav.addObject("notValid", "Survey Id is not valid or you do not have permission to view this survey.");
        	return mav;
        } else {
        	/** now we get change logs **/
	        List <SurveyChangeLogs> getSurveyChangeLogs = surveymanager.getSurveyChangeLogs(survey.getId());
	        mav.addObject("changeLogs", getSurveyChangeLogs);
	        /** log user **/
	        try {
	        	Log_userSurveyActivity ua = new Log_userSurveyActivity();
	        	ua.setActivityDesc("Saved survey change log");
	        	ua.setController(controllerName);
	        	ua.setPageAccessed("/changeLog");
	        	ua.setProgramId((Integer) session.getAttribute("selprogramId"));
	        	ua.setSurveyId(survey.getId());
	        	User userDetails = (User)session.getAttribute("userDetails");
	        	ua.setSystemUserId(userDetails.getId());
	            usermanager.insertUserLog (ua);
	    	} catch (Exception ex1) {
	    		ex1.printStackTrace();
	    	}
	        
        }
        
        mav.setViewName("/programAdmin/surveys/changeLogs");
        return mav;
    }
    
    /**
     * This method returns the form to edit survey title
     * @param session
     * @param s = surveyId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "getSurveyForm.do", method = RequestMethod.GET)
    @ResponseBody public ModelAndView getSurveyTitleForm (
    		HttpSession session, @RequestParam String s) throws Exception {
        ModelAndView mav = new ModelAndView();
        
        surveys survey = checkSurveyPermission(session, s, "getSurveyForm.do" );
        if (survey == null) {
        	mav.addObject("notValid", "Survey Id is not valid or you do not have permission.");
        	return mav;
        }
        
        List<activityCodes> activityCodes = activitycodemanager.getActivityCodes(0);
        mav.addObject("activityCodes", activityCodes);
        mav.addObject("survey", survey);
        mav.setViewName("/programAdmin/surveys/surveyModal");
        /** log user **/
        try {
        	Log_userSurveyActivity ua = new Log_userSurveyActivity();
        	ua.setActivityDesc("Access Survey Title Form");
        	ua.setController(controllerName);
        	ua.setPageAccessed("getSurveyForm.do");
        	ua.setProgramId((Integer) session.getAttribute("selprogramId"));
        	ua.setSurveyId(survey.getId());
        	User userDetails = (User)session.getAttribute("userDetails");
        	ua.setSystemUserId(userDetails.getId());
            usermanager.insertUserLog (ua);
    	} catch (Exception ex1) {
    		ex1.printStackTrace();
    	}
        
        return mav;
    }
    
    /**
     * This method checks and saves new survey title
     * @param surveyNew
     * @param result
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "saveSurveyForm.do", method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView saveSurveyTitleForm(@Valid @ModelAttribute(value = "survey") surveys surveyNew, 
    		BindingResult result, HttpSession session) throws Exception {
    		
    	ModelAndView mav = new ModelAndView();
    	List<activityCodes> activityCodes = activitycodemanager.getActivityCodes(0);
        mav.addObject("activityCodes", activityCodes);
        mav.setViewName("/programAdmin/surveys/surveyModal");
        
        if (result.hasErrors()) {
            mav.addObject("survey", surveyNew);
            return mav;
        }

        //check permissions
        surveys survey = checkSurveyPermission (session, String.valueOf(surveyNew.getId()), "saveSurveyForm.do");
        if (survey == null) {
        	mav.addObject("survey", surveyNew);
            return mav;
        }
        		
        //check title	
        if (!survey.getTitle().trim().equalsIgnoreCase(surveyNew.getTitle().trim())) {
        	//we do not allow duplicate title
            List <surveys> existingTitle = surveymanager.getProgramSurveysByTitle(surveyNew);
    		if (existingTitle.size() != 0) {
    			mav.addObject("survey", surveyNew);
            	mav.addObject("activityCodes", activityCodes);
            	mav.addObject("existingTitle", "Title is in use by this program already.");
            	return mav;
            } 
        }
        
        
        // update 
        surveymanager.updateSurvey(surveyNew);
        mav.addObject("updated", "updated");
        
        /** log user **/
        try {
        	Log_userSurveyActivity ua = new Log_userSurveyActivity();
        	ua.setActivityDesc("Updated Survey Title");
        	ua.setController(controllerName);
        	ua.setPageAccessed("saveSurveyForm.do");
        	ua.setProgramId((Integer) session.getAttribute("selprogramId"));
        	ua.setSurveyId(survey.getId());
        	User userDetails = (User)session.getAttribute("userDetails");
        	ua.setSystemUserId(userDetails.getId());
            usermanager.insertUserLog (ua);
    	} catch (Exception ex1) {
    		ex1.printStackTrace();
    	}
        
         return mav;
    }
    
    
    
    
    
    /** shared methods **/
    
    /**
     * This method checks to see if the user has permission to the survey in question
     * It first checks to see if the survey Id is an integer, then it checks to see 
     * if it belongs to session's programId
     * @param session
     * @param s
     * @param pageName
     * @return
     */
    
    public surveys checkSurveyPermission (HttpSession session, String s, String pageName) {
    	
    	int surveyId = 0;
    	surveys survey = new surveys();
    	
    	/** we check to make sure s is a number **/
        try {
        	surveyId = Integer.parseInt(s);
        	Integer programId = (Integer) session.getAttribute("selprogramId");
            /** make sure session program id matches the survey's programId **/
            try {
            	survey = surveymanager.getSurveyById(surveyId);
            	if (survey != null && survey.getProgramId() != programId ) {
            		survey = null;
                	try {
        	        	Log_userSurveyActivity ua = new Log_userSurveyActivity();
        	        	ua.setActivityDesc("accessed denied");
        	        	ua.setController(controllerName);
        	        	ua.setPageAccessed(pageName);
        	        	ua.setSurveyId(surveyId);
        	        	User userDetails = (User)session.getAttribute("userDetails");
        	        	ua.setSystemUserId(userDetails.getId());
        	            usermanager.insertUserLog (ua);
                	} catch (Exception ex1) {
                		ex1.printStackTrace();
                	}
                	
    	        }
            } catch (Exception exSur) {
        		exSur.printStackTrace();
            }
        	
        	
        	
        } catch (Exception ex) {
        	//log here
        	try {
        		survey = null;
	        	Log_userSurveyActivity ua = new Log_userSurveyActivity();
	        	ua.setActivityDesc("accessed denied - survey is not numeric");
	        	ua.setController(controllerName);
	        	ua.setPageAccessed(pageName);
	        	User userDetails = (User)session.getAttribute("userDetails");
	        	ua.setSystemUserId(userDetails.getId());
	        	usermanager.insertUserLog (ua);
        	} catch (Exception ex1) {
        		ex1.printStackTrace();
        	}
        }	
        	
        return survey;
    	
    }

}
