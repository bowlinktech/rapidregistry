/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.service.impl;

import com.bowlink.rr.dao.importDAO;
import com.bowlink.rr.model.User;
import com.bowlink.rr.model.moveFilesLog;
import com.bowlink.rr.model.fileTypes;
import com.bowlink.rr.model.mailMessage;
import com.bowlink.rr.model.programUploadTypes;
import com.bowlink.rr.model.programUploadTypesFormFields;
import com.bowlink.rr.model.programUploads;
import com.bowlink.rr.reference.fileSystem;
import com.bowlink.rr.service.emailMessageManager;
import com.bowlink.rr.service.importManager;

import java.io.File;
import java.io.FileFilter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.filefilter.HiddenFileFilter;
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
    
    //files that are ready to be loaded in RR are kept here
    private String processPath = "/rapidRegistry/processFiles/";
    
    private String importErrorToEmail ="gchan123@yahoo.com";
    
    private String importErrorFromEmail ="information@health-e-link.com";
    
    
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
			String subject = " Error job -  moving RR file to processed folder ";
			try {
				sendImportErrorEmail (subject, ex);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
	}

	/**
	 * This method allows the system to process one file on demand
	 * It checks the program upload to make sure it is SSA
	 * If file uses HEL, it will move it to HEL for processing
	 * If not, it will move it to RR process folder for job to pick up and import
	 * **/
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
						//we move the file to processFile
						fileSystem programdir = new fileSystem();	
						File newFile = new File(programdir.setPath(processPath) + pu.getAssignedFileName());
			            //files are in archiveIn with assignedFileName  
			            File archiveFile = new File(programdir.setPath(archivePath) + pu.getAssignedFileName());
			            // now we move file
			            Path source = archiveFile.toPath();
			            Path target = newFile.toPath();
			            try {
			            	Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
			            	pu.setStatusId(40);
			            } catch (Exception ex) {
			            	// send email with error
			            	pu.setStatusId(42);
						 	String subject = "ProgramUploadId - " + pu.getId() + " Error moving RR file to processed folder ";
						 	sendImportErrorEmail (subject, ex); 			            	
						 	
			            }
						
				}
					updateProgramUplaod(pu);
				} 
	}
	
	/** **/
	@Override
	public Integer moveFileToHEL(programUploads pu) {
		try {
			// all files get uploaded to archivesIn, we move it to HEL folder
			//HEL path should be from root
			//we encoded user's file if it is not
            File newFile = new File(pu.getProgUploadType().getHelDropPath() + pu.getAssignedFileName());
            //files are in archiveIn with assignedFileName  
            fileSystem programdir = new fileSystem();
            File archiveFile = new File(programdir.setPath(archivePath) + pu.getAssignedFileName());
            // now we move file
            Path source = archiveFile.toPath();
            Path target = newFile.toPath();
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING );
			
		} catch (Exception ex) {
				//catching error so it moves onto next file
				ex.printStackTrace();
				
		        return 1;
		   } 
		return 0;
		}


	@Override
	public List<programUploads> getProgramUploads(Integer statusId) throws Exception {
		return importDAO.getProgramUploads(statusId);
	}

	@Override
	public void updateProgramUplaod(programUploads programUpload)  throws Exception{
		importDAO.updateProgramUplaod(programUpload);	
		
	}

	@Override
	public Integer saveProgramUplaod(programUploads programUpload)  throws Exception{
		return importDAO.saveProgramUplaod(programUpload);		
	}

	@Override
	public programUploads getProgramUpload(Integer programUploadId)
			throws Exception {
		programUploads pu =  importDAO.getProgramUpload(programUploadId);
		if (pu != null) {
			//we get the details for the programUploadType
			pu.setProgUploadType(getProgramUploadType(pu.getProgramUploadTypeId()));
		}
		return pu;
	}

	@Override
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
	 * We pick up the distinct active paths from programUploadTypes and through the folder files 
	 * we match the assigned file name from programUploads table 
	 * update programUpload status so RR job can pick it up for import
	 * @throws Exception 
	 * 
	 */
	@Override
	public void moveHELFilestoRR(){
		try {
		//get HEL paths
		List<programUploadTypes> pickUpPathList = getDistinctHELPaths(1);
			for (programUploadTypes put : pickUpPathList) {
				//check path
				moveFilesLog moveJob = new moveFilesLog();
		        moveJob.setStatusId(1);
		        moveJob.setFolderPath(put.getHelPickUpPath());
				boolean pathInUse = movePathInUse (moveJob);
				if  (!pathInUse) {
					Integer lastId = insertMoveFilesLog(moveJob);
			        moveJob.setId(lastId);
					//we move it to process folder, set the status and let it finish processing on RR
					moveHELFiletoRR(put);
					moveJob.setStatusId(2);
					updateMoveFilesLogRun(moveJob);		 
				}
			}
		
		//we loop through the files in the folder and look up the bath info by Id
		} catch (Exception ex) {
			ex.printStackTrace();
			String subject = " Error job - moveHELFilestoRR";
			try {
				sendImportErrorEmail (subject, ex); 
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/** this is so we can ask the system to move files for a particular programUploadType)
	 * 
	 * **/
	@Override
	public void moveHELFiletoRR(programUploadTypes programUploadType)  throws Exception {
		// check if directory exists, if not create
        fileSystem fileSystem = new fileSystem();
        //paths are from root instead of /home
        String inPath = fileSystem.setPathFromRoot(programUploadType.getHelPickUpPath());
        File f = new File(inPath);
        if (!f.exists()) {
        	String subject = " Error path does not exist - moveHELFiletoRR";
			try {
				sendImportErrorEmail (subject, null); 
			} catch (Exception e) {
				e.printStackTrace();
			}
        } else  {
        	//we move the file and update the programUpload status
        	moveFilesByPath(programUploadType.getHelPickUpPath());
        }

	}

	@Override
	public void processRRFile(programUploads programUpload)  throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<programUploadTypes> getProgramUploadTypes (boolean usesHEL, boolean checkHEL, Integer status) throws Exception {
		return importDAO.getProgramUploadTypes(usesHEL, checkHEL, status);
	}

	@Override
	public void sendImportErrorEmail(String subject, Exception ex) throws Exception{
		// send email with error
	 	mailMessage messageDetails = new mailMessage();
	 	messageDetails.settoEmailAddress(importErrorToEmail);
	 	messageDetails.setfromEmailAddress(importErrorFromEmail);
	    try {
        	messageDetails.setmessageSubject(subject + " " + InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
        StringBuilder sb = new StringBuilder();
        sb.append(new Date().toString() + "<br/>");
        sb.append("Stack Trace: " + Arrays.toString(ex.getStackTrace()));
        messageDetails.setmessageBody(sb.toString());
        try {
				emailMessageManager.sendEmail(messageDetails);
			} catch (Exception e) {
				System.err.println("sendImportErrorEmail Error");
				e.printStackTrace();
			}
		
	}

	@Override
	@Transactional
	public List<programUploadTypes> getDistinctHELPaths(Integer status)
			throws Exception {
		return importDAO.getDistinctHELPaths(status);
	}

	@Override
	@Transactional
	public Integer insertMoveFilesLog(moveFilesLog moveJob) throws Exception {
		 return importDAO.insertMoveFilesLog(moveJob);
	}

	@Override
	@Transactional
	public void updateMoveFilesLogRun(moveFilesLog moveJob) throws Exception {
		importDAO.updateMoveFilesLogRun(moveJob);
		
	}

	@Override
	@Transactional
	public boolean movePathInUse(moveFilesLog moveJob) throws Exception {
		return importDAO.movePathInUse(moveJob);
	}

	/** 
	 * This method will go to the pick up folder and move all the file to RR process folder
	It will take the file name and match it up against the update the programUpload table's assignedFileName's status
	**/
	@Override
	public void moveFilesByPath(String inPath) throws Exception {
		fileSystem fileSystem = new fileSystem();
        String fileInPath = fileSystem.setPathFromRoot(inPath);
        File folder = new File(fileInPath);

        //list files
        //we only list visible files
        File[] listOfFiles = folder.listFiles((FileFilter) HiddenFileFilter.VISIBLE);
        String outPath = fileSystem.setPath(processPath);

        //too many variables that could come into play regarding file types, will check files with one method
        //loop files 
        for (File file : listOfFiles) {
            String fileName = file.getName();
            Date date = new Date();
            
            if (!fileName.endsWith("_error")) {
            	//we look for a match
            	programUploads pu = new programUploads ();
            	pu.setAssignedFileName(fileName);
            	programUploads puNew = getProgramUploadByAssignedFileName(pu);
            	if (puNew != null) {
            		//we move the file and update the status
            		 //we encoded user's file if it is not
                    File newFile = new File(outPath + fileName);
                    // now we move file
                    Path source = file.toPath();
                    Path target = newFile.toPath();
                    Files.move(source, target);
            		pu.setStatusDateTime(date);
            		pu.setStatusId(40);
            		updateProgramUplaod(puNew);
            	} else {
            		//no match, we rename the file and notify admin
            		String subject = "Cannot find programUplaod with fileName " + fileName;
        			try {
        				sendImportErrorEmail (subject, null); 
        			} catch (Exception e) {
        				e.printStackTrace();
        			}
            		file.renameTo((new File(file.getAbsolutePath() + fileName + "_error")));            		
            	}
            }
       }
		
	}

	@Override
	@Transactional
	public programUploads getProgramUploadByAssignedFileName(programUploads pu) {
		return importDAO.getProgramUploadByAssignedFileName(pu);
	}

	@Override
	public List<programUploadTypes> getProgramUploadTypesByUserId(
			Integer systemUserId, Integer statusId) throws Exception {
		return importDAO.getProgramUploadTypesByUserId(systemUserId, statusId);
	}

	@Override
	public List<User> getUsersForProgramUploadTypes(Integer statusId) {
		return importDAO.getUsersForProgramUploadTypes(statusId);
	}
	
}
