/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.controller;

import com.bowlink.rr.model.User;
import com.bowlink.rr.model.programServiceCategories;
import com.bowlink.rr.model.programServiceCategoryAssoc;
import com.bowlink.rr.model.programServices;
import com.bowlink.rr.security.decryptObject;
import com.bowlink.rr.security.encryptObject;
import com.bowlink.rr.service.serviceManager;
import com.bowlink.rr.service.programManager;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
@RequestMapping("/programAdmin/services")
public class servicesController {
    
    @Autowired
    programManager programmanager;
    
    @Autowired
    serviceManager servicemanager;
    
     private String topSecret = "What goes up but never comes down";
    
    /**
     * The '' request will serve up the page to list all available services for the program.
     *
     * @param request
     * @param response
     * 
     * @return	the program service list page view
     * @throws Exception
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView listServices(HttpSession session, RedirectAttributes redirectAttr) throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/services");
        
        /* Get the list of org hierarchy for the click program */
        List<programServices> services = servicemanager.getProgramServices((Integer) session.getAttribute("selprogramId"));
        
        encryptObject encrypt = new encryptObject();
        Map<String,String> map;
        for(programServices service : services) {
            
            //Encrypt the id to pass in the url
            map = new HashMap<String,String>();
            map.put("id",Integer.toString(service.getId()));
            map.put("topSecret",topSecret);
            
            String[] encrypted = encrypt.encryptObject(map);
            
            service.setEncryptedId(encrypted[0]);
            service.setEncryptedSecret(encrypted[1]);
           
        }
        
        mav.addObject("services", services);
        
        return mav;

    }
    
    /**
     * The '/newService' request will serve up the new service modal page.
     *
     * 
     * @return	the service detail form
     * @throws Exception
     */
    @RequestMapping(value = "/newService", method = RequestMethod.GET)
    public @ResponseBody ModelAndView newService(HttpSession session) throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/programAdmin/services/serviceForm");
        
        programServices serviceDetails = new programServices();
        serviceDetails.setProgramId((Integer) session.getAttribute("selprogramId"));
        mav.addObject("serviceDetails", serviceDetails);
        mav.addObject("modalTitle", "Add New Service");
        mav.addObject("btnValue", "Create");
        
        return mav;
    }
    
   /**
     * The '/create_service' POST request will handle submitting the new program service.
     *
     * @param serviceDetails    The object containing the service form fields
     * @param result          The validation result
     *
     * @return	Will send the program admin to the details of the new program service
     
     * @throws Exception
     */
    @RequestMapping(value = "/create_service", method = RequestMethod.POST)
    public @ResponseBody ModelAndView saveService(@Valid @ModelAttribute(value = "serviceDetails") programServices serviceDetails, BindingResult result, HttpSession session) throws Exception {
  
        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/programAdmin/services/serviceForm");
            mav.addObject("btnValue", "Create");
            mav.addObject("modalTitle", "Add New Service");
            return mav;
        }
        
        Integer serviceId = servicemanager.saveNewProgramService(serviceDetails);
        
        Map<String,String> map = new HashMap<String,String>();
        map.put("id",Integer.toString(serviceId));
        map.put("topSecret",topSecret);
        
        encryptObject encrypt = new encryptObject();

        String[] encrypted = encrypt.encryptObject(map);
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/programAdmin/services/serviceForm");
        mav.addObject("success", "serviceCreated");
        mav.addObject("encryptedURL", "?i="+encrypted[0]+"&v="+encrypted[1]);
        return mav;

    }
    
    /**
     * The '/details' request will serve up the service details page.
     *
     * @param serviceId    The id of the selected category service
     * 
     * @return	the service detail form
     * @throws Exception
     */
    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public ModelAndView serviceDetails(@RequestParam String i, @RequestParam String v, HttpSession session) throws Exception {
        
         /* Decrypt the url */
        decryptObject decrypt = new decryptObject();
        
        Object obj = decrypt.decryptObject(i, v);
        
        String[] result = obj.toString().split((","));
        
        int serviceId = Integer.parseInt(result[0].substring(4));

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/serviceDetails");
       
        programServices serviceDetails = servicemanager.getProgramServiceDetails(serviceId);
        mav.addObject("serviceDetails", serviceDetails);
        
        return mav;
    }
    
    /**
     * The '/categoryDetails' request will serve up the category details page.
     *
     * @param categoryId    The id of the selected category
     * 
     * @return	the service category detail form
     * @throws Exception
     */
    @RequestMapping(value = "/categoryDetails", method = RequestMethod.GET)
    public @ResponseBody ModelAndView categoryDetails(@RequestParam Integer categoryId, HttpSession session) throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/programAdmin/services/categoryForm");
        
        if(categoryId > 0) {
            programServiceCategories categoryDetails = servicemanager.getProgramServiceCategoryDetails(categoryId);
            mav.addObject("categoryDetails", categoryDetails);
            mav.addObject("modalTitle", "Edit Service Category");
            mav.addObject("btnValue", "Update");
        }
        else {
            programServiceCategories categoryDetails = new programServiceCategories();
            categoryDetails.setProgramId((Integer) session.getAttribute("selprogramId"));
            mav.addObject("categoryDetails", categoryDetails);
            mav.addObject("modalTitle", "Add New Service Category");
            mav.addObject("btnValue", "Create");
        }

        return mav;
    }
    
    /**
     * The '/saveserviceCategory' POST request will handle submitting the new program service.
     *
     * @param serviceDetails    The object containing the service form fields
     * @param result          The validation result
     *
     * @return	Will send the program admin to the details of the new program service
     
     * @throws Exception
     */
    @RequestMapping(value = "/saveserviceCategory", method = RequestMethod.POST)
    public @ResponseBody ModelAndView saveserviceCategory(@Valid @ModelAttribute(value = "categoryDetails") programServiceCategories categoryDetails, BindingResult result, HttpSession session) throws Exception {
  
        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/programAdmin/services/categoryForm");
            
            if(categoryDetails.getId() > 0) {
                mav.addObject("modalTitle", "Edit Service Category");
                mav.addObject("btnValue", "Update");
            }
            else {
                mav.addObject("modalTitle", "Add New Service Category");
                mav.addObject("btnValue", "Create");
            }
            
            return mav;
        }
        
        servicemanager.saveProgramServiceCategory(categoryDetails);
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/programAdmin/services/categoryForm");
        
        if(categoryDetails.getId() > 0) {
            mav.addObject("success", "categoryUpdated");
        }
        else {
            mav.addObject("success", "categoryCreated");
        }
        return mav;

    }
    
    
    /**
     * The '/categories' request will display the list of service categories assocaited to the program.
     *
     * @param request
     * @param response
     * 
     * @return	the program organization hierarchy list page view
     * @throws Exception
     */
    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public ModelAndView listCategories(HttpSession session) throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/categories");
        
        List<programServiceCategories> categories = servicemanager.getProgramServiceCategories((Integer) session.getAttribute("selprogramId"));
        mav.addObject("categories", categories);
        
        return mav;
    }
    
    /**
     * The 'getAssignedCategories' GET request will return a list of categories the services is assigned to.
     * 
     * @param i The encrypted serviceId
     * @param v The encrypted secret
     * @return  This function will return the assigned categories view.
     * @throws Exception 
     */
    @RequestMapping(value = "/getAssignedCategories.do", method = RequestMethod.GET)
    public @ResponseBody ModelAndView getAssignedCategories(@RequestParam String i, @RequestParam String v) throws Exception {
        
        /* Decrypt the url */
        int serviceId = decryptURLParam(i,v);
        
         /* Get associated categories */
        List<programServiceCategoryAssoc> categories = servicemanager.getProgramServiceCategoryAssoc(serviceId);
        
        for(programServiceCategoryAssoc category : categories) {
            programServiceCategories categoryDetails = servicemanager.getProgramServiceCategoryDetails(category.getServiceCategoryId());
            category.setCategoryName(categoryDetails.getCategoryName());
        }
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/programAdmin/services/assignedCategories");
        mav.addObject("categories", categories);
        
        return mav;
    }
    
    /**
     * The 'assignNewCategory' GET request will open the new assign category modal.
     * 
     * @param i The encrypted serviceId
     * @param v The encrypted secret
     * @return  This function will return the new associated programs view.
     * @throws Exception 
     */
    @RequestMapping(value = "/assignNewCategory.do", method = RequestMethod.GET)
    public @ResponseBody ModelAndView associateNewProgram(@RequestParam String i, @RequestParam String v, HttpSession session) throws Exception {
        
        /* Decrypt the url */
        int serviceId = decryptURLParam(i,v);
        
        /* Get available categories */
        List<programServiceCategories> categories = servicemanager.getAvailableProgramServiceCategories((Integer) session.getAttribute("selprogramId"), serviceId);
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/programAdmin/services/availableCategories");
        mav.addObject("categories", categories);
        
        mav.addObject("v", v);
        mav.addObject("serviceId", i);
        mav.addObject("encryptedURL", "?i="+i+"&v="+v);
        
        return mav;
        
    }
    
    /**
     * The 'saveAssignedServiceCategories' POST request will submit the selected service categories.
     * 
     * @param i The encrypted userId
     * @param v The encrypted secret
     * @param modules   The selected program modules.
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/saveAssignedServiceCategories.do", method = RequestMethod.POST)
    public @ResponseBody ModelAndView saveAssignedServiceCategories(@RequestParam String i, @RequestParam String v, @RequestParam(value = "category", required = false) List<Integer> categories, HttpSession session) throws Exception {
        
        /* Decrypt the url */
        int serviceId = decryptURLParam(i,v);
        
        /* Clear out current user modules */
        servicemanager.removeExistingCategories(serviceId);
        
        if(categories != null && !categories.isEmpty()) {
            for(Integer category : categories) {
                programServiceCategoryAssoc assignCategory = new programServiceCategoryAssoc();
                assignCategory.setServiceCategoryId(category);
                assignCategory.setServiceId(serviceId);
                
                servicemanager.saveAssignedCategories(assignCategory);
            }
        }
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/programAdmin/services/availableCategories");
        mav.addObject("encryptedURL", "?i="+i+"&v="+v);
        
        return mav;
    }
    
    /**
     * The 'removeAssociatedServiceCategory' POST request will remove the selected category from the service.
     * 
     * @param i The encrypted servicId
     * @param v The encrypted secret
     * @param modules   The selected service category.
     * @categoryId
     * @throws Exception 
     */
    @RequestMapping(value = "/removeAssociatedServiceCategory.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Integer removeUserProgram(@RequestParam String i, @RequestParam String v, @RequestParam Integer categoryId, HttpSession session) throws Exception {
        
        /* Decrypt the url */
        int serviceId = decryptURLParam(i,v);
        
        servicemanager.removeAssignedCategory(serviceId, categoryId);
        
        return 1;
    }
    
    /**
     * The 'decryptURLParam' will take the encryptd url parameters and return the userid as an integer.
     * 
     * @param i The encrypted userId
     * @param v The encrypted secret.
     * @return  This function will return the decrypted userid.
     * @throws Exception 
     */
    public int decryptURLParam(@RequestParam String i, @RequestParam String v) throws Exception {
        
        /* Decrypt the url */
        decryptObject decrypt = new decryptObject();
        
        Object obj = decrypt.decryptObject(URLDecoder.decode(i, "UTF-8"), URLDecoder.decode(v, "UTF-8"));
        
        String[] result = obj.toString().split((","));
        
        int userId = Integer.parseInt(result[0].substring(4));
        
        return userId;
    }
    
}
