/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bowlink.rr.dao;

import com.bowlink.rr.model.User;
import com.bowlink.rr.model.Log_userSurveyActivity;
import com.bowlink.rr.model.userPrograms;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 *
 * @author chadmccue
 */
@Repository
public interface userDAO {
    
    Integer createUser(User user);

    void updateUser(User user);

    User getUserById(int userId);

    User getUserByEmail(String emailAddress);

    Long findTotalLogins(int userId);

    void setLastLogin(String userName);

    User getUserByResetCode(String resetCode);

    List<User> getAllUsers();
    
    Integer getUserByIdentifier(String identifier);
    
    List<User> getProgramAdmins();
    
    List<User> getUsersByRoleId(Integer roleId);
    
    void deleteUser(Integer userId);
     
    List<User> getUsersByProgramId(Integer programId);
    
    @SuppressWarnings("rawtypes")
    List getUserTypes();
    
    List<User> searchStaffMembers(Integer programId, String firstName, String lastName, Integer status, Integer typeId) throws Exception;
    
    List<userPrograms> getUserPrograms(Integer userId) throws Exception;
    
    void removeProgram(Integer userId, Integer programId) throws Exception;
    
    List<String> getUserRoles (User user) throws Exception;
    
    void insertUserLog (Log_userSurveyActivity ual) throws Exception;
    
    List<User> getAllUsersByProgram(Integer programId) throws Exception;
    
    User getUserByUsername(String username, Integer programId) throws Exception;
    
    User getUserByUserNameOnly(String userName);
    
    public User checkDuplicateUsername(String username, Integer programId, Integer userId) throws Exception;
}
