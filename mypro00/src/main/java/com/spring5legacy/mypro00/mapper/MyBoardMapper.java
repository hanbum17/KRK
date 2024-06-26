package com.spring5legacy.mypro00.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring5legacy.mypro00.common.paging.MyBoardPagingDTO;
import com.spring5legacy.mypro00.domain.MyBoardVO;

public interface MyBoardMapper {
    //기본 CRUD 에 해당하는 메서드 정의
    
    //게시물 조회 - 목록
    public List<MyBoardVO> selectMyBoardList(MyBoardPagingDTO myBoardPaging);
    public List<MyBoardVO> selectMyBoardList2(MyBoardPagingDTO myBoardPaging);
    
    //게시물 총 개수(검색 페이징)
    public Long selectRowTotal(MyBoardPagingDTO myBoardPaging) ;
    public Long selectRowTotal2(MyBoardPagingDTO myBoardPaging) ;

    //게시물 등록 - selectKey 이용
    public int insertMyBoard(MyBoardVO myBoard);
	
    //게시물 등록1 - selectKey 이용 않함
    public int insertMyBoardNoKey(MyBoardVO myBoard);
	
    //특정 게시물 조회 
    public MyBoardVO selectMyBoard(Long bno);
    

    //특정 게시물 수정
    public int updateMyBoard(MyBoardVO myBoard);
    public int updateMyBoard(@Param("bno") Long bno, 
    		                 @Param("btitle") String btitle, 
    		                 @Param("bcontent") String bcontent );

    //특정 게시물 삭제 요청 - 해당 글의 bdelFlag을 1로 수정 
    public int updateBdelFlag(Long bno);
	
    //특정 게시물 삭제 - 실제 삭제 
    public int deleteMyBoard(Long bno);

    
    //특정 게시물 조회수 증가
    public void updateBviewsCnt(Long bno);
    
    //특정 게시물의 댓글 수 변경
    public void updateBreplyCnt(@Param("bno") Long bno, @Param("amt") Integer amt) ;

}
