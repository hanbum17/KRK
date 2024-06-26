package com.spring5legacy.mypro00.common.fileupload;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnailator;

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

@RestController
public class FileUploadAjaxController {

    private String uploadFileRepoDir = "C:\\myupload";

//    @GetMapping("/fileUploadByAjax")
//    public String showFileUploadPage() {
//        return "sample/fileUploadByAjax";
//    }

    @PostMapping(value = "/doFileUploadByAjax", produces = {"application/json; charset=utf-8"})
    @ResponseBody
    public List<AttachFileDTO> doFileUploadByAjax(MultipartFile[] uploadFiles) {
    	
    	if(uploadFiles == null) {
    		return new ArrayList<AttachFileDTO>();
    	}
    
    	List<AttachFileDTO> attachFileList = new ArrayList<AttachFileDTO>() ;
    	AttachFileDTO attachFile = null ;
    	
    	
    	//날짜 형식 폴더 구조 생성
    	String dateDir = getDatePathName();
    	File fileUploadPath = new File(uploadFileRepoDir, dateDir);
    	
    	
    	System.out.println("폴더 생성 결과: " + fileUploadPath.mkdirs());
    	
    	
        for (MultipartFile uploadFile : uploadFiles) {
            System.out.println("uploadFile.getOriginalFilename(): " + uploadFile.getOriginalFilename());
            System.out.println("uploadFile.getName(): " + uploadFile.getName());
            System.out.println("uploadFile.getContentType(): " + uploadFile.getContentType());
            System.out.println("uploadFile.getSize(): " + uploadFile.getSize());

            attachFile = new AttachFileDTO();
            attachFile.setUploadPath(dateDir);
            attachFile.setRepoPath(uploadFileRepoDir);
            
            String uploadFileName = uploadFile.getOriginalFilename();
            uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);

            attachFile.setFileName(uploadFileName);
            
         // 파일 이름 충돌 방지
            String uuid = UUID.randomUUID().toString();
            uploadFileName = uuid + "_" + uploadFileName;
            
            attachFile.setUuid(uuid) ;
            
            File saveUploadFile = new File(fileUploadPath, uploadFileName);
            try {
                uploadFile.transferTo(saveUploadFile); // 저장
                
                if(isImageFile(saveUploadFile)) {
                	
                	attachFile.setFileType("I");
                	
                	File thumbnailFile = new File(fileUploadPath, "s_" + uploadFileName);
                	FileOutputStream myfos = new FileOutputStream(thumbnailFile);
                	
                	InputStream myis = uploadFile.getInputStream();
                	
                	Thumbnailator.createThumbnail(myis, myfos, 50, 50);
                	
                	myis.close();
                	myfos.flush();
                	myfos.close();
                } else {
                	attachFile.setFileType("F");
                }
                
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            attachFileList.add(attachFile);
        }
        return attachFileList;
    }
    
    private String getDatePathName() {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    	return sdf.format(new Date());
    }
    
    private boolean isImageFile(File myFile) {
    	
    	String myFileContentType = null;
    	try {
			myFileContentType = Files.probeContentType(myFile.toPath());
			return myFileContentType.startsWith("image");
		} catch (IOException e) {
			e.getMessage();
			return false;
		}
    	
    	
    }
    
  //첨부파일 삭제
    @DeleteMapping(value = {"/deleteFile"})
    @ResponseBody
    public ResponseEntity<String> deleteFile(String fileName, String fileType) {
    	System.out.println("fileName: " + fileName);
    	
    	try {
			fileName = URLDecoder.decode(fileName, "utf-8") ;
			System.out.println("fileName: " + fileName);
    	} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    
    	
    	File delFile = new File(fileName);
    	
    	boolean delResult = delFile.delete();
    	//정상삭제: true 반환, 삭제실패: false반환
    	
    	if(!delResult) {
    		return new ResponseEntity<String>("DelFail", HttpStatus.OK) ;
    	}
    	
    	if(fileType.equals("I")) {
    		delFile = new File(fileName.replaceFirst("s_", ""));
    		delResult = delFile.delete() ;
    	}
    	
    	return delResult ? new ResponseEntity<String>("DelSuccess", HttpStatus.OK) 
    					 : new ResponseEntity<String>("DelFail", HttpStatus.OK);
    }

}









