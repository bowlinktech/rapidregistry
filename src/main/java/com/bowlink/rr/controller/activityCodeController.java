package com.bowlink.rr.controller;


import com.bowlink.rr.model.activityCodeAssocCategories;
import com.bowlink.rr.model.activityCodeCategories;
import com.bowlink.rr.model.activityCodes;
import com.bowlink.rr.model.program;
import com.bowlink.rr.model.programActivityCodes;
import com.bowlink.rr.service.activityCodeManager;
import com.bowlink.rr.service.programManager;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author chadmccue
 */
@Controller
@RequestMapping(value={"/sysAdmin/activity-codes","/sysAdmin/programs/{programName}/activity-codes"})
public class activityCodeController {
    
    @Autowired
    programManager programmanager;
    
    @Autowired
    activityCodeManager activitycodemanager;
    
    /**
     * The '/categories' request will serve up the activity code list page.
     *
     * @param request
     * @param response
     * 
     * @return	the activity code list page view
     * @throws Exception
     */
    @RequestMapping(value = "categories", method = RequestMethod.GET)
    public ModelAndView listActivityCodeCategories(HttpServletRequest request, HttpServletResponse response, HttpSession session, RedirectAttributes redirectAttr) throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/activityCodeCategories");

        /* Get the list of programs in the system */
        List<activityCodeCategories> categories = activitycodemanager.getActivityCodeCategories();

        mav.addObject("categories", categories);

        return mav;
    }
    
     /**
     * The 'category.create' GET request will be used to create a new system activity code category
     *
     * @return The blank activity code category page
     *
     * @Objects (1) An object that will hold the blank activity code category
     */
    @RequestMapping(value = "/category.create", method = RequestMethod.GET)
    @ResponseBody public ModelAndView newActivityCodeCategoryForm(HttpSession session) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/activityCodes/categoryDetails");

        //Create a new blank provider.
        activityCodeCategories category = new activityCodeCategories();
       
        mav.addObject("btnValue", "Create");
        mav.addObject("categorydetails", category);

        return mav;
    }
    
    /**
     * The 'create_activityCodeCategory' POST request will handle submitting the new activity code category.
     *
     * @param categorydetails    The object containing the activity code category form fields
     * @param result        The validation result
     *
    * @throws Exception
     */
    @RequestMapping(value = "/create_activityCodeCategory", method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView createActivityCodeCategory(@Valid @ModelAttribute(value = "categorydetails") activityCodeCategories categorydetails, BindingResult result, HttpSession session) throws Exception {

        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/sysAdmin/activityCodes/categoryDetails");
            mav.addObject("btnValue", "Create");
            return mav;
        }

       activitycodemanager.createActivityCodeCategory(categorydetails);
        
        ModelAndView mav = new ModelAndView("/sysAdmin/activityCodes/categoryDetails");
        mav.addObject("success", "categoryCreated");
        return mav;
    }
    
    /**
     * The 'category.edit' GET request will be used to create a new system activity code category
     *
     * @return The blank activity code page
     *
     * @Objects (1) An object that will hold the blank activity code
     */
    @RequestMapping(value = "/category.edit", method = RequestMethod.GET)
    public @ResponseBody ModelAndView activityCodeCategoryForm(@RequestParam Integer categoryId, HttpSession session) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/activityCodes/categoryDetails");

        //Create a new blank provider.
        activityCodeCategories categorydetails = activitycodemanager.getActivityCodeCategoryById(categoryId);
       
        mav.addObject("btnValue", "Update");
        mav.addObject("categorydetails", categorydetails);

        return mav;
    }
    
    /**
     * The 'update_activityCodeCategory' POST request will handle submitting the activity code changes.
     *
     * @param mpidetails    The object containing the activity code form fields
     * @param result        The validation result
     * @param redirectAttr  The variable that will hold values that can be read after the redirect
     *
     *
     * @Objects	(1) The object containing all the information for the clicked org
     * @throws Exception
     */
    @RequestMapping(value = "/update_activityCodeCategory", method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView updateActivityCode(@Valid @ModelAttribute(value = "categorydetails") activityCodeCategories categorydetails, BindingResult result, HttpSession session) throws Exception {

        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/sysAdmin/activityCodes/categoryDetails");
            mav.addObject("btnValue", "Update");
            return mav;
        }

       activitycodemanager.updateActivityCodeCategory(categorydetails);
        
        ModelAndView mav = new ModelAndView("/sysAdmin/activityCodes/categoryDetails");
        mav.addObject("success", "categoryUpdated");
        return mav;
    }
    
    /**
     * The '/list' request will serve up the activity code list page.
     *
     * @param request
     * @param response
     * 
     * @return	the activity code list page view
     * @throws Exception
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ModelAndView listActivityCodes(HttpServletRequest request, HttpServletResponse response, HttpSession session, RedirectAttributes redirectAttr) throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/activityCodes");

        /* Get the list of programs in the system */
        List<activityCodes> activityCodes = activitycodemanager.getActivityCodes(0, 0);

        mav.addObject("activityCodes", activityCodes);

        return mav;

    }
    
    /**
     * The 'code.create' GET request will be used to create a new system activity code
     *
     * @return The blank activity code page
     *
     * @Objects (1) An object that will hold the blank activity code
     */
    @RequestMapping(value = "/code.create", method = RequestMethod.GET)
    @ResponseBody public ModelAndView newActivityCodeForm(HttpSession session) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/activityCodes/details");

        //Create a new blank provider.
        activityCodes code = new activityCodes();
       
        mav.addObject("btnValue", "Create");
        mav.addObject("codedetails", code);

        return mav;
    }
    
    /**
     * The 'create_activityCode' POST request will handle submitting the new activity code.
     *
     * @param mpidetails    The object containing the activity code form fields
     * @param result        The validation result
     * @param redirectAttr  The variable that will hold values that can be read after the redirect
     *
     *
     * @Objects	(1) The object containing all the information for the clicked org
     * @throws Exception
     */
    @RequestMapping(value = "/create_activityCode", method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView createActivityCode(@Valid @ModelAttribute(value = "codedetails") activityCodes codedetails, BindingResult result, HttpSession session) throws Exception {

        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/sysAdmin/activityCodes/details");
            mav.addObject("btnValue", "Create");
            return mav;
        }

       activitycodemanager.createActivityCode(codedetails);
        
        ModelAndView mav = new ModelAndView("/sysAdmin/activityCodes/details");
        mav.addObject("success", "codeCreated");
        return mav;
    }
    
    /**
     * The '/{codeId}/details' GET request will be used to create a new system activity code
     *
     * @return The blank activity code page
     *
     * @Objects (1) An object that will hold the blank activity code
     */
    @RequestMapping(value = "/{codeId}/details", method = RequestMethod.GET)
    public ModelAndView activityCodeDetails(@PathVariable Integer codeId, HttpSession session) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/activityCodeDetails");

        //Create a new blank provider.
        activityCodes codeDetails = activitycodemanager.getActivityCodeById(codeId);
       
        mav.addObject("codedetails", codeDetails);

        return mav;
    }
    
    /**
     * The '/{codeId}/details' POST request will be used to create a new system activity code
     *
     * @return The blank activity code page
     *
     * @Objects (1) An object that will hold the blank activity code
     */
    @RequestMapping(value = "/{codeId}/details", method = RequestMethod.POST)
    public ModelAndView submitActivityCodeUpdates(@Valid @ModelAttribute(value = "codedetails") activityCodes codedetails, @RequestParam String action, BindingResult result, 
            RedirectAttributes redirectAttr, HttpSession session) throws Exception {
        
        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/activityCodeDetails");
            return mav;
        }

       activitycodemanager.updateActivityCode(codedetails);
       
       if (action.equals("save")) {
           redirectAttr.addFlashAttribute("savedStatus", "updated");
            ModelAndView mav = new ModelAndView(new RedirectView("/sysAdmin/activity-codes/"+codedetails.getId()+"/details"));
            return mav;
        } else {
            ModelAndView mav = new ModelAndView(new RedirectView("/sysAdmin/activity-codes/list"));
            return mav;
        }
    }
    
    
    /**
       * 
       * @param codeId
       * @param session
       * @return
       * @throws Exception 
    */
    @RequestMapping(value = "/getAssocCategories", method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView getAssocCategories(@RequestParam Integer codeId, HttpSession session) throws Exception {
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/activityCodes/categories");
        
        /* Check to see associated categories */
        List<activityCodeAssocCategories> assocCategories = activitycodemanager.getSelActivityCodeCategories(codeId);
        
        if(assocCategories != null && assocCategories.size() > 0) {
            List<activityCodeCategories> categories = new ArrayList<activityCodeCategories>();
            
            for(activityCodeAssocCategories assoc : assocCategories) {
                activityCodeCategories catDetails = activitycodemanager.getActivityCodeCategoryById(assoc.getCategoryId());
                categories.add(catDetails);
            }
            
            mav.addObject("categories", categories);
        }
        
        return mav;
    }
    
    /**
     * The 'getAvailableCategories' GET request will return the list of available categories not already associated
     * to the selected activity code.
     * 
     * @param codeId    The selected activity code Id
     * @param session
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/getAvailableCategories", method = RequestMethod.GET)
    @ResponseBody public ModelAndView getAvailableCategories(@RequestParam Integer codeId, HttpSession session) throws Exception {
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/activityCodes/categoryAssocForm");
        
        /* Get the list of programs in the system */
        List<activityCodeCategories> availCategories = activitycodemanager.getActivityCodeCategories();

        /* Check to see associated categories */
        List<activityCodeAssocCategories> assocCategories = activitycodemanager.getSelActivityCodeCategories(codeId);
        
        if(assocCategories != null && assocCategories.size() > 0) {
            List<activityCodeCategories> categories = new ArrayList<activityCodeCategories>();
            
            for(activityCodeCategories category : availCategories) {
                boolean found = false;
                
                for(activityCodeAssocCategories assoc : assocCategories) {
                    if(assoc.getCategoryId() == category.getId()) {
                        found = true;
                    }
                }
                
                if(found == false) {
                    categories.add(category);
                }
            }
            
            mav.addObject("categories", categories);
        }
        else {
             mav.addObject("categories", availCategories);
        }
        
        return mav;
        
    }
    
    /**
     * The 'removeCategoryAssoc' POST request will remove the selected category from the activity code.
     * 
     * @param id    The selected association id
     * @param session
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/removeCategoryAssoc", method = RequestMethod.POST)
    @ResponseBody public Integer removeCategoryAssoc(@RequestParam Integer id, HttpSession session) throws Exception {
        
        activitycodemanager.removeCategoryAssoc(id);
        
        return 1;
    }
    
    /**
     * The 'saveCategoryAssocItem' POST request will save the selected item for the selected provider.
     */
    @RequestMapping(value = "/saveCategoryAssocItem", method = RequestMethod.POST)
    @ResponseBody public ModelAndView saveCategoryAssocItem(@RequestParam Integer codeId, @RequestParam List<String> categories, HttpSession session) throws Exception {
        
        ModelAndView mav = new ModelAndView("/sysAdmin/activityCodes/categoryAssocForm");
        
        if(categories != null && !categories.isEmpty()) {
            
            for(String category : categories) {
                if(category != "null") {
                    activityCodeAssocCategories newAssoc = new activityCodeAssocCategories();
                    newAssoc.setActivityCodeId(codeId);
                    newAssoc.setCategoryId(Integer.parseInt(category.replace("'", "")));

                    activitycodemanager.saveActivityCodeCategoryAssoc(newAssoc);
                }
            }
        }
       
        mav.addObject("success", "itemAssociated");
        
        return mav;
        
    }

    
    /**
     * The '/{programName}/activity-codes' GET request will display the program activity code page.
     *
     * @param programName	The {programName} will be the program name with spaces removed.
     *
     * @return	Will return the program details page.
     *
     * @throws Exception
     *
    */ 
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView programActivityCodes(@RequestParam(value = "c", required = false) Integer c, HttpSession session) throws Exception {
        
        if(c == null) {
            c = 0;
        }

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/programActivityCodes");
        mav.addObject("id", session.getAttribute("programId"));

        program programDetails = programmanager.getProgramById((Integer) session.getAttribute("programId"));
        mav.addObject("programDetails", programDetails);

        List<activityCodes> activityCodes = activitycodemanager.getActivityCodes(0, c);

        for (activityCodes code : activityCodes) {

            boolean codeBeingUsed = activitycodemanager.getActivityCodesByProgram((Integer) session.getAttribute("programId"), code.getId());

            code.setSelected(codeBeingUsed);

        }
        
        /* Get the list of programs in the system */
        List<activityCodeCategories> availCategories = activitycodemanager.getActivityCodeCategories();


        mav.addObject("availactivityCodes", activityCodes);
        mav.addObject("availCategories", availCategories);
        mav.addObject("selCategory", c);

        return mav;

    }

    /**
     * The '/{programName}/activity-codes' POST request will display the program activity code page.
     *
     * @param programName	The {programName} will be the program name with spaces removed.
     * @param action
     * @param activityCodeList
     * @param redirectAttr
     * @param session
     * @return
     * @throws Exception
    */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ModelAndView saveProgramActivityCodes(@RequestParam String action, @RequestParam(value = "activityCodeList", required = false) List<Integer> activityCodeList, RedirectAttributes redirectAttr, HttpSession session) throws Exception {

        if (activityCodeList == null) {
            activitycodemanager.removeProgramActivityCodes((Integer) session.getAttribute("programId"));
        } else {
            activitycodemanager.removeProgramActivityCodes((Integer) session.getAttribute("programId"));

            for (Integer code : activityCodeList) {
                programActivityCodes newCodeAssoc = new programActivityCodes();
                newCodeAssoc.setCodeId(code);
                newCodeAssoc.setProgramId((Integer) session.getAttribute("programId"));

                activitycodemanager.saveProgramActivityCode(newCodeAssoc);
            }

        }

        redirectAttr.addFlashAttribute("savedStatus", "codesupdated");

        if (action.equals("save")) {
            ModelAndView mav = new ModelAndView(new RedirectView("activity-codes"));
            return mav;
        } else {
            ModelAndView mav = new ModelAndView(new RedirectView("../../programs"));
            return mav;
        }

    }
    
}
