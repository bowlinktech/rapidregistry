package com.bowlink.rr.controller;

import com.bowlink.rr.model.modules;
import com.bowlink.rr.model.patientSharing;
import com.bowlink.rr.model.program;
import com.bowlink.rr.model.programModules;
import com.bowlink.rr.service.moduleManager;
import com.bowlink.rr.service.programManager;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
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
            ModelAndView mav = new ModelAndView(new RedirectView(program.getProgramName().replace(" ", "-").toLowerCase()+"/patient-sharing"));
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
     * The '/patient-sharing' GET request will display the patient sharing page.
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
        
        if(!sharingWith.isEmpty()) {
            for(program program : availPrograms) {

                if(sharingWith.contains(program.getId())) {
                    program.setSharing(true);
                }
            }
        }
        
        
        mav.addObject("availPrograms", availPrograms);
        
        
        return mav;

    }
    
    /**
     * The '/patient-sharing' POST request will save the patient sharing form.
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
        
        if(programIds.isEmpty()) {
            programmanager.deletePatientSharing(selProgramId);
        }
        else {
            programmanager.deletePatientSharing(selProgramId);
            
            for(Integer programId : programIds) {
            
                patientSharing newpatientshare = new patientSharing();

                newpatientshare.setProgramId(selProgramId);
                newpatientshare.setSharingProgramId(programId);

                programmanager.savePatientSharing(newpatientshare);
            }
        }
        
        
        redirectAttr.addFlashAttribute("savedStatus", "updatedpatientsharing");

        if (action.equals("save")) {
            ModelAndView mav = new ModelAndView(new RedirectView("program-modules"));
            return mav;
        } else {
            ModelAndView mav = new ModelAndView(new RedirectView("../../programs/"));
            return mav;
        }
        
    }
    
    /**
     * The '/program-modules' GET request will display the page to associate modules to a program.
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
        
        if(!usedModules.isEmpty()) {
            for(modules module : availModules) {

                if(usedModules.contains(module.getId())) {
                    module.setUseModule(true);
                }
            }
        }
        
        mav.addObject("availModules", availModules);
        
        return mav;

    }
    
    /**
     * The '/program-modules' POST request will save the program module form.
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
        
        if(moduleIds.isEmpty()) {
            programmanager.deleteProgramModules(selProgramId);
        }
        else {
            programmanager.deleteProgramModules(selProgramId);
            
            for(Integer moduleId : moduleIds) {
            
                programModules module = new programModules();

                module.setProgramId(selProgramId);
                module.setModuleId(moduleId);

                programmanager.saveProgramModules(module);
            }
        }
        
        
        redirectAttr.addFlashAttribute("savedStatus", "updatedprogrammodules");

        if (action.equals("save")) {
            ModelAndView mav = new ModelAndView(new RedirectView("demo-data-elements"));
            return mav;
        } else {
            ModelAndView mav = new ModelAndView(new RedirectView("../../programs/"));
            return mav;
        }
        
    }

}
