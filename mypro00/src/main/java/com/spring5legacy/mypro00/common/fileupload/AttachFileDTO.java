package com.spring5legacy.mypro00.common.fileupload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
//@AllArgsConstructor
public class AttachFileDTO {
	
	private String uuid ;  // 파일 이름에 추가된 UUID.toString() 값.
	private String fileName ;  //원본 파일이름(img0.jpg)
	private String uploadPath ; //yyyy/MM/dd 형식 경로 문자열
	private String fileType ; //I:이미지파일, F:이미지가 아닌 모든 파일
	
	private String repoPath = "C:/myupload" ;
	

}
