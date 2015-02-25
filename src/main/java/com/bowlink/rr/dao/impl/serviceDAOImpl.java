/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.dao.impl;

import com.bowlink.rr.dao.serviceDAO;
import com.bowlink.rr.model.programServiceCategories;
import com.bowlink.rr.model.programServiceCategoryAssoc;
import com.bowlink.rr.model.programServices;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author chadmccue
 */
@Repository
public class serviceDAOImpl implements serviceDAO {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    /**
     * The 'getProgramServiceCategories' function will return a list of service categories for the passed in programId.
     * 
     * @param programId The selected program id.
     * @return
     * @throws Exception 
     */
    @Override
    public List<programServiceCategories> getProgramServiceCategories(Integer programId) throws Exception {
        Query query = sessionFactory.getCurrentSession().createQuery("from programServiceCategories where programId = :programId order by categoryName asc");
        query.setParameter("programId", programId);

        List<programServiceCategories> categoryList = query.list();
        
        return categoryList;
    }
    
    /**
     * The 'getAvailableProgramServiceCategories' function will return a list of active service categories for the passed in programId.
     * 
     * @param programId The selected program id.
     * @return
     * @throws Exception 
     */
    @Override
    public List<programServiceCategories> getAvailableProgramServiceCategories(Integer programId, Integer serviceId) throws Exception {
        
        String sql = "select id, programId, status, categoryName, dateCreated from program_serviceCategories where programId = " + programId + " and status = 1 and id not in (select serviceCategoryId from rel_programServiceCategories where serviceId = "+ serviceId + ") order by categoryName asc";
        
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql) 
            .setResultTransformer(Transformers.aliasToBean(programServiceCategories.class)
        );
        
        List<programServiceCategories> categoryList = query.list();
        
        return categoryList;
    }
    
    /**
     * The 'getProgramServiceCategoryDetails' function will return a details for the selected service category.
     * 
     * @param categoryId The selected category.
     * @return
     * @throws Exception 
     */
    @Override
    public programServiceCategories getProgramServiceCategoryDetails(Integer categoryId) throws Exception {
        Query query = sessionFactory.getCurrentSession().createQuery("from programServiceCategories where id = :categoryId");
        query.setParameter("categoryId", categoryId);

        return (programServiceCategories) query.uniqueResult();
    }
    
    /**
     * The '/saveProgramServiceCategory' function will save the service category
     * programs
     * 
     * @param programServiceCategories   The object holding the programServiceCategories
     */
    @Override
    public void saveProgramServiceCategory(programServiceCategories category) throws Exception {
       sessionFactory.getCurrentSession().saveOrUpdate(category);
    }
    
    
    /**
     * The 'getProgramServiceCategories' function will return a list of service categories for the passed in programId.
     * 
     * @param programId The selected program id.
     * @return
     * @throws Exception 
     */
    @Override
    public List<programServices> getProgramServices(Integer programId) throws Exception {
        Query query = sessionFactory.getCurrentSession().createQuery("from programServices where programId = :programId order by serviceName asc");
        query.setParameter("programId", programId);

        List<programServices> services = query.list();
        
        return services;
    }
    
    /**
     * The 'getProgramServiceDetails' function will return a details for the selected service.
     * 
     * @param serviceId The selected category service.
     * @return
     * @throws Exception 
     */
    @Override
    public programServices getProgramServiceDetails(Integer serviceId) throws Exception {
        Query query = sessionFactory.getCurrentSession().createQuery("from programServices where id = :serviceId");
        query.setParameter("serviceId", serviceId);

        return (programServices) query.uniqueResult();
    }
    
    /**
     * The 'saveNewProgramService" function will create the new program service.
     *
     * @Table	program_services
     *
     * @param	service	This will hold the programServices object from the form
     *
     * @return the function will return the id of the new service
     *
     */
    @Override
    public Integer saveNewProgramService(programServices service) {
        Integer lastId = null;

        lastId = (Integer) sessionFactory.getCurrentSession().save(service);

        return lastId;
    }
    
    /**
     * The '/saveProgramService' function will save the service 
     * programs
     * 
     * @param programServices   The object holding the programServices
     */
    @Override
    public void saveProgramService(programServices service) throws Exception {
       sessionFactory.getCurrentSession().save(service);
    }
    
    /**
     * The 'getProgramServiceCategoryAssoc' function will return the list of categories the service is associated to.
     * 
     * @param serviceId The selected service
     * @return
     * @throws Exception 
     */
    @Override
    public List<programServiceCategoryAssoc> getProgramServiceCategoryAssoc(Integer serviceId) throws Exception {
       Query query = sessionFactory.getCurrentSession().createQuery("from programServiceCategoryAssoc where serviceId = :serviceId");
        query.setParameter("serviceId", serviceId);

        List<programServiceCategoryAssoc> categories = query.list();
        
        return categories;
    }
    
    /**
     * The 'removeExistingCategories' function will remove the assigned categories for the passed in service.
     * 
     * @param serviceId The id of the selected service.
     * @throws Exception 
     */
    @Override
    public void removeExistingCategories(Integer serviceId) throws Exception {
        //Remove the user program module associations
        Query removeCategories = sessionFactory.getCurrentSession().createQuery("delete from programServiceCategoryAssoc where serviceId = :serviceId");
        removeCategories.setParameter("serviceId", serviceId);
        removeCategories.executeUpdate();
    }
    
    /**
     * The 'saveAssignedCategories' function will save the selected category object.
     * 
     * @param assignCategory The object containing the assigned service categories
     * @throws Exception 
     */
    @Override
    public void saveAssignedCategories(programServiceCategoryAssoc assignCategory) throws Exception {
        sessionFactory.getCurrentSession().save(assignCategory);
    }
    
    /**
     * The 'removeAssignedCategory' function will remove the selected category from the service.
     * 
     * @param serviceId     The id of the selected service
     * @param categoryId    The id of the selected category
     * @throws Exception 
     */
    @Override
    public void removeAssignedCategory(Integer serviceId, Integer categoryId) throws Exception {
        //Remove the user program module associations
        Query removeCategory = sessionFactory.getCurrentSession().createQuery("delete from programServiceCategoryAssoc where serviceId = :serviceId and serviceCategoryId = :categoryId");
        removeCategory.setParameter("serviceId", serviceId);
        removeCategory.setParameter("categoryId", categoryId);
        removeCategory.executeUpdate();
    }
    
}
