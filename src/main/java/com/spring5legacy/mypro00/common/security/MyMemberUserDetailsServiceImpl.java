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



//	@Autowired
//	public MyMemberDetailsServiceImpl(MyMemberMapper myMemberMapper) {
//		this.myMemberMapper = myMemberMapper;
//	}



//	@Override
//		public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		MyMemberVO myMember = myMemberMapper.selectMember(username);
//		
//		return myMember == null ? null : new MyMemberUser(myMember);
//	}

	
	@Override
	public UserDetails loadUserByUsername (String username) {
		MyMemberVO myMember = myMemberMapper.selectMember(username);
		UserDetails userDetails = null;
		try {
		userDetails = User.builder().username(myMember.getUserid())
									.password(myMember.getUserpw()) 
									.authorities(myMember.getAuthList()
				        		  						 .stream()
				        		  						 .map(auth -> new SimpleGrantedAuthority(auth.getAuthority()))
				        		  						 .collect(Collectors.toList())
											    ).build();
			return userDetails;
		} catch (UsernameNotFoundException e) {
			return null;
		}		        		  				
	}	
}















