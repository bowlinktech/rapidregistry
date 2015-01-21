/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.service;

import com.bowlink.rr.model.User;
import com.bowlink.rr.model.userActivityLog;
import com.bowlink.rr.model.userPrograms;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.List;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author chadmccue
 */
public interface userManager {

    Integer createUser(User user);

    void updateUser(User user);

    User getUserById(int userId);

    User getUserByEmail(String emailAddress);

    Long findTotalLogins(int userId);

    void setLastLogin(String emailAddress);

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
    
    User encryptPW(User user) throws Exception;
    
    byte[] generateSalt() throws NoSuchAlgorithmException;
    
    byte[] getEncryptedPassword(String password, byte[] salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException;
    
    boolean authenticate(String attemptedPassword, byte[] encryptedPassword, byte[] salt)
 		   throws NoSuchAlgorithmException, InvalidKeySpecException;

    List<String> getUserRoles (User user) throws Exception;
    
    void insertUserLog (userActivityLog ual);
}
