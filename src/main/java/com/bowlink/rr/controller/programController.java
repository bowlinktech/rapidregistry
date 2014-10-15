package com.bowlink.rr.controller;

import com.bowlink.rr.model.User;
import com.bowlink.rr.model.activityCodes;
import com.bowlink.rr.model.program;
import com.bowlink.rr.model.programActivityCodes;
import com.bowlink.rr.model.programAdmin;
import com.bowlink.rr.model.programAvailableTables;
import com.bowlink.rr.model.programPatientFields;
import com.bowlink.rr.model.programEngagementFields;
import com.bowlink.rr.model.programMCIAlgorithms;
import com.bowlink.rr.model.programMCIFields;
import com.bowlink.rr.model.programPatientEntryMethods;
import com.bowlink.rr.model.programReports;
import com.bowlink.rr.model.reports;
import com.bowlink.rr.model.surveys;
import com.bowlink.rr.service.activityCodeManager;
import com.bowlink.rr.service.dataElementManager;
import com.bowlink.rr.service.programManager;
import com.bowlink.rr.service.reportManager;
import com.bowlink.rr.service.surveyManager;
import com.bowlink.rr.service.userManager;
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
     * The '/{programName}/details' GET request will display the clicked program details page.
     *
     * @param programName   The {programName} will be the program name with spaces removed.
     *
     * @return	Will return the program details page.
     *
     * @throws Exception
     *
     */
    @RequestMapping(value = "/{programName}/details", method = RequestMethod.GET)
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
     * The '/{programName}/details' POST request will submit the program changes once all required fields are checked, the system will also check to make sure the program name is not already in use.
     *
     * @param program	The object holding the program form fields
     * @param result	The validation result
     * @param redirectAttr	The variable that will hold values that can be read after the redirect
     * @param action	The variable that holds which button was pressed
     *
     * @return	Will return the organization list page on "Save & Close" Will return the organization details page on "Save" Will return the organization create page on error
     * @throws Exception
     */
    @RequestMapping(value = "/{programName}/details", method = RequestMethod.POST)
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
            ModelAndView mav = new ModelAndView(new RedirectView("details"));
            return mav;
        } else {
            ModelAndView mav = new ModelAndView(new RedirectView("../../programs"));
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
     * The '/{programName}/activity-codes' GET request will display the program activity code page.
     *
     * @param programName	The {programName} will be the program name with spaces removed.
     *
     * @return	Will return the program details page.
     *
     * @throws Exception
     *
     
    @RequestMapping(value = "/{programName}/activity-codes", method = RequestMethod.GET)
    public ModelAndView programActivityCodes(HttpSession session) throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/activitycodes");
        mav.addObject("id", session.getAttribute("programId"));

        program programDetails = programmanager.getProgramById((Integer) session.getAttribute("programId"));
        mav.addObject("programDetails", programDetails);

        List<activityCodes> activityCodes = activitycodemanager.getActivityCodes(0);

        for (activityCodes code : activityCodes) {

            boolean codeBeingUsed = programmanager.getUsedActivityCodes((Integer) session.getAttribute("programId"), code.getId());

            code.setSelected(codeBeingUsed);

        }

        mav.addObject("availactivityCodes", activityCodes);

        return mav;

    }
*/
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
*/
    /**
     * The '/{programName}/canned-reports' GET request will display the page to associate canned reports to a program.
     *
     * @param programName	The {programName} will be the program name with spaces removed.
     *
     * @return	Will return the program reports page.
     *
     * @throws Exception
     *
     
    @RequestMapping(value = "/{programName}/canned-reports", method = RequestMethod.GET)
    public ModelAndView programReports(HttpSession session) throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/programReports");
        mav.addObject("id", session.getAttribute("programId"));

        program programDetails = programmanager.getProgramById((Integer) session.getAttribute("programId"));
        mav.addObject("programDetails", programDetails);

        List<Integer> useReports = programmanager.getProgramReports((Integer) session.getAttribute("programId"));

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
    */

    /**
     * The '/{programName}/canned-reports' POST request will save the program report form.
     *
     * @param List<Imteger>	The list of reportIds to use.
     *
     * @return	Will return the program details page.
     *
     * @throws Exception
     *
    
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
    */
    /**
     * The '/{programName}/mci-algorithms' GET request will display the program MCI Algorithms.
     *
     * @param programName	The {programName} will be the program name with spaces removed.
     *
     * @return	Will return the MPI Algorithms page.
     *
     * @throws Exception
     *
     
    @RequestMapping(value = "/{programName}/mci-algorithms", method = RequestMethod.GET)
    public ModelAndView programMCIAlgorithms(HttpSession session) throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/mcialgorithms");
        mav.addObject("id", session.getAttribute("programId"));

        program programDetails = programmanager.getProgramById((Integer) session.getAttribute("programId"));
        mav.addObject("programDetails", programDetails);

        
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
    */

}
