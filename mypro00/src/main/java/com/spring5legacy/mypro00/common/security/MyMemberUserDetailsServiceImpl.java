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
//	}
	
	@Override
	public UserDetails loadUserByUsername(String username)  {
		
		//로그인 페이지에서 입력된 값
		MyMemberVO myMember = myMemberMapper.selectMember(username);
		
		try {
			
			UserDetails userDetails = User.builder()//DB에서 가져온 (mymemberVO에있는 id) 
										  .username(myMember.getUserid())
										  .password(myMember.getUserpw())
										  .authorities(
										       myMember.getAuthList() //List<MyAuthorityVO> 타입이였는데
										       		   .stream() //Stream<MyAuthorityVO>로 바뀜
										       		   .map(auth -> new SimpleGrantedAuthority(auth.getAuthority()))// Stream<GrantedAuthority>로 바뀜
										       		   .collect(Collectors.toList()) //List<GrantedAuthority> 로 바뀜
												  
										  )
										  .build() ;
			return userDetails ;
			
		} catch(UsernameNotFoundException e) {
			
			return null ;
			
		}
		
	} //UserDetails_end

} //class_end










