/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.controller;

import com.bowlink.rr.model.surveys;
import com.bowlink.rr.service.surveyManager;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author chadmccue
 */
@Controller
@RequestMapping("/programAdmin/surveys")
public class surveyController {
    
    @Autowired
    surveyManager surveymanager;
    
    /**
     * The '' request will display the list of surveys.
     *
     * @param request
     * @param response
     * @return	the program admin survey view
     * @throws Exception
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView listSurveys(HttpServletRequest request, HttpServletResponse response, HttpSession session, RedirectAttributes redirectAttr) throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/surveys");

        /* Get the list of programs in the system */
        List<surveys> surveys = surveymanager.getProgramSurveys((Integer) session.getAttribute("selprogramId"));

        mav.addObject("surveys", surveys);

        return mav;

    }
    
}
