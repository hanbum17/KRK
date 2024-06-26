package com.spring5legacy.mypro00.service;

import java.util.List;

import com.spring5legacy.mypro00.common.paging.MyBoardPagingCreatorDTO;
import com.spring5legacy.mypro00.common.paging.MyBoardPagingDTO;
import com.spring5legacy.mypro00.domain.MyBoardVO;

public interface MyBoardService {
	
	//게시물 조회 - 목록
	public MyBoardPagingCreatorDTO getBoardList(MyBoardPagingDTO myBoardPaging) ;
	
	//게시물 등록 - selectKey 이용
	public Long registerBoard(MyBoardVO myBoard) ;
	
	//특정 게시물 조회 + 조회수가 증가됨
	public MyBoardVO getBoard(Long bno) ;
	
	//특정 게시물 조회 + 조회수가 증가 않됨
	public MyBoardVO getBoardToAfterModify(Long bno) ;
	
	//특정 게시물 수정 
	public boolean modifyBoard(Long bno, String btitle, String bcontent) ;
	public boolean modifyBoard(MyBoardVO myBoard) ;
	
	//특정 게시물 삭제 요청 - 해당 글의 bdelFlag을 1로 수정
	public boolean setBoardDeleted(Long bno) ;
	
	//특정 게시물 삭제 - 실제 삭제 
	//public boolean removeBoard(Long bno) ;
	public MyBoardVO removeBoard(MyBoardVO myBoard) ; ; //댓글-답글 삭제 고려 시
	
}
