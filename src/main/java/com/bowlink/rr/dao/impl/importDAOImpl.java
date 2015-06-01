/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.dao.impl;

import com.bowlink.rr.dao.importDAO;
import com.bowlink.rr.model.User;
import com.bowlink.rr.model.MoveFilesLog;
import com.bowlink.rr.model.delimiters;
import com.bowlink.rr.model.errorCodes;
import com.bowlink.rr.model.fieldsAndCols;
import com.bowlink.rr.model.fileTypes;
import com.bowlink.rr.model.programUploadRecordValues;
import com.bowlink.rr.model.programUploadTypes;
import com.bowlink.rr.model.programUploadTypesFormFields;
import com.bowlink.rr.model.programUpload_Errors;
import com.bowlink.rr.model.programUploads;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
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
    
  //list of final status - these records we skip
    private List<Integer> finalStatuses = Arrays.asList(11, 12, 13, 16);
    
    //list of error status
    private List<Integer> errorStatuses = Arrays.asList(14);
    

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
    @SuppressWarnings("unchecked")
	@Override
	@Transactional
    public List<programUploadTypesFormFields> getImportTypeFields(Integer importTypeId) throws Exception {
        Query query = sessionFactory.getCurrentSession().createQuery("from programUploadTypesFormFields where programUploadTypeId = :importTypeId order by dspPos asc");
        query.setParameter("importTypeId", importTypeId);

        return query.list();
    }

    /**
     * The 'deleteUploadTypeFields' function will remove all the fields for the passed in upload type
     * with a certain status
     * Usually we will pass in D for fields that should be deleted
     *
     * @param uploadTypeId The id of the clicked upload type
     * @throws Exception
     */
    @Override
    public void deleteUploadTypeFieldsByStatus(Integer importTypeId, String status) throws Exception {
        Query deleteFields = sessionFactory.getCurrentSession().createQuery("delete from programUploadTypesFormFields where programUploadTypeId = :importTypeId and formFieldStatus = :status");
        deleteFields.setParameter("importTypeId", importTypeId);
        deleteFields.setParameter("status", status);  
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
        if (field.getId() != 0) {
        	lastId = field.getId();
        	sessionFactory.getCurrentSession().update(field);
        } else {
        	lastId = (Integer) sessionFactory.getCurrentSession().save(field);

        }
        
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
    @Transactional
    @SuppressWarnings("unchecked")
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
    public void updateProgramUpload(programUploads programUpload) throws Exception {
        sessionFactory.getCurrentSession().update(programUpload);
    }

    @Override
    @Transactional
    public Integer saveProgramUpload(programUploads programUpload) throws Exception {
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
        if (moveJob.getFolderPath() != null) {
        	criteria.add(Restrictions.eq("folderPath", moveJob.getFolderPath()));
        } else {
        	criteria.add(Restrictions.isNull("folderPath"));
        }
        List<MoveFilesLog> moveLogList = criteria.list();
        if (moveLogList.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public programUploads getProgramUploadByAssignedId(programUploads pu) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(programUploads.class);
        criteria.add(Restrictions.eq("assignedId", pu.getAssignedId()));

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

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public delimiters getDelimiter(Integer delimId) throws Exception {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(delimiters.class);

        if (delimId != 0) {
            criteria.add(Restrictions.eq("id", delimId));
        }
        
		List<delimiters> delimeterList = criteria.list();
        
        return delimeterList.get(0);
	}

	@Override
	@Transactional
	public void insertError(programUpload_Errors uploadError) throws Exception {
		sessionFactory.getCurrentSession().save(uploadError);
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<programUpload_Errors> getProgramUploadErrorList(Integer id, String type)
			throws Exception {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(programUpload_Errors.class);

        if (type.equalsIgnoreCase("programUploadId")) {
            criteria.add(Restrictions.eq("programUploadId", id));
        } else {
        	criteria.add(Restrictions.eq("engagementId", id));
        }
        
		List<programUpload_Errors> errorList = criteria.list();      
        return errorList;
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<errorCodes> getErrorCodes(Integer status) throws Exception {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(errorCodes.class);

        if (status != 0) {
            criteria.add(Restrictions.eq("status", status));
        } 
        
		List<errorCodes> errorList = criteria.list();      
        
		return errorList;
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<programUploads> getProgramUploadsByImportType(
			Integer importTypeId) throws Exception {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(programUploads.class);
		
		criteria.add(Restrictions.eq("programUploadTypeId", importTypeId));
        
		List<programUploads> puList = criteria.list();      
        
		return puList;
	}

	@Override
	@Transactional
	public void dropLoadTable(String loadTableName) throws Exception {
		String sql = ("drop TABLE if exists " + loadTableName + ";");
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.executeUpdate();
	}

	@Override
	@Transactional
	public void createLoadTable(String loadTableName) throws Exception {
		String sql = ("create TABLE " + loadTableName
                + "(f1 text DEFAULT NULL,  f2 text DEFAULT NULL,  f3 text DEFAULT NULL,  f4 text DEFAULT NULL,  f5 text DEFAULT NULL,  f6 text DEFAULT NULL,  f7 text DEFAULT NULL,  f8 text DEFAULT NULL,  f9 text DEFAULT NULL,  f10 text DEFAULT NULL,  f11 text DEFAULT NULL,  f12 text DEFAULT NULL,  f13 text DEFAULT NULL,  f14 text DEFAULT NULL,  f15 text DEFAULT NULL,  f16 text DEFAULT NULL,  f17 text DEFAULT NULL,  f18 text DEFAULT NULL,  f19 text DEFAULT NULL,  f20 text DEFAULT NULL,  f21 text DEFAULT NULL,  f22 text DEFAULT NULL,  f23 text DEFAULT NULL,  f24 text DEFAULT NULL,  f25 text DEFAULT NULL,  f26 text DEFAULT NULL,  f27 text DEFAULT NULL,  f28 text DEFAULT NULL,  f29 text DEFAULT NULL,  f30 text DEFAULT NULL,  f31 text DEFAULT NULL,  f32 text DEFAULT NULL,  f33 text DEFAULT NULL,  f34 text DEFAULT NULL,  f35 text DEFAULT NULL,  f36 text DEFAULT NULL,  f37 text DEFAULT NULL,  f38 text DEFAULT NULL,  f39 text DEFAULT NULL,  f40 text DEFAULT NULL,  f41 text DEFAULT NULL,  f42 text DEFAULT NULL,  f43 text DEFAULT NULL,  f44 text DEFAULT NULL,  f45 text DEFAULT NULL,  f46 text DEFAULT NULL,  f47 text DEFAULT NULL,  f48 text DEFAULT NULL,  f49 text DEFAULT NULL,  f50 text DEFAULT NULL,  f51 text DEFAULT NULL,  f52 text DEFAULT NULL,  f53 text DEFAULT NULL,  f54 text DEFAULT NULL,  f55 text DEFAULT NULL,  f56 text DEFAULT NULL,  f57 text DEFAULT NULL,  f58 text DEFAULT NULL,  f59 text DEFAULT NULL,  f60 text DEFAULT NULL,  f61 text DEFAULT NULL,  f62 text DEFAULT NULL,  f63 text DEFAULT NULL,  f64 text DEFAULT NULL,  f65 text DEFAULT NULL,  f66 text DEFAULT NULL,  f67 text DEFAULT NULL,  f68 text DEFAULT NULL,  f69 text DEFAULT NULL,  f70 text DEFAULT NULL,  f71 text DEFAULT NULL,  f72 text DEFAULT NULL,  f73 text DEFAULT NULL,  f74 text DEFAULT NULL,  f75 text DEFAULT NULL,  f76 text DEFAULT NULL,  f77 text DEFAULT NULL,  f78 text DEFAULT NULL,  f79 text DEFAULT NULL,  f80 text DEFAULT NULL,  f81 text DEFAULT NULL,  f82 text DEFAULT NULL,  f83 text DEFAULT NULL,  f84 text DEFAULT NULL,  f85 text DEFAULT NULL,  f86 text DEFAULT NULL,  f87 text DEFAULT NULL,  f88 text DEFAULT NULL,  f89 text DEFAULT NULL,  f90 text DEFAULT NULL,  f91 text DEFAULT NULL,  f92 text DEFAULT NULL,  f93 text DEFAULT NULL,  f94 text DEFAULT NULL,  f95 text DEFAULT NULL,  f96 text DEFAULT NULL,  f97 text DEFAULT NULL,  f98 text DEFAULT NULL,  f99 text DEFAULT NULL,  f100 text DEFAULT NULL,  f101 text DEFAULT NULL,  f102 text DEFAULT NULL,  f103 text DEFAULT NULL,  f104 text DEFAULT NULL,  f105 text DEFAULT NULL,  f106 text DEFAULT NULL,  f107 text DEFAULT NULL,  f108 text DEFAULT NULL,  f109 text DEFAULT NULL,  f110 text DEFAULT NULL,  f111 text DEFAULT NULL,  f112 text DEFAULT NULL,  f113 text DEFAULT NULL,  f114 text DEFAULT NULL,  f115 text DEFAULT NULL,  f116 text DEFAULT NULL,  f117 text DEFAULT NULL,  f118 text DEFAULT NULL,  f119 text DEFAULT NULL,  f120 text DEFAULT NULL,  f121 text DEFAULT NULL,  f122 text DEFAULT NULL,  f123 text DEFAULT NULL,  f124 text DEFAULT NULL,  f125 text DEFAULT NULL,  f126 text DEFAULT NULL,  f127 text DEFAULT NULL,  f128 text DEFAULT NULL,  f129 text DEFAULT NULL,  f130 text DEFAULT NULL,  f131 text DEFAULT NULL,  f132 text DEFAULT NULL,  f133 text DEFAULT NULL,  f134 text DEFAULT NULL,  f135 text DEFAULT NULL,  f136 text DEFAULT NULL,  f137 text DEFAULT NULL,  f138 text DEFAULT NULL,  f139 text DEFAULT NULL,  f140 text DEFAULT NULL,  f141 text DEFAULT NULL,  f142 text DEFAULT NULL,  f143 text DEFAULT NULL,  f144 text DEFAULT NULL,  f145 text DEFAULT NULL,  f146 text DEFAULT NULL,  f147 text DEFAULT NULL,  f148 text DEFAULT NULL,  f149 text DEFAULT NULL,  f150 text DEFAULT NULL,  f151 text DEFAULT NULL,  f152 text DEFAULT NULL,  f153 text DEFAULT NULL,  f154 text DEFAULT NULL,  f155 text DEFAULT NULL,  f156 text DEFAULT NULL,  f157 text DEFAULT NULL,  f158 text DEFAULT NULL,  f159 text DEFAULT NULL,  f160 text DEFAULT NULL,  f161 text DEFAULT NULL,  f162 text DEFAULT NULL,  f163 text DEFAULT NULL,  f164 text DEFAULT NULL,  f165 text DEFAULT NULL,  f166 text DEFAULT NULL,  f167 text DEFAULT NULL,  f168 text DEFAULT NULL,  f169 text DEFAULT NULL,  f170 text DEFAULT NULL,  f171 text DEFAULT NULL,  f172 text DEFAULT NULL,  f173 text DEFAULT NULL,  f174 text DEFAULT NULL,  f175 text DEFAULT NULL,  f176 text DEFAULT NULL,  f177 text DEFAULT NULL,  f178 text DEFAULT NULL,  f179 text DEFAULT NULL,  f180 text DEFAULT NULL,  f181 text DEFAULT NULL,  f182 text DEFAULT NULL,  f183 text DEFAULT NULL,  f184 text DEFAULT NULL,  f185 text DEFAULT NULL,  f186 text DEFAULT NULL,  f187 text DEFAULT NULL,  f188 text DEFAULT NULL,  f189 text DEFAULT NULL,  f190 text DEFAULT NULL,  f191 text DEFAULT NULL,  f192 text DEFAULT NULL,  f193 text DEFAULT NULL,  f194 text DEFAULT NULL,  f195 text DEFAULT NULL,  f196 text DEFAULT NULL,  f197 text DEFAULT NULL,  f198 text DEFAULT NULL,  f199 text DEFAULT NULL,  f200 text DEFAULT NULL,  f201 text DEFAULT NULL,  f202 text DEFAULT NULL,  f203 text DEFAULT NULL,  f204 text DEFAULT NULL,  f205 text DEFAULT NULL,  f206 text DEFAULT NULL,  f207 text DEFAULT NULL,  f208 text DEFAULT NULL,  f209 text DEFAULT NULL,  f210 text DEFAULT NULL,  f211 text DEFAULT NULL,  f212 text DEFAULT NULL,  f213 text DEFAULT NULL,  f214 text DEFAULT NULL,  f215 text DEFAULT NULL,  f216 text DEFAULT NULL,  f217 text DEFAULT NULL,  f218 text DEFAULT NULL,  f219 text DEFAULT NULL,  f220 text DEFAULT NULL,  f221 text DEFAULT NULL,  f222 text DEFAULT NULL,  f223 text DEFAULT NULL,  f224 text DEFAULT NULL,  f225 text DEFAULT NULL,  f226 text DEFAULT NULL,  f227 text DEFAULT NULL,  f228 text DEFAULT NULL,  f229 text DEFAULT NULL,  f230 text DEFAULT NULL,  f231 text DEFAULT NULL,  f232 text DEFAULT NULL,  f233 text DEFAULT NULL,  f234 text DEFAULT NULL,  f235 text DEFAULT NULL,  f236 text DEFAULT NULL,  f237 text DEFAULT NULL,  f238 text DEFAULT NULL,  f239 text DEFAULT NULL,  f240 text DEFAULT NULL,  f241 text DEFAULT NULL,  f242 text DEFAULT NULL,  f243 text DEFAULT NULL,  f244 text DEFAULT NULL,  f245 text DEFAULT NULL,  f246 text DEFAULT NULL,  f247 text DEFAULT NULL,  f248 text DEFAULT NULL,  f249 text DEFAULT NULL,  f250 text DEFAULT NULL,  f251 text DEFAULT NULL,  f252 text DEFAULT NULL,  f253 text DEFAULT NULL,  f254 text DEFAULT NULL,  f255 text DEFAULT NULL,  "
                + "f256 text DEFAULT NULL,f257 text DEFAULT NULL,f258 text DEFAULT NULL,f259 text DEFAULT NULL,f260 text DEFAULT NULL,f261 text DEFAULT NULL,f262 text DEFAULT NULL,f263 text DEFAULT NULL,f264 text DEFAULT NULL,f265 text DEFAULT NULL,f266 text DEFAULT NULL,f267 text DEFAULT NULL,f268 text DEFAULT NULL,f269 text DEFAULT NULL,f270 text DEFAULT NULL,f271 text DEFAULT NULL,f272 text DEFAULT NULL,f273 text DEFAULT NULL,f274 text DEFAULT NULL,f275 text DEFAULT NULL,f276 text DEFAULT NULL,f277 text DEFAULT NULL,f278 text DEFAULT NULL,f279 text DEFAULT NULL,f280 text DEFAULT NULL,f281 text DEFAULT NULL,f282 text DEFAULT NULL,f283 text DEFAULT NULL,f284 text DEFAULT NULL,f285 text DEFAULT NULL,f286 text DEFAULT NULL,f287 text DEFAULT NULL,f288 text DEFAULT NULL,f289 text DEFAULT NULL,f290 text DEFAULT NULL,f291 text DEFAULT NULL,f292 text DEFAULT NULL,f293 text DEFAULT NULL,f294 text DEFAULT NULL,f295 text DEFAULT NULL,f296 text DEFAULT NULL,f297 text DEFAULT NULL,f298 text DEFAULT NULL,f299 text DEFAULT NULL,f300 text DEFAULT NULL,f301 text DEFAULT NULL,f302 text DEFAULT NULL,f303 text DEFAULT NULL,f304 text DEFAULT NULL,f305 text DEFAULT NULL,f306 text DEFAULT NULL,f307 text DEFAULT NULL,f308 text DEFAULT NULL,f309 text DEFAULT NULL,f310 text DEFAULT NULL,f311 text DEFAULT NULL,f312 text DEFAULT NULL,f313 text DEFAULT NULL,f314 text DEFAULT NULL,f315 text DEFAULT NULL,f316 text DEFAULT NULL,f317 text DEFAULT NULL,f318 text DEFAULT NULL,f319 text DEFAULT NULL,f320 text DEFAULT NULL,f321 text DEFAULT NULL,f322 text DEFAULT NULL,f323 text DEFAULT NULL,f324 text DEFAULT NULL,f325 text DEFAULT NULL,f326 text DEFAULT NULL,f327 text DEFAULT NULL,f328 text DEFAULT NULL,f329 text DEFAULT NULL,f330 text DEFAULT NULL,f331 text DEFAULT NULL,f332 text DEFAULT NULL,f333 text DEFAULT NULL,f334 text DEFAULT NULL,f335 text DEFAULT NULL,f336 text DEFAULT NULL,f337 text DEFAULT NULL,f338 text DEFAULT NULL,f339 text DEFAULT NULL,f340 text DEFAULT NULL,f341 text DEFAULT NULL,f342 text DEFAULT NULL,f343 text DEFAULT NULL,f344 text DEFAULT NULL,f345 text DEFAULT NULL,f346 text DEFAULT NULL,f347 text DEFAULT NULL,f348 text DEFAULT NULL,f349 text DEFAULT NULL,f350 text DEFAULT NULL,f351 text DEFAULT NULL,f352 text DEFAULT NULL,f353 text DEFAULT NULL,f354 text DEFAULT NULL,f355 text DEFAULT NULL,f356 text DEFAULT NULL,f357 text DEFAULT NULL,f358 text DEFAULT NULL,f359 text DEFAULT NULL,f360 text DEFAULT NULL,f361 text DEFAULT NULL,f362 text DEFAULT NULL,f363 text DEFAULT NULL,f364 text DEFAULT NULL,f365 text DEFAULT NULL,f366 text DEFAULT NULL,f367 text DEFAULT NULL,f368 text DEFAULT NULL,f369 text DEFAULT NULL,f370 text DEFAULT NULL,f371 text DEFAULT NULL,f372 text DEFAULT NULL,f373 text DEFAULT NULL,f374 text DEFAULT NULL,f375 text DEFAULT NULL,f376 text DEFAULT NULL,f377 text DEFAULT NULL,f378 text DEFAULT NULL,f379 text DEFAULT NULL,f380 text DEFAULT NULL,f381 text DEFAULT NULL,f382 text DEFAULT NULL,f383 text DEFAULT NULL,f384 text DEFAULT NULL,f385 text DEFAULT NULL,f386 text DEFAULT NULL,f387 text DEFAULT NULL,f388 text DEFAULT NULL,f389 text DEFAULT NULL,f390 text DEFAULT NULL,f391 text DEFAULT NULL,f392 text DEFAULT NULL,f393 text DEFAULT NULL,f394 text DEFAULT NULL,f395 text DEFAULT NULL,f396 text DEFAULT NULL,f397 text DEFAULT NULL,f398 text DEFAULT NULL,f399 text DEFAULT NULL,f400 text DEFAULT NULL,f401 text DEFAULT NULL,f402 text DEFAULT NULL,f403 text DEFAULT NULL,f404 text DEFAULT NULL,f405 text DEFAULT NULL,f406 text DEFAULT NULL,f407 text DEFAULT NULL,f408 text DEFAULT NULL,f409 text DEFAULT NULL,f410 text DEFAULT NULL,f411 text DEFAULT NULL,f412 text DEFAULT NULL,f413 text DEFAULT NULL,f414 text DEFAULT NULL,f415 text DEFAULT NULL,f416 text DEFAULT NULL,f417 text DEFAULT NULL,f418 text DEFAULT NULL,f419 text DEFAULT NULL,f420 text DEFAULT NULL,f421 text DEFAULT NULL,f422 text DEFAULT NULL,f423 text DEFAULT NULL,f424 text DEFAULT NULL,f425 text DEFAULT NULL,f426 text DEFAULT NULL,f427 text DEFAULT NULL,f428 text DEFAULT NULL,f429 text DEFAULT NULL,f430 text DEFAULT NULL,f431 text DEFAULT NULL,f432 text DEFAULT NULL,f433 text DEFAULT NULL,f434 text DEFAULT NULL,f435 text DEFAULT NULL,f436 text DEFAULT NULL,f437 text DEFAULT NULL,f438 text DEFAULT NULL,f439 text DEFAULT NULL,f440 text DEFAULT NULL,f441 text DEFAULT NULL,f442 text DEFAULT NULL,f443 text DEFAULT NULL,f444 text DEFAULT NULL,f445 text DEFAULT NULL,f446 text DEFAULT NULL,f447 text DEFAULT NULL,f448 text DEFAULT NULL,f449 text DEFAULT NULL,f450 text DEFAULT NULL,f451 text DEFAULT NULL,f452 text DEFAULT NULL,f453 text DEFAULT NULL,f454 text DEFAULT NULL,f455 text DEFAULT NULL,f456 text DEFAULT NULL,f457 text DEFAULT NULL,f458 text DEFAULT NULL,f459 text DEFAULT NULL,f460 text DEFAULT NULL,f461 text DEFAULT NULL,f462 text DEFAULT NULL,f463 text DEFAULT NULL,f464 text DEFAULT NULL,f465 text DEFAULT NULL,f466 text DEFAULT NULL,f467 text DEFAULT NULL,f468 text DEFAULT NULL,f469 text DEFAULT NULL,f470 text DEFAULT NULL,f471 text DEFAULT NULL,f472 text DEFAULT NULL,f473 text DEFAULT NULL,f474 text DEFAULT NULL,f475 text DEFAULT NULL,f476 text DEFAULT NULL,f477 text DEFAULT NULL,f478 text DEFAULT NULL,f479 text DEFAULT NULL,f480 text DEFAULT NULL,f481 text DEFAULT NULL,f482 text DEFAULT NULL,f483 text DEFAULT NULL,f484 text DEFAULT NULL,f485 text DEFAULT NULL,f486 text DEFAULT NULL,f487 text DEFAULT NULL,f488 text DEFAULT NULL,f489 text DEFAULT NULL,f490 text DEFAULT NULL,f491 text DEFAULT NULL,f492 text DEFAULT NULL,f493 text DEFAULT NULL,f494 text DEFAULT NULL,f495 text DEFAULT NULL,f496 text DEFAULT NULL,f497 text DEFAULT NULL,f498 text DEFAULT NULL,f499 text DEFAULT NULL,f500 text DEFAULT NULL, "
                + " programUploadId int(11),  loadTableId varchar(45) ,  id int(11) NOT NULL AUTO_INCREMENT,  PRIMARY KEY (id)) ENGINE=InnoDB AUTO_INCREMENT=1038 DEFAULT CHARSET=latin1;");
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.executeUpdate();
		
	}
	
	@Override
	@Transactional
	public void indexLoadTable(String loadTableName) throws Exception {
		 String sql = "ALTER TABLE " + loadTableName + " ADD INDEX loadTableId (loadTableId ASC);";
         Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
         query.executeUpdate();		
	}

	@Override
	@Transactional
	public void insertLoadData(programUploadTypes put, String loadTableName, String fileWithPath) throws Exception {
		String sql = ("LOAD DATA LOCAL INFILE '" + fileWithPath + "' INTO TABLE "
                + loadTableName + " fields terminated by '" + put.getDelimChar() + "' "
                + " ENCLOSED BY '\"' ESCAPED BY ''"
                + " LINES TERMINATED BY '\\n'");
        if (put.isContainsHeaderRow()) {
            sql = sql + "  IGNORE 1 LINES";
        }
        sql = sql + ";";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.executeUpdate();
	}

	@Override
	@Transactional
	public void updateLoadTable(String loadTableName, Integer programUploadId)
			throws Exception {
		String sql = ("update " + loadTableName + " set programUploadId = :programUploadId, loadTableId = concat('" + loadTableName + "_', id);");
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("programUploadId", programUploadId);
        query.executeUpdate();		
	}

	@Override
	@Transactional
	public void insertUploadRecords(String loadTableName,
			Integer programUploadId) throws Exception {
		String sql = ("insert into programuploadrecords (programUploadId, statusId, loadTableId) "
                + " select :programUploadId, 9, loadTableId from " + loadTableName + ";");
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("programUploadId", programUploadId);
        query.executeUpdate();
		
	}

	@Override
	@Transactional
	public void insertUploadRecordDetails(Integer programUploadId)
			throws Exception {
		String sql = ("insert into programUploadRecordDetails (programUploadId, programUploadRecordId, loadTableId) select :programUploadId, id, loadTableId from programuploadrecords where programUploadId = :programUploadId");
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("programUploadId", programUploadId);
        query.executeUpdate();
		
	}

	@Override
	@Transactional
	public void insertUploadRecordDetailsData(String loadTableName) {
				String sql = ("UPDATE programUploadRecordDetails details"
	                    + "	INNER JOIN " + loadTableName + " loadTableTemp ON "
	                    + "details.loadTableId = loadTableTemp.loadTableId "
	                    + "set  details.F1 = loadTableTemp.F1, "
	                    + "details.F2 = loadTableTemp.F2, "
	                    + "details.F3 = loadTableTemp.F3, "
	                    + "details.F4 = loadTableTemp.F4, "
	                    + "details.F5 = loadTableTemp.F5, "
	                    + "details.F6 = loadTableTemp.F6, "
	                    + "details.F7 = loadTableTemp.F7, "
	                    + "details.F8 = loadTableTemp.F8, "
	                    + "details.F9 = loadTableTemp.F9, "
	                    + "details.F10 = loadTableTemp.F10, "
	                    + "details.F11 = loadTableTemp.F11, "
	                    + "details.F12 = loadTableTemp.F12, "
	                    + "details.F13 = loadTableTemp.F13, "
	                    + "details.F14 = loadTableTemp.F14, "
	                    + "details.F15 = loadTableTemp.F15, "
	                    + "details.F16 = loadTableTemp.F16, "
	                    + "details.F17 = loadTableTemp.F17, "
	                    + "details.F18 = loadTableTemp.F18, "
	                    + "details.F19 = loadTableTemp.F19, "
	                    + "details.F20 = loadTableTemp.F20, "
	                    + "details.F21 = loadTableTemp.F21, "
	                    + "details.F22 = loadTableTemp.F22, "
	                    + "details.F23 = loadTableTemp.F23, "
	                    + "details.F24 = loadTableTemp.F24, "
	                    + "details.F25 = loadTableTemp.F25, "
	                    + "details.F26 = loadTableTemp.F26, "
	                    + "details.F27 = loadTableTemp.F27, "
	                    + "details.F28 = loadTableTemp.F28, "
	                    + "details.F29 = loadTableTemp.F29, "
	                    + "details.F30 = loadTableTemp.F30, "
	                    + "details.F31 = loadTableTemp.F31, "
	                    + "details.F32 = loadTableTemp.F32, "
	                    + "details.F33 = loadTableTemp.F33, "
	                    + "details.F34 = loadTableTemp.F34, "
	                    + "details.F35 = loadTableTemp.F35, "
	                    + "details.F36 = loadTableTemp.F36, "
	                    + "details.F37 = loadTableTemp.F37, "
	                    + "details.F38 = loadTableTemp.F38, "
	                    + "details.F39 = loadTableTemp.F39, "
	                    + "details.F40 = loadTableTemp.F40, "
	                    + "details.F41 = loadTableTemp.F41, "
	                    + "details.F42 = loadTableTemp.F42, "
	                    + "details.F43 = loadTableTemp.F43, "
	                    + "details.F44 = loadTableTemp.F44, "
	                    + "details.F45 = loadTableTemp.F45, "
	                    + "details.F46 = loadTableTemp.F46, "
	                    + "details.F47 = loadTableTemp.F47, "
	                    + "details.F48 = loadTableTemp.F48, "
	                    + "details.F49 = loadTableTemp.F49, "
	                    + "details.F50 = loadTableTemp.F50, "
	                    + "details.F51 = loadTableTemp.F51, "
	                    + "details.F52 = loadTableTemp.F52, "
	                    + "details.F53 = loadTableTemp.F53, "
	                    + "details.F54 = loadTableTemp.F54, "
	                    + "details.F55 = loadTableTemp.F55, "
	                    + "details.F56 = loadTableTemp.F56, "
	                    + "details.F57 = loadTableTemp.F57, "
	                    + "details.F58 = loadTableTemp.F58, "
	                    + "details.F59 = loadTableTemp.F59, "
	                    + "details.F60 = loadTableTemp.F60, "
	                    + "details.F61 = loadTableTemp.F61, "
	                    + "details.F62 = loadTableTemp.F62, "
	                    + "details.F63 = loadTableTemp.F63, "
	                    + "details.F64 = loadTableTemp.F64, "
	                    + "details.F65 = loadTableTemp.F65, "
	                    + "details.F66 = loadTableTemp.F66, "
	                    + "details.F67 = loadTableTemp.F67, "
	                    + "details.F68 = loadTableTemp.F68, "
	                    + "details.F69 = loadTableTemp.F69, "
	                    + "details.F70 = loadTableTemp.F70, "
	                    + "details.F71 = loadTableTemp.F71, "
	                    + "details.F72 = loadTableTemp.F72, "
	                    + "details.F73 = loadTableTemp.F73, "
	                    + "details.F74 = loadTableTemp.F74, "
	                    + "details.F75 = loadTableTemp.F75, "
	                    + "details.F76 = loadTableTemp.F76, "
	                    + "details.F77 = loadTableTemp.F77, "
	                    + "details.F78 = loadTableTemp.F78, "
	                    + "details.F79 = loadTableTemp.F79, "
	                    + "details.F80 = loadTableTemp.F80, "
	                    + "details.F81 = loadTableTemp.F81, "
	                    + "details.F82 = loadTableTemp.F82, "
	                    + "details.F83 = loadTableTemp.F83, "
	                    + "details.F84 = loadTableTemp.F84, "
	                    + "details.F85 = loadTableTemp.F85, "
	                    + "details.F86 = loadTableTemp.F86, "
	                    + "details.F87 = loadTableTemp.F87, "
	                    + "details.F88 = loadTableTemp.F88, "
	                    + "details.F89 = loadTableTemp.F89, "
	                    + "details.F90 = loadTableTemp.F90, "
	                    + "details.F91 = loadTableTemp.F91, "
	                    + "details.F92 = loadTableTemp.F92, "
	                    + "details.F93 = loadTableTemp.F93, "
	                    + "details.F94 = loadTableTemp.F94, "
	                    + "details.F95 = loadTableTemp.F95, "
	                    + "details.F96 = loadTableTemp.F96, "
	                    + "details.F97 = loadTableTemp.F97, "
	                    + "details.F98 = loadTableTemp.F98, "
	                    + "details.F99 = loadTableTemp.F99, "
	                    + "details.F100 = loadTableTemp.F100, "
	                    + "details.F101 = loadTableTemp.F101, "
	                    + "details.F102 = loadTableTemp.F102, "
	                    + "details.F103 = loadTableTemp.F103, "
	                    + "details.F104 = loadTableTemp.F104, "
	                    + "details.F105 = loadTableTemp.F105, "
	                    + "details.F106 = loadTableTemp.F106, "
	                    + "details.F107 = loadTableTemp.F107, "
	                    + "details.F108 = loadTableTemp.F108, "
	                    + "details.F109 = loadTableTemp.F109, "
	                    + "details.F110 = loadTableTemp.F110, "
	                    + "details.F111 = loadTableTemp.F111, "
	                    + "details.F112 = loadTableTemp.F112, "
	                    + "details.F113 = loadTableTemp.F113, "
	                    + "details.F114 = loadTableTemp.F114, "
	                    + "details.F115 = loadTableTemp.F115, "
	                    + "details.F116 = loadTableTemp.F116, "
	                    + "details.F117 = loadTableTemp.F117, "
	                    + "details.F118 = loadTableTemp.F118, "
	                    + "details.F119 = loadTableTemp.F119, "
	                    + "details.F120 = loadTableTemp.F120, "
	                    + "details.F121 = loadTableTemp.F121, "
	                    + "details.F122 = loadTableTemp.F122, "
	                    + "details.F123 = loadTableTemp.F123, "
	                    + "details.F124 = loadTableTemp.F124, "
	                    + "details.F125 = loadTableTemp.F125, "
	                    + "details.F126 = loadTableTemp.F126, "
	                    + "details.F127 = loadTableTemp.F127, "
	                    + "details.F128 = loadTableTemp.F128, "
	                    + "details.F129 = loadTableTemp.F129, "
	                    + "details.F130 = loadTableTemp.F130, "
	                    + "details.F131 = loadTableTemp.F131, "
	                    + "details.F132 = loadTableTemp.F132, "
	                    + "details.F133 = loadTableTemp.F133, "
	                    + "details.F134 = loadTableTemp.F134, "
	                    + "details.F135 = loadTableTemp.F135, "
	                    + "details.F136 = loadTableTemp.F136, "
	                    + "details.F137 = loadTableTemp.F137, "
	                    + "details.F138 = loadTableTemp.F138, "
	                    + "details.F139 = loadTableTemp.F139, "
	                    + "details.F140 = loadTableTemp.F140, "
	                    + "details.F141 = loadTableTemp.F141, "
	                    + "details.F142 = loadTableTemp.F142, "
	                    + "details.F143 = loadTableTemp.F143, "
	                    + "details.F144 = loadTableTemp.F144, "
	                    + "details.F145 = loadTableTemp.F145, "
	                    + "details.F146 = loadTableTemp.F146, "
	                    + "details.F147 = loadTableTemp.F147, "
	                    + "details.F148 = loadTableTemp.F148, "
	                    + "details.F149 = loadTableTemp.F149, "
	                    + "details.F150 = loadTableTemp.F150, "
	                    + "details.F151 = loadTableTemp.F151, "
	                    + "details.F152 = loadTableTemp.F152, "
	                    + "details.F153 = loadTableTemp.F153, "
	                    + "details.F154 = loadTableTemp.F154, "
	                    + "details.F155 = loadTableTemp.F155, "
	                    + "details.F156 = loadTableTemp.F156, "
	                    + "details.F157 = loadTableTemp.F157, "
	                    + "details.F158 = loadTableTemp.F158, "
	                    + "details.F159 = loadTableTemp.F159, "
	                    + "details.F160 = loadTableTemp.F160, "
	                    + "details.F161 = loadTableTemp.F161, "
	                    + "details.F162 = loadTableTemp.F162, "
	                    + "details.F163 = loadTableTemp.F163, "
	                    + "details.F164 = loadTableTemp.F164, "
	                    + "details.F165 = loadTableTemp.F165, "
	                    + "details.F166 = loadTableTemp.F166, "
	                    + "details.F167 = loadTableTemp.F167, "
	                    + "details.F168 = loadTableTemp.F168, "
	                    + "details.F169 = loadTableTemp.F169, "
	                    + "details.F170 = loadTableTemp.F170, "
	                    + "details.F171 = loadTableTemp.F171, "
	                    + "details.F172 = loadTableTemp.F172, "
	                    + "details.F173 = loadTableTemp.F173, "
	                    + "details.F174 = loadTableTemp.F174, "
	                    + "details.F175 = loadTableTemp.F175, "
	                    + "details.F176 = loadTableTemp.F176, "
	                    + "details.F177 = loadTableTemp.F177, "
	                    + "details.F178 = loadTableTemp.F178, "
	                    + "details.F179 = loadTableTemp.F179, "
	                    + "details.F180 = loadTableTemp.F180, "
	                    + "details.F181 = loadTableTemp.F181, "
	                    + "details.F182 = loadTableTemp.F182, "
	                    + "details.F183 = loadTableTemp.F183, "
	                    + "details.F184 = loadTableTemp.F184, "
	                    + "details.F185 = loadTableTemp.F185, "
	                    + "details.F186 = loadTableTemp.F186, "
	                    + "details.F187 = loadTableTemp.F187, "
	                    + "details.F188 = loadTableTemp.F188, "
	                    + "details.F189 = loadTableTemp.F189, "
	                    + "details.F190 = loadTableTemp.F190, "
	                    + "details.F191 = loadTableTemp.F191, "
	                    + "details.F192 = loadTableTemp.F192, "
	                    + "details.F193 = loadTableTemp.F193, "
	                    + "details.F194 = loadTableTemp.F194, "
	                    + "details.F195 = loadTableTemp.F195, "
	                    + "details.F196 = loadTableTemp.F196, "
	                    + "details.F197 = loadTableTemp.F197, "
	                    + "details.F198 = loadTableTemp.F198, "
	                    + "details.F199 = loadTableTemp.F199, "
	                    + "details.F200 = loadTableTemp.F200, "
	                    + "details.F201 = loadTableTemp.F201, "
	                    + "details.F202 = loadTableTemp.F202, "
	                    + "details.F203 = loadTableTemp.F203, "
	                    + "details.F204 = loadTableTemp.F204, "
	                    + "details.F205 = loadTableTemp.F205, "
	                    + "details.F206 = loadTableTemp.F206, "
	                    + "details.F207 = loadTableTemp.F207, "
	                    + "details.F208 = loadTableTemp.F208, "
	                    + "details.F209 = loadTableTemp.F209, "
	                    + "details.F210 = loadTableTemp.F210, "
	                    + "details.F211 = loadTableTemp.F211, "
	                    + "details.F212 = loadTableTemp.F212, "
	                    + "details.F213 = loadTableTemp.F213, "
	                    + "details.F214 = loadTableTemp.F214, "
	                    + "details.F215 = loadTableTemp.F215, "
	                    + "details.F216 = loadTableTemp.F216, "
	                    + "details.F217 = loadTableTemp.F217, "
	                    + "details.F218 = loadTableTemp.F218, "
	                    + "details.F219 = loadTableTemp.F219, "
	                    + "details.F220 = loadTableTemp.F220, "
	                    + "details.F221 = loadTableTemp.F221, "
	                    + "details.F222 = loadTableTemp.F222, "
	                    + "details.F223 = loadTableTemp.F223, "
	                    + "details.F224 = loadTableTemp.F224, "
	                    + "details.F225 = loadTableTemp.F225, "
	                    + "details.F226 = loadTableTemp.F226, "
	                    + "details.F227 = loadTableTemp.F227, "
	                    + "details.F228 = loadTableTemp.F228, "
	                    + "details.F229 = loadTableTemp.F229, "
	                    + "details.F230 = loadTableTemp.F230, "
	                    + "details.F231 = loadTableTemp.F231, "
	                    + "details.F232 = loadTableTemp.F232, "
	                    + "details.F233 = loadTableTemp.F233, "
	                    + "details.F234 = loadTableTemp.F234, "
	                    + "details.F235 = loadTableTemp.F235, "
	                    + "details.F236 = loadTableTemp.F236, "
	                    + "details.F237 = loadTableTemp.F237, "
	                    + "details.F238 = loadTableTemp.F238, "
	                    + "details.F239 = loadTableTemp.F239, "
	                    + "details.F240 = loadTableTemp.F240, "
	                    + "details.F241 = loadTableTemp.F241, "
	                    + "details.F242 = loadTableTemp.F242, "
	                    + "details.F243 = loadTableTemp.F243, "
	                    + "details.F244 = loadTableTemp.F244, "
	                    + "details.F245 = loadTableTemp.F245, "
	                    + "details.F246 = loadTableTemp.F246, "
	                    + "details.F247 = loadTableTemp.F247, "
	                    + "details.F248 = loadTableTemp.F248, "
	                    + "details.F249 = loadTableTemp.F249, "
	                    + "details.F250 = loadTableTemp.F250, "
	                    + "details.F251 = loadTableTemp.F251, "
	                    + "details.F252 = loadTableTemp.F252, "
	                    + "details.F253 = loadTableTemp.F253, "
	                    + "details.F254 = loadTableTemp.F254, "
	                    + "details.F255 = loadTableTemp.F255,"
	                    + "details.F256 =  loadTableTemp.F256,"
	                    + "details.F257 =  loadTableTemp.F257,"
	                    + "details.F258 =  loadTableTemp.F258,"
	                    + "details.F259 =  loadTableTemp.F259,"
	                    + "details.F260 =  loadTableTemp.F260,"
	                    + "details.F261 =  loadTableTemp.F261,"
	                    + "details.F262 =  loadTableTemp.F262,"
	                    + "details.F263 =  loadTableTemp.F263,"
	                    + "details.F264 =  loadTableTemp.F264,"
	                    + "details.F265 =  loadTableTemp.F265,"
	                    + "details.F266 =  loadTableTemp.F266,"
	                    + "details.F267 =  loadTableTemp.F267,"
	                    + "details.F268 =  loadTableTemp.F268,"
	                    + "details.F269 =  loadTableTemp.F269,"
	                    + "details.F270 =  loadTableTemp.F270,"
	                    + "details.F271 =  loadTableTemp.F271,"
	                    + "details.F272 =  loadTableTemp.F272,"
	                    + "details.F273 =  loadTableTemp.F273,"
	                    + "details.F274 =  loadTableTemp.F274,"
	                    + "details.F275 =  loadTableTemp.F275,"
	                    + "details.F276 =  loadTableTemp.F276,"
	                    + "details.F277 =  loadTableTemp.F277,"
	                    + "details.F278 =  loadTableTemp.F278,"
	                    + "details.F279 =  loadTableTemp.F279,"
	                    + "details.F280 =  loadTableTemp.F280,"
	                    + "details.F281 =  loadTableTemp.F281,"
	                    + "details.F282 =  loadTableTemp.F282,"
	                    + "details.F283 =  loadTableTemp.F283,"
	                    + "details.F284 =  loadTableTemp.F284,"
	                    + "details.F285 =  loadTableTemp.F285,"
	                    + "details.F286 =  loadTableTemp.F286,"
	                    + "details.F287 =  loadTableTemp.F287,"
	                    + "details.F288 =  loadTableTemp.F288,"
	                    + "details.F289 =  loadTableTemp.F289,"
	                    + "details.F290 =  loadTableTemp.F290,"
	                    + "details.F291 =  loadTableTemp.F291,"
	                    + "details.F292 =  loadTableTemp.F292,"
	                    + "details.F293 =  loadTableTemp.F293,"
	                    + "details.F294 =  loadTableTemp.F294,"
	                    + "details.F295 =  loadTableTemp.F295,"
	                    + "details.F296 =  loadTableTemp.F296,"
	                    + "details.F297 =  loadTableTemp.F297,"
	                    + "details.F298 =  loadTableTemp.F298,"
	                    + "details.F299 =  loadTableTemp.F299,"
	                    + "details.F300 =  loadTableTemp.F300,"
	                    + "details.F301 =  loadTableTemp.F301,"
	                    + "details.F302 =  loadTableTemp.F302,"
	                    + "details.F303 =  loadTableTemp.F303,"
	                    + "details.F304 =  loadTableTemp.F304,"
	                    + "details.F305 =  loadTableTemp.F305,"
	                    + "details.F306 =  loadTableTemp.F306,"
	                    + "details.F307 =  loadTableTemp.F307,"
	                    + "details.F308 =  loadTableTemp.F308,"
	                    + "details.F309 =  loadTableTemp.F309,"
	                    + "details.F310 =  loadTableTemp.F310,"
	                    + "details.F311 =  loadTableTemp.F311,"
	                    + "details.F312 =  loadTableTemp.F312,"
	                    + "details.F313 =  loadTableTemp.F313,"
	                    + "details.F314 =  loadTableTemp.F314,"
	                    + "details.F315 =  loadTableTemp.F315,"
	                    + "details.F316 =  loadTableTemp.F316,"
	                    + "details.F317 =  loadTableTemp.F317,"
	                    + "details.F318 =  loadTableTemp.F318,"
	                    + "details.F319 =  loadTableTemp.F319,"
	                    + "details.F320 =  loadTableTemp.F320,"
	                    + "details.F321 =  loadTableTemp.F321,"
	                    + "details.F322 =  loadTableTemp.F322,"
	                    + "details.F323 =  loadTableTemp.F323,"
	                    + "details.F324 =  loadTableTemp.F324,"
	                    + "details.F325 =  loadTableTemp.F325,"
	                    + "details.F326 =  loadTableTemp.F326,"
	                    + "details.F327 =  loadTableTemp.F327,"
	                    + "details.F328 =  loadTableTemp.F328,"
	                    + "details.F329 =  loadTableTemp.F329,"
	                    + "details.F330 =  loadTableTemp.F330,"
	                    + "details.F331 =  loadTableTemp.F331,"
	                    + "details.F332 =  loadTableTemp.F332,"
	                    + "details.F333 =  loadTableTemp.F333,"
	                    + "details.F334 =  loadTableTemp.F334,"
	                    + "details.F335 =  loadTableTemp.F335,"
	                    + "details.F336 =  loadTableTemp.F336,"
	                    + "details.F337 =  loadTableTemp.F337,"
	                    + "details.F338 =  loadTableTemp.F338,"
	                    + "details.F339 =  loadTableTemp.F339,"
	                    + "details.F340 =  loadTableTemp.F340,"
	                    + "details.F341 =  loadTableTemp.F341,"
	                    + "details.F342 =  loadTableTemp.F342,"
	                    + "details.F343 =  loadTableTemp.F343,"
	                    + "details.F344 =  loadTableTemp.F344,"
	                    + "details.F345 =  loadTableTemp.F345,"
	                    + "details.F346 =  loadTableTemp.F346,"
	                    + "details.F347 =  loadTableTemp.F347,"
	                    + "details.F348 =  loadTableTemp.F348,"
	                    + "details.F349 =  loadTableTemp.F349,"
	                    + "details.F350 =  loadTableTemp.F350,"
	                    + "details.F351 =  loadTableTemp.F351,"
	                    + "details.F352 =  loadTableTemp.F352,"
	                    + "details.F353 =  loadTableTemp.F353,"
	                    + "details.F354 =  loadTableTemp.F354,"
	                    + "details.F355 =  loadTableTemp.F355,"
	                    + "details.F356 =  loadTableTemp.F356,"
	                    + "details.F357 =  loadTableTemp.F357,"
	                    + "details.F358 =  loadTableTemp.F358,"
	                    + "details.F359 =  loadTableTemp.F359,"
	                    + "details.F360 =  loadTableTemp.F360,"
	                    + "details.F361 =  loadTableTemp.F361,"
	                    + "details.F362 =  loadTableTemp.F362,"
	                    + "details.F363 =  loadTableTemp.F363,"
	                    + "details.F364 =  loadTableTemp.F364,"
	                    + "details.F365 =  loadTableTemp.F365,"
	                    + "details.F366 =  loadTableTemp.F366,"
	                    + "details.F367 =  loadTableTemp.F367,"
	                    + "details.F368 =  loadTableTemp.F368,"
	                    + "details.F369 =  loadTableTemp.F369,"
	                    + "details.F370 =  loadTableTemp.F370,"
	                    + "details.F371 =  loadTableTemp.F371,"
	                    + "details.F372 =  loadTableTemp.F372,"
	                    + "details.F373 =  loadTableTemp.F373,"
	                    + "details.F374 =  loadTableTemp.F374,"
	                    + "details.F375 =  loadTableTemp.F375,"
	                    + "details.F376 =  loadTableTemp.F376,"
	                    + "details.F377 =  loadTableTemp.F377,"
	                    + "details.F378 =  loadTableTemp.F378,"
	                    + "details.F379 =  loadTableTemp.F379,"
	                    + "details.F380 =  loadTableTemp.F380,"
	                    + "details.F381 =  loadTableTemp.F381,"
	                    + "details.F382 =  loadTableTemp.F382,"
	                    + "details.F383 =  loadTableTemp.F383,"
	                    + "details.F384 =  loadTableTemp.F384,"
	                    + "details.F385 =  loadTableTemp.F385,"
	                    + "details.F386 =  loadTableTemp.F386,"
	                    + "details.F387 =  loadTableTemp.F387,"
	                    + "details.F388 =  loadTableTemp.F388,"
	                    + "details.F389 =  loadTableTemp.F389,"
	                    + "details.F390 =  loadTableTemp.F390,"
	                    + "details.F391 =  loadTableTemp.F391,"
	                    + "details.F392 =  loadTableTemp.F392,"
	                    + "details.F393 =  loadTableTemp.F393,"
	                    + "details.F394 =  loadTableTemp.F394,"
	                    + "details.F395 =  loadTableTemp.F395,"
	                    + "details.F396 =  loadTableTemp.F396,"
	                    + "details.F397 =  loadTableTemp.F397,"
	                    + "details.F398 =  loadTableTemp.F398,"
	                    + "details.F399 =  loadTableTemp.F399,"
	                    + "details.F400 =  loadTableTemp.F400,"
	                    + "details.F401 =  loadTableTemp.F401,"
	                    + "details.F402 =  loadTableTemp.F402,"
	                    + "details.F403 =  loadTableTemp.F403,"
	                    + "details.F404 =  loadTableTemp.F404,"
	                    + "details.F405 =  loadTableTemp.F405,"
	                    + "details.F406 =  loadTableTemp.F406,"
	                    + "details.F407 =  loadTableTemp.F407,"
	                    + "details.F408 =  loadTableTemp.F408,"
	                    + "details.F409 =  loadTableTemp.F409,"
	                    + "details.F410 =  loadTableTemp.F410,"
	                    + "details.F411 =  loadTableTemp.F411,"
	                    + "details.F412 =  loadTableTemp.F412,"
	                    + "details.F413 =  loadTableTemp.F413,"
	                    + "details.F414 =  loadTableTemp.F414,"
	                    + "details.F415 =  loadTableTemp.F415,"
	                    + "details.F416 =  loadTableTemp.F416,"
	                    + "details.F417 =  loadTableTemp.F417,"
	                    + "details.F418 =  loadTableTemp.F418,"
	                    + "details.F419 =  loadTableTemp.F419,"
	                    + "details.F420 =  loadTableTemp.F420,"
	                    + "details.F421 =  loadTableTemp.F421,"
	                    + "details.F422 =  loadTableTemp.F422,"
	                    + "details.F423 =  loadTableTemp.F423,"
	                    + "details.F424 =  loadTableTemp.F424,"
	                    + "details.F425 =  loadTableTemp.F425,"
	                    + "details.F426 =  loadTableTemp.F426,"
	                    + "details.F427 =  loadTableTemp.F427,"
	                    + "details.F428 =  loadTableTemp.F428,"
	                    + "details.F429 =  loadTableTemp.F429,"
	                    + "details.F430 =  loadTableTemp.F430,"
	                    + "details.F431 =  loadTableTemp.F431,"
	                    + "details.F432 =  loadTableTemp.F432,"
	                    + "details.F433 =  loadTableTemp.F433,"
	                    + "details.F434 =  loadTableTemp.F434,"
	                    + "details.F435 =  loadTableTemp.F435,"
	                    + "details.F436 =  loadTableTemp.F436,"
	                    + "details.F437 =  loadTableTemp.F437,"
	                    + "details.F438 =  loadTableTemp.F438,"
	                    + "details.F439 =  loadTableTemp.F439,"
	                    + "details.F440 =  loadTableTemp.F440,"
	                    + "details.F441 =  loadTableTemp.F441,"
	                    + "details.F442 =  loadTableTemp.F442,"
	                    + "details.F443 =  loadTableTemp.F443,"
	                    + "details.F444 =  loadTableTemp.F444,"
	                    + "details.F445 =  loadTableTemp.F445,"
	                    + "details.F446 =  loadTableTemp.F446,"
	                    + "details.F447 =  loadTableTemp.F447,"
	                    + "details.F448 =  loadTableTemp.F448,"
	                    + "details.F449 =  loadTableTemp.F449,"
	                    + "details.F450 =  loadTableTemp.F450,"
	                    + "details.F451 =  loadTableTemp.F451,"
	                    + "details.F452 =  loadTableTemp.F452,"
	                    + "details.F453 =  loadTableTemp.F453,"
	                    + "details.F454 =  loadTableTemp.F454,"
	                    + "details.F455 =  loadTableTemp.F455,"
	                    + "details.F456 =  loadTableTemp.F456,"
	                    + "details.F457 =  loadTableTemp.F457,"
	                    + "details.F458 =  loadTableTemp.F458,"
	                    + "details.F459 =  loadTableTemp.F459,"
	                    + "details.F460 =  loadTableTemp.F460,"
	                    + "details.F461 =  loadTableTemp.F461,"
	                    + "details.F462 =  loadTableTemp.F462,"
	                    + "details.F463 =  loadTableTemp.F463,"
	                    + "details.F464 =  loadTableTemp.F464,"
	                    + "details.F465 =  loadTableTemp.F465,"
	                    + "details.F466 =  loadTableTemp.F466,"
	                    + "details.F467 =  loadTableTemp.F467,"
	                    + "details.F468 =  loadTableTemp.F468,"
	                    + "details.F469 =  loadTableTemp.F469,"
	                    + "details.F470 =  loadTableTemp.F470,"
	                    + "details.F471 =  loadTableTemp.F471,"
	                    + "details.F472 =  loadTableTemp.F472,"
	                    + "details.F473 =  loadTableTemp.F473,"
	                    + "details.F474 =  loadTableTemp.F474,"
	                    + "details.F475 =  loadTableTemp.F475,"
	                    + "details.F476 =  loadTableTemp.F476,"
	                    + "details.F477 =  loadTableTemp.F477,"
	                    + "details.F478 =  loadTableTemp.F478,"
	                    + "details.F479 =  loadTableTemp.F479,"
	                    + "details.F480 =  loadTableTemp.F480,"
	                    + "details.F481 =  loadTableTemp.F481,"
	                    + "details.F482 =  loadTableTemp.F482,"
	                    + "details.F483 =  loadTableTemp.F483,"
	                    + "details.F484 =  loadTableTemp.F484,"
	                    + "details.F485 =  loadTableTemp.F485,"
	                    + "details.F486 =  loadTableTemp.F486,"
	                    + "details.F487 =  loadTableTemp.F487,"
	                    + "details.F488 =  loadTableTemp.F488,"
	                    + "details.F489 =  loadTableTemp.F489,"
	                    + "details.F490 =  loadTableTemp.F490,"
	                    + "details.F491 =  loadTableTemp.F491,"
	                    + "details.F492 =  loadTableTemp.F492,"
	                    + "details.F493 =  loadTableTemp.F493,"
	                    + "details.F494 =  loadTableTemp.F494,"
	                    + "details.F495 =  loadTableTemp.F495,"
	                    + "details.F496 =  loadTableTemp.F496,"
	                    + "details.F497 =  loadTableTemp.F497,"
	                    + "details.F498 =  loadTableTemp.F498,"
	                    + "details.F499 =  loadTableTemp.F499,"
	                    + "details.F500 =  loadTableTemp.F500");
	            Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
	            query.executeUpdate();
	   }

	@Override
	@Transactional
	public void insertFailedRequiredFields(
			programUploadTypesFormFields putField, Integer programUploadId,
			Integer programUploadRecordId) throws Exception {
		
		String sql = "insert into programUpload_errors (programUploadId, programUploadRecordId, fieldId, dspPos, errorid)"
                + "select " + programUploadId + ", programUploadRecordId, " + putField.getFieldId() +", " + putField.getDspPos()
                + ", 1 from programUploadRecordDetails where programUploadId = :programUploadId "
                + " and (F" + putField.getDspPos()
                + " is  null  or length(trim(F" +  putField.getDspPos() + ")) = 0"
                + " or length(REPLACE(REPLACE(F" +  putField.getDspPos() + ", '\n', ''), '\r', '')) = 0)";
        if (programUploadRecordId != 0) {
            sql = sql + "and programUploadRecordId = :programUploadRecordId";
        }

        Query insertData = sessionFactory.getCurrentSession().createSQLQuery(sql);
		insertData.setParameter("programUploadId",programUploadId);
		if (programUploadRecordId != 0) {
			insertData.setParameter("programUploadRecordId", programUploadRecordId);
		}
		insertData.executeUpdate();
		
	}

	@Override
	@Transactional
	public void updateStatusForErrorRecord(Integer programUploadId,
			Integer statusId, Integer programUploadRecordId)  throws Exception{
		String sql;
		Integer id = programUploadId;
			
		sql = "update programUploadRecords set statusId = :statusId where"
                    + " id in (select distinct programUploadRecordId from programupload_errors where ";
            if (programUploadRecordId == 0) {
                sql = sql + " programUploadId = :id) and statusId not in (:finalStatuses); ";
            } else {
                sql = sql + " programUploadRecordId = :id);";
                id = programUploadRecordId;
            }
        Query updateData = sessionFactory.getCurrentSession().createSQLQuery(sql)
                .setParameter("id", id)
                .setParameter("statusId", statusId);
        if (programUploadRecordId == 0) {
            updateData.setParameterList("finalStatuses", finalStatuses);
        }
        updateData.executeUpdate();		
	}

	@Override
	@Transactional
	public void genericValidation(programUploadTypesFormFields putField,
			Integer validationTypeId, Integer programUploadId, Integer programUploadRecordId) throws Exception {
		String sql = "call insertValidationErrors(:vtType, :dspPos, :fieldId , :programUploadId, :programUploadRecordId)";
		Query insertError = sessionFactory.getCurrentSession().createSQLQuery(sql);
        insertError.setParameter("vtType", putField.getValidationId());
        insertError.setParameter("dspPos", putField.getDspPos());
        insertError.setParameter("fieldId", putField.getFieldId());       
        insertError.setParameter("programUploadId", programUploadId);
        insertError.setParameter("programUploadRecordId", programUploadRecordId);
        insertError.executeUpdate();
   }

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<programUploadRecordValues> getFieldColAndValues(
			Integer programUploadId, programUploadTypesFormFields putField)
			throws Exception {
		String sql = ("select programUploadRecordId as programUploadRecordId, F" + putField.getDspPos()
                + "  as fieldValue, " + putField.getFieldId() + " as fieldId,"
                + putField.getDspPos() + " as dspPos from programUploadRecordDetails "
                + " where F" + putField.getDspPos() + " is not null "
                + " and programUploadRecordId in (select id from programUploadRecords where"
                + " programUploadId = :programUploadId"
                + " and statusId not in ( :finalStatuses ) order by programUploadRecordId); ");

        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql)
                .addScalar("programUploadRecordId", StandardBasicTypes.INTEGER)
                .addScalar("fieldValue", StandardBasicTypes.STRING)
                .addScalar("fieldId", StandardBasicTypes.INTEGER)
                .addScalar("dspPos", StandardBasicTypes.INTEGER)
                .setResultTransformer(Transformers.aliasToBean(programUploadRecordValues.class))
                .setParameter("programUploadId", programUploadId)
                .setParameterList("finalStatuses", finalStatuses);

        
		List<programUploadRecordValues> programUploadRecordValues = query.list();

        return programUploadRecordValues;
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<programUploadRecordValues> getFieldColAndValueByProgramUploadRecordId(
			programUploadTypesFormFields putField, Integer programUploadRecordId)
			throws Exception {
		
		String sql = ("select programUploadRecordId as transactionId, F" + putField.getDspPos()
        + "  as fieldValue, " + putField.getFieldId() + " as fieldId, "+ putField.getDspPos() +" as dspPos"
        + " from programUploadRecordDetails "
        + " where F" + putField.getDspPos()+ " is not null "
        + " and programUploadRecordId = :id");

		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql)
        .addScalar("programUploadRecordId", StandardBasicTypes.INTEGER)
        .addScalar("fieldValue", StandardBasicTypes.STRING)
        .addScalar("fieldId", StandardBasicTypes.INTEGER)
        .setResultTransformer(Transformers.aliasToBean(programUploadRecordValues.class))
        .setParameter("id", programUploadRecordId);

		List<programUploadRecordValues> programUploadRecordValues = query.list();

		return programUploadRecordValues;
	}

	@Override
	@Transactional
	public void updateFieldValue(programUploadRecordValues prv, String newValue)
			throws Exception {
		String sql = "update programUploadRecordDetails set F" + prv.getDspPos() + " = :newValue where"
                + " programUploadRecordId = :programUploadRecordId";
        Query updateData = sessionFactory.getCurrentSession().createSQLQuery(sql);
        updateData.setParameter("programUploadRecordId", prv.getProgramUploadRecordId());
        updateData.setParameter("newValue", newValue);
        updateData.executeUpdate();
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<programUploadTypesFormFields> getFieldDetailByTableAndColumn(
			String tableName, String columnName, Integer programUploadTypeId, Integer useField)
			throws Exception {
		
		String sql = ("select * from put_formfields where "
  				+ " programuploadtypeid = :programUploadTypeId and fieldid in ( "
  				+ " select id from dataElements where savetotablename = :tableName "
  				+ " and saveToTableCol = :columnName)");
		if (useField != null) {
			sql = sql  + (" and useField = :useField");
		}

				Query query = sessionFactory.getCurrentSession().createSQLQuery(sql)
		        .setResultTransformer(Transformers.aliasToBean(programUploadTypesFormFields.class))
		        .setParameter("programUploadTypeId", programUploadTypeId)
		        .setParameter("tableName", tableName)
		        .setParameter("columnName", columnName);
				if (useField != null) {
					query.setParameter("useField", useField);
				}

				List<programUploadTypesFormFields> putFields = query.list();

				return putFields;
	}

	@Override
	@Transactional
	public void insertInvalidPermission (Integer permissionField, Integer hierarchyFieldId,  programUploads programUpload, Integer programHierarchyId) 
	throws Exception {
		String sql = "insert into programupload_errors (programuploadId, programuploadrecordId, errorId, fieldId, dspPos, errorData) "
				+ " select programUploadId, programUploadRecordId, 9, :hierarchyFieldId, :permissionField, F"+ permissionField 
				+ " from programUploadRecordDetails "
				+ " where F"+ permissionField + " not in (select orgHierarchyDetailId from user_authorizedOrgHierarchy  "
				+ " where programId = :programId and programHierarchyId = :programHierarchyId and systemuserid = :systemUserId) and programUploadId = :programUploadId";
        Query insertData = sessionFactory.getCurrentSession().createSQLQuery(sql);
        insertData.setParameter("permissionField", permissionField);
        insertData.setParameter("hierarchyFieldId", hierarchyFieldId);
        insertData.setParameter("programId", programUpload.getProgramId());
        insertData.setParameter("programHierarchyId", programHierarchyId);
        insertData.setParameter("systemUserId", programUpload.getSystemUserId());
        insertData.setParameter("programUploadId", programUpload.getId());      
        insertData.executeUpdate();		
	}

	@Override
	@Transactional
	public void updateProgramHierarchyId(Integer programUploadId, Integer programUploadRecordId, Integer dspPos) throws Exception {
		String sql = "UPDATE programuploadRecords INNER JOIN programUploadRecordDetails ON "
				+ " (programuploadRecords.id = programUploadRecordDetails.programUploadRecordId) "
				+ " SET  programhierarchyid = f" + dspPos + " where statusid not in (:errorStatuses) "
				+ "	and programuploadRecords.programuploadId = :programUploadId";
				if (programUploadRecordId > 0) {
					sql = sql + " and programUploadRecordId = :programUploadRecordId";
				}
		Query updateData = sessionFactory.getCurrentSession().createSQLQuery(sql);
        updateData.setParameter("programUploadId", programUploadId);
        updateData.setParameterList("errorStatuses", errorStatuses);
        
        
        if (programUploadRecordId > 0) {
        	updateData.setParameter("programUploadRecordId", programUploadRecordId);      
        }
        updateData.executeUpdate();		
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<String> getAlgorithmTables(Integer algorithmId, String type)
			throws Exception {
		String sql = ("select distinct saveToTableName from dataElements where id in ("
				+ " select fieldId from put_formFields where id in ("
				+ " select putFormFieldId from put_algorithmFields where algorithmid = :algorithmId)) "
				+ " and saveToTableName like '%"+ type + "%' order by saveToTableName;");
  		
				Query query = sessionFactory.getCurrentSession().createSQLQuery(sql)
		        .setParameter("algorithmId", algorithmId);
		        
				List<String> tableNameList = query.list();

				return tableNameList;
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public boolean hasTable(String tableName, Integer algorithmId)
			throws Exception {
		String sql = ("select distinct saveToTableName from dataElements where id in ("
				+ " select fieldId from put_formFields where id in ("
				+ " select putFormFieldId from put_algorithmFields where algorithmid = :algorithmId)) "
				+ " and saveToTableName = :tableName order by saveToTableName;");
  		
				Query query = sessionFactory.getCurrentSession().createSQLQuery(sql)
		        .setResultTransformer(Transformers.aliasToBean(programUploadTypesFormFields.class))
		        .setParameter("algorithmId", algorithmId)
				.setParameter("tableName", tableName);
				
		        
				List<String> tableNameList = query.list();
				if (tableNameList.size() == 1) {
					return true;
				}
				return false;
	}

	@Override
	@Transactional
	public void insertNewProgramPatients(programUploads programUpload,
			Integer programUploadRecordId) throws Exception {
		String sql = "insert into programPatients (programId, programUploadRecordId, systemUserId)"
				+ "select " + programUpload.getProgramId() +", id, "+ programUpload.getSystemUserId() +" from "
				+ "ProgramUploadRecords where statusId = 10 "
				+ "and programUploadId = :programUploadId and programPatientId is null";
				if (programUploadRecordId > 0 ) {
					sql = sql + " and id = :programUploadRecordId";
				}
		Query updateData = sessionFactory.getCurrentSession().createSQLQuery(sql);
        updateData.setParameter("programUploadId", programUpload.getId());
        
        if (programUploadRecordId > 0) {
        	updateData.setParameter("programUploadRecordId", programUploadRecordId);      
        }
        updateData.executeUpdate();			
	}
	
	@Override
	@Transactional
	public void updateProgramPatientIdInUploadRecord(programUploads programUpload,
			Integer programUploadRecordId) throws Exception {
		String sql = "UPDATE programuploadRecords INNER JOIN programPatients ON "
				+ " (programuploadRecords.id = programPatients.programUploadRecordId) "
				+ " SET  programuploadRecords.programPatientId = programPatients.id where statusid  = 10"
				+ "	and programuploadRecords.programpatientId is null"
				+ " and programuploadid = :programUploadId";
				if (programUploadRecordId > 0 ) {
					sql = sql + " and id = :programUploadRecordId";
				}
		Query updateData = sessionFactory.getCurrentSession().createSQLQuery(sql);
        updateData.setParameter("programUploadId", programUpload.getId());
        
        if (programUploadRecordId > 0) {
        	updateData.setParameter("programUploadRecordId", programUploadRecordId);      
        }
        updateData.executeUpdate();			
	}

	@Override
	@Transactional
	public void changeProgramUploadRecordStatus(programUploads programUpload,
			Integer programUploadRecordId, Integer oldStatusId,
			Integer newStatusId) throws Exception {
		String sql = "UPDATE programuploadRecords set statusId = :newStatusId"
				+ " where statusid  = :oldStatusId"
				+ "	and programuploadid = :programUploadId";
				if (programUploadRecordId > 0 ) {
					sql = sql + " and id = :programUploadRecordId";
				}
		Query updateData = sessionFactory.getCurrentSession().createSQLQuery(sql);
        updateData.setParameter("programUploadId", programUpload.getId());
        updateData.setParameter("newStatusId", newStatusId);
        updateData.setParameter("oldStatusId", oldStatusId);
        
        if (programUploadRecordId > 0) {
        	updateData.setParameter("programUploadRecordId", programUploadRecordId);      
        }
        updateData.executeUpdate();		
		
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public boolean checkMultiValue(programUploads programUpload,
			String tableName) throws Exception {
		String sql = ("select count(*) as insertPerColumn from dataelements, put_formfields"
				+ " where useField = 1 and dataelements.id = put_formfields.fieldId "
				+ " and programuploadTypeid = :programUploadTypeId and saveToTableName = :tableName"
				+ " group by saveToTableName, saveToTableCol "
				+ " order by insertpercolumn desc, saveToTableName, saveToTableCol, multivalue;");
  		
				Query query = sessionFactory.getCurrentSession().createSQLQuery(sql)
		        .setParameter("programUploadTypeId", programUpload.getProgramUploadTypeId())
				.setParameter("tableName", tableName);
				
		        List<BigInteger> insertPerColumnList = query.list();
				if (insertPerColumnList.size() > 0) {
					if (insertPerColumnList.get(0).compareTo(new BigInteger("1")) == 1) {
						return true;
					}
				}
				
				return false;
	}

	@Override
	@Transactional
	public void insertStoragePatients(fieldsAndCols fieldsAndColumns,
			programUploads programUpload, Integer programUploadRecordId)
			throws Exception {
		String sql = "insert into storage_patients (programPatientId, "+ fieldsAndColumns.getStorageFields() +") "
				+ " select programpatientId, " +fieldsAndColumns.getfColumns() + " from programuploadrecords, programuploadRecorddetails "
				+ " where programuploadrecords.id = programUploadRecordDetails.programUploadRecordId "
				+ " and statusId = 10  and programuploadrecords.programUploadId = :programUploadId";
				if (programUploadRecordId > 0 ) {
					sql = sql + " and programuploadrecords.id = :programUploadRecordId";
				}
		Query updateData = sessionFactory.getCurrentSession().createSQLQuery(sql);
        updateData.setParameter("programUploadId", programUpload.getId());
        if (programUploadRecordId > 0) {
        	updateData.setParameter("programUploadRecordId", programUploadRecordId);      
        }
        updateData.executeUpdate();	
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List <fieldsAndCols> selectInsertTableAndColumns(programUploads programUpload, String tableName) {
		String sql = ("select GROUP_CONCAT(saveToTableCol) as storageFields, GROUP_CONCAT((concat('F', dspPos))) as fColumns,"
				+ " group_concat( concat('F',dspPos, ' like ''%,%''') ORDER BY dspPos SEPARATOR ' or ') as checkForDelimSQL, "
				+ " group_concat(concat('strSplit(F',dspPos, ', '','',@valPos)') ORDER BY dspPos SEPARATOR ',') as splitFieldSQL"
				+ " from dataelements, put_formfields where useField = 1 and "
				+ " dataelements.id = put_formfields.fieldId and programuploadTypeid = :programUploadTypeId "
				+ "and saveToTableName = :tableName order by dspPos, saveToTableName, saveToTableCol;");
  		//System.out.println(sql);
				Query query = sessionFactory.getCurrentSession().createSQLQuery(sql)
				.setResultTransformer(Transformers.aliasToBean(fieldsAndCols.class))
		        .setParameter("programUploadTypeId", programUpload.getProgramUploadTypeId())
				.setParameter("tableName", tableName);
				
		        List <fieldsAndCols> insertSQLList = query.list();
				return insertSQLList;
				
	}
	
	@Override
	@Transactional
	public void insertStorageEngagements(fieldsAndCols fieldsAndColumns,
			programUploads programUpload, Integer programUploadRecordId)
			throws Exception {
		String sql = "insert into storage_engagements (programPatientId, programId, programUploadRecordId,"
				+ " "+ fieldsAndColumns.getStorageFields()+") "
				+ " select programpatientId, "+ programUpload.getProgramId()+", programUploadRecordId, "+fieldsAndColumns.getfColumns()
				+" from programuploadrecords, programuploadRecorddetails "
				+ " where programuploadrecords.id = programUploadRecordDetails.programUploadRecordId "
				+ " and statusId = 10  and programuploadrecords.programUploadId = :programUploadId";
				if (programUploadRecordId > 0 ) {
					sql = sql + " and programuploadrecords.id = :programUploadRecordId";
				}
		Query updateData = sessionFactory.getCurrentSession().createSQLQuery(sql);
        updateData.setParameter("programUploadId", programUpload.getId());
        if (programUploadRecordId > 0) {
        	updateData.setParameter("programUploadRecordId", programUploadRecordId);      
        }
        updateData.executeUpdate();	
	}

	@Override
	@Transactional
	public void updateEngagementIdForProgramUploadRecord(
			programUploads programUpload, Integer programUploadRecordId)
			throws Exception {
		
		String sql = "UPDATE programUploadRecords INNER JOIN storage_engagements ON "
				+ "(storage_engagements.programUploadRecordId = programUploadRecords.id)"
				+ " SET programUploadRecords.storage_engagementId = storage_engagements.id "
				+ " where statusId = 10 and programUploadId = :programUploadId";
				if (programUploadRecordId > 0 ) {
					sql = sql + " and programuploadrecords.id = :programUploadRecordId";
				}
		Query updateData = sessionFactory.getCurrentSession().createSQLQuery(sql);
        updateData.setParameter("programUploadId", programUpload.getId());
        if (programUploadRecordId > 0) {
        	updateData.setParameter("programUploadRecordId", programUploadRecordId);      
        }
        updateData.executeUpdate();
		
	}

	@Override
	@Transactional
	public void blanksToNull(Integer fColumn,
			programUploads programUpload, Integer programUploadRecordId)
			throws Exception {
		String sql = "update programUploadRecordDetails set F" + fColumn + " = null where length(F"
                + fColumn + ") = 0 "
                + "and programUploadId = :programUploadId ";
			if (programUploadRecordId > 0 ) {
				sql = sql + "and id = :programUploadRecordId";
			}

        Query updateData = sessionFactory.getCurrentSession().createSQLQuery(sql);
        updateData.setParameter("programUploadId", programUpload.getId());
        if (programUploadRecordId > 0 ) {
        	updateData.setParameter("programUploadRecordId", programUploadRecordId);
		}
        
        updateData.executeUpdate();
        
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public List <Integer> getFColumnsForProgramUploadType(
			programUploads programUpload) throws Exception {
		String sql = ("select distinct dspPos as fColumns "
				+ " from dataelements, put_formfields where useField = 1 and "
				+ " dataelements.id = put_formfields.fieldId and programuploadTypeid = :programUploadTypeId "
				+ " order by dspPos;");
  		
				Query query = sessionFactory.getCurrentSession().createSQLQuery(sql)
				.setParameter("programUploadTypeId", programUpload.getProgramUploadTypeId());
				
		        List <Integer> columnList = query.list();
		        
				return columnList;
	}

	@Override
	@Transactional
    public void updateFormFieldStatus(Integer programUploadTypeId, String status) throws Exception {
		 Query updateFields = sessionFactory.getCurrentSession().createQuery("update programUploadTypesFormFields set formFieldStatus =  :status where programUploadTypeId = :importTypeId");
			 updateFields.setParameter("importTypeId", programUploadTypeId);
			 updateFields.setParameter("status", status);
			 updateFields.executeUpdate();
	}

	@Override
	@Transactional
    public void deleteFormFieldsFromAlgorithms(Integer programUploadTypeId)
			throws Exception {
		String sql = "delete from put_algorithmFields  "
				+ " where putFormFieldId in (select id from put_formFields where programuploadtypeid = :programUploadTypeId "
				+ " and formfieldstatus = 'D') ";
			
        Query deleteData = sessionFactory.getCurrentSession().createSQLQuery(sql);
        deleteData.setParameter("programUploadTypeId", programUploadTypeId);
       
        
        deleteData.executeUpdate();
		
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<String> getNonMainTablesForProgramUploadType(
			Integer programUploadTypeId) throws Exception {
		String sql = ("select distinct (saveToTableName) as saveToTableName "
				+ " from dataelements, put_formfields where "
				+ " dataelements.id = put_formfields.fieldId "
				+ " and programuploadTypeid = :programUploadTypeId and saveToTableName "
				+ " not in ('storage_patients','storage_engagements')");
  		
				Query query = sessionFactory.getCurrentSession().createSQLQuery(sql)
				.setParameter("programUploadTypeId",programUploadTypeId);
				
		        List <String> tableList = query.list();
		        
				return tableList;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public boolean usesMultiValue(Integer programUploadTypeId, String tableName)
			throws Exception {
		String sql = ("select saveToTableName from dataelements, put_formfields "
				+ " where dataelements.id = put_formfields.fieldId "
				+ " and programuploadTypeid = :programUploadTypeId and useField = 1 "
				+ "and saveToTableName = :tableName "
				+ "and multivalue = 1;");
  		
				Query query = sessionFactory.getCurrentSession().createSQLQuery(sql)
				.setParameter("programUploadTypeId",programUploadTypeId)
				.setParameter("tableName",tableName);
				
		        List <String> multiValueList = query.list();
		        if (multiValueList.size() == 0) {
		        	return false;
		        } 
		        
		        return true;
	}
	

	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public boolean multiRow(Integer programUploadTypeId, String tableName)
			throws Exception {
		String sql = ("select count(*) as insertPerColumn from dataelements, put_formfields"
				+ " where useField = true and dataelements.id = put_formfields.fieldId "
				+ " and programuploadTypeid = :programUploadTypeId and usefield = 1 "
				+ " and saveToTableName = :tableName group by  saveToTableCol order by insertpercolumn desc;");
  		
				Query query = sessionFactory.getCurrentSession().createSQLQuery(sql)
				.setParameter("programUploadTypeId",programUploadTypeId)
				.setParameter("tableName",tableName);
				
		        List<BigInteger> multiRow = query.list();
		        if (multiRow.size() > 0) {
					if (multiRow.get(0).compareTo(new BigInteger("1")) == 1) {
						return true;
					}
				}
		        return false;
	}

	@Override
	@Transactional
	public void insertSingleStorageTable(fieldsAndCols fieldsAndColumns,
			programUploads programUpload, String tableName, Integer programUploadRecordId, List<Integer> skipRecordIds) throws Exception {
		//System.out.println(tableName);
		String keyField = "storage_engagementId";
		if (tableName.toLowerCase().contains("patient")) {
			keyField = "programPatientId";
		}
		String sql = "insert into " + tableName + " ("+ keyField +", "
				+ " "+ fieldsAndColumns.getStorageFields()+") "
				+ " select "+ keyField +", "+fieldsAndColumns.getfColumns()
				+" from programuploadrecords, programuploadRecorddetails "
				+ " where programuploadrecords.id = programUploadRecordDetails.programUploadRecordId "
				+ " and statusId = 10  and programuploadrecords.programUploadId = :programUploadId ";
				if (skipRecordIds.size() > 0) {
					sql = sql + "and programuploadrecords.id not in (:blankRecordIds)";
				}
				if (programUploadRecordId > 0 ) {
					sql = sql + " and programuploadrecords.id = :programUploadRecordId";
				}
		Query updateData = sessionFactory.getCurrentSession().createSQLQuery(sql);
        updateData.setParameter("programUploadId", programUpload.getId());
        if (programUploadRecordId > 0) {
        	updateData.setParameter("programUploadRecordId", programUploadRecordId);      
        }
        if (skipRecordIds.size() > 0) {
        	updateData.setParameterList("blankRecordIds", skipRecordIds);
		}
        
        updateData.executeUpdate();		
	}
	
	@Override
    @Transactional
    @SuppressWarnings("unchecked")
    public List<Integer> getBlankRecordIds(fieldsAndCols fieldsAndColumns, programUploads programUpload, 
    		Integer programUploadRecordId) 
    throws Exception{

        String sql = ("select programUploadRecordId from "
                + " programUploadRecordDetails where (length(CONCAT_WS(''," + fieldsAndColumns.getfColumns()
                + ")) = 0 or length(CONCAT_WS(''," + fieldsAndColumns.getfColumns()
                + ")) is null) and programUploadRecordId in (select id from programUploadRecords where statusId = 10 "
                + " and programUploadId = :programUploadId)");
        if (programUploadRecordId > 0){
        	sql = sql + (" and programUploadRecordId = :programUploadRecordId");
        }
       
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("programUploadId", programUpload.getId());
        if (programUploadRecordId > 0){
        	query.setParameter("programUploadRecordId", programUploadRecordId);
        }
        List<Integer> recordIds = query.list();

        return recordIds;
    }

	@Override
    @Transactional
    @SuppressWarnings("unchecked")
	public List<Integer> getListRecordIds(fieldsAndCols fieldsAndColumns,
			programUploads programUpload, Integer programUploadRecordId)
			throws Exception {
		String sql = ("select programUploadRecordId from "
                + " programUploadRecordDetails where ("+ fieldsAndColumns.getCheckForDelimSQL() +") and programUploadRecordId in (select id from programUploadRecords where statusId = 10 "
                + " and programUploadId = :programUploadId)");
        if (programUploadRecordId > 0){
        	sql = sql + (" and programUploadRecordId = :programUploadRecordId");
        }
       
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("programUploadId", programUpload.getId());
        if (programUploadRecordId > 0){
        	query.setParameter("programUploadRecordId", programUploadRecordId);
        }
        List<Integer> recordIds = query.list();

        return recordIds;
	}

	@Override
	@Transactional
	public Integer countSubString(String col, Integer programUploadRecordId)
			throws Exception {
		String sql
	        = "(SELECT ROUND(((LENGTH(" + col
	        + ") - LENGTH(REPLACE(LCASE(" + col
	        + "), ',', '')))/LENGTH(',')),0) as stringCount from programUploadRecordDetails "
	        + " where programUploadRecordId = :id);";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql)
				.addScalar("stringCount", StandardBasicTypes.INTEGER);
		query.setParameter("id", programUploadRecordId);
		Integer stringCount = (Integer) query.list().get(0);
		
		return stringCount;
	}

	@Override
	@Transactional
	public void insertMultiValToMessageTables(fieldsAndCols fieldsAndColumns,
			Integer subStringCounter, Integer programUploadRecordId, String tableName, programUploads programUpload)
			throws Exception {
		String replaceSplitField = fieldsAndColumns.getSplitFieldSQL().replaceAll("@valPos", subStringCounter.toString());
        String keyField = "storage_engagementId";
		if (tableName.toLowerCase().contains("patient")) {
        	keyField = "programPatientId";
        }
		String sql = "insert into " + tableName
                + " ("+keyField+", " + fieldsAndColumns.getStorageFields()
                + ") select "+keyField+", "
                + replaceSplitField
                + " from programUploadRecordDetails, programUploadRecords where "
                + " programUploadRecordDetails.programUploadRecordId = programUploadRecords.id "
                + " and  programUploadRecords.programUploadId = :programUploadId and statusId in (10) "
                + " and programUploadRecords.id = :programUploadRecordId";
        Query insertData = sessionFactory.getCurrentSession().createSQLQuery(sql)
                .setParameter("programUploadId", programUpload.getId())
                .setParameter("programUploadRecordId", programUploadRecordId);
        insertData.executeUpdate();
		
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public boolean checkMultiRowSetUp(Integer programUploadTypeId,
			String tableName) throws Exception {
		String sql = ("select distinct (saveToTableCol) as cols from dataelements, put_formfields where useField = 1 and "
				+ "	dataelements.id = put_formfields.fieldId and programuploadTypeid = :programUploadTypeId"
				+ " and saveToTableName = :tableName");
        
       
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("programUploadTypeId", programUploadTypeId);
        query.setParameter("tableName", tableName);
        
        List<String> columnList = query.list();
        
        if (columnList.size() == 1) {
        	return true;
        }

        return false;
	}

}
