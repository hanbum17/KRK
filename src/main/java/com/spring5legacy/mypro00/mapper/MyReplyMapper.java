package com.spring5legacy.mypro00.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring5legacy.mypro00.common.paging.MyReplyPagingDTO;
import com.spring5legacy.mypro00.domain.MyReplyVO;

public interface MyReplyMapper {
    List<MyReplyVO> selectReplyList(MyReplyPagingDTO myReplyPagingDTO);
    Long selectReplyTotal(Long bno);
    Long insertComment(MyReplyVO myReply);
    Long insertReply(MyReplyVO myReply);
    int updateComment(MyReplyVO myReply);
    MyReplyVO selectReply(@Param("bno") Long bno, @Param("rno") Long rno);
    int updateBlindFlag(@Param("bno") Long bno, @Param("rno") Long rno, @Param("blindFlag") int blindFlag);
    
    void updateReplyCnt(@Param("prno") Long prno, @Param("amt") int amt);
    List<MyReplyVO> selectChildren(@Param("bno") Long bno, @Param("rno") Long rno);
    MyReplyVO selectReplyByRno(Long rno);
    int deleteComment(@Param("bno") Long bno, @Param("rno") Long rno);
    int deleteRepliesByBoard(Long bno);

}
