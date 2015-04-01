/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.dao.impl;

import com.bowlink.rr.dao.importDAO;
import com.bowlink.rr.model.User;
import com.bowlink.rr.model.MoveFilesLog;
import com.bowlink.rr.model.fileTypes;
import com.bowlink.rr.model.programUploadTypes;
import com.bowlink.rr.model.programUploadTypesFormFields;
import com.bowlink.rr.model.programUploads;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author chadmccue
 */
@Repository
public class importDAOImpl implements importDAO {

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * The 'getUploadTypes' function will return a list of upload types set up for the program.
     *
     * @return The function will return a list of upload types in the system
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<programUploadTypes> getUploadTypes(Integer programId) throws Exception {

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(programUploadTypes.class);
        criteria.add(Restrictions.eq("programId", programId));

        List<programUploadTypes> programUploadTypeList = criteria.list();

        return programUploadTypeList;
    }

    /**
     * The 'getUploadTypeById' function will return the details for the clicked upload type.
     *
     * @param uploadTypeId The id of the selected upload type.
     * @return
     * @throws Exception
     */
    @Override
    public programUploadTypes getUploadTypeById(Integer importTypeId) throws Exception {
        return (programUploadTypes) sessionFactory.getCurrentSession().get(programUploadTypes.class, importTypeId);
    }

    /**
     * The 'saveUploadType' function will save all selected program upload type.
     *
     * @param patientSection The programPatientSections object to save
     * @throws Exception
     */
    @Override
    public void saveUploadType(programUploadTypes importTypeDetails) throws Exception {
        sessionFactory.getCurrentSession().saveOrUpdate(importTypeDetails);
    }

    /**
     * The 'getImportTypeFields' function will return the fields associated the passed in upload type.
     *
     * @param uploadTypeId The id of the clicked upload type
     * @return
     * @throws Exception
     */
    @Override
    public List<programUploadTypesFormFields> getImportTypeFields(Integer importTypeId) throws Exception {
        Query query = sessionFactory.getCurrentSession().createQuery("from programUploadTypesFormFields where programUploadTypeId = :importTypeId order by dspPos asc");
        query.setParameter("importTypeId", importTypeId);

        return query.list();
    }

    /**
     * The 'deleteUploadTypeFields' function will remove all the fields for the passed in upload type
     *
     * @param uploadTypeId The id of the clicked upload type
     * @throws Exception
     */
    @Override
    public void deleteUploadTypeFields(Integer importTypeId) throws Exception {
        Query deleteFields = sessionFactory.getCurrentSession().createQuery("delete from programUploadTypesFormFields where programUploadTypeId = :importTypeId");
        deleteFields.setParameter("importTypeId", importTypeId);
        deleteFields.executeUpdate();
    }

    /**
     * The 'saveUploadTypeField' function will save the new upload type field
     *
     * @param field The object containing the new upload field
     * @return
     * @throws Exception
     */
    @Override
    public Integer saveUploadTypeField(programUploadTypesFormFields field) throws Exception {
        Integer lastId = null;

        lastId = (Integer) sessionFactory.getCurrentSession().save(field);

        return lastId;
    }

    /**
     * The 'getUploadTypeFieldById' function will return the details of the selected upload type field.
     *
     * @param fieldId The id of the clicked field
     * @return
     * @throws Exception
     */
    @Override
    public programUploadTypesFormFields getUploadTypeFieldById(Integer fieldId) throws Exception {
        return (programUploadTypesFormFields) sessionFactory.getCurrentSession().get(programUploadTypesFormFields.class, fieldId);
    }

    /**
     * The 'saveImportField' function will save the changes to the selected import field.
     *
     * @param fieldDetails
     * @throws Exception
     */
    @Override
    public void saveImportField(programUploadTypesFormFields fieldDetails) throws Exception {
        sessionFactory.getCurrentSession().saveOrUpdate(fieldDetails);
    }

    /**
     * The 'removeImportType' function will remove all the fields for the passed in upload type
     *
     * @param importTypeId The id of the clicked upload type
     * @throws Exception
     */
    @Override
    public void removeImportType(Integer importTypeId) throws Exception {

        Query deleteFields = sessionFactory.getCurrentSession().createQuery("delete from programUploadTypesFormFields where programUploadTypeId = :importTypeId");
        deleteFields.setParameter("importTypeId", importTypeId);
        deleteFields.executeUpdate();

        Query deleteImportType = sessionFactory.getCurrentSession().createQuery("delete from programUploadTypes where id = :importTypeId");
        deleteImportType.setParameter("importTypeId", importTypeId);
        deleteImportType.executeUpdate();
    }

    /**
     * The 'getFileTypes' function will return a list of file types and its id
     *
     * @param fileTypeId The id of the clicked file type Use 0 if we would like the entire list
     * @throws Exception
     */
    @Override
    public List<fileTypes> getFileTypes(Integer fileTypeId) throws Exception {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(fileTypes.class);

        if (fileTypeId != 0) {
            criteria.add(Restrictions.eq("id", fileTypeId));
        }
        List<fileTypes> fileTypeList = criteria.list();

        return fileTypeList;
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public List<programUploads> getProgramUploads(Integer statusId) throws Exception {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(programUploads.class);

        if (statusId != 0) {
            criteria.add(Restrictions.eq("statusId", statusId));
        }
        List<programUploads> programUploads = criteria.list();

        return programUploads;

    }

    @Override
    @Transactional
    public void updateProgramUplaod(programUploads programUpload) throws Exception {
        sessionFactory.getCurrentSession().update(programUpload);
    }

    @Override
    @Transactional
    public Integer saveProgramUplaod(programUploads programUpload) throws Exception {
        Integer lastId = null;
        lastId = (Integer) sessionFactory.getCurrentSession().save(programUpload);
        return lastId;

    }

    @Override
    @Transactional
    public programUploads getProgramUpload(Integer programUploadId)
            throws Exception {
        Query query = sessionFactory.getCurrentSession().createQuery(" from programUploads where id = :id");
        query.setParameter("id", programUploadId);
        if (query.list().size() != 0) {
            return (programUploads) query.list().get(0);
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public programUploadTypes getProgramUploadType(Integer programUploadTypeId)
            throws Exception {
        Query query = sessionFactory.getCurrentSession().createQuery(" from programUploadTypes where id = :id");
        query.setParameter("id", programUploadTypeId);
        if (query.list().size() != 0) {
            return (programUploadTypes) query.list().get(0);
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public List<programUploadTypes> getProgramUploadTypes(boolean usesHEL, boolean checkHEL, Integer status)
            throws Exception {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(programUploadTypes.class);
        if (checkHEL) {
            criteria.add(Restrictions.eq("useHEL", usesHEL));
        }
        if (status != 0) {
            criteria.add(Restrictions.eq("status", status));
        }

        List<programUploadTypes> programUploadTypes = criteria.list();

        return programUploadTypes;
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public List<programUploadTypes> getDistinctHELPaths(Integer status)
            throws Exception {

        String sql = ("select distinct helDropPath, helPickUpPath"
                + " from programuploadtypes where useHel = 1 ");
        if (status != 0) {
            sql = sql + " and status = :status";
        }

        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql)
                .setResultTransformer(
                        Transformers.aliasToBean(programUploadTypes.class))
                .setParameter("status", status);

        List<programUploadTypes> putList = query.list();
        return putList;

    }

    @Override
    @Transactional
    public Integer insertMoveFilesLog(MoveFilesLog moveJob) throws Exception {
        Integer lastId = null;
        lastId = (Integer) sessionFactory.getCurrentSession().save(moveJob);
        return lastId;
    }

    @Override
    @Transactional
    public void updateMoveFilesLogRun(MoveFilesLog moveJob) throws Exception {
        sessionFactory.getCurrentSession().update(moveJob);
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public boolean movePathInUse(MoveFilesLog moveJob) throws Exception {

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MoveFilesLog.class);
        criteria.add(Restrictions.eq("statusId", moveJob.getStatusId()));
        criteria.add(Restrictions.eq("folderPath", moveJob.getFolderPath()));

        List<MoveFilesLog> moveLogList = criteria.list();
        if (moveLogList == null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public programUploads getProgramUploadByAssignedFileName(programUploads pu) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(programUploads.class);
        criteria.add(Restrictions.eq("assignedFileName", pu.getAssignedFileName()));

        List<programUploads> programUploads = criteria.list();
        if (programUploads.size() == 1) {
            return programUploads.get(0);
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<programUploadTypes> getProgramUploadTypesByUserId(
            Integer systemUserId, Integer statusId) {

        String sqlQuery = "select * from programuploadTypes where "
                + " programId in (select programId from user_programs "
                + " where systemUserId = :systemUserId)";
        if (statusId != 0) {
            sqlQuery = sqlQuery + " and status = :statusId ";
        }
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery)
                .setResultTransformer(Transformers.aliasToBean(programUploadTypes.class)
                );
        query.setParameter("systemUserId", systemUserId);

        if (statusId != 0) {
            query.setParameter("statusId", statusId);
        }
        return query.list();
    }

    /**
     * we only return users has permission to upload
	 * *
     */
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<User> getUsersForProgramUploadTypes(Integer statusId) {
        String sqlQuery = "select * from users where id in ("
                + " select systemuserid from user_programs where programid in ("
                + " select programid from programuploadtypes";
        if (statusId != 0) {
            sqlQuery = sqlQuery + " where status = :statusId ";
        }
        sqlQuery = sqlQuery + " ))";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery)
                .setResultTransformer(Transformers.aliasToBean(User.class)
                );
        if (statusId != 0) {
            query.setParameter("statusId", statusId);
        }
        return query.list();
    }

}
