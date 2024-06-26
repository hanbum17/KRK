package com.spring5legacy.mypro00.common.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyAccessForbiddenDenied {

	@GetMapping(value = {"/accessDeniedError", "/accessForbiddenError"})
	public String toAccessForbiddenPage(Model model, Authentication authentiation) {
		System.out.println("AccessDenied(Forbidden) 오류 발생 > Authentication 정보: " + authentiation);
		model.addAttribute("msg", "접근이 금지됨");
		
		
		return "common/errors/myErrorAccessDeniedPage";
	}
	
	
}
