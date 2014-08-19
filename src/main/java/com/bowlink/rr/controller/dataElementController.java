/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bowlink.rr.controller;

import com.bowlink.rr.model.crosswalks;
import com.bowlink.rr.model.demoDataElements;
import com.bowlink.rr.model.healthDataElements;
import com.bowlink.rr.service.dataElementManager;
import com.bowlink.rr.service.programManager;
import java.util.List;
import javax.servlet.http.HttpSession;
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
import org.springframework.web.servlet.view.RedirectView;

/**
 *
 * @author chadmccue
 */
@Controller
@RequestMapping("/sysAdmin/data-elements")
public class dataElementController {
    
    @Autowired
    dataElementManager dataelementmanager;
    
    @Autowired
    programManager programmanager;
    
    /**
     * The '/demo-fields' GET request will display the demographic field list page.
     *
     *
     * @return	Will return the data element list page.
     *
     * @throws Exception
     *
     */
    @RequestMapping(value = "/demo-fields", method = RequestMethod.GET)
    public ModelAndView demoDataElements(HttpSession session) throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/demoFields");
       
        /**
         * Get a list of all available demographic fields *
         */
        List<demoDataElements> dataElements = dataelementmanager.getDemoDataElements();
        mav.addObject("availableFields", dataElements);


        return mav;

    }
    
    
    /**
     * The '/health-fields' GET request will display the health field list page.
     *
     *
     * @return	Will return the data element list page.
     *
     * @throws Exception
     *
     */
    @RequestMapping(value = "/health-fields", method = RequestMethod.GET)
    public ModelAndView healthDataElements(HttpSession session) throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/healthFields");
       
        /**
         * Get a list of all available demographic fields *
         */
        List<healthDataElements> dataElements = dataelementmanager.getHealthDataElements();
        mav.addObject("availableFields", dataElements);


        return mav;

    }
    
    
    /**
     * The '/getCrosswalks.do' function will return all the available crosswalks.
     *
     * @Return list of crosswalks
     */
    @RequestMapping(value = "/getCrosswalks.do", method = RequestMethod.GET)
    public @ResponseBody
    ModelAndView getCrosswalks(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "maxCrosswalks", required = false) Integer maxCrosswalks, HttpSession session) throws Exception {
       
        if (page == null) {
            page = 1;
        }
        
        int programId = 0;

        if (null != session.getAttribute("programId")) {
            programId = (Integer) session.getAttribute("programId");
        }
        
        if(maxCrosswalks == null) {
            maxCrosswalks = 4;
        }

        double maxCrosswalkVal = maxCrosswalks;
       
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/dataElements/crosswalks");

        //Need to return a list of crosswalks
        List<crosswalks> crosswalks = dataelementmanager.getCrosswalks(page, maxCrosswalks, programId);
        mav.addObject("availableCrosswalks", crosswalks);

        //Find out the total number of crosswalks
        double totalCrosswalks = dataelementmanager.findTotalCrosswalks(programId);
        
        Integer totalPages = (int) Math.ceil((double)totalCrosswalks / maxCrosswalkVal);
        mav.addObject("totalPages", totalPages);
        mav.addObject("currentPage", page);

        return mav;

    }
    
    /**
     * The '/newCrosswalk' GET request will be used to return a blank crosswalk form.
     *
     *
     * @return	The crosswalk details page
     *
     * @Objects	(1) An object that will hold all the details of the clicked crosswalk
     *
     */
    @RequestMapping(value = "/newCrosswalk", method = RequestMethod.GET)
    public @ResponseBody
    ModelAndView newCrosswalk(HttpSession session) throws Exception {

        int programId = 0;

        if (null != session.getAttribute("programId")) {
            programId = (Integer) session.getAttribute("programId");
        }

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/dataElements/crosswalkDetails");

        crosswalks crosswalkDetails = new crosswalks();
        mav.addObject("crosswalkDetails", crosswalkDetails);
        mav.addObject("btnValue", "Create");

        //Get the list of available file delimiters
        @SuppressWarnings("rawtypes")
        List delimiters = dataelementmanager.getDelimiters();
        mav.addObject("delimiters", delimiters);

        return mav;
    }

    /**
     * The '/createCrosswalk' function will be used to create a new crosswalk
     *
     * @Return The function will either return the crosswalk form on error or redirect to the data translation page.
     */
    @RequestMapping(value = "/createCrosswalk", method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView createCrosswalk(@ModelAttribute(value = "crosswalkDetails") crosswalks crosswalkDetails, @RequestParam String frompage, BindingResult result, RedirectAttributes redirectAttr, HttpSession session) throws Exception {

        int programId = 0;

        if (null != session.getAttribute("programId")) {
            programId = (Integer) session.getAttribute("programId");
        }
        
        crosswalkDetails.setProgramId(programId);
        int lastId = dataelementmanager.createCrosswalk(crosswalkDetails);

        if (lastId == 0) {
            redirectAttr.addFlashAttribute("savedStatus", "error");
        } else {
            redirectAttr.addFlashAttribute("savedStatus", "created");
        }

        //if programId > 0 then need to send back to the configurations page
        //otherwise send back to the message type libarary translation page.
        if (programId > 0) {
            String programName = programmanager.getProgramById(programId).getProgramName().replace(" ", "-").toLowerCase();
            String redirectPage = "demo-data-elements";
            if("health".equals(frompage)) {
                redirectPage = "health-data-elements";
            }
            ModelAndView mav = new ModelAndView(new RedirectView("../programs/"+programName+"/"+redirectPage));
            return mav;
        } else {
            ModelAndView mav = new ModelAndView(new RedirectView("translations"));
            return mav;
        }
    }

    /**
     *
     */
    @RequestMapping(value = "/checkCrosswalkName.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Long checkCrosswalkName(@RequestParam(value = "name", required = true) String name, HttpSession session) throws Exception {

        int programId = 0;

        if (null != session.getAttribute("programId")) {
            programId = (Integer) session.getAttribute("programId");
        }

        Long nameExists = (Long) dataelementmanager.checkCrosswalkName(name, programId);

        return nameExists;

    }

    /**
     * The '/viewCrosswalk{params}' function will return the details of the selected crosswalk. The results will be displayed in the overlay.
     *
     * @Param	i	This will hold the id of the selected crosswalk
     *
     * @Return	This function will return the crosswalk details view.
     */
    @RequestMapping(value = "/viewCrosswalk{params}", method = RequestMethod.GET)
    public @ResponseBody
    ModelAndView viewCrosswalk(@RequestParam(value = "i", required = true) Integer cwId) throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/dataElements/crosswalkDetails");

        //Get the details of the selected crosswalk
        crosswalks crosswalkDetails = dataelementmanager.getCrosswalk(cwId);
        mav.addObject("crosswalkDetails", crosswalkDetails);

        //Get the data associated with the selected crosswalk
        @SuppressWarnings("rawtypes")
        List crosswalkData = dataelementmanager.getCrosswalkData(cwId);
        mav.addObject("crosswalkData", crosswalkData);

        //Get the list of available file delimiters
        @SuppressWarnings("rawtypes")
        List delimiters = dataelementmanager.getDelimiters();
        mav.addObject("delimiters", delimiters);

        return mav;
    }
    
    /**
     * The '/addNewDemoField' GET request will return the new field module
     * 
     *  
     * @return  The function returns the new field module with a empty messageTypeFormFields
     *          object
     */
    @RequestMapping(value = "/addNewDemoField", method = RequestMethod.GET)
    public @ResponseBody ModelAndView addNewDemoField() throws Exception {
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/dataElements/details");
        
        demoDataElements newdemoField = new demoDataElements();
        mav.addObject("dataElementFormFields", newdemoField);
       

        //Get the list of available information tables
        @SuppressWarnings("rawtypes")
        List infoTables = dataelementmanager.getInformationTables();
        mav.addObject("infoTables", infoTables);

        return mav;
    }
    
    /**
     * The '/addNewDemoField' POST request will submit the new field module
     * 
     * @param dataElementFormFields      The object containing the new field
     * 
     * @return  The function will reload the mappings page showing the new field
     *          after the field has been successfully added.
     */    
    @RequestMapping(value = "/addNewDemoField", method = RequestMethod.POST)
    public ModelAndView submitNewDemoField(@ModelAttribute(value = "dataElementFormFields") demoDataElements dataElementFormFields, RedirectAttributes redirectAttr) throws Exception {
        
        dataelementmanager.saveDemoField(dataElementFormFields);
       
        redirectAttr.addFlashAttribute("savedStatus", "fieldcreated");
        
        ModelAndView mav = new ModelAndView(new RedirectView("demo-fields"));
        return mav;
    }
    
    /**
     * The '/editDemoField' GET request will return the selected field module
     * 
     *  
     * @return  The function returns the new field module with a empty messageTypeFormFields
     *          object
     */
    @RequestMapping(value = "/editDemoField", method = RequestMethod.GET)
    public @ResponseBody ModelAndView editDemoField(@RequestParam Integer fieldId) throws Exception {
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/dataElements/details");
         
        
        demoDataElements fieldDetails = dataelementmanager.getDemoFieldDetails(fieldId);
        mav.addObject("dataElementFormFields", fieldDetails);
       

        //Get the list of available information tables
        @SuppressWarnings("rawtypes")
        List infoTables = dataelementmanager.getInformationTables();
        mav.addObject("infoTables", infoTables);

        return mav;
    }
    
    /**
     * The '/editDemoField' POST request will submit the deom field changes
     * 
     * @param dataElementFormFields      The object containing the new field
     * 
     * @return  The function will reload the mappings page showing the new field
     *          after the field has been successfully added.
     */    
    @RequestMapping(value = "/editDemoField", method = RequestMethod.POST)
    public ModelAndView submitDemoFieldChanges(@ModelAttribute(value = "dataElementFormFields") demoDataElements dataElementFormFields, RedirectAttributes redirectAttr) throws Exception {
        
        dataelementmanager.saveDemoField(dataElementFormFields);
       
        redirectAttr.addFlashAttribute("savedStatus", "fieldupdated");
       
        ModelAndView mav = new ModelAndView(new RedirectView("demo-fields"));
        return mav;
    }
    
    /**
     * The '/addNewHealthField' GET request will return the new field module
     */
    @RequestMapping(value = "/addNewHealthField", method = RequestMethod.GET)
    public @ResponseBody ModelAndView addNewHealthField() throws Exception {
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/dataElements/details");
         mav.addObject("fieldType", "demo");
        
        healthDataElements newhealthField = new healthDataElements();
        mav.addObject("dataElementFormFields", newhealthField);
       

        //Get the list of available information tables
        @SuppressWarnings("rawtypes")
        List infoTables = dataelementmanager.getInformationTables();
        mav.addObject("infoTables", infoTables);

        return mav;
    }
    
    /**
     * The '/addNewHealthField' POST request will submit the new field module
     * 
     * @param dataElementFormFields      The object containing the new field
     * 
     * @return  The function will reload the mappings page showing the new field
     *          after the field has been successfully added.
     */    
    @RequestMapping(value = "/addNewHealthField", method = RequestMethod.POST)
    public ModelAndView submitNewDemoField(@ModelAttribute(value = "dataElementFormFields") healthDataElements dataElementFormFields, RedirectAttributes redirectAttr) throws Exception {
        
        dataelementmanager.saveHealthField(dataElementFormFields);
        
        if(dataElementFormFields.getId() > 0) {
            redirectAttr.addFlashAttribute("savedStatus", "fieldupdated");
        }
        else {
            redirectAttr.addFlashAttribute("savedStatus", "fieldcreated");
        }
       
        ModelAndView mav = new ModelAndView(new RedirectView("health-fields"));
        return mav;
    }
    
    /**
     * The '/editHealthField' GET request will return the selected field module
     */
    @RequestMapping(value = "/editHealthField", method = RequestMethod.GET)
    public @ResponseBody ModelAndView editHealthField(@RequestParam Integer fieldId) throws Exception {
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/dataElements/details");
         
        
        healthDataElements fieldDetails = dataelementmanager.getHealthFieldDetails(fieldId);
        mav.addObject("dataElementFormFields", fieldDetails);
       

        //Get the list of available information tables
        @SuppressWarnings("rawtypes")
        List infoTables = dataelementmanager.getInformationTables();
        mav.addObject("infoTables", infoTables);

        return mav;
    }
    
    /**
     * The '/editHealthField' POST request will submit the deom field changes
     * 
     * @param dataElementFormFields      The object containing the new field
     * 
     * @return  The function will reload the mappings page showing the new field
     *          after the field has been successfully added.
     */    
    @RequestMapping(value = "/editHealthField", method = RequestMethod.POST)
    public ModelAndView submitHealthFieldChanges(@ModelAttribute(value = "dataElementFormFields") healthDataElements dataElementFormFields, RedirectAttributes redirectAttr) throws Exception {
        
        dataelementmanager.saveHealthField(dataElementFormFields);
       
        redirectAttr.addFlashAttribute("savedStatus", "fieldupdated");
       
        ModelAndView mav = new ModelAndView(new RedirectView("health-fields"));
        return mav;
    }
    
    /**
     * The '/getTableCols.do' GET request will return a list of columns for the passed in table name
     *
     * @param tableName
     *
     * @return The function will return a list of column names.
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/getTableCols.do", method = RequestMethod.GET)
    public @ResponseBody List getTableCols(@RequestParam(value = "tableName", required = true) String tableName) {

        List columns = dataelementmanager.getTableColumns(tableName);
        return columns;
    }
    
}
