/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.service.impl;

import com.bowlink.rr.dao.dataElementDAO;
import com.bowlink.rr.model.crosswalkData;
import com.bowlink.rr.model.crosswalks;
import com.bowlink.rr.model.customProgramFields;
import com.bowlink.rr.model.dataElements;
import com.bowlink.rr.model.environmentalstrategyquestions;
import com.bowlink.rr.reference.fileSystem;
import com.bowlink.rr.service.dataElementManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author chadmccue
 */
@Service
public class dataElementManagerImpl implements dataElementManager {

    @Autowired
    dataElementDAO dataElementDAO;

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public List<dataElements> getdataElements() throws Exception {
        return dataElementDAO.getdataElements();
    }

    @Override
    @Transactional
    public List<dataElements> getActiveDataElements() throws Exception {
        return dataElementDAO.getActiveDataElements();
    }

    @Override
    @Transactional
    public List<crosswalks> getCrosswalks(int page, int maxResults, int programId) {
        return dataElementDAO.getCrosswalks(page, maxResults, programId);
    }

    @Override
    @Transactional
    public List getValidationTypes() {
        return dataElementDAO.getValidationTypes();
    }

    @Override
    @Transactional
    public String getfieldName(int fieldId) {
        return dataElementDAO.getfieldName(fieldId);
    }

    @Override
    @Transactional
    public String getCrosswalkName(int cwId) {
        return dataElementDAO.getCrosswalkName(cwId);
    }

    @Override
    @Transactional
    public String getValidationName(int validationId) {
        return dataElementDAO.getValidationName(validationId);
    }

    @Override
    @Transactional
    public double findTotalCrosswalks(int programId) {
        return dataElementDAO.findTotalCrosswalks(programId);
    }

    @Override
    @Transactional
    public Long checkCrosswalkName(String name, int orgId) {
        return dataElementDAO.checkCrosswalkName(name, orgId);
    }

    @SuppressWarnings("rawtypes")
    @Override
    @Transactional
    public List getCrosswalkData(int cwId) {
        return dataElementDAO.getCrosswalkData(cwId);
    }

    @Override
    @Transactional
    public Integer createCrosswalk(crosswalks crosswalkDetails) {
        Integer lastId = null;

        MultipartFile file = crosswalkDetails.getFile();
        String fileName = file.getOriginalFilename();

        InputStream inputStream = null;
        OutputStream outputStream = null;
        fileSystem dir = new fileSystem();

        dir.setDirByName("crosswalks/");

        File newFile = null;
        newFile = new File(dir.getDir() + fileName);

        try {
            inputStream = file.getInputStream();

            if (!newFile.exists()) {
                newFile.createNewFile();
            }
            outputStream = new FileOutputStream(newFile);
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            outputStream.close();

            //Set the filename to the original file name
            crosswalkDetails.setfileName(fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }

        //Need to get the actual delimiter character
        String delimChar = (String) dataElementDAO.getDelimiterChar(crosswalkDetails.getFileDelimiter());

        //Check to make sure the file contains the selected delimiter
        //Set the directory that holds the crosswalk files
        int delimCount = (Integer) dir.checkFileDelimiter(dir, fileName, delimChar);

        if (delimCount > 0) {
            //Submit the new message type to the database
            lastId = (Integer) dataElementDAO.createCrosswalk(crosswalkDetails);

            //Call the function that will load the content of the crosswalk text file
            //into the rel_crosswalkData table
            loadCrosswalkContents(lastId, fileName, delimChar);

            return lastId;
        } else {
            //Need to delete the file
            newFile.delete();

            //Need to return an error
            return 0;
        }
    }

    @Override
    @Transactional
    public crosswalks getCrosswalk(int cwId) {
        return dataElementDAO.getCrosswalk(cwId);
    }

    /**
     * The 'loadCrosswalkContents' will take the contents of the uploaded text template file and populate the rel_crosswalkData table.
     *
     * @param id id: value of the latest added crosswalk
     * @param fileName	fileName: file name of the uploaded text file.
     * @param delim	delim: the delimiter used in the file
     *
     */
    public void loadCrosswalkContents(int id, String fileName, String delim) {

        //Set the directory that holds the crosswalk files
        fileSystem dir = new fileSystem();

        dir.setDirByName("crosswalks/");

        FileInputStream file = null;
        String[] lineValue = null;
        try {
            file = new FileInputStream(new File(dir.getDir() + fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(file));

        try {
            String line = null;
            try {
                line = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Query deleteExistingCrosswalks = sessionFactory.getCurrentSession().createSQLQuery("delete from rel_crosswalkData where crosswalkId = :crosswalkId");
            deleteExistingCrosswalks.setParameter("crosswalkId", id);
            deleteExistingCrosswalks.executeUpdate();

            while (line != null) {

                //Need to parse each line via passed in delimiter
                if (delim == "t") {
                    lineValue = line.split("\t");
                } else {
                    lineValue = line.split("\\" + delim);
                }
                String sourceValue = lineValue[0];
                String targetValue = lineValue[1];
                String descVal = lineValue[2];

                //Need to insert all the fields into the crosswalk data Fields table
                Query query = sessionFactory.getCurrentSession().createSQLQuery("INSERT INTO rel_crosswalkData (crosswalkId, sourceValue, targetValue, descValue)"
                        + "VALUES (:crosswalkid, :sourceValue, :targetValue, :descVal)")
                        .setParameter("crosswalkid", id)
                        .setParameter("sourceValue", sourceValue)
                        .setParameter("targetValue", targetValue)
                        .setParameter("descVal", descVal);

                query.executeUpdate();

                try {
                    line = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("rawtypes")
    @Override
    @Transactional
    public List getDelimiters() {
        return dataElementDAO.getDelimiters();
    }

    @SuppressWarnings("rawtypes")
    @Override
    @Transactional
    public List getInformationTables() {
        return dataElementDAO.getInformationTables();
    }

    @SuppressWarnings("rawtypes")
    @Override
    @Transactional
    public List getAllTables() {
        return dataElementDAO.getAllTables();
    }

    @SuppressWarnings("rawtypes")
    @Override
    @Transactional
    public List getTableColumns(String tableName) {
        return dataElementDAO.getTableColumns(tableName);
    }

    @Override
    @Transactional
    public void saveField(dataElements formField) throws Exception {
        dataElementDAO.saveField(formField);
    }

    @Override
    @Transactional
    public dataElements getFieldDetails(Integer fieldId) throws Exception {
        return dataElementDAO.getFieldDetails(fieldId);
    }

    @SuppressWarnings("rawtypes")
    @Override
    @Transactional
    public List getAnswerTypes() {
        return dataElementDAO.getAnswerTypes();
    }

    @SuppressWarnings("rawtypes")
    @Override
    @Transactional
    public List getLookUpTables() {
        return dataElementDAO.getLookUpTables();
    }

    @SuppressWarnings("rawtypes")
    @Override
    @Transactional
    public List getLookupTableValues(Integer fieldId) throws Exception {
        return dataElementDAO.getLookupTableValues(fieldId);
    }

    @SuppressWarnings("rawtypes")
    @Override
    @Transactional
    public List getLookupTableValues(String tableName) throws Exception {
        return dataElementDAO.getLookupTableValues(tableName);
    }

    @SuppressWarnings("rawtypes")
    @Override
    @Transactional
    public List getLookupTableValues(String tableName, Integer programId) throws Exception {
        return dataElementDAO.getLookupTableValues(tableName, programId);
    }

    @Override
    @Transactional
    public List<customProgramFields> getCustomFields(int page, int maxResults, int programId) throws Exception {
        return dataElementDAO.getCustomFields(page, maxResults, programId);
    }

    @Override
    @Transactional
    public double findTotalCustomFields(int programId) {
        return dataElementDAO.findTotalCustomFields(programId);
    }

    @Override
    @Transactional
    public customProgramFields getCustomField(int fieldId) throws Exception {
        return dataElementDAO.getCustomField(fieldId);
    }

    @Override
    @Transactional
    public Long checkCustomFieldName(String name, int programId, int fieldId) {
        return dataElementDAO.checkCustomFieldName(name, programId, fieldId);
    }

    @Override
    @Transactional
    public void saveCustomField(customProgramFields customField) throws Exception {
        dataElementDAO.saveCustomField(customField);
    }

    @Override
    @Transactional
    public Integer uploadNewFileForCrosswalk(crosswalks crosswalkDetails) {
        MultipartFile file = crosswalkDetails.getFile();
        String fileName = file.getOriginalFilename();

        InputStream inputStream = null;
        OutputStream outputStream = null;
        fileSystem dir = new fileSystem();

        dir.setDirByName("crosswalks/");

        File newFile = null;
        newFile = new File(dir.getDir() + fileName);

        try {
            inputStream = file.getInputStream();

            if (!newFile.exists()) {
                newFile.createNewFile();
            }
            outputStream = new FileOutputStream(newFile);
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            outputStream.close();

            //Set the filename to the original file name
            crosswalkDetails.setfileName(fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }

        //Need to get the actual delimiter character
        String delimChar = (String) dataElementDAO.getDelimiterChar(crosswalkDetails.getFileDelimiter());

        //Check to make sure the file contains the selected delimiter
        //Set the directory that holds the crosswalk files
        int delimCount = (Integer) dir.checkFileDelimiter(dir, fileName, delimChar);

        if (delimCount > 0) {
            //Submit the new message type to the database
            dataElementDAO.updateCrosswalk(crosswalkDetails);

            //Call the function that will load the content of the crosswalk text file
            //into the rel_crosswalkData table
            loadCrosswalkContents(crosswalkDetails.getId(), fileName, delimChar);

            return crosswalkDetails.getId();
        } else {
            //Need to delete the file
            newFile.delete();

            //Need to return an error
            return 0;
        }
    }

    @Override
    @Transactional
    public List<crosswalkData> getCrosswalkDataByCWId(Integer cwId)
            throws Exception {
        return dataElementDAO.getCrosswalkDataByCWId(cwId);
    }
    
    @Override
    @Transactional
    public List<String> getEnvironmentalStrategies(Integer crosswalkId, Integer programId) throws Exception {
         return dataElementDAO.getEnvironmentalStrategies(crosswalkId,programId);
    }
    
    @Override
    @Transactional
    public List<environmentalstrategyquestions> getEnvironmentalStrategyQuestions(String code) throws Exception {
        return dataElementDAO.getEnvironmentalStrategyQuestions(code);
    }
    
    @Override
    @Transactional
    public environmentalstrategyquestions getEnvironmentalStrategyQuestion(Integer qId) throws Exception {
        return dataElementDAO.getEnvironmentalStrategyQuestion(qId);
    }
    
    @Override
    @Transactional
    public Integer saveEnvironmentalStrategyQuestion(environmentalstrategyquestions question) throws Exception {
        return dataElementDAO.saveEnvironmentalStrategyQuestion(question);
    }
    
}
