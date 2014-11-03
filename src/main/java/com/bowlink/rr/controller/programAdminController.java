/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

/**
 *
 * @author chadmccue
 */
@Controller
@RequestMapping("/programAdmin")
public class programAdminController {
    
    /**
     * The 'changeRegistry{params}' request will switch registries to the selected registry.
     *
     * @param request
     * @param response
     * @return	the administrator dashboard view
     * @throws Exception
     */
    @RequestMapping(value = "/changeRegistry{params}", method = RequestMethod.GET)
    public ModelAndView listPrograms(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "i", required = true) Integer programId, HttpSession session, RedirectAttributes redirectAttr) throws Exception {

        session.setAttribute("selprogramId", programId);
        
        /* Redirect to the survey list page */
        ModelAndView mav = new ModelAndView(new RedirectView("/programAdmin/surveys"));
       return mav;
        
        
    }
    
}
