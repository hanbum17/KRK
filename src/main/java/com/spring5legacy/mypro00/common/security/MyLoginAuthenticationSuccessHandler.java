package com.spring5legacy.mypro00.common.security;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;


public class MyLoginAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess (HttpServletRequest request, 
										 HttpServletResponse response,
										 Authentication authentication) throws ServletException, IOException {

		HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
        
        Set<String> roleNames = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        
        RequestCache requestCache = new HttpSessionRequestCache();
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        
        if(savedRequest == null) {
            if (roleNames.contains("ADMIN")) {
        	       response.sendRedirect("/mypro00/myboard/detail?bno=1");
            } else {
                response.sendRedirect("/mypro00/");
            }
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }


	}
	
}
