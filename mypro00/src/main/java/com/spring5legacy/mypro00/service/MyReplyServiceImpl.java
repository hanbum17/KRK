package com.spring5legacy.mypro00.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring5legacy.mypro00.common.paging.MyReplyPagingCreatorDTO;
import com.spring5legacy.mypro00.common.paging.MyReplyPagingDTO;
import com.spring5legacy.mypro00.domain.MyReplyVO;
import com.spring5legacy.mypro00.mapper.MyBoardMapper;
import com.spring5legacy.mypro00.mapper.MyReplyMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MyReplyServiceImpl implements MyReplyService{
	
	 private MyReplyMapper myReplyMapper ;
	 private MyBoardMapper myBoardMapper ;

//특정 게시물에 대한 댓글/답글 목록 조회
//@Override
//public List<MyReplyVO> getReplyList(MyReplyPagingDTO myreplyPaging) {
//    return myReplyMapper.selectReplyList(myreplyPaging) ;
//}
	
//특정 게시물에 대한 댓글 목록 조회
@Override
public MyReplyPagingCreatorDTO getReplyList(MyReplyPagingDTO myReplyPaging) {
    Long rowTotal = myReplyMapper.selectReplyTotal(myReplyPaging) ;
    List<MyReplyVO> myReplyList = myReplyMapper.selectReplyList(myReplyPaging) ;
	
    MyReplyPagingCreatorDTO myReplyPagingCreator = new MyReplyPagingCreatorDTO(myReplyPaging, rowTotal, myReplyList) ;
    return myReplyPagingCreator ;
}
    

//댓글/답글 등록
@Override
@Transactional
public Long registerReply(MyReplyVO myReply) {
    myReplyMapper.insertReply(myReply) ;
    myBoardMapper.updateBreplyCnt(myReply.getBno(), 1);
    return myReply.getRno() ;
}


//특정 게시물에 대한 특정 댓글/답글 수정
@Override
public Boolean modifyReply(MyReplyVO myreply) {
    return myReplyMapper.updateReply(myreply) == 1 ;

}

//특정 게시물에 대한 특정 댓글/답글 블라인드 처리
@Override
@Transactional
public Boolean blindReply(Long bno, Long rno) {
	
	int blindedRowCnt = myReplyMapper.updateRdelFlag(bno, rno) ;
	
	myBoardMapper.updateBreplyCnt(bno, -1);
	
	return blindedRowCnt == 1 ;

}


//특정 게시물에 대한 특정 댓글/답글 및 자식 답글 모드 삭제
@Override
@Transactional
public String removeReply(Long bno, Long rno) {
  Integer deletedRowCnt = myReplyMapper.deleteReply(bno, rno) ;
  System.out.println("service:::삭제된 댓글-답글(자식댓글 포함) 수: " + deletedRowCnt);
  String _deletedRowCnt = null ;
  if (deletedRowCnt != null) {
      _deletedRowCnt = String.valueOf(deletedRowCnt) ;
      myBoardMapper.updateBreplyCnt(bno, -deletedRowCnt);
  }
  return _deletedRowCnt ;
}

//
////특정 게시물에 대한 모든 댓글 삭제: 삭제 행수가 반환됨
//@Override
//public int removeAllMyReply(long bno) {
//	return myReplyMapper.deleteAllReply(bno) ;
//}

////특정 게시물에 대한 특정 댓글/답글 조회
//@Override
//public MyReplyVO getReply(Long bno, Long rno) {
//return myReplyMapper.selectReply(bno, rno) ;
//}

}
