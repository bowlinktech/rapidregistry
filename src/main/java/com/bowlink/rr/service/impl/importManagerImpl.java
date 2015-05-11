/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.service.impl;

import com.bowlink.rr.dao.importDAO;
import com.bowlink.rr.model.User;
import com.bowlink.rr.model.MoveFilesLog;
import com.bowlink.rr.model.delimiters;
import com.bowlink.rr.model.errorCodes;
import com.bowlink.rr.model.fileTypes;
import com.bowlink.rr.model.mailMessage;
import com.bowlink.rr.model.program;
import com.bowlink.rr.model.programUploadRecordValues;
import com.bowlink.rr.model.programUploadTypeAlgorithm;
import com.bowlink.rr.model.programUploadTypes;
import com.bowlink.rr.model.programUploadTypesFormFields;
import com.bowlink.rr.model.programUpload_Errors;
import com.bowlink.rr.model.programUploads;
import com.bowlink.rr.reference.fileSystem;
import com.bowlink.rr.service.emailMessageManager;
import com.bowlink.rr.service.fileManager;
import com.bowlink.rr.service.importManager;
import com.bowlink.rr.service.masterClientIndexManager;
import com.bowlink.rr.service.programManager;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    
    @Autowired
    private fileManager filemanager;
    
    @Autowired
    private masterClientIndexManager mcimanager;
    
    @Autowired
    private programManager programmanager;
    
    
    private String rootPath = "/rapidRegistry/";
    
    private String archivePath = rootPath + "archivesIn/";
    
    //files that are ready to be loaded in RR are kept here
    private String processPath = rootPath + "processFiles/";
    
  //files that are ready to be loaded in RR are kept here
    private String loadPath = rootPath + "loadFiles/";
    
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
    public String removeImportType(Integer importTypeId) throws Exception {
    	//if import type is in programUploads table, we do not let them delete.
    	//we set status to inactive
    	//we need to remove import type and all rules associated
        boolean deleted = false;
        List<programUploads> puList = getProgramUploadsByImportType (importTypeId);
        if (puList.size() == 0) {
        	//we remove algorithms fields
        	List<programUploadTypeAlgorithm> putAlgorithms = mcimanager.getProgramUploadTypeAlgorithm(importTypeId);

        	for (programUploadTypeAlgorithm algorithm : putAlgorithms) {
        		mcimanager.removeAlgorithm(algorithm.getId());
        	}
        	//we remove importType
        	importDAO.removeImportType(importTypeId);
        	deleted = true;
        	
        }  else {
        	//we change the status
        	programUploadTypes importType = getProgramUploadType(importTypeId);
        	importType.setStatus(false);
        	saveUploadType(importType);
        }
    	if (deleted) {
    		return "success";
    	} else {
    		return "failed";
    	}

    	
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
					updateProgramUpload(pu);
					//we see if it uses HEL
					if (pu.getProgramUploadType().isUseHEL()) {
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
					updateProgramUpload(pu);
				} 
	}
	
	/** **/
	@Override
	public Integer moveFileToHEL(programUploads pu) {
		try {
			// all files get uploaded to archivesIn, we move it to HEL folder
			//HEL path should be from root
			//we encoded user's file if it is not
            File newFile = new File(pu.getProgramUploadType().getHelDropPath() + pu.getAssignedFileName());
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
	public void updateProgramUpload(programUploads programUpload)  throws Exception{
		importDAO.updateProgramUpload(programUpload);	
		
	}

	@Override
	public Integer saveProgramUpload(programUploads programUpload)  throws Exception{
		return importDAO.saveProgramUpload(programUpload);		
	}

	@Override
	public programUploads getProgramUpload(Integer programUploadId)
			throws Exception {
		programUploads pu =  importDAO.getProgramUpload(programUploadId);
		if (pu != null) {
			//we get the details for the programUploadType
			pu.setProgramUploadType(getProgramUploadType(pu.getProgramUploadTypeId()));
			//we get errors
			pu.setErrors(getProgramUploadErrorList (programUploadId, "programUploadId"));
			//we set error descriptions
			if (pu.getErrors().size() != 0) {
				List <errorCodes> errorList = getErrorCodes(0);
				Map<Integer, String> errorDisplayMap = new HashMap<Integer, String>();
				Map<Integer, String> errorDescMap = new HashMap<Integer, String>();
                for (errorCodes error : errorList) {
                	errorDisplayMap.put(error.getId(), error.getDisplayText());
                	errorDescMap.put(error.getId(), error.getDescription());                	
                }
                for (programUpload_Errors error : pu.getErrors()) {
                	error.setErrorDisplayText(errorDisplayMap.get(error.getErrorId()));
                	error.setErrorDesc(errorDescMap.get(error.getErrorId()));                  	
                }
			}
		}
		return pu;
	}

	@Override
	public programUploadTypes getProgramUploadType(Integer programUploadTypeId)
			throws Exception {
		programUploadTypes put = importDAO.getProgramUploadType(programUploadTypeId);
		put.setInFileExt(getFileTypes(put.getInFileTypeId()).get(0).getFileType());
		put.setOutFileExt(getFileTypes(put.getOutFileTypeId()).get(0).getFileType());
		put.setDelimChar(getDelimiter(put.getFileDelimId()).getDelimChar());
		return put;
	}

	/** this job imports the RR file
	 * It checks the patient logic
	 * the visit logic 
	 * sends the engagement records to proper location
	 */
	@Override
	public void processRRFiles() throws Exception{
		//1. finds all files that is ready for RR process - 40
		//1.5 we recheck the status
		//2. we update the status
		//3. we processRRFile(programUploads programUpload) and process the file
		
		
		
			//we check the status, we update the status
			List <programUploads> puList = getProgramUploads(40);
		    for (programUploads pu : puList) {
		    	//time could have lapsed, we make sure status is 40
		    	programUploads programUploadToProcess  = getProgramUpload(pu.getId());
				if (programUploadToProcess.getStatusId() == 40) {
					//first set new status
			    	pu.setStatusId(43);
			    	pu.setStatusDateTime(new Date());
			    	updateProgramUpload(pu);
			    	// now we process the file, RR files are ready to load, we still check R/O and validate
			    	processRRFile(pu);
			    	
			    	// if all is well we update status to done
			    	pu.setStatusId(0);
				}
		    	
		    	
		    }		
	}
	
	/** this method takes in a programUpload and process the RR file **/
	@Override
	public void processRRFile(programUploads programUpload)  throws Exception {
		
		//load file
		loadFile(programUpload);

		// check for R/O & validation
		//get field list
		List <programUploadTypesFormFields> putFields = getImportTypeFields(programUpload.getProgramUploadTypeId());
		for (programUploadTypesFormFields putField : putFields) {
			 if (putField.isUseField()) {
			if (putField.isRequiredField()) {
				insertFailedRequiredFields(putField, programUpload.getId(), 0);
			}
			
			//now we check validation
			runValidations(programUpload.getId(), putField, 0);
			
			}
		}
		updateStatusForErrorRecord(programUpload.getId(), 14, 0);
		
		//TODO
		//check for permission
		
		
		//RUN MCI - this will set look for patient id and then visit info according to rule to find match patients
		
		//insert records
		
		
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
				MoveFilesLog moveJob = new MoveFilesLog();
		        moveJob.setStatusId(1);
		        moveJob.setFolderPath(put.getHelPickUpPath());
		        boolean pathInUse = movePathInUse (moveJob);
		        if  (!pathInUse) {
		        	Integer lastId = insertMoveFilesLog(moveJob);
			        moveJob.setId(lastId);
					if (put.getHelPickUpPath() == null) {
						String subject = " Null path detected - moveHELFilestoRR";
						try {
							sendImportErrorEmail (subject, null); 
						} catch (Exception e) {
							e.printStackTrace();
						}
			        } else if (put.getHelPickUpPath().length() == 0) {
			        	String subject = " Empty path detected - moveHELFilestoRR";
						try {
							sendImportErrorEmail (subject, null); 
						} catch (Exception e) {
							e.printStackTrace();
						}
			        } else {
						
						//we move it to process folder, set the status and let it finish processing on RR
						moveHELFiletoRR(put);
						moveJob.setStatusId(2);
						updateMoveFilesLogRun(moveJob);		 
			        }
					
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
	public void moveHELFiletoRR(programUploadTypes programUploadType) throws Exception{
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
        if (ex != null) {
        	sb.append("Stack Trace: " + Arrays.toString(ex.getStackTrace()));
        }
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
	public Integer insertMoveFilesLog(MoveFilesLog moveJob) throws Exception {
		 return importDAO.insertMoveFilesLog(moveJob);
	}

	@Override
	@Transactional
	public void updateMoveFilesLogRun(MoveFilesLog moveJob) throws Exception {
		importDAO.updateMoveFilesLogRun(moveJob);
		
	}

	@Override
	@Transactional
	public boolean movePathInUse(MoveFilesLog moveJob) throws Exception {
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
            	//we remove file extension
            	String assignedId = fileName.substring(0, fileName.lastIndexOf("."));
            	pu.setAssignedId(assignedId);
            	programUploads puNew = getProgramUploadByAssignedId(pu);
            	
            	String fileExt = fileName.substring((fileName.lastIndexOf(".") + 1), fileName.length());
            	if (puNew != null) {
            		//we check output file's expected extension
            		puNew.setProgramUploadType(getProgramUploadType(puNew.getProgramUploadTypeId()));
            		
            		if (!fileExt.equalsIgnoreCase(puNew.getProgramUploadType().getOutFileExt())) {
            			puNew.setStatusId(44);   
            		} else {
            			puNew.setStatusId(40);
            		}
            		//we move the file and update the status
            		//we encoded user's file if it is not
            		File newFile = new File(outPath + fileName);
                    // now we move file
                    Files.move(file.toPath(), newFile.toPath(),StandardCopyOption.REPLACE_EXISTING);
                    puNew.setStatusDateTime(date);
                    updateProgramUpload(puNew);
            	} else {
            		//no match, we rename the file and notify admin
            		String subject = "Cannot find programUpload with fileName " + fileName;
        			try {
        				sendImportErrorEmail (subject, null); 
        			} catch (Exception e) {
        				e.printStackTrace();
        			}
        			File newFile = new File(outPath + fileName + "_error");
                    // now we move file
                    Files.move(file.toPath(), newFile.toPath(),StandardCopyOption.REPLACE_EXISTING);
            	}
            }
       }
		
	}

	@Override
	@Transactional
	public programUploads getProgramUploadByAssignedId(programUploads pu) {
		return importDAO.getProgramUploadByAssignedId(pu);
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

	@Override
	public delimiters getDelimiter(Integer delimId) throws Exception {
		return importDAO.getDelimiter(delimId);
	}

	@Override
	public String saveUploadedFile(programUploads pu, MultipartFile fileUpload) throws Exception {
		
	        //save the file to archive folder
	        MultipartFile file = fileUpload;
	        String fileName = pu.getAssignedFileName();

	        InputStream inputStream;
	        OutputStream outputStream;
	        
	        String fileExt = fileName.substring(fileName.lastIndexOf("."), (fileName.length()));

	        try {
	            inputStream = file.getInputStream();
	            File newFile = null;

	           
	            fileSystem dir = new fileSystem();
	            String archivePathHere = archivePath.replace("/rapidRegistry/", "");
	            dir.setDirByName(archivePathHere);

	            newFile = new File(dir.getDir() + fileName);

	            if (newFile.exists()) {
	                int i = 1;
	                while (newFile.exists()) {
	                    int iDot = fileName.lastIndexOf(".");
	                    newFile = new File(dir.getDir() + fileName.substring(0, iDot) + "_(" + ++i + ")" + fileName.substring(iDot));
	                }
	                fileName = newFile.getName();
	                newFile.createNewFile();
	            } else {
	                newFile.createNewFile();
	            }

	            //Save the attachment
	            outputStream = new FileOutputStream(newFile);
	            int read = 0;
	            byte[] bytes = new byte[1024];

	            while ((read = inputStream.read(bytes)) != -1) {
	                outputStream.write(bytes, 0, read);
	            }
	            outputStream.close();
	            if (pu.getProgramUploadType().getEncodingId() != 2) {
	            	//we can't re-decode excel files 
	            	//TODO need to figure out how to encode non-text files
	            	if (!fileExt.equalsIgnoreCase(".xlsx") && !fileExt.equalsIgnoreCase(".mdb")) {
		            	//we encode file if it is not base64
		            	String encoded = filemanager.encodeFileToBase64Binary(newFile);
		            	//delete existing un-encrypted file
		            	Files.delete(newFile.toPath());
		            	filemanager.writeFile(newFile.getAbsolutePath(), encoded);	            	
	            	}
	            }
	            return fileName;
	        } catch (IOException e) {
	            System.err.println("saveUploadedFile " + e.getCause());
	            e.printStackTrace();
	            return null;
	        }
	}
	
	@Override
    public Integer chkUploadedFile(programUploads pu, File loadFile) throws Exception {
		Integer errors = 0;
        try {
            long fileSize = loadFile.length();
            long fileSizeMB = (fileSize / (1024L * 1024L));
            
            programUpload_Errors error = new programUpload_Errors();
            error.setProgramUploadId(pu.getId());
            /* 
             26 = File is empty
             12 = Too large
             13 = Wrong file type
             15 = Wrong delimiter
             */
            /* Make sure the file is not empty : ERROR CODE 26 */
            if (fileSize == 0) {
                error.setErrorId(27);
                insertError(error); 
                errors++;
            }

            /* Make sure file is the correct size : ERROR CODE 12 */
            double maxFileSize = (double) pu.getProgramUploadType().getMaxFileSize();

            if (fileSizeMB > maxFileSize) {
            	error.setErrorId(12);
                insertError(error); 
                errors++;
            }

            /* Make sure file is the correct file type : ERROR CODE 3 */
            String ext = FilenameUtils.getExtension(loadFile.getAbsolutePath());

            String fileType = pu.getProgramUploadType().getInFileExt();
            
            if (ext == null ? fileType != null : !ext.equals(fileType)) {
            	error.setErrorId(13);
                insertError(error); 
                errors++;
            }

            fileSystem dir = new fileSystem();

            /* Make sure the file has the correct delimiter : ERROR CODE 15 */
            /**Check to make sure the file contains the selected delimiter, this should only be for 
            txt or csv only
            **/
            int delimCount = 3;
            if (fileType.equalsIgnoreCase("txt") || fileType.equalsIgnoreCase("csv")) {
            	delimCount = (Integer) dir.checkFileDelimiter(loadFile, pu.getProgramUploadType().getDelimChar());
            }
            if (delimCount < 3 && !"xml".equals(pu.getProgramUploadType().getInFileExt())) {
            	error.setErrorId(15);
                insertError(error); 
                errors++;
            }
            return errors;
            //Save the attachment
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }

	@Override
	public void insertError(programUpload_Errors uploadError) throws Exception {
		importDAO.insertError(uploadError);	
	}

	@Override
	public List<programUpload_Errors> getProgramUploadErrorList(Integer id, String type)
			throws Exception {
		return importDAO.getProgramUploadErrorList(id, type);	
	}

	@Override
	public List<errorCodes> getErrorCodes(Integer status) throws Exception {
		return importDAO.getErrorCodes(status);
	}

	@Override
	public Integer submitUploadFile(Integer userId,
			Integer programUploadTypeId, MultipartFile uploadedFile)
			throws Exception {
		//we get our programUploadType
    	programUploadTypes put = getProgramUploadType(programUploadTypeId);
    	put.setDelimChar(getDelimiter(put.getFileDelimId()).getDelimChar());
    	put.setInFileExt(getFileTypes(put.getInFileTypeId()).get(0).getFileType());
    	
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
        pu.setProgramUploadType(put);
        Integer programUploadId = saveProgramUpload(pu);
        
        // we save the file to archivesIn
        fileName = saveUploadedFile(pu, uploadedFile);
        
        fileSystem dir = new fileSystem();
        File archiveFile = new File(dir.setPath(archivePath) + pu.getAssignedFileName());
        File loadFile = new File(dir.setPath(loadPath) + pu.getAssignedFileName());
        
        //now we start our checks
        String decodedString = "";
        decodedString = filemanager.decodeFileToBase64Binary(archiveFile);
    		if (decodedString == null) {
    			programUpload_Errors error = new programUpload_Errors();
                error.setProgramUploadId(pu.getId());
                error.setErrorId(17);                
    		} else  {
	    		//write it to load folder
	    		filemanager.writeFile(loadFile.getAbsolutePath(), decodedString);
    		}
        
        //need to encode all files sitting idle in system
        //save to input type file
        program programInfo = programmanager.getProgramById(pu.getProgramId()); 
        File programFolder = new File(dir.setPath(rootPath) + programInfo.getProgramName().replace(" ", "-").toLowerCase() + "/importFiles/" + pu.getAssignedFileName());
        Files.copy(archiveFile.toPath(), programFolder.toPath(), StandardCopyOption.REPLACE_EXISTING);
        
    	
    	//we check encoding, delmiter, file size etc
    	if (decodedString != null) {
    		chkUploadedFile(pu, loadFile);
    	}
    	
    	//remove load file
    	Files.delete(loadFile.toPath());
    	return programUploadId;
		
	}

	@Override
	public List<programUploads> getProgramUploadsByImportType(
			Integer importTypeId) throws Exception {
		return importDAO.getProgramUploadsByImportType(importTypeId);
	}

	/**
	 * We return void because admin will get email with system error so we know where it went wrong
	 * we don't need to track each step of the way, it should just stop and move onto the next file
	 */
	@Override
	public void loadFile(programUploads pu) throws Exception {
		
		
			// let's clear all tables first as we are starting over
			clearUpload(pu.getId());
            
			String loadTableName = "uploadTable_" + pu.getId();
            //make sure old table is dropped if exists
            dropLoadTable(loadTableName);
            
            createLoadTable(loadTableName);

             //we need to index loadTable
            indexLoadTable(loadTableName);

            fileSystem dir = new fileSystem();
            dir.setDirByName("/");

            //2. we load data with my sql to loadTable
            /**
             * RR file is in processFolder, file if in text format should be encrypted
             * if programUpload uses HEL, we want to look for a text file, 
             * if not, we process file with its own extension
             */
            pu.setProgramUploadType(getProgramUploadType(pu.getProgramUploadTypeId()));
            String actualFileName = null;
            
            String encodedFilePath = dir.setPath(processPath);
            String encodedFileName = pu.getAssignedId() + "." + pu.getProgramUploadType().getOutFileExt();
            File encodedFile = new File(encodedFilePath + encodedFileName);
            
            String decodedFilePath = dir.setPath(loadPath);
            String decodedFileName = encodedFileName;
            String strDecode = filemanager.decodeFileToBase64Binary(encodedFile);
            
            filemanager.writeFile((decodedFilePath + decodedFileName), strDecode);
            actualFileName = (decodedFilePath + decodedFileName);

            /**
             * we load actualFileName into mysql, we can only load csv and txt, all other types of file must
             * be loaded through HEL to change into text file
            **/
            insertLoadData(pu.getProgramUploadType(), loadTableName,actualFileName);
            
            //we update puId, loadRecordId
           updateLoadTable(loadTableName, pu.getId());

           /**
            * insert into putRecords with status of loaded
            */
           insertUploadRecords (loadTableName, pu.getId());
           /**
            * insert into programUploadRecords
            * **/
           insertUploadRecordDetails(pu.getId());
           
           /** sync data **/
           insertUploadRecordDetailsData(loadTableName);
           //clean up
           dropLoadTable(loadTableName);          
	}

	@Override
	public programUploads getProgramUploadOnly(Integer programUploadId) throws Exception {
		return importDAO.getProgramUpload(programUploadId);
	}

	@Override
	public void clearUpload(Integer programUploadId) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dropLoadTable(String loadTableName) throws Exception {
		importDAO.dropLoadTable(loadTableName);
	}

	@Override
	public void createLoadTable(String loadTableName) throws Exception {
		importDAO.createLoadTable(loadTableName);
		
	}

	@Override
	public void indexLoadTable(String loadTableName) throws Exception {
		importDAO.indexLoadTable(loadTableName);
		
	}

	@Override
	public void insertLoadData(programUploadTypes put, String loadTableName,
			String fileWithPath) throws Exception {
		importDAO.insertLoadData(put, loadTableName, fileWithPath);
		
	}

	@Override
	public void updateLoadTable(String loadTableName, Integer programUploadId)
			throws Exception {
		importDAO.updateLoadTable(loadTableName, programUploadId);	
	}

	@Override
	public void insertUploadRecords(String loadTableName,
			Integer programUploadId) throws Exception {
		importDAO.insertUploadRecords(loadTableName, programUploadId);
	}

	@Override
	public void insertUploadRecordDetails(Integer programUploadId)
			throws Exception {
		importDAO.insertUploadRecordDetails(programUploadId);	
	}

	@Override
	public void insertUploadRecordDetailsData(String loadTableName) {
		importDAO.insertUploadRecordDetailsData(loadTableName);			
	}

	@Override
	public void insertFailedRequiredFields(
			programUploadTypesFormFields putField, Integer programUploadId,
			Integer programUploadRecordId)  throws Exception{
		importDAO.insertFailedRequiredFields(putField, programUploadId, programUploadRecordId);		
	}

	@Override
	public void updateStatusForErrorRecord(Integer programUploadId,
			Integer statusId, Integer programUploadRecordId) throws Exception{
		importDAO.updateStatusForErrorRecord(programUploadId,statusId, programUploadRecordId);	
	}

	@Override
    public void runValidations(Integer programUploadId, programUploadTypesFormFields putField, Integer programUploadRecordId) throws Exception {
         /**
         * MySql RegEXP validate numeric - ^-?[0-9]+[.]?[0-9]*$|^-?[.][0-9]+$ validate email - ^[a-z0-9\._%+!$&*=^|~#%\'`?{}/\-]+@[a-z0-9\.-]+\.[a-z]{2,6}$ or ^[A-Z0-9._%-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$ validate url - ^(https?://)?([\da-z.-]+).([a-z0-9])([0-9a-z]*)*[/]?$ - need to fix not correct - might have to run in java as mysql is not catching all. validate phone - should be no longer than 11 digits ^[0-9]{7,11}$ validate date - doing this in java
         *
         */
          switch (putField.getValidationId()) {
                case 1:
                    break; // no validation
                //email calling SQL to validation and insert - one statement
                case 2:
                    genericValidation(putField, putField.getValidationId(), programUploadId, programUploadRecordId);
                    break;
                //phone  calling SP to validation and insert - one statement 
                case 3:
                    genericValidation(putField, putField.getValidationId(), programUploadId, programUploadRecordId);
                    break;
                // need to loop through each record / each field
                case 4:
                    dateValidation(putField, programUploadId, programUploadRecordId);
                    break;
                //numeric   calling SQL to validation and insert - one statement      
                case 5:
                    genericValidation(putField, putField.getValidationId(), programUploadId, programUploadRecordId);
                    break;
                //url - need to rethink as regExp is not validating correctly
                case 6:
                    urlValidation(putField, programUploadId, programUploadRecordId);
                    break;
                //anything new we hope to only have to modify sp
                default:
                    genericValidation(putField, putField.getValidationId(), programUploadId, programUploadRecordId);
                    break;
            }

        }

	@Override
	public void genericValidation(programUploadTypesFormFields putField,
			Integer validationTypeId, Integer programUploadId, Integer programUploadRecordId) throws Exception {
		importDAO.genericValidation(putField, validationTypeId, programUploadId, programUploadRecordId);	
	}
	
	@Override
    public void urlValidation(programUploadTypesFormFields putField,
           Integer programUploadId, Integer programUploadRecordId) throws Exception{
        
		//1. we grab all recordIds for programUploadRecord that are not length of 0 and not null 
            List<programUploadRecordValues> prv = null;
            if (programUploadRecordId == 0) {
                prv = getFieldColAndValues(programUploadId, putField);
            } else {
            	prv = getFieldColAndValueByProgramUploadRecordId(putField, programUploadRecordId);
            }
            //2. we look at each column and check each value to make sure it is a valid url
            for (programUploadRecordValues pur : prv) {
                //System.out.println(tr.getfieldValue());
                if (pur.getFieldValue() != null) {
                	 if (pur.getFieldValue().length() != 0) {
                    //we append http:// if url doesn't start with it
                    String urlToValidate = pur.getFieldValue();
                    if (!urlToValidate.startsWith("http")) {
                        urlToValidate = "http://" + urlToValidate;
                    }
                    if (!isValidURL(urlToValidate)) {
                    	programUpload_Errors uploadError = new programUpload_Errors();
                    	uploadError.setErrorData(pur.getFieldValue());
                    	uploadError.setProgramUploadId(programUploadId);
                    	uploadError.setProgramUploadRecordId(programUploadRecordId);
                    	uploadError.setErrorId(31);
                    	uploadError.setFieldId(putField.getFieldId());
                    	uploadError.setDspPos(putField.getDspPos());
                    	insertError(uploadError);                        
                    }
                }
                }
            }

    }

	@Override
	public void dateValidation(programUploadTypesFormFields putField,
			Integer programUploadId, Integer programUploadRecordId) throws Exception {
		List<programUploadRecordValues> prv = null;
        //1. we grab all transactionInIds for messages that are not length of 0 and not null 
        if (programUploadRecordId == 0) {
        	prv = getFieldColAndValues(programUploadId, putField);
        } else {
        	prv = getFieldColAndValueByProgramUploadRecordId(putField, programUploadRecordId);
        }
        //2. we look at each column and check each value by trying to convert it to a date
        for (programUploadRecordValues pur : prv) {
            if (pur.getFieldValue() != null) {
            	 if (pur.getFieldValue().length() != 0) {
                //sql is picking up dates in mysql format and it is not massive inserting, running this check to avoid unnecessary sql call
                //System.out.println(tr.getFieldValue());
                //we check long dates
                Date dateValue = null;
                String mySQLDate = chkMySQLDate(pur.getFieldValue());

                if (dateValue == null && mySQLDate.equalsIgnoreCase("")) {
                    dateValue = convertLongDate(pur.getFieldValue());
                }
                if (dateValue == null && mySQLDate.equalsIgnoreCase("")) {
                    dateValue = convertDate(pur.getFieldValue());
                }

                String formattedDate = null;
                if (dateValue != null && mySQLDate.equalsIgnoreCase("")) {
                    formattedDate = formatDateForDB(dateValue);
                    //3. if it converts, we update the column value
                    updateFieldValue(pur, formattedDate);
                }

                if (formattedDate == null && (mySQLDate.equalsIgnoreCase("") || mySQLDate.equalsIgnoreCase("ERROR"))) {
                	programUpload_Errors uploadError = new programUpload_Errors();
                	uploadError.setErrorData(pur.getFieldValue());
                	uploadError.setProgramUploadId(programUploadId);
                	uploadError.setProgramUploadRecordId(pur.getProgramUploadRecordId());
                	uploadError.setErrorId(29);
                	uploadError.setFieldId(putField.getFieldId());
                	uploadError.setDspPos(putField.getDspPos());
                	insertError(uploadError);
                   
                }
             }
            }
        }
		
	}

	@Override
	public List<programUploadRecordValues> getFieldColAndValues(
			Integer programUploadId, programUploadTypesFormFields putField)
			throws Exception {
		return importDAO.getFieldColAndValues(programUploadId, putField);
	}

	@Override
	public List<programUploadRecordValues> getFieldColAndValueByProgramUploadRecordId(
			programUploadTypesFormFields putField, Integer programUploadRecordId)
			throws Exception {
		return importDAO.getFieldColAndValueByProgramUploadRecordId(putField, programUploadRecordId);
	}
	
	@Override
    public boolean isValidURL(String url) {
        UrlValidator urlValidator = new UrlValidator();
        if (urlValidator.isValid(url)) {
            return true;
        } else {
            return false;
        }
    }
	
	@Override
    public String formatDateForDB(Date date) {
        try {
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
            return dateformat.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Date convertLongDate(String dateValue) {

        Date date = null;
        //this checks convert long date such February 2, 2014
        try {
            date = java.text.DateFormat.getDateInstance().parse(dateValue);
            /**
             * this method converts February 29 to March 1, we need to run through check two to make sure it is valid 
             *  *
             */
            if (!recheckLongDate(dateValue, date.toString())) {
                return null;
            }
        } catch (Exception e) {
        }
        return date;
    }

    public String chkMySQLDate(String dateValue) {

        // some regular expression
        String time = "(\\s(([01]?\\d)|(2[0123]))[:](([012345]\\d)|(60))"
                + "[:](([012345]\\d)|(60)))?"; // with a space before, zero or one time

        // no check for leap years (Schaltjahr)
        // and 31.02.2006 will also be correct
        String day = "(([12]\\d)|(3[01])|(0?[1-9]))"; // 01 up to 31
        String month = "((1[012])|(0\\d))"; // 01 up to 12
        String year = "\\d{4}";

        // define here all date format
        String date = dateValue.replaceAll("/", "-");
        date = date.replaceAll("\\.", "-");
        Pattern pattern = Pattern.compile(year + "-" + month + "-" + day + time);

        // check dates
        if (pattern.matcher(date).matches()) {
            try {
                SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
                dateformat.setLenient(false);
                dateformat.parse(date);
                return "Valid";
            } catch (Exception e) {
                e.printStackTrace();
                return "Error";
            }
        } else {
            return "";
        }
    }

    public Date convertDate(String input) {

        // some regular expression
        String time = "(\\s(([01]?\\d)|(2[0123]))[:](([012345]\\d)|(60))"
                + "[:](([012345]\\d)|(60)))?"; // with a space before, zero or one time

        // no check for leap years (Schaltjahr)
        // and 31.02.2006 will also be correct
        String day = "(([12]\\d)|(3[01])|(0?[1-9]))"; // 01 up to 31
        String month = "((1[012])|(0\\d))"; // 01 up to 12
        String year = "\\d{4}";

        // define here all date format
        String date = input.replaceAll("/", "-");
        date = date.replaceAll("\\.", "-");
        //ArrayList<Pattern> patterns = new ArrayList<Pattern>();
        //Pattern pattern1 = Pattern.compile(day + "-" + month + "-" + year + time); //not matching, doesn't work for 01-02-2014 is it jan or feb, will only accept us dates
        Pattern pattern2 = Pattern.compile(year + "-" + month + "-" + day + time);
        Pattern pattern3 = Pattern.compile(month + "-" + day + "-" + year + time);
        // check dates
        //month needs to have leading 0
        if (date.indexOf("-") == 1) {
            date = "0" + date;
        }

        if (pattern2.matcher(date).matches()) {
            //we have y-m-d format, we transform and return date
            try {
                SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-mm-dd");
                dateformat.setLenient(false);
                Date dateValue = dateformat.parse(date);
                return dateValue;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        } else if (pattern3.matcher(date).matches()) {
            //we have m-d-y format, we transform and return date
            try {
                SimpleDateFormat dateformat = new SimpleDateFormat("MM-dd-yyyy");
                dateformat.setLenient(false);
                Date dateValue = dateformat.parse(date);
                return dateValue;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        } else {
            return null;
        }

    }

	@Override
	public void updateFieldValue(programUploadRecordValues prv, String newValue) throws Exception {
		importDAO.updateFieldValue(prv, newValue);
	}

	@Override
	public boolean recheckLongDate(String longDateVal, String convertedDate) {
		try {
            longDateVal = longDateVal.toLowerCase();
            convertedDate = convertedDate.toLowerCase();
            if (longDateVal.contains("jan") && convertedDate.contains("jan")) {
                return true;
            } else if (longDateVal.contains("feb") && convertedDate.contains("feb")) {
                return true;
            } else if (longDateVal.contains("mar") && convertedDate.contains("mar")) {
                return true;
            } else if (longDateVal.contains("apr") && convertedDate.contains("apr")) {
                return true;
            } else if (longDateVal.contains("may") && convertedDate.contains("may")) {
                return true;
            } else if (longDateVal.contains("jun") && convertedDate.contains("jun")) {
                return true;
            } else if (longDateVal.contains("jul") && convertedDate.contains("jul")) {
                return true;
            } else if (longDateVal.contains("aug") && convertedDate.contains("aug")) {
                return true;
            } else if (longDateVal.contains("sep") && convertedDate.contains("sep")) {
                return true;
            } else if (longDateVal.contains("oct") && convertedDate.contains("oct")) {
                return true;
            } else if (longDateVal.contains("nov") && convertedDate.contains("nov")) {
                return true;
            } else if (longDateVal.contains("dec") && convertedDate.contains("dec")) {
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return false;
	}
}

