package com.bowlink.rr.controller;

import com.bowlink.rr.model.User;
import com.bowlink.rr.model.activityCodes;
import com.bowlink.rr.model.crosswalks;
import com.bowlink.rr.model.dataElements;
import com.bowlink.rr.model.program;
import com.bowlink.rr.model.programActivityCodes;
import com.bowlink.rr.model.programAdmin;
import com.bowlink.rr.model.programAvailableTables;
import com.bowlink.rr.model.programPatientFields;
import com.bowlink.rr.model.programEngagementFields;
import com.bowlink.rr.model.programMCIAlgorithms;
import com.bowlink.rr.model.programMCIFields;
import com.bowlink.rr.model.programPatientEntryMethods;
import com.bowlink.rr.model.programPatientSections;
import com.bowlink.rr.model.programReports;
import com.bowlink.rr.model.reports;
import com.bowlink.rr.model.surveys;
import com.bowlink.rr.service.activityCodeManager;
import com.bowlink.rr.service.dataElementManager;
import com.bowlink.rr.service.programManager;
import com.bowlink.rr.service.reportManager;
import com.bowlink.rr.service.surveyManager;
import com.bowlink.rr.service.userManager;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

/**
 * The programController class will handle administrator page requests that fall outside specific sections.
 *
 *
 * @author chadmccue
 *
 */
@Controller
@RequestMapping("/sysAdmin/programs")
public class programController {

    @Autowired
    programManager programmanager;

    @Autowired
    dataElementManager dataelementmanager;

    @Autowired
    activityCodeManager activitycodemanager;
    
    @Autowired
    reportManager reportmanager;
    
    @Autowired
    userManager usermanager;
    
    @Autowired
    surveyManager surveymanager;

    private static List<programPatientFields> patientFields = null;

    private static List<programEngagementFields> engagementFields = null;

    /**
     * The '' request will serve up the administrator dashboard after a successful login.
     *
     * @param request
     * @param response
     * @return	the administrator dashboard view
     * @throws Exception
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView listPrograms(HttpServletRequest request, HttpServletResponse response, HttpSession session, RedirectAttributes redirectAttr) throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/programList");

        /* Get the list of programs in the system */
        List<program> programList = programmanager.getAllPrograms();

        mav.addObject("programList", programList);

        return mav;

    }

    /**
     * The '/create' request will display the form to create a new program.
     *
     * @param request
     * @param response
     * @param session
     * @param redirectAttr
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public @ResponseBody ModelAndView createProgram(HttpServletRequest request, HttpServletResponse response, HttpSession session, RedirectAttributes redirectAttr) throws Exception {

        program program = new program();

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/programs/newProgram");
        mav.addObject("program", program);

        return mav;
    }

    /**
     * The '/create' POST request will submit the new program once all required fields are checked, the system will also check to make sure the program name is not already in use.
     *
     * @param program	The object holding the program form fields
     * @param result	The validation result
     * @param redirectAttr	The variable that will hold values that can be read after the redirect
     * @param action	The variable that holds which button was pressed
     *
     * @return	Will return the organization list page on "Save & Close" Will return the organization details page on "Save" Will return the organization create page on error
     * @throws Exception
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody ModelAndView saveNewProgram(@Valid program program, BindingResult result, @RequestParam String action, HttpSession session) throws Exception {

        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/sysAdmin/programs/newProgram");
            return mav;
        }

        program exisingProgram = programmanager.getProgramByName(program.getProgramName(), 0);
        if (exisingProgram != null) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/sysAdmin/programs/newProgram");
            mav.addObject("id", program.getId());
            mav.addObject("existingProgram", "Program " + program.getProgramName() + " already exists.");

            return mav;
        }

        Integer id = null;

        /* Need to remove hyphens */
        String programNameNoHyphens = program.getProgramName().replace("-", " ");
        program.setProgramName(programNameNoHyphens);

        id = (Integer) programmanager.createProgram(program);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/programs/newProgram");
        mav.addObject("success", "programCreated");
        
        return mav;

    }

    /**
     * The '/{programName}' GET request will display the clicked program details page.
     *
     * @param programName   The {programName} will be the program name with spaces removed.
     *
     * @return	Will return the program details page.
     *
     * @throws Exception
     *
     */
    @RequestMapping(value = "/{programName}", method = RequestMethod.GET)
    public ModelAndView viewProgramDetails(@PathVariable String programName, HttpSession session) throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/details");
        
        session.setAttribute("programName", programName);
        program programDetails = programmanager.getProgramByName(programName);

        mav.addObject("id", programDetails.getId());
        mav.addObject("program", programDetails);
        
        /* Get the list of patient entry methods */
        List<programPatientEntryMethods> entryMethods = programmanager.getPatientEntryMethods(programDetails.getId());
        
        for(programPatientEntryMethods entryMethod : entryMethods) {
            
            if(entryMethod.getSurveyId() > 0) {
                /* Get the survey title */
                entryMethod.setSurveyTitle("SURVEY TITLE HERE");
            }
        }
        mav.addObject("entryMethods", entryMethods);
        
        /* Get the list of avaiable table for survey */
        List<programAvailableTables> availableTables = programmanager.getAvailableTablesForSurveys(programDetails.getId());
        mav.addObject("availableTables", availableTables);

        session.setAttribute("programId", programDetails.getId());
        
        return mav;

    }

    /**
     * The '/{programName}' POST request will submit the program changes once all required fields are checked, the system will also check to make sure the program name is not already in use.
     *
     * @param program	The object holding the program form fields
     * @param result	The validation result
     * @param redirectAttr	The variable that will hold values that can be read after the redirect
     * @param action	The variable that holds which button was pressed
     *
     * @return	Will return the organization list page on "Save & Close" Will return the organization details page on "Save" Will return the organization create page on error
     * @throws Exception
     */
    @RequestMapping(value = "/{programName}", method = RequestMethod.POST)
    public ModelAndView updateProgram(@Valid program program, BindingResult result, RedirectAttributes redirectAttr, @RequestParam String action, HttpSession session) throws Exception {

        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/details");
            return mav;
        }

        program exisingProgram = programmanager.getProgramByName(program.getProgramName(), program.getId());
        if (exisingProgram != null) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/details");
            mav.addObject("id", program.getId());
            mav.addObject("existingProgram", "Program " + program.getProgramName() + " already exists.");

            return mav;
        }

        Integer id = null;

        /* Need to remove hyphens */
        String programNameNoHyphens = program.getProgramName().replace("-", " ");
        program.setProgramName(programNameNoHyphens);

        programmanager.updateProgram(program);

        redirectAttr.addFlashAttribute("savedStatus", "updated");

        if (action.equals("save")) {
            ModelAndView mav = new ModelAndView(new RedirectView("organization-hierarchy"));
            return mav;
        } else {
            ModelAndView mav = new ModelAndView(new RedirectView("../../programs/"));
            return mav;
        }

    }
    
    /**
     * The '/entryMethodForm' request will display the form to create/edit a program table association available for surveys to pull off of.
     *
     *  @param session
     * @param redirectAttr
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/entryMethodForm", method = RequestMethod.GET)
    public @ResponseBody ModelAndView entryMethodForm(@RequestParam Integer id, HttpSession session, RedirectAttributes redirectAttr) throws Exception {
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/programs/patientEntryForm");
        
        programPatientEntryMethods patientEntryMethod;
        
        if(id > 0) {
            Integer maxDspPos = programmanager.getPatientEntryMethods((Integer) session.getAttribute("programId")).size();
            patientEntryMethod = programmanager.getpatientEntryMethodDetails(id);
            mav.addObject("modalTitle", "Modify Patient Entry Method");
            mav.addObject("maxDspPos",maxDspPos);
        }
        else {
            patientEntryMethod = new programPatientEntryMethods();
            patientEntryMethod.setProgramId((Integer) session.getAttribute("programId"));
            mav.addObject("modalTitle", "Save Patient Entry Method");
            mav.addObject("maxDspPos",1);
        }
        mav.addObject("programPatientEntryMethods", patientEntryMethod);
         
        /* Get a list of surveys in the system */
        @SuppressWarnings("rawtypes")
        List<surveys> surveys = surveymanager.getActiveSurveys((Integer) session.getAttribute("programId"));
        mav.addObject("surveys", surveys);
        
        mav.addObject("programName", session.getAttribute("programName"));

        return mav;
    }     
    
    /**
     * The '/saveEntryMethod' POST request will submit the new program patient entry method.
     *
     * @param program	The object holding the patient entry form fields
     * @param result	The validation result
     * @param redirectAttr	The variable that will hold values that can be read after the redirect
     * @param action	The variable that holds which button was pressed
     *
     * @throws Exception
     */
    @RequestMapping(value = "/saveEntryMethod", method = RequestMethod.POST)
    public @ResponseBody ModelAndView saveEntryMethod(@Valid programPatientEntryMethods programPatientEntryMethods, BindingResult result, @RequestParam String action, HttpSession session) throws Exception {

        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/sysAdmin/programs/patientEntryForm");
            return mav;
        }
        
        programmanager.saveProgramPatientEntryMethod(programPatientEntryMethods);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/programs/patientEntryForm");
        mav.addObject("programName", session.getAttribute("programName"));
        mav.addObject("success", "entrySaved");
        
        return mav;

    }
    
    /**
     * The '/deleteEntryMethod' POST request will remove the passed in patient entry method for the program.
     * 
     * @param id The id of the program entry method.
     * @param session
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/deleteEntryMethod", method = RequestMethod.POST)
    public @ResponseBody String deleteEntryMethod(@RequestParam Integer id, HttpSession session) throws Exception {

        programmanager.deletePatientEntryMethod(id);

        return (String) session.getAttribute("programName");

    }

    
    /**
     * The '/availableTables' request will display the form to create/edit a program table association available for surveys to pull off of.
     *
     * @param request
     * @param response
     * @param session
     * @param redirectAttr
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/availableTables", method = RequestMethod.GET)
    public @ResponseBody ModelAndView availableTables(@RequestParam Integer id, HttpServletRequest request, HttpServletResponse response, HttpSession session, RedirectAttributes redirectAttr) throws Exception {
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/programs/availableTables");
        
        programAvailableTables availableTable;
        
        if(id > 0) {
            availableTable = programmanager.getProgramAvailableTable(id);
            mav.addObject("modalTitle", "Modify Available Survey Table");
        }
        else {
            availableTable = new programAvailableTables();
            availableTable.setProgramId((Integer) session.getAttribute("programId"));
            mav.addObject("modalTitle", "Save Available Survey Table");
        }
        mav.addObject("programAvailableTables", availableTable);
         
        /* Get a list of tables in the system */
        //Get the list of available information tables
        @SuppressWarnings("rawtypes")
        List tables = dataelementmanager.getAllTables();
        mav.addObject("tables", tables);
        
        mav.addObject("programName", session.getAttribute("programName"));

        return mav;
    }
    
    
    /**
     * The '/saveAvailableTables' POST request will submit the program available tables for survey form.
     *
     * @param program	The object holding the program form fields
     * @param result	The validation result
     * @param redirectAttr	The variable that will hold values that can be read after the redirect
     * @param action	The variable that holds which button was pressed
     *
     * @throws Exception
     */
    @RequestMapping(value = "/saveAvailableTables", method = RequestMethod.POST)
    public @ResponseBody ModelAndView saveAvailableTable(@Valid programAvailableTables programAvailableTables, BindingResult result, @RequestParam String action, HttpSession session) throws Exception {

        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/sysAdmin/programs/availableTables");
            return mav;
        }
        
        programmanager.saveProgramAvailableTables(programAvailableTables);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/programs/availableTables");
        mav.addObject("programName", session.getAttribute("programName"));
        mav.addObject("success", "tableSaved");
        
        return mav;

    }
    
    /**
     * The '/deleteAvailableTable' POST request will remove the passed in available table for the program.
     * 
     * @param id The id of the program available table association.
     * @param session
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/deleteAvailableTable", method = RequestMethod.POST)
    public @ResponseBody String deleteAvailableTable(@RequestParam Integer id, HttpSession session) throws Exception {

        programmanager.deleteProgramAvailableTable(id);

        return (String) session.getAttribute("programName");

    }
    

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
    @RequestMapping(value = "/{programName}/patient-detail-sections", method = RequestMethod.GET)
    public ModelAndView programPatientDetailSections(HttpSession session) throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/patientDetailSections");
        mav.addObject("id", session.getAttribute("programId"));

        program programDetails = programmanager.getProgramById((Integer) session.getAttribute("programId"));
        mav.addObject("programDetails", programDetails);

        /**
         * Get a list of all patient detail sections *
         */
        List<programPatientSections> patientSections = programmanager.getPatientSections((Integer) session.getAttribute("programId"));
        mav.addObject("patientSections", patientSections);

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
    @RequestMapping(value = "/{programName}/activity-codes", method = RequestMethod.GET)
    public ModelAndView programActivityCodes(HttpSession session) throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/activitycodes");
        mav.addObject("id", session.getAttribute("programId"));

        program programDetails = programmanager.getProgramById((Integer) session.getAttribute("programId"));
        mav.addObject("programDetails", programDetails);

        /* Get a list available activity codes not already associated with the program */
        List<activityCodes> activityCodes = activitycodemanager.getActivityCodes(0);

        for (activityCodes code : activityCodes) {

            boolean codeBeingUsed = programmanager.getUsedActivityCodes((Integer) session.getAttribute("programId"), code.getId());

            code.setSelected(codeBeingUsed);

        }

        mav.addObject("availactivityCodes", activityCodes);

        return mav;

    }

    /**
     * The '/{programName}/activity-codes' GET request will display the program activity code page.
     *
     * @param programName	The {programName} will be the program name with spaces removed.
     * @param action
     * @param activityCodeList
     * @param redirectAttr
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{programName}/activity-codes", method = RequestMethod.POST)
    public ModelAndView saveProgramActivityCodes(@RequestParam String action, @RequestParam(value = "activityCodeList", required = false) List<Integer> activityCodeList, RedirectAttributes redirectAttr, HttpSession session) throws Exception {

        if (activityCodeList == null) {
            programmanager.removeProgramActivityCodes((Integer) session.getAttribute("programId"));
        } else {
            programmanager.removeProgramActivityCodes((Integer) session.getAttribute("programId"));

            for (Integer code : activityCodeList) {
                programActivityCodes newCodeAssoc = new programActivityCodes();
                newCodeAssoc.setCodeId(code);
                newCodeAssoc.setProgramId((Integer) session.getAttribute("programId"));

                programmanager.saveProgramActivityCode(newCodeAssoc);
            }

        }

        redirectAttr.addFlashAttribute("savedStatus", "codesupdated");

        if (action.equals("save")) {
            ModelAndView mav = new ModelAndView(new RedirectView("activity-codes"));
            return mav;
        } else {
            ModelAndView mav = new ModelAndView(new RedirectView("../../programs/"));
            return mav;
        }

    }

    /**
     * The '/{programName}/canned-reports' GET request will display the page to associate canned reports to a program.
     *
     * @param programName	The {programName} will be the program name with spaces removed.
     *
     * @return	Will return the program reports page.
     *
     * @throws Exception
     *
     */
    @RequestMapping(value = "/{programName}/canned-reports", method = RequestMethod.GET)
    public ModelAndView programReports(HttpSession session) throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/programReports");
        mav.addObject("id", session.getAttribute("programId"));

        program programDetails = programmanager.getProgramById((Integer) session.getAttribute("programId"));
        mav.addObject("programDetails", programDetails);

        /* Get a list of reports the program uses */
        List<Integer> useReports = programmanager.getProgramReports((Integer) session.getAttribute("programId"));

        /* Get a list of other reports */
        List<reports> availReports = reportmanager.getAllReports();

        if (!useReports.isEmpty()) {
            for (reports report : availReports) {

                if (useReports.contains(report.getId())) {
                    report.setUseReport(true);
                }
            }
        }

        mav.addObject("availReports", availReports);

        return mav;

    }

    /**
     * The '/{programName}/canned-reports' POST request will save the program report form.
     *
     * @param List<Imteger>	The list of reportIds to use.
     *
     * @return	Will return the program details page.
     *
     * @throws Exception
     *
     */
    @RequestMapping(value = "/{programName}/canned-reports", method = RequestMethod.POST)
    public ModelAndView saveprogramReports(@RequestParam List<Integer> reportIds, @RequestParam String action, RedirectAttributes redirectAttr, HttpSession session) throws Exception {

        Integer selProgramId = (Integer) session.getAttribute("programId");

        if (reportIds.isEmpty()) {
            programmanager.deleteProgramReports(selProgramId);
        } else {
            programmanager.deleteProgramReports(selProgramId);

            for (Integer reportId : reportIds) {

                programReports report = new programReports();

                report.setProgramId(selProgramId);
                report.setReportId(reportId);

                programmanager.saveProgramReports(report);
            }
        }

        redirectAttr.addFlashAttribute("savedStatus", "updatedprogramreports");

        if (action.equals("save")) {
            ModelAndView mav = new ModelAndView(new RedirectView("canned-reports"));
            return mav;
        } else {
            ModelAndView mav = new ModelAndView(new RedirectView("../../programs/"));
            return mav;
        }

    }
    
    /**
     * The '/{programName}/mci-algorithms' GET request will display the program MCI Algorithms.
     *
     * @param programName	The {programName} will be the program name with spaces removed.
     *
     * @return	Will return the MPI Algorithms page.
     *
     * @throws Exception
     *
     */
    @RequestMapping(value = "/{programName}/mci-algorithms", method = RequestMethod.GET)
    public ModelAndView programMCIAlgorithms(HttpSession session) throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/mcialgorithms");
        mav.addObject("id", session.getAttribute("programId"));

        program programDetails = programmanager.getProgramById((Integer) session.getAttribute("programId"));
        mav.addObject("programDetails", programDetails);

        /* Get a list of MPI Algorithms */
        List<programMCIAlgorithms> mciAlgorithms = programmanager.getProgramMCIAlgorithms((Integer) session.getAttribute("programId"));
        
        if(!mciAlgorithms.isEmpty()) {
            for(programMCIAlgorithms mci : mciAlgorithms) {
                List<programMCIFields> fields = programmanager.getProgramMCIFields(mci.getId());
                
                for(programMCIFields field : fields) {
                    //Get the field name by id
                    String fieldName = dataelementmanager.getfieldName(field.getFieldId());
                    field.setFieldName(fieldName);
                }
                
                mci.setFields(fields);
            }
        }
        
        mav.addObject("mpiAlgorithms", mciAlgorithms);

        return mav;

    }
    
    /**
     * The '/{programName}/algorithm.create' GET request will be used to create a new MCI Algorithm
     *
     * @return The blank MPI Algorithm page
     *
     * @Objects (1) An object that will hold the blank MCI Algorithm
     */
    @RequestMapping(value = "/{programName}/algorithm.create", method = RequestMethod.GET)
    @ResponseBody public ModelAndView newMCIAlgorithmForm(HttpSession session) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/programs/mci/details");

        //Create a new blank provider.
        programMCIAlgorithms mci = new programMCIAlgorithms();
        mci.setProgramId((Integer) session.getAttribute("programId"));
        
        /* Get a list of available fields */
        List<programPatientFields> programPatientFields = programmanager.getPatientFieldsByProgramId((Integer) session.getAttribute("programId"));

        String fieldName;

        for (programPatientFields field : programPatientFields) {
            //Get the field name by id
            fieldName = dataelementmanager.getfieldName(field.getFieldId());
            field.setFieldName(fieldName);

        }
        mav.addObject("availableFields", programPatientFields);

        mav.addObject("btnValue", "Create");
        mav.addObject("mcidetails", mci);

        return mav;
    }
    
    
    /**
     * The '/{programName}/create_mcialgorithm' POST request will handle submitting the new MCI Algorithm.
     *
     * @param mpidetails    The object containing the MPI Algorithm form fields
     * @param result        The validation result
     * @param redirectAttr	The variable that will hold values that can be read after the redirect
     *
     * @return	Will return the MCI Algorithm list page on "Save" Will return the MPI Algorithm form page on error
     *
     * @Objects	(1) The object containing all the information for the clicked org
     * @throws Exception
     */
    @RequestMapping(value = "/{programName}/create_mcialgorithm", method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView createMCIAlgorithm(@Valid @ModelAttribute(value = "mcidetails") programMCIAlgorithms mcidetails, BindingResult result,  @RequestParam(value = "fieldIds", required = true) List<Integer> fieldIds, @RequestParam(value = "fieldAction", required = true) List<String> fieldAction, HttpSession session) throws Exception {

        
        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/sysAdmin/programs/mci/details");
            /* Get a list of available fields */
            List<programPatientFields> programPatientFields = programmanager.getPatientFieldsByProgramId((Integer) session.getAttribute("programId"));

            String fieldName;

            for (programPatientFields field : programPatientFields) {
                //Get the field name by id
                fieldName = dataelementmanager.getfieldName(field.getFieldId());
                field.setFieldName(fieldName);

            }
            mav.addObject("availableFields", programPatientFields);
            mav.addObject("btnValue", "Create");
            return mav;
        }


        Integer mciId = programmanager.createMCIAlgorithm(mcidetails);
        
        int i = 0;
        for(Integer fieldId : fieldIds) {
            programMCIFields newField = new programMCIFields();
            newField.setFieldId(fieldId);
            newField.setMciId(mciId);
            
            String selFieldAction = fieldAction.get(i);
            newField.setAction(selFieldAction);
            
            programmanager.createMCIAlgorithmFields(newField);
            
            i+=1;
        }

        ModelAndView mav = new ModelAndView("/sysAdmin/programs/mci/details");
        mav.addObject("success", "algorithmCreated");
        return mav;
    }
    
   /**
     * The '/{programName}/update_mcialgorithm' POST request will handle submitting the selected MCI Algorithm changes.
     *
     * @param mcidetails    The object containing the MCI Algorithm form fields
     * @param result        The validation result
     * @param redirectAttr	The variable that will hold values that can be read after the redirect
     *
     * @return	Will return the MPI Algorithm list page on "Save" Will return the MCI Algorithm form page on error
     *
     * @Objects	(1) The object containing all the information for the clicked org
     * @throws Exception
     */
    @RequestMapping(value = "/{programName}/update_mcialgorithm", method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView updateMPIAlgorithm(@Valid @ModelAttribute(value = "mcidetails") programMCIAlgorithms mpidetails, BindingResult result,  @RequestParam(value = "fieldIds", required = true) List<Integer> fieldIds, @RequestParam(value = "fieldAction", required = true) List<String> fieldAction, HttpSession session) throws Exception {

        
        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/sysAdmin/programs/mpi/details");
            /* Get a list of available fields */
            List<programPatientFields> programPatienttFields = programmanager.getPatientFieldsByProgramId((Integer) session.getAttribute("programId"));

            String fieldName;

            for (programPatientFields field : programPatienttFields) {
                //Get the field name by id
                fieldName = dataelementmanager.getfieldName(field.getFieldId());
                field.setFieldName(fieldName);

            }
            mav.addObject("availableFields", programPatienttFields);
            mav.addObject("btnValue", "Create");
            return mav;
        }


        programmanager.updateMCIAlgorithm(mpidetails);
        
        int i = 0;
        for(Integer fieldId : fieldIds) {
            programMCIFields newField = new programMCIFields();
            newField.setFieldId(fieldId);
            newField.setMciId(mpidetails.getId());
            
            String selFieldAction = fieldAction.get(i);
            newField.setAction(selFieldAction);
            
            programmanager.createMCIAlgorithmFields(newField);
            
            i+=1;
        }

        ModelAndView mav = new ModelAndView("/sysAdmin/programs/mci/details");
        mav.addObject("success", "algorithmUpdated");
        return mav;
    }

    /**
     * The '/{programName}/algorithm.edit' GET request will be used to create a new MCI Algorithm
     *
     * @return The blank MCI Algorithm page
     *
     * @Objects (1) An object that will hold the blank MCI Algorithm
     */
    @RequestMapping(value = "/{programName}/algorithm.edit", method = RequestMethod.GET)
    @ResponseBody public ModelAndView editMCIAlgorithmForm(@RequestParam Integer mciId, HttpSession session) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/programs/mpi/details");

        //Create a new blank provider.
        programMCIAlgorithms mci = programmanager.getMCIAlgorithm(mciId);
        
        /* Get a list of available fields */
        List<programPatientFields> programPatientFields = programmanager.getPatientFieldsByProgramId((Integer) session.getAttribute("programId"));

        String fieldName;

        for (programPatientFields field : programPatientFields) {
            //Get the field name by id
            fieldName = dataelementmanager.getfieldName(field.getFieldId());
            field.setFieldName(fieldName);
        }
        mav.addObject("availableFields", programPatientFields);
        
        List<programMCIFields> fields = programmanager.getProgramMCIFields(mciId);
        
        for(programMCIFields field : fields) {
            //Get the field name by id
            String selfieldName = dataelementmanager.getfieldName(field.getFieldId());
            field.setFieldName(selfieldName);
        }
        mav.addObject("selFields", fields);

        mav.addObject("btnValue", "Update");
        mav.addObject("mpidetails", mci);

        return mav;
    }
    
    /**
     * The '/{programName}/remvoeAlgorithmField.do' POST request will remove the selected field for the passed in
     * MCI Algorithm.
     * 
     * @param algorithmFieldId  The id of the field to be removed.
     * 
     * @return Will return a 1 when the field is successfully removed.
     */
    @RequestMapping(value = "/{programName}/removeAlgorithmField.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Integer removeAlgorithmField(@RequestParam Integer algorithmfieldId, HttpSession session) throws Exception {
        
        programmanager.removeAlgorithmField(algorithmfieldId);
        
        return 1;
    }
    
    /**
     * The '/{programName}/program-admins' GET request will display the program administrators.
     *
     * @param programName	The {programName} will be the program name with spaces removed.
     *
     * @return	Will return the program admin list page.
     *
     * @throws Exception
     *
     */
    @RequestMapping(value = "/{programName}/program-admins", method = RequestMethod.GET)
    public ModelAndView getprogramAdministrators(HttpSession session) throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/administrators");
        mav.addObject("id", session.getAttribute("programId"));

        program programDetails = programmanager.getProgramById((Integer) session.getAttribute("programId"));
        mav.addObject("programDetails", programDetails);

        /* Get a list of Adminsitrators */
        List<programAdmin> administrators = programmanager.getProgramAdministrators((Integer) session.getAttribute("programId"));
        
        List<User> programAdministrators = null;
        programAdministrators = new CopyOnWriteArrayList<User>();
        
        if(!administrators.isEmpty()) {
            
            for(programAdmin admin : administrators) {
                User userDetails = usermanager.getUserById(admin.getsystemUserId());
                userDetails.setTimesloggedIn(usermanager.findTotalLogins(admin.getsystemUserId()));
           
                programAdministrators.add(userDetails);
            }
        }
        
        mav.addObject("programAdministrators", programAdministrators);

        return mav;

    }
    
    /**
     * The '/{programName}/administrator.create' GET request will be used to create a new program Administrator
     *
     * @return The blank program administrator page
     *
     * @Objects (1) An object that will hold the blank program admin form.
     */
    @RequestMapping(value = "/{programName}/administrator.create", method = RequestMethod.GET)
    @ResponseBody public ModelAndView newAdministratorForm(HttpSession session) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/programs/programAdmins/details");

        //Create a new blank provider.
        User user = new User();
        user.setRoleId(2);
       
        mav.addObject("btnValue", "Create");
        mav.addObject("admindetails", user);
        
        List<User> availableUsers = usermanager.getProgramAdmins();
        mav.addObject("availableUsers", availableUsers);

        return mav;
    }
    
    /**
     * The '/{programName}/create_programadmin' POST request will handle submitting the new program administrator.
     *
     * @param admindetails    The object containing the program administrator form fields
     * @param result        The validation result
     * @param redirectAttr	The variable that will hold values that can be read after the redirect
     *
     * @return	Will return the program administrators list page on "Save" Will return the program administrator form page on error
     
     * @throws Exception
     */
    @RequestMapping(value = "/{programName}/create_programadmin", method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView saveAdminProram(@Valid @ModelAttribute(value = "admindetails") User admindetails, BindingResult result, HttpSession session) throws Exception {

        
        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/sysAdmin/programs/programAdmins/details");
            List<User> availableUsers = usermanager.getProgramAdmins();
            mav.addObject("availableUsers", availableUsers);
            mav.addObject("btnValue", "Create");
            return mav;
        }
        
        
        Integer adminId = usermanager.createUser(admindetails);

        programAdmin adminprogram = new programAdmin();
        adminprogram.setProgramId((Integer) session.getAttribute("programId"));
        adminprogram.setsystemUserId(adminId);
        
        programmanager.saveAdminProgram(adminprogram);

        ModelAndView mav = new ModelAndView("/sysAdmin/programs/programAdmins/details");
        mav.addObject("success", "adminCreated");
        return mav;
    }
    
    /**
     * The '/{programName}/administrator.edit' GET request will be used to edit the selected program Administrator
     *
     * @return The program administrator page
     *
     * @Objects (1) An object that will hold the selected program admin details
     */
    @RequestMapping(value = "/{programName}/administrator.edit", method = RequestMethod.GET)
    @ResponseBody public ModelAndView editAdministratorForm(@RequestParam Integer adminId, HttpSession session) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/programs/programAdmins/details");

        //Create a new blank provider.
        User userDetails = usermanager.getUserById(adminId);
       
        userDetails.setTimesloggedIn(usermanager.findTotalLogins(adminId));
        
        mav.addObject("btnValue", "Update");
        mav.addObject("admindetails", userDetails);
        
        List<User> availableUsers = usermanager.getProgramAdmins();
        mav.addObject("availableUsers", availableUsers);

        return mav;
    }
    
    /**
     * The '/{programName}/update_programadmin' POST request will handle submitting the program administrator changes.
     *
     * @param admindetails    The object containing the program administrator form fields
     * @param result        The validation result
     * @param redirectAttr	The variable that will hold values that can be read after the redirect
     *
     * @return	Will return the program administrators list page on "Save" Will return the program administrator form page on error
     
     * @throws Exception
     */
    @RequestMapping(value = "/{programName}/update_programadmin", method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView updateProgramAdmin(@Valid @ModelAttribute(value = "admindetails") User admindetails, BindingResult result, HttpSession session) throws Exception {

        
        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/sysAdmin/programs/programAdmins/details");
            List<User> availableUsers = usermanager.getProgramAdmins();
            mav.addObject("availableUsers", availableUsers);
            mav.addObject("btnValue", "Update");
            return mav;
        }
        
        usermanager.updateUser(admindetails);

        ModelAndView mav = new ModelAndView("/sysAdmin/programs/programAdmins/details");
        mav.addObject("success", "adminUpdated");
        return mav;
    }
    
    
    /**
     * The '/{programName}/administrator.associateToProgram' POST request will be used to associate the selected admin to the
     * program
     *
     * @return The program administrator page
     *
     * @Objects (1) An object that will hold the selected program admin details
     */
    @RequestMapping(value = "/{programName}/administrator.associateToProgram", method = RequestMethod.POST)
    @ResponseBody public ModelAndView associateAdminToProgram(@RequestParam Integer adminId, HttpSession session) throws Exception {
        
        //Create a new blank provider.
        programAdmin details = new programAdmin();
        details.setProgramId((Integer) session.getAttribute("programId"));
        details.setsystemUserId(adminId);
       
        programmanager.saveAdminProgram(details);
        
        //Create a new blank provider.
        User userDetails = usermanager.getUserById(adminId);
       
        ModelAndView mav = new ModelAndView("/sysAdmin/programs/programAdmins/details");
        mav.addObject("success", "adminUpdated");
        mav.addObject("admindetails", userDetails);
        return mav;
    }
    
    /**
     * The '/{programName}/administrator.removeFromProgram' POST request will remove the association between the selected
     * admin and the program
     *
     * @return The program administrator list page
     *
     */
    @RequestMapping(value = "/{programName}/administrator.removeFromProgram", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Integer removeProgramAssociation(@RequestParam Integer adminId, HttpSession session) throws Exception {
        
        //Create a new blank provider.
        programmanager.removeAdminProgram((Integer) session.getAttribute("programId"), adminId);
       
        return 1;
    }
    

}
