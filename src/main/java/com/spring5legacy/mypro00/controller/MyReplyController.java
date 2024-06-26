package com.spring5legacy.mypro00.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.spring5legacy.mypro00.common.paging.MyReplyPagingCreatorDTO;
import com.spring5legacy.mypro00.common.paging.MyReplyPagingDTO;
import com.spring5legacy.mypro00.domain.MyReplyVO;
import com.spring5legacy.mypro00.service.MyReplyService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/replies")
@AllArgsConstructor
public class MyReplyController {

    private final MyReplyService myReplyService;

    // 댓글 목록 조회
    @GetMapping(value = "/list/{bno}/{pageNum}", produces = "application/json;charset=utf-8")
    public ResponseEntity<MyReplyPagingCreatorDTO> showReplyList(@PathVariable("bno") Long bno, 
                                                                 @PathVariable("pageNum") Integer pageNum) {
        MyReplyPagingCreatorDTO myReplyPagingCreator = myReplyService.getReplyList(new MyReplyPagingDTO(bno, pageNum));
        return new ResponseEntity<>(myReplyPagingCreator, HttpStatus.OK);
    }

    // 댓글 등록
    @PostMapping(value = "/{bno}/new", consumes = "application/json;charset=utf-8", produces = "text/plain;charset=utf-8")
    public ResponseEntity<String> registerComment(@PathVariable("bno") Long bno, @RequestBody MyReplyVO myReply) {
        myReply.setBno(bno);
        Long registeredRno = myReplyService.registerComment(myReply);
        return registeredRno != null ? new ResponseEntity<>("success", HttpStatus.OK)
                                     : new ResponseEntity<>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 답글 등록
    @PostMapping(value = "/{bno}/{prno}/new", consumes = "application/json;charset=utf-8", produces = "text/plain;charset=utf-8")
    public ResponseEntity<String> registerReply(@PathVariable("bno") Long bno, @PathVariable("prno") Long prno, @RequestBody MyReplyVO myReply) {
        myReply.setBno(bno);
        myReply.setPrno(prno);
        Long registeredRno = myReplyService.registerReply(myReply);
        return registeredRno != null ? new ResponseEntity<>("success", HttpStatus.OK)
                                     : new ResponseEntity<>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 특정 댓글/답글 수정
    @PutMapping(value = "/{bno}/{rno}", consumes = "application/json;charset=utf-8", produces = "text/plain;charset=utf-8")
//    @PreAuthorize("isAuthenticated() && principal.username == #myreply.rwriter")
    public ResponseEntity<String> updateComment(@PathVariable("bno") Long bno, @PathVariable("rno") Long rno, @RequestBody MyReplyVO myReply) {
        myReply.setBno(bno);
        myReply.setRno(rno);
        int updatedCount = myReplyService.updateComment(myReply);
        return updatedCount == 1 ? new ResponseEntity<>("success", HttpStatus.OK)
                                 : new ResponseEntity<>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 특정 댓글/답글 조회
    @GetMapping(value = "/{bno}/{rno}", produces = "application/json;charset=utf-8")
    public ResponseEntity<MyReplyVO> getComment(@PathVariable("bno") Long bno, @PathVariable("rno") Long rno) {
        MyReplyVO myReply = myReplyService.getComment(bno, rno);
        return new ResponseEntity<>(myReply, HttpStatus.OK);
    }

    // 특정 댓글/답글 블라인드 처리
    @PatchMapping(value = "/{bno}/{rno}", produces = "text/plain;charset=utf-8")
    public ResponseEntity<String> blindComment(@PathVariable("bno") Long bno, @PathVariable("rno") Long rno) {
        int blindFlag = 1; // 블라인드 처리
        int updatedCount = myReplyService.blindComment(bno, rno, blindFlag);
        return updatedCount == 1 ? new ResponseEntity<>("success", HttpStatus.OK)
                                 : new ResponseEntity<>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 특정 댓글/답글 삭제
    @DeleteMapping(value = "/{bno}/{rno}", produces = "text/plain;charset=utf-8")
    public ResponseEntity<String> deleteComment(@PathVariable("bno") Long bno, @PathVariable("rno") Long rno) {
        int deletedCount = myReplyService.deleteComment(bno, rno);
        return deletedCount == 1 ? new ResponseEntity<>("success", HttpStatus.OK)
                                 : new ResponseEntity<>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
