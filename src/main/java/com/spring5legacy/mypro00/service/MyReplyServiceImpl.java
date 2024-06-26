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
public class MyReplyServiceImpl implements MyReplyService {

    private final MyReplyMapper myReplyMapper;
    private final MyBoardMapper myBoardMapper;

    @Override
    public MyReplyPagingCreatorDTO getReplyList(MyReplyPagingDTO myReplyPaging) {
        Long rowTotal = myReplyMapper.selectReplyTotal(myReplyPaging.getBno());
        List<MyReplyVO> myReplyList = myReplyMapper.selectReplyList(myReplyPaging);
        return new MyReplyPagingCreatorDTO(myReplyPaging, rowTotal, myReplyList);
    }

    @Override
    @Transactional
    public Long registerComment(MyReplyVO myReply) {
        myReplyMapper.insertComment(myReply);
        myBoardMapper.updateBreplyCnt(myReply.getBno(), 1);
        return myReply.getRno();
    }

    @Override
    @Transactional
    public Long registerReply(MyReplyVO myReply) {
        MyReplyVO parentReply = myReplyMapper.selectReplyByRno(myReply.getPrno());
        if (parentReply != null) {
        	myReply.setLvl(parentReply.getLvl() + 1); // 부모의 lv1 값에 1을 더함
        } else {
        	myReply.setLvl(0); // 최상위 댓글의 경우 0으로 설정
        }
        myReplyMapper.insertReply(myReply);
        myReplyMapper.updateReplyCnt(myReply.getPrno(), 1);
        return myReply.getRno();
    }

    @Override
    public int updateComment(MyReplyVO myReply) {
        return myReplyMapper.updateComment(myReply);
    }

    @Override
    public MyReplyVO getComment(Long bno, Long rno) {
        return myReplyMapper.selectReply(bno, rno);
    }

    @Override
    public int blindComment(Long bno, Long rno, int blindFlag) {
        return myReplyMapper.updateBlindFlag(bno, rno, blindFlag);
    }

    @Override
    @Transactional
    public int deleteComment(Long bno, Long rno) {
        List<MyReplyVO> children = myReplyMapper.selectChildren(bno, rno);
        for (MyReplyVO child : children) {
            deleteComment(bno, child.getRno());
        }
        myBoardMapper.updateBreplyCnt(bno, -1);
        return myReplyMapper.deleteComment(bno, rno);
    }
    

}
