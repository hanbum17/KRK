package com.spring5legacy.mypro00.service;



import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring5legacy.mypro00.common.paging.MyBoardPagingCreatorDTO;
import com.spring5legacy.mypro00.common.paging.MyBoardPagingDTO;
import com.spring5legacy.mypro00.domain.MyBoardAttachFileVO;
import com.spring5legacy.mypro00.domain.MyBoardVO;
import com.spring5legacy.mypro00.mapper.MyBoardAttachFileMapper;
import com.spring5legacy.mypro00.mapper.MyBoardMapper;
import com.spring5legacy.mypro00.mapper.MyReplyMapper;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class MyBoardServiceImpl implements MyBoardService {

    private final MyBoardMapper myBoardMapper;
    private final MyBoardAttachFileMapper myBoardAttachFileMapper;
    private MyReplyMapper myReplyMapper;

//    @Override
//    public List<MyBoardVO> getBoardList(MyBoardPagingDTO myBoardPaging) {
//        System.out.println("서비스목록 조회서비스 시작");
//        
//        Long rowTotal = myBoardMapper.selectRowTotal();
//        
//        return myBoardMapper.selectMyBoardList(myBoardPaging);
//    }
    
    @Override
    public MyBoardPagingCreatorDTO getBoardList(MyBoardPagingDTO myBoardPaging) {

    	System.out.println("서비스목록 조회서비스 시작");
	    return new MyBoardPagingCreatorDTO(myBoardMapper.selectRowTotal(myBoardPaging), 
                                          myBoardPaging,
                                          myBoardMapper.selectMyBoardList(myBoardPaging)) ;
	  
    }

//    //게시물 등록 서비스
//    @Override
//    public Long registerBoard(MyBoardVO myBoard) {
//        System.out.println("서비스: 게시를 등록 메서드에 전달된 MyBoardVO: " + myBoard);
//        myBoardMapper.insertMyBoard(myBoard);
//        System.out.println("서비스: 게시를 등록 후 MyBoardVO: " + myBoard);
//        return myBoard.getBno();
//    }
    
    //게시물 등록 서비스 + 첨부파일 고려
    @Override
    @Transactional
    public Long registerBoard(MyBoardVO myBoard) {
        System.out.println("서비스: 게시를 등록 메서드에 전달된 MyBoardVO: " + myBoard);
        myBoardMapper.insertMyBoard(myBoard);
        System.out.println("서비스: 게시를 등록 후 MyBoardVO: " + myBoard);
        
        //첨부파일 등록
        List<MyBoardAttachFileVO> attachFileList = myBoard.getAttachFileList() ;
        
        if (attachFileList != null && attachFileList.size() > 0) {
        	for(MyBoardAttachFileVO attachFile : attachFileList) {
        		attachFile.setBno(myBoard.getBno());
        		myBoardAttachFileMapper.insertAttachFile(attachFile);
        	}
        }
        
        return myBoard.getBno();
    }

    @Override
    public MyBoardVO getBoard(Long bno) {
        System.out.println("서비스: 게시를 조회 메서드에 전달된 bno: " + bno);
        MyBoardVO myBoard = myBoardMapper.selectMyBoard(bno); 
	    myBoardMapper.updateBviewsCnt(bno);
	    return myBoard;
    }
    


    @Override
    public MyBoardVO getBoardToAfterModify(Long bno) {
    	System.out.println("서비스: 게시를 조회 메서드에 전달된 bno: " + bno);
    	return myBoardMapper.selectMyBoard(bno);
    }

    //게시물 수정 서비스
    @Override
    public boolean modifyBoard(Long bno, String btitle, String bcontent) {
    	System.out.println("서비스: 게시물 수정 메서드에 전달된 값들: " + bno + ", " + btitle + ", " + bcontent);
        return myBoardMapper.updateMyBoard(bno, btitle, bcontent) == 1;
    }

  //게시물 수정 서비스
    @Override
    @Transactional
    public boolean modifyBoard (MyBoardVO myBoard) {
	    System.out.println("서비스: 게시물 수정 메서드에 전달된 VO: + myBoard");
	    long bno = myBoard.getBno();
	    boolean boardModifyResult = myBoardMapper.updateMyBoard(myBoard) == 1;
	    
	    myBoardAttachFileMapper.deleteAttachFiles(bno);
	    List<MyBoardAttachFileVO> attachFileList = myBoard.getAttachFileList();
	    
	    if(boardModifyResult && attachFileList != null) {
	    	for(MyBoardAttachFileVO attachFile : attachFileList) {
	    		attachFile.setBno(bno);
	    		myBoardAttachFileMapper.insertAttachFile(attachFile);
	    	}
	    }
	    
	    return boardModifyResult;
    }

  //게시물 삭제 서비스: 블라인드 처리
    @Override
    public boolean setBoardDeleted(Long bno) {
        System.out.println("서비스: 게시물 이제 메서드(블라인드)에 전달된 bno: " + bno);
        System.out.println("service::: 블라인드된 첨부파일 갯수:" + myBoardAttachFileMapper.updateSetFileDelFlag(bno));
        return myBoardMapper.updateBdelFlag(bno) == 1;
    }

  //게시물 삭제 서비스: 실제 삭제
    @Override
    public boolean removeBoard(Long bno) {
        System.out.println("서비스: 게시물 이제 메서드(실제식제)에 전달된 bno:" + bno);
        
        List<MyBoardAttachFileVO> attachFileList = myBoardAttachFileMapper.selectAttachFiles(bno);
        
        System.out.println("service:::DB로부터 삭제된 파일 정보 갯수: " + myBoardAttachFileMapper.deleteAttachFiles(bno)); //DB정보 삭제
        int removeBoardCnt = myBoardMapper.deleteMyBoard(bno);
        
        System.out.println("service:::삭제된 파일 갯수: " + removeAttachFiles(attachFileList)); //서버 파일 삭제

        return removeBoardCnt == 1;
    }
    
    
    private int removeAttachFiles (List<MyBoardAttachFileVO> attachFileList) {
    	if (attachFileList == null || attachFileList.size() == 0) {
    	return 0;
    	}
    	Path filePath = null;
    	Path thumbnail = null;
    	
    	int deletedFileCnt = 0;
    	System.out.println("service::: 삭제시작 삭제파일 목록 ===========");
    	
    	for(MyBoardAttachFileVO attachFile : attachFileList) {
    		
    		filePath = Paths.get(attachFile.getRepoPath(), attachFile.getUploadPath(), attachFile.getUuid()+ "_" + attachFile.getFileName());
    		
    		try {
				if(Files.deleteIfExists(filePath)) {
					System.out.println(filePath.toString() + "파일 삭제 됨...");
					deletedFileCnt+=1;
				} else {
					System.out.println(filePath.toString() + "파일이 존재하지 않음...");
				}
				
				if(attachFile.getFileType().equals("I")) {
	    			thumbnail = Paths.get(attachFile.getRepoPath(), attachFile.getUploadPath(), "s_" + attachFile.getUuid()+ "_" + attachFile.getFileName());
	    			
	    			Files.deleteIfExists(thumbnail);
	    		}
			} catch (IOException e) {
				e.getMessage();
			}
    		

    	} 
    	
    	System.out.println("========================================");
    	
    	
    	return deletedFileCnt;
    	
    	}
    
    @Override
    @Transactional
    public boolean removeBoardAndReplies(Long bno) {
        // 게시물에 달린 모든 댓글 및 답글 삭제
        myReplyMapper.deleteRepliesByBoard(bno);
        // 게시물 삭제
        return myBoardMapper.deleteMyBoard(bno) == 1;
    }
}
