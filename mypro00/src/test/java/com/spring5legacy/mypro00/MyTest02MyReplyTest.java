package com.spring5legacy.mypro00;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;
import com.spring5legacy.mypro00.domain.MyReplyVO;
import com.spring5legacy.mypro00.mapper.MyReplyMapper;
//테스트 메소드를 실행하는 어노테이션
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration    //DispatcherServlet의 servlet-context.xml 설정 구성파일(들)을 사용하기 위한 어노테이션
@ContextConfiguration({	"file:src/main/webapp/WEB-INF/spring/mybatis-context.xml",
                        "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
public class MyTest02MyReplyTest {
//컨트롤러 테스트 시작//////////////////////////////////////////////////////////////////////////
    //컨트롤러 테스트 환경 구성 시작, 
    //컨트롤러 테스트를 위해서는 WebApplicationContext 를 객체로 주입 받아야 함
    private WebApplicationContext ctx;
    
    @Autowired
    public void setCtx (WebApplicationContext ctx) {
    	this.ctx = ctx ;
    }
    //테스트 환경 객체를 담을 필드
    private MockMvc mockMvc ;
    
    //테스트 전에 테스트 객체를 생성하여 테스트 환경 구성
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
    } //테스트 환경 구성-끝

    
//    //게시물에 대한 댓글 페이징 목록 조회 테스트: GET /replies/list/{bno}/{page}
//    @Test
//    public void testShowReplyList() throws Exception {
//        int resultStatus = mockMvc.perform(get("/replies/list/229742/1")
//                                              .accept("application/json; charset=utf-8")  //서버로부터 JSON 형식으로 데이터를 받음
//                                              .characterEncoding("utf-8"))      //서버로 보내는 데이터에 대한 인코딩을 UTF-8로 지정
//                                     .andDo(print())// 콘솔 출력
//                                     .andReturn().getResponse().getStatus();
//        System.out.println("controller:::웹브라우저에 전달된 상태 코드(resultStatus): " + resultStatus);
//    }

    
    
    //게시물에 대한 댓글 등록(rno 반환):POST /replies/{bno}/new 
    //[참고] 브라우저에서 JSON으로 보내는 것처럼 테스트 코드 작성
    @Test
    public void testRegisterComment() throws Exception {
    	//테스트 환경에서 브라우저가 Json 형식으로 보낼 데이터 준비
    	MyReplyVO myReply = new MyReplyVO();
        myReply.setBno(229742L);
        myReply.setRwriter("user2");
        myReply.setRcontent("컨트롤러-댓글등록 테스트");
        myReply.setPrno(null);

        String myReplyJsonStr = new Gson().toJson(myReply);

        int resultStatus
                = mockMvc.perform(post("/replies/229742/new")
                                   .accept("text/plain; charset=UTF-8")
                                   .contentType("application/json; charset=UTF-8")
                                   .characterEncoding("utf-8")
                                   .content(myReplyJsonStr) )
                          .andDo(print()).andReturn().getResponse().getStatus();
        System.out.println("controller:::웹브라우저에 전달된 상태 코드(resultStatus): " + resultStatus);
    }
    
  @Test
  public void testRegisterReplyForBoard() throws Exception {
      //테스트 환경에서 브라우저가 Json으로 보낼 데이터 준비
      MyReplyVO myReply = new MyReplyVO();
      myReply.setBno(229742L);
      myReply.setRcontent("컨트롤러-게시물에 대한 댓글 등록 테스트: JSON입력테스트");
      myReply.setRwriter("testJson");
      myReply.setRwriter("testJson");
      //myReply.setPrno(82L);  //부모댓글의 rno를 받아와야 함
      String myReplyJsonStr = new Gson().toJson(myReply);  //myReply 객체를 JSON 문자열로로 변환
      System.out.println("controller:::게시물에 대한 댓글 등록 테스트: 준비된 JsonStr: " + myReplyJsonStr);
    
      int resultStatus
          = mockMvc.perform(post("/replies/229762/new")
                              .accept("text/plain; charset=UTF-8")  //서버가 보내는 것이 String 이어야 함
                              .contentType("application/json; charset=UTF-8") //서버로 보내는 데이터가 JSON 임을 header에 명시
                              .characterEncoding("utf-8")  //서버로 보내는 데이터에 대한 인코딩 설정.
                              .content(myReplyJsonStr))   //테스트에 처리되는 데이터
                    .andDo(print()).andReturn().getResponse().getStatus();
      System.out.println("controller:::웹브라우저에 전달된 상태 코드(resultStatus): " + resultStatus);
  }

  
  
    
//컨트롤러 테스트 끝//////////////////////////////////////////////////////////////////////////////
	
	
	
//댓글/답글 메퍼 테스트 시작//////////////////////////////////////////////////////////////////////// 	
	private MyReplyMapper myReplyMapper;

    @Autowired
    public void setMyReplyMapper(MyReplyMapper myReplyMapper) {
	       this.myReplyMapper = myReplyMapper;
	       System.out.println("MyReplyMapper 주입됨");
    }
    
//  //특정 게시물에 대한 댓글 목록 조회 테스트 
//  @Test
//  public void testSelectMyReplyList() {
//
//      myReplyMapper.selectMyReplyList(229742L)
//      			 .forEach(myReply -> System.out.println(myReply));
//  }
    
//    //특정 게시물에 대한 댓글 목록 조회 테스트 
//    @Test
//    public void testSelectReplyList() {
//
//        myReplyMapper.selectReplyList(229742L)
//        			 .forEach(myReply -> System.out.println(myReply));
//    }
    
////특정 게시물에 대한 댓글 페이징 목록 조회 테스트 
//@Test
//public void testSelectMyReplyList() {
//    myReplyMapper.selectMyReplyList(new MyReplyPagingDTO(229742L, 1)).forEach(myReply -> System.out.println(myReply));
//}
//    
////특정 게시물에 대한 댓글 페이징 목록 조회 테스트 
//@Test
//public void testSelectReplyList() {
//    myReplyMapper.selectReplyList(new MyReplyPagingDTO(229742L, 1)).forEach(myReply -> System.out.println(myReply));
//}

//특정 게시물에 대한 댓글 페이징 목록 조회 테스트 
//@Test
//public void testSelectReplyTotal() {
//    System.out.println("댓글/답글 개수: " + myReplyMapper.selectReplyTotal(new MyReplyPagingDTO(229742L, 1)));
//}

    
  
    
  
//  //특정 게시물에 대한 댓글 등록 테스트
//  @Test
//  public void testInsertMyReplyForBoard() {
//      MyReplyVO myReply = new MyReplyVO();
//      myReply.setBno(229571L);   // <- 댓글이 없는 게시물의 bno를 지정
//      myReply.setRcontent("매퍼 댓글 입력 테스트 ");
//      myReply.setRwriter("user9");
//      System.out.println("DB INSERT 전 댓글 객체: " + myReply);
//      myReplyMapper.insertMyReplyForBoard(myReply); 
//      System.out.println("DB INSERT 후 댓글 객체: " + myReply);
//  }
  
//  //댓글의 답글 등록 테스트, 댓글 등록 테스트에서 새로 입력된 댓글에 답글 등록
//  @Test
//  public void testInsertMyReplyForReply() {
//      MyReplyVO myReply = new MyReplyVO();
//      myReply.setBno(229571L); // <- 댓글 등록 테스트에 사용한 게시물의 bno를 동일하게 지정
//      myReply.setRcontent("매퍼 댓글의 답글 입력 테스트 ");
//      myReply.setRwriter("user9");
//      myReply.setPrno(41L); // <- 댓글 등록 테스트로 입력된 댓글의 rno를 지정
//      System.out.println("DB INSERT 전 myReply: " + myReply);
//      myReplyMapper.insertMyReplyForReply(myReply); 
//      System.out.println("DB INSERT 후 myReply: " + myReply);
//  }

//  //특정 댓글/답글 조회 테스트, 답글 등록 테스트에서 새로 입력된 답글 조회
//  @Test
//  public void testSelectMyReply() {
//
//      MyReplyVO myReply = myReplyMapper.selectMyReply(229571L, 42L); //(bno, rno) 순서로 지정, 
//      System.out.println("특정 댓글/답글 조회 테스트: " + myReply);
//  }



  
//  //특정 댓글 수정 테스트, 답글 조회 테스트에서 사용한 답글 수정
//  @Test
//  public void testUpdateMyReply() {
//      MyReplyVO myReply = myReplyMapper.selectMyReply(229571L, 42L);
//      System.out.println("수정 전 댓글: " + myReply);
//      myReply.setRcontent("매퍼 - 수정 테스트..");
//      Integer count = myReplyMapper.updateMyReply(myReply); 
//      System.out.println("UPDATE ROW COUNT: " + count);
//      System.out.println("수정 후 댓글: " + myReplyMapper.selectMyReply(229571L, 42L));
//  }



//  //특정 게시물에 대한 특정 댓글 삭제(블라인드) 테스트, 댓글/답글 수정 테스트에서 사용한 답글 블라인드
//  @Test
//  public void testUpdateRdelFlag() {
//      int count = myReplyMapper.updateRdelFlag(229571L, 42L); 
//      System.out.println("DELETE COUNT: " + count); 
//      System.out.println("블라인드 처리 후 reply: " + myReplyMapper.selectMyReply(229571L, 42L));
//  }

//  //특정 게시물에 대한 특정 댓글 삭제 테스트, 댓글/답글 블라인드 테스트에서 사용한 답글 삭제
//  @Test
//  public void testDeleteReply() {
//      int count = myReplyMapper.deleteReply(229571L, 42L); 
//      System.out.println("DELETE COUNT: " + count); 
//      System.out.println("블라인드 처리 후 reply: " + myReplyMapper.selectMyReply(229571L, 42L));
//  }

//  //특정 게시물에 대한 특정 댓글 삭제 테스트, 특정 댓글/답글 삭제 테스트에서 사용한 게시물의 bno를 이용
//  @Test
//  public void testDeleteReplys() {
//      int count = myReplyMapper.deleteReplys(229571L); 
//      System.out.println("DELETE COUNT: " + count); 
//  }

  
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
  
  
  
//  //특정 게시물에 대한 특정 댓글 삭제 테스트
//  @Test
//  public void testDeleteMyReply() {
//  	
//      Long targetBno = 229381L; //SQL Developer에서 확인된 가장 최신 게시물의 bno를 지정.
//      Long targetRno = 4L;
//      int count = myReplyMapper.deleteMyReply(targetBno, targetRno);
//      log.info("DELETE COUNT: " + count);
//      log.info(myReplyMapper.selectMyReply(targetBno, targetRno));
//  }

////매퍼 인스턴스 생성 테스트
//@Test
//public void testMapper() {
//    System.out.println(myReplyMapper);
//}
  
////특정 게시물에 대한 댓글 목록 조회 테스트
//@Test
//public void testSelectMyReplyList() {
//    List<MyReplyVO> myReplies = myReplyMapper.selectReplyList(229570L);  // <- 테스트 댓글이 있는 게시물의 bno 지정
//    myReplies.forEach(myReply -> System.out.println(myReply));
//}
    

    
    
    
    
    
//Mybatis Session Test	
	private SqlSessionTemplate sqlSession ;
//	
//	@Autowired
//	public void setSqlSession(SqlSessionTemplate sqlSession) {
//		this.sqlSession = sqlSession ;
//		System.out.println(this.sqlSession);
//		
//	}
//	
//	@Test
//	public void testMyBatisSession() {
//		
//		try(Connection con = sqlSession.getConnection()){
//			System.out.println(con);
//			System.out.println(sqlSession);
//			//sqlSession.close(); //Manual close is not allowed over a Spring managed SqlSession
//		} catch(Exception e) {
//			fail(e.getMessage());
//		}
//		
//	}

}
