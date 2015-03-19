/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.service.impl;

import com.bowlink.rr.dao.importDAO;
import com.bowlink.rr.model.fileTypes;
import com.bowlink.rr.model.mailMessage;
import com.bowlink.rr.model.programUploadTypes;
import com.bowlink.rr.model.programUploadTypesFormFields;
import com.bowlink.rr.model.programUploads;
import com.bowlink.rr.reference.fileSystem;
import com.bowlink.rr.service.emailMessageManager;
import com.bowlink.rr.service.importManager;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author chadmccue
 */
@Service
public class importManagerImpl implements importManager {
    
    @Autowired
    importDAO importDAO;
    
    @Autowired
    private emailMessageManager emailMessageManager;
    
    private String archivePath = "/rapidRegistry/archivesIn/";
    
    
    @Override
    @Transactional
    public List<programUploadTypes> getUploadTypes(Integer programId) throws Exception {
        return importDAO.getUploadTypes(programId);
    }
    
    @Override
    @Transactional
    public programUploadTypes getUploadTypeById(Integer importTypeId) throws Exception {
        return importDAO.getUploadTypeById(importTypeId);
    }
    
    @Override
    @Transactional
    public void saveUploadType(programUploadTypes importTypeDetails) throws Exception {
        importDAO.saveUploadType(importTypeDetails);
    }
    
    @Override
    @Transactional
    public List<programUploadTypesFormFields> getImportTypeFields(Integer importTypeId) throws Exception {
        return importDAO.getImportTypeFields(importTypeId);
    }
    
    @Override
    @Transactional
    public void deleteUploadTypeFields(Integer importTypeId) throws Exception {
        importDAO.deleteUploadTypeFields(importTypeId);
    }
    
    @Override
    @Transactional
    public Integer saveUploadTypeField(programUploadTypesFormFields field) throws Exception {
        return importDAO.saveUploadTypeField(field);
    }
    
    @Override
    @Transactional
    public programUploadTypesFormFields getUploadTypeFieldById(Integer fieldId) throws Exception {
        return importDAO.getUploadTypeFieldById(fieldId);
    }
    
    @Override
    @Transactional
    public void saveImportField(programUploadTypesFormFields fieldDetails) throws Exception {
        importDAO.saveImportField(fieldDetails);
    }
    
    @Override
    @Transactional
    public void removeImportType(Integer importTypeId) throws Exception {
        importDAO.removeImportType(importTypeId);
    }

	@Override
	@Transactional
	public List <fileTypes> getFileTypes(Integer fileTypeId) throws Exception {
		return importDAO.getFileTypes(fileTypeId);
	}

	/**
	 * this job checks the program uploads table 
	 * and drop the files that need to be process via HEL into the proper folder so it will
	 * be picked up and process by HEL
	 **/
	@Override
	public void processUploadedFiles() {
		
		/**we get all the files that are not process, we loop them**/
		try {
			List<programUploads> puList = getProgramUploads (2); //ssa
		
		for(programUploads pu : puList) {
			processUploadedFile (pu);
		} 
		
		}catch (Exception ex) {
			ex.printStackTrace();
		}	
		
		
		/** we change status so that it will know not to move it again **/
		
		
		
	}

	
	@Override
	public void processUploadedFile(programUploads pu) throws Exception {
				
				pu = getProgramUpload(pu.getId());
				if (pu.getStatusId() == 2) {
					// set the time and status
					pu.setStatusId(4);
					pu.setStatusDateTime(new Date());
					updateProgramUplaod(pu);
					//we see if it uses HEL
					if (pu.getProgUploadType().isUseHEL()) {
						//we move it to HEL
						if (moveFileToHEL(pu) == 0){
							pu.setStatusId(41);		
						} else {
							pu.setStatusId(42);
						}
				} else {
						//we set its status to be ready for RR processing
						pu.setStatusId(40);
				}
					updateProgramUplaod(pu);
				} 
	}
	
	/** **/
	@Override
	public Integer moveFileToHEL(programUploads pu) {
		Integer sysError = 1;
		try {
			// all files get uploaded to archivesIn, we move it to UT folder
			//we encoded user's file if it is not
            File newFile = new File(pu.getProgUploadType().getHelDropPath() + pu.getAssignedFileName());
            //files are in archiveIn with assignedFileName  
            fileSystem programdir = new fileSystem();
            File archiveFile = new File(programdir.setPath(archivePath) + pu.getAssignedFileName());
            // now we move file
            Path source = archiveFile.toPath();
            Path target = newFile.toPath();
            Files.copy(source, target);
			
		} catch (Exception ex) {
				ex.printStackTrace();
				// send email with error
			 	mailMessage messageDetails = new mailMessage();
			 	messageDetails.settoEmailAddress("gchan123@Yahoo.com");
		        String subject = "Error moving RR file to HEL ";
		        try {
		        	messageDetails.setmessageSubject(subject  + InetAddress.getLocalHost().getHostAddress());
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
		        StringBuilder sb = new StringBuilder();
		        sb.append(new Date().toString());
		        sb.append("ProgramUploadId - " + pu.getId());
		        sb.append("Stack Trace: " + Arrays.toString(ex.getStackTrace()));
		        messageDetails.setmessageBody(sb.toString());
		        messageDetails.setfromEmailAddress("information@health-e-link.com");
		        try {
						emailMessageManager.sendEmail(messageDetails);
					} catch (Exception e) {
						System.err.println("error sending alert regarding batch " + pu.getId() + " error");
						e.printStackTrace();
					}
		        return 1;
		   } 
		return sysError;
		}


	@Override
	@Transactional
	public List<programUploads> getProgramUploads(Integer statusId) throws Exception {
		return importDAO.getProgramUploads(statusId);
	}

	@Override
	@Transactional
	public void updateProgramUplaod(programUploads programUpload)  throws Exception{
		importDAO.updateProgramUplaod(programUpload);	
		
	}

	@Override
	@Transactional
	public Integer saveProgramUplaod(programUploads programUpload)  throws Exception{
		return importDAO.saveProgramUplaod(programUpload);		
	}

	@Override
	@Transactional
	public programUploads getProgramUpload(Integer programUploadId)
			throws Exception {
		programUploads pu =  importDAO.getProgramUpload(programUploadId);
		if (pu != null) {
			//we get the details for the programUploadType
			pu.setProgUploadType(getProgramUploadType(pu.getProgramId()));
		}
		return pu;
	}

	@Override
	@Transactional
	public programUploadTypes getProgramUploadType(Integer programUploadTypeId)
			throws Exception {
		return importDAO.getProgramUploadType(programUploadTypeId);
	}

	/** this job imports the RR file
	 * It checks the patient logic
	 * the visit logic 
	 * sends the engagement records to proper location
	 */
	@Override
	public void processRRFiles() {
		// TODO Auto-generated method stub
		
	}

	/** 
	 * this job takes the output file and get it ready for RR processing
	 */
	@Override
	public void moveHELtoRR() {
		// TODO Auto-generated method stub
		
	}


	
	
}
