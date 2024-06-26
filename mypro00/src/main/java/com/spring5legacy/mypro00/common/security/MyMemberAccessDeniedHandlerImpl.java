package com.spring5legacy.mypro00.common.security;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import lombok.extern.log4j.Log4j;

@Log4j
public class MyMemberAccessDeniedHandlerImpl implements AccessDeniedHandler{

	
	
	@Override
	public void handle(HttpServletRequest request, 
					   HttpServletResponse response,
					   AccessDeniedException accessDeniedException) throws IOException, ServletException {
		
		System.out.println("AccessDeniedHandler의 구현 객체 - 사용자요청 Redirect.....");
		response.sendRedirect("/mypro00/myboard/list");
	}

}
