package com.bowlink.rr.security;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;


public class CustomWebAuthenticationDetails extends
    WebAuthenticationDetailsSource {

  @Override
  public WebAuthenticationDetails buildDetails(HttpServletRequest context) {
    return new MyAuthenticationDetails(context);
  }

  @SuppressWarnings("serial")
  class MyAuthenticationDetails extends WebAuthenticationDetails {

    private final String loginAsUser;
    
    private final String programId;

    public MyAuthenticationDetails(HttpServletRequest request) {
      super(request);
      this.loginAsUser = request.getParameter("loginAsUser");
      this.programId = request.getParameter("programId");
      
    }

    public String getLoginAsUser() {
      return loginAsUser;
    }
    
    public String getProgramId() {
        return programId;
      }
  }

}