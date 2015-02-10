/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bowlink.rr.dao.impl;

import com.bowlink.rr.dao.userDAO;
import com.bowlink.rr.model.User;
import com.bowlink.rr.model.program;
import com.bowlink.rr.model.programAdmin;
import com.bowlink.rr.model.Log_userSurveyActivity;
import com.bowlink.rr.model.userLogin;
import com.bowlink.rr.model.userPrograms;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
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
public class userDAOImpl implements userDAO {
    
    @Autowired
    private SessionFactory sessionFactory;

    /**
     * The 'createUser" function will create the new system user and save the user.
     *
     * @Table	users
     *
     * @param	user	This will hold the user object from the form
     *
     * @return the function will return the id of the new user
     *
     */
    @Override
    public Integer createUser(User user) {
        Integer lastId = null;

        lastId = (Integer) sessionFactory.getCurrentSession().save(user);

        return lastId;
    }

    /**
     * The 'updateUser' function will update the selected user with the changes entered into the form.
     *
     * @param	user	This will hold the user object from the user form
     *
     * @return the function does not return anything
     */
    @Override
    public void updateUser(User user) {
        sessionFactory.getCurrentSession().update(user);
    }

    /**
     * The 'getUserById' function will return a single user object based on the userId passed in.
     *
     * @param	userId	This will be used to find the specifc user
     *
     * @return	The function will return a user object
     */
    @Override
    public User getUserById(int userId) {
        return (User) sessionFactory.getCurrentSession().get(User.class, userId);
    }

    /**
     * The 'emailAddress' function will return a single user object based on a email address passed in.
     *
     * @param	emailAddress	This will used to query the email field of the users table
     *
     * @return	The function will return a user object
     */
    @Override
    public User getUserByEmail(String emailAddress) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
        criteria.add(Restrictions.eq("email", emailAddress));
        return (User) criteria.uniqueResult();
    }

    /**
     * The 'findTotalLogins' function will return the total number of logins for a user.
     *
     * @param	userId	This will be the userid used to find logins
     *
     * @return	The function will return a number of logins
     */
    @Override
    public Long findTotalLogins(int userId) {

        Query query = sessionFactory.getCurrentSession().createQuery("select count(id) as totalLogins from userLogin where systemUserId = :userId");
        query.setParameter("userId", userId);

        Long totalLogins = (Long) query.uniqueResult();

        return totalLogins;

    }

    /**
     * The 'setLastLogin' function will be called upon a successful login. It will save the entry into the rel_userLogins table.
     *
     * @param username	This will be the username of the person logging in.
     *
     */
    @Override
    public void setLastLogin(String emailAddress) {
        Query q1 = sessionFactory.getCurrentSession().createQuery("insert into userLogin (systemUserId)" + "select id from User where email = :emailAddress");
        q1.setParameter("emailAddress", emailAddress);
        q1.executeUpdate();
        
        /* Update the users last logged in value */
        Query q2 = sessionFactory.getCurrentSession().createQuery("from userLogin where systemUserId = (select id from User where email = :emailAddress) order by id desc");
        q2.setParameter("emailAddress", emailAddress);
        
        userLogin lastLogin = (userLogin) q2.list().get(0);
        
        User user = getUserByEmail(emailAddress);
        user.setLastloggedIn(lastLogin.getDateCreated());
        
        updateUser(user);
    }


    /**
     * The 'getUserByIdentifier' function will try to location a user based on the identifier passed in.
     *
     * @param identifier The value that will be used to find a user.
     *
     * @return The function will return a user object
     */
    @Override
    public Integer getUserByIdentifier(String identifier) {

        String sql = ("select id from users where lower(email) = '" + identifier.toLowerCase() + "' or lower(concat(concat(firstName,' '),lastName)) = '" + identifier.toLowerCase() + "'");
        
        Query findUser = sessionFactory.getCurrentSession().createSQLQuery(sql);

        if (findUser.list().size() > 1) {
            return null;
        } else {
            if (findUser.uniqueResult() == null) {
                return null;
            } else {
                return (Integer) findUser.uniqueResult();
            }
        }
    }

    /**
     * The 'getUserByResetCode' function will try to location a user based on the a reset code
     *
     * @param resetCode The value that will be used to find a user.
     *
     * @return The function will return a user object
     */
    @Override
    public User getUserByResetCode(String resetCode) {

        Query query = sessionFactory.getCurrentSession().createQuery("from User where resetCode = :resetCode");
        query.setParameter("resetCode", resetCode);

        if (query.list().size() > 1) {
            return null;
        } else {
            if (query.uniqueResult() == null) {
                return null;
            } else {
                return (User) query.uniqueResult();
            }
        }
    }

    /**
     * The 'getAllUsers' function will return a list of all users in the system.
     * 
     * @return This function will return a list of User objects.
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        Query query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.list();
    }

    /**
     * The 'getProgramAdmins' function will return a list of users with a role of program admin.
     * 
     * @return This function will return a list of User objects that have a role of program admin
     */
    @Override
    public List<User> getProgramAdmins() {
        
        Query query = sessionFactory.getCurrentSession().createQuery("from User where roleId = 2 order by lastName asc");

        return query.list();
    }
    
    /**
     * The 'getUsersByRoleId' function will return a a list of users based on the roleId passed in.
     *
     * @param	roleId	The id of the role to find users
     *
     * @return	The function will return a user object
     */
    @Override
    public List<User> getUsersByRoleId(Integer roleId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
        criteria.add(Restrictions.eq("roleId", roleId));
        criteria.addOrder(Order.desc("lastName"));
        return criteria.list();
    }
    
    /**
     * The 'deleteUser' function will remove the passed in user.
     * 
     * @param userId    The id of the user to be removed.
     */
    @Override
    public void deleteUser(Integer userId) {
        Query removeuserLogins = sessionFactory.getCurrentSession().createQuery("delete from userLogin where systemUserId = :userId");
        removeuserLogins.setParameter("userId", userId);
        removeuserLogins.executeUpdate();
        
        Query removeAdminProgram = sessionFactory.getCurrentSession().createQuery("delete from programAdmin where systemUserId = :userId");
        removeAdminProgram.setParameter("userId", userId);
        removeAdminProgram.executeUpdate();
        
        Query removeUser = sessionFactory.getCurrentSession().createQuery("delete from User where id = :userId");
        removeUser.setParameter("userId", userId);
        removeUser.executeUpdate();
    }
    
    /**
     * The 'getUsersByProgramId' function will return all staff members associated to the selected program.
     * 
     * @param programId The id of the selected program.
     * @return This function will return a list of User objects
     */
    @Override
    public List<User> getUsersByProgramId(Integer programId) {
        
        Query query = sessionFactory.getCurrentSession().createQuery("from programAdmin where programId = :programId");
        query.setParameter("programId", programId);
        
        List<programAdmin> programAdminList = query.list();
        List<Integer> userIds = null;
        
        if(!programAdminList.isEmpty()) {
            userIds = new CopyOnWriteArrayList<Integer>();
            
            for(programAdmin program : programAdminList) {
                userIds.add(program.getsystemUserId());
            }
        }
        
        List<User> users = null;
        
        if(!userIds.isEmpty()) {
            
            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
            criteria.add(Restrictions.in("id", userIds));
            criteria.add(Restrictions.eq("roleId", 3));
            
            users = criteria.list();
            
        }
        
        return users;
       
    }
    
    /**
     * The 'getUserTypes' function will return a list of available user types
     *
     */
    @Override
    @SuppressWarnings("rawtypes")
    public List getUserTypes() {
        Query query = sessionFactory.getCurrentSession().createSQLQuery("SELECT id, type FROM lu_userTypes order by id asc");

        return query.list();
    }
    
    /**
     * The 'searchStaffMembers' function will search the program staff members based on the passed in criteria.
     * 
     * @param programId The program to find staff members for
     * @param firstName The first name search field
     * @param lastName  The last name search field
     * @return
     * @throws Exception 
     */
    @Override
    public List<User> searchStaffMembers(Integer programId, String firstName, String lastName, Integer status, Integer typeId) throws Exception {
        
        Query query = sessionFactory.getCurrentSession().createQuery("from programAdmin where programId = :programId");
        query.setParameter("programId", programId);
        
        List<programAdmin> programAdminList = query.list();
        List<Integer> userIds = null;
        
        if(!programAdminList.isEmpty()) {
            userIds = new CopyOnWriteArrayList<Integer>();
            
            for(programAdmin program : programAdminList) {
                userIds.add(program.getsystemUserId());
            }
        }
        
        List<User> users = null;
        
        if(!userIds.isEmpty()) {
            
            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
            criteria.add(Restrictions.in("id", userIds));
            criteria.add(Restrictions.eq("roleId", 3));
            
            if(firstName != null && !"".equals(firstName)) {
                criteria.add(Restrictions.like("firstName", firstName+"%"));
            }
            
            if(lastName != null && !"".equals(lastName)) {
                criteria.add(Restrictions.like("lastName", lastName+"%"));
            }
            
            if(status != null && status != 2) {
                if(status == 1) {
                    criteria.add(Restrictions.eq("status", true));
                }
                else {
                    criteria.add(Restrictions.eq("status", false));
                }
            }
            
            if(typeId != null && typeId > 0) {
                criteria.add(Restrictions.eq("typeId", typeId));
            }
            
            users = criteria.list();
            
        }
        
        return users;
    }
    
    
    /**
     * The 'getUserPrograms' function will return the list of associated programs for the selected user.
     * 
     * @param userId    The id of the selected user.
     * @return
     * @throws Exception 
     */
    @Override
    public List<userPrograms> getUserPrograms(Integer userId) throws Exception {
        
        String sqlQuery = "select a.id as assocId, b.programName, a.programId, a.dateCreated from user_programs a inner join programs b on b.id = a.programId where a.systemUserId = " + userId;
        
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery) 
        .setResultTransformer(Transformers.aliasToBean(userPrograms.class)
        );
        
        return query.list();
    }
    
    /**
     * The 'removeProgram' function will remove the association with the passed in user and the passed in program
     * 
     * @param userId    The id of the user
     * @param programId The id of the selected program
     * 
     * @throws Exception 
     */
    @Override
    public void removeProgram(Integer userId, Integer programId) throws Exception {
        
        //Remove the user program module associations
        Query removeModules = sessionFactory.getCurrentSession().createQuery("delete from userProgramModules where systemUserId = :userId and programId = :programId");
        removeModules.setParameter("userId", userId);
        removeModules.setParameter("programId", programId);
        removeModules.executeUpdate();
        
        //Remove the user program assocation
        Query removeProgram = sessionFactory.getCurrentSession().createQuery("delete from programAdmin where systemUserId = :userId and programId = :programId");
        removeProgram.setParameter("userId", userId);
        removeProgram.setParameter("programId", programId);
        removeProgram.executeUpdate();
        
        //Remove the user program assocation
        Query removeOrgHierarchy = sessionFactory.getCurrentSession().createQuery("delete from userProgramHierarchy where systemUserId = :userId and programId = :programId");
        removeOrgHierarchy.setParameter("userId", userId);
        removeOrgHierarchy.setParameter("programId", programId);
        removeOrgHierarchy.executeUpdate();
        
    }
    
    @Override
    @SuppressWarnings("unchecked")
    @Transactional
    public List<String> getUserRoles(User user) {
        try {
            String sql = ("select r.role as authority from users u inner join "
            		+ " user_roles r on u.roleId = r.id where u.status = 1 and u.email = :email");

            Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
            query.setParameter("email", user.getEmail());
            List<String> roles = query.list();

            return roles;

        } catch (Exception ex) {
            System.err.println("getUserRoles  " + ex.getCause());
            ex.printStackTrace();
            return null;
        }
    }
    
    @Override
    public void insertUserLog (Log_userSurveyActivity ual) {
    	sessionFactory.getCurrentSession().save(ual);
    }

}
