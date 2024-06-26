package com.spring5legacy.mypro00;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.spring5legacy.mypro00.domain.MyBoardVO;
import com.spring5legacy.mypro00.service.MyBoardService;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class) //
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/mybatis-context.xml",
					   "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class Test04MyBoardServiceTest {
	
    //MyBoardService가 주입되도록 구현
    private MyBoardService myBoardService ;
    
    @Autowired
    public void setMyBoardService(MyBoardService myBoardService) {
        this.myBoardService = myBoardService ;
    }
	
//    JUnit은, 생성자 주입을 사용하면 오류가 발생됩니다.(JUnit 특징)
//    따라서, JUnit에서는 setter를 이용하여 테스트 객체를 주입해야 합니다.    
//    private MyBoardService myBoardService;
//    public Test04MyBoardServiceTest(MyBoardService myBoardService) {
//        this.myBoardService = myBoardService ;
//    } 
//    발생 오류: java.lang.Exception: 
//    Test class should have exactly one public zero-argument constructor

//    //MyBoardService 빈 주입 확인 테스트
//    @Test
//    public void testMyBoardServiceExist() {
//
//        System.out.println(myBoardService);
//        assertNotNull(myBoardService); //MyBoardService 필드가 null 이면, AssertionError 예외 발생
//    }
//
//    //게시물 목록 조회 서비스 테스트
//    @Test
//    public void testGetBoardList() {
//        myBoardService.getBoardList().forEach(myBoard -> System.out.println(myBoard));
//    }

//    //게시물 등록(selectKey 이용) 테스트
//    @Test
//    public void testRegisterBoard() {
//        MyBoardVO myBoard = new MyBoardVO();
//        myBoard.setBtitle("서비스 새글 입력  테스트 제목");
//        myBoard.setBcontent("서비스 새글 입력 테스트  내용");
//        myBoard.setBwriter("test");
//
//        myBoardService.registerBoard(myBoard);
//        System.out.println("등록된 게시물의 Bno: " + myBoard.getBno());
//
//    }
//
//    //게시물 수정 테스트
//    @Test
//    public void testModifyBoard() {
//        MyBoardVO myBoard = myBoardService.getBoard(1L);
//        
//        if (myBoard == null) {
//    	    return;
//        }
//        
//        myBoard.setBtitle("제목 수정:서비스 테스트");
//        System.out.println("수정된 게시물 조회 결과(boolean): " + myBoardService.modifyBoard(myBoard));
//    }
//
//    //게시물 수정 테스트
//    @Test
//    public void testModifyBoard() {
//        System.out.println("수정된 게시물 조회 결과(boolean): " 
//                          + myBoardService.modifyBoard(1L, 
//                                                       "제목 수정:서비스 테스트",
//                                                       "내용 수정:서비스 테스트"));
//    }
    
//    //게시물 조회  테스트: by bno + 조회수 증가 고려
//    @Test
//    public void testGetBoard() {
//        System.out.println(myBoardService.getBoard(1L));
//        System.out.println(myBoardService.getBoard(1L));
//        
//    }
//    
//    //특정 게시물 삭제  테스트 - 실제 삭제
//    @Test
//    public void testRemoveBoard() {
//        // 게시물 번호의 존재 여부를 확인하고 테스트할 것
//    	System.out.println("삭제 전: " + myBoardService.getBoard(6L));
//        System.out.println("서비스: 특정 게시물 삭제 테스트: " + myBoardService.removeBoard(6L));
//        System.out.println("삭제 후: " + myBoardService.getBoard(6L));
//    }

//     //게시물 삭제 요청 처리 서비스 테스트 - bdelFlag 컬럼만 1로 수정
//    @Test
//    public void testSetBoardDeleted() {
//        // 게시물 번호의 존재 여부를 확인하고 테스트할 것
//        System.out.println("수행결과: " + myBoardService.setBoardDeleted(7L));
//        System.out.println("수행결과: " + myBoardService.setBoardDeleted(8L));
//        System.out.println(myBoardService.getBoard(7L));
//        System.out.println(myBoardService.getBoard(8L));
//    }

}
