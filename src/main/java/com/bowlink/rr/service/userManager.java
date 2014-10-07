/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.service;

import com.bowlink.rr.model.User;
import java.util.List;

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

}
