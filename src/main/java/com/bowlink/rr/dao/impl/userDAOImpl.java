/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bowlink.rr.dao.impl;

import com.bowlink.rr.dao.userDAO;
import com.bowlink.rr.model.User;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
        criteria.add(Restrictions.like("email", emailAddress));
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

        String sql = ("select id from users where lower(email) = '" + identifier + "' or lower(username) = '" + identifier + "' or lower(concat(concat(firstName,' '),lastName)) = '" + identifier + "'");

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
}
