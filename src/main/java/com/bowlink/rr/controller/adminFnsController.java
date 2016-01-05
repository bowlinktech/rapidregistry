package com.bowlink.rr.controller;


import java.util.List;
import com.bowlink.rr.model.User;
import com.bowlink.rr.model.program;
import com.bowlink.rr.model.programUploadTypes;
import com.bowlink.rr.model.programUploads;
import com.bowlink.rr.service.fileManager;
import com.bowlink.rr.service.importManager;
import com.bowlink.rr.service.programManager;
import com.bowlink.rr.service.userManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gchan
 */
@Controller
@RequestMapping(value={"/sysAdmin/adminFns"})
public class adminFnsController {
    
    @Autowired
    importManager importmanager;
    
    @Autowired
    fileManager filemanager;
    
    @Autowired
    programManager programmanager;
    
    @Autowired
    userManager usermanager;
    
    /**
     * The '/importfile' request will serve up the user list drop down of program types so the 
     * admin can upload as
     *
     * @param request
     * @param response
     * 
     * @return	user list
     * @throws Exception
     */
    @RequestMapping(value = "/importfile", method = RequestMethod.GET)
    public ModelAndView importScreen(HttpServletRequest request, HttpServletResponse response, 
    		HttpSession session, RedirectAttributes redirectAttr) throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/importfile");
        List<User> userList = importmanager.getUsersForProgramUploadTypes(1);
        mav.addObject("users", userList);

        return mav;
    }
    
    
    /**
     * The '/getTableCols.do' GET request will return a list of columns for the passed in table name
     *
     * @param tableName
     *
     * @return The function will return a list of column names.
     */
    @RequestMapping(value = "/getProgramUploadTypes.do", method = RequestMethod.POST)
    public @ResponseBody ModelAndView getProgramUploadTypes(@RequestParam(value = "userId", required = true) Integer userId)
    		throws Exception {
    	
    	ModelAndView mav = new ModelAndView();
    	mav.setViewName("/sysAdmin/adminFns/programUploadDropDown");
    	List <programUploadTypes> putList =  importmanager.getProgramUploadTypesByUserId (userId, 1);
    	mav.addObject("putList", putList);    	
    	return mav;
    }
    
        
    /**
     * The '/submitImportFile' POST request will submit the new file and run the file through various validations. If a single validation fails the batch will be put in a error validation status and the file will be removed from the system. The user will receive an error message on the screen letting them know which validations have failed and be asked to upload a new file.
     *
     * The following validations will be taken place. - File is not empty - Proper file type (as determined in the configuration set up) - Proper delimiter (as determined in the configuration set up) - Does not exceed file size (as determined in the configuration set up)
     */
    @RequestMapping(value = "/submitImportFile", method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView submitFileUpload(RedirectAttributes redirectAttr, 
    		HttpSession session, @RequestParam(value = "userId", required = true) Integer userId, 
    		@RequestParam(value = "programUploadTypeId", required = true) Integer programUploadTypeId, 
    		@RequestParam(value = "uploadedFile", required = true) MultipartFile uploadedFile) throws Exception {
    	
    		Integer programUploadId = importmanager.submitUploadFile(userId,programUploadTypeId, uploadedFile);
    	
    		programUploads pu = importmanager.getProgramUpload(programUploadId);
        
        	
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/importfile");
            
            if (pu.getErrors().size() == 0) {
                pu.setStatusId(2);
                importmanager.updateProgramUpload(pu);
                mav.addObject("savedStatus", "uploaded");

            } else {
            	//we query for errors
            	mav.addObject("savedStatus", "error");   
                //we get program upload types for user and set it 
                List <programUploadTypes> puts = importmanager.getProgramUploadTypesByUserId(userId, 1);
                mav.addObject("programUploadTypes", puts);
                mav.addObject("puUserId", pu.getSystemUserId());
                mav.addObject("programUploadTypeId", pu.getProgramUploadTypeId());
                mav.addObject("errorCodes", pu.getErrors());
                
            }
            
            List<User> userList = importmanager.getUsersForProgramUploadTypes(1);
            mav.addObject("users", userList);
           
            return mav;

    }
    
    @RequestMapping(value = "/loginas", method = RequestMethod.GET)
    public ModelAndView loginAsForm(HttpServletRequest request, HttpServletResponse response, 
    		HttpSession session, RedirectAttributes redirectAttr) throws Exception {

    	User userDetails = (User) session.getAttribute("userDetails");
    	
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/loginas");
        //we get list of programs
        List<program> programList = programmanager.getAllPrograms();
        mav.addObject("programList", programList);
        mav.addObject("userDetails", userDetails);
        
        return mav;
    }
    
    @RequestMapping(value = "/getProgramUsers.do", method = RequestMethod.POST)
    public @ResponseBody ModelAndView getProgramUsers(@RequestParam(value = "programId", required = true) Integer programId)
    		throws Exception {
    	
    	ModelAndView mav = new ModelAndView();
    	mav.setViewName("/sysAdmin/adminFns/userListDropDown");
    	List <User> userList =  usermanager.getEncryptedtUserListByProgram(programId);
    	mav.addObject("userList", userList); 
    	program programDetails = programmanager.getProgramById(programId);
    	mav.addObject("programURL", programDetails.getUrl()); 
    	return mav;
    }
    
    
    /** login as post check - response body **/
    @RequestMapping(value = "loginAsCheck.do", method = RequestMethod.POST)
    public @ResponseBody
    String checkLoginAsPW(HttpServletRequest request,  
    		Authentication authentication) throws Exception {

        User user = usermanager.getUserByUserNameOnly(authentication.getName());
        boolean okToLoginAs = false;
        
        /** we verify existing password **/
        if (user.getRoleId() == 1 || user.getRoleId() == 4) {
	        try  {
	        	okToLoginAs = usermanager.authenticate(request.getParameter("j_password"), user.getEncryptedPw(), user.getRandomSalt());
	        } catch(Exception ex) {
	        	okToLoginAs = false;
	        }
        }
        
        if (okToLoginAs) {
        	return "pwmatched";
        } else {
        	return "failed";
            
        }
    }
    

}
