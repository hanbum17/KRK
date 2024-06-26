package com.spring5legacy.mypro00.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MyBoardAttachFileVO {
	
	private String uuid ;
	private String uploadPath ;
	private String fileName ;
	private String fileType ;
	private Long bno ;
	private Integer fileDelFlag ;
	private String repoPath = "C:/myupload" ;
	
}
