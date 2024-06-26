package com.spring5legacy.mypro00;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.spring5legacy.mypro00.mapper.MyBoardMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/mybatis-context.xml")
public class Test03MyBoardMapperTest {
	
	private MyBoardMapper myBoardMapper ;

	@Autowired
    public void setMyBoardMapper(MyBoardMapper myBoardMapper) {
	    this.myBoardMapper = myBoardMapper;
	    System.out.println("BoardMapper 주입됨");
    }
	
//	//게시물 목록 조회테스트
//	@Test
//	public void testSelectMyBoardList() {
//		myBoardMapper.selectMyBoardList().forEach(myBoard -> System.out.println(myBoard) );
//	}
//
    //특정 게시물 조회 테스트(by bno)
    @Test
    public void testSelectMyBoard() {
        System.out.println(myBoardMapper.selectMyBoard(3000L));
    }
//
//    //게시물 등록 테스트 - selectKey 사용 안함
//	@Test
//    public void testInsertMyBoardNoKey() {
//    	MyBoardVO myBoard = new MyBoardVO() ;
//    	myBoard.setBtitle("매퍼 테스트-입력 제목-noKey");
//    	myBoard.setBcontent("매퍼 테스트-입력 내용-noKey");
//    	myBoard.setBwriter("user1");
//    	
//    	myBoardMapper.insertMyBoardNoKey(myBoard) ;
//    	System.out.println(myBoard);
//    }
//    
//    //게시물 등록 테스트 - selectKey 사용
//	@Test
//    public void testInsertMyBoard() {
//    	MyBoardVO myBoard = new MyBoardVO() ;
//    	myBoard.setBtitle("매퍼 테스트-입력 제목");
//    	myBoard.setBcontent("매퍼 테스트-입력 내용");
//    	myBoard.setBwriter("user11");
//    	
//    	System.out.println("입력 전: " + myBoard);
//    	myBoardMapper.insertMyBoard(myBoard) ;
//    	System.out.println("입력 후: " + myBoard);
//    }
//	
//	@Test
//	public void testUpdateBdelFlag() {
//		myBoardMapper.updateBdelFlag(10L) ;
//		System.out.println(myBoardMapper.selectMyBoard(10L));
//	}
//	
//	@Test
//	public void testDeleteMyBoard() {
//		myBoardMapper.deleteMyBoard(9L) ;
//		System.out.println(myBoardMapper.selectMyBoard(9L));
//	}
//	
//	@Test
//	public void testUpdateMyBoard1() {
//		myBoardMapper.updateMyBoard(1L,"매퍼 테스트-수정2 제목", "매퍼 테스트-수정2 내용") ;
//		System.out.println(myBoardMapper.selectMyBoard(1L));
//	}
//	
//	@Test
//	public void testUpdateMyBoard2() {
//		MyBoardVO myBoard = new MyBoardVO() ;
//		myBoard.setBno(1L);
//    	myBoard.setBtitle("매퍼 테스트-수정 제목");
//    	myBoard.setBcontent("매퍼 테스트-수정 내용");
//		
//		myBoardMapper.updateMyBoard(myBoard) ;
//		System.out.println(myBoardMapper.selectMyBoard(1L));
//	}
//
//	//조회수 증가
//	@Test
//	public void testUpdateBviewsCnt() {
//
//		myBoardMapper.updateBviewsCnt(1L); ;
//		System.out.println(myBoardMapper.selectMyBoard(1L));
//	}

}
