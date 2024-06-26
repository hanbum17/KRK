package com.spring5legacy.mypro00.common.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.spring5legacy.mypro00.domain.MyAuthorityVO;
import com.spring5legacy.mypro00.domain.MyMemberVO;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MyMemberUser extends User{
	
	private static final long serialVersionUID = 1L;
	private MyMemberVO myMember;
	 
//    private static List<GrantedAuthority> changeToGrantedAuthList(List<MyAuthorityVO> authList) {
//        List<GrantedAuthority> grantedAuthList = new ArrayList<>();
//        GrantedAuthority grantedAuth = null;
//        for (MyAuthorityVO myAuth : authList) {
//            grantedAuth = new SimpleGrantedAuthority(myAuth.getAuthority());
//            grantedAuthList.add(grantedAuth);
//        }
//        return grantedAuthList;
//    }
//		
//    public MyMemberUser(MyMemberVO myMember) {
//        super(myMember.getUserid(),
//              myMember.getUserpw(),
//              changeToGrantedAuthList(myMember.getAuthList()));
//
//        this.myMember = myMember;
//    }
	
	
    public MyMemberUser(MyMemberVO myMember) {
        super(myMember.getUserid(),
              myMember.getUserpw(),
              myMember.getAuthList().stream()
                      .map(auth -> new SimpleGrantedAuthority(auth.getAuthority()))
                      .collect(Collectors.toList()));
    
        this.myMember = myMember;
    }
}










