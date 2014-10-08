/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.controller;

import com.bowlink.rr.model.modules;
import com.bowlink.rr.model.program;
import com.bowlink.rr.service.moduleManager;
import com.bowlink.rr.service.programManager;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

/**
 *
 * @author chadmccue
 */
@Controller
@RequestMapping("/sysAdmin/programs/{programName}")
public class programModules {
    
    @Autowired
    moduleManager modulemanager;
    
    @Autowired
    programManager programmanager;
    
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
    @RequestMapping(value = "/program-modules", method = RequestMethod.GET)
    public ModelAndView programModules(HttpSession session) throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/programModules");
        mav.addObject("id", session.getAttribute("programId"));

        program programDetails = programmanager.getProgramById((Integer) session.getAttribute("programId"));
        mav.addObject("programDetails", programDetails);

        /* Get a list of modules the program uses */
        List<Integer> usedModules = modulemanager.getModulesByProgram((Integer) session.getAttribute("programId"));

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
     * The '/program-modules' POST request will save the program module form.
     *
     * @param List<Imteger>	The list of moduleIds to use.
     *
     * @return	Will return the program details page.
     *
     * @throws Exception
     *
     */
    @RequestMapping(value = "/program-modules", method = RequestMethod.POST)
    public ModelAndView saveprogramModules(@RequestParam List<Integer> moduleIds, @RequestParam String action, RedirectAttributes redirectAttr, HttpSession session) throws Exception {

        Integer selProgramId = (Integer) session.getAttribute("programId");

        if (moduleIds.isEmpty()) {
            modulemanager.deleteProgramModules(selProgramId);
        } else {
            modulemanager.deleteProgramModules(selProgramId);

            for (Integer moduleId : moduleIds) {

                com.bowlink.rr.model.programModules module = new com.bowlink.rr.model.programModules();

                module.setProgramId(selProgramId);
                module.setModuleId(moduleId);

                modulemanager.saveProgramModules(module);
            }
        }

        redirectAttr.addFlashAttribute("savedStatus", "updatedprogrammodules");

        if (action.equals("save")) {
            ModelAndView mav = new ModelAndView(new RedirectView("program-modules"));
            return mav;
        } else {
            ModelAndView mav = new ModelAndView(new RedirectView("../../programs"));
            return mav;
        }

    }
    
}
