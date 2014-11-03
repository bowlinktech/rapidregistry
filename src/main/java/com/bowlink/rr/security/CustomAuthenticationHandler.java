package com.bowlink.rr.security;

import com.bowlink.rr.model.User;
import com.bowlink.rr.model.program;
import com.bowlink.rr.service.programManager;
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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;

public class CustomAuthenticationHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private userManager usermanager;
    
    @Autowired
    private programManager programmanager;
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        String programAdminTargetUrl = "/programAdmin/surveys";
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
            
            Integer programId = 0;
            List<program> availPrograms = null;
            String[][] availablePrograms = null;
            try {
                availPrograms = programmanager.getProgramsByAdminisrator(userDetails.getId());
               
                if(!availPrograms.isEmpty()) {
                    availablePrograms = new String[availPrograms.size()][2];
                    int index = 0;
                    for(program program : availPrograms) {
                        availablePrograms[index][0] = Integer.toString(program.getId());
                        availablePrograms[index][1] = program.getProgramName();
                        index++;
                    }
                    programId = availPrograms.get(0).getId();
                }
                
            } catch (Exception ex) {
                Logger.getLogger(CustomAuthenticationHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
           
            session.setAttribute("availPrograms", availablePrograms);
            session.setAttribute("selprogramId", programId);
            
            getRedirectStrategy().sendRedirect(request, response, programAdminTargetUrl);
        } 
        else {
            super.onAuthenticationSuccess(request, response, authentication);
            return;
        }
    }
}
