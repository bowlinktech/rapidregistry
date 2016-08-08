/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.controller;

import com.bowlink.rr.model.crosswalks;
import com.bowlink.rr.model.customProgramFields;
import com.bowlink.rr.model.dataElements;
import com.bowlink.rr.model.program;
import com.bowlink.rr.model.programEngagementFieldValues;
import com.bowlink.rr.model.programEngagementFields;
import com.bowlink.rr.model.programEngagementSections;
import com.bowlink.rr.model.programPatientFieldValues;
import com.bowlink.rr.model.programPatientFields;
import com.bowlink.rr.model.programPatientSections;
import com.bowlink.rr.model.programProfileFieldValues;
import com.bowlink.rr.model.programProfileFields;
import com.bowlink.rr.service.dataElementManager;
import com.bowlink.rr.service.programFormsManager;
import com.bowlink.rr.service.programManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
    
    @Autowired
    dataElementManager dataelementmanager;
    
    private static List<programPatientFields> patientFields = null;

    private static List<programEngagementFields> engagementFields = null;
    
    private static List<programProfileFields> programProfileFields = null;
    
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
           
           List<programEngagementSections> engagementSections = programformsmanager.getEngagementSections((Integer) session.getAttribute("programId"));
           mav.addObject("sections", engagementSections); 
          
           mav.setViewName("/engagementformsections");
        }

        mav.addObject("sectionName", section);
        return mav;

    }
    
    /**
     * The '/sectionForm' request will display the form to create/edit a program patient detail section.
     *
     * @param session
     * @param redirectAttr
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/sectionForm", method = RequestMethod.GET)
    public @ResponseBody ModelAndView sectionForm(@RequestParam Integer id, @RequestParam String section, HttpSession session, RedirectAttributes redirectAttr) throws Exception {
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/programs/forms/sectionForm");
        
        if("patient-sections".equals(section)) {
            programPatientSections patientSection;
            
            if(id > 0) {
                Integer maxDspPos = programformsmanager.getPatientSections((Integer) session.getAttribute("programId")).size();
                patientSection = programformsmanager.getPatientSectionById(id);
                mav.addObject("modalTitle", "Modify Patient Detail Section");
                mav.addObject("maxDspPos", maxDspPos);
            }
            else {
                patientSection = new programPatientSections();
                patientSection.setProgramId((Integer) session.getAttribute("programId"));
                
                mav.addObject("modalTitle", "Create New Patient Detail Section");
                Integer maxDspPos = programformsmanager.getPatientSections((Integer) session.getAttribute("programId")).size()+1;
                mav.addObject("maxDspPos", maxDspPos);
            }
            
            mav.addObject("sectionDetails", patientSection);
        }
        
        /* Engagement Form Sections */
        else if ("engagement-sections".equals(section)) {
            programEngagementSections engagementSection;
            
            if(id > 0) {
                Integer maxDspPos = programformsmanager.getEngagementSections((Integer) session.getAttribute("programId")).size();
                engagementSection = programformsmanager.getEngagementSectionById(id);
                mav.addObject("modalTitle", "Modify Engagement Detail Section");
                mav.addObject("maxDspPos", maxDspPos);
            }
            else {
                engagementSection = new programEngagementSections();
                engagementSection.setProgramId((Integer) session.getAttribute("programId"));
                
                mav.addObject("modalTitle", "Create New Engagement Detail Section");
                
                Integer maxDspPos = programformsmanager.getEngagementSections((Integer) session.getAttribute("programId")).size()+1;
                mav.addObject("maxDspPos", maxDspPos);
            }
            
            mav.addObject("sectionDetails", engagementSection);
        }
        
        mav.addObject("programName", session.getAttribute("programName"));
        mav.addObject("section", section);

        return mav;
    }    
    
    /**
     * The '/savePatienSection' POST request will submit the patient detail section.
     *
     * @param program	The object holding the patient section form fields
     * @param result	The validation result
     * @param redirectAttr	The variable that will hold values that can be read after the redirect
     * @param action	The variable that holds which button was pressed
     *
     * @throws Exception
     */
    @RequestMapping(value = "/savePatienSection", method = RequestMethod.POST)
    public @ResponseBody ModelAndView savePatienSection(@Valid @ModelAttribute(value = "sectionDetails") programPatientSections sectionDetails, BindingResult result, @RequestParam String action, @RequestParam String section, @RequestParam Integer currdspPos, HttpSession session) throws Exception {

        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/sysAdmin/programs/forms/sectionForm");
            
            if(sectionDetails.getId() > 0) {
                Integer maxDspPos = programformsmanager.getPatientSections((Integer) session.getAttribute("programId")).size();
                mav.addObject("modalTitle", "Modify Patient Detail Section");
                mav.addObject("maxDspPos", maxDspPos);
            }
            else {
                mav.addObject("modalTitle", "Create New Patient Detail Section");
                mav.addObject("maxDspPos", 1);
            }
            mav.addObject("programName", session.getAttribute("programName"));
            mav.addObject("section", section);
            
            return mav;
        }
        
        /* Check to see if dspPos changed, if so need to update the old dsp Pos */
        if(sectionDetails.getDspPos() != currdspPos) {
            programPatientSections foundSection = programformsmanager.getPatientSectionBydspPos(sectionDetails.getDspPos(), (Integer) session.getAttribute("programId"));
            if(foundSection != null) {
                foundSection.setDspPos(currdspPos);
                programformsmanager.savePatientSection(foundSection);
            }
        }
        
        programformsmanager.savePatientSection(sectionDetails);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/programs/forms/sectionForm");
        mav.addObject("programName", session.getAttribute("programName"));
        mav.addObject("sectionName", section);
        mav.addObject("success", "sectionSaved");
        
        return mav;

    }
    
    
    /**
     * The '/saveEngagementSection' POST request will submit the engagement detail section.
     *
     * @param program	The object holding the engagement section form fields
     * @param result	The validation result
     * @param redirectAttr	The variable that will hold values that can be read after the redirect
     * @param action	The variable that holds which button was pressed
     *
     * @throws Exception
     */
    @RequestMapping(value = "/saveEngagementSection", method = RequestMethod.POST)
    public @ResponseBody ModelAndView saveEngagementSection(@Valid @ModelAttribute(value = "sectionDetails") programEngagementSections sectionDetails, BindingResult result, @RequestParam String action, @RequestParam String section, @RequestParam Integer currdspPos, HttpSession session) throws Exception {

        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/sysAdmin/programs/forms/sectionForm");
            
            if(sectionDetails.getId() > 0) {
                Integer maxDspPos = programformsmanager.getEngagementSections((Integer) session.getAttribute("programId")).size();
                mav.addObject("modalTitle", "Modify Patient Detail Section");
                mav.addObject("maxDspPos", maxDspPos);
            }
            else {
                mav.addObject("modalTitle", "Create New Engagement Section");
                mav.addObject("maxDspPos", 1);
            }
            mav.addObject("programName", session.getAttribute("programName"));
            mav.addObject("section", section);
            
            return mav;
        }
        
        /* Check to see if dspPos changed, if so need to update the old dsp Pos */
        if(sectionDetails.getDspPos() != currdspPos) {
            programEngagementSections foundSection = programformsmanager.getEngagementSectionBydspPos(sectionDetails.getDspPos(), (Integer) session.getAttribute("programId"));
            if(foundSection != null) {
                foundSection.setDspPos(currdspPos);
                programformsmanager.saveEngagementSection(foundSection);
            }
        }
        
        programformsmanager.saveEngagementSection(sectionDetails);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/programs/forms/sectionForm");
        mav.addObject("programName", session.getAttribute("programName"));
        mav.addObject("sectionName", section);
        mav.addObject("success", "sectionSaved");
        
        return mav;

    }
    
    
    /**
     * The '/fields' GET request will display the page that will list the fields 
     * for the selected section and program.
     *
     * @param s	The id of the selected section
     *
     * @return	Will return the program modules page.
     *
     * @throws Exception
     *
     */
    @RequestMapping(value = "/{section}/fields", method = RequestMethod.GET)
    public ModelAndView programSectionFields(
            @PathVariable String section, 
            @RequestParam(value = "s", required = true, defaultValue = "0") Integer sectionId, HttpSession session) throws Exception {
        
        ModelAndView mav = new ModelAndView();
        mav.addObject("id", session.getAttribute("programId"));
        mav.addObject("programName", session.getAttribute("programName"));
        
        // Patient Form Sections      
        if("patient-sections".equals(section)) {
            patientFields = new CopyOnWriteArrayList<programPatientFields>();
            mav.setViewName("/patientFields");
            
            programPatientSections sectionDetails = programformsmanager.getPatientSectionById(sectionId);
            mav.addObject("sectionDetails", sectionDetails);
        
        }
        
        // Engagement Form Sections
        else if ("engagement-sections".equals(section)) {
           engagementFields  = new CopyOnWriteArrayList<programEngagementFields>(); 
           mav.setViewName("/engagementFields");
           
           programEngagementSections sectionDetails = programformsmanager.getEngagementSectionById(sectionId);
           mav.addObject("sectionDetails", sectionDetails);
        
        }
        else if("programProfile".equals(section)) {
           programProfileFields  = new CopyOnWriteArrayList<programProfileFields>(); 
           mav.setViewName("/programProfileFields");
        }

        mav.addObject("sectionId", sectionId);
        mav.addObject("sectionName", section);

        program programDetails = programmanager.getProgramById((Integer) session.getAttribute("programId"));
        mav.addObject("programDetails", programDetails);
        

        /**
         * Get a list of all available demographic fields *
         */
        List<dataElements> dataElements = dataelementmanager.getdataElements();
        mav.addObject("availableFields", dataElements);
        
        //Return a list of available crosswalks
        List<crosswalks> crosswalks = dataelementmanager.getCrosswalks(1, 0, (Integer) session.getAttribute("programId"));
        mav.addObject("crosswalks", crosswalks);
        
        //Return a list of available custom program fields
        List<customProgramFields> customFields = dataelementmanager.getCustomFields(1, 0, (Integer) session.getAttribute("programId"));
        mav.addObject("customFields", customFields);

        //Return a list of validation types
        List validationTypes = dataelementmanager.getValidationTypes();
        mav.addObject("validationTypes", validationTypes);

        return mav;

    }

    /**
     * The '/getFields.do' function will return the list of existing fields set up for the selected program and section.
     *
     * @Return list of data fields
     */
    @RequestMapping(value = "/getFields.do", method = RequestMethod.GET)
    public @ResponseBody
    ModelAndView getFields(@RequestParam(value = "reload", required = true) boolean reload, HttpSession session, @RequestParam(value = "section", required = true) String section, @RequestParam(value = "sectionId", required = true) Integer sectionId) throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/programs/forms/existingFields");

        /**
         * only get the saved translations if reload == 0 We only want to retrieve the saved ones on initial load
         */
        if (reload == false) {
            
            String fieldName;
            String crosswalkName;
            String validationName;
            Map<String, String> defaultValues;
            String optionDesc;
            String optionValue;
            
            // Patient Form Sections         
            if("patient-sections".equals(section)) {
                //Need to get a list of existing patient fields
                List<programPatientFields> existingPatientFields = programformsmanager.getPatientFields((Integer) session.getAttribute("programId"), sectionId);
                
                for (programPatientFields field : existingPatientFields) {
                    
                    //Get the field Details
                    if(field.getFieldId() > 0) {
                        dataElements fieldDetails = dataelementmanager.getFieldDetails(field.getFieldId());
                        field.setFieldName(fieldDetails.getElementName());

                        if(fieldDetails.getPopulateFromTable() != null && !fieldDetails.getPopulateFromTable().isEmpty()) {
                            field.setAutoPopulate(true);
                        }
                        else {
                            field.setAutoPopulate(false);
                        }
                    }
                    else {
                        customProgramFields fieldDetails = dataelementmanager.getCustomField(field.getCustomfieldId());
                        field.setFieldName(fieldDetails.getFieldName());
                        field.setAutoPopulate(false);
                    }

                    //Get the crosswalk name by id
                    if (field.getCrosswalkId() != 0) {
                        crosswalkName = dataelementmanager.getCrosswalkName(field.getCrosswalkId());
                        field.setCwName(crosswalkName);
                        
                        defaultValues = new HashMap<>();

                        /* Get values of crosswalk */
                        List crosswalkdata = dataelementmanager.getCrosswalkData(field.getCrosswalkId());

                        Iterator cwDataIt = crosswalkdata.iterator();
                        while (cwDataIt.hasNext()) {
                            Object cwDatarow[] = (Object[]) cwDataIt.next();
                            optionDesc = (String) cwDatarow[2];
                            optionValue = (String) cwDatarow[1];

                            defaultValues.put(optionValue, optionDesc);
                        }

                        field.setDefaultValues(defaultValues);
                       
                    }

                    //Get the macro name by id
                    if (field.getValidationId() > 0) {
                        validationName = dataelementmanager.getValidationName(field.getValidationId());
                        field.setValidationName(validationName);
                    }

                    patientFields.add(field);
                }
                
                mav.addObject("existingFields", patientFields);
            }
            
            // Engagement Form Sections 
            else if ("engagement-sections".equals(section)) {
                //Need to get a list of existing engagement fields
                List<programEngagementFields> existingEngagementFields = programformsmanager.getEngagementFields((Integer) session.getAttribute("programId"), sectionId);

                for (programEngagementFields field : existingEngagementFields) {
                    
                    //Get the field Details
                    if(field.getFieldId() > 0) {
                        dataElements fieldDetails = dataelementmanager.getFieldDetails(field.getFieldId());
                        field.setFieldName(fieldDetails.getElementName());

                        if(fieldDetails.getPopulateFromTable() != null && !fieldDetails.getPopulateFromTable().isEmpty()) {
                            field.setAutoPopulate(true);
                        }
                        else {
                            field.setAutoPopulate(false);
                        }
                    }
                    else {
                        customProgramFields fieldDetails = dataelementmanager.getCustomField(field.getCustomfieldId());
                        field.setFieldName(fieldDetails.getFieldName());
                        field.setAutoPopulate(false);
                    }
                    
                    //Get the crosswalk name by id
                    if (field.getCrosswalkId() != 0) {
                        crosswalkName = dataelementmanager.getCrosswalkName(field.getCrosswalkId());
                        field.setCwName(crosswalkName);
                        
                        defaultValues = new HashMap<>();

                        /* Get values of crosswalk */
                        List crosswalkdata = dataelementmanager.getCrosswalkData(field.getCrosswalkId());

                        Iterator cwDataIt = crosswalkdata.iterator();
                        while (cwDataIt.hasNext()) {
                            Object cwDatarow[] = (Object[]) cwDataIt.next();
                            optionDesc = (String) cwDatarow[2];
                            optionValue = (String) cwDatarow[0];

                            defaultValues.put(optionValue, optionDesc);
                        }

                        field.setDefaultValues(defaultValues);
                    }

                    //Get the macro name by id
                    if (field.getValidationId() > 0) {
                        validationName = dataelementmanager.getValidationName(field.getValidationId());
                        field.setValidationName(validationName);
                    }

                    engagementFields.add(field);
                }
                
                mav.addObject("existingFields", engagementFields);
                
            }
            // Program Profile Fields
            else if ("programProfile".equals(section)) {
                //Need to get a list of existing engagement fields
                List<programProfileFields> existingProgramProfileFields = programformsmanager.getProgramProfileFields((Integer) session.getAttribute("programId"));

                for (programProfileFields field : existingProgramProfileFields) {
                    
                    //Get the field Details
                    if(field.getFieldId() > 0) {
                        dataElements fieldDetails = dataelementmanager.getFieldDetails(field.getFieldId());
                        field.setFieldName(fieldDetails.getElementName());

                        if(fieldDetails.getPopulateFromTable() != null && !fieldDetails.getPopulateFromTable().isEmpty()) {
                            field.setAutoPopulate(true);
                        }
                        else {
                            field.setAutoPopulate(false);
                        }
                    }
                    else {
                        customProgramFields fieldDetails = dataelementmanager.getCustomField(field.getCustomfieldId());
                        field.setFieldName(fieldDetails.getFieldName());
                        field.setAutoPopulate(false);
                    }
                    
                    //Get the crosswalk name by id
                    if (field.getCrosswalkId() != 0) {
                        crosswalkName = dataelementmanager.getCrosswalkName(field.getCrosswalkId());
                        field.setCwName(crosswalkName);
                        
                        defaultValues = new HashMap<>();

                        /* Get values of crosswalk */
                        List crosswalkdata = dataelementmanager.getCrosswalkData(field.getCrosswalkId());

                        Iterator cwDataIt = crosswalkdata.iterator();
                        while (cwDataIt.hasNext()) {
                            Object cwDatarow[] = (Object[]) cwDataIt.next();
                            optionDesc = (String) cwDatarow[2];
                            optionValue = (String) cwDatarow[0];

                            defaultValues.put(optionValue, optionDesc);
                        }

                        field.setDefaultValues(defaultValues);
                    }

                    //Get the macro name by id
                    if (field.getValidationId() > 0) {
                        validationName = dataelementmanager.getValidationName(field.getValidationId());
                        field.setValidationName(validationName);
                    }

                    programProfileFields.add(field);
                }
                
                mav.addObject("existingFields", programProfileFields);
                
            }
        }

        else {
            
            // Patient Form Sections        
            if("patient-sections".equals(section)) {
                mav.addObject("existingFields", patientFields);
            }
            
            // Engagement Form Sections
            else if ("engagement-sections".equals(section)) {
                mav.addObject("existingFields", engagementFields);
            }
            
            // Program Profile Form Sections
            else if ("programProfile".equals(section)) {
                mav.addObject("existingFields", programProfileFields);
            }
            
        }
        
        return mav;

    }

    /**
     * The '/setField.do' function will handle taking in a selected field, selected crosswalk and selected validation type and add it to an array of fields. This array will be used when the form is 
     * submitted to associate to the existing program and section.
     *
     * @param fieldId This will hold the id of the selected field
     * @param sectionId This will hold the id of the selected program section
     * @param fieldText This will hold the text value of the selected field (used for display purposes)
     * @param fieldDisplayName This will hold the text value of how the program will display the field
     * @param cw This will hold the id of the selected crosswalk
     * @param CWText This will hold the text value of the selected crosswalk (used for display purposes)
     * @param validationId This will hold the id of the selected validation type
     * @param validationName This will hold the name of the selected validation type
     * @param requiredfield This will hold value if the field is required or not
     * @param dataGridColumn This will hold the value if the field will be displayed as column in the data grid
     * @param section   This will hold the name of the passed in section
     *
     * @Return	This function will return the existing translations view that will display the table of newly selected translations
     */
    @RequestMapping(value = "/setField.do", method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView setField(
            @RequestParam(value = "fieldId", required = true) Integer fieldId, @RequestParam(value = "sectionId", required = true) Integer sectionId, @RequestParam(value = "fieldText", required = false, defaultValue="") String fieldText,
            @RequestParam(value = "fieldDisplayName", required = true) String fieldDisplayName,
            @RequestParam(value = "cw", required = true) Integer cw, @RequestParam(value = "CWText", required = true) String cwText,
            @RequestParam(value = "validationId", required = true) Integer validationId, @RequestParam(value = "validationName", required = true) String validationName,
            @RequestParam(value = "requiredField", required = true) boolean requiredField, 
            @RequestParam(value = "hideField", required = true) boolean hideField, 
            @RequestParam(value = "dataGridColumn", required = true) boolean dataGridColumn,
            @RequestParam(value = "section", required = true) String section,
            @RequestParam(value = "searchColumn", required = true) boolean searchColumn,
            @RequestParam(value = "summaryColumn", required = true) boolean summaryColumn,
            @RequestParam(value = "customFieldId", required = true) Integer customFieldId,
            @RequestParam(value = "customFieldText", required = false, defaultValue="") String customFieldText,
            @RequestParam(value = "readOnly", required = true) boolean readOnly,
            HttpSession session
    ) throws Exception {

        int dspPos;

        if (cw == null) {
            cw = 0;
            cwText = null;
        }
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/programs/forms/existingFields");
        
        /* Patient Form Sections */   
        if("patient-sections".equals(section)) {
            dspPos = patientFields.size() + 1;
            Integer totalSearchColumns = 0;
            
            if(searchColumn == true) {
                for(programPatientFields currField : patientFields) {
                    if(currField.isSearchField()) {
                        totalSearchColumns+=1;
                    }
                }
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
            field.setSummaryField(summaryColumn);
            field.setSearchField(searchColumn);
            field.setHideField(hideField);
            field.setReadOnly(readOnly);
            field.setCustomfieldId(customFieldId);
            
            if(searchColumn == true) {
                field.setSearchDspPos(totalSearchColumns+1);
            }
            
            if(cw > 0) {
                Map<String, String> defaultValues = new HashMap<>();
                String optionDesc;
                String optionValue;

                /* Get values of crosswalk */
                List crosswalkdata = dataelementmanager.getCrosswalkData(cw);

                Iterator cwDataIt = crosswalkdata.iterator();
                while (cwDataIt.hasNext()) {
                    Object cwDatarow[] = (Object[]) cwDataIt.next();
                    optionDesc = (String) cwDatarow[2];
                    optionValue = (String) cwDatarow[0];

                    defaultValues.put(optionValue, optionDesc);

                }

                field.setDefaultValues(defaultValues);
            }
            

            patientFields.add(field);
            mav.addObject("existingFields", patientFields);
        }
        
        /* Engagement Form Sections */
        else if ("engagement-sections".equals(section)) {
            dspPos = engagementFields.size() + 1;
            
            Integer totalSearchColumns = 0;
            
            if(searchColumn == true) {
                for(programEngagementFields currField : engagementFields) {
                    if(currField.isSearchField()) {
                        totalSearchColumns+=1;
                    }
                }
            }
            
            programEngagementFields field = new programEngagementFields();
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
            field.setSummaryField(summaryColumn);
            field.setSearchField(searchColumn);
            field.setHideField(hideField);
            field.setReadOnly(readOnly);
            field.setCustomfieldId(customFieldId);
            
            if(searchColumn == true) {
                field.setSearchDspPos(totalSearchColumns+1);
            }
            
            if(cw > 0) {
                Map<String, String> defaultValues = new HashMap<>();
                String optionDesc;
                String optionValue;

                /* Get values of crosswalk */
                List crosswalkdata = dataelementmanager.getCrosswalkData(cw);

                Iterator cwDataIt = crosswalkdata.iterator();
                while (cwDataIt.hasNext()) {
                    Object cwDatarow[] = (Object[]) cwDataIt.next();
                    optionDesc = (String) cwDatarow[2];
                    optionValue = (String) cwDatarow[0];

                    defaultValues.put(optionValue, optionDesc);

                }

                field.setDefaultValues(defaultValues);
            }
            
            engagementFields.add(field);
            mav.addObject("existingFields", engagementFields);
        }
        
        // Program Profile Form Sections 
        else if ("programProfile".equals(section)) {
            dspPos = programProfileFields.size() + 1;
            
            Integer totalSearchColumns = 0;
            
            programProfileFields field = new programProfileFields();
            field.setProgramId((Integer) session.getAttribute("programId"));
            field.setCrosswalkId(cw);
            field.setCwName(cwText);
            field.setFieldId(fieldId);
            field.setFieldName(fieldText);
            field.setFieldDisplayname(fieldDisplayName);
            field.setValidationId(validationId);
            field.setValidationName(validationName);
            field.setRequiredField(requiredField);
            field.setDspPos(dspPos);
            field.setCustomfieldId(customFieldId);
           
            if(cw > 0) {
                Map<String, String> defaultValues = new HashMap<>();
                String optionDesc;
                String optionValue;

                /* Get values of crosswalk */
                List crosswalkdata = dataelementmanager.getCrosswalkData(cw);

                Iterator cwDataIt = crosswalkdata.iterator();
                while (cwDataIt.hasNext()) {
                    Object cwDatarow[] = (Object[]) cwDataIt.next();
                    optionDesc = (String) cwDatarow[2];
                    optionValue = (String) cwDatarow[0];

                    defaultValues.put(optionValue, optionDesc);

                }

                field.setDefaultValues(defaultValues);
            }
            
            programProfileFields.add(field);
            mav.addObject("existingFields", programProfileFields);
        }

        return mav;
    }


    /**
     * The '/removeField.do' function will handle removing a field from fields array.
     *
     * @param   section This will hold the section name (patient-sections) or (engagement-sections)
     * @param	fieldId This will hold the field that is being removed
     * @param	processOrder This will hold the process order of the field to be removed so we remove the correct field number as the same field could be in the list with different crosswalks
     *
     * @Return	1	The function will simply return a 1 back to the ajax call
     */
    @RequestMapping(value = "/removeField.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Integer removeField(@RequestParam(value = "section", required = true) String section, @RequestParam(value = "fieldId", required = true) Integer fieldId, @RequestParam(value = "dspOrder", required = true) Integer dspOrder, @RequestParam(value = "searchDspOrder", required = true) Integer searchDspOrder) throws Exception {

        // Patient Form Sections 
        if ("patient-sections".equals(section)) {
            Iterator<programPatientFields> it = patientFields.iterator();

            int currdspOrder;
            int currsearchdspOrder;

            while (it.hasNext()) {
                programPatientFields field = it.next();
                
                if (field.getFieldId() == fieldId && field.getDspPos() == dspOrder) {
                    patientFields.remove(field);
                } else if (field.getDspPos() > dspOrder) {
                    currdspOrder = field.getDspPos();
                    field.setDspPos(currdspOrder - 1);
                }
                
                if(field.getSearchDspPos() > searchDspOrder) {
                    currsearchdspOrder = field.getSearchDspPos();
                    field.setSearchDspPos(currsearchdspOrder -1);
                }
            }
        } 
        
        // Engagement Form Sections
        else if ("engagement-sections".equals(section)) {
            Iterator<programEngagementFields> it = engagementFields.iterator();

            int currdspOrder;
            int currsearchdspOrder;

            while (it.hasNext()) {
                programEngagementFields field = it.next();
                if (field.getFieldId() == fieldId && field.getDspPos() == dspOrder) {
                    engagementFields.remove(field);
                } else if (field.getDspPos() > dspOrder) {
                    currdspOrder = field.getDspPos();
                    field.setDspPos(currdspOrder - 1);
                }
                
                if(field.getSearchDspPos() > searchDspOrder) {
                    currsearchdspOrder = field.getSearchDspPos();
                    field.setSearchDspPos(currsearchdspOrder -1);
                }
            }
        }
        
        // Program Profile Form Fields
        else if ("programProfile".equals(section)) {
            Iterator<programProfileFields> it = programProfileFields.iterator();

            int currdspOrder;
            int currsearchdspOrder;

            while (it.hasNext()) {
                programProfileFields field = it.next();
                if (field.getFieldId() == fieldId && field.getDspPos() == dspOrder) {
                    programProfileFields.remove(field);
                } else if (field.getDspPos() > dspOrder) {
                    currdspOrder = field.getDspPos();
                    field.setDspPos(currdspOrder - 1);
                }
            }
        }

        return 1;
    }

    
    /**
     * The '/updateFieldDspOrder.do' function will handle updating the field display position.
     *
     * @param   section This will hold the section name (patient-sections) or (engagement-sections)
     * @param	fieldId This will hold the field that is being removed
     * @param	processOrder This will hold the process order of the field to be removed so we remove the correct field number as the same field could be in the list with different crosswalks
     *
     * @Return	1	The function will simply return a 1 back to the ajax call
     */
    @RequestMapping(value = "/updateFieldDspOrder.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Integer updateFormDisplayOrder(@RequestParam(value = "section", required = true) String section, @RequestParam(value = "currdspOrder", required = true) Integer currdspOrder, @RequestParam(value = "newdspOrder", required = true) Integer newdspOrder) throws Exception {

        // Patient Form Sections 
        if ("patient-sections".equals(section)) {
            Iterator<programPatientFields> it = patientFields.iterator();

            while (it.hasNext()) {
                programPatientFields field = it.next();
                if (field.getDspPos() == currdspOrder) {
                    field.setDspPos(newdspOrder);
                } else if (field.getDspPos() == newdspOrder) {
                    field.setDspPos(currdspOrder);
                }
            }
        } 
        
        // Engagement Form Sections
        else if ("engagement-sections".equals(section)) {
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
        
        // Program Profile Form Sections
        else if ("programProfile".equals(section)) {
            Iterator<programProfileFields> it = programProfileFields.iterator();

            while (it.hasNext()) {
                programProfileFields field = it.next();
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
     * The '/updateFieldSearchDspOrder.do' function will handle updating the field display position.
     *
     * @param   section This will hold the section name (patient-sections) or (engagement-sections)
     * @param	fieldId This will hold the field that is being removed
     * @param	processOrder This will hold the process order of the field to be removed so we remove the correct field number as the same field could be in the list with different crosswalks
     *
     * @Return	1	The function will simply return a 1 back to the ajax call
     */
    @RequestMapping(value = "/updateFieldSearchDspOrder.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Integer updateFieldSearchDspOrder(@RequestParam(value = "section", required = true) String section, @RequestParam(value = "currdspOrder", required = true) Integer currdspOrder, @RequestParam(value = "newdspOrder", required = true) Integer newdspOrder) throws Exception {

        // Patient Form Sections
        if ("patient-sections".equals(section)) {
            Iterator<programPatientFields> it = patientFields.iterator();

            while (it.hasNext()) {
                programPatientFields field = it.next();
                if (field.getSearchDspPos()== currdspOrder) {
                    field.setSearchDspPos(newdspOrder);
                } else if (field.getSearchDspPos() == newdspOrder) {
                    field.setSearchDspPos(currdspOrder);
                }
            }
        } 
        
        // Engagement Form Sections
        else if ("engagement-sections".equals(section)) {
            Iterator<programEngagementFields> it = engagementFields.iterator();

            while (it.hasNext()) {
                programEngagementFields field = it.next();
                if (field.getSearchDspPos() == currdspOrder) {
                    field.setSearchDspPos(newdspOrder);
                } else if (field.getSearchDspPos() == newdspOrder) {
                    field.setSearchDspPos(currdspOrder);
                }
            }
        }
        
        
        return 1;
    }

    
    /**
     * The '/saveSectionFields.do' POST request will submit the selected fields and save it to the data base.
     *
     */
    @RequestMapping(value = "/saveSectionFields.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Integer saveSectionFields(@RequestParam(value = "section", required = true) String section, @RequestParam(value = "sectionId", required = true) Integer sectionId, HttpSession session) throws Exception {

        // Patient Form Sections
        if("patient-sections".equals(section)) {
            programformsmanager.deletePatientFields((Integer) session.getAttribute("programId"), sectionId);
            
            //Loop through the list of translations
            for (programPatientFields field : patientFields) {
                Integer oldFieldId = field.getId();
                
                //Delete field
                programformsmanager.deletePatientField(field.getId());
                
                Integer newFieldId = programformsmanager.savePatientFields(field);
                
                //Update any populated field values
                programformsmanager.savePatientFieldValueFieldId(oldFieldId,newFieldId);
            }
        }
        
        // Engagement Form Sections 
        else if ("engagement-sections".equals(section)) {
            programformsmanager.deleteEngagementFields((Integer) session.getAttribute("programId"), sectionId);
            
            //Loop through the list of translations
            for (programEngagementFields field : engagementFields) {
                Integer oldFieldId = field.getId();
                
                //Delete field
                programformsmanager.deleteEngagementField(field.getId());
                
                Integer newFieldId = programformsmanager.saveEngagementFields(field);
                
                //Update any populated field values
                programformsmanager.saveEngagementFieldValueFieldId(oldFieldId,newFieldId);
            }
        }
        
        // Program Profile Form Sections 
        else if ("programProfile".equals(section)) {
            programformsmanager.deleteProgramProfileFields((Integer) session.getAttribute("programId"));
            
            //Loop through the list of translations
            for (programProfileFields field : programProfileFields) {
                Integer oldFieldId = field.getId();
                
                //Delete field
                programformsmanager.deleteProgramProfileField(field.getId());
                
                Integer newFieldId = programformsmanager.saveProgramProfileFields(field);
                
                //Update any populated field values
                programformsmanager.saveProgramProfileFieldValueFieldId(oldFieldId,newFieldId);
            }
        }
        
            
        return 1;
    }
    
    
    /**
     * The '/fieldForm' request will display the form to edit the selected field.
     *
     * @param session
     * @param redirectAttr
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/fieldForm", method = RequestMethod.GET)
    public @ResponseBody ModelAndView fieldForm(@RequestParam(value = "id", required = true) Integer fieldId, @RequestParam String section, @RequestParam(value = "sectionId", required = true) Integer sectionId, HttpSession session, RedirectAttributes redirectAttr) throws Exception {
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/programs/forms/fieldForm");
        
        if("patient-sections".equals(section)) {
            programPatientFields fieldDetails = programformsmanager.getPatientFieldById(fieldId);
            
            if(fieldDetails.getCrosswalkId() > 0) {
                Map<String, String> defaultValues = new HashMap<>();;
                String optionDesc;
                String optionValue;

                /* Get values of crosswalk */
                List crosswalkdata = dataelementmanager.getCrosswalkData(fieldDetails.getCrosswalkId());

                Iterator cwDataIt = crosswalkdata.iterator();
                while (cwDataIt.hasNext()) {
                    Object cwDatarow[] = (Object[]) cwDataIt.next();
                    optionDesc = (String) cwDatarow[2];
                    optionValue = (String) cwDatarow[0];

                    defaultValues.put(optionValue, optionDesc);

                }

                fieldDetails.setDefaultValues(defaultValues);
            }
            
            mav.addObject("fieldDetails", fieldDetails);
        }
        
        // Engagement Form Sections 
        else if ("engagement-sections".equals(section)) {
            //Need to get a list of existing engagement fields
            programEngagementFields fieldDetails = programformsmanager.getEngagementFieldById(fieldId);
            
            if(fieldDetails.getCrosswalkId() > 0) {
                Map<String, String> defaultValues = new HashMap<>();;
                String optionDesc;
                String optionValue;

                /* Get values of crosswalk */
                List crosswalkdata = dataelementmanager.getCrosswalkData(fieldDetails.getCrosswalkId());

                Iterator cwDataIt = crosswalkdata.iterator();
                while (cwDataIt.hasNext()) {
                    Object cwDatarow[] = (Object[]) cwDataIt.next();
                    optionDesc = (String) cwDatarow[2];
                    optionValue = (String) cwDatarow[0];

                    defaultValues.put(optionValue, optionDesc);

                }

                fieldDetails.setDefaultValues(defaultValues);
            }
            
            mav.addObject("fieldDetails", fieldDetails);
 
        }
        
        // Progaam Profile Form Sections 
        else if ("programProfile".equals(section)) {
            programProfileFields fieldDetails = programformsmanager.getProgramProfileFieldById(fieldId);
            
            if(fieldDetails.getCrosswalkId() > 0) {
                Map<String, String> defaultValues = new HashMap<>();;
                String optionDesc;
                String optionValue;

                /* Get values of crosswalk */
                List crosswalkdata = dataelementmanager.getCrosswalkData(fieldDetails.getCrosswalkId());

                Iterator cwDataIt = crosswalkdata.iterator();
                while (cwDataIt.hasNext()) {
                    Object cwDatarow[] = (Object[]) cwDataIt.next();
                    optionDesc = (String) cwDatarow[2];
                    optionValue = (String) cwDatarow[0];

                    defaultValues.put(optionValue, optionDesc);

                }

                fieldDetails.setDefaultValues(defaultValues);
            }
            
            mav.addObject("fieldDetails", fieldDetails);
 
        }
        
        mav.addObject("programName", session.getAttribute("programName"));
        mav.addObject("section", section);
        
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
     * The '/savePatientField' POST request will submit the patient field.
     *
     * @param fieldDetails	The object holding the patient field form fields
     * @param result	The validation result
     * @param redirectAttr	The variable that will hold values that can be read after the redirect
     * @param action	The variable that holds which button was pressed
     *
     * @throws Exception
     */
    @RequestMapping(value = "/savePatientField", method = RequestMethod.POST)
    public @ResponseBody ModelAndView savePatientField(@Valid @ModelAttribute(value = "fieldDetails") programPatientFields fieldDetails, BindingResult result, @RequestParam String action, @RequestParam String section, HttpSession session) throws Exception {

        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/sysAdmin/programs/forms/fieldForm");
            
            List<dataElements> dataElements = dataelementmanager.getdataElements();
            mav.addObject("availableFields", dataElements);

            //Return a list of available crosswalks
            List<crosswalks> crosswalks = dataelementmanager.getCrosswalks(1, 0, (Integer) session.getAttribute("programId"));
            mav.addObject("crosswalks", crosswalks);

            //Return a list of validation types
            List validationTypes = dataelementmanager.getValidationTypes();
            mav.addObject("validationTypes", validationTypes);
            
            mav.addObject("programName", session.getAttribute("programName"));
            mav.addObject("section", section);
            
            return mav;
        }
        
        programformsmanager.savePatientField(fieldDetails);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/programs/forms/fieldForm");
        mav.addObject("programName", session.getAttribute("programName"));
        mav.addObject("sectionName", section);
        mav.addObject("success", "fieldSaved");
        
        return mav;
    }
    
    
    /**
     * The '/saveEngagementField' POST request will submit the engagement field.
     *
     * @param fieldDetails	The object holding the patient engagement form fields
     * @param result	The validation result
     * @param redirectAttr	The variable that will hold values that can be read after the redirect
     * @param action	The variable that holds which button was pressed
     *
     * @throws Exception
     */
    @RequestMapping(value = "/saveEngagementField", method = RequestMethod.POST)
    public @ResponseBody ModelAndView saveEngagementField(@Valid @ModelAttribute(value = "fieldDetails") programEngagementFields fieldDetails, BindingResult result, @RequestParam String action, @RequestParam String section, HttpSession session) throws Exception {

        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/sysAdmin/programs/forms/fieldForm");
            
            List<dataElements> dataElements = dataelementmanager.getdataElements();
            mav.addObject("availableFields", dataElements);

            //Return a list of available crosswalks
            List<crosswalks> crosswalks = dataelementmanager.getCrosswalks(1, 0, (Integer) session.getAttribute("programId"));
            mav.addObject("crosswalks", crosswalks);

            //Return a list of validation types
            List validationTypes = dataelementmanager.getValidationTypes();
            mav.addObject("validationTypes", validationTypes);
            
            mav.addObject("programName", session.getAttribute("programName"));
            mav.addObject("section", section);
            mav.addObject("error", result.getFieldError());
            
            return mav;
        }
        
        programformsmanager.saveEngagementField(fieldDetails);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/programs/forms/fieldForm");
        mav.addObject("programName", session.getAttribute("programName"));
        mav.addObject("sectionName", section);
        mav.addObject("success", "fieldSaved");
        
        return mav;

    }
    
    /**
     * The '/saveProgramProfileField' POST request will submit the engagement field.
     *
     * @param fieldDetails	The object holding the patient engagement form fields
     * @param result	The validation result
     * @param redirectAttr	The variable that will hold values that can be read after the redirect
     * @param action	The variable that holds which button was pressed
     *
     * @throws Exception
     */
    @RequestMapping(value = "/saveProgramProfileField", method = RequestMethod.POST)
    public @ResponseBody ModelAndView saveProgramProfileField(@Valid @ModelAttribute(value = "fieldDetails") programProfileFields fieldDetails, BindingResult result, @RequestParam String action, @RequestParam String section, HttpSession session) throws Exception {

        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/sysAdmin/programs/forms/fieldForm");
            
            List<dataElements> dataElements = dataelementmanager.getdataElements();
            mav.addObject("availableFields", dataElements);

            //Return a list of available crosswalks
            List<crosswalks> crosswalks = dataelementmanager.getCrosswalks(1, 0, (Integer) session.getAttribute("programId"));
            mav.addObject("crosswalks", crosswalks);

            //Return a list of validation types
            List validationTypes = dataelementmanager.getValidationTypes();
            mav.addObject("validationTypes", validationTypes);
            
            mav.addObject("programName", session.getAttribute("programName"));
            mav.addObject("section", section);
            mav.addObject("error", result.getFieldError());
            
            return mav;
        }
        
        programformsmanager.saveProgramProfileField(fieldDetails);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/programs/forms/fieldForm");
        mav.addObject("programName", session.getAttribute("programName"));
        mav.addObject("sectionName", section);
        mav.addObject("success", "fieldSaved");
        
        return mav;

    }        
    
    /**
     * The '/getFieldValues' GET request will return the list of values for the selected table.
     * 
     * @param section   The section the field belongs to either "patient-sections" or "engagement-sections"
     * @param fieldId   The id of the selected field
     * 
     * @return  This function will return a list of values
     * @throws Exception 
     */
    @RequestMapping(value = "/getFieldValues", method = RequestMethod.GET)
    public @ResponseBody ModelAndView getFieldValues(@RequestParam(value = "section", required = true) String section, @RequestParam(value = "fieldId", required = true) Integer fieldId) throws Exception {
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/programs/forms/selectValues");
        
        if("patient-sections".equals(section)) {
            programPatientFields fieldDetails = programformsmanager.getPatientFieldById(fieldId);
            mav.addObject("fieldName", fieldDetails.getFieldDisplayname());
            
            List tableValues = dataelementmanager.getLookupTableValues(fieldDetails.getFieldId());
            mav.addObject("tableValues", tableValues);
            
            //Get already selected values 
            List<programPatientFieldValues> selectedFieldValues = programformsmanager.getPatientFieldValues(fieldId);
            
            List<Integer> selectedFieldIds = new ArrayList<Integer>() ;
            
            if(!selectedFieldValues.isEmpty()) {
                for(programPatientFieldValues fieldValue : selectedFieldValues) {
                    selectedFieldIds.add(fieldValue.getValueId());
                }
            }
            
            mav.addObject("selectedFieldIds", selectedFieldIds);
            
        }
        
        //Engagement Form Sections 
        else if ("engagement-sections".equals(section)) {
            //Need to get a list of existing engagement fields
            programEngagementFields fieldDetails = programformsmanager.getEngagementFieldById(fieldId);
            mav.addObject("fieldName", fieldDetails.getFieldDisplayname());
            
            List tableValues = dataelementmanager.getLookupTableValues(fieldDetails.getFieldId());
            mav.addObject("tableValues", tableValues);
            
            //Get already selected values 
            List<programEngagementFieldValues> selectedFieldValues = programformsmanager.getEngagementFieldValues(fieldId);
            
            List<Integer> selectedFieldIds = new ArrayList<Integer>() ;
            
            if(!selectedFieldValues.isEmpty()) {
                for(programEngagementFieldValues fieldValue : selectedFieldValues) {
                    selectedFieldIds.add(fieldValue.getValueId());
                }
            }
            
            
            mav.addObject("selectedFieldIds", selectedFieldIds);
            
        }
        
        // Program Profile Form Sections 
        else if ("prgoramProfile".equals(section)) {
            //Need to get a list of existing program profile fields
            programProfileFields fieldDetails = programformsmanager.getProgramProfileFieldById(fieldId);
            mav.addObject("fieldName", fieldDetails.getFieldDisplayname());
            
            List tableValues = dataelementmanager.getLookupTableValues(fieldDetails.getFieldId());
            mav.addObject("tableValues", tableValues);
            
            //Get already selected values 
            List<programProfileFieldValues> selectedFieldValues = programformsmanager.getProgramProfileFieldValues(fieldId);
            
            List<Integer> selectedFieldIds = new ArrayList<Integer>() ;
            
            if(!selectedFieldValues.isEmpty()) {
                for(programProfileFieldValues fieldValue : selectedFieldValues) {
                    selectedFieldIds.add(fieldValue.getValueId());
                }
            }
            
            
            mav.addObject("selectedFieldIds", selectedFieldIds);
            
        }
        
        mav.addObject("section", section);
        mav.addObject("fieldId", fieldId);
        
        
        return mav;
        
    }
    
    /**
     * 
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/saveFieldValues", method = RequestMethod.POST)
    public @ResponseBody ModelAndView saveFieldValues(@RequestParam(value = "section", required = true) String section, @RequestParam(value = "fieldId", required = true) Integer fieldId, @RequestParam(value = "selectedValues", required = true) String selectedValues) throws Exception {
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/programs/forms/selectValues");
        
        if("patient-sections".equals(section)) {
            
            //Delete all values for the field
            programformsmanager.removeProgramFieldValues(fieldId);
            
            String[] selValues = selectedValues.split("\\|", -1);
            
            for(String value : selValues) {
                programPatientFieldValues newValue = new programPatientFieldValues();
                
                String[] valueDetails = value.split("\\~", -1);
                newValue.setpatientFieldId(fieldId);
                newValue.setValueId(Integer.parseInt(valueDetails[0]));
                newValue.setValueDisplayText(valueDetails[1]);
                
                programformsmanager.savePatientFieldValue(newValue);
                
            }
            
        }
        
        /* Engagement Form Sections */
        else if ("engagement-sections".equals(section)) {
            
            //Delete all values for the field
            programformsmanager.removeEngagementFieldValues(fieldId);
            
            String[] selValues = selectedValues.split("\\|", -1);
            
            for(String value : selValues) {
                programEngagementFieldValues newValue = new programEngagementFieldValues();
                
                String[] valueDetails = value.split("\\~", -1);
                newValue.setEngagementFieldId(fieldId);
                newValue.setValueId(Integer.parseInt(valueDetails[0]));
                newValue.setValueDisplayText(valueDetails[1]);
                
                programformsmanager.saveEngagementFieldValue(newValue);
                
            }
            
        }
        
        mav.addObject("success", "valuesSaved");
        
        return mav;
        
    }
    
    /**
     * The 'updateDefaultValue{params}' function will handle setting the crosswalk default value.
     *
     * @param	fieldId         This will hold the field that is being set
     * @param	selValue	The selected default value
     *
     * @Return	1	The function will simply return a 1 back to the ajax call
     */
    @RequestMapping(value = "/updateDefaultValue{params}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Integer updateDefaultValue(@RequestParam(value = "section", required = true) String section, @RequestParam(value = "fieldId", required = true) Integer fieldId, @RequestParam(value = "selValue", required = true) String selValue) throws Exception {

        if("patient-sections".equals(section)) {
            Iterator<programPatientFields> it = patientFields.iterator();
            
            while (it.hasNext()) {
                programPatientFields translation = it.next();
                if (translation.getId() == fieldId) {
                    translation.setDefaultValue(selValue);
                }
            }
            
        }
        
        /* Engagement Form Sections */
        else if ("engagement-sections".equals(section)) {
            Iterator<programEngagementFields> it = engagementFields.iterator();
            
            while (it.hasNext()) {
                programEngagementFields translation = it.next();
                if (translation.getId() == fieldId) {
                    translation.setDefaultValue(selValue);
                }
            }
            
        }
        
        return 1;
    }
}
