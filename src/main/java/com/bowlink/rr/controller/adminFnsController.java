package com.bowlink.rr.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bowlink.rr.model.User;
import com.bowlink.rr.model.programUploadTypes;
import com.bowlink.rr.model.programUploads;
import com.bowlink.rr.reference.fileSystem;
import com.bowlink.rr.service.fileManager;
import com.bowlink.rr.service.importManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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

    private String archivePath = "/rapidRegistry/archivesIn/";
    
    //files that are ready to be loaded in RR are kept here
    private String processPath = "/rapidRegistry/processFiles/";
    
    //files that are ready to be loaded in RR are kept here
    private String loadPath = "/rapidRegistry/loadFiles/";
    
    
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
    		@RequestParam(value = "programUplaodTypeId", required = true) Integer programUploadTypeId, 
    		@RequestParam(value = "uploadedFile", required = true) MultipartFile uploadedFile) throws Exception {
    	
    	
    	//we get our programUploadType
    	programUploadTypes put = importmanager.getProgramUploadType(programUploadTypeId);
    	put.setDelimChar(importmanager.getDelimiter(put.getFileDelimId()).getDelimChar());
    	put.setFileExt(importmanager.getFileTypes(put.getFileTypeId()).get(0).getFileType());
    	
    	//we assign a batch id
    	DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssS");
        Date date = new Date();
        //adding transport method id to UT batch name - rr_transport method_putypeId_dateTime_uId_1.txt
        String assignedId = new StringBuilder().append("rr_t1_p").append(put.getId()).append("_")
        		.append(dateFormat.format(date)).append("_u").append(userId).toString();
        
        String fileName = uploadedFile.getOriginalFilename();
        
        String fileExt = "";
        if (uploadedFile.getOriginalFilename().lastIndexOf(".") != -1) {
        	fileExt = fileName.substring(uploadedFile.getOriginalFilename().lastIndexOf("."), (uploadedFile.getOriginalFilename().length()));
        }
        
        programUploads pu = new programUploads();
        pu.setAssignedFileName(assignedId + fileExt);
        pu.setAssignedId(assignedId);
        pu.setUploadedFileName(uploadedFile.getOriginalFilename());
        pu.setProgramId(put.getProgramId());
        pu.setProgramUploadTypeId(put.getId());
        pu.setStatusDateTime(date);
        pu.setStatusId(1); // SFV
        pu.setSystemUserId(userId);
        pu.setTransportId(1);
        pu.setDateUploaded(date);
        pu.setTotalInError(0);
        pu.setTotalRows(0);
        Integer programUploadId = importmanager.saveProgramUplaod(pu);
        
    	// we save the file to archivesIn
        fileName = importmanager.saveUploadedFile(pu, uploadedFile);
        
        fileSystem dir = new fileSystem();
        File archiveFile = new File(dir.setPath(archivePath) + pu.getAssignedFileName());
        File loadFile = new File(dir.setPath(loadPath) + pu.getAssignedFileName());
        
        //now we start our checks
        Map<String, String> fileResults = new HashMap<String, String>();
        String decodedString = "";
        if (put.getEncodingId() == 2) {
    		decodedString = filemanager.decodeFileToBase64Binary(archiveFile);
    		if (decodedString == null) {
    			fileResults.put("wrongEncoding", "5");
    		} else  {
	    		//write it to load folder
	    		filemanager.writeFile(loadFile.getAbsolutePath(), decodedString);
    		}
        } else {
        	Files.copy(archiveFile.toPath(), loadFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    	
    	//we check encoding, delmiter, file size etc
    	if (decodedString != null) {
    		fileResults = importmanager.chkUploadBatchFile(put, loadFile);
    	}
        
            List<Integer> errorCodes = new ArrayList<Integer>();

            Object emptyFileVal = fileResults.get("emptyFile");
            if (emptyFileVal != null) {
                errorCodes.add(1);
            }

            Object wrongSizeVal = fileResults.get("wrongSize");
            if (wrongSizeVal != null) {
                errorCodes.add(2);
            }

            Object wrongFileTypeVal = fileResults.get("wrongFileType");
            if (wrongFileTypeVal != null) {
                errorCodes.add(3);
            }

            Object wrongDelimVal = fileResults.get("wrongDelim");
            if (wrongDelimVal != null) {
                errorCodes.add(4);
            }
            
            Object wrongEncoding = fileResults.get("wrongEncoding");
            if (wrongEncoding != null) {
                errorCodes.add(5);
            }

            ModelAndView mav = new ModelAndView();
            mav.setViewName("/importfile");
            
            if (0 == errorCodes.size()) {
                pu.setStatusId(2);
                importmanager.updateProgramUpload(pu);
                mav.addObject("savedStatus", "uploaded");

            } else {
            	mav.addObject("savedStatus", "error");
                mav.addObject("errorCodes", errorCodes);
            }

            
            List<User> userList = importmanager.getUsersForProgramUploadTypes(1);
            mav.addObject("users", userList);
            
            return mav;

    }

}
