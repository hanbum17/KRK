package com.spring5legacy.mypro00;

import static org.junit.Assert.fail;

import java.sql.Connection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/mybatis-context.xml")

public class Test02MybatisSessionTest {
	
	private SqlSessionTemplate sqlSession ;
	
	@Autowired
	public void setSqlSession(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession ;
		System.out.println(this.sqlSession);
		
	}
	
	@Test
	public void testMyBatisSession() {
		
		try(Connection con = sqlSession.getConnection()){
			System.out.println(con);
			System.out.println(sqlSession);
			//sqlSession.close(); //Manual close is not allowed over a Spring managed SqlSession
		} catch(Exception e) {
			fail(e.getMessage());
		}
		
	}

}
