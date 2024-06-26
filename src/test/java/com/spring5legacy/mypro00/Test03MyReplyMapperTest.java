package com.spring5legacy.mypro00;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.spring5legacy.mypro00.common.paging.MyReplyPagingDTO;
import com.spring5legacy.mypro00.mapper.MyBoardMapper;
import com.spring5legacy.mypro00.mapper.MyReplyMapper;


@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/mybatis-context.xml") public class Test03MyReplyMapperTest {
	private MyReplyMapper myReplyMapper;

	@Autowired
	public void setMyReplyMapper (MyReplyMapper myReplyMapper) { 
		this.myReplyMapper = myReplyMapper; System.out.println("MyReplyMapper 주입됨");
	}
	
	//특정 게시물에 대한 댓글 페이징 목록 조회 테스트
	@Test
	public void testSelectMyReplyList() {
		myReplyMapper.selectReplyList (new MyReplyPagingDTO(229570L, 2)) 
						.forEach(myReply -> System.out.println(myReply));
	}

}