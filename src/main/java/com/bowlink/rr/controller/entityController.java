/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.controller;

import com.bowlink.rr.model.activityCodes;
import com.bowlink.rr.model.programActivityCodes;
import com.bowlink.rr.model.programOrgHierarchy;
import com.bowlink.rr.model.programOrgHierarchyAssoc;
import com.bowlink.rr.model.programOrgHierarchyDetails;
import com.bowlink.rr.reference.USStateList;
import com.bowlink.rr.security.decryptObject;
import com.bowlink.rr.security.encryptObject;
import com.bowlink.rr.service.activityCodeManager;
import com.bowlink.rr.service.orgHierarchyManager;
import com.bowlink.rr.service.programManager;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    
    @Autowired
    activityCodeManager activitycodemanager;
    
    
    private String topSecret = "What goes up but never comes down";
    
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
    public ModelAndView listTopLevelEntities(HttpServletRequest request, HttpServletResponse response, HttpSession session, RedirectAttributes redirectAttr, @RequestParam(value = "i", required = false) Integer i) throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/entityList");
        mav.addObject("id", session.getAttribute("selprogramId"));
        
        List<programOrgHierarchy> entities = orghierarchymanager.getProgramOrgHierarchy((Integer) session.getAttribute("selprogramId"));
        mav.addObject("entities", entities);
        if(i != null && i > 0) {
            mav.addObject("selEntity", i);
        }
        else {
            mav.addObject("selEntity", entities.get(0).getId());
        }
        
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
        
        encryptObject encrypt = new encryptObject();
        Map<String,String> map;
        for(programOrgHierarchyDetails entityItem : entityItems) {
            
            //Encrypt the use id to pass in the url
            map = new HashMap<String,String>();
            map.put("id",Integer.toString(entityItem.getId()));
            map.put("topSecret",topSecret);
            
            String[] encrypted = encrypt.encryptObject(map);
            
            entityItem.setEncryptedId(encrypted[0]);
            entityItem.setEncryptedSecret(encrypted[1]);
        }
        
        mav.addObject("entityItems", entityItems);
        
        return mav;
    }
    
    
    /**
     * The 'entityItemDetails' GET request will return the details of the selected hierarchy
     * 
     * @param entityId   The id of the clicked hierarchy
     * @param itemId    The id of the clicked hierarchy item
     * @param session
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/entityItemDetails", method = RequestMethod.GET)
    @ResponseBody public ModelAndView getHierarchyItemDetails(@RequestParam(value = "entityId", required = false) Integer entityId, @RequestParam(value = "itemId", required = false) Integer itemId, @RequestParam(value = "dspPos", required = false) Integer dspPos, HttpSession session) throws Exception {
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/programAdmin/entity/entityItemForm");
        mav.addObject("entityId", entityId);
        
        programOrgHierarchy entityDetails = orghierarchymanager.getOrgHierarchyById(entityId);
        
        String btnValue;
        programOrgHierarchyDetails entityItemDetails;
        if(itemId == 0) {
            entityItemDetails = new programOrgHierarchyDetails();
            entityItemDetails.setProgramHierarchyId(entityId);
            btnValue = "Create " + entityDetails.getName();
        }
        else {
            entityItemDetails = orghierarchymanager.getProgramHierarchyItemDetails(itemId);
            btnValue = "Edit " + entityDetails.getName();
        }
        
        //Get a list of states
        USStateList stateList = new USStateList();

        //Get the object that will hold the states
        mav.addObject("stateList", stateList.getStates());
       
        mav.addObject("hierarchyDetails",entityItemDetails);
        mav.addObject("btnValue", btnValue);
        
        return mav;
    }
    
    
    /**
     * The '/saveEntityItem' POST request will handle submitting the hierarchyform.
     *
     * @param admindetails    The object containing the system administrator form fields
     * @param result        The validation result
     * @param redirectAttr	The variable that will hold values that can be read after the redirect
     *
     * @return	Will return the system administrators list page on "Save" Will return the system administrator form page on error
     
     * @throws Exception
     */
    @RequestMapping(value = "/saveEntityItem", method = RequestMethod.POST)
    public @ResponseBody ModelAndView saveEntityItem(@Valid @ModelAttribute(value = "hierarchyDetails") programOrgHierarchyDetails entityItemDetails, BindingResult result, HttpSession session) throws Exception {

        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/programAdmin/entity/entityItemForm");
            
            programOrgHierarchy entityDetails = orghierarchymanager.getOrgHierarchyById(entityItemDetails.getProgramHierarchyId());
            
            String btnValue;
            if(entityItemDetails.getId() > 0) {
                 btnValue = "Create " + entityDetails.getName();
            }
            else {
                btnValue = "Edit " + entityDetails.getName();
            }
            mav.addObject("btnValue", btnValue);
            return mav;
        }
       
        orghierarchymanager.saveOrgHierarchyItem(entityItemDetails);

        ModelAndView mav = new ModelAndView("/programAdmin/entity/entityItemForm");
        
        
        if(entityItemDetails.getId() > 0) {
            mav.addObject("success", "itemUpdated");
        }
        else {
           mav.addObject("success", "itemCreated");
        }
        
        return mav;
    }
    
    
    /**
     * The '/details' request will serve up the program organization hierarchy list page.
     *
     * @param request
     * @param response
     * 
     * @return	the program organization hierarchy list page view
     * @throws Exception
     */
    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public ModelAndView getItemDetails(@RequestParam String i, @RequestParam String v, HttpSession session) throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/entityDetails");
        
        List<programOrgHierarchy> entities = orghierarchymanager.getProgramOrgHierarchy((Integer) session.getAttribute("selprogramId"));
        mav.addObject("entities", entities);
        
        /* Decrypt the url */
        decryptObject decrypt = new decryptObject();
        
        Object obj = decrypt.decryptObject(i, v);
        
        String[] result = obj.toString().split((","));
        
        int entityItemId = Integer.parseInt(result[0].substring(4));
        
        programOrgHierarchyDetails entityItemDetails = orghierarchymanager.getProgramHierarchyItemDetails(entityItemId);
        mav.addObject("hierarchyDetails",entityItemDetails);
        mav.addObject("selEntity", entityItemDetails.getProgramHierarchyId());
        
        /* Get the available entitie to associate with */
        programOrgHierarchy entityDetails = orghierarchymanager.getOrgHierarchyById(entityItemDetails.getProgramHierarchyId());
        programOrgHierarchy parentEntity = orghierarchymanager.getProgramOrgHierarchyBydspPos(entityDetails.getDspPos()-1, (Integer) session.getAttribute("selprogramId"));
        List<programOrgHierarchyDetails> entityItems = orghierarchymanager.getProgramHierarchyItems(parentEntity.getId());
        
        List<programOrgHierarchyAssoc> associatedItems = orghierarchymanager.getAssociatedItems(entityItemId);
        
        if(entityItems.size() > 0 && associatedItems.size() > 0) {
            
            for(programOrgHierarchyDetails entityItem : entityItems) {
                
                for(programOrgHierarchyAssoc associatedItem : associatedItems) {
                    
                    if(entityItem.getId() == associatedItem.getAssociatedWith()) {
                        entityItem.setIsAssociated(true);
                    }
                }
            }
        }
        
        mav.addObject("entityItems", entityItems);
        
        //Get a list of states
        USStateList stateList = new USStateList();

        //Get the object that will hold the states
        mav.addObject("stateList", stateList.getStates());
        
        /* Get a lit of available activity codes */
        List<activityCodes> availActivityCodes = activitycodemanager.getActivityCodesByProgram((Integer) session.getAttribute("selprogramId"));
        
        
        
        mav.addObject("availActivityCodes", availActivityCodes);
        
        return mav;

    }
    
    
    /**
     * The '/saveEntityItem' POST request will handle submitting the hierarchyform.
     *
     * @param admindetails    The object containing the system administrator form fields
     * @param result        The validation result
     * @param redirectAttr	The variable that will hold values that can be read after the redirect
     *
     * @return	Will return the system administrators list page on "Save" Will return the system administrator form page on error
     
     * @throws Exception
     */
    @RequestMapping(value = "/details", method = RequestMethod.POST)
    public ModelAndView saveItemDetails(@Valid @ModelAttribute(value = "hierarchyDetails") programOrgHierarchyDetails entityItemDetails, BindingResult result, 
            RedirectAttributes redirectAttr, 
            @RequestParam String action, 
            @RequestParam String i, 
            @RequestParam String v, 
            @RequestParam(value = "selActivityCodes", required = false) List<Integer> selActivityCodes,
            HttpSession session,
            HttpServletResponse response) throws Exception {
        
        /* Decrypt the url */
        decryptObject decrypt = new decryptObject();
        
        Object obj = decrypt.decryptObject(i, v);
        
        String[] resultObj = obj.toString().split((","));
        
        int itemId = Integer.parseInt(resultObj[0].substring(4));
        
        entityItemDetails.setId(itemId);
        

        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/entityDetails");
            
            return mav;
        }
       
        orghierarchymanager.saveOrgHierarchyItem(entityItemDetails);
        
        if(selActivityCodes != null && !selActivityCodes.isEmpty()) {
            activitycodemanager.saveActivityCodesForEntity(selActivityCodes, itemId);
        }

        if (action.equals("save")) {
            redirectAttr.addFlashAttribute("savedStatus", "updated");
            ModelAndView mav = new ModelAndView(new RedirectView("/programAdmin/entity/details?i="+URLEncoder.encode(i,"UTF-8")+"&v="+URLEncoder.encode(v,"UTF-8")));
            return mav;
        } else {
            ModelAndView mav = new ModelAndView(new RedirectView("/programAdmin/entity?i="+entityItemDetails.getProgramHierarchyId()+"&msg=updated"));
            return mav;
        }
        
    }
    
    /**
     * The 'associateEntity' POST request will submit the association between the selected entity item
     * and the selected entity.
     * 
     * @parm isChecked  The boolean value to determine if the association should be created or removed.
     * @param entityId   The id of the clicked hierarchy
     * @param itemId    The id of the clicked hierarchy item
     * @param session
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/associateEntity", method = RequestMethod.POST)
    @ResponseBody public Integer associateEntity(@RequestParam(value = "isChecked" , required = true) Boolean isChecked, @RequestParam(value = "entityId", required = true) Integer entityId, @RequestParam(value = "itemId", required = true) Integer itemId) throws Exception {
        
        if(isChecked) {
            programOrgHierarchyAssoc newAssoc = new programOrgHierarchyAssoc();
            newAssoc.setAssociatedWith(entityId);
            newAssoc.setProgramHierarchyId(itemId);

            orghierarchymanager.saveOrgHierarchyAssociation(newAssoc);
        }
        else {
            orghierarchymanager.removeOrgHierarchyAssociation(itemId, entityId);
        }
       
        return 1;
    }
}
