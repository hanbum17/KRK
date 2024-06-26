package com.spring5legacy.mypro00;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.spring5legacy.mypro00.common.paging.MyReplyPagingDTO;
import com.spring5legacy.mypro00.mapper.MyReplyMapper;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/mybatis-context.xml")
public class Test03MyReplyMapperTest {

    private MyReplyMapper myReplyMapper;

    @Autowired
    public void setMyReplyMapper(MyReplyMapper myReplyMapper) {
	       this.myReplyMapper = myReplyMapper;
	       System.out.println("MyReplyMapper 주입됨");
    }


//  //특정 게시물에 대한 댓글 페이징 목록 조회 테스트 
//    @Test
//    public void testSelectMyReplyList() {
//
//        myReplyMapper.selectReplyList(new MyReplyPagingDTO(229742L, 1))
//        			 .forEach(myReply -> System.out.println(myReply));
//    }

    
    
//    //특정 게시물에 대한 댓글 등록 테스트
//    @Test
//    public void testInsertMyReplyForBoard() {
//        MyReplyVO myReply = new MyReplyVO();
//        myReply.setBno(229571L);   // <- 댓글이 없는 게시물의 bno를 지정
//        myReply.setRcontent("매퍼 댓글 입력 테스트 ");
//        myReply.setRwriter("user9");
//        System.out.println("DB INSERT 전 댓글 객체: " + myReply);
//        myReplyMapper.insertMyReplyForBoard(myReply); 
//        System.out.println("DB INSERT 후 댓글 객체: " + myReply);
//    }
    
//    //댓글의 답글 등록 테스트, 댓글 등록 테스트에서 새로 입력된 댓글에 답글 등록
//    @Test
//    public void testInsertMyReplyForReply() {
//        MyReplyVO myReply = new MyReplyVO();
//        myReply.setBno(229571L); // <- 댓글 등록 테스트에 사용한 게시물의 bno를 동일하게 지정
//        myReply.setRcontent("매퍼 댓글의 답글 입력 테스트 ");
//        myReply.setRwriter("user9");
//        myReply.setPrno(41L); // <- 댓글 등록 테스트로 입력된 댓글의 rno를 지정
//        System.out.println("DB INSERT 전 myReply: " + myReply);
//        myReplyMapper.insertMyReplyForReply(myReply); 
//        System.out.println("DB INSERT 후 myReply: " + myReply);
//    }

//    //특정 댓글/답글 조회 테스트, 답글 등록 테스트에서 새로 입력된 답글 조회
//    @Test
//    public void testSelectMyReply() {
//
//        MyReplyVO myReply = myReplyMapper.selectMyReply(229571L, 42L); //(bno, rno) 순서로 지정, 
//        System.out.println("특정 댓글/답글 조회 테스트: " + myReply);
//    }



    
//    //특정 댓글 수정 테스트, 답글 조회 테스트에서 사용한 답글 수정
//    @Test
//    public void testUpdateMyReply() {
//        MyReplyVO myReply = myReplyMapper.selectMyReply(229571L, 42L);
//        System.out.println("수정 전 댓글: " + myReply);
//        myReply.setRcontent("매퍼 - 수정 테스트..");
//        Integer count = myReplyMapper.updateMyReply(myReply); 
//        System.out.println("UPDATE ROW COUNT: " + count);
//        System.out.println("수정 후 댓글: " + myReplyMapper.selectMyReply(229571L, 42L));
//    }



//    //특정 게시물에 대한 특정 댓글 삭제(블라인드) 테스트, 댓글/답글 수정 테스트에서 사용한 답글 블라인드
//    @Test
//    public void testUpdateRdelFlag() {
//        int count = myReplyMapper.updateRdelFlag(229571L, 42L); 
//        System.out.println("DELETE COUNT: " + count); 
//        System.out.println("블라인드 처리 후 reply: " + myReplyMapper.selectMyReply(229571L, 42L));
//    }

//    //특정 게시물에 대한 특정 댓글 삭제 테스트, 댓글/답글 블라인드 테스트에서 사용한 답글 삭제
//    @Test
//    public void testDeleteReply() {
//        int count = myReplyMapper.deleteReply(229571L, 42L); 
//        System.out.println("DELETE COUNT: " + count); 
//        System.out.println("블라인드 처리 후 reply: " + myReplyMapper.selectMyReply(229571L, 42L));
//    }

//    //특정 게시물에 대한 특정 댓글 삭제 테스트, 특정 댓글/답글 삭제 테스트에서 사용한 게시물의 bno를 이용
//    @Test
//    public void testDeleteReplys() {
//        int count = myReplyMapper.deleteReplys(229571L); 
//        System.out.println("DELETE COUNT: " + count); 
//    }

    
    //특정 게시물의 모든 댓글답글 삭제
//	@Test
//	public void testDeleteAllReply() {
//	  	
//		Long targetBno = 229381L; //SQL Developer에서 확인된 가장 최신 게시물의 bno를 지정.
//		  
//		int count = myReplyMapper.deleteAllReply(targetBno);
//		log.info("DELETED COUNT: " + count);
//		
//	}   
    
    
    
//    //특정 게시물에 대한 특정 댓글 삭제 테스트
//    @Test
//    public void testDeleteMyReply() {
//    	
//        Long targetBno = 229381L; //SQL Developer에서 확인된 가장 최신 게시물의 bno를 지정.
//        Long targetRno = 4L;
//        int count = myReplyMapper.deleteMyReply(targetBno, targetRno);
//        log.info("DELETE COUNT: " + count);
//        log.info(myReplyMapper.selectMyReply(targetBno, targetRno));
//    }

//  //매퍼 인스턴스 생성 테스트
//  @Test
//  public void testMapper() {
//      System.out.println(myReplyMapper);
//  }
    
//  //특정 게시물에 대한 댓글 목록 조회 테스트
//  @Test
//  public void testSelectMyReplyList() {
//      List<MyReplyVO> myReplies = myReplyMapper.selectReplyList(229570L);  // <- 테스트 댓글이 있는 게시물의 bno 지정
//      myReplies.forEach(myReply -> System.out.println(myReply));
//  }

}
