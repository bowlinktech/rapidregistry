/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.controller;

import com.bowlink.rr.model.dataElements;
import com.bowlink.rr.model.fileTypes;
import com.bowlink.rr.model.program;
import com.bowlink.rr.model.programUploadTypes;
import com.bowlink.rr.model.programUploadTypesFormFields;
import com.bowlink.rr.reference.fileSystem;
import com.bowlink.rr.service.dataElementManager;
import com.bowlink.rr.service.importManager;
import com.bowlink.rr.service.programManager;

import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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
@RequestMapping(value={"/sysAdmin/programs/{programName}","/programAdmin/programs"})
public class programImports {
    
    @Autowired
    programManager programmanager;
    
    @Autowired
    importManager importManager;
    
    @Autowired
    dataElementManager dataelementmanager;
    
    private static List<programUploadTypesFormFields> importFields = null;
    
    
    /**
     * The '/imports' GET request will display the page to program imports.
     *
     *
     * @return	Will return the program import list page.
     *
     * @throws Exception
     *
     */
    @RequestMapping(value = "/imports", method = RequestMethod.GET)
    public ModelAndView programModules(HttpSession session) throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/imports");
        mav.addObject("id", session.getAttribute("programId"));

        program programDetails = programmanager.getProgramById((Integer) session.getAttribute("programId"));
        mav.addObject("programDetails", programDetails);

        /* Get a list of program upload types */
        List<programUploadTypes> importTypes = importManager.getUploadTypes((Integer) session.getAttribute("programId"));
        
        mav.addObject("importTypes", importTypes);
        
        mav.addObject("programName", session.getAttribute("programName"));

        return mav;

    }
    
    /**
     * The '/importTypeForm' request will display the form to create/edit a program import type.
     *
     * @param session
     * @param redirectAttr
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/importTypeForm", method = RequestMethod.GET)
    public @ResponseBody ModelAndView importTypeForm(@RequestParam Integer id, HttpSession session, RedirectAttributes redirectAttr) throws Exception {
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/programs/imports/importForm");
        
        programUploadTypes uploadType;

        if(id > 0) {
            uploadType = importManager.getUploadTypeById(id);
            mav.addObject("modalTitle", "Modify Import Type");
        }
        else {
            uploadType = new programUploadTypes();
            uploadType.setProgramId((Integer) session.getAttribute("programId"));
            mav.addObject("modalTitle", "Create New Import Type");
        }

        List <fileTypes> fileTypesList =  importManager.getFileTypes(0);
        mav.addObject("fileTypesList", fileTypesList);
        
        List<programUploadTypes> putList = importManager.getParentUploadTypes((Integer) session.getAttribute("programId"));
        
        //Get the list of available file delimiters
        @SuppressWarnings("rawtypes")
        List delimiters = dataelementmanager.getDelimiters();
        mav.addObject("delimiters", delimiters);
        
        if (uploadType.getParentProgramUploadTypeId() != 0) {
        	uploadType.setParent(true);
        }
        mav.addObject("importTypeDetails", uploadType);
        mav.addObject("putList", putList);
        
       
        mav.addObject("programName", session.getAttribute("programName"));

        return mav;
    }   
    
    /**
     * The '/saveImportType' POST request will submit the program import type.
     *
     * @param importTypeDetails	The object holding the program import types
     * @param result	The validation result
     * @param redirectAttr	The variable that will hold values that can be read after the redirect
     * @param action	The variable that holds which button was pressed
     *
     * @throws Exception
     */
    @RequestMapping(value = "/saveImportType", method = RequestMethod.POST)
    public @ResponseBody ModelAndView saveImportType(@Valid @ModelAttribute(value = "importTypeDetails") programUploadTypes importTypeDetails, BindingResult result, @RequestParam String action, HttpSession session) throws Exception {
    	
    	ModelAndView mav = new ModelAndView();
    	
    	List <fileTypes> fileTypesList =  importManager.getFileTypes(0);
        mav.addObject("fileTypesList", fileTypesList);
        
        //Get the list of available file delimiters
        @SuppressWarnings("rawtypes")
        List delimiters = dataelementmanager.getDelimiters();
        mav.addObject("delimiters", delimiters);
    	
        if (result.hasErrors()) {
            
            mav.setViewName("/sysAdmin/programs/imports/importForm");
            
            if(importTypeDetails.getId() > 0) {
                mav.addObject("modalTitle", "Modify Import Type");
            }
            else {
                mav.addObject("modalTitle", "Create New Import Type");
            }
            mav.addObject("programName", session.getAttribute("programName"));
            List<programUploadTypes> putList = importManager.getParentUploadTypes((Integer) session.getAttribute("programId"));
            mav.addObject("putList", putList);
            return mav;
        }
        
        importManager.saveUploadType(importTypeDetails);

        mav.setViewName("/sysAdmin/programs/imports/importForm");
        mav.addObject("programName", session.getAttribute("programName"));
        mav.addObject("success", "importTypeSaved");
        
        return mav;

    }
    
    /**
     * The 'removeImportType' POST request will remove the import type and the associated fields.
     * we do not let them delete 
     * 
     * @param importTypeId The id of the selected import type.
     * @param session
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/removeImportType.do", method = RequestMethod.POST)
    public @ResponseBody String removeImportType(@RequestParam(value = "id", required = true) Integer importTypeId, HttpSession session) throws Exception {
        
        String deleted = importManager.removeImportType(importTypeId);
       
        
        return (String) (session.getAttribute("programName") + "/imports?deleted=" + deleted + "");

    }

    
    /**
     * The '/imports/fields' GET request will display the page that will list the fields 
     * for the selected import type.
     *
     * @param s	The id of the selected import type
     *
     * @return	Will return the program import type fields page.
     *
     * @throws Exception
     *
     */
    @RequestMapping(value = "/imports/fields", method = RequestMethod.GET)
    public ModelAndView impoortFields(@RequestParam(value = "s", required = true) Integer importTypeId, HttpSession session) throws Exception {
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/importFields");
        mav.addObject("id", session.getAttribute("programId"));
        mav.addObject("programName", session.getAttribute("programName"));
        mav.addObject("importTypeId", importTypeId);
        
        importFields = new CopyOnWriteArrayList<programUploadTypesFormFields>();
        
        program programDetails = programmanager.getProgramById((Integer) session.getAttribute("programId"));
        mav.addObject("programDetails", programDetails);
        
        /**
         * Get a list of all available demographic fields *
         */
        List<dataElements> dataElements = dataelementmanager.getActiveDataElements();
        mav.addObject("availableFields", dataElements);

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
    ModelAndView getFields(@RequestParam(value = "reload", required = true) boolean reload, HttpSession session, @RequestParam(value = "importTypeId", required = true) Integer importTypeId) throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/programs/imports/existingFields");

        /**
         * only get the saved import fields if reload == 0 We only want to retrieve the saved ones on initial load
         */
        if (reload == false) {
            
            String fieldName;
            String validationName;
            
            //Need to get a list of existing import fields
            List<programUploadTypesFormFields> existingFields = importManager.getImportTypeFields(importTypeId);

            for (programUploadTypesFormFields field : existingFields) {

                //Get the field Details
                dataElements fieldDetails = dataelementmanager.getFieldDetails(field.getFieldId());
                field.setFieldName(fieldDetails.getElementName());

                //Get the validation name by id
                if (field.getValidationId() > 0) {
                    validationName = dataelementmanager.getValidationName(field.getValidationId());
                    field.setValidationName(validationName);
                }

                importFields.add(field);
            }

            mav.addObject("existingFields", importFields);
            
        }

        else {
            
            mav.addObject("existingFields", importFields);
           
        }
        
        return mav;

    }

    /**
     * The '/setField.do' function will handle taking in a selected field and selected validation type and add it to an array of fields. This array will be used when the form is 
     * submitted to associate to the existing program and import type.
     *
     * @param fieldId This will hold the id of the selected field
     * @param importTypeId This will hold the id of the selected program import type
     * @param fieldText This will hold the text value of the selected field (used for display purposes)
     * @param validationId This will hold the id of the selected validation type
     * @param validationName This will hold the name of the selected validation type
     * @param requiredfield This will hold value if the field is required or not
     * @param multiValue This will hold value if the field allows multiValues or not
     * @param useField This will hold value if the field is in used or not
     * @Return	This function will return the existing import fields that will display the table of newly selected import type
     */
    @RequestMapping(value = "/setField.do", method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView setField(
            @RequestParam(value = "fieldId", required = true) Integer fieldId, 
            @RequestParam(value = "importTypeId", required = true) Integer importTypeId,
            @RequestParam(value = "fieldText", required = true) String fieldText,
            @RequestParam(value = "validationId", required = true) Integer validationId,
            @RequestParam(value = "validationName", required = true) String validationName,
            @RequestParam(value = "requiredField", required = true) boolean requiredField, 
            @RequestParam(value = "useField", required = true) boolean useField, 
            @RequestParam(value = "multiValue", required = true) boolean multiValue, 
            
            HttpSession session
    ) throws Exception {

        int dspPos;

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/programs/imports/existingFields");
        
        dspPos = importFields.size() + 1;

        programUploadTypesFormFields field = new programUploadTypesFormFields();
        field.setProgramUploadTypeId(importTypeId);
        field.setFieldId(fieldId);
        field.setFieldName(fieldText);
        field.setValidationId(validationId);
        field.setValidationName(validationName);
        field.setRequiredField(requiredField);
        field.setDspPos(dspPos);
        field.setMultiValue(multiValue);
        field.setUseField(useField);

        importFields.add(field);
        mav.addObject("existingFields", importFields);
        
        return mav;
    }


    /**
     * The '/removeField.do' function will handle removing a field from fields array.
     *
     * @param	fieldId This will hold the field that is being removed
     * @param	processOrder This will hold the process order of the field to be removed so we remove the correct field number as the same field could be in the list with different crosswalks
     *
     * @Return	1	The function will simply return a 1 back to the ajax call
     */
    @RequestMapping(value = "/removeField.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Integer removeField(@RequestParam(value = "fieldId", required = true) Integer fieldId, @RequestParam(value = "dspOrder", required = true) Integer dspOrder) throws Exception {

        Iterator<programUploadTypesFormFields> it = importFields.iterator();

        int currdspOrder;

        while (it.hasNext()) {
            programUploadTypesFormFields field = it.next();

            if (field.getFieldId() == fieldId && field.getDspPos() == dspOrder) {
                importFields.remove(field);
            } else if (field.getDspPos() > dspOrder) {
                currdspOrder = field.getDspPos();
                field.setDspPos(currdspOrder - 1);
            }

        }
        
        return 1;
    }

    
    /**
     * The '/updateFieldDspOrder.do' function will handle updating the field display position.
     *
     * @param	fieldId This will hold the field that is being removed
     * @param	processOrder This will hold the process order of the field to be removed so we remove the correct field number as the same field could be in the list with different crosswalks
     *
     * @Return	1	The function will simply return a 1 back to the ajax call
     */
    @RequestMapping(value = "/updateFieldDspOrder.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Integer updateFormDisplayOrder(@RequestParam(value = "currdspOrder", required = true) Integer currdspOrder, @RequestParam(value = "newdspOrder", required = true) Integer newdspOrder) throws Exception {

        Iterator<programUploadTypesFormFields> it = importFields.iterator();

        while (it.hasNext()) {
            programUploadTypesFormFields field = it.next();
            if (field.getDspPos() == currdspOrder) {
                field.setDspPos(newdspOrder);
            } else if (field.getDspPos() == newdspOrder) {
                field.setDspPos(currdspOrder);
            }
        }
        
        return 1;
    }
    
    /**
     * The '/saveImportFields.do' POST request will submit the selected fields and save it to the data base.
     *
     */
    @RequestMapping(value = "/saveImportFields.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Integer saveSectionFields(@RequestParam(value = "importTypeId", required = true) Integer importTypeId, HttpSession session) throws Exception {

        //we need to keep existing fieldId
    	importManager.updateFormFieldStatus(importTypeId, "D");
        
        StringBuilder file_header = new StringBuilder();
        
        //Loop through the list of fields
        int totalFields = importFields.size();
        int loopCounter = 1;
        for (programUploadTypesFormFields field : importFields) {
        	//we are saving these, we set it to K - keep
        	
            Integer oldFieldId = field.getId();
            field.setFormFieldStatus("k");
            Integer newFieldId = importManager.saveUploadTypeField(field);
            
            
            file_header.append(field.getFieldName());
            if(loopCounter < totalFields) {
                file_header.append(",");
            }
        }
        
        //we need to delete these field from algorithm fields
        importManager.deleteFormFieldsFromAlgorithms(importTypeId);
        importManager.deleteUploadTypeFieldsByStatus(importTypeId, "D");
        
        //Generate sample CSV file for this import.
        
        //Need to make sure all folders are created for
        //the organization
        
        //Set the directory to save the uploaded message type template to
        fileSystem programdir = new fileSystem();
        
        programUploadTypes importTypeDetails = importManager.getUploadTypeById(importTypeId);
        
        program programDetails = programmanager.getProgramById(importTypeDetails.getProgramId());

        programdir.setDir(programDetails.getProgramName().replaceAll(" ", "-").toLowerCase(), "importFiles");
        
        //Delete the existing file
        File sampleFile = new File(programdir.getDir() + importTypeDetails.getName().replaceAll(" ", "-").toLowerCase() + ".csv");
        
        if (sampleFile.exists()) {
            sampleFile.delete();
        }
        
        //Create new CSV File
        sampleFile.createNewFile();
        
        try (FileWriter fileWriter = new FileWriter(sampleFile)) {
            fileWriter.append(file_header.toString());
            
            fileWriter.flush();
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
    public @ResponseBody ModelAndView fieldForm(@RequestParam(value = "id", required = true) Integer fieldId, @RequestParam(value = "importTypeId", required = true) Integer importTypeId, HttpSession session, RedirectAttributes redirectAttr) throws Exception {
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/programs/imports/fieldForm");
        
        programUploadTypesFormFields fieldDetails = importManager.getUploadTypeFieldById(fieldId);
        mav.addObject("fieldDetails", fieldDetails);
        
        mav.addObject("programName", session.getAttribute("programName"));
        
        List<dataElements> dataElements = dataelementmanager.getdataElements();
        mav.addObject("availableFields", dataElements);

        //Return a list of validation types
        List validationTypes = dataelementmanager.getValidationTypes();
        mav.addObject("validationTypes", validationTypes);

        return mav;
    }    
    
    /**
     * The '/saveImportField' POST request will submit the import field.
     *
     * @param fieldDetails	The object holding the patient engagement form fields
     * @param result	The validation result
     * @param redirectAttr	The variable that will hold values that can be read after the redirect
     * @param action	The variable that holds which button was pressed
     *
     * @throws Exception
     */
    @RequestMapping(value = "/saveImportField", method = RequestMethod.POST)
    public @ResponseBody ModelAndView saveImportField(@Valid @ModelAttribute(value = "fieldDetails") programUploadTypesFormFields fieldDetails, BindingResult result, @RequestParam String action, HttpSession session) throws Exception {

        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/sysAdmin/programs/imports/fieldForm");
            
            List<dataElements> dataElements = dataelementmanager.getdataElements();
            mav.addObject("availableFields", dataElements);

            //Return a list of validation types
            List validationTypes = dataelementmanager.getValidationTypes();
            mav.addObject("validationTypes", validationTypes);
            
            mav.addObject("programName", session.getAttribute("programName"));
            mav.addObject("error", result.getFieldError());
            
            return mav;
        }
        
        importManager.saveImportField(fieldDetails);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/programs/imports/fieldForm");
        mav.addObject("programName", session.getAttribute("programName"));
        mav.addObject("success", "fieldSaved");
        
        return mav;

    }
    
}
