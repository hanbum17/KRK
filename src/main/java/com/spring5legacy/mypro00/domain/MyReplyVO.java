package com.spring5legacy.mypro00.domain;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MyReplyVO {
	private Long rno;
	private String rcontent;
	private String rwriter;
	private Date rregDate;
	private Date modDate;
	private Long bno;
	private Long prno;
	private Integer rdelFlag;	//1: 삭제 요청됨, : 유지

	//private String bwriter; //시큐리티: 게시물 작성자의 댓글 등록 방지
	private int lvl; //오라클 계중쿼리의 level 값을 저장할 필드

	
	
}


