package com.spring5legacy.mypro00.common.fileupload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnailator;

@RestController
public class FileUploadAjaxController {
	
    private String uploadFileRepoDir = "C:/myupload" ; //업로드 파일 저장 폴더
	
    //업로드 요청 JSP 호출 메서드
//    @GetMapping("/fileUploadByAjax")
//    public String showFileUpoadPage() {
//        return "sample/fileUploadByAjax" ;
//    }
	
    //파일업로드 요청 처리
    //업로드 처리 핵심 기능: 보낸 파일을 받음 > 저장
    //스프링-MVC에는 제공하는 MultipartFile 타입의 매개변수를 이용, 파일-input의 name 속성과 매개변수 이름이 동일해야 함
    //여러개의 파일을 처리하기 위해 MultipartFile[] 타입을 사용
    @PostMapping(value = "/doFileUploadByAjax", produces = {"application/json; charset=utf-8"})
    @ResponseBody
    public List<AttachFileDTO> doFileUploadByAjax(MultipartFile[] uploadFiles) {
        if (uploadFiles == null) {
            return new ArrayList<AttachFileDTO>() ;
        }
        
        List<AttachFileDTO> attachFileList = new ArrayList<AttachFileDTO>() ;
        AttachFileDTO attachFile = null ;
        
        //날짜 형식 폴더 구조 생성
        String dateDir = getDatePathName() ;
        File fileUploadPath = new File(uploadFileRepoDir, dateDir) ;  // C:/myupload\2023/12/14
        System.out.println("폴더 생성 결과: " + fileUploadPath.mkdirs());
		
        String uploadFileName = null ;
        String uuid = null ;
        File thumbnailFile = null ;
        FileOutputStream myfos = null ;
        InputStream myis = null ;
        
        for( MultipartFile uploadFile : uploadFiles) {
            System.out.println("uploadFile.getOriginalFilename(): " + uploadFile.getOriginalFilename());
            System.out.println("uploadFile.getName(): " + uploadFile.getName());
            System.out.println("uploadFile.getContentType(): " + uploadFile.getContentType());
            System.out.println("uploadFile.getSize(): " + uploadFile.getSize());
            
            attachFile = new AttachFileDTO() ;
            attachFile.setUploadPath(dateDir) ;
            attachFile.setRepoPath(uploadFileRepoDir) ;
            
            uploadFileName = uploadFile.getOriginalFilename() ;
            uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\")+1) ;
            attachFile.setFileName(uploadFileName) ;
            
            uuid = UUID.randomUUID().toString() ;
            uploadFileName = uuid + "_" + uploadFileName ;
            attachFile.setUuid(uuid) ;  //UUID 저장
            
            File saveUploadFile = new File(fileUploadPath, uploadFileName); //uploadFileRepoDir > fileUploadPath로 수정
            try {
                uploadFile.transferTo(saveUploadFile) ; //저장
                
                if(isImageFile(saveUploadFile)) {
                
                    attachFile.setFileType("I") ;  //파일유형 저장
                    thumbnailFile = new File(fileUploadPath, "s_" + uploadFileName) ;
                    
                    myfos = new FileOutputStream(thumbnailFile) ;
                    myis = uploadFile.getInputStream() ;
                    Thumbnailator.createThumbnail(myis, myfos, 50, 50) ;
                    myis.close() ;
                    myfos.flush() ;
                    myfos.close() ;
                } else {
                    attachFile.setFileType("F") ;  //파일유형 저장
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            
            attachFileList.add(attachFile) ;  //첨부파일 정보가 저장된 DTO 객체를 List 타입 객체에 저장
        }
        return attachFileList ;  //List 타입 객체 반환 > @ResponseBody 어노테이션에 의해 브라우저의 ajax() 동작함수에 결과 전송됨
    }
	
    //날짜형식 문자열 생성 메서드(yyyy/MM/dd)
    private String getDatePathName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd") ;
        return sdf.format(new Date()) ;
    }
	
    //이미지파일 확인 메서드
    private boolean isImageFile(File myFile) {
    
        String myFileContentType = null ;
        
        try {
            myFileContentType = Files.probeContentType(myFile.toPath()) ;
            return myFileContentType.startsWith("image") ;
            
        } catch (IOException e) {
            e.getMessage() ;
            return false ;
        }
    }
    
    //@Controller       @RestController
    //@ResponseBody        @PostMapping
    //ResponseEntity       @GetMapping
    //   @PostMapping      @DeleteMapping
    //   @GetMapping       @PutMapping
    //                     @PatchMapping
   
    
    //첨부파일 삭제
    @PostMapping(value = {"/deleteFile"})
    public ResponseEntity<String> deleteFile( String fileName, String fileType) {
    	
    	System.out.println("fileName: " + fileName);
        System.out.println("fileType: " + fileType);
    	
    	try {
			fileName = URLDecoder.decode(fileName, "utf-8") ;
			System.out.println("fileName: " + fileName);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	File delFile = new File(fileName) ;
    	
    	boolean delResult = delFile.delete() ;  //일반파일과 썸네일 파일 삭제
    	//정상삭제: true 반환, 삭제실패: false 반환
    	
    	if(!delResult) {
    		return new ResponseEntity<String>("DelFail", HttpStatus.NOT_FOUND) ;
    	}
    	
    	if(fileType.equals("I")) {
    		delFile = new File(fileName.replaceFirst("s_", "")) ;
    		delResult = delFile.delete() ;
    	}
    	
    	return delResult ? new ResponseEntity<String>("DelSuccess", HttpStatus.OK) 
    			         : new ResponseEntity<String>("DelFail", HttpStatus.OK);
    }
  
   
}
