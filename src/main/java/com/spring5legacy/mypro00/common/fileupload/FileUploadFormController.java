package com.spring5legacy.mypro00.common.fileupload;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.spring5legacy.mypro00.domain.MyBoardVO;

@Controller
public class FileUploadFormController {

private String uploadFileRepoDir = "C:\\myupload" ;
	
	//업로드 요청 JSP 호출 메서드
	@GetMapping("/fileUploadByForm")
	public String showFileUploadPage() {
	    return "sample/fileUploadByForm";
	}
	
	
	//파일업로드 요청 처리
	//업로드 처리 핵심 기능 : 보낸 파일을 받음 > 저장
	//스프링-MVC에는 제공하는 MultipartFile 타입의 매개변수를 이용, 파일-input의 name 속성과 매개변수 이름이 동일해야 함
    //여러개의 파일을 처리하기 위해 MultipartFile[] 타입을 사용
	
	@PostMapping("/doFileUploadByForm")
	public String doFileUploadByForm(MultipartFile[] uploadFiles, @ModelAttribute("myBoardVO") MyBoardVO myBoardVO) {
		for( MultipartFile uploadFile : uploadFiles) {
			File saveUploadFile = new File(uploadFileRepoDir, uploadFile.getOriginalFilename());
			
			String uploadFileName = uploadFile.getOriginalFilename();
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\")+1);
			
			try {
				uploadFile.transferTo(saveUploadFile);
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	
		
		
		return "sample/fileUploadResult";
	}
	
}






