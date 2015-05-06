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
import com.bowlink.rr.model.fileTypes;
import com.bowlink.rr.model.programUploadTypes;
import com.bowlink.rr.model.programUploadTypesFormFields;
import com.bowlink.rr.model.programUpload_Errors;
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
    @SuppressWarnings("unchecked")
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
                + " batchId int(11),  loadTableId varchar(45) ,  id int(11) NOT NULL AUTO_INCREMENT,  PRIMARY KEY (id)) ENGINE=InnoDB AUTO_INCREMENT=1038 DEFAULT CHARSET=latin1;");
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

}
