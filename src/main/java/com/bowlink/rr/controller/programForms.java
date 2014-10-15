/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.controller;

import com.bowlink.rr.model.crosswalks;
import com.bowlink.rr.model.dataElements;
import com.bowlink.rr.model.program;
import com.bowlink.rr.model.programEngagementFields;
import com.bowlink.rr.model.programPatientFields;
import com.bowlink.rr.model.programPatientSections;
import com.bowlink.rr.service.programFormsManager;
import com.bowlink.rr.service.programManager;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/sysAdmin/programs/{programName}/forms")
public class programForms {
    
    @Autowired
    programManager programmanager;
    
    @Autowired
    programFormsManager programformsmanager;
    
    /**
     * The '/{programName}/patient-detail-sections' GET request will display the page that will list the patient detail sections to a program.
     *
     * @param programName	The {programName} will be the program name with spaces removed.
     *
     * @return	Will return the program modules page.
     *
     * @throws Exception
     *
     */
    @RequestMapping(value = "/{section}", method = RequestMethod.GET)
    public ModelAndView programPatientDetailSections(@PathVariable String section, HttpSession session) throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.addObject("id", session.getAttribute("programId"));

        program programDetails = programmanager.getProgramById((Integer) session.getAttribute("programId"));
        mav.addObject("programDetails", programDetails);
        
        /* Patient Form Sections */        
        if("patient-sections".equals(section)) {
            
           List<programPatientSections> patientSections = programformsmanager.getPatientSections((Integer) session.getAttribute("programId"));
           mav.addObject("sections", patientSections); 
           
           mav.addObject("pageTitle", "Patient Detail Sections");
           
           mav.setViewName("/patientformsections");
        }
        
        /* Engagement Form Sections */
        else if ("engagement-sections".equals(section)) {
           mav.addObject("pageTitle", "Engagement Sections");
           
           mav.setViewName("/engagementformsections");
        }

        mav.addObject("sectionName", section);
        return mav;

    }
    
    
    /**
     * The '/{programName}/{sectionId}/{sectionName}/fields' GET request will display the page that will list the fields 
     * for the selected section and program.
     *
     * @param programName	The {programName} will be the program name with spaces removed.
     * @param sectionid     The {sectionId} will be the id of the selected section
     * @param sectionName   The {sectionName} will be the section name with spaces removed.
     *
     * @return	Will return the program modules page.
     *
     * @throws Exception
     *
     */
    @RequestMapping(value = "/{programName}/{sectionId}/{sectionName}/fields", method = RequestMethod.GET)
    public ModelAndView programPatientSectionFields(@PathVariable String programName, @PathVariable Integer sectionId, @PathVariable String sectionName, HttpSession session) throws Exception {

        //Set the data translations array to get ready to hold data
        patientFields = new CopyOnWriteArrayList<programPatientFields>();

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/patientFields");
        mav.addObject("id", session.getAttribute("programId"));
        mav.addObject("sectionId", sectionId);

        program programDetails = programmanager.getProgramById((Integer) session.getAttribute("programId"));
        mav.addObject("programDetails", programDetails);
        
        programPatientSections sectionDetails = programmanager.getPatientSectionById(sectionId);
        mav.addObject("sectionDetails", sectionDetails);
        

        /**
         * Get a list of all available demographic fields *
         */
        List<dataElements> dataElements = dataelementmanager.getdataElements();
        mav.addObject("availableFields", dataElements);

        //Return a list of available crosswalks
        List<crosswalks> crosswalks = dataelementmanager.getCrosswalks(1, 0, (Integer) session.getAttribute("programId"));
        mav.addObject("crosswalks", crosswalks);

        //Return a list of validation types
        List validationTypes = dataelementmanager.getValidationTypes();
        mav.addObject("validationTypes", validationTypes);

        return mav;

    }

    /**
     * The '/getPatientFields.do' function will return the list of existing patient fields set up for the selected program and section.
     *
     * @Return list of data fields
     */
    @RequestMapping(value = "/getPatientFields.do", method = RequestMethod.GET)
    public @ResponseBody
    ModelAndView getDemographicFields(@RequestParam(value = "reload", required = true) boolean reload, HttpSession session, @RequestParam(value = "sectionId", required = true) Integer sectionId) throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/programs/dataElements/existingFields");

        /**
         * only get the saved translations if reload == 0 We only want to retrieve the saved ones on initial load
         */
        if (reload == false) {
            //Need to get a list of existing translations
            List<programPatientFields> patientFields = programmanager.getPatientFields((Integer) session.getAttribute("programId"), sectionId);

            String fieldName;
            String crosswalkName;
            String validationName;

            for (programPatientFields field : patientFields) {
                //Get the field name by id
                fieldName = dataelementmanager.getfieldName(field.getFieldId());
                field.setFieldName(fieldName);

                //Get the crosswalk name by id
                if (field.getCrosswalkId() != 0) {
                    crosswalkName = dataelementmanager.getCrosswalkName(field.getCrosswalkId());
                    field.setCwName(crosswalkName);
                }

                //Get the macro name by id
                if (field.getValidationId() > 0) {
                    validationName = dataelementmanager.getValidationName(field.getValidationId());
                    field.setValidationName(validationName);
                }

                patientFields.add(field);
            }
        }

        mav.addObject("existingFields", patientFields);

        return mav;

    }

    /**
     * The '/setPatientField' function will handle taking in a selected field, selected crosswalk and selected validation type and add it to an array of translations. This array will be used when the form is submitted to associate to the existing program.
     *
     * @param fieldId This will hold the id of the selected field
     * @param cw This will hold the id of the selected crosswalk
     * @param fieldText This will hold the text value of the selected field (used for display purposes)
     * @param CWText This will hold the text value of the selected crosswalk (used for display purposes)
     * @param validationId This will hold the id of the selected validation type
     * @param validationName This will hold the name of the selected validation type
     *
     * @Return	This function will return the existing translations view that will display the table of newly selected translations
     */
    @RequestMapping(value = "/setPatientField{params}", method = RequestMethod.GET)
    public @ResponseBody
    ModelAndView setDemographicField(
            @RequestParam(value = "fieldId", required = true) Integer fieldId, @RequestParam(value = "sectionId", required = true) Integer sectionId, @RequestParam(value = "fieldText", required = true) String fieldText,
            @RequestParam(value = "fieldDisplayName", required = true) String fieldDisplayName,
            @RequestParam(value = "cw", required = true) Integer cw, @RequestParam(value = "CWText", required = true) String cwText,
            @RequestParam(value = "validationId", required = true) Integer validationId, @RequestParam(value = "validationName", required = true) String validationName,
            @RequestParam(value = "requiredField", required = true) boolean requiredField, 
            @RequestParam(value = "dataGridColumn", required = true) boolean dataGridColumn, HttpSession session
    ) throws Exception {

        int dspPos = patientFields.size() + 1;

        if (cw == null) {
            cw = 0;
            cwText = null;
        }

        programPatientFields field = new programPatientFields();
        field.setProgramId((Integer) session.getAttribute("programId"));
        field.setSectionId(sectionId);
        field.setCrosswalkId(cw);
        field.setCwName(cwText);
        field.setFieldId(fieldId);
        field.setFieldName(fieldText);
        field.setFieldDisplayname(fieldDisplayName);
        field.setValidationId(validationId);
        field.setValidationName(validationName);
        field.setRequiredField(requiredField);
        field.setDspPos(dspPos);
        field.setDataGridColumn(dataGridColumn);

        patientFields.add(field);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/programs/dataElements/existingFields");
        mav.addObject("existingFields", patientFields);

        return mav;
    }


    /**
     * The 'removeField{params}' function will handle removing a field from demoFields array.
     *
     * @param fieldType This will hold either DEMO or HEALTH
     * @param	fieldId This will hold the field that is being removed
     * @param	processOrder This will hold the process order of the field to be removed so we remove the correct field number as the same field could be in the list with different crosswalks
     *
     * @Return	1	The function will simply return a 1 back to the ajax call
     */
    @RequestMapping(value = "/removeField{params}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Integer removeField(@RequestParam(value = "fieldType", required = true) String fieldType, @RequestParam(value = "fieldId", required = true) Integer fieldId, @RequestParam(value = "dspOrder", required = true) Integer dspOrder) throws Exception {

        if ("patient".equals(fieldType)) {
            Iterator<programPatientFields> it = patientFields.iterator();

            int currdspOrder;

            while (it.hasNext()) {
                programPatientFields field = it.next();
                if (field.getFieldId() == fieldId && field.getDspPos() == dspOrder) {
                    patientFields.remove(field);
                } else if (field.getDspPos() > dspOrder) {
                    currdspOrder = field.getDspPos();
                    field.setDspPos(currdspOrder - 1);
                }
            }
        } else {
            Iterator<programEngagementFields> it = engagementFields.iterator();

            int currdspOrder;

            while (it.hasNext()) {
                programEngagementFields field = it.next();
                if (field.getFieldId() == fieldId && field.getDspPos() == dspOrder) {
                    engagementFields.remove(field);
                } else if (field.getDspPos() > dspOrder) {
                    currdspOrder = field.getDspPos();
                    field.setDspPos(currdspOrder - 1);
                }
            }
        }

        return 1;
    }

    /**
     * The 'updateFieldDspOrder{params}' function will handle updating the field display position.
     *
     * @param fieldType This will hold either DEMO or HEALTH
     * @param	fieldId This will hold the field that is being removed
     * @param	processOrder This will hold the process order of the field to be removed so we remove the correct field number as the same field could be in the list with different crosswalks
     *
     * @Return	1	The function will simply return a 1 back to the ajax call
     */
    @RequestMapping(value = "/updateFieldDspOrder{params}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Integer updateTranslationProcessOrder(@RequestParam(value = "fieldType", required = true) String fieldType, @RequestParam(value = "currdspOrder", required = true) Integer currdspOrder, @RequestParam(value = "newdspOrder", required = true) Integer newdspOrder) throws Exception {

        if ("demo".equals(fieldType)) {
            Iterator<programPatientFields> it = patientFields.iterator();

            while (it.hasNext()) {
                programPatientFields field = it.next();
                if (field.getDspPos() == currdspOrder) {
                    field.setDspPos(newdspOrder);
                } else if (field.getDspPos() == newdspOrder) {
                    field.setDspPos(currdspOrder);
                }
            }
        } else {
            Iterator<programEngagementFields> it = engagementFields.iterator();

            while (it.hasNext()) {
                programEngagementFields field = it.next();
                if (field.getDspPos() == currdspOrder) {
                    field.setDspPos(newdspOrder);
                } else if (field.getDspPos() == newdspOrder) {
                    field.setDspPos(currdspOrder);
                }
            }
        }

        return 1;
    }

    /**
     * The '/saveProgramPatientFields' POST request will submit the selected fields and save it to the data base.
     *
     */
    @RequestMapping(value = "/saveProgramPatientFields", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Integer saveProgramPatientFields(@RequestParam(value = "sectionId", required = true) Integer sectionId, HttpSession session) throws Exception {

        //Delete all the data translations before creating
        //This will help with the jquery removing translations
        programmanager.deletePatientFields((Integer) session.getAttribute("programId"), sectionId);

        //Loop through the list of translations
        for (programPatientFields field : patientFields) {
            programmanager.savePatientFields(field);
        }

        return 1;
    }
    
}
