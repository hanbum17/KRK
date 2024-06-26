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
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/mybatis-context.xml",
					  "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"}) 
public class Test04MyBoardServiceTest {

	//MyBoardService가 주입되도록 구현
    
    private MyBoardService myBoardService;

    @Autowired
    public void setMyBoardService(MyBoardService myBoardService) {
		this.myBoardService = myBoardService;
		System.out.println("BoardMapper 주입됨");
    }
    
//    @Test
//    public void testGetBoardList() {
//
//        myBoardService.getBoardList().forEach(board -> System.out.println(board));
//    }

//    @Test
//    public void testRegisterBoard() {
//        MyBoardVO myBoard = new MyBoardVO();
//        myBoard.setBtitle("테스트 제목");
//        myBoard.setBcontent("테스트 내용");
//        myBoard.setBwriter("테스터");
//
//    	  myBoardService.registerBoard(myBoard);
//        System.out.println("등록된 게시물 번호: " + myBoard.getBno());
//        System.out.println("등록된 게시물: " + myBoard);
//    }

//    @Test
//    public void testGetBoard() {
//    	System.out.println(myBoardService.getBoard(1L)); 
//    	System.out.println(myBoardService.getBoard(1L));
//    }

  //게시물 수정 테스트
//  @Test
//  public void testModifyBoard() {
//      MyBoardVO myBoard = myBoardService.getBoard(1L);
//      
//      if (myBoard == null) {
//         return;
//      }
//      
//      myBoard.setBtitle("제목 수정:서비스 테스트");
//      System.out.println("수정된 게시물 조회 결과(boolean): " + myBoardService.modifyBoard(myBoard));
//  }


  //게시물 수정 테스트
//  @Test
//  public void testModifyBoard() {
//	  System.out.println("수정된 게시물 조회 결과(boolean): " 
//                      + myBoardService.modifyBoard(1L, 
//                                                   "제목 수정:서비스 테스트",
//                                                   "내용 수정:서비스 테스트"));
//  }


//    @Test
//    public void testSetBoardDeleted() {
//    	System.out.println("수행결과: "+ myBoardService.setBoardDeleted(7L));
//    	System.out.println("수행결과: " + myBoardService.setBoardDeleted (8L));
//    	System.out.println(myBoardService.getBoard(7L));
//    	System.out.println(myBoardService.getBoard(8L));
//    }

//    @Test
//    public void testRemoveBoard() {
//    	// 게시물 번호의 존재 여부를 확인하고 테스트할 것
//    	System.out.println("삭제 전:"+ myBoardService.getBoard(6L));
//    	System.out.println("서비스: 특정 게시물 삭제 테스트: "+ myBoardService.removeBoard(6L));
//    	System.out.println("삭제 후:"+ myBoardService.getBoard (6L));
//    }
	
	
}
