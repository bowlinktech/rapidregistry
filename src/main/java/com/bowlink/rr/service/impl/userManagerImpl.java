/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bowlink.rr.service.impl;

import com.bowlink.rr.dao.userDAO;
import com.bowlink.rr.model.User;
import com.bowlink.rr.service.userManager;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author chadmccue
 */
@Service
public class userManagerImpl implements userManager {
    
    @Autowired
    private userDAO userDAO;

    @Override
    @Transactional
    public Integer createUser(User user) {
        Integer lastId = null;
        lastId = (Integer) userDAO.createUser(user);
        return lastId;
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    @Override
    @Transactional
    public User getUserById(int userId) {
        return userDAO.getUserById(userId);
    }

    @Override
    @Transactional
    public User getUserByUserName(String username) {
        return userDAO.getUserByUserName(username);
    }

    @Override
    @Transactional
    public Long findTotalLogins(int userId) {
        return userDAO.findTotalLogins(userId);
    }

    @Override
    @Transactional
    public void setLastLogin(String username) {
        userDAO.setLastLogin(username);
    }

    @Override
    @Transactional
    public Integer getUserByIdentifier(String identifier) {
        return userDAO.getUserByIdentifier(identifier);
    }

    @Override
    @Transactional
    public User getUserByResetCode(String resetCode) {
        return userDAO.getUserByResetCode(resetCode);
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }
    
    @Override
    @Transactional
    public List<User> getProgramAdmins() {
        return userDAO.getProgramAdmins();
    }
    
    @Override
    @Transactional
    public List<User> getUsersByRoleId(Integer roleId) {
        return userDAO.getUsersByRoleId(roleId);
    }
    
    @Override
    @Transactional
    public void deleteUser(Integer userId) {
        userDAO.deleteUser(userId);
    }

}
