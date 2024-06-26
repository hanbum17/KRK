package com.spring5legacy.mypro00.common.security;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;


public class MyLoginAuthenticationSuccessHandler 
	   extends SavedRequestAwareAuthenticationSuccessHandler {

	
	//재정의 하기
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, 
										HttpServletResponse response,
										Authentication authentication) throws ServletException, IOException {
        //이전 인증 오류 삭제(아래 설명 참고)
        //로그인 실패 후에 로그인을 성공한 경우, 스프링 시큐리티에 의해 로그인 실패 오류가 session 객체에 남겨짐
        //로그인 성공 시에, 로그인 과정의 실패 오류 관련 데이터를 session 객체에서 삭제해주는 것이 좋음
		HttpSession session = request.getSession(false);
			if(session!=null) {
				session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
			}
			
			System.out.println("로그인 성공 -> 전달된 Authentication : " + authentication);
		
		//권한이름을 Set 객체에 저장
		Set<String> roleNames= AuthorityUtils.authorityListToSet(authentication.getAuthorities());
		System.out.println("ROLE NAMES : " + roleNames ); //권한 이름의 리스트를 표시
		
		RequestCache requestCache = new HttpSessionRequestCache();
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		
        if(savedRequest == null) {
            if (roleNames.contains("ADMIN")) {
        	       response.sendRedirect("/mypro00/myboard/detail?bno=1");
            } else {
                response.sendRedirect("/mypro00/myboard/list");
            }
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }

	}
	
}
