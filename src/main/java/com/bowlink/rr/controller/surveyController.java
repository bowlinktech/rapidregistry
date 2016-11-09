/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.controller;

import com.bowlink.rr.model.AnswerTypes;
import com.bowlink.rr.model.SurveyChangeLogs;
import com.bowlink.rr.model.SurveyPages;
import com.bowlink.rr.model.SurveyQuestions;
import com.bowlink.rr.model.User;
import com.bowlink.rr.model.activityCodes;
import com.bowlink.rr.model.surveys;
import com.bowlink.rr.model.Log_userSurveyActivity;
import com.bowlink.rr.model.SurveyQuestionChoices;
import com.bowlink.rr.model.crosswalks;
import com.bowlink.rr.model.programAvailableTables;
import com.bowlink.rr.security.decryptObject;
import com.bowlink.rr.security.encryptObject;
import com.bowlink.rr.service.activityCodeManager;
import com.bowlink.rr.service.dataElementManager;
import com.bowlink.rr.service.programFormsManager;
import com.bowlink.rr.service.programManager;
import com.bowlink.rr.service.surveyManager;
import com.bowlink.rr.service.userManager;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
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
    dataElementManager dataelementmanager;

    @Autowired
    userManager usermanager;
    
    @Autowired
    programFormsManager programFormsManager;
    
    @Autowired
    programManager programmanager;

    private String controllerName = "survey";
    
    private String topSecret = "What goes up but never comes down";
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAutoGrowCollectionLimit(1000);
    }

    /**
     * The '' request will display the list of surveys.
     *
     * @param request
     * @param response
     * @return	the program admin survey view
     * @throws Exception
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView listSurveys(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/surveys");

        /* Get the list of programs in the system */
        List<surveys> surveys = surveymanager.getProgramSurveys((Integer) session.getAttribute("selprogramId"));
        
        encryptObject encrypt = new encryptObject();
        Map<String,String> map;
        for(surveys survey : surveys) {
            //Encrypt the use id to pass in the url
            map = new HashMap<String,String>();
            map.put("id",Integer.toString(survey.getId()));
            map.put("topSecret",topSecret);
            
            String[] encrypted = encrypt.encryptObject(map);
            
            survey.setEncryptedId(encrypted[0]);
            survey.setEncryptedSecret(encrypted[1]);
        }

        mav.addObject("surveys", surveys);

        /**
         * log user *
         */
        try {
            Log_userSurveyActivity ua = new Log_userSurveyActivity();
            ua.setActivityDesc("Accessed survey list");
            ua.setController(controllerName);
            ua.setPageAccessed("/surveys");
            ua.setProgramId((Integer) session.getAttribute("selprogramId"));
            User userDetails = (User) session.getAttribute("userDetails");
            ua.setSystemUserId(userDetails.getId());
            usermanager.insertUserLog(ua);
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
    public ModelAndView newSurvey(HttpServletRequest request,HttpServletResponse response, HttpSession session) throws Exception {
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/surveys/create");
        mav.addObject("create", "create");
        
        mav.addObject("survey", new surveys());
        
        /* Get the list of programs in the system */
        List<activityCodes> activityCodes = activitycodemanager.getActivityCodesByProgram((Integer) session.getAttribute("selprogramId"));
        mav.addObject("activityCodes", activityCodes);
       
        /**
         * log user *
         */
        try {
            Log_userSurveyActivity ua = new Log_userSurveyActivity();
            ua.setActivityDesc("Created Survey Form");
            ua.setController(controllerName);
            ua.setPageAccessed("/surveys");
            ua.setProgramId((Integer) session.getAttribute("selprogramId"));
            User userDetails = (User) session.getAttribute("userDetails");
            ua.setSystemUserId(userDetails.getId());
            usermanager.insertUserLog(ua);
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
    public ModelAndView createSurvey(@Valid @ModelAttribute(value = "survey") surveys survey, BindingResult result, @RequestParam String action, HttpSession session, RedirectAttributes redirectAttr) throws Exception {

        ModelAndView mav = new ModelAndView();

        if (result.hasErrors()) {
            List<activityCodes> activityCodes = activitycodemanager.getActivityCodes((Integer) session.getAttribute("selprogramId"), 0);
            mav.addObject("activityCodes", activityCodes);
            mav.addObject("create", "create");
            mav.setViewName("/surveys/create");
            return mav;
        }

        survey.setProgramId((Integer) session.getAttribute("selprogramId"));

        //we do not allow duplicate title
        List<surveys> existingTitle = surveymanager.getProgramSurveysByTitle(survey);
        if (!existingTitle.isEmpty()) {
            List<activityCodes> activityCodes = activitycodemanager.getActivityCodes((Integer) session.getAttribute("selprogramId"), 0);
            mav.addObject("activityCodes", activityCodes);
            mav.addObject("existingTitle", "The survey title is in use by this program already.");
            mav.addObject("create", "create");
            mav.setViewName("/surveys/create");
            return mav;
        }
        
        //check tag	
        if (!"".equals(survey.getSurveyTag())) {
            //we do not allow duplicate title
            List<surveys> existingTag = surveymanager.getProgramSurveysByTag(survey);
             if (!existingTag.isEmpty()) {
                List<activityCodes> activityCodes = activitycodemanager.getActivityCodes((Integer) session.getAttribute("selprogramId"), 0);
                mav.addObject("activityCodes", activityCodes);
                mav.addObject("existingsurveyTag", "Survey Tag is in use by this program already.");
                mav.addObject("create", "create");
                mav.setViewName("/surveys/create");
                return mav;
            }
        }

        //insert survey into db
        Integer surveyId = surveymanager.saveSurvey(survey);
        
        //we automatically add page 1
        SurveyPages sp = new SurveyPages();
        sp.setPageNum(1);
        sp.setPageTitle("Page One");
        sp.setSurveyId(surveyId);
        surveymanager.createSurveyPage(sp);
        redirectAttr.addFlashAttribute("msg", "created");

        /**
         * log user *
         */
        try {
            Log_userSurveyActivity ua = new Log_userSurveyActivity();
            ua.setActivityDesc("Created Survey");
            ua.setController(controllerName);
            ua.setPageAccessed("/create");
            ua.setProgramId((Integer) session.getAttribute("selprogramId"));
            ua.setSurveyId(surveyId);
            User userDetails = (User) session.getAttribute("userDetails");
            ua.setSystemUserId(userDetails.getId());
            usermanager.insertUserLog(ua);
        } catch (Exception ex1) {
            ex1.printStackTrace();
        }

        if (action.equals("save")) {
            
            Map<String,String> map = new HashMap<String,String>();
            map.put("id",Integer.toString(surveyId));
            map.put("topSecret",topSecret);

            encryptObject encrypt = new encryptObject();

            String[] encrypted = encrypt.encryptObject(map);
            
            redirectAttr.addFlashAttribute("savedStatus", "updated");
            mav = new ModelAndView(new RedirectView("/programAdmin/surveys/details?s=" + encrypted[0]+"&v="+encrypted[1]));
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
     * @throws Exception This page should contain all editable parts of a survey, all parts should display a modal when clicked Permission will be tracked
     */
    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public ModelAndView viewSurveyDetails(@RequestParam String s, @RequestParam String v, HttpSession session, RedirectAttributes redirectAttr, Authentication authentication) throws Exception {
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/surveys/details");
        mav.addObject("edit", "edit");
        
        /* Decrypt the url */
        decryptObject decrypt = new decryptObject();
        
        Object obj = decrypt.decryptObject(s, v);
        
        String[] result = obj.toString().split((","));
        
        int surveyId = Integer.parseInt(result[0].substring(4));
        
        //s - survey Id       
        surveys survey = checkSurveyPermission(session, surveyId, "/details");

        if (survey != null) {
            /**
             * we look up survey and send back survey object we look for survey --> pages --> questions --> answers we loop and display in jsp page
            *
             */
            List<activityCodes> activityCodes = activitycodemanager.getActivityCodesByProgram((Integer) session.getAttribute("selprogramId"));
            mav.addObject("activityCodes", activityCodes);
            mav.addObject("survey", survey);

            
            /* Get the list of answer types in the system */
            List<AnswerTypes> answerTypeList = surveymanager.getAnswerTypes();
            mav.addObject("answerTypeList", answerTypeList);
            
            mav.addObject("encryptedId", s);
            mav.addObject("encryptedSecret", v);

            /**
             * log user *
             */
            try {
                Log_userSurveyActivity ua = new Log_userSurveyActivity();
                ua.setActivityDesc("Accessed Survey Details");
                ua.setController(controllerName);
                ua.setPageAccessed("/details");
                ua.setProgramId((Integer) session.getAttribute("selprogramId"));
                ua.setSurveyId(survey.getId());
                User userDetails = (User) session.getAttribute("userDetails");
                ua.setSystemUserId(userDetails.getId());
                usermanager.insertUserLog(ua);
            } catch (Exception ex1) {
                ex1.printStackTrace();
            }

            return mav;

        } else {
            mav.addObject("notValid", "Survey is not valid or you do not have permission.");
            return mav;
        }

    }
    
    
    /**
     * The '/details' POST method will submit the changes to the selected survey.
     * 
     * @param survey    The object holding the survey information
     * @param result
     * @param action
     * @param session
     * @param redirectAttr
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/details", method = RequestMethod.POST)
    public ModelAndView editSurvey(@Valid @ModelAttribute(value = "survey") surveys survey,BindingResult result,@RequestParam String action,HttpSession session, RedirectAttributes redirectAttr) throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.addObject("edit", "edit");

        if (result.hasErrors()) {
            List<activityCodes> activityCodes = activitycodemanager.getActivityCodesByProgram((Integer) session.getAttribute("selprogramId"));
            mav.addObject("activityCodes", activityCodes);
            mav.setViewName("/surveys/details");
            return mav;
        }

        Integer programId = (Integer) session.getAttribute("selprogramId");
        survey.setProgramId(programId);
        //update survey in db
        if (survey.getProgramId() == (Integer) session.getAttribute("selprogramId")) {
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
            session.setAttribute("surveyId", survey.getId());
        } else {
            mav = new ModelAndView(new RedirectView("/programAdmin/surveys/page"));
        }

        /**
         * log user *
         */
        try {
            Log_userSurveyActivity ua = new Log_userSurveyActivity();
            ua.setActivityDesc("Saved survey change note");
            ua.setController(controllerName);
            ua.setPageAccessed("/details");
            ua.setProgramId((Integer) session.getAttribute("selprogramId"));
            ua.setSurveyId(survey.getId());
            User userDetails = (User) session.getAttribute("userDetails");
            ua.setSystemUserId(userDetails.getId());
            usermanager.insertUserLog(ua);
        } catch (Exception ex1) {
            ex1.printStackTrace();
        }

        return mav;

    }
    
    
    /**
     * The 'savePageTitle.do' POST request will submit the survey page title chanage.
     * 
     * @param pageId    The id of the current page.
     * @param pageTitle The new page title
     * @param session
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "savePageTitle.do", method = RequestMethod.POST)
    public @ResponseBody String savePageTitle(@RequestParam(value = "pageId", required = true) Integer pageId, @RequestParam(value = "pageTitle", required = true) String pageTitle, HttpSession session) throws Exception {

        //get page info
        SurveyPages surveyPage = surveymanager.getSurveyPageById(pageId);
        surveyPage.setPageTitle(pageTitle);

        // update 
        surveymanager.updateSurveyPage(surveyPage);

        /**
         * log user *
         */
        try {
            Log_userSurveyActivity ua = new Log_userSurveyActivity();
            ua.setActivityDesc("Updated Page Title");
            ua.setController(controllerName);
            ua.setPageAccessed("savePageTitleForm.do");
            ua.setProgramId((Integer) session.getAttribute("selprogramId"));
            ua.setSurveyId(surveyPage.getSurveyId());
            ua.setPageId(surveyPage.getId());
            User userDetails = (User) session.getAttribute("userDetails");
            ua.setSystemUserId(userDetails.getId());
            usermanager.insertUserLog(ua);
        } catch (Exception ex1) {
            ex1.printStackTrace();
        }

        return pageTitle;
    }
    
    /**
     * The 'savePageSkipLogic.do' POST request will submit the survey page skip to chanage.
     * 
     * @param pageId    The id of the current page.
     * @param skipToPageId The page id to skip to
     * @param session
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "savePageSkipLogic.do", method = RequestMethod.POST)
    public @ResponseBody boolean savePageSkipLogic(@RequestParam(value = "pageId", required = true) Integer pageId, @RequestParam(value = "skipToPageId", required = true) Integer skipToPageId, HttpSession session) throws Exception {

        //get page info
        SurveyPages surveyPage = surveymanager.getSurveyPageById(pageId);
        surveyPage.setSkipToPage(skipToPageId);

        // update 
        surveymanager.updateSurveyPage(surveyPage);

        /**
         * log user *
         */
        try {
            Log_userSurveyActivity ua = new Log_userSurveyActivity();
            ua.setActivityDesc("Updated Page Skip logic");
            ua.setController(controllerName);
            ua.setPageAccessed("savePageTitleForm.do");
            ua.setProgramId((Integer) session.getAttribute("selprogramId"));
            ua.setSurveyId(surveyPage.getSurveyId());
            ua.setPageId(surveyPage.getId());
            User userDetails = (User) session.getAttribute("userDetails");
            ua.setSystemUserId(userDetails.getId());
            usermanager.insertUserLog(ua);
        } catch (Exception ex1) {
            ex1.printStackTrace();
        }

        return true;
    }
    
    
    /**
     * The 'getSurveyPages.do' GET request will return the list of pages associated with the selected survey.
     * 
     * @param s
     * @param v
     * @param session
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/getSurveyPages.do", method = RequestMethod.GET)
    public ModelAndView getSurveyPages(@RequestParam String s, @RequestParam String v, HttpSession session) throws Exception {
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/programAdmin/surveys/surveyPages");
        
        /* Decrypt the url */
        decryptObject decrypt = new decryptObject();
        
        Object obj = decrypt.decryptObject(s, v);
        
        String[] result = obj.toString().split((","));
        
        int surveyId = Integer.parseInt(result[0].substring(4));
        
        /**
        * add of pages drop down box *
        */
        List<SurveyPages> surveyPages = surveymanager.getSurveyPages(surveyId, false);
        
        /* Get page questions */
        for(SurveyPages page : surveyPages) {
            
            List<SurveyQuestions> questions = surveymanager.getSurveyQuestions(page.getId());
            
            if(questions != null && !questions.isEmpty()) {
                
                /* Get question choices */
                for(SurveyQuestions question : questions) {
                    if(question.getAnswerTypeId() == 1) {
                        List<SurveyQuestionChoices> questionChoices = surveymanager.getQuestionChoices(question.getId());
                        question.setquestionChoices(questionChoices);
                    }
                }
                
                page.setSurveyQuestions(questions);
            }
        }
        
        mav.addObject("surveyPages", surveyPages);
        
        return mav;
        
    }
    
    /**
     * The 'addNewSurveyPage.do' POST request will generate a new page for the passed in survey.
     * 
     * @param s The encrypted survey Id
     * @param v The decryption key
     * @param lastPageId    The id of the last page in the survey.
     * @param session
     * @throws Exception 
     */
    @RequestMapping(value = "addNewSurveyPage.do", method = RequestMethod.POST)
    public @ResponseBody String addNewSurveyPage(@RequestParam String s, @RequestParam String v, @RequestParam Integer lastPageId, HttpSession session) throws Exception {
       
        decryptObject decrypt = new decryptObject();
        
        Object obj = decrypt.decryptObject(s, v);
        
        String[] result = obj.toString().split((","));
        
        int surveyId = Integer.parseInt(result[0].substring(4));
        
        SurveyPages currentPage = surveymanager.getSurveyPageById(lastPageId);
        
        SurveyPages newPage = new SurveyPages();
        newPage.setSurveyId(surveyId);
        newPage.setPageTitle("New Page");
        
        Integer nextPageNum = currentPage.getPageNum()+1;
        newPage.setPageNum(nextPageNum);
        
        /* Get a list of pages that have a page number greater than the new page added */
        List<SurveyPages> pages = surveymanager.getSurveyPagesByPageNum(surveyId, nextPageNum);
        
        if(pages.size() > 0) {
            for(SurveyPages page : pages) {
                Integer newPageNum = page.getPageNum() + 1;
                page.setPageNum(newPageNum);
                surveymanager.updateSurveyPage(page);
            }
        }
        
        Integer pageId = surveymanager.createSurveyPage(newPage);
        
        return "Page Added";
        
    }
    
    /**
     * The 'addNewPageQuestion.do' GET request will return the list of pages associated with the selected survey.
     * 
     * @param s
     * @param v
     * @param session
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "addNewPageQuestion.do", method = RequestMethod.GET)
    public @ResponseBody ModelAndView addNewPageQuestion(@RequestParam String s, @RequestParam String v, @RequestParam Integer pageId, @RequestParam Integer questionType, HttpSession session) throws Exception {
        
        ModelAndView mav = new ModelAndView();
        
        /** Multiple Choice **/
        if(questionType == 1) {
            mav.setViewName("/programAdmin/surveys/questionTypes/multipleChoice");
            
            /** Get a list of available tables to auto-populate from **/
            List<programAvailableTables> availableTables = programmanager.getAvailableTablesForSurveys((Integer) session.getAttribute("selprogramId"));
            mav.addObject("availableTables", availableTables);
            
            /** Get a list of available crosswalks to auto-populate from **/
            List<crosswalks> availableCW = dataelementmanager.getCrosswalks(0, 0, (Integer) session.getAttribute("selprogramId"));
            mav.addObject("availableCW", availableCW);
        }
        
        /** Drop down (select box) **/
        else if(questionType == 2) {
            mav.setViewName("/programAdmin/surveys/questionTypes/dropDown");
            
            /** Get a list of available tables to auto-populate from **/
            List<programAvailableTables> availableTables = programmanager.getAvailableTablesForSurveys((Integer) session.getAttribute("selprogramId"));
            mav.addObject("availableTables", availableTables);
            
            /** Get a list of available crosswalks to auto-populate from **/
            List<crosswalks> availableCW = dataelementmanager.getCrosswalks(0, 0, (Integer) session.getAttribute("selprogramId"));
            mav.addObject("availableCW", availableCW);
        }
        
        /** Single text box **/
        else if(questionType == 3) {
            mav.setViewName("/programAdmin/surveys/questionTypes/singleTextBox");
        }
        
        /** Multiple text box **/
        else if(questionType == 4) {
            mav.setViewName("/programAdmin/surveys/questionTypes/multiTextbox");
             
            /** Get a list of available tables to auto-populate from **/
            List<programAvailableTables> availableTables = programmanager.getAvailableTablesForSurveys((Integer) session.getAttribute("selprogramId"));
            mav.addObject("availableTables", availableTables);
        }
        
        /** Comment box **/
        else if(questionType == 5) {
            mav.setViewName("/programAdmin/surveys/questionTypes/commentBox");
        }
        
        /** Date/Time **/
        else if(questionType == 6) {
            mav.setViewName("/programAdmin/surveys/questionTypes/datetime");
        }
        
        /** Display Text **/
        else if(questionType == 7) {
            mav.setViewName("/programAdmin/surveys/questionTypes/displayText");
        }
      
        /* Decrypt the url */
        decryptObject decrypt = new decryptObject();
        
        Object obj = decrypt.decryptObject(s, v);
        
        String[] result = obj.toString().split((","));
        
        int surveyId = Integer.parseInt(result[0].substring(4));
        
        List<SurveyQuestions> surveypagequestions = surveymanager.getSurveyQuestions(pageId);
        
        Integer qnum = 1;
        if(surveypagequestions != null && !surveypagequestions.isEmpty()) {
            qnum = surveypagequestions.get(surveypagequestions.size()-1).getQuestionNum()+1;
        }
        else {
            List<SurveyQuestions> surveyquestions = surveymanager.getAllSurveyQuestions(surveyId);
            
            if(surveyquestions != null && !surveyquestions.isEmpty()) {
                qnum = surveyquestions.get(surveyquestions.size()-1).getQuestionNum()+1;
            }
            else {
                qnum = 1;
            }
        }
        
        /* Get the page details */
        SurveyPages pageDetails = surveymanager.getSurveyPageById(pageId);
        
        /* Create a new empty question */
        SurveyQuestions surveyQuestion = new SurveyQuestions();
        surveyQuestion.setSurveyId(surveyId);
        surveyQuestion.setAnswerTypeId(questionType);
        surveyQuestion.setSurveyPageId(pageId);
        surveyQuestion.setQuestionNum(qnum);
        
        if(pageDetails != null) {
            surveyQuestion.setPageNum(pageDetails.getPageNum());
        }
        else {
            surveyQuestion.setPageNum(1);
        }
        
        /* Create 3 blank answers */
        if(questionType == 1 || questionType == 2) {
            List<SurveyQuestionChoices> questionChoices = new CopyOnWriteArrayList<>();
            
            for(int i = 1; i <= 3; i++) {
                SurveyQuestionChoices questionChoice = new SurveyQuestionChoices();
                questionChoices.add(questionChoice);
            }
            surveyQuestion.setquestionChoices(questionChoices);
        }
        
        mav.addObject("surveyQuestion", surveyQuestion);
       
        mav.addObject("s",s);
        mav.addObject("v",v);
        mav.addObject("qnum", qnum);
        
        /* Return a list of validation types */
        List validationTypes = dataelementmanager.getValidationTypes();
        mav.addObject("validationTypes", validationTypes);
        
        /* Return a list of available fields */
        List fields = programFormsManager.getFieldsForProgram((Integer) session.getAttribute("selprogramId"));
        mav.addObject("fields", fields);
        
        /* Return a list of pages */
        List<SurveyPages> pages = surveymanager.getSurveyPages(surveyId, false);
        mav.addObject("pages", pages);
        
        /* Get the list of programs in the system */
        List<activityCodes> activityCodes = activitycodemanager.getActivityCodesByProgram((Integer) session.getAttribute("selprogramId"));
        mav.addObject("activityCodes", activityCodes);
       
        return mav;
        
    }
    
    /**
     * The 'editPageQuestion.do' GET request will return the question edit page.
     * 
     * @param s
     * @param v
     * @param session
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "editPageQuestion.do", method = RequestMethod.GET)
    public @ResponseBody ModelAndView editPageQuestion(@RequestParam String s, @RequestParam String v, @RequestParam Integer pageId, @RequestParam Integer questionId, HttpSession session) throws Exception {
        
        ModelAndView mav = new ModelAndView();
        
        SurveyQuestions questionDetails = surveymanager.getSurveyQuestionById(questionId);
        mav.addObject("qnum", questionDetails.getQuestionNum());
        
        /* Get question choices */
        if(questionDetails.getAnswerTypeId() == 1 || questionDetails.getAnswerTypeId() == 2) {
            List<SurveyQuestionChoices> questionChoices = surveymanager.getQuestionChoices(questionId);
            questionDetails.setquestionChoices(questionChoices);
        }
       
        /* Get the page details */
        SurveyPages pageDetails = surveymanager.getSurveyPageById(questionDetails.getSurveyPageId());
        questionDetails.setPageNum(pageDetails.getPageNum());
        
        mav.addObject("surveyQuestion", questionDetails);
        
        /** Multiple Choice **/
        if(questionDetails.getAnswerTypeId() == 1) {
            mav.setViewName("/programAdmin/surveys/questionTypes/multipleChoice");
            
            /** Get a list of available tables to auto-populate from **/
            List<programAvailableTables> availableTables = programmanager.getAvailableTablesForSurveys((Integer) session.getAttribute("selprogramId"));
            mav.addObject("availableTables", availableTables);
            
            /** Get a list of available crosswalks to auto-populate from **/
            List<crosswalks> availableCW = dataelementmanager.getCrosswalks(0, 0, (Integer) session.getAttribute("selprogramId"));
            mav.addObject("availableCW", availableCW);
        }
        
        /** Drop down (select box) **/
        else if(questionDetails.getAnswerTypeId() == 2) {
            mav.setViewName("/programAdmin/surveys/questionTypes/dropDown");
            
            /** Get a list of available tables to auto-populate from **/
            List<programAvailableTables> availableTables = programmanager.getAvailableTablesForSurveys((Integer) session.getAttribute("selprogramId"));
            mav.addObject("availableTables", availableTables);
            
            /** Get a list of available crosswalks to auto-populate from **/
            List<crosswalks> availableCW = dataelementmanager.getCrosswalks(0, 0, (Integer) session.getAttribute("selprogramId"));
            mav.addObject("availableCW", availableCW);
        }
        
        /** Single text box **/
        else if(questionDetails.getAnswerTypeId() == 3) {
            mav.setViewName("/programAdmin/surveys/questionTypes/singleTextBox");
        }
        
        /** Multiple text box **/
        else if(questionDetails.getAnswerTypeId() == 4) {
            mav.setViewName("/programAdmin/surveys/questionTypes/multiTextbox");
             
            /** Get a list of available tables to auto-populate from **/
            List<programAvailableTables> availableTables = programmanager.getAvailableTablesForSurveys((Integer) session.getAttribute("selprogramId"));
            mav.addObject("availableTables", availableTables);
        }
        
        /** Comment box **/
        else if(questionDetails.getAnswerTypeId() == 5) {
            mav.setViewName("/programAdmin/surveys/questionTypes/commentBox");
        }
        
        /** Date/Time **/
        else if(questionDetails.getAnswerTypeId() == 6) {
            mav.setViewName("/programAdmin/surveys/questionTypes/datetime");
        }
        
        /** Display Text **/
        else if(questionDetails.getAnswerTypeId() == 7) {
            mav.setViewName("/programAdmin/surveys/questionTypes/displayText");
        }
        
        
        /* Decrypt the url */
        decryptObject decrypt = new decryptObject();
        
        Object obj = decrypt.decryptObject(s, v);
        
        String[] result = obj.toString().split((","));
        
        int surveyId = Integer.parseInt(result[0].substring(4));
        
        mav.addObject("s",s);
        mav.addObject("v",v);
        
        //Return a list of validation types
        List validationTypes = dataelementmanager.getValidationTypes();
        mav.addObject("validationTypes", validationTypes);
        
        /* Return a list of available fields */
        List fields = programFormsManager.getFieldsForProgram((Integer) session.getAttribute("selprogramId"));
        mav.addObject("fields", fields);
        
        /* Return a list of pages */
        List<SurveyPages> pages = surveymanager.getSurveyPages(surveyId, false);
        mav.addObject("pages", pages);
        
        /* Get the list of programs in the system */
        List<activityCodes> activityCodes = activitycodemanager.getActivityCodesByProgram((Integer) session.getAttribute("selprogramId"));
        mav.addObject("activityCodes", activityCodes);
        
        return mav;
        
    }
    
    
    /**
     * The 'submitSurveyQuestion' POST request will submit the changes to the selected question.
     * 
     * @param surveyQuestion    The question object
     * @param session
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "submitSurveyQuestion", method = RequestMethod.POST)
    public @ResponseBody Integer submitSurveyQuestion(
            @ModelAttribute(value = "surveyQuestion") SurveyQuestions surveyQuestion,
            HttpSession session
    ) throws Exception {
        
        Integer questionId = 0;
        if(surveyQuestion.getId() > 0) {
            questionId = surveyQuestion.getId();
            
            try {
                int cwId = Integer.parseInt(surveyQuestion.getPopulateFromTable());
                surveyQuestion.setPopulateFromCW(cwId);
                surveyQuestion.setPopulateFromTable("");
                
            } catch(NumberFormatException e) {
                surveyQuestion.setPopulateFromCW(0);
            }
            
            surveymanager.saveSurveyQuestion(surveyQuestion);
            
            /** Log the change **/
            SurveyChangeLogs scl = new SurveyChangeLogs();

            scl.setNotes("Question number " + surveyQuestion.getQuestionNum() + " was updated.");
            scl.setSurveyId(surveyQuestion.getSurveyId());
            User userDetails = (User) session.getAttribute("userDetails");
            scl.setSystemUserId(userDetails.getId());
            surveymanager.saveChangeLogs(scl);
        }
        else {
            
           /* Update all other survey page numbers */
           List<SurveyQuestions> surveyquestions = surveymanager.getAllSurveyQuestions(surveyQuestion.getSurveyId());
            
           for(SurveyQuestions question : surveyquestions) {
               if(question.getQuestionNum() >= surveyQuestion.getQuestionNum()) {
                   question.setQuestionNum(question.getQuestionNum()+1);
                   surveymanager.saveSurveyQuestion(question);
               }
           } 
            
           questionId = surveymanager.saveNewSurveyQuestion(surveyQuestion); 
           
           /** Log the change **/
           SurveyChangeLogs scl = new SurveyChangeLogs();

           scl.setNotes("Question number " + surveyQuestion.getQuestionNum() + " was added.");
           scl.setSurveyId(surveyQuestion.getSurveyId());
           User userDetails = (User) session.getAttribute("userDetails");
           scl.setSystemUserId(userDetails.getId());
           surveymanager.saveChangeLogs(scl);
        }
        
        /* If question type is mutliple choice or dropdown then insert the question choices */
        if(surveyQuestion.getAnswerTypeId() == 1 || surveyQuestion.getAnswerTypeId() == 2) {
            
            /* Delete existing choices */
            surveymanager.removeQuestionChoices(questionId);
            
            if(surveyQuestion.getquestionChoices() != null) {
                
                for(SurveyQuestionChoices questionChoice : surveyQuestion.getquestionChoices()) {
                    if(questionChoice.getChoiceText() != null && !"".equals(questionChoice.getChoiceText())) {
                     
                        questionChoice.setQuestionId(questionId);

                        surveymanager.saveQuestionChoice(questionChoice);
                    }
                }
                
            }
        }
       
        
        return questionId;
    }
    
    
    /**
     * The 'getQuestionChoicesForSelTable.do' GET request will populate the question options with the value of the selected table.
     * 
     * @param s The encrypted id of the selected survey
     * @param v The encrypted encryption key for
     * @param pageId    The id of the current page
     * @param questionId    The id of the selected question
     * @param tableName The selected table
     * @param session
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "getQuestionChoicesForSelTable.do", method = RequestMethod.GET)
    public @ResponseBody ModelAndView getQuestionChoicesForSelTable(
            @RequestParam String s, @RequestParam String v, @RequestParam Integer pageId, 
            @RequestParam Integer questionId, @RequestParam String tableName, @RequestParam Integer cwId,
            HttpSession session) throws Exception {
        
        ModelAndView mav = new ModelAndView();
        
        SurveyQuestions questionDetails = surveymanager.getSurveyQuestionById(questionId);
        List<SurveyQuestionChoices> currentquestionChoices = surveymanager.getQuestionChoices(questionId);
        mav.addObject("qnum", questionDetails.getQuestionNum());
        
        if(!"".equals(tableName) && !questionDetails.getPopulateFromTable().equals(tableName)) {
            
            /* Delete current options */
            surveymanager.removeQuestionChoices(questionId);
            
            questionDetails.setPopulateFromTable(tableName);
            questionDetails.setPopulateFromCW(0);
            surveymanager.saveSurveyQuestion(questionDetails);
            
            /** Need to get all available rows for the selected table **/
            List tableValues = dataelementmanager.getLookupTableValues(tableName, (Integer) session.getAttribute("selprogramId"));
            
            /* Create 3 blank answers */
            if(tableValues != null && !tableValues.isEmpty()) {
                List<SurveyQuestionChoices> questionChoices = new CopyOnWriteArrayList<>();
                
                for (ListIterator iter = tableValues.listIterator(); iter.hasNext();) {
                    
                    SurveyQuestionChoices questionChoice = new SurveyQuestionChoices();
            
                    Object[] row = (Object[]) iter.next();
                    
                    questionChoice.setChoiceText(String.valueOf(row[1]));
                    questionChoice.setChoiceValue(Integer.parseInt(String.valueOf(row[0])));
                    questionChoice.setQuestionId(questionId);
                    
                    questionChoices.add(questionChoice);
                }

                questionDetails.setquestionChoices(questionChoices);
            }
        }
        else if(cwId > 0 && !questionDetails.getPopulateFromCW().equals(cwId)) {
            
            /* Delete current options */
            surveymanager.removeQuestionChoices(questionId);
            
            questionDetails.setPopulateFromTable("");
            questionDetails.setPopulateFromCW(cwId);
            surveymanager.saveSurveyQuestion(questionDetails);
            
            /** Need to get all available rows for the selected table **/
            List cwValues = dataelementmanager.getCrosswalkData(cwId);
            
            /* Create 3 blank answers */
            if(cwValues != null && !cwValues.isEmpty()) {
                List<SurveyQuestionChoices> questionChoices = new CopyOnWriteArrayList<>();
                
                for (ListIterator iter = cwValues.listIterator(); iter.hasNext();) {
                    
                    SurveyQuestionChoices questionChoice = new SurveyQuestionChoices();
            
                    Object[] row = (Object[]) iter.next();
                    
                    questionChoice.setChoiceText(String.valueOf(row[0]));
                    questionChoice.setChoiceValue(Integer.parseInt(String.valueOf(row[3])));
                    questionChoice.setQuestionId(questionId);
                    
                    questionChoices.add(questionChoice);
                }

                questionDetails.setquestionChoices(questionChoices);
            }
        }
        else if(currentquestionChoices != null && !currentquestionChoices.isEmpty()) {
            
            questionDetails.setquestionChoices(currentquestionChoices);
        }
       
        mav.addObject("surveyQuestion", questionDetails);
        
        /** Multiple Choice **/
        if(questionDetails.getAnswerTypeId() == 1) {
            mav.setViewName("/programAdmin/surveys/questionTypes/multipleChoice");
            
            /** Get a list of available tables to auto-populate from **/
            List<programAvailableTables> availableTables = programmanager.getAvailableTablesForSurveys((Integer) session.getAttribute("selprogramId"));
            mav.addObject("availableTables", availableTables);
            
            /** Get a list of available crosswalks to auto-populate from **/
            List<crosswalks> availableCW = dataelementmanager.getCrosswalks(0, 0, (Integer) session.getAttribute("selprogramId"));
            mav.addObject("availableCW", availableCW);
        }
        
        /** Drop down (select box) **/
        else if(questionDetails.getAnswerTypeId() == 2) {
            mav.setViewName("/programAdmin/surveys/questionTypes/dropDown");
            
            /** Get a list of available tables to auto-populate from **/
            List<programAvailableTables> availableTables = programmanager.getAvailableTablesForSurveys((Integer) session.getAttribute("selprogramId"));
            mav.addObject("availableTables", availableTables);
            
            /** Get a list of available crosswalks to auto-populate from **/
            List<crosswalks> availableCW = dataelementmanager.getCrosswalks(0, 0, (Integer) session.getAttribute("selprogramId"));
            mav.addObject("availableCW", availableCW);
        }
        
        /** Single text box **/
        else if(questionDetails.getAnswerTypeId() == 3) {
            mav.setViewName("/programAdmin/surveys/questionTypes/singleTextBox");
        }
        
        /** Multiple text box **/
        else if(questionDetails.getAnswerTypeId() == 4) {
            mav.setViewName("/programAdmin/surveys/questionTypes/multipleTextBox");
        }
        
        /** Comment box **/
        else if(questionDetails.getAnswerTypeId() == 5) {
            mav.setViewName("/programAdmin/surveys/questionTypes/commentBox");
        }
        
        /** Date/Time **/
        else if(questionDetails.getAnswerTypeId() == 6) {
            mav.setViewName("/programAdmin/surveys/questionTypes/datetime");
        }
        
        /** Display Text **/
        else if(questionDetails.getAnswerTypeId() == 7) {
            mav.setViewName("/programAdmin/surveys/questionTypes/displayText");
        }
        
        /* Decrypt the url */
        decryptObject decrypt = new decryptObject();
        
        Object obj = decrypt.decryptObject(s, v);
        
        String[] result = obj.toString().split((","));
        
        int surveyId = Integer.parseInt(result[0].substring(4));
        
        mav.addObject("s",s);
        mav.addObject("v",v);
        
        //Return a list of validation types
        List validationTypes = dataelementmanager.getValidationTypes();
        mav.addObject("validationTypes", validationTypes);
        
        /* Return a list of available fields */
        List fields = programFormsManager.getFieldsForProgram((Integer) session.getAttribute("selprogramId"));
        mav.addObject("fields", fields);
        
        /* Return a list of pages */
        List<SurveyPages> pages = surveymanager.getSurveyPages(surveyId, false);
        mav.addObject("pages", pages);
        
        /* Get the list of programs in the system */
        List<activityCodes> activityCodes = activitycodemanager.getActivityCodesByProgram((Integer) session.getAttribute("selprogramId"));
        mav.addObject("activityCodes", activityCodes);
        
        return mav;
        
    }
    
    /**
     * The 'getQuestionManualChoices.do' GET request will populate the question options with the value of the selected table.
     * 
     * @param s The encrypted id of the selected survey
     * @param v The encrypted encryption key for
     * @param pageId    The id of the current page
     * @param questionId    The id of the selected question
     * @param tableName The selected table
     * @param session
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "getQuestionManualChoices.do", method = RequestMethod.GET)
    public @ResponseBody ModelAndView getQuestionManualChoices(@RequestParam String s, @RequestParam String v, @RequestParam Integer pageId, @RequestParam Integer questionId, HttpSession session) throws Exception {
        
        ModelAndView mav = new ModelAndView();
        
        SurveyQuestions questionDetails = surveymanager.getSurveyQuestionById(questionId);
        
        if(questionDetails.getPopulateFromTable() != null && !"".equals(questionDetails.getPopulateFromTable())) { 
            /* Delete current options */
            surveymanager.removeQuestionChoices(questionId);
        }       
        
        questionDetails.setPopulateFromTable("");
        questionDetails.setPopulateFromCW(0);
        surveymanager.saveSurveyQuestion(questionDetails);
        
        List<SurveyQuestionChoices> currentquestionChoices = surveymanager.getQuestionChoices(questionId);
        mav.addObject("qnum", questionDetails.getQuestionNum());
        
        if(currentquestionChoices == null || currentquestionChoices.isEmpty()) {
           /* Create 3 blank answers */
            List<SurveyQuestionChoices> questionChoices = new CopyOnWriteArrayList<>();

            for(int i = 1; i <= 3; i++) {
                SurveyQuestionChoices questionChoice = new SurveyQuestionChoices();
                questionChoices.add(questionChoice);
            }
            questionDetails.setquestionChoices(questionChoices);
        }
        else if(currentquestionChoices != null && !currentquestionChoices.isEmpty()) {
            
            questionDetails.setquestionChoices(currentquestionChoices);
        }
       
        mav.addObject("surveyQuestion", questionDetails);
        
        /** Multiple Choice **/
        if(questionDetails.getAnswerTypeId() == 1) {
            mav.setViewName("/programAdmin/surveys/questionTypes/multipleChoice");
            
            /** Get a list of available tables to auto-populate from **/
            List<programAvailableTables> availableTables = programmanager.getAvailableTablesForSurveys((Integer) session.getAttribute("selprogramId"));
            mav.addObject("availableTables", availableTables);
            
            /** Get a list of available crosswalks to auto-populate from **/
            List<crosswalks> availableCW = dataelementmanager.getCrosswalks(0, 0, (Integer) session.getAttribute("selprogramId"));
            mav.addObject("availableCW", availableCW);
        }
        
        /** Drop down (select box) **/
        else if(questionDetails.getAnswerTypeId() == 2) {
            mav.setViewName("/programAdmin/surveys/questionTypes/dropDown");
            
            /** Get a list of available tables to auto-populate from **/
            List<programAvailableTables> availableTables = programmanager.getAvailableTablesForSurveys((Integer) session.getAttribute("selprogramId"));
            mav.addObject("availableTables", availableTables);
            
            /** Get a list of available crosswalks to auto-populate from **/
            List<crosswalks> availableCW = dataelementmanager.getCrosswalks(0, 0, (Integer) session.getAttribute("selprogramId"));
            mav.addObject("availableCW", availableCW);
        }
        
        /** Single text box **/
        else if(questionDetails.getAnswerTypeId() == 3) {
            mav.setViewName("/programAdmin/surveys/questionTypes/singleTextBox");
        }
        
        /** Multiple text box **/
        else if(questionDetails.getAnswerTypeId() == 4) {
            mav.setViewName("/programAdmin/surveys/questionTypes/multipleTextBox");
        }
        
        /** Comment box **/
        else if(questionDetails.getAnswerTypeId() == 5) {
            mav.setViewName("/programAdmin/surveys/questionTypes/commentBox");
        }
        
        /** Date/Time **/
        else if(questionDetails.getAnswerTypeId() == 6) {
            mav.setViewName("/programAdmin/surveys/questionTypes/datetime");
        }
        
        /** Display Text **/
        else if(questionDetails.getAnswerTypeId() == 7) {
            mav.setViewName("/programAdmin/surveys/questionTypes/displayText");
        }
        
        /* Decrypt the url */
        decryptObject decrypt = new decryptObject();
        
        Object obj = decrypt.decryptObject(s, v);
        
        String[] result = obj.toString().split((","));
        
        int surveyId = Integer.parseInt(result[0].substring(4));
        
        mav.addObject("s",s);
        mav.addObject("v",v);
        
        //Return a list of validation types
        List validationTypes = dataelementmanager.getValidationTypes();
        mav.addObject("validationTypes", validationTypes);
        
        /* Return a list of available fields */
        List fields = programFormsManager.getFieldsForProgram((Integer) session.getAttribute("selprogramId"));
        mav.addObject("fields", fields);
        
        /* Return a list of pages */
        List<SurveyPages> pages = surveymanager.getSurveyPages(surveyId, false);
        mav.addObject("pages", pages);
        
        /* Get the list of programs in the system */
        List<activityCodes> activityCodes = activitycodemanager.getActivityCodesByProgram((Integer) session.getAttribute("selprogramId"));
        mav.addObject("activityCodes", activityCodes);
        
        return mav;
        
    }
    
    
    /**
     * The 'removeSurveyQuestion' POST request will DELETE the question from the survey. We do not want to delete
     * the question so we can hold on to previous answers. When a question is hidden it will no longer show on the 
     * survey form and the survey build form.
     * 
     * @param s The encrypted surveyId
     * @param v The encrypted pass phrase
     * @param pageId    The page id the question is currently on
     * @param questionId   The id of the selected question
     * @param session
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "removeSurveyQuestion.do", method = RequestMethod.POST)
    public @ResponseBody Integer removeSurveyQuestion(@RequestParam String s, @RequestParam String v, @RequestParam Integer pageId, @RequestParam Integer questionId, HttpSession session) throws Exception {
        
        decryptObject decrypt = new decryptObject();
        
        Object obj = decrypt.decryptObject(s, v);
        
        String[] result = obj.toString().split((","));
        
        int surveyId = Integer.parseInt(result[0].substring(4));
        
        SurveyQuestions questionDetails = surveymanager.getSurveyQuestionById(questionId);
        
        /** Update all question numbers **/
        List<SurveyQuestions> surveyquestions = surveymanager.getAllSurveyQuestions(questionDetails.getSurveyId());

        for(SurveyQuestions question : surveyquestions) {
            if(question.getQuestionNum() > questionDetails.getQuestionNum()) {
                Integer newqNum = question.getQuestionNum()-1;
                question.setQuestionNum(newqNum);
                surveymanager.saveSurveyQuestion(question);
            }
        }
        
        /** Hide the question **/
        questionDetails.setDeleted(true);
        surveymanager.saveSurveyQuestion(questionDetails);
        
        /** Log the change **/
        SurveyChangeLogs scl = new SurveyChangeLogs();

        scl.setNotes("Question number " + questionDetails.getQuestionNum() + " was removed.");
        scl.setSurveyId(surveyId);
        User userDetails = (User) session.getAttribute("userDetails");
        scl.setSystemUserId(userDetails.getId());
        surveymanager.saveChangeLogs(scl);

        return 1;
    }

    
    /**
     * The 'hideSurveyQuestion' POST request will HIDE the question from the survey. 
     * 
     * @param s The encrypted surveyId
     * @param v The encrypted pass phrase
     * @param pageId    The page id the question is currently on
     * @param questionId   The id of the selected question
     * @param session
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "hideSurveyQuestion.do", method = RequestMethod.POST)
    public @ResponseBody Integer hideSurveyQuestion(@RequestParam String s, @RequestParam String v, @RequestParam Integer pageId, @RequestParam Integer questionId, HttpSession session) throws Exception {
        
        decryptObject decrypt = new decryptObject();
        
        Object obj = decrypt.decryptObject(s, v);
        
        String[] result = obj.toString().split((","));
        
        int surveyId = Integer.parseInt(result[0].substring(4));
        
        SurveyQuestions questionDetails = surveymanager.getSurveyQuestionById(questionId);
        
        /** Update all question numbers **/
        List<SurveyQuestions> surveyquestions = surveymanager.getAllSurveyQuestions(questionDetails.getSurveyId());

        for(SurveyQuestions question : surveyquestions) {
            if(question.getQuestionNum() > questionDetails.getQuestionNum()) {
                Integer newqNum = question.getQuestionNum()-1;
                question.setQuestionNum(newqNum);
                surveymanager.saveSurveyQuestion(question);
            }
        }
        
        /** Hide the question **/
        questionDetails.setHide(true);
        surveymanager.saveSurveyQuestion(questionDetails);
        
        /** Log the change **/
        SurveyChangeLogs scl = new SurveyChangeLogs();

        scl.setNotes("Question number " + questionDetails.getQuestionNum() + " was hidden.");
        scl.setSurveyId(surveyId);
        User userDetails = (User) session.getAttribute("userDetails");
        scl.setSystemUserId(userDetails.getId());
        surveymanager.saveChangeLogs(scl);

        return 1;
    }
    
    /**
     * The 'unhideSurveyQuestion' POST request will UNHIDE the question from the survey. 
     * 
     * @param s The encrypted surveyId
     * @param v The encrypted pass phrase
     * @param pageId    The page id the question is currently on
     * @param questionId   The id of the selected question
     * @param session
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "unhideSurveyQuestion.do", method = RequestMethod.POST)
    public @ResponseBody Integer unhideSurveyQuestion(@RequestParam String s, @RequestParam String v, @RequestParam Integer pageId, @RequestParam Integer questionId, HttpSession session) throws Exception {
        
        decryptObject decrypt = new decryptObject();
        
        Object obj = decrypt.decryptObject(s, v);
        
        String[] result = obj.toString().split((","));
        
        int surveyId = Integer.parseInt(result[0].substring(4));
        
        SurveyQuestions questionDetails = surveymanager.getSurveyQuestionById(questionId);
        
        /** Update all question numbers **/
        List<SurveyQuestions> surveyquestions = surveymanager.getAllSurveyQuestions(questionDetails.getSurveyId());

        for(SurveyQuestions question : surveyquestions) {
            if(question.getId() != questionId && question.getQuestionNum() >= questionDetails.getQuestionNum()) {
                Integer newqNum = question.getQuestionNum()+1;
                question.setQuestionNum(question.getQuestionNum()+1);
                surveymanager.saveSurveyQuestion(question);
            }
        }
        
        /** Hide the question **/
        questionDetails.setHide(false);
        surveymanager.saveSurveyQuestion(questionDetails);
        
        /** Log the change **/
        SurveyChangeLogs scl = new SurveyChangeLogs();

        scl.setNotes("Question number " + questionDetails.getQuestionNum() + " is now visible.");
        scl.setSurveyId(surveyId);
        User userDetails = (User) session.getAttribute("userDetails");
        scl.setSystemUserId(userDetails.getId());
        surveymanager.saveChangeLogs(scl);

        return 1;
    }
    
    /**
     * The 'deleteSurveyPage' POST request will remove the selected survey page. 
     * 
     * @param pageId    The selected page id
     * @param session
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "deleteSurveyPage.do", method = RequestMethod.POST)
    public @ResponseBody Integer deleteSurveyPage(@RequestParam Integer pageId, HttpSession session) throws Exception {
        
        SurveyPages pageDetails = surveymanager.getSurveyPageById(pageId);
        
        List<SurveyPages> surveyPages = surveymanager.getSurveyPages(pageDetails.getSurveyId(), false);
        for(SurveyPages page : surveyPages) {
            if(page.getPageNum() > pageDetails.getPageNum()) {
                page.setPageNum(page.getPageNum()-1);
                surveymanager.updateSurveyPage(page);
            }
        }
        
        surveymanager.deleteSurveyPage(pageId);
        
        /** Log the change **/
        SurveyChangeLogs scl = new SurveyChangeLogs();

        scl.setNotes("Survey page number " + pageDetails.getPageNum()+ " was removed.");
        scl.setSurveyId(pageDetails.getSurveyId());
        User userDetails = (User) session.getAttribute("userDetails");
        scl.setSystemUserId(userDetails.getId());
        surveymanager.saveChangeLogs(scl);

        return 1;
    }
    
    /**
     * The 'moveSurveyQuestion.do' POST request will move the selected question to the selected position.
     * 
     * @param curQuestionId The id of the question to copy
     * @param newPage   The page the question is going to be placed
     * @param position  The position the question will be placed
     * @param selQuestionId The id of the selected question to place the question before or after
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "moveSurveyQuestion.do", method = RequestMethod.POST)
    public @ResponseBody Integer moveSurveyQuestion(@RequestParam Integer curQuestionId, @RequestParam Integer newPage, @RequestParam String position, @RequestParam Integer selQuestionId, HttpSession session) throws Exception {
        
        SurveyQuestions curQuestionDetails = surveymanager.getSurveyQuestionById(curQuestionId);
        
        SurveyPages pageDetails = surveymanager.getSurveyPageById(newPage);
        
        Integer curPos = curQuestionDetails.getQuestionNum();
        
        if("after".equals(position)) {
           SurveyQuestions selQuestionDetails = surveymanager.getSurveyQuestionById(selQuestionId); 
           
           Integer newPos = selQuestionDetails.getQuestionNum() + 1;
           
           /* Update all other survey page numbers */
           List<SurveyQuestions> surveyquestions = surveymanager.getAllSurveyQuestions(curQuestionDetails.getSurveyId());
           
           if(newPos > curPos) {
               for(SurveyQuestions question : surveyquestions) {
                   if(question.getQuestionNum() ==  selQuestionDetails.getQuestionNum()) {
                       Integer newqNum = question.getQuestionNum()-1;
                       question.setQuestionNum(newqNum);
                       surveymanager.saveSurveyQuestion(question);
                   }
               }
               newPos = newPos - 1;
               curQuestionDetails.setQuestionNum(newPos);
               
           }
           else if (newPos < curPos) {
              for(SurveyQuestions question : surveyquestions) {
                   if(question.getQuestionNum() >= newPos && question.getQuestionNum() <= curPos) {
                       Integer newqNum = question.getQuestionNum()+1;
                       question.setQuestionNum(newqNum);
                       surveymanager.saveSurveyQuestion(question);
                   }
              }
              curQuestionDetails.setQuestionNum(newPos);
           }
           
           curQuestionDetails.setSurveyPageId(newPage);
           surveymanager.saveSurveyQuestion(curQuestionDetails);
           
           /** Log the change **/
           SurveyChangeLogs scl = new SurveyChangeLogs();

           scl.setNotes("Question number " + curPos + " was moved to " + " position " + newPos + " on page " + pageDetails.getPageNum());
           scl.setSurveyId(curQuestionDetails.getSurveyId());
           User userDetails = (User) session.getAttribute("userDetails");
           scl.setSystemUserId(userDetails.getId());
           surveymanager.saveChangeLogs(scl);
        }
        /** Adding before a question **/
        else if("before".equals(position)) {
           SurveyQuestions selQuestionDetails = surveymanager.getSurveyQuestionById(selQuestionId); 
           
           Integer newPos = selQuestionDetails.getQuestionNum() - 1;
           
           /* Update all other survey page numbers */
           List<SurveyQuestions> surveyquestions = surveymanager.getAllSurveyQuestions(curQuestionDetails.getSurveyId());
           
           if(newPos > curPos) {
               for(SurveyQuestions question : surveyquestions) {
                   if(question.getQuestionNum() <=  newPos) {
                       Integer newqNum = question.getQuestionNum()-1;
                       question.setQuestionNum(newqNum);
                       surveymanager.saveSurveyQuestion(question);
                   }
               }
               curQuestionDetails.setQuestionNum(newPos);
           }
           else if (newPos < curPos) {
              for(SurveyQuestions question : surveyquestions) {
                   if(question.getQuestionNum() != newPos && question.getQuestionNum() > newPos && question.getQuestionNum() <= curPos) {
                       Integer newqNum = question.getQuestionNum()+1;
                       question.setQuestionNum(newqNum);
                       surveymanager.saveSurveyQuestion(question);
                   }
              }
              newPos = newPos + 1;
              curQuestionDetails.setQuestionNum(newPos);
           }
           
           curQuestionDetails.setSurveyPageId(newPage);
           surveymanager.saveSurveyQuestion(curQuestionDetails);
           
           /** Log the change **/
           SurveyChangeLogs scl = new SurveyChangeLogs();

           scl.setNotes("Question number " + curPos + " was moved to " + " position " + newPos + " on page " + pageDetails.getPageNum());
           scl.setSurveyId(curQuestionDetails.getSurveyId());
           User userDetails = (User) session.getAttribute("userDetails");
           scl.setSystemUserId(userDetails.getId());
           surveymanager.saveChangeLogs(scl);
        }
        /** Adding to a blank page **/
        else {
          
           /* Update all other survey page numbers */
           List<SurveyQuestions> surveyquestions = surveymanager.getAllSurveyQuestions(curQuestionDetails.getSurveyId());
           
           Integer pageNum = surveymanager.getSurveyPageById(newPage).getPageNum();
           
           Integer newPos = curQuestionDetails.getQuestionNum();
          
           if(surveyquestions.size() > 1) {
                for(SurveyQuestions question : surveyquestions) {
                
                    Integer questionpageNum = surveymanager.getSurveyPageById(question.getSurveyPageId()).getPageNum();

                    if(curPos < question.getQuestionNum() && pageNum > questionpageNum) {
                        newPos = question.getQuestionNum();
                        Integer newqNum = question.getQuestionNum() - 1;
                        question.setQuestionNum(newqNum);
                        surveymanager.saveSurveyQuestion(question);
                    }
                    else if(curPos > question.getQuestionNum() && questionpageNum > pageNum ) {
                        newPos = question.getQuestionNum();
                        Integer newqNum = question.getQuestionNum() + 1;
                        question.setQuestionNum(newqNum);
                        surveymanager.saveSurveyQuestion(question);
                    }
                } 
           }
           
           curQuestionDetails.setQuestionNum(newPos);
           curQuestionDetails.setSurveyPageId(newPage);
           surveymanager.saveSurveyQuestion(curQuestionDetails);
           
           /** Log the change **/
           SurveyChangeLogs scl = new SurveyChangeLogs();

           scl.setNotes("Question number " + curPos + " was moved to " + " position " + newPos + " on page " + pageDetails.getPageNum());
           scl.setSurveyId(curQuestionDetails.getSurveyId());
           User userDetails = (User) session.getAttribute("userDetails");
           scl.setSystemUserId(userDetails.getId());
           surveymanager.saveChangeLogs(scl);
        }
        
        return 1;
    }
    
    /**
     * The 'copySurveyQuestion.do' POST request will copy the selected question into a new question.
     * 
     * @param curQuestionId The id of the question to copy
     * @param newPage   The page the question is going to be placed
     * @param position  The position the question will be placed
     * @param selQuestionId The id of the selected question to place the question before or after
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "copySurveyQuestion.do", method = RequestMethod.POST)
    public @ResponseBody Integer copySurveyQuestion(@RequestParam Integer curQuestionId, @RequestParam Integer newPage, @RequestParam String position, @RequestParam Integer selQuestionId, HttpSession session) throws Exception {
        
        SurveyQuestions curQuestionDetails = surveymanager.getSurveyQuestionById(curQuestionId);
        
        SurveyPages pageDetails = surveymanager.getSurveyPageById(newPage);
        
        Integer curPos = curQuestionDetails.getQuestionNum();
        
        /* Update all other survey page numbers */
        List<SurveyQuestions> surveyquestions = surveymanager.getAllSurveyQuestions(curQuestionDetails.getSurveyId());
        
        Integer qnum = 1;
        if(surveyquestions != null && !surveyquestions.isEmpty()) {
            qnum = surveyquestions.get(surveyquestions.size()-1).getQuestionNum()+1;
        }
        
        SurveyQuestions newQuestion = new SurveyQuestions();
        newQuestion.setAllowMultipleAns(curQuestionDetails.isAllowMultipleAns());
        newQuestion.setAnswerTypeId(curQuestionDetails.getAnswerTypeId());
        newQuestion.setAutoPopulateFromField(curQuestionDetails.isAutoPopulateFromField());
        newQuestion.setColumnsDisplayed(curQuestionDetails.getColumnsDisplayed());
        newQuestion.setDspQuestionId(curQuestionDetails.getDspQuestionId());
        newQuestion.setHide(curQuestionDetails.isHide());
        newQuestion.setQuestion(curQuestionDetails.getQuestion());
        newQuestion.setQuestionNum(qnum);
        newQuestion.setRequired(curQuestionDetails.isRequired());
        newQuestion.setRequiredResponse(curQuestionDetails.getRequiredResponse());
        newQuestion.setSaveToFieldId(curQuestionDetails.getSaveToFieldId());
        newQuestion.setSurveyId(curQuestionDetails.getSurveyId());
        newQuestion.setSurveyPageId(newPage);
        newQuestion.setValidationId(curQuestionDetails.getValidationId());
        
        Integer newQuestionId = surveymanager.saveNewSurveyQuestion(newQuestion);
        
        SurveyQuestions newQuestionDetails = surveymanager.getSurveyQuestionById(newQuestionId);
        
        /* Update all other survey page numbers */
        List<SurveyQuestions> currsurveyquestions = surveymanager.getAllSurveyQuestions(curQuestionDetails.getSurveyId());
        
        Integer newPos = 0;
        
        if("after".equals(position)) {
           SurveyQuestions selQuestionDetails = surveymanager.getSurveyQuestionById(selQuestionId); 
           
           newPos = selQuestionDetails.getQuestionNum() + 1;
           
           if(newPos > newQuestionDetails.getQuestionNum()) {
               for(SurveyQuestions question : currsurveyquestions) {
                   if(question.getQuestionNum() ==  selQuestionDetails.getQuestionNum()) {
                       Integer newqNum = question.getQuestionNum()-1;
                       question.setQuestionNum(newqNum);
                       surveymanager.saveSurveyQuestion(question);
                   }
               }
               newPos = newPos - 1;
               newQuestionDetails.setQuestionNum(newPos);
           }
           else if (newPos < newQuestionDetails.getQuestionNum()) {
              for(SurveyQuestions question : currsurveyquestions) {
                   if(question.getQuestionNum() >= newPos && question.getQuestionNum() <= newQuestionDetails.getQuestionNum()) {
                       Integer newqNum = question.getQuestionNum()+1;
                       question.setQuestionNum(newqNum);
                       surveymanager.saveSurveyQuestion(question);
                   }
              }
              newQuestionDetails.setQuestionNum(newPos);
           }
           
        }
        /** Adding before a question **/
        else if("before".equals(position)) {
           SurveyQuestions selQuestionDetails = surveymanager.getSurveyQuestionById(selQuestionId); 
           
           newPos = selQuestionDetails.getQuestionNum() - 1;
           
           if(newPos > newQuestionDetails.getQuestionNum()) {
               for(SurveyQuestions question : currsurveyquestions) {
                   if(question.getQuestionNum() <=  newPos) {
                       Integer newqNum = question.getQuestionNum()-1;
                       question.setQuestionNum(newqNum);
                       surveymanager.saveSurveyQuestion(question);
                   }
               }
               curQuestionDetails.setQuestionNum(newPos);
           }
           else if (newPos < newQuestionDetails.getQuestionNum()) {
              for(SurveyQuestions question : currsurveyquestions) {
                   if(question.getQuestionNum() != newPos && question.getQuestionNum() > newPos && question.getQuestionNum() <= curQuestionDetails.getQuestionNum()) {
                       Integer newqNum = question.getQuestionNum()+1;
                       question.setQuestionNum(newqNum);
                       surveymanager.saveSurveyQuestion(question);
                   }
              }
              newPos = newPos + 1;
              newQuestionDetails.setQuestionNum(newPos);
           }
        }
        /** Adding to a blank page **/
        else {
           
           Integer pageNum = surveymanager.getSurveyPageById(newQuestionDetails.getSurveyPageId()).getPageNum();
           
           newPos = newQuestionDetails.getQuestionNum();
          
           if(currsurveyquestions.size() > 1) {
                for(SurveyQuestions question : currsurveyquestions) {
                
                    Integer questionpageNum = surveymanager.getSurveyPageById(question.getSurveyPageId()).getPageNum();
                   
                    if(newQuestionDetails.getQuestionNum() < question.getQuestionNum() && pageNum > questionpageNum) {
                        newPos = question.getQuestionNum();
                        Integer newqNum = question.getQuestionNum()-1;
                        question.setQuestionNum(newqNum);
                        surveymanager.saveSurveyQuestion(question);
                    }
                    else if(newQuestionDetails.getQuestionNum() > question.getQuestionNum() && questionpageNum > pageNum ) {
                        newPos = question.getQuestionNum();
                        Integer newqNum = question.getQuestionNum()+1;
                        question.setQuestionNum(question.getQuestionNum() + 1);
                        surveymanager.saveSurveyQuestion(question);
                    }
                } 
           }
           
           newQuestionDetails.setQuestionNum(newPos);
        }
        
        surveymanager.saveSurveyQuestion(newQuestionDetails);
        
        /** Log the change **/
        SurveyChangeLogs scl = new SurveyChangeLogs();

        scl.setNotes("Copied Question number " + curPos + ". The new question was moved to position " + newPos + " on page " + pageDetails.getPageNum());
        scl.setSurveyId(curQuestionDetails.getSurveyId());
        User userDetails = (User) session.getAttribute("userDetails");
        scl.setSystemUserId(userDetails.getId());
        surveymanager.saveChangeLogs(scl);
        
        return 1;
    }
    
    /**
     * The 'getQuestionsForSelectedPage' GET request will return a list of questions for the past in page.
     * 
     * @param pageId        The id of the selected page
     * @param questionId    The id of the selected question
     * @return
     * @throws Exception 
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/getQuestionsForSelectedPage.do", method = RequestMethod.GET)
    public @ResponseBody List getQuestionsForSelectedPage(@RequestParam(value = "pageId", required = true) Integer pageId, @RequestParam(value = "questionId", required = true) Integer questionId) throws Exception {

        List questions = surveymanager.getQuestionForSelectedPage(pageId, questionId);
        return questions;
    }
    
    
    /**
     * This method returns the change log - save note box
     *
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/saveNote", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView newNoteBox() throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("btnValue", "Save Note");
        mav.setViewName("/programAdmin/surveys/saveNote");
        return mav;
    }

    /**
     * 
     * @param request
     * @param response
     * @param session
     * @param redirectAttr
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/saveNote", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView saveNote(HttpServletRequest request,HttpServletResponse response, HttpSession session,RedirectAttributes redirectAttr) throws Exception {
        
        ModelAndView mav = new ModelAndView();
        //surveyId
        SurveyChangeLogs scl = new SurveyChangeLogs();

        scl.setNotes(request.getParameter("notes"));
        scl.setSurveyId(Integer.parseInt(request.getParameter("surveyId")));
        User userDetails = (User) session.getAttribute("userDetails");
        scl.setSystemUserId(userDetails.getId());
        //insert here
        surveymanager.saveChangeLogs(scl);

        mav.setViewName("/programAdmin/surveys/saveNote");

        /**
         * log user *
         */
        try {
            Log_userSurveyActivity ua = new Log_userSurveyActivity();
            ua.setActivityDesc("Saved survey change note");
            ua.setController(controllerName);
            ua.setPageAccessed("/saveNote");
            ua.setProgramId((Integer) session.getAttribute("selprogramId"));
            ua.setSurveyId(Integer.parseInt(request.getParameter("surveyId")));
            ua.setSystemUserId(userDetails.getId());
            usermanager.insertUserLog(ua);
        } catch (Exception ex1) {
            ex1.printStackTrace();
        }
        return mav;
    }

    /**
     * This method returns the modal with change logs info
     *
     * @param session
     * @param s = surveyId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/changeLog", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView viewChangeLog(HttpSession session, @RequestParam Integer surveyId) throws Exception {
        
        
        ModelAndView mav = new ModelAndView();

        surveys survey = checkSurveyPermission(session, surveyId, "/changeLog");

        if (survey == null) {
            mav.addObject("notValid", "Survey Id is not valid or you do not have permission to view this survey.");
            return mav;
        } else {
            /**
             * now we get change logs *
             */
            List<SurveyChangeLogs> getSurveyChangeLogs = surveymanager.getSurveyChangeLogs(survey.getId());
            mav.addObject("changeLogs", getSurveyChangeLogs);
            /**
             * log user *
             */
            try {
                Log_userSurveyActivity ua = new Log_userSurveyActivity();
                ua.setActivityDesc("Saved survey change log");
                ua.setController(controllerName);
                ua.setPageAccessed("/changeLog");
                ua.setProgramId((Integer) session.getAttribute("selprogramId"));
                ua.setSurveyId(survey.getId());
                User userDetails = (User) session.getAttribute("userDetails");
                ua.setSystemUserId(userDetails.getId());
                usermanager.insertUserLog(ua);
            } catch (Exception ex1) {
                ex1.printStackTrace();
            }

        }

        mav.setViewName("/programAdmin/surveys/changeLogs");
        return mav;
    }

    /**
     * This method returns the form to edit survey title
     *
     * @param session
     * @param s = surveyId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "getSurveyForm.do", method = RequestMethod.GET)
    public @ResponseBody ModelAndView getSurveyTitleForm(HttpSession session, @RequestParam String s, @RequestParam String v) throws Exception {
        
        /* Decrypt the url */
        decryptObject decrypt = new decryptObject();
        
        Object obj = decrypt.decryptObject(s, v);
        
        String[] result = obj.toString().split((","));
        
        int surveyId = Integer.parseInt(result[0].substring(4));
        
        
        ModelAndView mav = new ModelAndView();

        surveys survey = checkSurveyPermission(session, surveyId, "getSurveyForm.do");
        if (survey == null) {
            mav.addObject("notValid", "Survey Id is not valid or you do not have permission.");
            return mav;
        }

        List<activityCodes> activityCodes = activitycodemanager.getActivityCodes(0, 0);
        mav.addObject("activityCodes", activityCodes);
        mav.addObject("survey", survey);
        mav.setViewName("/programAdmin/surveys/surveyModal");
        /**
         * log user *
         */
        try {
            Log_userSurveyActivity ua = new Log_userSurveyActivity();
            ua.setActivityDesc("Access Survey Title Form");
            ua.setController(controllerName);
            ua.setPageAccessed("getSurveyForm.do");
            ua.setProgramId((Integer) session.getAttribute("selprogramId"));
            ua.setSurveyId(survey.getId());
            User userDetails = (User) session.getAttribute("userDetails");
            ua.setSystemUserId(userDetails.getId());
            usermanager.insertUserLog(ua);
        } catch (Exception ex1) {
            ex1.printStackTrace();
        }

        return mav;
    }

    /**
     * This method checks and saves new survey title
     *
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
        List<activityCodes> activityCodes = activitycodemanager.getActivityCodes(0, 0);
        mav.addObject("activityCodes", activityCodes);
        mav.setViewName("/programAdmin/surveys/surveyModal");

        if (result.hasErrors()) {
            mav.addObject("survey", surveyNew);
            return mav;
        }

        //check permissions
        surveys survey = checkSurveyPermission(session, surveyNew.getId(), "saveSurveyForm.do");
        if (survey == null) {
            mav.addObject("survey", surveyNew);
            return mav;
        }

        //check title	
        if (!survey.getTitle().trim().equalsIgnoreCase(surveyNew.getTitle().trim())) {
            //we do not allow duplicate title
            List<surveys> existingTitle = surveymanager.getProgramSurveysByTitle(surveyNew);
            if (existingTitle.size() != 0) {
                mav.addObject("survey", surveyNew);
                mav.addObject("activityCodes", activityCodes);
                mav.addObject("existingTitle", "Title is in use by this program already.");
                return mav;
            }
        }
        
        //check tag	
        if (!"".equals(surveyNew.getSurveyTag()) && !survey.getSurveyTag().trim().equalsIgnoreCase(surveyNew.getSurveyTag().trim())) {
            //we do not allow duplicate title
            List<surveys> existingTag = surveymanager.getProgramSurveysByTag(surveyNew);
            if (existingTag.size() != 0) {
                mav.addObject("survey", surveyNew);
                mav.addObject("activityCodes", activityCodes);
                mav.addObject("existingsurveyTag", "Survey Tag is in use by this program already.");
                return mav;
            }
        }

        // update 
        surveymanager.updateSurvey(surveyNew);
        mav.addObject("updated", "updated");

        /**
         * log user *
         */
        try {
            Log_userSurveyActivity ua = new Log_userSurveyActivity();
            ua.setActivityDesc("Updated Survey Title");
            ua.setController(controllerName);
            ua.setPageAccessed("saveSurveyForm.do");
            ua.setProgramId((Integer) session.getAttribute("selprogramId"));
            ua.setSurveyId(survey.getId());
            User userDetails = (User) session.getAttribute("userDetails");
            ua.setSystemUserId(userDetails.getId());
            usermanager.insertUserLog(ua);
        } catch (Exception ex1) {
            ex1.printStackTrace();
        }

        return mav;
    }
    
    
    /**
     * The 'getAvailableSurveys.do' GET request will return a list of available surveys for the selected 
     * program.
     * 
     * @param session
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "getAvailableSurveys.do", method = RequestMethod.GET)
    public @ResponseBody ModelAndView getAvailableSurveys(HttpSession session) throws Exception {
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/programAdmin/surveys/copySurvey");
        
        /* Get the list of programs in the system */
        List<surveys> surveys = surveymanager.getProgramSurveys((Integer) session.getAttribute("selprogramId"));
        mav.addObject("surveys", surveys);
        
        return mav;
    }

    /**
     * The 'copySurveySubmit.do' POST request will create a new survey based on the selected survey.
     * @param session
     * @param surveyId
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "copySurveySubmit.do", method = RequestMethod.POST)
    public @ResponseBody ModelAndView copySurveySubmit(HttpSession session , @RequestParam(value = "surveyId", required = true) Integer surveyId) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/programAdmin/surveys/copySurvey");
        
        Integer newSurveyId = surveymanager.copySurvey(surveyId);
        
        
        Map<String,String> map = new HashMap<String,String>();
        map.put("id",Integer.toString(newSurveyId));
        map.put("topSecret",topSecret);
        
        encryptObject encrypt = new encryptObject();

        String[] encrypted = encrypt.encryptObject(map);
        
        mav.addObject("encryptedURL", "?s="+encrypted[0]+"&v="+encrypted[1]);
        
        return mav;
    }

    /**
     * shared methods *
     */
    /**
     * This method checks to see if the user has permission to the survey in question It first checks to see if the survey Id is an integer, then it checks to see if it belongs to session's programId
     *
     * @param session
     * @param s
     * @param pageName
     * @return
     */
    public surveys checkSurveyPermission(HttpSession session, Integer surveyId, String pageName) {

        surveys survey = new surveys();

        /**
         * we check to make sure s is a number *
         */
        try {
            Integer programId = (Integer) session.getAttribute("selprogramId");
            /**
             * make sure session program id matches the survey's programId *
             */
            try {
                survey = surveymanager.getSurveyById(surveyId);
                
                if (survey != null && survey.getProgramId() != programId) {
                    survey = null;
                    try {
                        Log_userSurveyActivity ua = new Log_userSurveyActivity();
                        ua.setActivityDesc("accessed denied");
                        ua.setController(controllerName);
                        ua.setPageAccessed(pageName);
                        ua.setSurveyId(surveyId);
                        User userDetails = (User) session.getAttribute("userDetails");
                        ua.setSystemUserId(userDetails.getId());
                        usermanager.insertUserLog(ua);
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
                User userDetails = (User) session.getAttribute("userDetails");
                ua.setSystemUserId(userDetails.getId());
                usermanager.insertUserLog(ua);
            } catch (Exception ex1) {
                ex1.printStackTrace();
            }
        }

        return survey;

    }
    
    /**
     * The 'checkForDuplicateQuestionTag' GET request will query the system to make sure their is not a same contract number
     * in the system.
     * 
     * @param i
     * @param v
     * @param enteredDate
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/checkForDuplicateQuestionTag.do", method = RequestMethod.GET, produces={MediaType.TEXT_PLAIN_VALUE})
    @ResponseBody
    public String checkForDuplicateQuestionTag(
            @RequestParam Integer surveyId, 
            @RequestParam Integer questionId, 
            @RequestParam String questionTag) throws Exception {
        
        boolean questionTagOk =  surveymanager.checkForDuplicateQuestionTag(surveyId, questionId, questionTag);
        
        if(questionTagOk == true) {
            return "0";
        }
        else {
            return "1";
        }
    }
    
}
