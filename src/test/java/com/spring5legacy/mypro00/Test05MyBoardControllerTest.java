package com.spring5legacy.mypro00;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import lombok.extern.log4j.Log4j;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration    //테스트 시, DispatcherServlet의 servlet-context.xml 설정 구성파일(들)을 사용하기 위한 어노테이션
@ContextConfiguration({	"file:src/main/webapp/WEB-INF/spring/mybatis-context.xml",
                          "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
@Log4j
public class Test05MyBoardControllerTest {

    //테스트 환경 구성 시작, 
    //컨트롤러 테스트를 위해서는 WebApplicationContext 를 객체로 주입 받아야 합니다.
    private WebApplicationContext ctx;
    
    @Autowired
    public void setCtx (WebApplicationContext ctx) {
    	this.ctx = ctx ;
    }
    
    private MockMvc mockMvc ;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
    } //테스트 환경 구성-끝


    //게시물 목록 조회 테스트
//    @Test
//    public void testShowBoardList() throws Exception {
//        log.info(mockMvc.perform(MockMvcRequestBuilders.get("/myboard/list")
////                                                       .param("pageNum", "2")    //페이징 테스트 시 추가
////                                                       .param("rowAmountPerPage", "5") //페이징 테스트 시 추가
//        		                )
//                        .andReturn()
//                        .getModelAndView()
//                        .getViewName());
////                        .getModelMap());
//    }
    

//    //게시물 등록-페이지 호출 테스트
//    @Test
//    public void testShowBoardRegisterPage() throws Exception {
//        log.info(mockMvc.perform(MockMvcRequestBuilders.get("/myboard/register"))
//                        .andReturn()
//                        .getModelAndView()
//                        .getViewName());
////                        .getModelMap());
//    }

//    //게시물 등록 처리 테스트
//    @Test
//    public void testRegisterNewBoard() throws Exception {
//        String resultPage =
//                mockMvc.perform(MockMvcRequestBuilders.post("/myboard/register")
//                                                          .param("btitle", "게시물 등록 -컨트롤러 테스트 제목")
//                                                          .param("bcontent", "게시물 등록 -컨트롤러 테스트 내용")
//                                                          .param("bwriter", "test"))
//                        .andReturn().getModelAndView().getViewName();
//        log.info(resultPage);
//    }

//    //게시물 조회-수정 페이지 호출 테스트: /myboard/detail, /myboard/modify 2개 모두 테스트도 수행
//    @Test
//    public void testShowBoardDetail() throws Exception {
//        log.info(mockMvc.perform(MockMvcRequestBuilders
//                               .get("/myboard/detail/21")  // /myboard/modify로 수정한 후, 다시 테스트
//                               .param("bno", "1")
//                               .param("from", "m")
//                               )
//                        .andReturn()
//                        .getModelAndView()
//                        .getModelMap());
//    }

//    //게시물 수정 처리
//    @Test
//    public void testModifyBoard() throws Exception {
//        String resultPage = mockMvc.perform(MockMvcRequestBuilders
//                                              .post("/myboard/modify")
//                                              .param("bno", "1")
//                                              .param("btitle", "게시물 수정-컨트롤러 테스트 제목2222")   //2222추가 테스트
//                                              .param("bcontent", "게시물 수정-컨트롤러 테스트 내용2222")
//                                              .param("bwriter", "test"))
//                                      .andReturn()
//                                      .getModelAndView()
//                                      .getViewName();
//        log.info(resultPage);
//    }

    //게시물 삭제 요청(bdelflag를 1로 수정) 테스트
    @Test
    public void testSetBoardDeleted() throws Exception {
        String resultPage = mockMvc.perform(MockMvcRequestBuilders
                                              .post("/myboard/delete")
                                              .param("bno", "4"))
                                      .andReturn()
                                      .getModelAndView()
                                      .getViewName();
        log.info(resultPage);
    }

//    //특정 게시물 삭제 테스트 - 실제 삭제
//    @Test
//    public void testRemoveBoard() throws Exception {
//        //삭제전 데이터베이스에 게시물 번호 확인할 것
//        String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/myboard/remove")
//                                                                  .param("bno", "3"))
//                                  .andReturn()
//                                  .getModelAndView()
//                                  .getViewName();
//        log.info(resultPage);
//    }

    
}

