package com.bowlink.rr.controller;


import java.util.List;

import com.bowlink.rr.model.User;
import com.bowlink.rr.service.importManager;
import com.bowlink.rr.service.userManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gchan
 */
@Controller
@RequestMapping(value={"/sysAdmin/adminFns"})
public class adminFnsController {
    
    @Autowired
    importManager importmanager;
    
    @Autowired
    userManager usermanager;
    
    /**
     * The '/importfile' request will serve up the user list drop down of program types so the 
     * admin can upload as
     *
     * @param request
     * @param response
     * 
     * @return	user list
     * @throws Exception
     */
    @RequestMapping(value = "/importfile", method = RequestMethod.GET)
    public ModelAndView importScreen(HttpServletRequest request, HttpServletResponse response, 
    		HttpSession session, RedirectAttributes redirectAttr) throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/importfile");
        List<User> userList = usermanager.getUsersByRoleId(3);
        mav.addObject("users", userList);

        return mav;
    }
        
}
