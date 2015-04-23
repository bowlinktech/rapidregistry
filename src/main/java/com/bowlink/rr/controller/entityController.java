/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.controller;

import com.bowlink.rr.model.programOrgHierarchy;
import com.bowlink.rr.model.programOrgHierarchyDetails;
import com.bowlink.rr.service.orgHierarchyManager;
import com.bowlink.rr.service.programManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author chadmccue
 */
@Controller
@RequestMapping("/programAdmin/entity")
public class entityController {
    
    @Autowired
    programManager programmanager;
    
    @Autowired
    orgHierarchyManager orghierarchymanager;
    
    /**
     * The '' request will serve up the program organization hierarchy list page.
     *
     * @param request
     * @param response
     * 
     * @return	the program organization hierarchy list page view
     * @throws Exception
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView listTopLevelEntities(HttpServletRequest request, HttpServletResponse response, HttpSession session, RedirectAttributes redirectAttr) throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/entityList");
        mav.addObject("id", session.getAttribute("selprogramId"));
        
        List<programOrgHierarchy> entities = orghierarchymanager.getProgramOrgHierarchy((Integer) session.getAttribute("selprogramId"));
        mav.addObject("entities", entities);
        mav.addObject("selEntity", entities.get(0).getId());

        return mav;

    }
    
    /**
     * The 'getEntityList' GET request will return the list of districts based on the selected county.
     * 
     * @param request
     * @param response
     * @param session
     * @param searchString  The string containing the list of search parameters.
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "getEntityList", method = RequestMethod.GET)
    @ResponseBody public ModelAndView getEntityList(HttpSession session, @RequestParam(value = "entityId", required = false) Integer entityId) throws Exception {
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/programAdmin/entity/entityList");
        
        /* Get selected entity details */
        programOrgHierarchy entityDetails = orghierarchymanager.getOrgHierarchyById(entityId);
        mav.addObject("entityName", entityDetails.getName());
        mav.addObject("entityDsp", entityDetails.getDspPos());
        mav.addObject("entityId", entityId);
        
        List<programOrgHierarchyDetails> entityItems = orghierarchymanager.getProgramHierarchyItems(entityId);
        mav.addObject("entityItems", entityItems);
        
        return mav;
    }
    
}
