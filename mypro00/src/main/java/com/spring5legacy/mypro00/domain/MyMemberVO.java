package com.spring5legacy.mypro00.domain;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MyMemberVO {
	private String userid ;
	private String userpw ;
	private String username ;
	private Date mregDate ;
	private Date mmodDate ;
	private String mdropFlag ;
	private Boolean enabled ;
	
	private List<MyAuthorityVO> authList ;

}
