package com.spring5legacy.mypro00;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;
import com.spring5legacy.mypro00.domain.MyReplyVO;
//테스트 메소드를 실행하는 어노테이션
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration    //DispatcherServlet의 servlet-context.xml 설정 구성파일(들)을 사용하기 위한 어노테이션
@ContextConfiguration({	"file:src/main/webapp/WEB-INF/spring/mybatis-context.xml",
                        "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })

public class Test05MyReplyControllerTest {
    //테스트 환경 구성 시작, 
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
//        int resultStatus = 
//            mockMvc.perform(get("/replies/list/229742/1")
//                            .accept("application/json; charset=UTF-8")//서버가 보낸 것이 JSON 일 때만 처리
//                            .characterEncoding("utf-8"))//서버로 보내는 데이터에 대한 인코딩을 UTF-8로 지정
//                    .andDo(print())// 콘솔 출력
//                    .andReturn()
//                    .getResponse()
//                    .getStatus();
//        System.out.println("웹브라우저에 전달되는 ResponseEntiry 객체의 처리 상태 코드: " + resultStatus);
//    }

    
////게시물에 대한 댓글/답글 등록(rno 반환): POST /replies/{bno}/new
//@Test
//public void testRegisterReplyForBoard() throws Exception {
//    MyReplyVO myReply = new MyReplyVO();
//    myReply.setBno(229762L);
//    myReply.setRcontent("컨트롤러-댓글에 대한 답글 등록 테스트");
//    myReply.setRwriter("user2");
//    myReply.setPrno(null);
//    String myReplyJsonStr = new Gson().toJson(myReply); //myReply 객체를 JSON 문자열로로 변환
//
//    String result
//          = mockMvc.perform(post("/replies/229762/new")
//                             .accept("text/plain; charset=UTF-8")  //서버가 보내는 것이 String 이어야 함
//                             .contentType("application/json; charset=UTF-8") //서버로 보내는 데이터가 JSON 임을 header에 명시
//                             .characterEncoding("utf-8")  //서버로 보내는 데이터에 대한 인코딩 설정.
//                             .content(myReplyJsonStr) )   //테스트에 처리되는 데이터
//                    .andDo(print()).andReturn().getResponse().getContentAsString();
//    System.out.println("controller:::웹브라우저에 전달된 결과(result): " + result);
//}


  //게시물에 대한 댓글에 대한 답글 등록(rno 반환):POST /replies/{bno}/new 
  //[참고] 브라우저에서 JSON으로 보내는 것처럼 테스트 코드 작성
  @Test
  public void testRegisterReplyForComment() throws Exception {
      //테스트 환경에서 브라우저가 Json으로 보낼 데이터 준비
      MyReplyVO myReply = new MyReplyVO();
      myReply.setBno(229762L);
      myReply.setRcontent("컨트롤러-게시물에 대한 댓글 등록 테스트: JSON입력테스트");
      myReply.setRwriter("user3");
      myReply.setPrno(26L);
      String myReplyJsonStr = new Gson().toJson(myReply);  //myReply 객체를 JSON 문자열로로 변환
    
      String result
          = mockMvc.perform(post("/replies/229762/new")
                              .accept("text/plain; charset=UTF-8")  //서버가 보내는 것이 String 이어야 함
                              .contentType("application/json; charset=UTF-8") //서버로 보내는 데이터가 JSON 임을 header에 명시
                              .characterEncoding("utf-8")  //서버로 보내는 데이터에 대한 인코딩 설정.
                              .content(myReplyJsonStr))   //테스트에 처리되는 데이터
                    .andDo(print()).andReturn().getResponse().getContentAsString();
      System.out.println("controller:::웹브라우저에 전달된 결과(result): " + result);
  }


    
//    //게시물에 대한 특정 댓글 조회 테스트: GET /replies/{bno}/{rno}
//    @Test
//    public void testShowReply() throws Exception{
//        int resultStatus =
//                mockMvc.perform(get("/replies/229762/22")
//                                .accept("application/json; charset=UTF-8")
//                                .characterEncoding("utf-8")) //서버로 보내는 데이터에 대한 인코딩을 UTF-8로 지정
//                       .andDo(print()).andReturn().getResponse().getStatus();
//  	    System.out.println("controller:::웹브라우저에 전달된 상태 코드(resultStatus): " + resultStatus );
//    }
  
//    //게시물에 대한 특정 댓글/답글 수정 테스트 : PUT /replies/{bno}/{rno}
//    @Test
//    public void testModifyReply() throws Exception{
//        MyReplyVO myReply = new MyReplyVO() ;
//        myReply.setRno(22L);
//        myReply.setBno(229762L);
//        myReply.setRcontent("컨트롤러-댓글에 대한 답글 등록 테스트 - 댓글 수정(patch)22222");
//
//        String myReplyJsonStr = new Gson().toJson(myReply);
//        int resultStatus =
//                mockMvc.perform(put("/replies/229762/22")
//                                .accept("text/plain; charset=UTF-8")
//                                .contentType("application/json; charset=UTF-8")
//                                .content(myReplyJsonStr))
//                      .andDo(print()).andReturn().getResponse().getStatus();
//      
//        System.out.println("controller:::웹브라우저에 전달된 상태 코드(resultStatus): " + resultStatus);
//    }

    
//    //게시물에 대한 특정 댓글/답글 블라인드 처리, PATCH: /replies/{bno}/{rno}
//    @Test
//    public void testRemoveReply() throws Exception{
////        MyReplyVO myReply = new MyReplyVO() ;
////        myReply.setRno(22L);
////        myReply.setBno(229762L);
////        myReply.setRdelFlag(1);
////
////      String myReplyJsonStr = new Gson().toJson(myReply);
//        int resultStatus =
//                mockMvc.perform(patch("/replies/229762/22")
//		                	    .accept("text/plain; charset=UTF-8")
////		                	    .contentType("application/json; charset=UTF-8")
////		                	    .content(myReplyJsonStr)
//		                	    )
//                       .andDo(print()).andReturn().getResponse().getStatus();    	
//        System.out.println("controller:::웹브라우저에 전달된 상태 코드(resultStatus): " + resultStatus );
//    }

    
//    //게시물에 대한 특정 댓글/답글 삭제, DELETE: /replies/{bno}/{rno}
//    @Test
//    public void testRemoveReply() throws Exception{
////        MyReplyVO myReply = new MyReplyVO() ;
////        myReply.setRno(22L);
////        myReply.setBno(229762L);
////        myReply.setRdelFlag(1);
////
////        String myReplyJsonStr = new Gson().toJson(myReply);
//        int resultStatus =
//                mockMvc.perform(delete("/replies/229762/22")
//                               .accept("text/plain; charset=UTF-8")
////                               .contentType("application/json; charset=UTF-8")
////                               .content(myReplyJsonStr)
//                               )
//                     .andDo(print()).andReturn().getResponse().getStatus();    	
//        System.out.println("controller:::웹브라우저에 전달된 상태 코드(resultStatus): " + resultStatus );
//    }

//    //특정 게시물에 대한 모든 댓글 삭제 테스트 메서드 작성: 삭제 행수가 반환됨
//    @Test
//    public void testRemoveReplys() throws Exception{
//		int resultStatus =
//				mockMvc.perform(delete("/replies/229762")
//								.accept("text/plain; charset=UTF-8"))
//					   .andDo(print()).andReturn().getResponse().getStatus();    	
//		System.out.println("controller:::웹브라우저에 전달된 상태 코드(resultStatus): " + resultStatus );
//	}    


    
}

