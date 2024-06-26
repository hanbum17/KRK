package com.spring5legacy.mypro00.service;

import com.spring5legacy.mypro00.common.paging.MyReplyPagingCreatorDTO;
import com.spring5legacy.mypro00.common.paging.MyReplyPagingDTO;
import com.spring5legacy.mypro00.domain.MyReplyVO;

public interface MyReplyService {
    MyReplyPagingCreatorDTO getReplyList(MyReplyPagingDTO myReplyPaging);
    Long registerComment(MyReplyVO myReply);
    Long registerReply(MyReplyVO myReply);
    int updateComment(MyReplyVO myReply);
    MyReplyVO getComment(Long bno, Long rno);
    int blindComment(Long bno, Long rno, int blindFlag);
    int deleteComment(Long bno, Long rno);
}
