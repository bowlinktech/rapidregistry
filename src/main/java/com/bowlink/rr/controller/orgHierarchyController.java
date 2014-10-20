/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.controller;

import com.bowlink.rr.model.program;
import com.bowlink.rr.model.programOrgHierarchy;
import com.bowlink.rr.service.orgHierarchyManager;
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

/**
 *
 * @author chadmccue
 */
@Controller
@RequestMapping(value={"/sysAdmin/programs/{programName}/organization-hierarchy"})
public class orgHierarchyController {
    
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
    public ModelAndView listOrgHierarchy(HttpServletRequest request, HttpServletResponse response, HttpSession session, RedirectAttributes redirectAttr) throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/programOrgHierarchy");
        mav.addObject("id", session.getAttribute("programId"));

        program programDetails = programmanager.getProgramById((Integer) session.getAttribute("programId"));
        mav.addObject("programDetails", programDetails);
        
        
        /* Get the list of org hierarchy for the click program */
        List<programOrgHierarchy> orgHierarchyList = orghierarchymanager.getProgramOrgHierarchy((Integer) session.getAttribute("programId"));
        mav.addObject("orgHierarchyList", orgHierarchyList);
        

        return mav;

    }
    
    /**
     * The '/hierarchyForm' request will display the form to add a new program organization hierarchy.
     *
     * @param request
     * @param response
     * @param session
     * @param redirectAttr
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/hierarchyForm", method = RequestMethod.GET)
    public @ResponseBody ModelAndView hierarchyForm(@RequestParam Integer id, HttpServletRequest request, HttpServletResponse response, HttpSession session, RedirectAttributes redirectAttr) throws Exception {
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/programs/orgHierarchy/details");
        
        if(id == 0) {
            programOrgHierarchy orgHierarchy = new programOrgHierarchy();
            orgHierarchy.setProgramId((Integer) session.getAttribute("programId"));
            mav.addObject("modalTitle", "Add New Organization Hierarchy Entry");
            Integer maxDspPos = orghierarchymanager.getProgramOrgHierarchy((Integer) session.getAttribute("programId")).size()+1;
            mav.addObject("hierarchyDetails", orgHierarchy);
            mav.addObject("maxDspPos", maxDspPos);
        }
        else {
            programOrgHierarchy orgHierarchy = orghierarchymanager.getOrgHierarchyById(id);
            Integer maxDspPos = orghierarchymanager.getProgramOrgHierarchy((Integer) session.getAttribute("programId")).size();
            mav.addObject("hierarchyDetails", orgHierarchy);
            mav.addObject("modalTitle", "Edit Organization Hierarchy Entry");
            mav.addObject("maxDspPos", maxDspPos);
        }
        
        mav.addObject("programName", session.getAttribute("programName"));

        return mav;
    }
    
    
    /**
     * The '/saveOrgHierarchy' POST request will submit the new organization hierarchy.
     *
     * @param program	The object holding the program form fields
     * @param result	The validation result
     * @param redirectAttr	The variable that will hold values that can be read after the redirect
     * @param action	The variable that holds which button was pressed
     *
     * @return	Will return the organization list page on "Save & Close" Will return the organization details page on "Save" Will return the organization create page on error
     * @throws Exception
     */
    @RequestMapping(value = "/saveOrgHierarchy", method = RequestMethod.POST)
    public @ResponseBody ModelAndView saveOrgHierarchy(@Valid @ModelAttribute(value = "hierarchyDetails") programOrgHierarchy hierarchyDetails, BindingResult result, @RequestParam Integer currdspPos, HttpSession session) throws Exception {

        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/sysAdmin/programs/orgHierarchy/details");
            mav.addObject("programName", session.getAttribute("programName"));
            
            if(hierarchyDetails.getId() == 0) {
                mav.addObject("modalTitle", "Add New Organization Hierarchy Entry");
                Integer maxDspPos = orghierarchymanager.getProgramOrgHierarchy((Integer) session.getAttribute("programId")).size()+1;
                mav.addObject("maxDspPos", maxDspPos);
            }
            else {
                Integer maxDspPos = orghierarchymanager.getProgramOrgHierarchy((Integer) session.getAttribute("programId")).size();
                mav.addObject("modalTitle", "Edit Organization Hierarchy Entry");
                mav.addObject("maxDspPos", maxDspPos);
            }
            
            return mav;
        }
        
        /* Check to see if dspPos changed, if so need to update the old dsp Pos */
        if(hierarchyDetails.getDspPos() != currdspPos) {
            programOrgHierarchy foundHierarchy = orghierarchymanager.getProgramOrgHierarchyBydspPos(hierarchyDetails.getDspPos(), (Integer) session.getAttribute("programId"));
            if(foundHierarchy != null) {
                foundHierarchy.setDspPos(currdspPos);
                orghierarchymanager.saveOrgHierarchy(foundHierarchy);
            }
        }
        
        orghierarchymanager.saveOrgHierarchy(hierarchyDetails);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/programs/orgHierarchy/details");
        mav.addObject("success", "hierarchySaved");
        
        return mav;

    }
}
