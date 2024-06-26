package com.spring5legacy.mypro00.mapper;

import com.spring5legacy.mypro00.domain.MyAuthorityVO;
import com.spring5legacy.mypro00.domain.MyMemberVO;

public interface MyMemberMapper {
	
	public MyMemberVO selectMember(String userid) ;
	
	public Integer insertMember(MyMemberVO myMember) ;
	
	public Integer insertMemberAuth(MyAuthorityVO myAuth) ;

}
