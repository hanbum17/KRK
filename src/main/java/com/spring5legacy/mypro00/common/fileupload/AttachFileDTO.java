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
@AllArgsConstructor
public class AttachFileDTO {
	
    private String uuid ;       				//파일 이름에 추가된 UUID.toString() 값.
	private String fileName ;   				//원본파일이름
    private String uploadPath ; 				//yyyy/MM/dd 형식 경로 문자열
    private String fileType ;   				//파일유형(이미지파일:I, 이미지가 아닌파일:F)
    
    private String repoPath = "C:/myupload" ; 	//서버 레포지토리 경로(C:\myupload)

    
    
}

