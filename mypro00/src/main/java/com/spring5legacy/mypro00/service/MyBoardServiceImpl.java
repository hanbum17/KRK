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
@AllArgsConstructor //생성자 주입에서 사용됨
public class MyBoardServiceImpl implements MyBoardService {
	
	private MyBoardMapper myBoardMapper ;
	private MyBoardAttachFileMapper myBoardAttachFileMapper ;
	private MyReplyMapper myReplyMapper ;
	//private MyBoardMapperHr myBoardMapperHr ;
	
	
	//게시물 목록 조회 서비스1
//	@Override
//	public MyBoardPagingCreatorDTO getBoardList(MyBoardPagingDTO myBoardPaging) {
//		System.out.println("서비스: 목록 조회 서비스");
//		
//		return new MyBoardPagingCreatorDTO(myBoardPaging, 
//                myBoardMapper.selectRowTotal(),
//                myBoardMapper.selectMyBoardList(myBoardPaging)) ;
//	}
	
	@Override
	   public MyBoardPagingCreatorDTO getBoardList(MyBoardPagingDTO myBoardPaging) {
	      /* System.out.println("게시물 목록 조회 : "); */
//	      List<MyBoardVO> myBoardList = myBoardMapper.selectMyBoardList();
//	      return myBoardList;
	      
	      // 게시물 총 수 : 
//	      Long rowTotal = myBoardMapper.selectRowTotal();
//	      System.out.println("서비스: " + rowTotal);
//	      List<MyBoardVO> myBoardList = myBoardMapper.selectMyBoardList(myBoardPaging) ;
//	      System.out.println("myBoardList: " + myBoardList);
//	      MyBoardPagingCreatorDTO pagingCreator = new MyBoardPagingCreatorDTO(myBoardPaging, rowTotal, myBoardList);
//	      System.out.println("service:::myBoardPaging :" + myBoardPaging);
//	      System.out.println("service:::pagingCreator" + pagingCreator);
//	      return pagingCreator;
	      
	      
//	      return new MyBoardPagingCreatorDTO(myBoardPaging, 
//	                                 myBoardMapper.selectRowTotal(myBoardPaging), 
//	                                 myBoardMapper.selectMyBoardList(myBoardPaging));
		
//		String beginDate = myBoardPaging.getBeginDate() ;
//		String endDate = myBoardPaging.getEndDate() ;
//		
//		//System.out.println("beginDate: " + beginDate);
//		//System.out.println("endDate: " + endDate);
//		
//		Date _endDate = null ;
//		Calendar myCal = null ;
//		
//		if((beginDate != null && beginDate.length() != 0) 
//				&& (endDate != null && endDate.length() != 0)) {
//				
//				SimpleDateFormat myDateFmt = new SimpleDateFormat("yyyy-MM-dd");
//				try {
//					_endDate = myDateFmt.parse(endDate);//Parses text from the beginning of the given string to produce a date
//					myCal = Calendar.getInstance() ;
//					myCal.setTime(_endDate); 			//Sets this Calendar's time with the given Date
//					
//					myCal.add(Calendar.DAY_OF_MONTH, 1);
//					
//					endDate = myDateFmt.format(myCal.getTime()) ; //문자열로 변환
//					System.out.println("변환 후 endDate: " + endDate);
//					
//				} catch (ParseException e) {
//					e.printStackTrace();
//				}
//				
//				myBoardPaging.setEndDate(endDate);
//		}
		
		return new MyBoardPagingCreatorDTO(myBoardPaging,
				myBoardMapper.selectRowTotal(myBoardPaging), 
				myBoardMapper.selectMyBoardList(myBoardPaging)) ;
	      
//	      return new MyBoardPagingCreatorDTO(myBoardPaging, 
//                  myBoardMapper.selectRowTotal2(myBoardPaging), 
//                  myBoardMapper.selectMyBoardList2(myBoardPaging));
	   }

//	//게시물 등록 서비스
//	@Override
//	public Long registerBoard(MyBoardVO myBoard) {
//		System.out.println("서비스: 게시물 등록 메서드에 전달된 MyBoardVO: " + myBoard);
//		myBoardMapper.insertMyBoard(myBoard) ;
//		System.out.println("서비스: 게시물 등록 후 MyBoardVO: " + myBoard);
//		return myBoard.getBno();
//	}
	
	//게시물 등록 서비스 + 첨부파일 고려
	@Override
	@Transactional
	public Long registerBoard(MyBoardVO myBoard) {
		System.out.println("서비스: 게시물 등록 메서드에 전달된 MyBoardVO: " + myBoard);
		myBoardMapper.insertMyBoard(myBoard) ;
		System.out.println("서비스: 게시물 등록 후 MyBoardVO: " + myBoard);
		
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

	//게시물 조회 서비스: 조회수 증가 고려
	@Override
	public MyBoardVO getBoard(Long bno) {
		System.out.println("서비스: 게시물 조회 메서드에 전달된 bno: " + bno);
		myBoardMapper.updateBviewsCnt(bno);
		MyBoardVO myBoard = myBoardMapper.selectMyBoard(bno) ;
		
		return myBoard;
	}
	
	//게시물 조회 서비스: 조회수가 증가 되면 않됨
    //게시물 조회 페이지 -> 게시물 수정 페이지로 이동(by bno), 조회수 변화 없음
    //게시물 수정 후 -> 게시물 조회 페이지 호출(by bno), 조회수 증가 없음
    @Override
	public MyBoardVO getBoardToAfterModify(Long bno) {
		System.out.println("서비스: 게시물 조회 메서드에 전달된 bno: " + bno);
		MyBoardVO myBoard = myBoardMapper.selectMyBoard(bno) ;
		return myBoard;
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
	public boolean modifyBoard(MyBoardVO myBoard) {
		System.out.println("서비스: 게시물 수정 메서드에 전달된 VO: " + myBoard);	
		
		long bno = myBoard.getBno() ;
		
		boolean boardModifyResult = myBoardMapper.updateMyBoard(myBoard) == 1 ;
		
		myBoardAttachFileMapper.deleteAttachFiles(bno) ;
		
		List<MyBoardAttachFileVO> attachFileList = myBoard.getAttachFileList() ;
		
		if (boardModifyResult && attachFileList != null) {
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
		System.out.println("service::: 게시물 블라인드 시에 전달된 bno: " + bno);
		System.out.println("service::: 블라인드된 첨부파일 갯수: " + myBoardAttachFileMapper.updateSetFileDelFlag(bno));
		return myBoardMapper.updateBdelFlag(bno) == 1 ;
	}

//	//게시물 삭제 서비스: 실제 삭제 (첨부파일 포함 게시물 삭제 시)
//	@Override
//	@Transactional
//	public boolean removeBoard(Long bno) {
//		System.out.println("service::: 게시물 실제삭제 시에 전달된 bno: " + bno);
//		
//		List<MyBoardAttachFileVO> attachFileList = myBoardAttachFileMapper.selectAttachFiles(bno) ;
//		
//		System.out.println("service:::DB로부터 삭제된 파일 정보 갯수: " + myBoardAttachFileMapper.deleteAttachFiles(bno)) ;  //DB정보 삭제
//		int removeBoardCnt = myBoardMapper.deleteMyBoard(bno) ;
//		
//		System.out.println("service:::삭제된 파일갯수: " + removeAttachFiles(attachFileList) ); //서버 파일 삭제
//
//		return removeBoardCnt == 1;
//	}
	
	//게시물 삭제 서비스: 실제 삭제 (첨부파일 및 모든 자식 댓글-답글 포함 게시물 삭제 시)
	@Override
	@Transactional
	public MyBoardVO removeBoard(MyBoardVO myBoard) {
        Long bno = myBoard.getBno() ;
        System.out.println("service::: 게시물 실제삭제 시에 전달된 bno: " + bno);

        List<MyBoardAttachFileVO> attachFileList = myBoardAttachFileMapper.selectAttachFiles(bno) ;

        Integer deletedAttachFileCnt =  myBoardAttachFileMapper.deleteAttachFiles(bno) ;  //첨부파일 정보 삭제
        System.out.println("service:::DB로부터 삭제된 파일 정보 갯수: " + deletedAttachFileCnt) ;

        Integer deletedReplyCnt = myReplyMapper.deleteAllReply(bno) ;  //댓글-답글 정보 삭제
        System.out.println("service:::DB로부터 삭제된 자식 댓글-답글 갯수: " + deletedReplyCnt) ;
        if(myBoardMapper.deleteMyBoard(bno) == 1) {  //게시물 정보 삭제
            System.out.println("service:::삭제된 파일갯수: " + removeAttachFiles(attachFileList) ); //서버 파일 삭제
            myBoard.setDeletedAttachFileCnt(deletedAttachFileCnt);
            myBoard.setDeletedReplyCnt(deletedReplyCnt);
            System.out.println("service:::controller로 전달할 myBoard: " + myBoard);
            return myBoard ;
            
        } else {
            return null ;
        }

	}	
	
	private int removeAttachFiles(List<MyBoardAttachFileVO> attachFileList) {
		
		if (attachFileList == null || attachFileList.size() == 0) {
			return 0 ;
		}
		
		Path filePath = null ;
		Path thumbnail = null ;
		
		int deletedFileCnt = 0 ;
		System.out.println("service::: 삭제시작 - 삭제파일 목록 ====================================");
		
		for(MyBoardAttachFileVO attachFile : attachFileList) {
			
			filePath = Paths.get(attachFile.getRepoPath(), attachFile.getUploadPath(), 
                                 attachFile.getUuid() + "_" + attachFile.getFileName()) ;
			
			try {
				if (Files.deleteIfExists(filePath)) {
				    System.out.println(filePath.toString() + " 파일 삭제 됨...");
				    deletedFileCnt += 1 ;
				} else {
					System.out.println(filePath.toString() + " 파일이 존재하지 않음...");
				}
				
				if (attachFile.getFileType().equals("I")) {
					thumbnail = Paths.get(attachFile.getRepoPath(), attachFile.getUploadPath(), 
                            "s_" + attachFile.getUuid() + "_" + attachFile.getFileName()) ;
					
//					if (Files.deleteIfExists(thumbnail)) {
//					    System.out.println(thumbnail.toString() + " 파일 삭제 됨...");	
//					} else {
//						System.out.println(thumbnail.toString() + " 파일이 존재하지 않음...");
//					}
					
					Files.deleteIfExists(thumbnail);
				}
				
			} catch (IOException e) {
				e.getMessage();
			}
		}
		System.out.println("==================================================================");
		
		
		return deletedFileCnt;
	}

}
