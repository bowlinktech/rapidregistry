package com.bowlink.rr.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.servlet.view.RedirectView;

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
     * The '/submitFileUpload' POST request will submit the new file and run the file through various validations. If a single validation fails the batch will be put in a error validation status and the file will be removed from the system. The user will receive an error message on the screen letting them know which validations have failed and be asked to upload a new file.
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
    	
    	//we check encoding, delmiter, file size etc
    	if (put.getEncodingId() == 2) {
    		
    	}
    	
    	//we generate a batch id
    	
    	//we save 
    	DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssS");
        Date date = new Date();
        //adding transport method id to UT batch name
        //String batchName = new StringBuilder().append("1").append(userInfo.getOrgId()).append(configDetails.getMessageTypeId()).append(dateFormat.format(date)).toString();

        

        try {
        	
        	/* Need to add the file to the batchUploads table */
            /* Create the batch name (TransportMethodId+OrgId+MessageTypeId+Date/Time/Seconds) */
            
            
            /* Upload the file */
            Map<String, String> batchResults =  null;
            
            fileSystem dir = new fileSystem();
            dir.setDirByName("/");
            
            //need to write the file first, we will write it to our process folder
            /**
            String uploadedFileName = transactionInManager.copyUplaodedPath(transportDetails, uploadedFile);
            String oldFilePath = dir.getDir() + transportDetails.getfileLocation();
            oldFilePath = oldFilePath.replace("bowlink///", "");
            File oldFile = new File(oldFilePath + uploadedFileName);
            Path source = oldFile.toPath();
        	
            String processFilePath = dir.setPath(processPath);
            String strProcessFile =  processFilePath + uploadedFileName;
            File processFile = new File(strProcessFile);
           
            //right now we only support id 2, Base64
            
            if (transportDetails.getEncodingId() == 2) {
            	String strDecode = filemanager.decodeFileToBase64Binary(oldFile);
            	filemanager.writeFile((processFilePath+ uploadedFileName), strDecode);  	
            } else {
            	processFile = oldFile;
            }
            //we decode file and pass it into uploadedFile
            batchResults = transactionInManager.chkUploadBatchFile(transportDetails, processFile); 
            //we delete the temp file here
            if (transportDetails.getEncodingId() == 2) {
            	processFile.delete();
            }
            
            //we set archive path
            File archiveFile = new File(dir.setPath(archivePath) + batchName + batchResults.get("fileName").substring(batchResults.get("fileName").lastIndexOf(".")));
            Path archive = archiveFile.toPath();
            
            if (transportDetails.getEncodingId() == 1) {
            	String strEncodedFile = filemanager.encodeFileToBase64Binary(oldFile);
            	Files.move(source, archive, REPLACE_EXISTING);
            	 //we replace file with encoded
                filemanager.writeFile(oldFile.getAbsolutePath(), strEncodedFile);
            } else { // already encoded
            	Files.copy(source, archive);
            }
             **/
            /* Submit a new batch */
          //we build a programUpload
        	programUploads pu = new programUploads();
        	
        	/**we save it as failed validation (1)  or SSA (2)**/
           
           
            List<Integer> errorCodes = new ArrayList<Integer>();

            Object emptyFileVal = batchResults.get("emptyFile");
            if (emptyFileVal != null) {
                errorCodes.add(1);
            }

            Object wrongSizeVal = batchResults.get("wrongSize");
            if (wrongSizeVal != null) {
                errorCodes.add(2);
            }

            Object wrongFileTypeVal = batchResults.get("wrongFileType");
            if (wrongFileTypeVal != null) {
                errorCodes.add(3);
            }

            Object wrongDelimVal = batchResults.get("wrongDelim");
            if (wrongDelimVal != null) {
                errorCodes.add(4);
            }



            /* If Passed validation update the status to Source Submission Accepted */
            if (0 == errorCodes.size()) {
                /* Get the details of the batch */
               

                /* Redirect to the list of uploaded batches */
                redirectAttr.addFlashAttribute("savedStatus", "uploaded");

            } else {
                redirectAttr.addFlashAttribute("savedStatus", "error");
                redirectAttr.addFlashAttribute("errorCodes", errorCodes);
            }

            /** add logging **/
            
            ModelAndView mav = new ModelAndView(new RedirectView("upload"));
            return mav;

        } catch (Exception e) {
            throw new Exception("Error occurred uploading a new file. programUploadTypeId: " + programUploadTypeId, e);
        }

    }

}
