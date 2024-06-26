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

@Controller
public class FileDownloadAjaxController {
	
	@GetMapping(value = {"/doFileDownloadAjax"}, produces = {"application/octet-stream"})
	public ResponseEntity<Resource> doFileDownloadAjax(String fileName) {
		
		System.out.println("처리 전 파일이름: " + fileName);
		
		Resource fileResource = new FileSystemResource(fileName);
		System.out.println("fileResource: " + fileResource);
		
		if(!fileResource.exists()) {
	        return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
	    }

	    String downloadName = fileResource.getFilename();
	    downloadName = downloadName .substring(downloadName .indexOf("_") + 1);
	    
	    //스프링의 HttpHeders 객체 생성
	    HttpHeaders httpHeaders = new HttpHeaders();
	    
	    String _downloadName = null;
	    
        try {
			_downloadName = new String(downloadName.getBytes("UTF-8"), "ISO-8859-1");
			
			httpHeaders.add("Content-Disposition", "attachment; filename=" + _downloadName);
//			httpHeaders.add("Content-Disposition", "attachment; filename=" + downloadName);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		
		return new ResponseEntity<Resource>(fileResource, httpHeaders, HttpStatus.OK);
	}
	
	@GetMapping(value = "/displayThumbnail")
	public ResponseEntity<byte[]> sendThumbnail(String fileName) {
		
		File thumbnailFile = new File(fileName) ;     
		
		if (!thumbnailFile.exists()) {
		    return new ResponseEntity<byte[]>(new byte[0], HttpStatus.NOT_FOUND);
		}

		
        ResponseEntity<byte[]> result = null ;
        HttpHeaders httpHeaders = new HttpHeaders() ;

        try {
			httpHeaders.add("Content-Type", Files.probeContentType(thumbnailFile.toPath())) ;
			result = new ResponseEntity<byte[]>(	FileCopyUtils.copyToByteArray(thumbnailFile), httpHeaders, HttpStatus.OK) ;
		} catch (IOException e) {
			e.printStackTrace();
		}

        
        
        return result ;
    }


	
}
