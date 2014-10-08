package com.bowlink.rr.security;

import com.bowlink.rr.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletException;

import java.io.IOException;
import java.util.Set;

import com.bowlink.rr.service.userManager;
import javax.servlet.http.HttpSession;

public class CustomAuthenticationHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private userManager usermanager;
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        String programAdminTargetUrl = "/programAdmin";
        String sysAdminTargetUrl = "/sysAdmin/programs";
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        usermanager.setLastLogin(authentication.getName());
        
        HttpSession session = request.getSession();

        if (roles.contains("ROLE_SYSTEMADMIN")) {
            
            /* Need to get the userId */
            User userDetails = usermanager.getUserByEmail(authentication.getName());
            
            /* Need to store the user object in session */
            session.setAttribute("userDetails", userDetails);
            
            getRedirectStrategy().sendRedirect(request, response, sysAdminTargetUrl);
        } 
        
        else if (roles.contains("ROLE_PROGRAMADMIN")) {
            /* Need to get the userId */
            User userDetails = usermanager.getUserByEmail(authentication.getName());
            
            /* Need to store the user object in session */
            session.setAttribute("userDetails", userDetails);
            
            
            getRedirectStrategy().sendRedirect(request, response, programAdminTargetUrl);
        } 
        else if (roles.contains("ROLE_USER")) {
            /* Need to get the userId */
            User userDetails = usermanager.getUserByEmail(authentication.getName());
            
            /* Need to store the user object in session */
            session.setAttribute("userDetails", userDetails);
            
            
            getRedirectStrategy().sendRedirect(request, response, "***PROGRAM NAME***/patients");
        }
        else {
            super.onAuthenticationSuccess(request, response, authentication);
            return;
        }
    }
}
