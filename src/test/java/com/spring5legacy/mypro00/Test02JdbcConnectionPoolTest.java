package com.spring5legacy.mypro00;

import static org.junit.Assert.fail;

import java.sql.Connection;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/mybatis-context.xml")
@Log4j
public class Test02JdbcConnectionPoolTest {

	private SqlSession sqlSession ;
	
	@Autowired
	public void setDataSource(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
//	@Test
//	public void testmyBatisSession() {
//		
//		try(Connection con = sqlSession.getConnection();) {
//			System.out.println(con);
//			System.out.println(sqlSession);
//			
//		}catch(Exception e) {
//			fail(e.getMessage());
//		}
//		
//	}
	
}
