/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.controller;

import com.bowlink.rr.model.algorithmCategories;
import com.bowlink.rr.model.algorithmMatchingActions;
import com.bowlink.rr.model.program;
import com.bowlink.rr.model.programUploadTypeAlgorithm;
import com.bowlink.rr.model.programUploadTypeAlgorithmFields;
import com.bowlink.rr.model.programUploadTypes;
import com.bowlink.rr.model.programUploadTypesFormFields;
import com.bowlink.rr.service.dataElementManager;
import com.bowlink.rr.service.importManager;
import com.bowlink.rr.service.masterClientIndexManager;
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
    importManager importmanager;
    

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
    public ModelAndView getAlgorithms(HttpSession session, 
    		@RequestParam(value = "s", required = true) Integer importTypeId) throws Exception {

    	ModelAndView mav = new ModelAndView();
        mav.setViewName("/mcialgorithms");
        mav.addObject("id", session.getAttribute("programId"));

        program programDetails = programmanager.getProgramById((Integer) session.getAttribute("programId"));
        mav.addObject("programDetails", programDetails);
        
        programUploadTypes importType = importmanager.getProgramUploadType(importTypeId);
        
        //we loop rules by categories
        List<algorithmCategories> algorithmByCatList =  mcimanager.getAlgorithmsByCatForUploadType(importTypeId);
        mav.addObject("algorithmByCatList", algorithmByCatList);
        
        //List<programUploadTypeAlgorithm> algorithmList = mcimanager.getProgramUploadTypeAlgorithm(importTypeId);
        //mav.addObject("algorithmList", algorithmList);
        
        mav.addObject("importType", importType);
        
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
    @ResponseBody public ModelAndView newMCIAlgorithmForm(HttpSession session, 
    		@RequestParam Integer importTypeId, @RequestParam Integer categoryId
    		) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/programs/mci/details");

        //Create a new blank provider.
        programUploadTypeAlgorithm mci = new programUploadTypeAlgorithm();
        mci.setProgramUploadTypeId(importTypeId);
        mci.setCategoryId(categoryId);
        /* Get a list of available fields for this upload type*/
        List<programUploadTypesFormFields> existingFormFields = importmanager.getImportTypeFields(importTypeId);

        /**get a list of categories**/
        List<algorithmCategories> categoryList = mcimanager.getAlgorithmCategories(true);
        mav.addObject("categoryList", categoryList);
        
        /** get a list of actions **/
        //drop down of possible actions for patient and visit reconciliation
        List<algorithmMatchingActions> actionList = mcimanager.getAlgorithmMatchingActions(true);
        mav.addObject("actionList", actionList);
       
        String fieldName;

        for (programUploadTypesFormFields field : existingFormFields) {
            //Get the field name by id
            fieldName = dataelementmanager.getfieldName(field.getFieldId());
            field.setFieldName(fieldName);

        }
        
        mav.addObject("availableFields", existingFormFields);
        mav.addObject("importTypeId", importTypeId);
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
    ModelAndView createMCIAlgorithm(@Valid @ModelAttribute(value = "mcidetails") programUploadTypeAlgorithm mcidetails, 
    		BindingResult result,  @RequestParam(value = "fieldIds", required = true) List<Integer> fieldIds, 
    		@RequestParam(value = "fieldAction", required = true) List<String> fieldAction, HttpSession session) 
    				throws Exception {
    	
        List<programUploadTypesFormFields> existingFormFields = importmanager.getImportTypeFields(mcidetails.getProgramUploadTypeId());
        programUploadTypes importType = importmanager.getProgramUploadType(mcidetails.getProgramUploadTypeId());
        
        
        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/sysAdmin/programs/mci/details");
            
            String fieldName;

            for (programUploadTypesFormFields field : existingFormFields) {
                //Get the field name by id
                fieldName = dataelementmanager.getfieldName(field.getFieldId());
                field.setFieldName(fieldName);

            }
            mav.addObject("availableFields", existingFormFields);
            mav.addObject("btnValue", "Create");
            return mav;
        }
        
        //look for max process order
        mcidetails.setProcessOrder(mcimanager.getMaxProcessOrder(mcidetails.getCategoryId(), (mcidetails.getProgramUploadTypeId() +1)));
        Integer algorithmId = mcimanager.createMCIAlgorithm(mcidetails);
        
        int i = 0;
        for(Integer fieldId : fieldIds) {
        	if (fieldId != null) {
	            programUploadTypeAlgorithmFields newField = new programUploadTypeAlgorithmFields();
	            newField.setFieldId(fieldId);
	            newField.setAlgorithmId(algorithmId);
	            
	            String selFieldAction = fieldAction.get(i);
	            newField.setAction(selFieldAction);           
	            mcimanager.createMCIAlgorithmFields(newField);
	            
	            i+=1;
        	}
        }

        ModelAndView mav = new ModelAndView("/sysAdmin/programs/mci/details");
        mav.addObject("importType", importType);
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
    ModelAndView updateMCIAlgorithm(@Valid @ModelAttribute(value = "mcidetails") programUploadTypeAlgorithm mcidetails, BindingResult result,  
    		@RequestParam(value = "fieldIds", required = true) List<Integer> fieldIds, 
    		@RequestParam(value = "fieldAction", required = true) List<String> fieldAction, HttpSession session) 
    				throws Exception {

    	List<programUploadTypesFormFields> existingFormFields = importmanager.getImportTypeFields(mcidetails.getProgramUploadTypeId());

    	
        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/sysAdmin/programs/mci/details");
            
            String fieldName;

            for (programUploadTypesFormFields field : existingFormFields) {
                //Get the field name by id
                fieldName = dataelementmanager.getfieldName(field.getFieldId());
                field.setFieldName(fieldName);

            }
            mav.addObject("availableFields", existingFormFields);
            mav.addObject("btnValue", "Create");
            return mav;
        }

        //get old details
        programUploadTypeAlgorithm mciOriginal = mcimanager.getMCIAlgorithm(mcidetails.getId());
        if (mcidetails.getCategoryId() != mciOriginal.getCategoryId()) {
        	//we set it to last process order of new category
        	mcidetails.setProcessOrder((mcimanager.getMaxProcessOrder(mcidetails.getCategoryId(), mcidetails.getProgramUploadTypeId())+1));
        }
        //we update
        mcimanager.updateMCIAlgorithm(mcidetails);
        // we reorder 
        if (mcidetails.getCategoryId() != mciOriginal.getCategoryId()) {
        	//we re-order
        	mcimanager.reorderAlgorithm(mcidetails.getCategoryId(), mcidetails.getProgramUploadTypeId());
        	mcimanager.reorderAlgorithm(mciOriginal.getCategoryId(), mciOriginal.getProgramUploadTypeId());
        }
        
        
        int i = 0;
        for(Integer fieldId : fieldIds) {
        	if (fieldId != null)  {
	            programUploadTypeAlgorithmFields newField = new programUploadTypeAlgorithmFields();
	            newField.setFieldId(fieldId);
	            newField.setAlgorithmId(mcidetails.getId());
	            
	            String selFieldAction = fieldAction.get(i);
	            newField.setAction(selFieldAction);
	            
	            mcimanager.createMCIAlgorithmFields(newField);
        	}
            i+=1;
        }

        ModelAndView mav = new ModelAndView("/sysAdmin/programs/mci/details");
        mav.addObject("importTypeId", mcidetails.getProgramUploadTypeId());
        mav.addObject("categoryId", mcidetails.getCategoryId());    
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
    @ResponseBody public ModelAndView editMCIAlgorithmForm(@RequestParam Integer algorithmId, HttpSession session) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/programs/mci/details");
        
        //Create a new blank provider.
        programUploadTypeAlgorithm mci = mcimanager.getMCIAlgorithm(algorithmId);
        
        /**get a list of categories**/
        List<algorithmCategories> categoryList = mcimanager.getAlgorithmCategories(true);
        mav.addObject("categoryList", categoryList);
        
        /** get a list of actions **/
        //drop down of possible actions for patient and visit reconciliation
        List<algorithmMatchingActions> actionList = mcimanager.getAlgorithmMatchingActions(true);
        mav.addObject("actionList", actionList);
       
        /* Get a list of available fields */
        List<programUploadTypesFormFields> existingFormFields = importmanager.getImportTypeFields(mci.getProgramUploadTypeId());
        //get info
        programUploadTypes importType = importmanager.getProgramUploadType(mci.getProgramUploadTypeId());
        
        String fieldName;

        for (programUploadTypesFormFields field : existingFormFields) {
            //Get the field name by id
            fieldName = dataelementmanager.getfieldName(field.getFieldId());
            field.setFieldName(fieldName);
        }
        mav.addObject("availableFields", existingFormFields);
        
        List<programUploadTypeAlgorithmFields> fields = mcimanager.getMCIAlgorithmFields(mci.getId());
        
        for(programUploadTypeAlgorithmFields field : fields) {
            //Get the field name by id
            String selfieldName = dataelementmanager.getfieldName(field.getFieldId());
            field.setFieldName(selfieldName);
        }
        mav.addObject("selFields", fields);
        mav.addObject("importType", importType);
        
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
    public @ResponseBody Integer removeAlgorithmField(@RequestParam Integer algorithmfieldId, 
    		HttpSession session) throws Exception {
        
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
    public @ResponseBody Integer removeAlgorithm(@RequestParam Integer algorithmId, @RequestParam Integer importTypeId, 
    		HttpSession session) throws Exception {
    	/** we need to reorder the rest of the algorithm **/
    	programUploadTypeAlgorithm algorithm = mcimanager.getMCIAlgorithm(algorithmId);
        mcimanager.removeAlgorithm(algorithmId);
        mcimanager.reorderAlgorithm(algorithm.getCategoryId(), importTypeId);
        
        return 1;
    }
    
    /**
     * The 'updateProcessOrder.do' function will handle updating the process order.
     *
     * @param   sectionId This will hold the section id of the algorithms that we want to reorder
     * @param	currOrder will hold the order of the current algorithm
     * @param	newOrder This will hold the new process order for the algorithm
     *
     * @Return	1	The function will simply return a 1 back to the ajax call
     */
    @RequestMapping(value = "/updateProcessOrder.do", 
    		method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Integer updateFormDisplayOrder(
    @RequestParam(value = "importTypeId", required = true) Integer importTypeId,
    @RequestParam(value = "categoryId", required = true) Integer categoryId,
    @RequestParam(value = "currOrder", required = true) Integer currOrder, 
    @RequestParam(value = "newOrder", required = true) Integer newOrder) throws Exception {

    	//we get algorithm info for the algorithm with the new order and the current order
    	programUploadTypeAlgorithm mciCurrent = mcimanager.getMCIAlgorithmByProcessOrder(currOrder,  importTypeId, categoryId);
    	programUploadTypeAlgorithm mciNew =  mcimanager.getMCIAlgorithmByProcessOrder(newOrder,  importTypeId, categoryId);
    	
    	//we switch and update
    	mciCurrent.setProcessOrder(newOrder);
    	mcimanager.updateMCIAlgorithm(mciCurrent);
    	mciNew.setProcessOrder(currOrder);
    	mcimanager.updateMCIAlgorithm(mciNew);
    	
        return 1;
    }
    
}
