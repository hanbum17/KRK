package com.spring5legacy.mypro00.common.schedule_task;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.spring5legacy.mypro00.domain.MyBoardAttachFileVO;
import com.spring5legacy.mypro00.mapper.MyScheduledMapper;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ClearMyUploadFileRepo {
	//자동실행 확인 실습
//	@Scheduled(cron = "0 0 16 * * *")
//	public void clearNeedlessFiles() {
//		System.out.println("자동실행됨1");
//	}
//	
//	@Scheduled(cron = "0 0 16 * * *")
//	public void clearNeedlessFiles2() {
//		System.out.println("자동실행됨2");
//	}
//	
//	@Scheduled(cron = "0 0 16 * * *")
//	public void clearNeedlessFiles3() {
//		System.out.println("자동실행됨3");
//	}
	
	private MyScheduledMapper myScheduledMapper ;
	
	
	private String getDateStringBeforeOneDay() {
		//오늘 날짜 문자열을 생성하는 코드를 작성하세요
		//SimpleDateFormat mysdf = new SimpleDateFormat("yyyy/MM/dd") ;
		//Date today = new Date() ;
		//String _today = mysdf.format(today) ;
		//return _today ;
		
		//하루 전 날짜 문자열을 생성하는 코드
		SimpleDateFormat mysdf = new SimpleDateFormat("yyyy/MM/dd") ;
		Calendar myCalendar = Calendar.getInstance() ;
		myCalendar.add(Calendar.DAY_OF_MONTH, -8) ;
		String beforeOneDay = mysdf.format(myCalendar.getTime()) ;
		System.out.println("getDateStringBeforeOneDay()생성문자열: " + beforeOneDay) ;
		return beforeOneDay;
	}
	
	
	//@Scheduled(cron = "0 50 * * * *")
	public void clearNeedlessFiles() {
		String repoPath = "C:/myupload" ;
		
		//하루 전 날짜경로가 저장된 파일 객체
		//File beforeOneDay1 = Paths.get(repoPath + "/" + getDateStringBeforeOneDay()).toFile() ;
		File beforeOneDay = new File(repoPath + "/" + getDateStringBeforeOneDay()) ;
		
		//데이터베이스에 저장된 하루 전 업로드 된 첨부파일 목록 정보 생성(삭제하면 않되는 파일들)
		List<MyBoardAttachFileVO> fromDBFileList = myScheduledMapper.selectAttachFilesDuringBeforeOneDay() ;
		
		if (fromDBFileList == null) {
			System.out.println("====필요한 첨부파일이 없습니다===============");
			File[] needlessFileArray = null ;
			//삭제해야되는 필요없는 파일 목록 생성
			needlessFileArray = beforeOneDay.listFiles();
			System.out.println("needlessUploadFileArray: \n" + Arrays.toString(needlessFileArray));
			
			//파일 삭제
			if(needlessFileArray == null || needlessFileArray.length == 0) {
				System.out.println("=================== 삭제할 파일이 없습니다.===================");
				return ;
			} else {
				System.out.println("====================== 삭제 파일 목록 ======================");
				for(File needlessFile : needlessFileArray) {
					System.out.println("Deleted FileName: " + needlessFile);
					//Files.deleteIfExists(needlessFile.toPath());
					needlessFile.delete() ;
					
				}
				System.out.println("========================================================");
					
			}
			
		} else { //fromDBFileList != null
//			//DB로부터 전달된 데이터가 있을 경우
//			//방법1
//			List<Path> doNotdeleteFilePathList = new ArrayList<Path>();
//			String fullFileName = null ;
//			String thumbnail = null ;
//			Path attachFilePath = null ;
//			List<File> needlessFileList = new ArrayList<File>() ;
//			//일반파일과 이미지 파일
//			System.out.println("====================== 지우면 않되는 파일 목록 ======================");
//			for (MyBoardAttachFileVO attachFile :fromDBFileList) {
//				fullFileName = attachFile.getRepoPath() + "/" 
//			                 + attachFile.getUploadPath() + "/" 
//						     + attachFile.getUuid() + "_" + attachFile.getFileName() ;
//				attachFilePath = (new File(fullFileName)).toPath() ;
//				System.out.println(attachFilePath);
//				doNotdeleteFilePathList.add(attachFilePath);
//				//썸네일 추가
//				if(attachFile.getFileType().equals("I")) {
//					
//					thumbnail = attachFile.getRepoPath() + "/" 
//			                  + attachFile.getUploadPath() + "/s_" 
//						      + attachFile.getUuid() + "_" + attachFile.getFileName() ;
//				    attachFilePath = (new File(thumbnail)).toPath() ;
//				    System.out.println(attachFilePath);
//				    doNotdeleteFilePathList.add(attachFilePath);
//				}
//			}
//			System.out.println("===================================================================");
//			
//			//지우면 않되는 파일 + 지워야 되는 파일 정보가 모두 저장되어 있음
//			File[] listFiles = beforeOneDay.listFiles();
//			
//			for(File file : listFiles) {
//
//				if(!doNotdeleteFilePathList.contains(file.toPath())) {
//					needlessFileList.add(file) ;
//				}
//			}
//			
//			if (needlessFileList == null || needlessFileList.size() == 0) {
//				System.out.println("=================== 삭제할 파일이 없습니다.===================");
//				return ;
//			} else {
//				System.out.println("====================== 삭제 파일 목록 ======================");
//				for (File needlessFile : needlessFileList) {
//					System.out.println(needlessFile.getAbsolutePath());
//					needlessFile.delete() ;
//				}
//				System.out.println("========================================================");
			
			
			
			//방법2-문서코드
			List<Path> doNotDeleteFilePathList2
            = fromDBFileList  //List<MyBoardAttachFileVO> 타입
              .stream()   // ->Stream<MyBoardAttachFileVO> 로 변환
              .map(attachFile -> Paths.get(attachFile.getRepoPath() + "/" +
                                           attachFile.getUploadPath() + "/" +
                                           attachFile.getUuid() + "_" +
                                           attachFile.getFileName()
                                          )
                  )  //Stream<Path> 로 변환
              .collect(Collectors.toList());  //List<Path> 로 변환
			
			fromDBFileList  //List<MyBoardAttachFileVO> 타입
              .stream()   // ->Stream<MyBoardAttachFileVO> 로 변환
              .filter(attachFile -> attachFile.getFileType().equals("I"))  //-> ->이미지만 Stream<MyBoardAttachFileVO>
              .map(attachFile -> Paths.get(attachFile.getRepoPath() + "/" +
                                           attachFile.getUploadPath() + "/s_" +
                                           attachFile.getUuid() + "_" +
                                           attachFile.getFileName()
                                          )
                  )  //Stream<Path> 로 변환
              .forEach(thumbnailPath -> doNotDeleteFilePathList2.add(thumbnailPath)) ;
			
			System.out.println("====================== 지우면 않되는 파일 목록 ==========================");
			for(Path doNotDeletePath2 : doNotDeleteFilePathList2) {
				System.out.println(doNotDeletePath2);
			}
			System.out.println("===================================================================");
			
			File[] needlessFileList2 = 
					beforeOneDay.listFiles(eachFile -> doNotDeleteFilePathList2.contains(eachFile.toPath()) == false ) ;
			
			if(needlessFileList2 == null || needlessFileList2.length == 0) {
				System.out.println("=================== 삭제할 파일이 없습니다.===================");
				return ;
			} else {
				System.out.println("====================== 삭제 파일 목록 ======================");
				for (File needlessFile : needlessFileList2) {
					System.out.println(needlessFile.getAbsolutePath());
					needlessFile.delete() ;
				}
				System.out.println("========================================================");
			}
			
			
			
			
        }
		
	}				
	
}
