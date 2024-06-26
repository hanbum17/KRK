package com.spring5legacy.mypro00.service;

import com.spring5legacy.mypro00.common.paging.MyReplyPagingCreatorDTO;
import com.spring5legacy.mypro00.common.paging.MyReplyPagingDTO;
import com.spring5legacy.mypro00.domain.MyReplyVO;

public interface MyReplyService {
//댓글/답글 목록
//public List<MyReplyVO> getReplyList(MyReplyPagingDTO myReplyPaging) ;
public MyReplyPagingCreatorDTO getReplyList(MyReplyPagingDTO myReplyPaging) ;

public Long registerReply(MyReplyVO myReply) ;

//특정 게시물에 대한 특정 댓글/답글 조회(사용하지 않음)
//public MyReplyVO getReply(Long bno, Long rno) ;

//특정 게시물에 대한 특정 댓글/답글 수정
public Boolean modifyReply(MyReplyVO myreply);


//특정 게시물에 대한 특정 댓글/답글 블라인드 처리
public Boolean blindReply(Long bno, Long rno) ;

//특정 게시물에 대한 특정 댓글/답글 및 자식 답글 모드 삭제
public String removeReply(Long bno, Long rno) ;


////특정 게시물에 대한 모든 댓글 삭제: 삭제 행수가 반환됨
//public int removeAllMyReply(long bno) ;

}
