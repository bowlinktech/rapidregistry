/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.controller;

import com.bowlink.rr.model.program;
import com.bowlink.rr.service.patientSharing;
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
public class programPatientSharing {
    
    @Autowired
    patientSharing patientsharing;
    
    @Autowired
    programManager programmanager;
    
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
    @RequestMapping(value = "/patient-sharing", method = RequestMethod.GET)
    public ModelAndView programPatientSharing(HttpSession session) throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/patientSharing");
        mav.addObject("id", session.getAttribute("programId"));

        program programDetails = programmanager.getProgramById((Integer) session.getAttribute("programId"));
        mav.addObject("programDetails", programDetails);

        /* Get a list of programs the program is sharing with */
        List<Integer> sharingWith = patientsharing.getSharedPrograms((Integer) session.getAttribute("programId"));

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
     * The '/patient-sharing' POST request will save the patient sharing form.
     *
     * @param List<Imteger>	The list of programIds to share with.
     *
     * @return	Will return the program details page.
     *
     * @throws Exception
     *
     */
    @RequestMapping(value = "/patient-sharing", method = RequestMethod.POST)
    public ModelAndView saveprogramPatientSharing(@RequestParam List<Integer> programIds, @RequestParam String action, RedirectAttributes redirectAttr, HttpSession session) throws Exception {

        Integer selProgramId = (Integer) session.getAttribute("programId");

        if (programIds.isEmpty()) {
            patientsharing.deletePatientSharing(selProgramId);
        } else {
            patientsharing.deletePatientSharing(selProgramId);

            for (Integer programId : programIds) {

                com.bowlink.rr.model.programPatientSharing newpatientshare = new com.bowlink.rr.model.programPatientSharing();

                newpatientshare.setProgramId(selProgramId);
                newpatientshare.setSharingProgramId(programId);

                patientsharing.savePatientSharing(newpatientshare);
            }
        }

        redirectAttr.addFlashAttribute("savedStatus", "updatedpatientsharing");

        if (action.equals("save")) {
            ModelAndView mav = new ModelAndView(new RedirectView("patient-sharing"));
            return mav;
        } else {
            ModelAndView mav = new ModelAndView(new RedirectView("../../programs"));
            return mav;
        }

    }
    
}
