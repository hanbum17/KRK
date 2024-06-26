package com.spring5legacy.mypro00.common.security;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyLoginLogoutPageController {
	
	@GetMapping(value = {"/myLogin", "/login" })
	public String toLoginPage(String error, String logout, Model model) {
		
		if (error != null) {
			System.out.println("=========:error.length(): " + error.length());  //0
 			System.out.println("=========:error.hashCode(): " + error.hashCode());
 			
 			model.addAttribute("error", "로그인 아이디와 암호를 확인 후, 로그인 해 주십시오.") ;
		} else if (logout != null) {
			System.out.println("=========:logout.length(): " + logout.length());  //0
 			System.out.println("=========:logout.hashCode(): " + logout.hashCode());
 			
 			model.addAttribute("logout", "로그아웃 하셨습니다.") ;
		} else {
			model.addAttribute("normal", "안녕하세요. 로그인 하십시오.") ;	
		}
	    
		
		
		return "common/myLogin" ;
	}
	
	@GetMapping(value = {"/myLogout", "/logout" })
	public String toLogoutPage() {
		System.out.println("로그아웃 페이지 호출=======");
		
		return "common/myLogout" ;
	}
	
	

}
