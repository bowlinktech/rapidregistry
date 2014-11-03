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
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.bowlink.rr.security.encryptObject;
import com.bowlink.rr.security.decryptObject;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

/**
 *
 * @author chadmccue
 */
@Controller
@RequestMapping("/programAdmin/staff")
public class staffController {
    
    @Autowired
    userManager usermanager;
    
    private String topSecret = "What goes up but never comes down";
    
    private String firstName = "";
    private String lastName = "";
    
    /**
     * The '' GET request will display the system administrators.
     *
     * @return	Will return the system admin list page.
     *
     * @throws Exception
     *
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView getstaffMembers(HttpSession session, @RequestParam String clear) throws Exception {
        
        if(clear == "yes") {
            firstName = "";
            lastName = "";
        }
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/staffMembers");

        List<User> staffMembers = usermanager.searchStaffMembers((Integer) session.getAttribute("selprogramId"), firstName, lastName);
        
        encryptObject encrypt = new encryptObject();
        Map<String,String> map;
        for(User user : staffMembers) {
            
            //Encrypt the use id to pass in the url
            map = new HashMap<String,String>();
            map.put("id",Integer.toString(user.getId()));
            map.put("topSecret",topSecret);
            
            String[] encrypted = encrypt.encryptObject(map);
            
            user.setEncryptedId(encrypted[0]);
            user.setEncryptedSecret(encrypted[1]);
        }
        
        mav.addObject("staffMembers", staffMembers);

        return mav;

    }
    
    /**
     * The '/staffmember.create' GET request will be used to create a new program staff member
     *
     * @return The blank staff member page
     *
     * @Objects (1) An object that will hold the blank program admin form.
     */
    @RequestMapping(value = "/staffmember.create", method = RequestMethod.GET)
    @ResponseBody public ModelAndView newStaffMemberForm(HttpSession session) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/programAdmin/staff/newStaffMember");

        //Create a new blank staff member.
        User user = new User();
        user.setRoleId(3);
       
        /* Get a list of user types */
        List userTypes = usermanager.getUserTypes();
        
        mav.addObject("btnValue", "Create");
        mav.addObject("staffdetails", user);
        mav.addObject("userTypes", userTypes);
        
        return mav;
    }
    
    /**
     * The '/create_staffmember' POST request will handle submitting the new staff member.
     *
     * @param admindetails    The object containing the staff member form fields
     * @param result          The validation result
     * @param redirectAttr    The variable that will hold values that can be read after the redirect
     *
     * @return	Will send the program admin to the details of the new staff member
     
     * @throws Exception
     */
    @RequestMapping(value = "/create_staffmember", method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView saveStaffMember(@Valid @ModelAttribute(value = "admindetails") User admindetails, BindingResult result, HttpSession session) throws Exception {

        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/programAdmin/staff/newStaffMember");
            mav.addObject("btnValue", "Create");
            return mav;
        }
        
        Integer adminId = usermanager.createUser(admindetails);
        
        Map<String,String> map = new HashMap<String,String>();
        map.put("id",Integer.toString(adminId));
        map.put("topSecret",topSecret);
        
        encryptObject encrypt = new encryptObject();

        String[] encrypted = encrypt.encryptObject(map);

        ModelAndView mav = new ModelAndView(new RedirectView("/programAdmin/staff/details?i="+encrypted[0]+"v="+encrypted[1]));
        return mav;

    }
    
    /**
     * The '/details' GET request will display the selected staff member details page.
     * 
     * @param i The encrypted url value containing the selected user id
     * @param v The encrypted url value containing the secret decode key
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/details", method = RequestMethod.GET)
    ModelAndView getStaffDetails(@RequestParam String i, @RequestParam String v) throws Exception {
        
        /* Decrypt the url */
        decryptObject decrypt = new decryptObject();
        
        Object obj = decrypt.decryptObject(i, v);
        
        String[] result = obj.toString().split((","));
        
        int userId = Integer.parseInt(result[0].substring(4));
        
        User staffdetails = usermanager.getUserById(userId);
        
        /* Get a list of user types */
        List userTypes = usermanager.getUserTypes();
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/staffMemberDetails");
        mav.addObject("staffdetails", staffdetails);
        mav.addObject("v", v);
        mav.addObject("userId", i);
        mav.addObject("userTypes", userTypes);
        
        return mav;
        
    }
}
