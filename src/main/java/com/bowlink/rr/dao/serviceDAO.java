/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.dao;

import com.bowlink.rr.model.programServiceCategories;
import com.bowlink.rr.model.programServiceCategoryAssoc;
import com.bowlink.rr.model.programServices;
import java.util.List;

/**
 *
 * @author chadmccue
 */
public interface serviceDAO {
    
    List<programServiceCategories> getProgramServiceCategories(Integer programId) throws Exception;
    
    List<programServiceCategories> getAvailableProgramServiceCategories(Integer programId, Integer serviceId) throws Exception;
    
    programServiceCategories getProgramServiceCategoryDetails(Integer categoryId) throws Exception;
    
    void saveProgramServiceCategory(programServiceCategories category) throws Exception;
    
    List<programServices> getProgramServices(Integer programId) throws Exception;
    
    programServices getProgramServiceDetails(Integer serviceId) throws Exception;
    
    Integer saveNewProgramService(programServices service) throws Exception;
    
    void saveProgramService(programServices service) throws Exception;
    
    List<programServiceCategoryAssoc> getProgramServiceCategoryAssoc(Integer serviceId) throws Exception;
    
    void removeExistingCategories(Integer serviceId) throws Exception;
    
    void saveAssignedCategories(programServiceCategoryAssoc assignCategory) throws Exception;
    
    void removeAssignedCategory(Integer serviceId, Integer categoryId) throws Exception;
    
}
