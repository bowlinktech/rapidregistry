/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bowlink.rr.service.impl;

import com.bowlink.rr.dao.userDAO;
import com.bowlink.rr.model.User;
import com.bowlink.rr.model.programAdmin;
import com.bowlink.rr.model.userPrograms;
import com.bowlink.rr.service.userManager;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.List;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

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
    public User getUserByEmail(String emailAddress) {
        return userDAO.getUserByEmail(emailAddress);
    }

    @Override
    @Transactional
    public Long findTotalLogins(int userId) {
        return userDAO.findTotalLogins(userId);
    }

    @Override
    @Transactional
    public void setLastLogin(String emailAddress) {
        userDAO.setLastLogin(emailAddress);
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
    
    @Override
    @Transactional
    public List<User> getUsersByProgramId(Integer programId) {
        return userDAO.getUsersByProgramId(programId);
    }
    
    @SuppressWarnings("rawtypes")
    @Override
    @Transactional
    public List getUserTypes() {
        return userDAO.getUserTypes();
    }
    
    @Override
    @Transactional
    public List<User> searchStaffMembers(Integer programId, String firstName, String lastName, Integer status, Integer typeId) throws Exception {
        return userDAO.searchStaffMembers(programId, firstName, lastName, status, typeId);
    }
    
    @Override
    @Transactional
    public List<userPrograms> getUserPrograms(Integer userId) throws Exception {
        return userDAO.getUserPrograms(userId);
    }
    
    @Override
    @Transactional
    public void removeProgram(Integer userId, Integer programId) throws Exception {
        userDAO.removeProgram(userId, programId);
    }

    @Override
    public User encryptPW(User user) throws Exception {
        //first we get salt
        byte[] salt = generateSalt();
        user.setRandomSalt(salt);

        byte[] encPW = getEncryptedPassword(user.getPassword(), salt);
        user.setEncryptedPw(encPW);
        // then we encrypt and send back pw
        return user;
    }
    
    @Override
    public byte[] generateSalt() throws NoSuchAlgorithmException {
        // VERY important to use SecureRandom instead of just Random
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");

        // Generate a 8 byte (64 bit) salt as recommended by RSA PKCS5
        byte[] salt = new byte[8];
        random.nextBytes(salt);

        return salt;
    }
    
    @Override
    public byte[] getEncryptedPassword(String password, byte[] salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
    
        // PBKDF2 with SHA-1 as the hashing algorithm. Note that the NIST
        // specifically names SHA-1 as an acceptable hashing algorithm for PBKDF2
        String algorithm = "PBKDF2WithHmacSHA1";
       
        // SHA-1 generates 160 bit hashes, so that's what makes sense here
        int derivedKeyLength = 160;
	
        // Pick an iteration count that works for you. The NIST recommends at
        // least 1,000 iterations:
        // http://csrc.nist.gov/publications/nistpubs/800-132/nist-sp800-132.pdf
        // iOS 4.x reportedly uses 10,000:
        // http://blog.crackpassword.com/2010/09/smartphone-forensics-cracking-blackberry-backup-passwords/
        int iterations = 20000;
        
        // byte[] b = string.getBytes(Charset.forName("UTF-8"));
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, derivedKeyLength);

        SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);

        return f.generateSecret(spec).getEncoded();
    }
    
    @Override
    public boolean authenticate(String attemptedPassword, byte[] encryptedPassword, byte[] salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
	
        // Encrypt the clear-text password using the same salt that was used to
        // encrypt the original password
        byte[] encryptedAttemptedPassword = getEncryptedPassword(attemptedPassword, salt);
        
	// Authentication succeeds if encrypted password that the user entered
        // is equal to the stored hash
        return Arrays.equals(encryptedPassword, encryptedAttemptedPassword);
    }
    
    @Override
    public List<String> getUserRoles(User user) throws Exception {
        return userDAO.getUserRoles(user);
    }
}
