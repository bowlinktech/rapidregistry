/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.controller;

import com.bowlink.rr.model.program;
import com.bowlink.rr.model.programEngagementSections;
import com.bowlink.rr.model.programEngagementSection_MCIAlgorithms;
import com.bowlink.rr.model.programEngagementSection_mciFields;
import com.bowlink.rr.model.programPatientFields;
import com.bowlink.rr.service.dataElementManager;
import com.bowlink.rr.service.masterClientIndexManager;
import com.bowlink.rr.service.programFormsManager;
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

/**
 *
 * @author chadmccue
 */
@Controller
@RequestMapping(value={"/sysAdmin/programs/{programName}/mci-algorithms"})
public class masterClientIndex {
    
    @Autowired
    programManager programmanager;
    
    @Autowired
    masterClientIndexManager mcimanager;
    
    @Autowired
    dataElementManager dataelementmanager;
    
    @Autowired
    programFormsManager programformsmanager;

    /**
     * The '/{programName}/mci-algorithms' GET request will display the program MCI Algorithms.
     *
     * @param programName	The {programName} will be the program name with spaces removed.
     *
     * @return	Will return the MCI Algorithms page.
     *
     * @throws Exception
     *
    */ 
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView programEngagementSection_MCIAlgorithms(HttpSession session) throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/mcialgorithms");
        mav.addObject("id", session.getAttribute("programId"));

        program programDetails = programmanager.getProgramById((Integer) session.getAttribute("programId"));
        mav.addObject("programDetails", programDetails);

        // get engagement sections
        List <programEngagementSections> engagementSectionList = programformsmanager.getEngagementSections((Integer) session.getAttribute("programId"));
        if (engagementSectionList.size() > 0) {
        	//we set the MCIAlgorithms that goes with that section
        	engagementSectionList = mcimanager.getMCIAlgorithms(engagementSectionList);
        	mav.addObject("programEngagementSections", engagementSectionList);
        }
        
        
        return mav;

    }
    
    /**
     * The '/algorithm.create' GET request will be used to create a new MCI Algorithm
     *
     * @return The blank MCI Algorithm page
     *
     * @Objects (1) An object that will hold the blank MCI Algorithm
     */
    @RequestMapping(value = "/algorithm.create", method = RequestMethod.GET)
    @ResponseBody public ModelAndView newMCIAlgorithmForm(HttpSession session) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/programs/mci/details");

        //Create a new blank provider.
        programEngagementSection_MCIAlgorithms mci = new programEngagementSection_MCIAlgorithms();
        mci.setProgramId((Integer) session.getAttribute("programId"));
        
        /* Get a list of available fields */
        List<programPatientFields> existingPatientFields = programformsmanager.getAllPatientFields((Integer) session.getAttribute("programId"));

        String fieldName;

        for (programPatientFields field : existingPatientFields) {
            //Get the field name by id
            fieldName = dataelementmanager.getfieldName(field.getFieldId());
            field.setFieldName(fieldName);

        }
        mav.addObject("availableFields", existingPatientFields);

        mav.addObject("btnValue", "Create");
        mav.addObject("mcidetails", mci);

        return mav;
    }
    
    
    /**
     * The '/create_mcialgorithm' POST request will handle submitting the new MCI Algorithm.
     *
     * @param mcidetails    The object containing the MCI Algorithm form fields
     * @param result        The validation result
     * @param redirectAttr	The variable that will hold values that can be read after the redirect
     *
     * @return	Will return the MCI Algorithm list page on "Save" Will return the MCI Algorithm form page on error
     *
     * @Objects	(1) The object containing all the information for the clicked org
     * @throws Exception
     */
    @RequestMapping(value = "/create_mcialgorithm", method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView createMCIAlgorithm(@Valid @ModelAttribute(value = "mcidetails") programEngagementSection_MCIAlgorithms mcidetails, BindingResult result,  @RequestParam(value = "fieldIds", required = true) List<Integer> fieldIds, @RequestParam(value = "fieldAction", required = true) List<String> fieldAction, HttpSession session) throws Exception {

        
        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/sysAdmin/programs/mci/details");
            /* Get a list of available fields */
            List<programPatientFields> existingPatientFields = programformsmanager.getAllPatientFields((Integer) session.getAttribute("programId"));

            String fieldName;

            for (programPatientFields field : existingPatientFields) {
                //Get the field name by id
                fieldName = dataelementmanager.getfieldName(field.getFieldId());
                field.setFieldName(fieldName);

            }
            mav.addObject("availableFields", existingPatientFields);
            mav.addObject("btnValue", "Create");
            return mav;
        }


        Integer mciId = mcimanager.createMCIAlgorithm(mcidetails);
        
        int i = 0;
        for(Integer fieldId : fieldIds) {
            programEngagementSection_mciFields newField = new programEngagementSection_mciFields();
            newField.setFieldId(fieldId);
            newField.setMciId(mciId);
            
            String selFieldAction = fieldAction.get(i);
            newField.setAction(selFieldAction);
            
            mcimanager.createMCIAlgorithmFields(newField);
            
            i+=1;
        }

        ModelAndView mav = new ModelAndView("/sysAdmin/programs/mci/details");
        mav.addObject("success", "algorithmCreated");
        return mav;
    }
    
   /**
     * The '/update_mcialgorithm' POST request will handle submitting the selected MCI Algorithm changes.
     *
     * @param mcidetails    The object containing the MCI Algorithm form fields
     * @param result        The validation result
     * @param redirectAttr	The variable that will hold values that can be read after the redirect
     *
     * @return	Will return the MCI Algorithm list page on "Save" Will return the MCI Algorithm form page on error
     *
     * @Objects	(1) The object containing all the information for the clicked org
     * @throws Exception
     */
    @RequestMapping(value = "/update_mcialgorithm", method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView updateMCIAlgorithm(@Valid @ModelAttribute(value = "mcidetails") programEngagementSection_MCIAlgorithms mcidetails, BindingResult result,  @RequestParam(value = "fieldIds", required = true) List<Integer> fieldIds, @RequestParam(value = "fieldAction", required = true) List<String> fieldAction, HttpSession session) throws Exception {

        
        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/sysAdmin/programs/mci/details");
            /* Get a list of available fields */
            List<programPatientFields> existingPatientFields = programformsmanager.getAllPatientFields((Integer) session.getAttribute("programId"));

            String fieldName;

            for (programPatientFields field : existingPatientFields) {
                //Get the field name by id
                fieldName = dataelementmanager.getfieldName(field.getFieldId());
                field.setFieldName(fieldName);

            }
            mav.addObject("availableFields", existingPatientFields);
            mav.addObject("btnValue", "Create");
            return mav;
        }


        mcimanager.updateMCIAlgorithm(mcidetails);
        
        int i = 0;
        for(Integer fieldId : fieldIds) {
            programEngagementSection_mciFields newField = new programEngagementSection_mciFields();
            newField.setFieldId(fieldId);
            newField.setMciId(mcidetails.getId());
            
            String selFieldAction = fieldAction.get(i);
            newField.setAction(selFieldAction);
            
            mcimanager.createMCIAlgorithmFields(newField);
            
            i+=1;
        }

        ModelAndView mav = new ModelAndView("/sysAdmin/programs/mci/details");
        mav.addObject("success", "algorithmUpdated");
        return mav;
    }

    /**
     * The '/algorithm.edit' GET request will be used to create a new MCI Algorithm
     *
     * @return The blank MCI Algorithm page
     *
     * @Objects (1) An object that will hold the blank MCI Algorithm
     */
    @RequestMapping(value = "/algorithm.edit", method = RequestMethod.GET)
    @ResponseBody public ModelAndView editMCIAlgorithmForm(@RequestParam Integer mciId, HttpSession session) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/programs/mci/details");

        //Create a new blank provider.
        programEngagementSection_MCIAlgorithms mci = mcimanager.getMCIAlgorithm(mciId);
        
        /* Get a list of available fields */
        List<programPatientFields> existingPatientFields = programformsmanager.getAllPatientFields((Integer) session.getAttribute("programId"));

        String fieldName;

        for (programPatientFields field : existingPatientFields) {
            //Get the field name by id
            fieldName = dataelementmanager.getfieldName(field.getFieldId());
            field.setFieldName(fieldName);
        }
        mav.addObject("availableFields", existingPatientFields);
        
        List<programEngagementSection_mciFields> fields = mcimanager.getMCIAlgorithmFields(mciId);
        
        for(programEngagementSection_mciFields field : fields) {
            //Get the field name by id
            String selfieldName = dataelementmanager.getfieldName(field.getFieldId());
            field.setFieldName(selfieldName);
        }
        mav.addObject("selFields", fields);

        mav.addObject("btnValue", "Update");
        mav.addObject("mcidetails", mci);

        return mav;
    }
    
    /**
     * The '/remvoeAlgorithmField.do' POST request will remove the selected field for the passed in
     * MCI Algorithm.
     * 
     * @param algorithmFieldId  The id of the field to be removed.
     * 
     * @return Will return a 1 when the field is successfully removed.
     */
    @RequestMapping(value = "/removeAlgorithmField.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Integer removeAlgorithmField(@RequestParam Integer algorithmfieldId, HttpSession session) throws Exception {
        
        mcimanager.removeAlgorithmField(algorithmfieldId);
        
        return 1;
    }
    
    /**
     * The '/remvoeAlgorithm.do' POST request will remove the selected field for the passed in
     * MCI Algorithm.
     * 
     * @param algorithmId  The id of the algorithm to be removed.
     * 
     * @return Will return a 1 when the field is successfully removed.
     */
    @RequestMapping(value = "/removeAlgorithm.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Integer removeAlgorithm(@RequestParam Integer algorithmId, HttpSession session) throws Exception {
        
        mcimanager.removeAlgorithm(algorithmId);
        
        return 1;
    }
}
