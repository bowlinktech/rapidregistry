/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.service.impl;

import com.bowlink.rr.dao.serviceDAO;
import com.bowlink.rr.model.programServiceCategories;
import com.bowlink.rr.model.programServiceCategoryAssoc;
import com.bowlink.rr.model.programServices;
import com.bowlink.rr.service.serviceManager;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author chadmccue
 */
@Service
public class serviceManagerImpl implements serviceManager {
    
    @Autowired
    serviceDAO serviceDAO;
    
    @Override
    @Transactional
    public List<programServiceCategories> getProgramServiceCategories(Integer programId) throws Exception {
        return serviceDAO.getProgramServiceCategories(programId);
    }
    
    @Override
    @Transactional
    public List<programServiceCategories> getAvailableProgramServiceCategories(Integer programId, Integer serviceId) throws Exception {
        return serviceDAO.getAvailableProgramServiceCategories(programId, serviceId);
    }
    
    @Override
    @Transactional
    public programServiceCategories getProgramServiceCategoryDetails(Integer categoryId) throws Exception {
        return serviceDAO.getProgramServiceCategoryDetails(categoryId);
    }
    
    @Override
    @Transactional
    public void saveProgramServiceCategory(programServiceCategories category) throws Exception {
        serviceDAO.saveProgramServiceCategory(category);
    }
    
    @Override
    @Transactional
    public List<programServices> getProgramServices(Integer programId) throws Exception {
        return serviceDAO.getProgramServices(programId);
    }
    
    @Override
    @Transactional
    public programServices getProgramServiceDetails(Integer serviceId) throws Exception {
        return serviceDAO.getProgramServiceDetails(serviceId);
    }
    
    @Override
    @Transactional
    public Integer saveNewProgramService(programServices service) throws Exception {
        return serviceDAO.saveNewProgramService(service);
    }
    
    @Override
    @Transactional
    public void saveProgramService(programServices service) throws Exception {
        serviceDAO.saveProgramService(service);
    }
    
    @Override
    @Transactional
    public List<programServiceCategoryAssoc> getProgramServiceCategoryAssoc(Integer serviceId) throws Exception {
        return serviceDAO.getProgramServiceCategoryAssoc(serviceId);
    }
    
    @Override
    @Transactional
    public void removeExistingCategories(Integer serviceId) throws Exception {
        serviceDAO.removeExistingCategories(serviceId);
    }
    
    @Override
    @Transactional
    public void saveAssignedCategories(programServiceCategoryAssoc assignCategory) throws Exception {
        serviceDAO.saveAssignedCategories(assignCategory);
    }
    
    @Override
    @Transactional
    public void removeAssignedCategory(Integer serviceId, Integer categoryId) throws Exception {
        serviceDAO.removeAssignedCategory(serviceId, categoryId);
    }
}
