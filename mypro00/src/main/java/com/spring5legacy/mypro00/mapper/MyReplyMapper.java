package com.spring5legacy.mypro00.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring5legacy.mypro00.common.paging.MyReplyPagingDTO;
import com.spring5legacy.mypro00.domain.MyReplyVO;

public interface MyReplyMapper {

    //특정 게시물에 대한 댓글 목록 조회
//    public List<MyReplyVO> selectReplyList(Long bno);   //Recursive With문
//    public List<MyReplyVO> selectMyReplyList(Long bno); //오라클 계층쿼리
	
    //특정 게시물에 대한 댓글 목록 조회: 페이징 고려
    public List<MyReplyVO> selectReplyList(MyReplyPagingDTO myreplyPaging);   //Recursive With문
    public List<MyReplyVO> selectMyReplyList(MyReplyPagingDTO myreplyPaging); //오라클 계층쿼리
    
    //특정 게시물에 대한 댓글 총 개수
    //public Long selectReplyTotal(Long bno) ;
    public Long selectReplyTotal(@Param("myReplyPagingDTO") MyReplyPagingDTO myReplyPagingDTO);    
    
    //댓글/답글 등록
    public Long insertReply(MyReplyVO myReply) ;

    //특정 게시물에 대한 특정 댓글/답글 수정
    public Integer updateReply(MyReplyVO myreply) ;


    //특정 게시물에 대한 특정 댓글/답글 삭제: 블라인드 처리
    public Integer updateRdelFlag(@Param("bno") Long bno, @Param("rno") Long rno) ;

    //특정 게시물에 대한 특정 댓글/답글 삭제
    public Integer deleteReply(@Param("bno") Long bno, @Param("rno") Long rno) ;

    //특정 게시물에 대한 모든 댓글 삭제
    public Integer deleteAllReply(Long bno) ;
    
//특정 게시물에 대한 특정 댓글/답글 조회
//public MyReplyVO selectReply(@Param("bno") Long bno, @Param("rno") Long rno) ;
}
