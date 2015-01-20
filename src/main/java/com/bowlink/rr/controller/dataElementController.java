/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bowlink.rr.controller;

import com.bowlink.rr.model.crosswalks;
import com.bowlink.rr.model.dataElements;
import com.bowlink.rr.service.dataElementManager;
import com.bowlink.rr.service.programManager;
import java.util.List;
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
     * The '/' GET request will display the data element (fields) list page.
     *
     *
     * @return	Will return the data element list page.
     *
     * @throws Exception
     *
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ModelAndView dataElements(HttpSession session) throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/dataElements");
       
        /**
         * Get a list of all available demographic fields *
         */
        List<dataElements> dataElements = dataelementmanager.getdataElements();
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
    ModelAndView newCrosswalk(HttpSession session, @RequestParam String frompage) throws Exception {

        int programId = 0;

        if (null != session.getAttribute("programId")) {
            programId = (Integer) session.getAttribute("programId");
        }

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/dataElements/crosswalkDetails");
        mav.addObject("frompage", frompage);

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
            ModelAndView mav = new ModelAndView(new RedirectView("../programs/"+programName+"/forms/"+frompage+"/fields?s=2"));
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
     * The '/fieldForm' GET request will return the new field module
     * 
     *  
     * @return  The function returns the new field module with a empty messageTypeFormFields
     *          object
     */
    @RequestMapping(value = "/fieldForm", method = RequestMethod.GET)
    public @ResponseBody ModelAndView fieldForm(@RequestParam Integer fieldId) throws Exception {
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/dataElements/details");
        
        if(fieldId == 0) {
            dataElements fieldDetails = new dataElements();
            mav.addObject("dataElementFormFields", fieldDetails);
            mav.addObject("modalTitle", "Create New Field");
        }
        else {
            dataElements fieldDetails = dataelementmanager.getFieldDetails(fieldId);
            mav.addObject("dataElementFormFields", fieldDetails);
            mav.addObject("modalTitle", "Edit Field");
        }
        

        //Get the list of available information tables
        @SuppressWarnings("rawtypes")
        List infoTables = dataelementmanager.getInformationTables();
        mav.addObject("infoTables", infoTables);
        
        //Get the list of available file answer types
        @SuppressWarnings("rawtypes")
        List answerTypes = dataelementmanager.getAnswerTypes();
        mav.addObject("answerTypes", answerTypes);
        
        //Get the list of available look up tables
        @SuppressWarnings("rawtypes")
        List lookUpTables = dataelementmanager.getLookUpTables();
        mav.addObject("lookUpTables", lookUpTables);

        return mav;
    }
    
    /**
     * The '/submitFieldForm' POST request will submit the new field module
     * 
     * @param dataElementFormFields      The object containing the new field
     * 
     * @return  The function will reload the mappings page showing the new field
     *          after the field has been successfully added.
     */    
    @RequestMapping(value = "/submitFieldForm", method = RequestMethod.POST)
    public ModelAndView submitNewField(@Valid @ModelAttribute(value = "dataElementFormFields") dataElements dataElementFormFields, BindingResult result, RedirectAttributes redirectAttr) throws Exception {
        
        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/sysAdmin/dataElements/details");
            
            if(dataElementFormFields.getId() == 0) {
                mav.addObject("modalTitle", "Create New Field");
            }
            else {
                mav.addObject("modalTitle", "Edit Field");
            }
            //Get the list of available information tables
            @SuppressWarnings("rawtypes")
            List infoTables = dataelementmanager.getInformationTables();
            mav.addObject("infoTables", infoTables);
            
            //Get the list of available file answer types
            @SuppressWarnings("rawtypes")
            List answerTypes = dataelementmanager.getAnswerTypes();
            mav.addObject("answerTypes", answerTypes);
            
            //Get the list of available look up tables
            @SuppressWarnings("rawtypes")
            List lookUpTables = dataelementmanager.getLookUpTables();
            mav.addObject("lookUpTables", lookUpTables);
            
            return mav;
        }
        
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/dataElements/details");
        
        dataelementmanager.saveField(dataElementFormFields);
       
        if(dataElementFormFields.getId() > 0) {
            mav.addObject("success", "fieldUpdated");
        }
        else {
            mav.addObject("success", "fieldCreated");
        }
        
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
