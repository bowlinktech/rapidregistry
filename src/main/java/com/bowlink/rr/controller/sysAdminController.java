/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bowlink.rr.controller;

import com.bowlink.rr.model.User;
import com.bowlink.rr.service.userManager;
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
@RequestMapping("/sysAdmin/system-admins")
public class sysAdminController {
    
    @Autowired
    userManager usermanager;
    
    /**
     * The '' GET request will display the system administrators.
     *
     * @return	Will return the system admin list page.
     *
     * @throws Exception
     *
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView getsystemAdministrators(HttpSession session) throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysadmins");

        List<User> systemAdmins = usermanager.getUsersByRoleId(1);
        
        if(!systemAdmins.isEmpty()) {
            for(User user : systemAdmins) {
                user.setTimesloggedIn(usermanager.findTotalLogins(user.getId()));
            }
        }
        
        mav.addObject("sysAdmins", systemAdmins);

        return mav;

    }
    
    /**
     * The '/administrator.create' GET request will be used to create a new system Administrator
     *
     * @return The blank system administrator page
     *
     * @Objects (1) An object that will hold the blank program admin form.
     */
    @RequestMapping(value = "/administrator.create", method = RequestMethod.GET)
    @ResponseBody public ModelAndView newAdministratorForm(HttpSession session) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/sysAdmins/details");

        //Create a new blank provider.
        User user = new User();
        user.setRoleId(1);
       
        mav.addObject("btnValue", "Create");
        mav.addObject("admindetails", user);
        
        return mav;
    }
    
    /**
     * The '/create_systemadmin' POST request will handle submitting the new system administrator.
     *
     * @param admindetails    The object containing the system administrator form fields
     * @param result        The validation result
     * @param redirectAttr	The variable that will hold values that can be read after the redirect
     *
     * @return	Will return the system administrators list page on "Save" Will return the system administrator form page on error
     
     * @throws Exception
     */
    @RequestMapping(value = "/create_systemadmin", method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView saveAdmin(@Valid @ModelAttribute(value = "admindetails") User admindetails, BindingResult result, HttpSession session) throws Exception {

        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/sysAdmin/sysAdmins/details");
            mav.addObject("btnValue", "Create");
            return mav;
        }
        
        Integer adminId = usermanager.createUser(admindetails);

        ModelAndView mav = new ModelAndView("/sysAdmin/sysAdmins/details");
        mav.addObject("success", "adminCreated");
        return mav;
    }
    
    /**
     * The '/administrator.edit' GET request will be used to edit the selected system Administrator
     *
     * @return The program administrator page
     *
     * @Objects (1) An object that will hold the selected program admin details
     */
    @RequestMapping(value = "/administrator.edit", method = RequestMethod.GET)
    @ResponseBody public ModelAndView editAdministratorForm(@RequestParam Integer adminId, HttpSession session) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/sysAdmin/sysAdmins/details");

        //Create a new blank provider.
        User userDetails = usermanager.getUserById(adminId);
       
        userDetails.setTimesloggedIn(usermanager.findTotalLogins(adminId));
        
        mav.addObject("btnValue", "Update");
        mav.addObject("admindetails", userDetails);
        
        return mav;
    }
    
    /**
     * The '/update_systemadmin' POST request will handle submitting the system administrator changes.
     *
     * @param admindetails    The object containing the system administrator form fields
     * @param result        The validation result
     * @param redirectAttr	The variable that will hold values that can be read after the redirect
     *
     * @return	Will return the system administrators list page on "Save" Will return the system administrator form page on error
     
     * @throws Exception
     */
    @RequestMapping(value = "/update_systemadmin", method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView updateProgramAdmin(@Valid @ModelAttribute(value = "admindetails") User admindetails, BindingResult result, HttpSession session) throws Exception {

        
        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/sysAdmin/sysAdmins/details");
            mav.addObject("btnValue", "Update");
            return mav;
        }
        
        usermanager.updateUser(admindetails);

        ModelAndView mav = new ModelAndView("/sysAdmin/sysAdmins/details");
        mav.addObject("success", "adminUpdated");
        return mav;
    }
    
    
    
    /**
     * The '/administrator.remove' POST request will remove the selected
     * admin
     *
     * @return The program administrator list page
     *
     */
    @RequestMapping(value = "/administrator.remove", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Integer removeProgramAssociation(@RequestParam Integer adminId, HttpSession session) throws Exception {
        
        //Create a new blank provider.
        usermanager.deleteUser(adminId);
       
        return 1;
    }
    
    
}
