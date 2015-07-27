package com.bowlink.rr.controller;

import com.bowlink.rr.model.User;
import com.bowlink.rr.model.algorithmMatchingActions;
import com.bowlink.rr.model.program;
import com.bowlink.rr.model.programAdmin;
import com.bowlink.rr.model.programAvailableTables;
import com.bowlink.rr.model.programOrgHierarchy;
import com.bowlink.rr.model.programPatientEntryMethods;
import com.bowlink.rr.model.surveys;
import com.bowlink.rr.service.activityCodeManager;
import com.bowlink.rr.service.dataElementManager;
import com.bowlink.rr.service.orgHierarchyManager;
import com.bowlink.rr.service.programFormsManager;
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
    
    @Autowired
    programFormsManager programformsmanager;
    
    @Autowired
    orgHierarchyManager orghierarchymanager;
    
    
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
        
        //get the last hierarchy
        List<programOrgHierarchy> programOrgHierarchy = orghierarchymanager.getProgramOrgHierarchy(programDetails.getId());
        String lastHierarchyName = "Program"; 
        if (programOrgHierarchy.size() != 0) {
        	lastHierarchyName = programOrgHierarchy.get(programOrgHierarchy.size() -1 ).getName();
        }
        
        mav.addObject("lastHierarchyName", lastHierarchyName);
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
            String programNameHyphens = program.getProgramName().replace(" ", "-").toLowerCase();
            ModelAndView mav = new ModelAndView(new RedirectView("/sysAdmin/programs/"+programNameHyphens+"/details"));
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
            Integer maxDspPos = programmanager.getPatientEntryMethods((Integer) session.getAttribute("programId")).size()+1;
            mav.addObject("maxDspPos",maxDspPos);
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
    public @ResponseBody ModelAndView saveEntryMethod(@Valid programPatientEntryMethods programPatientEntryMethods, BindingResult result, @RequestParam String action, @RequestParam Integer currdspPos, HttpSession session) throws Exception {

        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/sysAdmin/programs/patientEntryForm");
            return mav;
        }
        
        /* Check to see if dspPos changed, if so need to update the old dsp Pos */
        if(programPatientEntryMethods.getDspPos() != currdspPos) {
            programPatientEntryMethods foundMethod = programmanager.getPatientEntryMethodBydspPos(programPatientEntryMethods.getDspPos(), (Integer) session.getAttribute("programId"));
            if(foundMethod != null) {
                foundMethod.setDspPos(currdspPos);
                programmanager.saveProgramPatientEntryMethod(foundMethod);
            }
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
                
                if(userDetails.getRoleId() == 2) {
                    programAdministrators.add(userDetails);
                }
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
        
        admindetails = usermanager.encryptPW(admindetails);
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
