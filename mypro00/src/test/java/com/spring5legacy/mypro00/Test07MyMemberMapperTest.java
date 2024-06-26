package com.spring5legacy.mypro00;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.spring5legacy.mypro00.domain.MyAuthorityVO;
import com.spring5legacy.mypro00.domain.MyMemberVO;
import com.spring5legacy.mypro00.mapper.MyMemberMapper;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({	"file:src/main/webapp/WEB-INF/spring/mybatis-context.xml",
                        "file:src/main/webapp/WEB-INF/spring/security-context.xml",
                        "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
@Log4j
public class Test07MyMemberMapperTest {
    //사용자 암호의 암호화 빈 주입
    private PasswordEncoder pwencoder;

    @Autowired
    public void setPwencoder (PasswordEncoder pwencoder) {
        this.pwencoder = pwencoder;
    }

    //매퍼인터페이스 주입
    private MyMemberMapper myMemberMapper;
    
    @Autowired
    public void setMyMemberMapper (MyMemberMapper myMemberMapper) {
        this.myMemberMapper = myMemberMapper;
    }

//    //테스트 메서드 1: 회원 등록 테스트
//    @Test
//    public void testInsertMyMember() {
//    	
//        MyMemberVO myMember = new MyMemberVO();
//        
//        for(int i = 0; i <= 100; i++) {
//
//            myMember.setUserpw(pwencoder.encode("pw" + i));
//
//            if(i <= 70) {
//                myMember.setUserid("user" + i);
//                myMember.setUsername("일반사용자" + i);
//            
//            } else if (i <= 80) {
//                myMember.setUserid("member" + i);
//                myMember.setUsername("중급사용자" + i);
//                
//            } else if (i <= 90) {
//                myMember.setUserid("manager" + i);
//                myMember.setUsername("운영자" + i);
//                
//            } else {
//                myMember.setUserid("admin" + i);
//                myMember.setUsername("관리자" + i);
//            }            
//            log.info(myMember);            
//            myMemberMapper.insertMember(myMember);
//        } //for-End
//    }
    
//    //테스트 메서드 2: 회원 권한 등록 테스트
//    @Test
//    public void testInsertMyMemAuthority() {
//        MyAuthorityVO myAuthority = new MyAuthorityVO();
//        
//        for(int i = 0; i <= 100; i++) {
//
//            if(i <= 70) {
//                myAuthority.setUserid("user" + i);
//                myAuthority.setAuthority("USER");
//                
//            } else if (i <= 80) {
//                myAuthority.setUserid("member" + i);
//                myAuthority.setAuthority("ROLE_MEMBER");
//                
//            } else if (i <= 90) {
//                myAuthority.setUserid("manager" + i);
//                myAuthority.setAuthority("ROLE_MANAGER");
//                
//            } else {
//                myAuthority.setUserid("admin" + i);
//                myAuthority.setAuthority("ADMIN");
//                
//            }
//            log.info(myAuthority);
//            myMemberMapper.insertMemberAuth(myAuthority) ;
//        } //for-End
//
//        myAuthority.setUserid("admin99");
//        myAuthority.setAuthority("ROLE_MANAGER");
//        myMemberMapper.insertMemberAuth(myAuthority);
//        
//        myAuthority.setUserid("admin99");
//        myAuthority.setAuthority("ROLE_MEMBER");
//        myMemberMapper.insertMemberAuth(myAuthority);
//        
//        myAuthority.setUserid("admin91");
//        myAuthority.setAuthority("ROLE_MANAGER");
//        myMemberMapper.insertMemberAuth(myAuthority);
//    }
    
    //테스트 메서드 3: 회원 정보 조회 테스트 
    @Test
    public void testRead() {
        MyMemberVO myMember = myMemberMapper.selectMember("admin99");
        log.info(myMember);
        
        myMember.getAuthList().forEach(authorityVO -> log.info(authorityVO));
    }
}

