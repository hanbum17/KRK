package com.spring5legacy.mypro00.common.filedownload;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FileDownloadAjaxController {
	//파일 종류에 상관없이 다운로드가 가능하도록, 웹 브라우저로 보내는 파일의 MIME타입을 application/octet-stream 으로 설정
	@GetMapping(value = {"/doFileDownloadByAjax"}, produces = {"application/octet-stream"})
//	@ResponseBody  //일반 컨트롤러에서 사용자 브라우저에 데이터만 전송하는 REST-API 어노테이션
	public ResponseEntity<Resource> doFileDownloadByAjax(String fileName) {
		System.out.println("fileName: " + fileName);
		//C:/myupload/2024/06/02/96194ba2-73eb-4550-b584-684836f243e1_샘플 PDF.pdf
		
	    //파일 액세스 및 전송을 위해 스프링 Resource 인터페이스 타입의 구현객체를 사용(Resource: 대략 InputStream + File)
		Resource fileResource = new FileSystemResource(fileName) ;
		System.out.println("fileResource: " + fileResource);
		//file [C:\myupload\2024\06\02\96194ba2-73eb-4550-b584-684836f243e1_샘플 PDF.pdf]
		
		if(!fileResource.exists()) {
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND) ;
		}
		
		String downloadName = fileResource.getFilename() ;
		downloadName = downloadName.substring(downloadName.indexOf("_") + 1) ;
		
		HttpHeaders httpHeaders = new HttpHeaders() ;
		
		String _downloadName = null ;
		
		try {
			_downloadName = new String(downloadName.getBytes("UTF-8"), "ISO-8859-1") ;
			
			httpHeaders.add("Content-Disposition", "attachment; filename=" + _downloadName) ;
//			httpHeaders.add("Content-Disposition", "attachment; filename=" + downloadName) ;
		} catch (UnsupportedEncodingException e) {
			e.getMessage();
		}
		
		return new ResponseEntity<Resource>(fileResource, httpHeaders, HttpStatus.OK) ;
	}
	
	
	
	@GetMapping(value = {"/displayThumbnail"})
	public ResponseEntity<byte[]> sendThumbnail(String thumbnail) {
		
		System.out.println("thumbnail: " + thumbnail);
		
		File thumbnailFile = new File(thumbnail) ;
		
		if(!thumbnailFile.exists()) {
			return new ResponseEntity<byte[]>(new byte[0], HttpStatus.NOT_FOUND) ;
		}
		
		ResponseEntity<byte[]> result = null ;
		HttpHeaders httpHeaders = new HttpHeaders() ; 
		
		try {
			httpHeaders.add("Content-Type", Files.probeContentType(thumbnailFile.toPath()));
			result = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(thumbnailFile),
                                                httpHeaders,
                                                HttpStatus.OK) ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result ;
		
	}

}
