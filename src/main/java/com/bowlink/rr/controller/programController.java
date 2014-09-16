package com.bowlink.rr.controller;

import com.bowlink.rr.model.User;
import com.bowlink.rr.model.activityCodes;
import com.bowlink.rr.model.crosswalks;
import com.bowlink.rr.model.demoDataElements;
import com.bowlink.rr.model.healthDataElements;
import com.bowlink.rr.model.modules;
import com.bowlink.rr.model.patientSharing;
import com.bowlink.rr.model.program;
import com.bowlink.rr.model.programActivityCodes;
import com.bowlink.rr.model.programAdmin;
import com.bowlink.rr.model.programDemoDataElements;
import com.bowlink.rr.model.programHealthDataElements;
import com.bowlink.rr.model.programMPI;
import com.bowlink.rr.model.programMPIFields;
import com.bowlink.rr.model.programModules;
import com.bowlink.rr.model.programReports;
import com.bowlink.rr.model.reports;
import com.bowlink.rr.service.activityCodeManager;
import com.bowlink.rr.service.dataElementManager;
import com.bowlink.rr.service.moduleManager;
import com.bowlink.rr.service.programManager;
import com.bowlink.rr.service.reportManager;
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
    moduleManager modulemanager;

    @Autowired
    dataElementManager dataelementmanager;

    @Autowired
    activityCodeManager activitycodemanager;
    
    @Autowired
    reportManager reportmanager;
    
    @Autowired
    userManager usermanager;

    private static List<programDemoDataElements> demoFields = null;

    private static List<programHealthDataElements> healthFields = null;

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
    public ModelAndView createProgram(HttpServletRequest request, HttpServletResponse response, HttpSession session, RedirectAttributes redirectAttr) throws Exception {

        program program = new program();

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/details");
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
    public ModelAndView saveNewProgram(@Valid program program, BindingResult result, RedirectAttributes redirectAttr, @RequestParam String action, HttpSession session) throws Exception {

        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/details");
            return mav;
        }

        program exisingProgram = programmanager.getProgramByName(program.getProgramName(), 0);
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

        id = (Integer) programmanager.createProgram(program);

        session.setAttribute("programId", id);

        redirectAttr.addFlashAttribute("savedStatus", "created");

        if (action.equals("save")) {
            ModelAndView mav = new ModelAndView(new RedirectView(program.getProgramName().replace(" ", "-").toLowerCase() + "/patient-sharing"));
            return mav;
        } else {
            ModelAndView mav = new ModelAndView(new RedirectView("../programs/"));
            return mav;
        }

    }

    /**
     * The '/{programName}' GET request will display the clicked program details page.
     *
     * @param programName	The {programName} will be the program name with spaces removed.
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

        program programDetails = programmanager.getProgramByName(programName);

        mav.addObject("id", programDetails.getId());
        mav.addObject("program", programDetails);

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
            ModelAndView mav = new ModelAndView(new RedirectView("patient-sharing"));
            return mav;
        } else {
            ModelAndView mav = new ModelAndView(new RedirectView("../../programs/"));
            return mav;
        }

    }

    /**
     * The '/{programName}/patient-sharing' GET request will display the patient sharing page.
     *
     * @param programName	The {programName} will be the program name with spaces removed.
     *
     * @return	Will return the program details page.
     *
     * @throws Exception
     *
     */
    @RequestMapping(value = "/{programName}/patient-sharing", method = RequestMethod.GET)
    public ModelAndView programPatientSharing(HttpSession session) throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/patientSharing");
        mav.addObject("id", session.getAttribute("programId"));

        program programDetails = programmanager.getProgramById((Integer) session.getAttribute("programId"));
        mav.addObject("programDetails", programDetails);

        /* Get a list of programs the program is sharing with */
        List<Integer> sharingWith = programmanager.getSharedPrograms((Integer) session.getAttribute("programId"));

        /* Get a list of other programs */
        List<program> availPrograms = programmanager.getOtherPrograms((Integer) session.getAttribute("programId"));

        if (!sharingWith.isEmpty()) {
            for (program program : availPrograms) {

                if (sharingWith.contains(program.getId())) {
                    program.setSharing(true);
                }
            }
        }

        mav.addObject("availPrograms", availPrograms);

        return mav;

    }

    /**
     * The '/{programName}/patient-sharing' POST request will save the patient sharing form.
     *
     * @param List<Imteger>	The list of programIds to share with.
     *
     * @return	Will return the program details page.
     *
     * @throws Exception
     *
     */
    @RequestMapping(value = "/{programName}/patient-sharing", method = RequestMethod.POST)
    public ModelAndView saveprogramPatientSharing(@RequestParam List<Integer> programIds, @RequestParam String action, RedirectAttributes redirectAttr, HttpSession session) throws Exception {

        Integer selProgramId = (Integer) session.getAttribute("programId");

        if (programIds.isEmpty()) {
            programmanager.deletePatientSharing(selProgramId);
        } else {
            programmanager.deletePatientSharing(selProgramId);

            for (Integer programId : programIds) {

                patientSharing newpatientshare = new patientSharing();

                newpatientshare.setProgramId(selProgramId);
                newpatientshare.setSharingProgramId(programId);

                programmanager.savePatientSharing(newpatientshare);
            }
        }

        redirectAttr.addFlashAttribute("savedStatus", "updatedpatientsharing");

        if (action.equals("save")) {
            ModelAndView mav = new ModelAndView(new RedirectView("patient-sharing"));
            return mav;
        } else {
            ModelAndView mav = new ModelAndView(new RedirectView("../../programs/"));
            return mav;
        }

    }

    /**
     * The '/{programName}/program-modules' GET request will display the page to associate modules to a program.
     *
     * @param programName	The {programName} will be the program name with spaces removed.
     *
     * @return	Will return the program modules page.
     *
     * @throws Exception
     *
     */
    @RequestMapping(value = "/{programName}/program-modules", method = RequestMethod.GET)
    public ModelAndView programModules(HttpSession session) throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/programModules");
        mav.addObject("id", session.getAttribute("programId"));

        program programDetails = programmanager.getProgramById((Integer) session.getAttribute("programId"));
        mav.addObject("programDetails", programDetails);

        /* Get a list of modules the program uses */
        List<Integer> usedModules = programmanager.getProgramModules((Integer) session.getAttribute("programId"));

        /* Get a list of other modules */
        List<modules> availModules = modulemanager.getAllModules();

        if (!usedModules.isEmpty()) {
            for (modules module : availModules) {

                if (usedModules.contains(module.getId())) {
                    module.setUseModule(true);
                }
            }
        }

        mav.addObject("availModules", availModules);

        return mav;

    }

    /**
     * The '/{programName}/program-modules' POST request will save the program module form.
     *
     * @param List<Imteger>	The list of moduleIds to use.
     *
     * @return	Will return the program details page.
     *
     * @throws Exception
     *
     */
    @RequestMapping(value = "/{programName}/program-modules", method = RequestMethod.POST)
    public ModelAndView saveprogramModules(@RequestParam List<Integer> moduleIds, @RequestParam String action, RedirectAttributes redirectAttr, HttpSession session) throws Exception {

        Integer selProgramId = (Integer) session.getAttribute("programId");

        if (moduleIds.isEmpty()) {
            programmanager.deleteProgramModules(selProgramId);
        } else {
            programmanager.deleteProgramModules(selProgramId);

            for (Integer moduleId : moduleIds) {

                programModules module = new programModules();

                module.setProgramId(selProgramId);
                module.setModuleId(moduleId);

                programmanager.saveProgramModules(module);
            }
        }

        redirectAttr.addFlashAttribute("savedStatus", "updatedprogrammodules");

        if (action.equals("save")) {
            ModelAndView mav = new ModelAndView(new RedirectView("program-modules"));
            return mav;
        } else {
            ModelAndView mav = new ModelAndView(new RedirectView("../../programs/"));
            return mav;
        }

    }

    /**
     * The '/{programName}/demo-data-elements' GET request will display the page to associate demographic data elements to a program.
     *
     * @param programName	The {programName} will be the program name with spaces removed.
     *
     * @return	Will return the program modules page.
     *
     * @throws Exception
     *
     */
    @RequestMapping(value = "/{programName}/demo-data-elements", method = RequestMethod.GET)
    public ModelAndView programDemoDataElements(HttpSession session) throws Exception {

        //Set the data translations array to get ready to hold data
        demoFields = new CopyOnWriteArrayList<programDemoDataElements>();

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/demodataelements");
        mav.addObject("id", session.getAttribute("programId"));

        program programDetails = programmanager.getProgramById((Integer) session.getAttribute("programId"));
        mav.addObject("programDetails", programDetails);

        /**
         * Get a list of all available demographic fields *
         */
        List<demoDataElements> dataElements = dataelementmanager.getDemoDataElements();
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
     * The '/getDemographicFields.do' function will return the list of existing demographic fields set up for the selected program.
     *
     * @Return list of data fields
     */
    @RequestMapping(value = "/getDemographicFields.do", method = RequestMethod.GET)
    public @ResponseBody
    ModelAndView getDemographicFields(@RequestParam(value = "reload", required = true) boolean reload, HttpSession session) throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/programs/dataElements/existingFields");

        /**
         * only get the saved translations if reload == 0 We only want to retrieve the saved ones on initial load
         */
        if (reload == false) {
            //Need to get a list of existing translations
            List<programDemoDataElements> programdemoFields = programmanager.getProgramDemoFields((Integer) session.getAttribute("programId"));

            String fieldName;
            String crosswalkName;
            String validationName;

            for (programDemoDataElements field : programdemoFields) {
                //Get the field name by id
                fieldName = dataelementmanager.getDemoFieldName(field.getFieldId());
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

                demoFields.add(field);
            }
        }

        mav.addObject("existingFields", demoFields);

        return mav;

    }

    /**
     * The '/setDemographicField' function will handle taking in a selected field, selected crosswalk and selected validation type and add it to an array of translations. This array will be used when the form is submitted to associate to the existing program.
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
    @RequestMapping(value = "/setDemographicField{params}", method = RequestMethod.GET)
    public @ResponseBody
    ModelAndView setDemographicField(
            @RequestParam(value = "fieldId", required = true) Integer fieldId, @RequestParam(value = "fieldText", required = true) String fieldText,
            @RequestParam(value = "fieldDisplayName", required = true) String fieldDisplayName,
            @RequestParam(value = "cw", required = true) Integer cw, @RequestParam(value = "CWText", required = true) String cwText,
            @RequestParam(value = "validationId", required = true) Integer validationId, @RequestParam(value = "validationName", required = true) String validationName,
            @RequestParam(value = "requiredField", required = true) boolean requiredField, HttpSession session
    ) throws Exception {

        int dspPos = demoFields.size() + 1;

        if (cw == null) {
            cw = 0;
            cwText = null;
        }

        programDemoDataElements field = new programDemoDataElements();
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

        demoFields.add(field);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/programs/dataElements/existingFields");
        mav.addObject("existingFields", demoFields);

        return mav;
    }

    /**
     * The '/{programName}/health-data-elements' GET request will display the page to associate health data elements to a program.
     *
     * @param programName	The {programName} will be the program name with spaces removed.
     *
     * @return	Will return the program modules page.
     *
     * @throws Exception
     *
     */
    @RequestMapping(value = "/{programName}/health-data-elements", method = RequestMethod.GET)
    public ModelAndView programHealthDataElements(HttpSession session) throws Exception {

        //Set the health fields array to get ready to hold data
        healthFields = new CopyOnWriteArrayList<programHealthDataElements>();

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/healthdataelements");
        mav.addObject("id", session.getAttribute("programId"));

        program programDetails = programmanager.getProgramById((Integer) session.getAttribute("programId"));
        mav.addObject("programDetails", programDetails);

        /**
         * Get a list of all available demographic fields *
         */
        List<healthDataElements> dataElements = dataelementmanager.getHealthDataElements();
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
     * The '/getHealthFields.do' function will return the list of existing health fields set up for the selected program.
     *
     * @Return list of data fields
     */
    @RequestMapping(value = "/getHealthFields.do", method = RequestMethod.GET)
    public @ResponseBody
    ModelAndView getHealthFields(@RequestParam(value = "reload", required = true) boolean reload, HttpSession session) throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/programs/dataElements/existingFields");

        /**
         * only get the saved translations if reload == 0 We only want to retrieve the saved ones on initial load
         */
        if (reload == false) {
            //Need to get a list of existing health fields
            List<programHealthDataElements> programHealthFields = programmanager.getProgramHealthFields((Integer) session.getAttribute("programId"));

            String fieldName;
            String crosswalkName;
            String validationName;

            for (programHealthDataElements field : programHealthFields) {
                //Get the field name by id
                fieldName = dataelementmanager.getDemoFieldName(field.getFieldId());
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

                healthFields.add(field);
            }
        }

        mav.addObject("existingFields", healthFields);

        return mav;

    }

    /**
     * The '/setHealthField' function will handle taking in a selected field, selected crosswalk and selected validation type and add it to an array of translations. This array will be used when the form is submitted to associate to the existing program.
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
    @RequestMapping(value = "/setHealthField{params}", method = RequestMethod.GET)
    public @ResponseBody
    ModelAndView setHealthField(
            @RequestParam(value = "fieldId", required = true) Integer fieldId, @RequestParam(value = "fieldText", required = true) String fieldText,
            @RequestParam(value = "cw", required = true) Integer cw, @RequestParam(value = "CWText", required = true) String cwText,
            @RequestParam(value = "validationId", required = true) Integer validationId, @RequestParam(value = "validationName", required = true) String validationName,
            @RequestParam(value = "requiredField", required = true) boolean requiredField, HttpSession session
    ) throws Exception {

        int dspPos = healthFields.size() + 1;

        if (cw == null) {
            cw = 0;
            cwText = null;
        }

        programHealthDataElements field = new programHealthDataElements();
        field.setProgramId((Integer) session.getAttribute("programId"));
        field.setCrosswalkId(cw);
        field.setCwName(cwText);
        field.setFieldId(fieldId);
        field.setFieldName(fieldText);
        field.setValidationId(validationId);
        field.setValidationName(validationName);
        field.setRequiredField(requiredField);
        field.setDspPos(dspPos);

        healthFields.add(field);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/programs/dataElements/existingFields");
        mav.addObject("existingFields", healthFields);

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

        if ("demo".equals(fieldType)) {
            Iterator<programDemoDataElements> it = demoFields.iterator();

            int currdspOrder;

            while (it.hasNext()) {
                programDemoDataElements field = it.next();
                if (field.getFieldId() == fieldId && field.getDspPos() == dspOrder) {
                    demoFields.remove(field);
                } else if (field.getDspPos() > dspOrder) {
                    currdspOrder = field.getDspPos();
                    field.setDspPos(currdspOrder - 1);
                }
            }
        } else {
            Iterator<programHealthDataElements> it = healthFields.iterator();

            int currdspOrder;

            while (it.hasNext()) {
                programHealthDataElements field = it.next();
                if (field.getFieldId() == fieldId && field.getDspPos() == dspOrder) {
                    healthFields.remove(field);
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
            Iterator<programDemoDataElements> it = demoFields.iterator();

            while (it.hasNext()) {
                programDemoDataElements field = it.next();
                if (field.getDspPos() == currdspOrder) {
                    field.setDspPos(newdspOrder);
                } else if (field.getDspPos() == newdspOrder) {
                    field.setDspPos(currdspOrder);
                }
            }
        } else {
            Iterator<programHealthDataElements> it = healthFields.iterator();

            while (it.hasNext()) {
                programHealthDataElements field = it.next();
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
     * The '/saveProgramDemoFields' POST request will submit the selected fields and save it to the data base.
     *
     */
    @RequestMapping(value = "/saveProgramDemoFields", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Integer saveProgramDemoFields(HttpSession session) throws Exception {

        //Delete all the data translations before creating
        //This will help with the jquery removing translations
        programmanager.deleteDemoFields((Integer) session.getAttribute("programId"));

        //Loop through the list of translations
        for (programDemoDataElements field : demoFields) {
            programmanager.saveDemoFields(field);
        }

        return 1;
    }

    /**
     * The '/saveProgramHealthFields' POST request will submit the selected fields and save it to the data base.
     *
     */
    @RequestMapping(value = "/saveProgramHealthFields", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Integer saveProgramHealthFields(HttpSession session) throws Exception {

        //Delete all the data translations before creating
        //This will help with the jquery removing translations
        programmanager.deleteHealthFields((Integer) session.getAttribute("programId"));

        //Loop through the list of translations
        for (programHealthDataElements field : healthFields) {
            programmanager.saveHealthFields(field);
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
     * The '/{programName}/mpi-algorithms' GET request will display the program MPI Algorithms.
     *
     * @param programName	The {programName} will be the program name with spaces removed.
     *
     * @return	Will return the MPI Algorithms page.
     *
     * @throws Exception
     *
     */
    @RequestMapping(value = "/{programName}/mpi-algorithms", method = RequestMethod.GET)
    public ModelAndView programMPIAlgorithms(HttpSession session) throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/mpialgorithms");
        mav.addObject("id", session.getAttribute("programId"));

        program programDetails = programmanager.getProgramById((Integer) session.getAttribute("programId"));
        mav.addObject("programDetails", programDetails);

        /* Get a list of MPI Algorithms */
        List<programMPI> mpiAlgorithms = programmanager.getProgramMPIAlgorithms((Integer) session.getAttribute("programId"));
        
        if(!mpiAlgorithms.isEmpty()) {
            for(programMPI mpi : mpiAlgorithms) {
                List<programMPIFields> fields = programmanager.getProgramMPIFields(mpi.getId());
                
                for(programMPIFields field : fields) {
                    //Get the field name by id
                    String fieldName = dataelementmanager.getDemoFieldName(field.getFieldId());
                    field.setFieldName(fieldName);
                }
                
                mpi.setFields(fields);
            }
        }
        
        mav.addObject("mpiAlgorithms", mpiAlgorithms);

        return mav;

    }
    
    /**
     * The '/{programName}/algorithm.create' GET request will be used to create a new MPI Algorithm
     *
     * @return The blank MPI Algorithm page
     *
     * @Objects (1) An object that will hold the blank MPI Algorithm
     */
    @RequestMapping(value = "/{programName}/algorithm.create", method = RequestMethod.GET)
    @ResponseBody public ModelAndView newMPIAlgorithmForm(HttpSession session) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/programs/mpi/details");

        //Create a new blank provider.
        programMPI mpi = new programMPI();
        mpi.setProgramId((Integer) session.getAttribute("programId"));
        
        /* Get a list of available fields */
        List<programDemoDataElements> programdemoFields = programmanager.getProgramDemoFields((Integer) session.getAttribute("programId"));

        String fieldName;

        for (programDemoDataElements field : programdemoFields) {
            //Get the field name by id
            fieldName = dataelementmanager.getDemoFieldName(field.getFieldId());
            field.setFieldName(fieldName);

        }
        mav.addObject("availableFields", programdemoFields);

        mav.addObject("btnValue", "Create");
        mav.addObject("mpidetails", mpi);

        return mav;
    }
    
    
    /**
     * The '/{programName}/create_mpialgorithm' POST request will handle submitting the new MPI Algorithm.
     *
     * @param mpidetails    The object containing the MPI Algorithm form fields
     * @param result        The validation result
     * @param redirectAttr	The variable that will hold values that can be read after the redirect
     *
     * @return	Will return the MPI Algorithm list page on "Save" Will return the MPI Algorithm form page on error
     *
     * @Objects	(1) The object containing all the information for the clicked org
     * @throws Exception
     */
    @RequestMapping(value = "/{programName}/create_mpialgorithm", method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView createMPIAlgorithm(@Valid @ModelAttribute(value = "mpidetails") programMPI mpidetails, BindingResult result,  @RequestParam(value = "fieldIds", required = true) List<Integer> fieldIds, @RequestParam(value = "fieldAction", required = true) List<String> fieldAction, HttpSession session) throws Exception {

        
        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/sysAdmin/programs/mpi/details");
            /* Get a list of available fields */
            List<programDemoDataElements> programdemoFields = programmanager.getProgramDemoFields((Integer) session.getAttribute("programId"));

            String fieldName;

            for (programDemoDataElements field : programdemoFields) {
                //Get the field name by id
                fieldName = dataelementmanager.getDemoFieldName(field.getFieldId());
                field.setFieldName(fieldName);

            }
            mav.addObject("availableFields", programdemoFields);
            mav.addObject("btnValue", "Create");
            return mav;
        }


        Integer mpiId = programmanager.createMPIAlgorithm(mpidetails);
        
        int i = 0;
        for(Integer fieldId : fieldIds) {
            programMPIFields newField = new programMPIFields();
            newField.setFieldId(fieldId);
            newField.setMpiId(mpiId);
            
            String selFieldAction = fieldAction.get(i);
            newField.setAction(selFieldAction);
            
            programmanager.createMPIAlgorithmFields(newField);
            
            i+=1;
        }

        ModelAndView mav = new ModelAndView("/sysAdmin/programs/mpi/details");
        mav.addObject("success", "algorithmCreated");
        return mav;
    }
    
   /**
     * The '/{programName}/update_mpialgorithm' POST request will handle submitting the selected MPI Algorithm changes.
     *
     * @param mpidetails    The object containing the MPI Algorithm form fields
     * @param result        The validation result
     * @param redirectAttr	The variable that will hold values that can be read after the redirect
     *
     * @return	Will return the MPI Algorithm list page on "Save" Will return the MPI Algorithm form page on error
     *
     * @Objects	(1) The object containing all the information for the clicked org
     * @throws Exception
     */
    @RequestMapping(value = "/{programName}/update_mpialgorithm", method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView updateMPIAlgorithm(@Valid @ModelAttribute(value = "mpidetails") programMPI mpidetails, BindingResult result,  @RequestParam(value = "fieldIds", required = true) List<Integer> fieldIds, @RequestParam(value = "fieldAction", required = true) List<String> fieldAction, HttpSession session) throws Exception {

        
        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/sysAdmin/programs/mpi/details");
            /* Get a list of available fields */
            List<programDemoDataElements> programdemoFields = programmanager.getProgramDemoFields((Integer) session.getAttribute("programId"));

            String fieldName;

            for (programDemoDataElements field : programdemoFields) {
                //Get the field name by id
                fieldName = dataelementmanager.getDemoFieldName(field.getFieldId());
                field.setFieldName(fieldName);

            }
            mav.addObject("availableFields", programdemoFields);
            mav.addObject("btnValue", "Create");
            return mav;
        }


        programmanager.updateMPIAlgorithm(mpidetails);
        
        int i = 0;
        for(Integer fieldId : fieldIds) {
            programMPIFields newField = new programMPIFields();
            newField.setFieldId(fieldId);
            newField.setMpiId(mpidetails.getId());
            
            String selFieldAction = fieldAction.get(i);
            newField.setAction(selFieldAction);
            
            programmanager.createMPIAlgorithmFields(newField);
            
            i+=1;
        }

        ModelAndView mav = new ModelAndView("/sysAdmin/programs/mpi/details");
        mav.addObject("success", "algorithmUpdated");
        return mav;
    }

    /**
     * The '/{programName}/algorithm.edit' GET request will be used to create a new MPI Algorithm
     *
     * @return The blank MPI Algorithm page
     *
     * @Objects (1) An object that will hold the blank MPI Algorithm
     */
    @RequestMapping(value = "/{programName}/algorithm.edit", method = RequestMethod.GET)
    @ResponseBody public ModelAndView editMPIAlgorithmForm(@RequestParam Integer mpiId, HttpSession session) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/programs/mpi/details");

        //Create a new blank provider.
        programMPI mpi = programmanager.getMPIAlgorithm(mpiId);
        
        /* Get a list of available fields */
        List<programDemoDataElements> programdemoFields = programmanager.getProgramDemoFields((Integer) session.getAttribute("programId"));

        String fieldName;

        for (programDemoDataElements field : programdemoFields) {
            //Get the field name by id
            fieldName = dataelementmanager.getDemoFieldName(field.getFieldId());
            field.setFieldName(fieldName);
        }
        mav.addObject("availableFields", programdemoFields);
        
        List<programMPIFields> fields = programmanager.getProgramMPIFields(mpiId);
        
        for(programMPIFields field : fields) {
            //Get the field name by id
            String selfieldName = dataelementmanager.getDemoFieldName(field.getFieldId());
            field.setFieldName(selfieldName);
        }
        mav.addObject("selFields", fields);

        mav.addObject("btnValue", "Update");
        mav.addObject("mpidetails", mpi);

        return mav;
    }
    
    /**
     * The '/{programName}/remvoeAlgorithmField.do' POST request will remove the selected field for the passed in
     * MPI Algorithm.
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
                User userDetails = usermanager.getUserById(admin.getUserId());
                userDetails.setTimesloggedIn(usermanager.findTotalLogins(admin.getUserId()));
           
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
        adminprogram.setUserId(adminId);
        
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
        details.setUserId(adminId);
       
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
