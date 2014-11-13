/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.controller;

import com.bowlink.rr.model.User;
import com.bowlink.rr.model.program;
import com.bowlink.rr.model.programAdmin;
import com.bowlink.rr.model.userPrograms;
import com.bowlink.rr.model.programModules;
import com.bowlink.rr.model.programOrgHierarchy;
import com.bowlink.rr.model.userProgramHierarchy;
import com.bowlink.rr.model.userProgramModules;
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
import com.bowlink.rr.service.moduleManager;
import com.bowlink.rr.service.orgHierarchyManager;
import com.bowlink.rr.service.programManager;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
    
    @Autowired
    programManager programmanager;
    
    @Autowired
    moduleManager modulemanager;
    
    @Autowired
    orgHierarchyManager orghierarchymanager;
    
    private String topSecret = "What goes up but never comes down";
    
    private String firstName = "";
    private String lastName = "";
    private Integer status = 2;
    private Integer typeId = 0;
    
    /**
     * The '' GET request will display the system administrators.
     *
     * @return	Will return the system admin list page.
     *
     * @throws Exception
     *
     */
    @RequestMapping(value = "")
    public ModelAndView getstaffMembers(HttpSession session, @RequestParam(value = "clear", required = false) String clear, 
            @RequestParam(value = "firstname", required = false) String firstNameSF,
            @RequestParam(value = "lastname", required = false) String lastNameSF,
            @RequestParam(value = "typeId", required = false) Integer typeIdSF,
            @RequestParam(value = "status", required = false) Integer statusSF) throws Exception {
        
        if(clear != null && "yes".equals(clear)) {
            firstName = "";
            lastName = "";
            status = 2;
            typeId = 0;
        }
        else {
            if(firstNameSF != null) {firstName = firstNameSF;}
            if(lastNameSF != null) {lastName = lastNameSF;}
            if(typeIdSF != null) {typeId = typeIdSF;}
            if(statusSF != null) {status = statusSF;}
        }
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/staffMembers");
        
        /* Get a list of user types */
        List userTypes = usermanager.getUserTypes();
        mav.addObject("userTypes", userTypes);
        
        List<User> staffMembers = usermanager.searchStaffMembers((Integer) session.getAttribute("selprogramId"), firstName, lastName, status, typeId);
        
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
            
            for (ListIterator iter = userTypes.listIterator(); iter.hasNext();) {
            
                Object[] row = (Object[]) iter.next();
                
                if(Objects.equals(user.getTypeId(), Integer.valueOf(String.valueOf(row[0])))) {
                    user.setStaffType(String.valueOf(row[1]));
                }
            }
        }
        
        mav.addObject("staffMembers", staffMembers);
        mav.addObject("firstNameSF", firstName);
        mav.addObject("lastNameSF", lastName);
        mav.addObject("statusSF", status);
        mav.addObject("typeIdSF", typeId);
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
    public @ResponseBody ModelAndView saveStaffMember(@Valid @ModelAttribute(value = "staffdetails") User staffdetails, BindingResult result, HttpSession session) throws Exception {

        /* Get a list of user types */
        List userTypes = usermanager.getUserTypes();
        
        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/programAdmin/staff/newStaffMember");
            mav.addObject("userTypes", userTypes);
            mav.addObject("btnValue", "Create");
            return mav;
        }
        
        /* Check for duplicate email address */
        User existingUser = usermanager.getUserByEmail(staffdetails.getEmail());
        if (existingUser != null ) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/programAdmin/staff/newStaffMember");
            mav.addObject("userTypes", userTypes);
            mav.addObject("existingUser", "The email address is already being used by another user.");
            mav.addObject("btnValue", "Create");

            return mav;
        }
        
        Integer adminId = usermanager.createUser(staffdetails);
        
        /* associate user to program */
        programAdmin program = new programAdmin();
        program.setProgramId((Integer) session.getAttribute("selprogramId"));
        program.setsystemUserId(adminId);
        programmanager.saveAdminProgram(program);
        
        Map<String,String> map = new HashMap<String,String>();
        map.put("id",Integer.toString(adminId));
        map.put("topSecret",topSecret);
        
        encryptObject encrypt = new encryptObject();

        String[] encrypted = encrypt.encryptObject(map);
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/programAdmin/staff/newStaffMember");
        mav.addObject("encryptedURL", "?i="+encrypted[0]+"&v="+encrypted[1]);
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
    public ModelAndView getStaffDetails(@RequestParam String i, @RequestParam String v) throws Exception {
        
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
    
    /**
     * The '/details' POST request will submit the staff member changes once all required fields are checked, the system will also check to make sure the users email address is not already in use.
     *
     * @param staffdetails	The object holding the staff member form fields
     * @param result            The validation result
     * @param redirectAttr	The variable that will hold values that can be read after the redirect
     * @param action            The variable that holds which button was pressed
     * @param userId            The encrypted value of the staff member being updated
     * @param v                 The encrypted secret
     *
     * @return	Will return the staff member list page on "Save & Close" Will return the staff member details page on "Save" Will return the staff member create page on error
     * @throws Exception
     */
    @RequestMapping(value = "/details", method = RequestMethod.POST)
    public ModelAndView updateStaffDetails(@Valid @ModelAttribute(value = "staffdetails") User staffdetails, BindingResult result, 
            RedirectAttributes redirectAttr, 
            @RequestParam String action, 
            @RequestParam String i, 
            @RequestParam String v, 
            HttpSession session,
            HttpServletResponse response) throws Exception {
            
        /* Get a list of user types */
        List userTypes = usermanager.getUserTypes();
        
        /* Decrypt the url */
        decryptObject decrypt = new decryptObject();
        
        Object obj = decrypt.decryptObject(i, v);
        
        String[] decryptResult = obj.toString().split((","));
        
        int readableUserId = Integer.parseInt(decryptResult[0].substring(4));
        
        staffdetails.setId(readableUserId);
        
        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/staffMemberDetails");
            mav.addObject("userTypes", userTypes);
            return mav;
        }
        
        /* Check for duplicate email address */
        User existingUser = usermanager.getUserByEmail(staffdetails.getEmail());
        if (existingUser != null && existingUser.getId() != readableUserId) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/staffMemberDetails");
            mav.addObject("userTypes", userTypes);
            mav.addObject("existingUser", "The email address is already being used by another user.");

            return mav;
        }
        
        usermanager.updateUser(staffdetails);
        
        if (action.equals("save")) {
            redirectAttr.addFlashAttribute("savedStatus", "updated");
            ModelAndView mav = new ModelAndView(new RedirectView("/programAdmin/staff/details?i="+URLEncoder.encode(i,"UTF-8")+"&v="+URLEncoder.encode(v,"UTF-8")));
            return mav;
        } else {
            ModelAndView mav = new ModelAndView(new RedirectView("/programAdmin/staff"));
            return mav;
        }
    }
    
    
    /**
     * The 'getAssociatedPrograms' GET request will return a list of programs the user is associated with.
     * 
     * @param i The encrypted userId
     * @param v The encrypted secret
     * @return  This function will return the associated programs view.
     * @throws Exception 
     */
    @RequestMapping(value = "/getAssociatedPrograms.do", method = RequestMethod.GET)
    public @ResponseBody ModelAndView getAssociatedPrograms(@RequestParam String i, @RequestParam String v) throws Exception {
        
        /* Decrypt the url */
        int userId = decryptURLParam(i,v);
        
        /* Get associated programs */
        List<userPrograms> programs = usermanager.getUserPrograms(userId);
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/programAdmin/staff/associatedPrograms");
        mav.addObject("programs", programs);
        
        return mav;
        
    }
    
    /**
     * The 'associateNewProgram' GET request will open the new program association modal.
     * 
     * @param i The encrypted userId
     * @param v The encrypted secret
     * @return  This function will return the new associated programs view.
     * @throws Exception 
     */
    @RequestMapping(value = "/associateNewProgram.do", method = RequestMethod.GET)
    public @ResponseBody ModelAndView associateNewProgram(@RequestParam String i, @RequestParam String v) throws Exception {
        
        /* Decrypt the url */
        int userId = decryptURLParam(i,v);
        
        /* Get associated programs */
        List<program> programs = programmanager.getAvailbleProgramsForUser(userId);
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/programAdmin/staff/newProgram");
        mav.addObject("programs", programs);
        
        mav.addObject("v", v);
        mav.addObject("userId", i);
        
        return mav;
        
    }
    
    /**
     * The 'saveProgramUser' POST request will submit the selected program for the user.
     * 
     * @param i The encrypted userId
     * @param v The encrypted secret
     * @param modules   The selected program modules.
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/saveProgramUser.do", method = RequestMethod.POST)
    public @ResponseBody ModelAndView saveProgramUser(@RequestParam String i, @RequestParam String v, @RequestParam Integer program, @RequestParam List<String> hierarchyValues, @RequestParam List<Integer> programModules, HttpSession session) throws Exception {
        
        /* Decrypt the url */
        int userId = decryptURLParam(i,v);
        
        User userDetails = (User) session.getAttribute("userDetails");
        
        /* Save the new program */
        programAdmin userProgram = new programAdmin();
        userProgram.setProgramId(program);
        userProgram.setsystemUserId(userId);
        userProgram.setCreatedBy(userDetails.getId());
        
        programmanager.saveAdminProgram(userProgram);
        
        if(!programModules.isEmpty()) {
            for(Integer module : programModules) {
                userProgramModules userModule = new userProgramModules();
                userModule.setSystemUserId(userId);
                userModule.setProgramId(program);
                userModule.setModuleId(module);
                
                modulemanager.saveUsedModulesByUser(userModule);
            }
        }
        
        if(!hierarchyValues.isEmpty()) {
            for(String hierarchyValue : hierarchyValues) {
                
                if(hierarchyValue.contains("-")) {
                    String[] ids = hierarchyValue.split("-");
                
                    userProgramHierarchy hierarchy = new userProgramHierarchy();
                    hierarchy.setProgramId(program);
                    hierarchy.setSystemUserId(userId);
                    hierarchy.setProgramHierarchyId(Integer.parseInt(ids[0]));
                    hierarchy.setOrgHierarchyDetailId(Integer.parseInt(ids[1]));

                    orghierarchymanager.saveUserProgramHierarchy(hierarchy);
                }
                
            }
        }
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/programAdmin/staff/programModules");
        mav.addObject("encryptedURL", "?i="+i+"&v="+v);
        
        return mav;
    }
    
    /**
     * The 'removeUserProgram' POST request will submit the selected program for the user.
     * 
     * @param i The encrypted userId
     * @param v The encrypted secret
     * @param modules   The selected program modules.
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/removeUserProgram.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Integer removeUserProgram(@RequestParam String i, @RequestParam String v, @RequestParam Integer programId, HttpSession session) throws Exception {
        
        /* Decrypt the url */
        int userId = decryptURLParam(i,v);
        
        usermanager.removeProgram(userId, programId);
        
        return 1;
    }
    
    
    /**
     * The 'getProgramModules' GET request will return a list of modules associated to the program and check to see if the user has access to the module.
     * 
     * @param i The encrypted userId
     * @param v The encrypted secret
     * @return  This function will return the program module model
     * @throws Exception 
     */
    @RequestMapping(value = "/getProgramModules.do", method = RequestMethod.GET)
    public @ResponseBody ModelAndView getProgramModules(@RequestParam String i, @RequestParam String v, @RequestParam Integer programId, HttpSession session) throws Exception {
        
        /* Decrypt the url */
        int userId = decryptURLParam(i,v);
        
        /* Get a list of modules for the program */
        List<programModules> modules = modulemanager.getUsedModulesByProgram(programId);
        
        /* Get a list of modules for the user */
        List<userProgramModules> userModules = modulemanager.getUsedModulesByUser(programId, userId);
        
        if(!userModules.isEmpty()) {
            for(userProgramModules usermodule : userModules) {
                for(programModules module : modules) {
                    if(Objects.equals(module.getModuleId(), usermodule.getModuleId())) {
                        module.setUseModule(true);
                    }
                }
            }
        }
       
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/programAdmin/staff/programModules");
        mav.addObject("userId", i);
        mav.addObject("v", v);
        mav.addObject("programModules", modules);
        
        return mav;
        
    }
    
    /**
     * The 'saveProgramUserModules' POST request will submit the selected program modules for the user and program.
     * 
     * @param i The encrypted userId
     * @param v The encrypted secret
     * @param modules   The selected program modules.
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/saveProgramUserModules.do", method = RequestMethod.POST)
    public @ResponseBody ModelAndView saveProgramUserModules(@RequestParam String i, @RequestParam String v, @RequestParam List<Integer> programModules, HttpSession session) throws Exception {
        
        /* Decrypt the url */
        int userId = decryptURLParam(i,v);
        
        /* Clear out current user modules */
        modulemanager.removeUsedModulesByUser((Integer) session.getAttribute("selprogramId"), userId);
        
        if(!programModules.isEmpty()) {
            for(Integer module : programModules) {
                userProgramModules userModule = new userProgramModules();
                userModule.setSystemUserId(userId);
                userModule.setProgramId((Integer) session.getAttribute("selprogramId"));
                userModule.setModuleId(module);
                
                modulemanager.saveUsedModulesByUser(userModule);
            }
        }
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/programAdmin/staff/programModules");
        mav.addObject("encryptedURL", "?i="+i+"&v="+v);
        
        return mav;
    }
    
    
    /**
     * The 'getProgramDepartments.do' GET request will return a list of departments associated to the program and the user.
     * 
     * @param i The encrypted userId
     * @param v The encrypted secret
     * @param programId The clicked program
     * @return  This function will return the program department model
     * @throws Exception 
     */
    @RequestMapping(value = "/getProgramDepartments.do", method = RequestMethod.GET)
    public @ResponseBody ModelAndView getProgramDepartments(@RequestParam String i, @RequestParam String v, @RequestParam Integer programId, HttpSession session) throws Exception {
        
        /* Decrypt the url */
        int userId = decryptURLParam(i,v);
        
        List<programOrgHierarchy> getProgramOrgHierarchy = orghierarchymanager.getProgramOrgHierarchy(programId);
        
        /* Get a list of departments for the user */
        List<userProgramHierarchy> userDepartments = orghierarchymanager.getUserProgramHierarchy(programId, userId);
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/programAdmin/staff/programDepartments");
        mav.addObject("userId", i);
        mav.addObject("v", v);
        mav.addObject("hierarchyHeadings", getProgramOrgHierarchy);
        mav.addObject("userDepartments", userDepartments);
        
        return mav;
        
    }
    
    /**
     * The 'decryptURLParam' will take the encryptd url parameters and return the userid as an integer.
     * 
     * @param i The encrypted userId
     * @param v The encrypted secret.
     * @return  This function will return the decrypted userid.
     * @throws Exception 
     */
    public int decryptURLParam(@RequestParam String i, @RequestParam String v) throws Exception {
        
        /* Decrypt the url */
        decryptObject decrypt = new decryptObject();
        
        Object obj = decrypt.decryptObject(URLDecoder.decode(i, "UTF-8"), URLDecoder.decode(v, "UTF-8"));
        
        String[] result = obj.toString().split((","));
        
        int userId = Integer.parseInt(result[0].substring(4));
        
        return userId;
    }
    
}

