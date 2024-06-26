package com.spring5legacy.mypro00.common.security;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.spring5legacy.mypro00.domain.MyMemberVO;
import com.spring5legacy.mypro00.mapper.MyMemberMapper;


public class MyMemberUserDetailsServiceImpl implements UserDetailsService{
	
	private MyMemberMapper myMemberMapper ;
	
	@Autowired
	public void setMyMemberMapper(MyMemberMapper myMemberMapper) {
		this.myMemberMapper = myMemberMapper;
	}
    
//    @Autowired
//    public MyMemberUserDetailsServiceImpl(MyMemberMapper myMemberMapper) {
//			this.myMemberMapper = myMemberMapper;
//	}


//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		MyMemberVO myMember = myMemberMapper.selectMember(username) ;
//		
//		return myMember == null ? null : new MyMemberUser(myMember) ;
//		
//		
//		
//		
//	}
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		MyMemberVO myMember = myMemberMapper.selectMember(username) ; //username: 로그인 페이지에서 입력된 값
		
		UserDetails userDetails =null;
		try {
			userDetails=User.builder()
							.username(myMember.getUserid())
							.password(myMember.getUserpw())
							.authorities(
								myMember.getAuthList()  //List<MyAuthorityVO>
								.stream()  //Stream<MyAuthorityVO>
								.map(auth -> new SimpleGrantedAuthority(auth.getAuthority())) //Stream<GrantedAuthority>
			                    .collect(Collectors.toList()) //List<GrantedAuthority>
							).build();
			return userDetails;
		}catch (UsernameNotFoundException e) {
			return null;
		}
		
	}	

}












