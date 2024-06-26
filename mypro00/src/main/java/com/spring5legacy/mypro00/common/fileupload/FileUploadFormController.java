package com.spring5legacy.mypro00.common.fileupload;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.spring5legacy.mypro00.domain.MyBoardVO;

@Controller
public class FileUploadFormController {
	
	private String uploadFileRepoDir = "C:/myupload" ; //업로드 파일 저장 폴더
	
	//업로드 요청 JSP 호출 메서드
	@GetMapping("/fileUploadByForm")
	public String showFileUpoadForm() {
		return "sample/fileUploadByForm" ;
	}
	
	//파일업로드 요청 처리
	//업로드 처리 핵심 기능: 보낸 파일을 받음 > 저장
    //스프링-MVC에는 제공하는 MultipartFile 타입의 매개변수를 이용, 파일-input의 name 속성과 매개변수 이름이 동일해야 함
    //여러개의 파일을 처리하기 위해 MultipartFile[] 타입을 사용
	@PostMapping("/doFileUploadByForm")
	public String doFileUploadByForm(MultipartFile[] uploadFiles, 
//			                         @ModelAttribute("myBoard") MyBoardVO myBoard) {
		                             @ModelAttribute("msg") String msg) {
//		System.out.println("btitle: " + myBoard.getBtitle());
//		System.out.println("bcontent: " + myBoard.getBcontent());
		
		for( MultipartFile uploadFile : uploadFiles) {
			System.out.println("uploadFile.getOriginalFilename(): " + uploadFile.getOriginalFilename());
			System.out.println("uploadFile.getName(): " + uploadFile.getName());
			System.out.println("uploadFile.getContentType(): " + uploadFile.getContentType());
			System.out.println("uploadFile.getSize(): " + uploadFile.getSize());
//			try {
//				System.out.println("uploadFile.getResource().toString(): " + uploadFile.getResource().getFile().getPath());
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
			System.out.println("=============================================================================");
			
			//File saveUploadFile = new File(uploadFileRepoDir, uploadFile.getOriginalFilename());
			
			String uploadFileName = uploadFile.getOriginalFilename() ;
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\")+1) ;
			
			File saveUploadFile = new File(uploadFileRepoDir, uploadFileName);
			try {
				uploadFile.transferTo(saveUploadFile) ; //저장
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
			
		}
		
		return "sample/fileUploadResult";
	}


}
